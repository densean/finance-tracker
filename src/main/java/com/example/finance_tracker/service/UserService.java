package com.example.finance_tracker.service;

import com.example.finance_tracker.model.Role;
import com.example.finance_tracker.model.User;

import java.util.*;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void assignRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User>getUsers();
}
