package com.training.service.impl;

import com.google.common.collect.Maps;
import com.training.enums.Action;
import com.training.enums.Role;
import com.training.enums.State;
import com.training.mappers.ActionMapper;
import com.training.model.Ticket;
import com.training.repository.TicketRepo;
import com.training.service.ActionService;
import com.training.service.HistoryService;
import com.training.service.MailService;
import com.training.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.BiConsumer;

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j
public class ActionServiceImpl implements ActionService {

    HistoryService historyService;

    UserService userService;

    MailService mailService;

    TicketRepo ticketRepo;

    ActionMapper actionMapper;

    Map<Action, BiConsumer<Ticket, State>> actionConsumerMap;

    public ActionServiceImpl(HistoryService historyService, UserService userService, MailService mailService,
                             TicketRepo ticketRepo, ActionMapper actionMapper) {
        this.historyService = historyService;
        this.userService = userService;
        this.mailService = mailService;
        this.ticketRepo = ticketRepo;
        this.actionMapper = actionMapper;


        this.actionConsumerMap = Maps.newHashMap();
        actionConsumerMap.put(Action.SUBMIT, this::submit);
        actionConsumerMap.put(Action.APPROVE, this::approve);
        actionConsumerMap.put(Action.DECLINE, this::decline);
        actionConsumerMap.put(Action.CANCEL, this::cancel);
        actionConsumerMap.put(Action.ASSIGN_TO_ME, this::assignToMe);
        actionConsumerMap.put(Action.DONE, this::done);
    }

    @Override
    public void processAction(Action action, Long ticketId) {
        Ticket ticket = ticketRepo.findById(ticketId);
        State oldState = ticket.getState();
        if (actionMapper.fillActions(ticket).getActions().contains(action)) {
            actionConsumerMap.get(action).accept(ticket, oldState);
        }
    }

    private void submit(Ticket ticket, State oldState) {
        State newState = State.NEW;
        changeState(ticket, oldState, newState);
        mailService.sendNewTicketForApproval(ticket);
    }

    private void approve(Ticket ticket, State oldState) {
        State newState = State.APPROVED;
        ticket.setApprover(userService.findCurrentUser());
        changeState(ticket, oldState, newState);
        mailService.sendTicketWasApproved(ticket);
    }

    private void decline(Ticket ticket, State oldState) {
        State newState = State.DECLINED;
        ticket.setApprover(userService.findCurrentUser());
        changeState(ticket, oldState, newState);
        mailService.sendTicketWasDeclined(ticket);
    }

    private void cancel(Ticket ticket, State oldState) {
        State newState = State.CANCELED;
        changeState(ticket, oldState, newState);
        if (userService.findCurrentUser().getRole() == Role.MANAGER) {
            mailService.sendTicketWasCanceledByManager(ticket);
        } else if (userService.findCurrentUser().getRole() == Role.ENGINEER) {
            mailService.sendTicketWasCanceledByEngineer(ticket);
        }
    }

    private void assignToMe(Ticket ticket, State oldState) {
        State newState = State.IN_PROGRESS;
        ticket.setAssignee(userService.findCurrentUser());
        changeState(ticket, oldState, newState);
    }

    private void done(Ticket ticket, State oldState) {
        State newState = State.DONE;
        changeState(ticket, oldState, newState);
        mailService.sendTicketWasDone(ticket);
    }

    private void changeState(Ticket ticket, State oldState, State newState) {
        ticket.setState(newState);
        ticketRepo.save(ticket);
        historyService.createRecordTicketStatusChanged(ticket, oldState, newState);
    }
}
