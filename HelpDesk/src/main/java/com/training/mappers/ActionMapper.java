package com.training.mappers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.training.enums.Action;
import com.training.enums.State;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ActionMapper {

    UserService userService;

    public Ticket fillActions(Ticket ticket) {
        User currentUser = userService.findCurrentUser();

        Map<State, List<Action>> employeeActionListMap = Maps.newHashMap();
        Map<State, List<Action>> managerActionListMap = Maps.newHashMap();
        Map<State, List<Action>> engineerActionListMap = Maps.newHashMap();

        employeeActionListMap.put(State.DRAFT, Lists.newArrayList(Action.SUBMIT, Action.CANCEL));
        employeeActionListMap.put(State.DECLINED, Lists.newArrayList(Action.SUBMIT, Action.CANCEL));

        engineerActionListMap.put(State.APPROVED, Lists.newArrayList(Action.ASSIGN_TO_ME, Action.CANCEL));
        engineerActionListMap.put(State.IN_PROGRESS, Lists.newArrayList(Action.DONE));

        if (currentUser == ticket.getOwner()) {
            managerActionListMap.put(State.DRAFT, Lists.newArrayList(Action.SUBMIT, Action.CANCEL));
            managerActionListMap.put(State.DECLINED, Lists.newArrayList(Action.SUBMIT, Action.CANCEL));
        } else {
            managerActionListMap.put(State.NEW, Lists.newArrayList(Action.APPROVE, Action.DECLINE));
        }

        switch (currentUser.getRole()) {
            case EMPLOYEE: {
                ticket.setActions(employeeActionListMap.get(ticket.getState()));
                break;
            }
            case MANAGER: {
                ticket.setActions(managerActionListMap.get(ticket.getState()));
                if (currentUser == ticket.getOwner() && ticket.getState() == State.DRAFT) {
                    ticket.setActions(Lists.newArrayList(Action.SUBMIT, Action.CANCEL));
                }
                if (currentUser == ticket.getOwner() && ticket.getState() == State.DECLINED) {
                    ticket.setActions(Lists.newArrayList(Action.SUBMIT, Action.CANCEL));
                }
                break;
            }
            case ENGINEER: {
                ticket.setActions(engineerActionListMap.get(ticket.getState()));
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentUser.getRole());
        }
        return ticket;
    }
}

