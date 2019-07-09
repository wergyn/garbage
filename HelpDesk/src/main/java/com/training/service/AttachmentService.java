package com.training.service;

import com.training.dto.AttachmentDto;
import com.training.model.Attachment;
import com.training.model.Ticket;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    AttachmentDto save(MultipartFile file);

    List<AttachmentDto> findByTicketId(Long ticketId);

    Attachment findByName(String fileName);

    void update(List<AttachmentDto> attachments, Ticket ticket);

    void remove(String fileName);
}
