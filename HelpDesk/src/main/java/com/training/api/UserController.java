package com.training.api;

import com.training.dto.UserDto;
import com.training.mappers.UserMapper;
import com.training.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;

    UserMapper userMapper;

    @GetMapping(value = "/current")
    public ResponseEntity<UserDto> findCurrentUser() {
        UserDto userDto = userMapper.toDto(userService.findCurrentUser());

        return ResponseEntity.ok(userDto);
    }
}
