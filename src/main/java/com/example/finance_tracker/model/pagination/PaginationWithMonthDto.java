package com.example.finance_tracker.model.pagination;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaginationWithMonthDto {
    private int pageSize = 0;
    private int pageNo = Integer.MAX_VALUE;
    private int month;
    private int year;
}
