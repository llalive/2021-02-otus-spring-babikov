package dev.lochness.library.repository;

import dev.lochness.library.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

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
        Book book = em.find(Book.class, id);
        em.remove(book);
    }

    @Override
    public Book findBookById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-genres-entity-graph");
        List<Book> books = em.createQuery("SELECT b FROM Book b LEFT JOIN FETCH b.authors", Book.class)
                .getResultList();
        return em.createQuery("SELECT b FROM Book b WHERE b IN :books", Book.class)
                .setParameter("books", books)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }

}
