package com.training.mappers;

import com.training.dto.UserDto;
import com.training.enums.Role;
import com.training.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class UserMapperTest {

    private final UserMapper userMapper = new UserMapper(new ModelMapper());

    private static final Long USER_ID = 1L;

    private static final String FIRST_NAME = "Alexander";

    private static final String LAST_NAME = "Koputerko";

    private static final Role ROLE = Role.EMPLOYEE;

    private static final String EMAIL = "user1_mogilev@yopmail.com";

    private static final String PASSWORD = "P@ssword1";

    private User user = new User()
            .setId(USER_ID)
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setRole(ROLE)
            .setEmail(EMAIL)
            .setPassword(PASSWORD);

    @Test
    public void testUserToUserDto() {
        UserDto userDto = userMapper.toDto(user);

        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getRole(), userDto.getRole());
        Assert.assertEquals(user.getEmail(), userDto.getEmail());
    }
}
