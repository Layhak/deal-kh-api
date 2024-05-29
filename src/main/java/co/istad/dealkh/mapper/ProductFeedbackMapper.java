package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ProductFeedback;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductFeedbackMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", source = "user.id")
    ProductFeedbackResponse toProductFeedbackResponse(ProductFeedback productFeedback);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "user.id", source = "userId")
    ProductFeedback toProductFeedback(ProductFeedbackRequest productFeedbackRequest);
}
