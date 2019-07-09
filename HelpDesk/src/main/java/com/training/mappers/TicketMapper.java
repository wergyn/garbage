package com.training.mappers;

import com.training.dto.TicketDto;
import com.training.model.Ticket;
import com.training.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Log4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TicketMapper {

    ModelMapper modelMapper;

    UserService userService;

    public Ticket fromDto(TicketDto ticketDto) {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticket.setCreatedOn(LocalDate.now());
        ticket.setOwner(userService.findCurrentUser());

        return ticket;
    }
}
