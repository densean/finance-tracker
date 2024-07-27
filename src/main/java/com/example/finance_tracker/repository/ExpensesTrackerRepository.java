package com.example.finance_tracker.repository;

import com.example.finance_tracker.model.expensesTracker.ExpensesTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface ExpensesTrackerRepository extends JpaRepository<ExpensesTracker, Long> {
    Page<ExpensesTracker> findByUserId(Long userId, Pageable pageable);
    Optional<ExpensesTracker> findByIdAndUserId(Long id, Long userId);
    Page<ExpensesTracker> findByUserIdAndDate(Long userId, LocalDate date, Pageable pageable);
    @Query("SELECT e FROM _expenses_tracker e WHERE e.user.id = :userId AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    Page<ExpensesTracker> findByUserIdAndYearAndMonth(Long userId, int year, int month, Pageable pageable);
    @Query("SELECT e FROM _expenses_tracker e WHERE e.user.id = :userId AND YEAR(e.date) = :year")
    Page<ExpensesTracker> findByUserIdAndYear(Long userId, int year, Pageable pageable);
    Page<ExpensesTracker> findByCategory(String category, Pageable pageable);
    Page<ExpensesTracker> findByUserIdAndCategory(Long userId, String category, Pageable pageable);
}

