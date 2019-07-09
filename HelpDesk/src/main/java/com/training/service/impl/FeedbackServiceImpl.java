package com.training.service.impl;

import com.training.exceptions.FeedbackNotFoundException;
import com.training.model.Feedback;
import com.training.repository.FeedbackRepo;
import com.training.service.FeedbackService;
import com.training.service.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepo feedbackRepo;

    MailService mailService;

    @Override
    @Transactional
    public Long save(Feedback feedback) {
        mailService.sendFeedbackWasProvided(feedback.getTicket());

        return feedbackRepo.save(feedback);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Feedback findFeedbackByTicketId(Long ticketId) {
        return feedbackRepo.findFeedbackByTicketId(ticketId)
                .orElseThrow(FeedbackNotFoundException::new);
    }
}
