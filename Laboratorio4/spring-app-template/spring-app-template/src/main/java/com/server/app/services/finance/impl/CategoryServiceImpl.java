package com.server.app.services.finance.impl;

import com.server.app.dto.finance.category.CategoryCreateDto;
import com.server.app.dto.finance.category.CategoryResponseDto;
import com.server.app.dto.finance.category.CategoryUpdateDto;
import com.server.app.entities.finance.Category;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.finance.CategoryRepository;
import com.server.app.services.finance.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto create(CategoryCreateDto dto) {

        Category parent = null;

        if (dto.getParentCategoryId() != null) {
            parent = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() ->
                            new NotFoundException("La categoría padre no existe"));
        }

        Category category = Category.builder()
                .name(dto.getName())
                .type(dto.getType())
                .parentCategory(parent)
                .build();

        return toResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoryResponseDto findById(Integer id) {

        return toResponse(findCategory(id));
    }

    @Override
    public CategoryResponseDto update(Integer id, CategoryUpdateDto dto) {

        Category category = findCategory(id);

        if (dto.getName() != null)
            category.setName(dto.getName());

        if (dto.getType() != null)
            category.setType(dto.getType());

        if (dto.getParentCategoryId() != null) {

            Category parent = findCategory(dto.getParentCategoryId());

            category.setParentCategory(parent);
        }

        return toResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Integer id) {

        Category category = findCategory(id);

        categoryRepository.delete(category);
    }

    private Category findCategory(Integer id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("La categoría no existe"));
    }

    private CategoryResponseDto toResponse(Category category) {

        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .parentCategoryId(
                        category.getParentCategory() == null
                                ? null
                                : category.getParentCategory().getId())
                .build();
    }
}