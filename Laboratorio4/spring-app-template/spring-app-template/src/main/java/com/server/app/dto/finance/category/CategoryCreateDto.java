package com.server.app.dto.finance.category;

import com.server.app.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryCreateDto {

    @NotBlank
    private String name;

    @NotNull
    private CategoryType type;

    private Integer parentCategoryId;
}