package com.example.finance_tracker.tools;

import com.example.finance_tracker.model.pagination.PaginationResponse;
import org.springframework.data.domain.Page;

public class PaginationResponseUtil {

    public static <T> PaginationResponse<T> formatPaginationResponse(Page<T> page) {
        return new PaginationResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getPageable().getOffset()
        );
    }
}
