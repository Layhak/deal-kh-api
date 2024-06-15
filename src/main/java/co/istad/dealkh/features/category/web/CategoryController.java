package co.istad.dealkh.features.category.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.category.CategoryService;
import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;
import co.istad.dealkh.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CategoryController is a controller for managing categories.
 * It handles creating, retrieving, updating, and deleting categories.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link RestController} - Indicates that this class is a REST controller.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link RequestMapping} - Maps HTTP requests to handler methods of MVC and REST controllers.</li>
 * </ul>
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Creates a new category based on the provided request.
     *
     * @param categoryCreateRequest the request containing the details for the new category
     * @return a {@link CategoryResponse} containing the details of the created category
     */
    @PostMapping("")
    BaseResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        return BaseResponse.<CategoryResponse>createSuccess("Successfully created category!")
                .setPayload(categoryService.createCategory(categoryCreateRequest));
    }

    BaseResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return BaseResponse.<CategoryResponse>ok("Successfully retrieved category!")
                .setPayload(categoryService.getCategoryById(id));
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name
     * @return
     */
    @GetMapping("/{name}")
    BaseResponse<Optional<CategoryResponse>> getCategoryByName(@PathVariable String name) {
        return BaseResponse.<Optional<CategoryResponse>>ok("Successfully retrieved category!")
                .setPayload(categoryService.getCategoryByName(name));
    }

    /**
     * Retrieves all categories.
     *
     * @return
     */
    @GetMapping("")
    BaseResponse<List<CategoryResponse>> getAllCategory() {
        return BaseResponse.<List<CategoryResponse>>ok("Successfully retrieved category!")
                .setPayload(categoryService.getAllCategory());
    }

    /**
     * Updates a category identified by its name based on the provided request.
     *
     * @param slug                  the name of the category to update
     * @param categoryUpdateRequest the request containing the updated details for the category
     * @return a {@link CategoryResponse} containing the details of the updated category
     */
    @PutMapping("/{slug}")
    BaseResponse<CategoryResponse> updateCategory(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String slug,
            @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return BaseResponse.<CategoryResponse>ok("Update category successfully!")
                .setPayload(categoryService.updateCategoryBySlug(customUserDetails.getUsername(), slug, categoryUpdateRequest));
    }

    /**
     * Deletes a category identified by its slug.
     *
     * @param slug
     */
    @DeleteMapping("/{slug}")
    BaseResponse<?> deleteCategory(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String slug) {
        categoryService.deleteCategoryBySlug(customUserDetails.getUsername(), slug);
        return BaseResponse.ok("Delete category successfully!")
                .setPayload("No content");
    }

}
