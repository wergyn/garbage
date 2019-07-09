package com.training.service.impl;

import com.training.model.Comment;
import com.training.repository.CommentRepo;
import com.training.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentServiceImpl implements CommentService {

    CommentRepo commentRepo;

    @Override
    @Transactional
    public Long save(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Comment> findByTicketId(Long ticketId) {
        return commentRepo.findCommentsByTicketId(ticketId);
    }
}
