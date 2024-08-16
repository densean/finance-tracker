package com.example.finance_tracker.controller;

import com.example.finance_tracker.constants.ResponseConstants;
import com.example.finance_tracker.handlers.ResponseHandler;
import com.example.finance_tracker.model.billReminder.BillReminder;
import com.example.finance_tracker.model.pagination.PaginationDto;
import com.example.finance_tracker.model.pagination.PaginationResponse;
import com.example.finance_tracker.model.pagination.PaginationWithDateDto;
import com.example.finance_tracker.model.pagination.PaginationWithMonthDto;
import com.example.finance_tracker.service.BillReminderService;
import com.example.finance_tracker.tools.PaginationResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@Validated
public class BillsReminderController {
    private final BillReminderService billReminderService;

    @PostMapping("/users/{userId}/bills")
    public ResponseEntity<Object> addBillReminder(@PathVariable Long userId, @RequestBody BillReminder billReminder) {
        Collection<BillReminder> savedBill = billReminderService.saveBill(userId, billReminder);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_POST,
                HttpStatus.OK,
                savedBill,
                null,
                "addBillReminderAsync"
        );
    }

    @GetMapping("/users/{userId}/bills/{billReminderId}")
    public ResponseEntity<Object> getBillReminderById(@PathVariable Long userId, @PathVariable Long billReminderId) {
        Optional<BillReminder> billReminder = billReminderService.getBillById(userId, billReminderId);
        return billReminder.map(bill -> ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                bill,
                null,
                "getBillReminderByIdAsync"
        )).orElseGet(() -> ResponseHandler.generateResponse(
                "Not found",
                HttpStatus.NOT_FOUND,
                null,
                "Bill not found",
                "getBillReminderByIdAsync"
        ));
    }

    @GetMapping("/users/{userId}/bills")
    public ResponseEntity<Object> getBills(@PathVariable Long userId, @RequestBody PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Page<BillReminder> bills = billReminderService.getBills(pageNo, pageSize, userId);
        PaginationResponse<BillReminder> paginationResponse = PaginationResponseUtil.formatPaginationResponse(bills);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                paginationResponse,
                null,
                "getBillsAsync"
        );
    }

    @GetMapping("/users/{userId}/bills/date")
    public ResponseEntity<Object> getBillsByDueDate(@PathVariable Long userId, @RequestBody PaginationWithDateDto paginationWithDateDto) {
        int pageNo = paginationWithDateDto.getPageNo();
        int pageSize = paginationWithDateDto.getPageSize();
        LocalDate date = paginationWithDateDto.getDate();
        Page<BillReminder> bills = billReminderService.getBillsByDueDate(pageNo, pageSize, userId, date);
        PaginationResponse<BillReminder> paginationResponse = PaginationResponseUtil.formatPaginationResponse(bills);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                paginationResponse,
                null,
                "getBillsByDueDateAsync"
        );
    }

    @GetMapping("/users/{userId}/bills/month")
    public ResponseEntity<Object> getBillsByMonth(@PathVariable Long userId, @RequestBody PaginationWithMonthDto paginationWithMonthDto) {
        int pageNo = paginationWithMonthDto.getPageNo();
        int pageSize = paginationWithMonthDto.getPageSize();
        int year = paginationWithMonthDto.getYear();
        int month = paginationWithMonthDto.getMonth();
        Page<BillReminder> bills = billReminderService.getBillsByMonth(year, month, pageNo, pageSize, userId);
        PaginationResponse<BillReminder> paginationResponse = PaginationResponseUtil.formatPaginationResponse(bills);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                paginationResponse,
                null,
                "getBillsByMonthAsync"
        );
    }

    @DeleteMapping("/users/{userId}/bills/{billReminderId}")
    public ResponseEntity<Object> removeBill(@PathVariable Long userId, @PathVariable Long billReminderId) {
        billReminderService.removeBill(userId, billReminderId);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_DELETE,
                HttpStatus.OK,
                null,
                null,
                "removeBillAsync"
        );
    }

    @PutMapping("/user/{userId}/bills/{billReminderId}")
    public ResponseEntity<Object> updateBill(@PathVariable Long userId, @PathVariable Long billReminderId, @RequestBody BillReminder billReminder) {
        BillReminder updatedBill = billReminderService.updateBill(userId, billReminderId, billReminder);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_PUT,
                HttpStatus.OK,
                updatedBill,
                null,
                "updateBillAsync"
        );
    }

    @GetMapping("/users/{userId}/bills/installments/{installmentId}")
    public ResponseEntity<Object> getInstallmentBills(@PathVariable Long userId, @PathVariable Long installmentId) {
        Collection<BillReminder> bills = billReminderService.getInstallmentBills(installmentId);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                bills,
                null,
                "getInstallmentBillsAsync"
        );
    }

    @GetMapping("/super-admin/users/bills")
    public ResponseEntity<Object> getAllBills(@RequestBody PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Page<BillReminder> bills = billReminderService.getAllBills(pageNo, pageSize);
        PaginationResponse<BillReminder> billsDto = PaginationResponseUtil.formatPaginationResponse(bills);

        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                billsDto,
                null,
                "getAllBillsAsync"
        );
    }
}
