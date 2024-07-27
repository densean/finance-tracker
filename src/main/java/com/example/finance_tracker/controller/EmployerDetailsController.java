package com.example.finance_tracker.controller;

import com.example.finance_tracker.constants.ResponseConstants;
import com.example.finance_tracker.handlers.ResponseHandler;
import com.example.finance_tracker.model.employerDetails.EmployerDetails;
import com.example.finance_tracker.service.EmployerDetailsService;
import com.example.finance_tracker.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class EmployerDetailsController {
    private final EmployerDetailsService empDetailsService;
    private final SecurityService securityService;

    @GetMapping("/users/{userId}/employer-details")
    public ResponseEntity<Object> getEmployerDetails(@PathVariable Long userId) {
         return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                 empDetailsService.getEmployerDetails(userId),
                null,
                "getEmployerDetailsAsync");
    }

    @PostMapping("/users/{userId}/employer-details")
    public ResponseEntity<Object> saveEmployerDetails(@PathVariable Long userId,@Valid @RequestBody EmployerDetails employerDetails) {
        log.info("userId in controller:{}", userId);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_POST,
                HttpStatus.OK,
                empDetailsService.saveEmployerDetails(userId, employerDetails),
                null,
                "saveEmployerDetailsAsync");
    }

    @DeleteMapping("/users/{userId}/employer-details")
    public ResponseEntity<Object> removeEmployerDetails(@PathVariable Long userId) {
                empDetailsService.removeEmployerDetails(userId);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_DELETE,
                HttpStatus.OK,
                null,
                null,
                "removeEmployerDetailsAsync");
    }

    @PutMapping("users/{userId}/employer-details")
    public ResponseEntity<Object> updateEmployerDetails(@PathVariable Long userId, @RequestBody EmployerDetails employerDetails) {
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_PUT,
                HttpStatus.OK,
                empDetailsService.updateEmployerDetails(userId, employerDetails),
                null,
                "removeEmployerDetailsAsync");
    }
}
