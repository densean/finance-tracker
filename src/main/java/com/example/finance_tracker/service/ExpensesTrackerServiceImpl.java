package com.example.finance_tracker.service;

import com.example.finance_tracker.model.expensesTracker.ExpensesTracker;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.model.expensesTracker.ExpensesTrackerDto;
import com.example.finance_tracker.repository.ExpensesTrackerRepository;
import com.example.finance_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExpensesTrackerServiceImpl implements ExpensesTrackerService{

    private final UserRepository userRepo;
    private final ExpensesTrackerRepository expensesTrackerRepo;

    @Override
    public ExpensesTracker saveExpenses(Long userId, ExpensesTracker expensesDetails) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        expensesDetails.setUser(user);
        expensesTrackerRepo.save(expensesDetails);
        return expensesDetails;
    }

    @Override
    public Optional<ExpensesTracker> getExpensesById(Long userId, Long expensesTrackerId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        return expensesTrackerRepo.findById(expensesTrackerId);
    }

    @Override
    public Page<ExpensesTracker> getExpenses(int pageNo, int pageSize, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        log.info("pageNo {}, pageSize{}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return expensesTrackerRepo.findByUserId(userId, pageable);
    }

    @Override
    public Page<ExpensesTracker> getExpensesByDate(int pageNo, int pageSize, Long userId, LocalDate date) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return expensesTrackerRepo.findByUserIdAndDate(userId, date, pageable);
    }

    @Override
    public Page<ExpensesTracker> getExpensesByMonth(int year, int month, int pageNo, int pageSize, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        log.info("year: {}, month: {}, pageNo: {}, pageSize: {}", year, month, pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return expensesTrackerRepo.findByUserIdAndYearAndMonth(userId, year, month, pageable);
    }

    @Override
    public Page<ExpensesTracker> getExpensesByYear(int year, int pageNo, int pageSize, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return expensesTrackerRepo.findByUserIdAndYear(userId, year, pageable);
    }

    @Override
    public Page<ExpensesTrackerDto> getAllExpenses(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ExpensesTracker> expensesPage = expensesTrackerRepo.findAll(pageable);
        return expensesPage.map(this::convertToExpensesTrackerDto);
    }

    private ExpensesTrackerDto convertToExpensesTrackerDto(ExpensesTracker expenses) {
        ExpensesTrackerDto dto = new ExpensesTrackerDto();
        dto.setAmount(expenses.getAmount());
        dto.setRemarks(expenses.getRemarks());
        dto.setUsername(expenses.getUser().getUsername());
        dto.setDate(expenses.getDate());
        dto.setCategory(expenses.getCategory());
        dto.setPaymentMethod(expenses.getPaymentMethod());
        dto.setDescription(expenses.getDescription());
        return dto;
    }

    @Override
    public void removeExpenses(Long userId, Long expensesTrackerId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Optional<ExpensesTracker> expDetailsOpt = expensesTrackerRepo.findByIdAndUserId(expensesTrackerId, userId);
        if (expDetailsOpt.isPresent()) {
        expensesTrackerRepo.delete(expDetailsOpt.get());
        } else {
            log.info("Not found: {}", expDetailsOpt);
        }
    }

    @Override
    public ExpensesTracker updateExpenses(Long userId, ExpensesTracker expensesTracker, Long expensesTrackerId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Optional<ExpensesTracker> expensesDetailsOpt = expensesTrackerRepo.findByIdAndUserId(expensesTrackerId, userId);
        if (expensesDetailsOpt.isPresent()) {
            ExpensesTracker existingExpensesTracker = expensesDetailsOpt.get();
            existingExpensesTracker.setDescription(expensesTracker.getDescription());
            existingExpensesTracker.setAmount(expensesTracker.getAmount());
            existingExpensesTracker.setDate(expensesTracker.getDate());
            existingExpensesTracker.setCategory(expensesTracker.getCategory());
            existingExpensesTracker.setRemarks(expensesTracker.getRemarks());
            existingExpensesTracker.setPaymentMethod(expensesTracker.getPaymentMethod());

            return expensesTrackerRepo.save(existingExpensesTracker);
        } else {
            log.warn("No expenses found with expensesTrackerId {} for userId {}", expensesTracker.getId(), userId);
            throw new NoSuchElementException("Expense not found with id " + expensesTracker.getId() + " for user " + userId);
        }
    }

    @Override
    public Page<ExpensesTrackerDto> filterAllExpensesByCategory(int pageNo, int pageSize, String category) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ExpensesTracker> categorizedExpensesPage = expensesTrackerRepo.findByCategory(category, pageable);
        return categorizedExpensesPage.map(this::convertToExpensesTrackerDto);
    }

    @Override
    public Page<ExpensesTracker> filterUserExpensesByCategory(int pageNo, int pageSize, String category, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return expensesTrackerRepo.findByUserIdAndCategory(userId, category, pageable);
    }

}
