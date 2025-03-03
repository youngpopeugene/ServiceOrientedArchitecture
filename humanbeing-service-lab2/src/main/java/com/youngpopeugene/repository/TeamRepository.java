package com.youngpopeugene.repository;

import com.youngpopeugene.model.Team;
import com.youngpopeugene.exception.NotFoundException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Dependent
public class TeamRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Transactional
    public Team save(Team team) {
        entityManager.persist(team);
        return team;
    }

    @Transactional
    public List<Team> createTeamQuery(String query, int firstResult, int maxResults) {
        TypedQuery<Team> typedQuery = entityManager.createQuery(query, Team.class);
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(maxResults);
        return typedQuery.getResultList();
    }

    @Transactional
    public Long countTeams(String query) {
        String countQuery = query.replaceFirst("SELECT t", "SELECT COUNT(t)");
        int orderByIndex = countQuery.indexOf("ORDER BY");
        if (orderByIndex != -1) {
            countQuery = countQuery.substring(0, orderByIndex);
        }
        TypedQuery<Long> typedCountQuery = entityManager.createQuery(countQuery, Long.class);
        return typedCountQuery.getSingleResult();
    }

    @Transactional
    public Team findById(int id) {
        Team h = entityManager.find(Team.class, id);
        if (h == null) {
            throw new NotFoundException("Team not found");
        }
        return h;
    }

    @Transactional
    public void deleteById(int id) {
        entityManager.createQuery("UPDATE HumanBeing h SET h.teamID = NULL WHERE h.teamID = :teamId")
                .setParameter("teamId", id)
                .executeUpdate();
        entityManager.remove(findById(id));
    }

    @Transactional
    public Team update(Team team) {
        findById(team.getId());
        return entityManager.merge(team);
    }
}
