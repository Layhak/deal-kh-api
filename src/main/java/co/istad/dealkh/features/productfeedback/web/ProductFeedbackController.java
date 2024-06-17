package co.istad.dealkh.features.productfeedback.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.productfeedback.ProductFeedbackService;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackUpdate;
import co.istad.dealkh.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-feedbacks")
@RequiredArgsConstructor
public class ProductFeedbackController {
    private final ProductFeedbackService productFeedbackService;

    @GetMapping("/{productSlug}")
    @Operation(summary = "Get product feedbacks by product id")
    public BaseResponse<List<ProductFeedbackResponse>> getProductFeedbacks(@PathVariable String productSlug) {
        return BaseResponse.<List<ProductFeedbackResponse>>ok("Successfully retrieved product feedbacks!")
                .setPayload(productFeedbackService.getProductFeedbacks(productSlug));
    }

    @PostMapping
    @Operation(summary = "Create product feedback")
    public BaseResponse<ProductFeedbackResponse> createProductFeedback(@AuthenticationPrincipal CustomUserDetails customUserDetails,  @RequestBody @Valid ProductFeedbackRequest productFeedbackRequest) {
        return BaseResponse.<ProductFeedbackResponse>createSuccess("Successfully created product feedback!")
                .setPayload(productFeedbackService.createProductFeedback(customUserDetails.getUsername(), productFeedbackRequest));
    }


    @PatchMapping("/{uuid}")
    @Operation(summary = "Update product feedback")
    public BaseResponse<ProductFeedbackResponse> updateProductFeedback(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String uuid, @RequestBody ProductFeedbackUpdate productFeedbackUpdate) {
        return BaseResponse.<ProductFeedbackResponse>ok("Successfully updated product feedback!")
                .setPayload(productFeedbackService.updateProductFeedback(customUserDetails.getUsername(), uuid, productFeedbackUpdate));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete product feedback")
    public BaseResponse<?> deleteProductFeedback(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String uuid) {
        productFeedbackService.deleteProductFeedback(customUserDetails.getUsername(), uuid);
        return BaseResponse.ok("Successfully deleted product feedback!");
    }
}
