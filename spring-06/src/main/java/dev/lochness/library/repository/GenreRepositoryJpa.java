package dev.lochness.library.repository;

import dev.lochness.library.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
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
        Query query =
                em.createQuery("DELETE FROM Genre g WHERE g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Genre findGenreById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("SELECT g FROM Genre g " +
                "LEFT JOIN g.books ", Genre.class).getResultList();
    }
}
