package com.training.mappers;

import com.training.dto.CommentDto;
import com.training.dto.UserDto;
import com.training.model.Comment;
import com.training.model.Ticket;
import com.training.model.User;
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
public class CommentMapper {

    ModelMapper modelMapper;

    UserService userService;

    public Comment fromDto(CommentDto commentDto, Ticket ticket) {
        return Comment.builder()
                .date(LocalDateTime.now())
                .text(commentDto.getText())
                .user(currentUser())
                .ticket(ticket)
                .build();
    }

    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .date(comment.getDate())
                .text(comment.getText())
                .user(modelMapper.map(comment.getUser(), UserDto.class))
                .build();
    }

    private User currentUser() {
        return userService.findCurrentUser();
    }
}
