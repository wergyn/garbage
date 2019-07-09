package com.training.service;

import com.training.dto.TicketDto;
import com.training.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket findTicketById(Long id);

    List<Ticket> findAllTickets();

    List<Ticket> findMyTickets();

    Long saveTicket(TicketDto ticketDto);

    void updateTicket(TicketDto ticketDto);
}
