package com.training.services;

import com.google.common.collect.Lists;
import com.training.dto.AttachmentDto;
import com.training.exceptions.UnallowedFileTypeException;
import com.training.mappers.AttachmentMapper;
import com.training.model.Attachment;
import com.training.model.Ticket;
import com.training.repository.AttachmentRepo;
import com.training.service.AttachmentService;
import com.training.service.HistoryService;
import com.training.service.impl.AttachmentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class AttachmentServiceTest {

    @Mock
    private AttachmentRepo attachmentRepoMock;

    @Mock
    private HistoryService historyServiceMock;

    private String fileUUID = "181b16ee-0eef-4ddd-b02b-75d331f30433";

    private String fileName = "test.file";

    private byte[] fileBytes = fileUUID.getBytes();

    private Attachment attachment = new Attachment()
            .setId(1L)
            .setBlob(fileBytes)
            .setName(fileUUID + fileName)
            .setTicket(new Ticket());


    private AttachmentDto attachmentDto = new AttachmentDto()
            .setName(fileUUID + fileName)
            .setLink("http://localhost:8080/help-desk/api/attachments/" + fileUUID + fileName);

    private AttachmentService attachmentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        attachmentService = new AttachmentServiceImpl(attachmentRepoMock, new AttachmentMapper(attachmentRepoMock),
                historyServiceMock);
    }

    @Test
    public void testSaveAttachment() {
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "image/jpeg", fileUUID.getBytes());
        attachmentDto = attachmentService.save(multipartFile);

        Assert.assertEquals(fileName, attachmentDto.getName().substring(36));
    }

    @Test(expected = UnallowedFileTypeException.class)
    public void testSaveAttachmentWithUnallowedFileType() {
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "image/tiff", fileUUID.getBytes());

        attachmentService.save(multipartFile);
    }

    @Test
    public void testUpdateTicketAttachments() {
        when(attachmentRepoMock.findAttachmentByName(anyString())).thenReturn(Optional.of(attachment));

        attachmentService.update(Lists.newArrayList(attachmentDto), new Ticket());
    }

    @Test
    public void testGetAttachment() {
        when(attachmentRepoMock.findAttachmentByName(anyString())).thenReturn(Optional.of(attachment));

        Attachment attachment = attachmentService.findByName(fileName);

        Assert.assertEquals(attachment.getName(), fileUUID + fileName);
        Assert.assertEquals(attachment.getBlob(), fileBytes);
    }

    @Test
    public void testFindTicketAttachments() {
        when(attachmentRepoMock.findAttachmentsByTicketId(anyLong())).thenReturn(Lists.newArrayList(attachment));

        List<AttachmentDto> attachments = attachmentService.findByTicketId(1L);

        Assert.assertEquals(1, attachments.size());
        Assert.assertEquals(attachments.get(0).getName(), fileUUID + fileName);
        Assert.assertEquals(attachments.get(0).getLink(), "http://localhost:8080/help-desk/api/attachments/"
                + fileUUID + fileName);
    }

    @Test
    public void testDeleteAttachment() {
        when(attachmentRepoMock.findAttachmentByName(anyString())).thenReturn(Optional.of(attachment));

        attachmentService.remove(fileName);
    }

}
