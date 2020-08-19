package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getAuthorsCount() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Author.class)));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Author saveAuthor(Author author) {
        if (author.getId() > 0) {
            return em.merge(author);
        } else {
            em.persist(author);
            return author;
        }
    }

    @Override
    public void deleteAuthorById(long id) {
        Query query =
                em.createQuery("DELETE FROM Author a WHERE a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("SELECT a FROM Author a LEFT JOIN a.books GROUP BY a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findAuthorById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
