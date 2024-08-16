package com.example.finance_tracker.model.expensesTracker;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@Validated
public class ExpensesTrackerDto {
    private int id;
    private String description;
    private String category;
    private Date date;
    private double amount;
    private String paymentMethod;
    private String remarks;
    private String username;
}
