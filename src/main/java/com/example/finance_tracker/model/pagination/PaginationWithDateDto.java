package com.example.finance_tracker.model.pagination;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaginationWithDateDto {
    private int pageSize;
    private int pageNo;
    private LocalDate date;
}
