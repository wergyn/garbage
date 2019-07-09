package com.training.mappers;

import com.training.dto.FeedbackDto;
import com.training.model.Feedback;
import com.training.model.Ticket;
import com.training.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Log4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FeedbackMapper {

    UserService userService;

    ModelMapper modelMapper;

    public Feedback fromDto(FeedbackDto feedbackDto, Ticket ticket) {
        return Feedback.builder()
                .date(LocalDateTime.now())
                .text(feedbackDto.getText())
                .rate(feedbackDto.getRate())
                .ticket(ticket)
                .user(userService.findCurrentUser())
                .build();
    }

    public FeedbackDto toDto(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDto.class);
    }
}
