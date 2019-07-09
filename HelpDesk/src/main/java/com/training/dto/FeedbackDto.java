package com.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDto {

    @Range(min = 1, max = 5, message = "Rate must be between 1 and 5")
    private Integer rate;

    @Size(max = 100, message = "Feedback text must be less than 500")
    @Pattern(regexp = "^[A-Za-z0-9\\s~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+", message = "Comment has invalid symbols")
    private String text;
}
