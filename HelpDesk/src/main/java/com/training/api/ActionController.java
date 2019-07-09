package com.training.api;

import com.training.dto.ActionDto;
import com.training.service.ActionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tickets/{ticketId}/actions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ActionController {

    ActionService actionService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processAction(@RequestBody ActionDto actionDto,
                              @PathVariable Long ticketId) {
        actionService.processAction(actionDto.getAction(), ticketId);
    }
}
