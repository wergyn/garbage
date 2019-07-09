package com.training.service.impl;

import com.training.enums.State;
import com.training.model.Attachment;
import com.training.model.History;
import com.training.model.Ticket;
import com.training.repository.HistoryRepo;
import com.training.service.HistoryService;
import com.training.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HistoryServiceImpl implements HistoryService {

    UserService userService;

    HistoryRepo historyRepo;

    @Override
    public void createRecordTicketCreated(Ticket ticket) {
        historyRepo.save(createRecord(ticket, "Ticket is created",
                "Ticket is created"));
    }

    @Override
    public void createRecordTicketEdited(Ticket ticket) {
        historyRepo.save(createRecord(ticket, "Ticket is edited",
                "Ticket is edited"));
    }

    @Override
    public void createRecordTicketStatusChanged(Ticket ticket, State from, State to) {
        historyRepo.save(createRecord(ticket, "Ticket status is changed",
                "Ticket status is changed from " + from.name() + " to " + to.name()));
    }

    @Override
    public void createRecordAttachmentAdded(Ticket ticket, Attachment attachment) {
        String fileName = attachment.getName().substring(36);
        historyRepo.save(createRecord(ticket, "File is attached",
                "File attached: " + fileName));
    }

    @Override
    public void createRecordAttachmentRemoved(Ticket ticket, Attachment attachment) {
        String fileName = attachment.getName().substring(36);
        historyRepo.save(createRecord(ticket, "File is removed",
                "File removed: " + fileName));
    }

    private History createRecord(Ticket ticket, String action, String description) {
        return History.builder()
                .date(LocalDateTime.now())
                .action(action)
                .description(description)
                .ticket(ticket)
                .user(userService.findCurrentUser())
                .build();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<History> findByTicketId(Long ticketId) {
        return historyRepo.findHistoriesByTicketId(ticketId);
    }
}
