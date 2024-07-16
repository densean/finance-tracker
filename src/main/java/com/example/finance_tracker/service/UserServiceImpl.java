package com.example.finance_tracker.service;

import com.example.finance_tracker.model.Role;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.repository.RoleRepository;
import com.example.finance_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    @Override
    public User saveUser(User user) {
        log.info("added new user to db {}", user.getFirstName() + " " + user.getLastName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("added new role to db {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void assignRoleToUser(String username, String roleName) {
        log.info("assigned role {} to user {}", roleName, username);
     User user = userRepo.findByUsername(username);
     Role role = roleRepo.findByName(roleName);
     user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("user details {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll();
    }
}
