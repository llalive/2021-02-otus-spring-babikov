package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

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
        Author author = em.find(Author.class, id);
        em.remove(author);
    }

    @Override
    public List<Author> findAll() {
        EntityGraph<?> entityGraph = em.createEntityGraph("author-book-entity-graph");
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a", Author.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findAuthorById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
