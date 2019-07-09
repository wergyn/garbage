package com.training.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.training.dto.TicketDto;
import com.training.enums.Role;
import com.training.enums.State;
import com.training.mappers.ActionMapper;
import com.training.mappers.CommentMapper;
import com.training.mappers.TicketMapper;
import com.training.model.Comment;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.repository.TicketRepo;
import com.training.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TicketServiceImpl implements TicketService {

    UserService userService;

    CommentService commentService;

    AttachmentService attachmentService;

    HistoryService historyService;

    MailService mailService;

    TicketRepo ticketRepo;

    TicketMapper ticketMapper;

    CommentMapper commentMapper;

    ActionMapper actionMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Ticket findTicketById(Long id) {
        return actionMapper.fillActions(ticketRepo.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Ticket> findAllTickets() {
        User user = currentUser();
        List<Ticket> tickets;
        switch (user.getRole()) {
            case EMPLOYEE: {
                tickets = ticketRepo.findOwnTicketsByUserId(user.getId());
                break;
            }
            case MANAGER: {
                List<Ticket> ownTickets = ticketRepo.findOwnTicketsByUserId(user.getId());
                List<Ticket> employeeTickets = ticketRepo.findTicketsCreatedByRole(Role.EMPLOYEE).stream()
                        .filter(ticket -> ticket.getState() == State.NEW)
                        .collect(Collectors.toList());
                List<Ticket> approveTickets = ticketRepo.findApproveTicketsByUserId(user.getId()).stream()
                        .filter(ticket ->
                                ticket.getState() == State.APPROVED ||
                                        ticket.getState() == State.DECLINED ||
                                        ticket.getState() == State.CANCELED ||
                                        ticket.getState() == State.IN_PROGRESS ||
                                        ticket.getState() == State.DONE
                        ).collect(Collectors.toList());
                Iterable<Ticket> combinedIterables = Iterables.unmodifiableIterable(
                        Iterables.concat(ownTickets, approveTickets, employeeTickets));
                tickets = Lists.newArrayList(combinedIterables);
                break;
            }
            case ENGINEER: {
                List<Ticket> employeeTickets = ticketRepo.findTicketsCreatedByRole(Role.EMPLOYEE).stream()
                        .filter(ticket -> ticket.getState() == State.APPROVED)
                        .collect(Collectors.toList());
                List<Ticket> managerTickets = ticketRepo.findTicketsCreatedByRole(Role.MANAGER).stream()
                        .filter(ticket -> ticket.getState() == State.APPROVED)
                        .collect(Collectors.toList());
                List<Ticket> assignTickets = ticketRepo.findAssignTicketsByUserId(user.getId()).stream()
                        .filter(ticket -> ticket.getState() == State.IN_PROGRESS || ticket.getState() == State.DONE)
                        .collect(Collectors.toList());
                Iterable<Ticket> combinedIterables = Iterables.unmodifiableIterable(
                        Iterables.concat(employeeTickets, managerTickets, assignTickets));
                tickets = Lists.newArrayList(combinedIterables);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + user.getRole());
        }

        return tickets.stream()
                .map(actionMapper::fillActions)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Ticket> findMyTickets() {
        User user = currentUser();
        List<Ticket> tickets;
        switch (user.getRole()) {
            case EMPLOYEE: {
                tickets = ticketRepo.findOwnTicketsByUserId(user.getId());
                break;
            }
            case MANAGER: {
                List<Ticket> ownTickets = ticketRepo.findOwnTicketsByUserId(user.getId());
                List<Ticket> approveTickets = ticketRepo.findApproveTicketsByUserId(user.getId()).stream()
                        .filter(ticket -> ticket.getState() == State.APPROVED)
                        .collect(Collectors.toList());
                Iterable<Ticket> combinedIterables = Iterables.unmodifiableIterable(
                        Iterables.concat(ownTickets, approveTickets));
                tickets = Lists.newArrayList(combinedIterables);
                break;
            }
            case ENGINEER: {
                tickets = ticketRepo.findAssignTicketsByUserId(user.getId());
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + user.getRole());
        }
        return tickets.stream()
                .map(actionMapper::fillActions)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long saveTicket(TicketDto ticketDto) {
        Ticket ticket = ticketMapper.fromDto(ticketDto);
        Ticket createdTicket = ticketRepo.findById(ticketRepo.save(ticket));

        saveCommentAndAttachments(ticketDto, createdTicket);

        historyService.createRecordTicketCreated(ticket);
        return createdTicket.getId();
    }

    @Override
    @Transactional
    public void updateTicket(TicketDto ticketDto) {
        Ticket ticket = ticketMapper.fromDto(ticketDto);
        ticketRepo.update(ticket);
        Ticket updatedTicket = findTicketById(ticketDto.getId());

        saveCommentAndAttachments(ticketDto, updatedTicket);

        historyService.createRecordTicketEdited(updatedTicket);
    }

    private void saveCommentAndAttachments(TicketDto ticketDto, Ticket ticket) {
        Comment comment = commentMapper.fromDto(ticketDto.getComment(), ticket);
        if (comment.getText() != null && !Objects.equals(comment.getText(), "")) {
            commentService.save(comment);
        }

        attachmentService.update(ticketDto.getAttachments(), ticket);

        if (ticket.getState() != State.DRAFT) {
            mailService.sendNewTicketForApproval(ticket);
        }
    }

    private User currentUser() {
        return userService.findCurrentUser();
    }
}
