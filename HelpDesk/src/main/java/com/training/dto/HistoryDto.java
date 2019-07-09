package com.training.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss", locale = "US")
    private LocalDateTime date;

    private String action;

    private String description;

    private User user;
}
