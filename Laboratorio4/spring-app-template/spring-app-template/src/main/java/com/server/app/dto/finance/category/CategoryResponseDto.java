package com.server.app.dto.finance.category;

import com.server.app.enums.finance.CategoryType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {

    private Integer id;

    private String name;

    private CategoryType type;

    private Integer parentCategoryId;
}