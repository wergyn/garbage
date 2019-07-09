package com.training.repository;

import com.training.model.Feedback;

import java.util.Optional;

public interface FeedbackRepo extends GenericRepo<Feedback, Long> {
    Optional<Feedback> findFeedbackByTicketId(Long ticketId);
}
