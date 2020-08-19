package dev.lochness.library.repository;

import dev.lochness.library.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getBooksCount() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Book.class)));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getId() > 0) {
            return em.merge(book);
        } else {
            em.persist(book);
            return book;
        }
    }

    @Override
    public void deleteBookById(long id) {
        Query query =
                em.createQuery("DELETE FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book findBookById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT DISTINCT b FROM Book b " +
                        "LEFT JOIN FETCH b.authors a LEFT JOIN b.genres LEFT JOIN b.comments ",
                Book.class).getResultList();
    }

    @Override
    public List<Book> findBooksByAuthorId(long authorId) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b " +
                "LEFT JOIN FETCH b.authors a LEFT JOIN b.genres g LEFT JOIN b.comments c " +
                "WHERE a.id = :id ", Book.class);
        query.setParameter("id", authorId);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooksByGenreId(long genreId) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b " +
                "LEFT JOIN FETCH b.authors a LEFT JOIN b.genres g LEFT JOIN b.comments c " +
                "WHERE g.id = :id ", Book.class);
        query.setParameter("id", genreId);
        return query.getResultList();
    }
}
