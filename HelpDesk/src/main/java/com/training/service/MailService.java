package com.training.service;

import com.training.model.Ticket;

public interface MailService {
    void sendNewTicketForApproval(Ticket ticket);

    void sendTicketWasApproved(Ticket ticket);

    void sendTicketWasDeclined(Ticket ticket);

    void sendTicketWasCanceledByManager(Ticket ticket);

    void sendTicketWasCanceledByEngineer(Ticket ticket);

    void sendTicketWasDone(Ticket ticket);

    void sendFeedbackWasProvided(Ticket ticket);
}
