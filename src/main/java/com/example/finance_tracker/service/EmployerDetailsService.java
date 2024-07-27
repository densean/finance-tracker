package com.example.finance_tracker.service;

import com.example.finance_tracker.model.employerDetails.EmpDetailsNoAssociationDto;
import com.example.finance_tracker.model.employerDetails.EmployerDetails;

public interface EmployerDetailsService {
    EmpDetailsNoAssociationDto saveEmployerDetails(Long userId, EmployerDetails employerDetails);
    void removeEmployerDetails (Long userId);
    EmpDetailsNoAssociationDto updateEmployerDetails(Long userId, EmployerDetails employerDetails);
    EmpDetailsNoAssociationDto getEmployerDetails(Long userId);
}
