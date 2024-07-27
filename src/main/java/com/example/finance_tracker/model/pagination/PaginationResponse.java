package com.example.finance_tracker.model.pagination;

import lombok.Data;
import java.util.List;

@Data
public class PaginationResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long offset;

    public PaginationResponse(List<T> content, int pageNumber, int pageSize, long offset) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.offset = pageSize;
    }
}
