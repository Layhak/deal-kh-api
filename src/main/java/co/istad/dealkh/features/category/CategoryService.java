package co.istad.dealkh.features.category;

import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse getCategoryById(Long id);

    Optional<CategoryResponse> getCategoryByName(String name);

    List<CategoryResponse> getAllCategory();

    CategoryResponse updateCategoryByName(String name, CategoryUpdateRequest categoryUpdateRequest);

    void deleteCategoryByName(String name);
}
