package com.example.finance_tracker.controller;

import com.example.finance_tracker.model.ResponseMapping;
import com.example.finance_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        return ResponseMapping.generateResponse("Success", HttpStatus.OK, userService.getUsers(), null);
    }
}
