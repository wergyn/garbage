package com.training.service.impl;

import com.training.enums.Role;
import com.training.model.User;
import com.training.repository.UserRepo;
import com.training.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User findCurrentUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        return findUserByEmail(principal.getName());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<User> findUsersByRole(Role role) {
        return userRepo.findUsersByRole(role);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public boolean hasRole(String... roles) {
        for (String role : roles) {
            if (role.equals(findCurrentUser().getRole().name())) {
                return true;
            }
        }
        return false;
    }
}
