package co.istad.dealkh.features.product.dto;

import co.istad.dealkh.features.image.dto.ImageResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        double price,
        String description,
        List<ImageResponse> images,

        @NotNull(message = "Create shop first before create product")
        Long shopId,

        Long discountId,

        @NotNull(message = "Product category is required")
        Long categoryId

) {
}
