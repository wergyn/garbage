package com.training.service;

import com.training.enums.State;
import com.training.model.Attachment;
import com.training.model.History;
import com.training.model.Ticket;

import java.util.List;

public interface HistoryService {
    void createRecordTicketCreated(Ticket ticket);

    void createRecordTicketEdited(Ticket ticket);

    void createRecordTicketStatusChanged(Ticket ticket, State from, State to);

    void createRecordAttachmentAdded(Ticket ticket, Attachment attachment);

    void createRecordAttachmentRemoved(Ticket ticket, Attachment attachment);

    List<History> findByTicketId(Long ticketId);
}
