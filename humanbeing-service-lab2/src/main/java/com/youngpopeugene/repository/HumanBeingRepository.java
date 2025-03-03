package com.youngpopeugene.repository;

import com.youngpopeugene.exception.NotFoundException;
import com.youngpopeugene.model.HumanBeing;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Dependent
public class HumanBeingRepository {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Transactional
    public HumanBeing save(HumanBeing humanBeing) {
        entityManager.persist(humanBeing.getCar());
        entityManager.persist(humanBeing.getCoordinates());
        entityManager.persist(humanBeing);
        return humanBeing;
    }

    @Transactional
    public List<HumanBeing> createHumanBeingQuery(String query, int firstResult, int maxResults) {
        TypedQuery<HumanBeing> typedQuery = entityManager.createQuery(query, HumanBeing.class);
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(maxResults);
        return typedQuery.getResultList();
    }

    @Transactional
    public Long countHumanBeings(String query) {
        String countQuery = query.replaceFirst("SELECT h", "SELECT COUNT(h)");
        int orderByIndex = countQuery.indexOf("ORDER BY");
        if (orderByIndex != -1) {
            countQuery = countQuery.substring(0, orderByIndex);
        }
        TypedQuery<Long> typedCountQuery = entityManager.createQuery(countQuery, Long.class);
        return typedCountQuery.getSingleResult();
    }

    @Transactional
    public HumanBeing findById(int id) {
        HumanBeing h = entityManager.find(HumanBeing.class, id);
        if (h == null) {
            throw new NotFoundException("HumanBeing not found");
        }
        return h;
    }

    @Transactional
    public void deleteById(int id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public HumanBeing update(HumanBeing humanBeing) {
        findById(humanBeing.getId());
        return entityManager.merge(humanBeing);
    }

    @Transactional
    public double calculateImpactSpeedSum() {
        Double sum = entityManager.createQuery("SELECT SUM(h.impactSpeed) FROM HumanBeing h", Double.class)
                .getSingleResult();
        return sum != null ? sum : 0;
    }

    @Transactional
    public int countBySoundtrackNameLessThan(String soundtrackName) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(h) FROM HumanBeing h WHERE h.soundtrackName < :soundtrackName", Long.class)
                .setParameter("soundtrackName", soundtrackName)
                .getSingleResult();
        return count != null ? count.intValue() : 0;
    }

    @Transactional
    public List<HumanBeing> findByTeamId(int teamId) {
        return entityManager.createQuery("SELECT h FROM HumanBeing h WHERE h.teamID = :teamId", HumanBeing.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }
}
