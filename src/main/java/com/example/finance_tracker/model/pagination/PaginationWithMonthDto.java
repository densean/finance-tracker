package com.example.finance_tracker.model.pagination;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaginationWithMonthDto {
    private int pageSize;
    private int pageNo;
    private int month;
    private int year;
}
