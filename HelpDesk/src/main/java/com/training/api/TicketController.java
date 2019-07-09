package com.training.api;

import com.training.dto.TicketDto;
import com.training.model.Ticket;
import com.training.service.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tickets")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TicketController {

    TicketService ticketService;

    static String TICKETS_URL = "/help-desk/tickets/";


    @GetMapping(value = "/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ticketService.findTicketById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAllTickets());
    }

    @GetMapping(value = "/my")
    public ResponseEntity<List<Ticket>> getMyTickets() {
        return ResponseEntity.ok(ticketService.findMyTickets());
    }


    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PostMapping
    public ResponseEntity saveTicket(@Valid @RequestBody TicketDto ticketDto) {
        Long ticketId = ticketService.saveTicket(ticketDto);

        return ResponseEntity.created(URI.create(TICKETS_URL + ticketId + "/")).build();
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PutMapping("/{ticketId}")
    public ResponseEntity updateTicket(@Valid @RequestBody TicketDto ticketDto, @PathVariable Long ticketId) {
        ticketDto.setId(ticketId);
        ticketService.updateTicket(ticketDto);

        return ResponseEntity.created(URI.create(TICKETS_URL + ticketId + "/")).build();
    }
}
