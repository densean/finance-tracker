package com.example.finance_tracker.model.pagination;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaginationWithDateDto {
    private int pageSize = Integer.MAX_VALUE;
    private int pageNo = 0;
    private LocalDate date;
}
