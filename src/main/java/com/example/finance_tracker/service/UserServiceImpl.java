package com.example.finance_tracker.service;

import com.example.finance_tracker.model.Role;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.model.UserPublicDto;
import com.example.finance_tracker.repository.RoleRepository;
import com.example.finance_tracker.repository.UserRepository;
import com.example.finance_tracker.tools.NonNullPropertiesIdentifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.info("Not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User is {}", user);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role-> {authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("added new user to db {}", user.getFirstName() + " " + user.getLastName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("added new role to db {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public Object assignRoleToUser(String username, String roleName) {
        log.info("assigned role {} to user {}", roleName, username);
     User user = userRepo.findByUsername(username);
     Role role = roleRepo.findByName(roleName);
     user.getRoles().add(role);
        return null;
    }

    @Override
    public User getUser(String username) {
        log.info("user details {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public Page<User> getUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        log.info("fetching all users");
        return userRepo.findAll(pageable);
    }

    @Override
    public UserPublicDto getUserById(Long id) {
        User userDetails = userRepo.findById(id).orElseThrow();
        UserPublicDto user = new UserPublicDto();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setDob(userDetails.getDob());
        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setRoles(userDetails.getRoles());
        return user;
    }

    @Override
        public User patchUser(Long id, User userDetails) {
            User user = userRepo.findById(id).orElseThrow();
            NonNullPropertiesIdentifier.copyNonNullProperties(userDetails, user);
            return userRepo.save(user);
        }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        user.setDeleted(true);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepo.findById(id).orElseThrow();
        return userRepo.save(user);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepo.findById(id).orElseThrow();
    }

    @Override
    public void deleteRole(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public Role updateRole(Role roleDetails) {
        Role role = roleRepo.findById(roleDetails.getId()).orElseThrow();
        role.setName(roleDetails.getName());
        return roleRepo.save(role);
    }
}
