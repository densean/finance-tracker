package com.example.finance_tracker.model.pagination;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class PaginationWithCategoryDto {
    private int pageNo;
    private int pageSize;
    @NotBlank
    private String category;
}
