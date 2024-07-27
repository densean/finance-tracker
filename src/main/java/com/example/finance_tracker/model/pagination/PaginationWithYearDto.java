package com.example.finance_tracker.model.pagination;

import lombok.Data;

@Data
public class PaginationWithYearDto {
    private int pageSize;
    private int pageNo;
    private int year;
}
