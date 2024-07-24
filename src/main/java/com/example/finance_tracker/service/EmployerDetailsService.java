package com.example.finance_tracker.service;

import com.example.finance_tracker.model.EmployerDetails;
import com.example.finance_tracker.model.User;

public interface EmployerDetailsService {
    EmployerDetails saveEmployerDetails(Long userId, EmployerDetails employerDetails);
    void removeEmployerDetails (Long userId);
    EmployerDetails updateEmployerDetails(Long userId, EmployerDetails employerDetails);
    EmployerDetails getEmployerDetails(Long userId);
}
