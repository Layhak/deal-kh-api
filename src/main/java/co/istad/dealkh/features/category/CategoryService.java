package co.istad.dealkh.features.category;

import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;

import java.util.List;
import java.util.Optional;

/**
 * CategoryService is an interface that defines the contract for category-related operations.
 * It includes methods for creating, retrieving, updating, and deleting categories.
 */
public interface CategoryService {
    /**
     * Creates a new category based on the provided request.
     *
     * @param categoryCreateRequest the request containing the details for the new category
     * @return a {@link CategoryResponse} containing the details of the created category
     */
    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest);

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return a {@link CategoryResponse} containing the details of the retrieved category
     */

    /**
     * Retrieves a category by its name.
     *
     * @param slug the name of the category to retrieve
     * @return an {@link Optional} containing the {@link CategoryResponse} if found, or empty if not found
     */
    Optional<CategoryResponse> getCategoryBySlug(String slug);

    /**
     * Retrieves all categories.
     *
     * @return a list of {@link CategoryResponse} containing the details of all categories
     */
    List<CategoryResponse> getAllCategory();

    /**
     * Updates a category identified by its name based on the provided request.
     *
     * @param slug                  the name of the category to update
     * @param categoryUpdateRequest the request containing the updated details for the category
     * @return a {@link CategoryResponse} containing the details of the updated category
     */
    CategoryResponse updateCategoryBySlug(String username, String slug, CategoryUpdateRequest categoryUpdateRequest);

    /**
     * Deletes a category identified by its name.
     *
     * @param slug the name of the category to delete
     */
    void deleteCategoryBySlug(String username, String slug);
}
