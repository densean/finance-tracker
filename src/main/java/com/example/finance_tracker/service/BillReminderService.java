package com.example.finance_tracker.service;

import com.example.finance_tracker.model.billReminder.BillReminder;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface BillReminderService {
    Collection<BillReminder> saveBill (Long userId, BillReminder billReminder);
    Optional<BillReminder> getBillById (Long userId, Long billReminderId);
    Page<BillReminder> getBills (int pageNo, int pageSize, Long userId);
    Page<BillReminder> getBillsByDueDate (int pageNo, int pageSize, Long userId, LocalDate dueDate);
    Page<BillReminder> getBillsByMonth (int year, int month, int pageNo, int pageSize, Long userId);
    Page<BillReminder> getAllBills (int pageNo, int pageSize);
    void removeBill (Long userId, Long billReminderId);
    BillReminder updateBill(Long userId, Long billReminderId, BillReminder billReminder);
}
