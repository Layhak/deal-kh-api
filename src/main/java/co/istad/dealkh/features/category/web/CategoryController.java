package co.istad.dealkh.features.category.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.category.CategoryService;
import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    BaseResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        return BaseResponse.<CategoryResponse>createSuccess("Successfully created category!")
                .setPayload(categoryService.createCategory(categoryCreateRequest));
    }

    //    @GetMapping("/{id}")
    BaseResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return BaseResponse.<CategoryResponse>ok("Successfully retrieved category!")
                .setPayload(categoryService.getCategoryById(id));
    }

    @GetMapping("/{name}")
    BaseResponse<Optional<CategoryResponse>> getCategoryByName(@PathVariable String name) {
        return BaseResponse.<Optional<CategoryResponse>>ok("Successfully retrieved category!")
                .setPayload(categoryService.getCategoryByName(name));
    }

    @GetMapping("/")
    BaseResponse<List<CategoryResponse>> getAllCategory() {
        return BaseResponse.<List<CategoryResponse>>ok("Successfully retrieved category!")
                .setPayload(categoryService.getAllCategory());
    }

    @PutMapping("/{name}")
    BaseResponse<CategoryResponse> updateCategory(@PathVariable String name, @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return BaseResponse.<CategoryResponse>updateSuccess()
                .setPayload(categoryService.updateCategoryByName(name, categoryUpdateRequest));
    }

    @DeleteMapping("/{name}")
    BaseResponse<?> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategoryByName(name);
        return BaseResponse.deleteSuccess("Delete category successfully!");
    }

}
