package com.training.api;

import com.training.enums.Urgency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/urgencies")
public class UrgencyController {

    @GetMapping
    public ResponseEntity<List<Urgency>> getAllUrgencies() {
        List<Urgency> urgencies = Arrays.stream(Urgency.values()).collect(Collectors.toList());

        return ResponseEntity.ok(urgencies);
    }
}
