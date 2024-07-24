package com.example.finance_tracker.service;

import com.example.finance_tracker.model.EmployerDetails;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.repository.EmployerDetailsRepository;
import com.example.finance_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmployerDetailsServiceImpl implements EmployerDetailsService{

    private final UserRepository userRepo;
    private final EmployerDetailsRepository empDetailsRepo;

    @Override
    public EmployerDetails saveEmployerDetails(Long userId, EmployerDetails employerDetails) {
        User userDetails = userRepo.findById(userId).orElseThrow();
        return empDetailsRepo.save(employerDetails);
    }

    @Override
    public void removeEmployerDetails(Long userId) {
        EmployerDetails existingEmployerDetails = empDetailsRepo.findByUserId(userId);
            empDetailsRepo.delete(existingEmployerDetails);

    }

    @Override
    public EmployerDetails updateEmployerDetails(Long userId, EmployerDetails employerDetails) {
        EmployerDetails existingEmployerDetails = empDetailsRepo.findByUserId(userId);
            return empDetailsRepo.save(existingEmployerDetails);

    }

    @Override
    public EmployerDetails getEmployerDetails(Long userId) {
        return empDetailsRepo.findByUserId(userId);
    }
}
