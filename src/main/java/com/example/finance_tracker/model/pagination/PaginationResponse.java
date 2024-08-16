package com.example.finance_tracker.model.pagination;

import lombok.Data;
import java.util.List;

@Data
public class PaginationResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalPages;
    private long totalElements;

    public PaginationResponse(List<T> content, int pageNumber, int pageSize, long totalElements, long totalPages) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
