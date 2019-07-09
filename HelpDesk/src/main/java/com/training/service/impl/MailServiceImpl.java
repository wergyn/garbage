package com.training.service.impl;

import com.google.common.collect.Lists;
import com.training.enums.Role;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.service.MailService;
import com.training.service.UserService;
import com.training.utils.MailSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Log4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MailServiceImpl implements MailService {

    UserService userService;

    MailSender mailSender;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void sendMail(Ticket ticket, List<String> recipients, String templateName, String subject) {
        Context context = new Context();
        context.setVariable("ticket", ticket);

        recipients.forEach(recipient -> executorService.submit(() ->
                mailSender.sendMail(context, templateName, recipient, subject)));
    }

    @Override
    public void sendNewTicketForApproval(Ticket ticket) {
        String templateName = "NewTicketForApproval";
        String subject = "New ticket for approval";

        List<String> recipients = userService.findUsersByRole(Role.MANAGER).stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        sendMail(ticket, recipients, templateName, subject);
    }

    @Override
    public void sendTicketWasApproved(Ticket ticket) {
        String templateName = "TicketWasApproved";
        String subject = "Ticket was approved";

        List<String> recipients = userService.findUsersByRole(Role.ENGINEER).stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        recipients.add(ticket.getOwner().getEmail());

        sendMail(ticket, recipients, templateName, subject);
    }

    @Override
    public void sendTicketWasDeclined(Ticket ticket) {
        String templateName = "TicketWasDeclined";
        String subject = "Ticket was declined";

        List<String> recipients = Lists.newArrayList(ticket.getOwner().getEmail());

        sendMail(ticket, recipients, templateName, subject);
    }

    @Override
    public void sendTicketWasCanceledByManager(Ticket ticket) {
        String templateName = "TicketWasCancelledByManager";
        String subject = "Ticket was cancelled";

        List<String> recipients = Lists.newArrayList(ticket.getOwner().getEmail());

        sendMail(ticket, recipients, templateName, subject);
    }

    @Override
    public void sendTicketWasCanceledByEngineer(Ticket ticket) {
        String templateName = "TicketWasCanceledByEngineer";
        String subject = "Ticket was cancelled";

        List<String> recipients = Lists.newArrayList(ticket.getOwner().getEmail(), ticket.getApprover().getEmail());

        sendMail(ticket, recipients, templateName, subject);
    }

    @Override
    public void sendTicketWasDone(Ticket ticket) {
        String templateName = "TicketWasDone";
        String subject = "Ticket was done";

        List<String> recipients = Lists.newArrayList(ticket.getOwner().getEmail());

        sendMail(ticket, recipients, templateName, subject);
    }

    @Override
    public void sendFeedbackWasProvided(Ticket ticket) {
        String templateName = "FeedbackWasProvided";
        String subject = "Feedback was provided";

        List<String> recipients = Lists.newArrayList(ticket.getAssignee().getEmail());

        sendMail(ticket, recipients, templateName, subject);
    }
}
