package com.example.finance_tracker.controller;

import com.example.finance_tracker.constants.ResponseConstants;
import com.example.finance_tracker.handlers.ResponseHandler;
import com.example.finance_tracker.model.EmployerDetails;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.service.EmployerDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class EmployerDetailsController {
    private final EmployerDetailsService empDetailsService;

    @GetMapping("/users/{userId}/employer-details")
    public ResponseEntity<Object> getEmployerDetails(@PathVariable Long userId) {
        EmployerDetails employerDetails = empDetailsService.getEmployerDetails(userId);

         return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                employerDetails,
                null,
                "getEmployerDetailsAsync");
    }
    @PostMapping("/users/{userId}/employer-details")
    public ResponseEntity<Object> saveEmployerDetails(@PathVariable Long userId, @RequestBody EmployerDetails employerDetails) {
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
