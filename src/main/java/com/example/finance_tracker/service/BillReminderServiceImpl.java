package com.example.finance_tracker.service;

import com.example.finance_tracker.model.User;
import com.example.finance_tracker.model.billReminder.BillReminder;
import com.example.finance_tracker.repository.BillReminderRepository;
import com.example.finance_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BillReminderServiceImpl implements BillReminderService {
    private final BillReminderRepository billReminderRepository;
    private final UserRepository userRepository;

    @Override
    public Collection<BillReminder> saveBill(Long userId, BillReminder billReminder) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        LocalDate dueDate = billReminder.getDueDate();
        Collection<BillReminder> collectionOfBills = new ArrayList<>();

        boolean isInstallment = billReminder.getTerm() > 1 || billReminder.getOccurrence() > 1;
        billReminder.setInstallment(isInstallment);

        if (isInstallment) {
            for (int i = 0; i < billReminder.getTerm(); i++) {
                BillReminder newBill = new BillReminder();
                newBill.setName(billReminder.getName());
                newBill.setAmount(billReminder.getAmount());
                newBill.setDueDate(dueDate.plusMonths(i * billReminder.getOccurrence()));
                newBill.setNotes(billReminder.getNotes());
                newBill.setOccurrence(billReminder.getOccurrence());
                newBill.setTerm(1);
                newBill.setPaid(false);
                newBill.setInstallment(true);
                newBill.setPaidCount(0);
                newBill.setUser(user);
                BillReminder savedBill = billReminderRepository.save(newBill);
                collectionOfBills.add(savedBill);
            }
        } else {
            billReminder.setUser(user);
            billReminder.setPaidCount(0); // Initialize paidCount
            BillReminder savedBill = billReminderRepository.save(billReminder);
            collectionOfBills.add(savedBill);
        }

        return collectionOfBills;
    }

    @Override
    public Optional<BillReminder> getBillById(Long userId, Long billReminderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Optional<BillReminder> billReminder = billReminderRepository.findByIdAndUserId(billReminderId, userId);
        if (billReminder.isPresent() && billReminder.get().isInstallment()) {
            // If the bill is an installment, fetch all related installments
            return Optional.ofNullable((BillReminder) billReminderRepository.findByNameAndUser(
                    billReminder.get().getName(),
                    billReminder.get().getUser()
            ));
        }
        return billReminder;
    }

    @Override
    public Page<BillReminder> getBills(int pageNo, int pageSize, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return billReminderRepository.findAll(pageable);
    }

    @Override
    public Page<BillReminder> getBillsByDueDate(int pageNo, int pageSize, Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return billReminderRepository.findByUserIdAndDueDate(userId, date, pageable);
    }

    @Override
    public Page<BillReminder> getBillsByMonth(int year, int month, int pageNo, int pageSize, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return billReminderRepository.findByUserIdAndYearAndMonth(userId, year, month, pageable);
    }

    @Override
    public Page<BillReminder> getAllBills(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return billReminderRepository.findAll(pageable);
    }

    @Override
    public void removeBill(Long userId, Long billReminderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        billReminderRepository.deleteById(billReminderId);
    }

    @Override
    public BillReminder updateBill(Long userId, Long billReminderId, BillReminder billReminder) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        BillReminder existingBill = billReminderRepository.findByIdAndUserId(billReminderId, userId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        existingBill.setName(billReminder.getName());
        existingBill.setAmount(billReminder.getAmount());
        existingBill.setDueDate(billReminder.getDueDate());
        existingBill.setNotes(billReminder.getNotes());
        existingBill.setOccurrence(billReminder.getOccurrence());
        existingBill.setTerm(billReminder.getTerm());
        existingBill.setPaid(billReminder.isPaid());
        return billReminderRepository.save(existingBill);
    }

    // New method to fetch installment bills
    @Override
    public Collection<BillReminder> getInstallmentBills(Long installmentId) {
        BillReminder bill = billReminderRepository.findById(installmentId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (!bill.isInstallment()) {
            throw new RuntimeException("Provided ID does not belong to an installment bill");
        }

        return billReminderRepository.findByNameAndUser(bill.getName(), bill.getUser());
    }
}


