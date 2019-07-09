package com.training.repository.impl;

import com.google.common.collect.Lists;
import com.training.enums.Role;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.repository.TicketRepo;
import com.training.repository.UserRepo;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TicketRepoImpl extends GenericRepoImpl<Ticket, Long> implements TicketRepo {

    @NonNull UserRepo userRepo;

    @Override
    public List<Ticket> findOwnTicketsByUserId(Long userId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> goodRoot = criteriaQuery.from(Ticket.class);
        criteriaQuery.select(goodRoot)
                .where(criteriaBuilder.equal(goodRoot.get("owner"), userId));

        return currentSession().createQuery(criteriaQuery).list();
    }

    @Override
    public List<Ticket> findApproveTicketsByUserId(Long userId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> goodRoot = criteriaQuery.from(Ticket.class);
        criteriaQuery.select(goodRoot)
                .where(criteriaBuilder.equal(goodRoot.get("approver"), userId));

        return currentSession().createQuery(criteriaQuery).list();
    }

    @Override
    public List<Ticket> findAssignTicketsByUserId(Long userId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> goodRoot = criteriaQuery.from(Ticket.class);
        criteriaQuery.select(goodRoot)
                .where(criteriaBuilder.equal(goodRoot.get("assignee"), userId));

        return currentSession().createQuery(criteriaQuery).list();
    }

    @Override
    public List<Ticket> findTicketsCreatedByRole(Role role) {
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        List<User> users = userRepo.findUsersByRole(role);
        List<Ticket> tickets = Lists.newArrayList();
        for (User user : users) {
            CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
            Root<Ticket> goodRootTicket = cq.from(Ticket.class);
            cq.select(goodRootTicket)
                    .where(cb.equal(goodRootTicket.get("owner"), user.getId()));
            tickets.addAll(currentSession().createQuery(cq).list());
        }

        return tickets;
    }
}
