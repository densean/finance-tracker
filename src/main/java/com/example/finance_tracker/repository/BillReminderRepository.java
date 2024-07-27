package com.example.finance_tracker.repository;

import com.example.finance_tracker.model.billReminder.BillReminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface BillReminderRepository extends JpaRepository<BillReminder,Long> {
    Optional<BillReminder> findByIdAndUserId(Long id, Long userId);
    Page<BillReminder> findByUserIdAndDueDate(Long userId, LocalDate date, Pageable pageable);
    @Query("SELECT e FROM _bill_reminder e WHERE e.user.id = :userId AND YEAR(e.dueDate) = :year AND MONTH(e.dueDate) = :month")
    Page<BillReminder> findByUserIdAndYearAndMonth(Long userId, int year, int month, Pageable pageable);

}
