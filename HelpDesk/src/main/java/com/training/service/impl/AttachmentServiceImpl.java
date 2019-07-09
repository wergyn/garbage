package com.training.service.impl;

import com.training.dto.AttachmentDto;
import com.training.exceptions.UnallowedFileTypeException;
import com.training.mappers.AttachmentMapper;
import com.training.model.Attachment;
import com.training.model.Ticket;
import com.training.repository.AttachmentRepo;
import com.training.service.AttachmentService;
import com.training.service.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AttachmentServiceImpl implements AttachmentService {

    AttachmentRepo attachmentRepo;

    AttachmentMapper attachmentMapper;

    HistoryService historyService;

    static String ACCEPTED_TYPES = "image/jpeg, " +
            "image/png, image/jpg, " +
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document, " +
            "application/msword, application/pdf";

    static long FILE_MAX_SIZE = 5242880L;

    @Override
    public AttachmentDto save(MultipartFile file) {
        if (!ACCEPTED_TYPES.contains(Objects.requireNonNull(file.getContentType()))
                || file.getSize() > FILE_MAX_SIZE) {
            throw new UnallowedFileTypeException();
        }
        Attachment attachment = new Attachment();
        try {
            attachment.setBlob(file.getBytes());
        } catch (IOException e) {
            log.error("IO Exception!");
        }
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        attachment.setName(fileName);

        attachmentRepo.save(attachment);

        return attachmentMapper.toDto(attachment);
    }

    @Override
    public Attachment findByName(String fileName) {
        return attachmentRepo.findAttachmentByName(fileName).get();
    }

    @Override
    public List<AttachmentDto> findByTicketId(Long ticketId) {
        List<Attachment> attachments = attachmentRepo.findAttachmentsByTicketId(ticketId);
        return attachments.stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(List<AttachmentDto> attachmentsDtos, Ticket ticket) {
        List<Attachment> attachments = attachmentsDtos.stream()
                .map(attachmentMapper::toAttachment)
                .collect(Collectors.toList());
        attachments.forEach(attachment -> {
            if (attachment.getTicket() == null) {
                historyService.createRecordAttachmentAdded(ticket, attachment);
            }
        });
        attachmentRepo.updateAttachments(attachments, ticket.getId());
    }

    @Override
    public void remove(String fileName) {
        Attachment attachment = findByName(fileName);
        attachmentRepo.remove(attachment);
        if (attachment.getTicket() != null) {
            historyService.createRecordAttachmentRemoved(attachment.getTicket(), attachment);
        }
    }
}
