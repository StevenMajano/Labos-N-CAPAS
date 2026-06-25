package com.server.app.dto.finance.category;

import com.server.app.enums.finance.CategoryType;
import lombok.Data;

@Data
public class CategoryUpdateDto {

    private String name;

    private CategoryType type;

    private Integer parentCategoryId;
}