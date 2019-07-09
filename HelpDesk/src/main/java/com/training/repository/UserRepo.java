package com.training.repository;

import com.training.enums.Role;
import com.training.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends GenericRepo<User, Long> {
    Optional<User> findUserByEmail(String email);

    List<User> findUsersByRole(Role role);
}
