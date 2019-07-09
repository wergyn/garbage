package com.training.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AttachmentDto {
    private String name;

    private String link;
}
