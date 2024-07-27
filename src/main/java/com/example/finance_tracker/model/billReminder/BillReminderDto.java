package com.example.finance_tracker.model.billReminder;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BillReminderDto {
    private String name;
    private int amount;
    private LocalDate dueDate;
    private String notes;
    private int occurrence;
    private int term;
    private boolean isPaid = false;
    private String username;
}
