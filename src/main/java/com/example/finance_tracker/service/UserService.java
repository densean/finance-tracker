package com.example.finance_tracker.service;

import com.example.finance_tracker.model.Role;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.model.UserPublicDto;
import org.springframework.data.domain.Page;

import java.util.*;

public interface UserService {
    Page<UserPublicDto> getUsers(int pageNo, int pageSize);
    User saveUser(User user);
    User getUser(String username);
    UserPublicDto getUserById(Long id);
    User patchUser(Long id, User userDetails);
    void deleteUser(Long id);
    User updateUser(Long id, User userDetails);
    List<Role>getRoles();
    Role saveRole(Role role);
    Role getRoleById(Long id);
    void deleteRole(Long id);
    Role updateRole(Role role);
    Object assignRoleToUser(String username, String roleName);

}
