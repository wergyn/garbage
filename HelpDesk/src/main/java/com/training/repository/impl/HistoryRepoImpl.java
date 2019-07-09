package com.training.repository.impl;

import com.training.model.History;
import com.training.repository.HistoryRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class HistoryRepoImpl extends GenericRepoImpl<History, Long> implements HistoryRepo {
    @Override
    public List<History> findHistoriesByTicketId(Long ticketId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<History> criteriaQuery = criteriaBuilder.createQuery(History.class);
        Root<History> root = criteriaQuery.from(History.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("ticket"), ticketId));

        return currentSession().createQuery(criteriaQuery).list();
    }
}
