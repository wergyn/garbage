package com.training.repository;

import com.training.model.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepo extends GenericRepo<Attachment, Long> {
    Optional<Attachment> findAttachmentByName(String name);
    List<Attachment> findAttachmentsByTicketId(Long ticketId);
    void updateAttachments(List<Attachment> attachments, Long ticketId);
}
