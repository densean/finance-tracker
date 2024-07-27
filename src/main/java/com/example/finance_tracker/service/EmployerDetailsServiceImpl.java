package com.example.finance_tracker.service;

import com.example.finance_tracker.model.employerDetails.EmpDetailsNoAssociationDto;
import com.example.finance_tracker.model.employerDetails.EmployerDetails;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.repository.EmployerDetailsRepository;
import com.example.finance_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmployerDetailsServiceImpl implements EmployerDetailsService{

    private final UserRepository userRepo;
    private final EmployerDetailsRepository empDetailsRepo;

    @Override
    public EmpDetailsNoAssociationDto saveEmployerDetails(Long userId, EmployerDetails employerDetails) {
        User user = userRepo.findById(userId).orElseThrow();
        employerDetails.setUser(user);
        empDetailsRepo.save(employerDetails);
        EmpDetailsNoAssociationDto empDetailsNoAssociationDto = new EmpDetailsNoAssociationDto();
        empDetailsNoAssociationDto.setEmployerName(employerDetails.getEmployerName());
        empDetailsNoAssociationDto.setEmployerSalary(employerDetails.getEmployerSalary());
        empDetailsNoAssociationDto.setEmployerId(employerDetails.getEmployerId());
        empDetailsNoAssociationDto.setEmployerType(employerDetails.getEmployerType());
        return empDetailsNoAssociationDto;
    }

    @Override
    public void removeEmployerDetails(Long userId) {
        EmployerDetails existingEmployerDetails = empDetailsRepo.findByUserId(userId);
            empDetailsRepo.delete(existingEmployerDetails);
    }

    @Override
    public EmpDetailsNoAssociationDto updateEmployerDetails(Long userId, EmployerDetails employerDetails) {
        EmployerDetails existingEmployerDetails = empDetailsRepo.findByUserId(userId);
        existingEmployerDetails.setEmployerName(employerDetails.getEmployerName());
        existingEmployerDetails.setEmployerSalary(employerDetails.getEmployerSalary());
        existingEmployerDetails.setEmployerId(employerDetails.getEmployerId());
        existingEmployerDetails.setEmployerType(employerDetails.getEmployerType());
        empDetailsRepo.save(existingEmployerDetails);
        EmpDetailsNoAssociationDto empDetailsNoAssociationDto = new EmpDetailsNoAssociationDto();
        empDetailsNoAssociationDto.setEmployerName(employerDetails.getEmployerName());
        empDetailsNoAssociationDto.setEmployerSalary(employerDetails.getEmployerSalary());
        empDetailsNoAssociationDto.setEmployerId(employerDetails.getEmployerId());
        empDetailsNoAssociationDto.setEmployerType(employerDetails.getEmployerType());
        return empDetailsNoAssociationDto;

    }

    @Override
    public EmpDetailsNoAssociationDto getEmployerDetails(Long userId) {
        EmployerDetails employerDetails = empDetailsRepo.findByUserId(userId);
        EmpDetailsNoAssociationDto empDetails = new EmpDetailsNoAssociationDto();
        empDetails.setEmployerId(employerDetails.getEmployerId());
        empDetails.setEmployerSalary(employerDetails.getEmployerSalary());
        empDetails.setEmployerName(employerDetails.getEmployerName());
        empDetails.setEmployerType(employerDetails.getEmployerType());
        return empDetails;
    }

}
