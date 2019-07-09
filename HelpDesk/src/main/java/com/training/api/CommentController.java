package com.training.api;

import com.training.dto.CommentDto;
import com.training.mappers.CommentMapper;
import com.training.model.Comment;
import com.training.service.CommentService;
import com.training.service.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets/{ticketId}/comments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    CommentService commentService;

    TicketService ticketService;

    CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getTicketComments(@PathVariable Long ticketId) {
        List<CommentDto> comments = commentService.findByTicketId(ticketId)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity saveTicketComment(@Valid @RequestBody CommentDto commentDto,
                                            @PathVariable Long ticketId) {
        Comment comment = commentMapper.fromDto(commentDto, ticketService.findTicketById(ticketId));

        return ResponseEntity.ok(commentService.save(comment));
    }
}
