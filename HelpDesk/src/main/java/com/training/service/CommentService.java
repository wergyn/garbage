package com.training.service;

import com.training.model.Comment;

import java.util.List;

public interface CommentService {
    Long save(Comment comment);

    List<Comment> findByTicketId(Long ticketId);
}
