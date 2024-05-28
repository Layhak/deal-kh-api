package co.istad.dealkh.features.category;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;
import co.istad.dealkh.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {

        if(categoryRepository.existsByName(categoryCreateRequest.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }

        Category newCategory = categoryMapper.mapCategoryRequestToCategory(categoryCreateRequest);
        newCategory.setIcon("icon.png");
        return categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public Optional<CategoryResponse> getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        CategoryResponse categoryResponse = categoryMapper.mapCategoryToCategoryResponse(category);
        return Optional.of(categoryResponse);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategoryByName(String name, CategoryUpdateRequest categoryUpdateRequest) {

        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        category.setUpdatedAt(LocalDateTime.now());

        categoryMapper.mapCategoryUpdateRequest(category, categoryUpdateRequest);

        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }


    @Override
    public void deleteCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));
        categoryRepository.delete(category);
    }
}
