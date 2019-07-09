package com.training.mappers;

import com.training.dto.UserDto;
import com.training.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserMapper {

    ModelMapper modelMapper;

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
