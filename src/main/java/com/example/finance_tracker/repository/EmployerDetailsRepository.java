package com.example.finance_tracker.repository;

import com.example.finance_tracker.model.EmployerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerDetailsRepository extends JpaRepository<EmployerDetails, Long> {
    EmployerDetails findByUserId(Long id);
}
