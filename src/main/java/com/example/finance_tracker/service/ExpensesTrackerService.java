package com.example.finance_tracker.service;

import com.example.finance_tracker.model.expensesTracker.ExpensesTracker;
import com.example.finance_tracker.model.expensesTracker.ExpensesTrackerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ExpensesTrackerService {
    ExpensesTracker saveExpenses (Long userId, ExpensesTracker expensesTracker);
    Optional<ExpensesTracker> getExpensesById (Long userId, Long expensesTrackerId);
    Page<ExpensesTracker> getExpenses (int pageNo, int pageSize, Long userId);
    Page<ExpensesTracker> getExpensesByDate (int pageNo, int pageSize, Long userId, LocalDate date);
    Page<ExpensesTracker> getExpensesByMonth(int year, int month, int pageNo, int pageSize, Long userId);
    Page<ExpensesTracker> getExpensesByYear (int year, int pageNo, int pageSize, Long userId);
    Page<ExpensesTrackerDto> getAllExpenses (int pageNo, int pageSize);
    void removeExpenses (Long userId, Long expensesTrackerId);
    ExpensesTracker updateExpenses(Long userId, ExpensesTracker expensesTracker, Long expensesTrackerId);
    Page<ExpensesTrackerDto> filterAllExpensesByCategory (int pageNo, int pageSize, String category);
    Page<ExpensesTracker> filterUserExpensesByCategory (int pageNo, int pageSize, String category, Long userId);
}
