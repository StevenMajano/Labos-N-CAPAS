package com.server.app.services.finance;

import com.server.app.dto.finance.category.CategoryCreateDto;
import com.server.app.dto.finance.category.CategoryResponseDto;
import com.server.app.dto.finance.category.CategoryUpdateDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto create(CategoryCreateDto dto);

    List<CategoryResponseDto> findAll();

    CategoryResponseDto findById(Integer id);

    CategoryResponseDto update(Integer id, CategoryUpdateDto dto);

    void delete(Integer id);
}