package com.training.dto;

import com.training.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {
    private String firstName;

    private String lastName;

    private Role role;

    private String email;
}
