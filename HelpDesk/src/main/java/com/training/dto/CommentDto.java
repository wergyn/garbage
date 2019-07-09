package com.training.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Size(max = 100, message = "Comment text must be less than 500")
    @Pattern(regexp = "^[A-Za-z0-9\\s~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+", message = "Comment has invalid symbols")
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss", locale = "US")
    private LocalDateTime date;

    private UserDto user;
}
