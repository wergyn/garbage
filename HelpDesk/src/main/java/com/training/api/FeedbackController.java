package com.training.api;

import com.training.dto.FeedbackDto;
import com.training.mappers.FeedbackMapper;
import com.training.model.Feedback;
import com.training.service.FeedbackService;
import com.training.service.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets/{ticketId}/feedback")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FeedbackController {

    FeedbackService feedbackService;

    TicketService ticketService;

    FeedbackMapper feedbackMapper;

    @GetMapping
    public ResponseEntity<FeedbackDto> getTicketComments(@PathVariable Long ticketId) {
        FeedbackDto feedback = feedbackMapper.toDto(feedbackService.findFeedbackByTicketId(ticketId));

        return ResponseEntity.ok(feedback);
    }

    @PostMapping
    public ResponseEntity saveTicketFeedback(@Valid @RequestBody FeedbackDto feedbackDto,
                                             @PathVariable Long ticketId) {
        Feedback feedback = feedbackMapper.fromDto(feedbackDto, ticketService.findTicketById(ticketId));

        return ResponseEntity.ok(feedbackService.save(feedback));
    }
}
