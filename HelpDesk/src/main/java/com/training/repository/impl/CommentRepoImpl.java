package com.training.repository.impl;

import com.training.model.Comment;
import com.training.repository.CommentRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CommentRepoImpl extends GenericRepoImpl<Comment, Long> implements CommentRepo {
    @Override
    public List<Comment> findCommentsByTicketId(Long ticketId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("ticket"), ticketId));

        return currentSession().createQuery(criteriaQuery).list();
    }
}
