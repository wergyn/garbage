package com.training.mappers;

import com.training.dto.AttachmentDto;
import com.training.exceptions.AttachmentNotFoundException;
import com.training.model.Attachment;
import com.training.repository.AttachmentRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AttachmentMapper {

    AttachmentRepo attachmentRepo;

    public AttachmentDto toDto(Attachment attachment) {
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setName(attachment.getName());
        attachmentDto.setLink("http://localhost:8080/help-desk/api/attachments/" + attachment.getName());
        return attachmentDto;
    }

    public Attachment toAttachment(AttachmentDto attachmentDto) {
        return attachmentRepo.findAttachmentByName(attachmentDto.getName())
                .orElseThrow(AttachmentNotFoundException::new);
    }
}
