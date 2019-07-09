package com.training.api;

import com.training.dto.HistoryDto;
import com.training.mappers.HistoryMapper;
import com.training.service.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets/{ticketId}/histories")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HistoryController {

    HistoryService historyService;

    HistoryMapper historyMapper;

    @GetMapping
    public ResponseEntity<List<HistoryDto>> getTicketHistories(@PathVariable Long ticketId) {
        List<HistoryDto> histories = historyService.findByTicketId(ticketId)
                .stream()
                .map(historyMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(histories);
    }
}
