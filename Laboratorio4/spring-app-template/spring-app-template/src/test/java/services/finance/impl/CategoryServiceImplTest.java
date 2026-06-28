package services.finance.impl;

import com.server.app.dto.finance.category.CategoryCreateDto;
import com.server.app.dto.finance.category.CategoryResponseDto;
import com.server.app.entities.finance.Category;
import com.server.app.enums.CategoryType;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.finance.CategoryRepository;
import com.server.app.services.finance.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createShouldSaveCategoryWithoutParent() {
        CategoryCreateDto dto = new CategoryCreateDto();
        dto.setName("Salario");
        dto.setType(CategoryType.INCOME);

        Category savedCategory = Category.builder()
                .id(1)
                .name(dto.getName())
                .type(dto.getType())
                .parentCategory(null)
                .build();

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryResponseDto response = categoryService.create(dto);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Salario", response.getName());
        assertEquals(CategoryType.INCOME, response.getType());
        assertNull(response.getParentCategoryId());

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createShouldThrowNotFoundWhenParentCategoryDoesNotExist() {
        CategoryCreateDto dto = new CategoryCreateDto();
        dto.setName("Supermercado");
        dto.setType(CategoryType.EXPENSE);
        dto.setParentCategoryId(99);

        when(categoryRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.create(dto));

        verify(categoryRepository).findById(99);
        verify(categoryRepository, never()).save(any(Category.class));
    }
}