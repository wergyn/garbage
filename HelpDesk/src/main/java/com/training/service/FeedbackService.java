package com.training.service;

import com.training.model.Feedback;

public interface FeedbackService {
    Long save(Feedback feedback);

    Feedback findFeedbackByTicketId(Long ticketId);
}
