package co.istad.dealkh.features.productfeedback.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.productfeedback.ProductFeedbackService;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-feedbacks")
@RequiredArgsConstructor
public class ProductFeedbackController {
    private final ProductFeedbackService productFeedbackService;

    @GetMapping("/{productId}")
    @Operation(summary = "Get product feedbacks by product id")
    public BaseResponse<List<ProductFeedbackResponse>> getProductFeedbacks(@PathVariable Long productId) {
        return BaseResponse.<List<ProductFeedbackResponse>>ok("Successfully retrieved product feedbacks!")
                .setPayload(productFeedbackService.getProductFeedbacks(productId));
    }

    @PostMapping
    @Operation(summary = "Create product feedback")
    public BaseResponse<ProductFeedbackResponse> createProductFeedback(@RequestBody @Valid ProductFeedbackRequest productFeedbackRequest) {
        return BaseResponse.<ProductFeedbackResponse>createSuccess("Successfully created product feedback!")
                .setPayload(productFeedbackService.createProductFeedback(productFeedbackRequest));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update product feedback")
    public BaseResponse<ProductFeedbackResponse> updateProductFeedback(@PathVariable Long id, @RequestBody ProductFeedbackRequest productFeedbackRequest) {
        return BaseResponse.<ProductFeedbackResponse>ok("Successfully updated product feedback!")
                .setPayload(productFeedbackService.updateProductFeedback(id, productFeedbackRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product feedback")
    public BaseResponse<?> deleteProductFeedback(@PathVariable Long id) {
        productFeedbackService.deleteProductFeedback(id);
        return BaseResponse.ok("Successfully deleted product feedback!");
    }
}
