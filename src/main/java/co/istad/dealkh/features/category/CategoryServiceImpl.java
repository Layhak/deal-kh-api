package co.istad.dealkh.features.category;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.CategoryMapper;
import co.istad.dealkh.validator.formatter.NameFormatter;
import co.istad.dealkh.validator.formatter.SlugFormatter;
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
    private final UserRepository userRepository;

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

        String name = NameFormatter.formatName(categoryCreateRequest.name());

        String slug = SlugFormatter.formatSlug(categoryCreateRequest.name());

        if (categoryRepository.existsBySlug(slug)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists!");
        }

        Category newCategory = categoryMapper.mapCategoryRequestToCategory(categoryCreateRequest);

        newCategory.setName(name);
        newCategory.setSlug(slug);
        newCategory.setIcon(categoryCreateRequest.icon());

        return categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(newCategory));
    }


    /**
     * Retrieves a category by its name.
     *
     * @param slug the name of the category to retrieve
     * @return an {@link Optional} containing the {@link CategoryResponse} if found, or empty if not found
     * @throws ResponseStatusException if the category is not found
     */
    @Override
    public Optional<CategoryResponse> getCategoryBySlug(String slug) {

        Category category = categoryRepository.findBySlug(slug)
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
     * @param slug                  the name of the category to update
     * @param categoryUpdateRequest the request containing the updated details for the category
     * @return a {@link CategoryResponse} containing the details of the updated category
     * @throws ResponseStatusException if the category is not found
     */
    @Override
    public CategoryResponse updateCategoryBySlug(String username, String slug, CategoryUpdateRequest categoryUpdateRequest) {

        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with slug %s not found! ", slug)));

        if (categoryRepository.findByCreatedByAndSlug(username, slug).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        category.setUpdatedAt(LocalDateTime.now());
        System.out.println("USERNAME:"+username);
        category.setUpdatedBy(username);

        categoryMapper.mapCategoryUpdateRequest(category, categoryUpdateRequest);

       categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    /**
     * Deletes a category identified by its name.
     *
     * @param slug the name of the category to delete
     * @throws ResponseStatusException if the category is not found
     */
    @Override
    public void deleteCategoryBySlug(String username, String slug) {

        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with slug %s not found! ", slug)));

        if (categoryRepository.findByCreatedByAndSlug(username, slug).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }
        categoryRepository.delete(category);
    }
}
