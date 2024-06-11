package co.istad.dealkh.features.category;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;
import co.istad.dealkh.mapper.CategoryMapper;
import co.istad.dealkh.validator.category.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * CategoryServiceImpl is a service implementation of {@link CategoryService} that handles category-related operations.
 * It includes methods for creating, retrieving, updating, and deleting categories.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Creates a new category based on the provided request.
     *
     * @param categoryCreateRequest the request containing the details for the new category
     * @return a {@link CategoryResponse} containing the details of the created category
     * @throws ResponseStatusException if the category name already exists
     */
    @Override
    public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
        if (categoryRepository.existsByName(categoryCreateRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists!");
        }

        String slug = SlugFormatter.formatSlug(categoryCreateRequest.name());

        if (categoryRepository.existsBySlug(slug)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slug already exists!");
        }

        Category newCategory = categoryMapper.mapCategoryRequestToCategory(categoryCreateRequest);

        newCategory.setSlug(slug);
        newCategory.setIcon("icon.png");

        return categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(newCategory));
    }

    private String formattedName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        return name.toLowerCase().replaceAll("\\s*-\\s*", "-").replaceAll("\\s+", "-").trim();
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return a {@link CategoryResponse} containing the details of the retrieved category
     * @throws ResponseStatusException if the category is not found
     */
    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name the name of the category to retrieve
     * @return an {@link Optional} containing the {@link CategoryResponse} if found, or empty if not found
     * @throws ResponseStatusException if the category is not found
     */
    @Override
    public Optional<CategoryResponse> getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        CategoryResponse categoryResponse = categoryMapper.mapCategoryToCategoryResponse(category);
        return Optional.of(categoryResponse);
    }

    /**
     * Retrieves all categories.
     *
     * @return a list of {@link CategoryResponse} containing the details of all categories
     */
    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToCategoryResponse)
                .toList();
    }

    /**
     * Updates a category identified by its name based on the provided request.
     *
     * @param name                  the name of the category to update
     * @param categoryUpdateRequest the request containing the updated details for the category
     * @return a {@link CategoryResponse} containing the details of the updated category
     * @throws ResponseStatusException if the category is not found
     */
    @Override
    public CategoryResponse updateCategoryByName(String name, CategoryUpdateRequest categoryUpdateRequest) {

        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        category.setUpdatedAt(LocalDateTime.now());

        categoryMapper.mapCategoryUpdateRequest(category, categoryUpdateRequest);

        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    /**
     * Deletes a category identified by its name.
     *
     * @param name the name of the category to delete
     * @throws ResponseStatusException if the category is not found
     */
    @Override
    public void deleteCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));
        categoryRepository.delete(category);
    }
}
