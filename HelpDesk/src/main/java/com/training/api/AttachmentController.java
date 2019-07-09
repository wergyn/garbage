package com.training.api;

import com.training.dto.AttachmentDto;
import com.training.exceptions.UnallowedFileTypeException;
import com.training.model.Attachment;
import com.training.service.AttachmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@Log4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AttachmentController {

    AttachmentService attachmentService;

    @PostMapping("/attachments")
    public ResponseEntity<AttachmentDto> fileUpload(@RequestAttribute("file") MultipartFile file) {
        Optional<MultipartFile> multipartFile = Optional.ofNullable(file);
        AttachmentDto attachmentDto = attachmentService
                .save(multipartFile.orElseThrow(UnallowedFileTypeException::new));

        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentDto);
    }

    @GetMapping(value = "/attachments/{fileName:.+}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) {
        Attachment attachment = attachmentService.findByName(fileName);
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.substring(36));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(attachment.getBlob(), respHeaders, HttpStatus.OK);
    }

    @GetMapping("tickets/{ticketId}/attachments")
    public ResponseEntity<List<AttachmentDto>> getTicketsAttachments(@PathVariable Long ticketId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.findByTicketId(ticketId));
    }

    @DeleteMapping("/attachments/{fileName:.+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttachment(@PathVariable String fileName) {
        attachmentService.remove(fileName);
    }
}
