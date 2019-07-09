package com.training.repository;

import com.training.enums.Role;
import com.training.model.Ticket;

import java.util.List;

public interface TicketRepo extends GenericRepo<Ticket, Long> {
    List<Ticket> findOwnTicketsByUserId(Long userId);

    List<Ticket> findApproveTicketsByUserId(Long userId);

    List<Ticket> findAssignTicketsByUserId(Long userId);

    List<Ticket> findTicketsCreatedByRole(Role role);
}
