package dev.lochness.library.repository;

import dev.lochness.library.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getGenresCount() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Genre.class)));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Genre saveGenre(Genre genre) {
        if (genre.getId() > 0) {
            return em.merge(genre);
        } else {
            em.persist(genre);
            return genre;
        }
    }

    @Override
    public void deleteGenreById(Long id) {
        Genre genre = em.find(Genre.class, id);
        em.remove(genre);
    }

    @Override
    public Genre findGenreById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> findAll() {
        EntityGraph<?> entityGraph = em.createEntityGraph("genre-book-entity-graph");
        TypedQuery<Genre> query = em.createQuery("SELECT a FROM Genre a", Genre.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }
}
