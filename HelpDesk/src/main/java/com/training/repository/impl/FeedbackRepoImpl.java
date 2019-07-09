package com.training.repository.impl;

import com.training.model.Feedback;
import com.training.repository.FeedbackRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class FeedbackRepoImpl extends GenericRepoImpl<Feedback, Long> implements FeedbackRepo {

    @Override
    public Optional<Feedback> findFeedbackByTicketId(Long ticketId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Feedback> criteriaQuery = criteriaBuilder.createQuery(Feedback.class);
        Root<Feedback> root = criteriaQuery.from(Feedback.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("ticket"), ticketId));

        return currentSession().createQuery(criteriaQuery).list().stream().findFirst();
    }
}
