package com.example.finance_tracker.controller;

import com.example.finance_tracker.constants.ResponseConstants;
import com.example.finance_tracker.handlers.ResponseHandler;
import com.example.finance_tracker.model.UserPublicDto;
import com.example.finance_tracker.model.expensesTracker.ExpensesTracker;
import com.example.finance_tracker.model.expensesTracker.ExpensesTrackerDto;
import com.example.finance_tracker.model.pagination.*;
import com.example.finance_tracker.service.ExpensesTrackerService;
import com.example.finance_tracker.tools.PaginationResponseUtil;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@Validated
public class ExpensesTrackerController {
    private final ExpensesTrackerService expTrackerService;

    @PostMapping("/users/{userId}/expenses")
    public ResponseEntity<Object> saveExpenses(@PathVariable Long userId, @RequestBody @NotNull ExpensesTracker expensesDetails) {
        ExpensesTracker savedExpenses = expTrackerService.saveExpenses(userId, expensesDetails);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_POST,
                HttpStatus.OK,
                savedExpenses,
                null,
                "saveExpensesAsync");
    }

    @GetMapping("/users/{userId}/expenses/{expensesTrackerId}")
    public ResponseEntity<Object> getExpensesById(@PathVariable Long userId, @PathVariable Long expensesTrackerId) {
        Optional<ExpensesTracker> expenses = expTrackerService.getExpensesById(userId, expensesTrackerId);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expenses.orElse(null),
                null,
                "getExpensesByIdAsync");
    }

    @GetMapping("/users/{userId}/expenses")
    public ResponseEntity<Object> getExpenses(@PathVariable Long userId, @RequestBody PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Page<ExpensesTracker> expenses = expTrackerService.getExpenses(pageNo, pageSize, userId);
        PaginationResponse<ExpensesTracker> expensesDto = PaginationResponseUtil.formatPaginationResponse(expenses);

        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expensesDto,
                null,
                "getExpensesAsync");
    }

    @GetMapping("/users/{userId}/expenses/date")
    public ResponseEntity<Object> getExpensesByDate(@PathVariable Long userId, @RequestBody PaginationWithDateDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        LocalDate date = paginationDto.getDate();

        Page<ExpensesTracker> expenses = expTrackerService.getExpensesByDate(pageNo, pageSize, userId, date);
        PaginationResponse<ExpensesTracker> expensesDto = PaginationResponseUtil.formatPaginationResponse(expenses);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expensesDto,
                null,
                "getExpensesByDateAsync");
    }

    @GetMapping("/users/{userId}/expenses/month")
    public ResponseEntity<Object> getExpensesByMonth(@PathVariable Long userId, @RequestBody PaginationWithMonthDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        int month = paginationDto.getMonth();
        int year = paginationDto.getYear();

        Page<ExpensesTracker> expenses = expTrackerService.getExpensesByMonth(year, month, pageNo, pageSize, userId);
        PaginationResponse<ExpensesTracker> expensesDto = PaginationResponseUtil.formatPaginationResponse(expenses);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expensesDto,
                null,
                "getExpensesByMonthAsync");
    }

    @GetMapping("/users/{userId}/expenses/year")
    public ResponseEntity<Object> getExpensesByYear(@PathVariable Long userId, @RequestBody PaginationWithYearDto paginationDto) {
        int year = paginationDto.getYear();
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Page<ExpensesTracker> expenses = expTrackerService.getExpensesByYear(year, pageNo, pageSize, userId);
        PaginationResponse<ExpensesTracker> expensesDto = PaginationResponseUtil.formatPaginationResponse(expenses);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expensesDto,
                null,
                "getExpensesByYearAsync");
    }

    @DeleteMapping("/users/{userId}/expenses/{expensesTrackerId}")
    public ResponseEntity<Object> removeExpenses(@PathVariable Long userId, @PathVariable Long expensesTrackerId) {
        expTrackerService.removeExpenses(userId, expensesTrackerId);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_DELETE,
                HttpStatus.OK,
                null,
                null,
                "removeExpensesAsync");
    }

    @PutMapping("/users/{userId}/expenses/{expensesTrackerId}")
    public ResponseEntity<Object> updateExpenses(@PathVariable Long userId, @PathVariable Long expensesTrackerId, @RequestBody ExpensesTracker expensesTracker) {
        ExpensesTracker updatedExpenses = expTrackerService.updateExpenses(userId, expensesTracker, expensesTrackerId);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_PUT,
                HttpStatus.OK,
                updatedExpenses,
                null,
                "updateExpensesAsync");
    }

    @GetMapping("/users/{userId}/expenses/categories")
    public ResponseEntity<Object> filterExpensesByCategory( @PathVariable Long userId,@Valid @RequestBody PaginationWithCategoryDto paginationWithCategoryDto) {
        int pageNo = paginationWithCategoryDto.getPageNo();
        int pageSize = paginationWithCategoryDto.getPageSize();
        String category = paginationWithCategoryDto.getCategory();
        Page<ExpensesTracker> expenses = expTrackerService.filterUserExpensesByCategory(pageNo, pageSize, category, userId);
        PaginationResponse<ExpensesTracker> expensesDto = PaginationResponseUtil.formatPaginationResponse(expenses);


        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expensesDto,
                null,
                "filterExpensesByCategoryAsync");

    }

    @GetMapping("/super-admin/users/expenses")
    public ResponseEntity<Object> getAllExpenses( @RequestBody PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Page<ExpensesTrackerDto> expenses = expTrackerService.getAllExpenses(pageNo, pageSize);
        PaginationResponse<ExpensesTrackerDto> expensesDto = PaginationResponseUtil.formatPaginationResponse(expenses);

        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expensesDto,
                null,
                "getAllExpensesAsync"
        );
    }

    @GetMapping("/super-admin/users/expenses/categories")
    public ResponseEntity<Object> filterAllByCategory(@Valid @RequestBody PaginationWithCategoryDto paginationWithCategoryDto) {
        int pageNo = paginationWithCategoryDto.getPageNo();
        int pageSize = paginationWithCategoryDto.getPageSize();
        String category = paginationWithCategoryDto.getCategory();
        Page<ExpensesTrackerDto> expenses = expTrackerService.filterAllExpensesByCategory(pageNo, pageSize, category);
        PaginationResponse<ExpensesTrackerDto> expensesDto = PaginationResponseUtil.formatPaginationResponse(expenses);

        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                expensesDto,
                null,
                "filterAllByCategoryAsync");

    }


}