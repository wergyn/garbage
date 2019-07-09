package com.training.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.enums.State;
import com.training.enums.Urgency;
import com.training.model.Category;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class TicketDto {
    private Long id;

    @NotNull(message = "Please provide a category")
    private Category category;

    @Size(max = 100, message = "Ticket name must be less than 100")
    @Pattern(regexp = "^[A-Za-z0-9\\s~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+", message = "Invalid name")
    private String name;

    @Size(max = 500, message = "Ticket description must be less than 500")
    @Pattern(regexp = "^[A-Za-z0-9\\s~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+", message = "Invalid description")
    private String description;

    @NotNull(message = "Please provide a urgency")
    private Urgency urgency;

    private CommentDto comment;

    private State state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate desiredDate;

    List<AttachmentDto> attachments;
}
