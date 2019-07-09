package com.training.service;

import com.training.enums.Role;
import com.training.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    User findCurrentUser();

    List<User> findUsersByRole(Role role);

    boolean hasRole(String... roles);
}
