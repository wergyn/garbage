package com.training.repository.impl;

import com.training.model.Attachment;
import com.training.model.Ticket;
import com.training.repository.AttachmentRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentRepoImpl extends GenericRepoImpl<Attachment, Long> implements AttachmentRepo {

    @Override
    public Optional<Attachment> findAttachmentByName(String name) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Attachment> criteriaQuery = criteriaBuilder.createQuery(Attachment.class);
        Root<Attachment> root = criteriaQuery.from(Attachment.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("name"), name));

        return currentSession().createQuery(criteriaQuery).list().stream().findFirst();
    }

    @Override
    public List<Attachment> findAttachmentsByTicketId(Long ticketId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Attachment> criteriaQuery = criteriaBuilder.createQuery(Attachment.class);
        Root<Attachment> root = criteriaQuery.from(Attachment.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("ticket"), ticketId));

        return currentSession().createQuery(criteriaQuery).list();
    }

    @Override
    public void updateAttachments(List<Attachment> attachments, Long ticketId) {
        Ticket ticket = currentSession().get(Ticket.class, ticketId);
        for (Attachment attachment : attachments) {
            attachment.setTicket(ticket);
            currentSession().save(attachment);
        }
    }
}
