package com.training.repository;

import com.training.model.Comment;

import java.util.List;

public interface CommentRepo extends GenericRepo<Comment, Long> {
    List<Comment> findCommentsByTicketId(Long ticketId);
}
