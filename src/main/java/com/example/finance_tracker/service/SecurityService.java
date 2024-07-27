package com.example.finance_tracker.service;

import com.example.finance_tracker.model.User;
import com.example.finance_tracker.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("securityService")
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private final UserRepository userRepo;

    public boolean hasAccess(Authentication authentication, Long userId) {
        String loggedInUsername = authentication.getName();
        User loggedInUser = userRepo.findByUsername(loggedInUsername);
        return loggedInUser.getId().equals(userId) || loggedInUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_SUPER_ADMIN"));
    }
}
