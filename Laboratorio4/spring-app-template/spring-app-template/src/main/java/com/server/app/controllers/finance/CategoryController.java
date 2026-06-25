package com.server.app.controllers.finance;

import com.server.app.dto.finance.category.CategoryCreateDto;
import com.server.app.dto.finance.category.CategoryResponseDto;
import com.server.app.dto.finance.category.CategoryUpdateDto;
import com.server.app.services.finance.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(
            @Valid @RequestBody CategoryCreateDto dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryUpdateDto dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}