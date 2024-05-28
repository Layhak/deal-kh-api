package co.istad.dealkh.features.product.dto;
import co.istad.dealkh.domain.json.Image;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;

public record ProductCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        double price,
        String description,
        List<Image> images,

        @NotNull(message = "Create shop first before create product")
        Long shopId,

        Long discountId,

        @NotNull(message = "Product category is required")
        Long categoryId

) {
}
