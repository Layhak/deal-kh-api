package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.ProductFeedback;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackUpdate;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = {CustomMapper.class})
public interface ProductFeedbackMapper {
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "profile", source = "user.profile")
    ProductFeedbackResponse toProductFeedbackResponse(ProductFeedback productFeedback);

    @Mapping(target = "product.name", source = "productSlug")
    ProductFeedback toProductFeedback(ProductFeedbackRequest productFeedbackRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapProductFeedbackUpdateRequest(@MappingTarget ProductFeedback productFeedback, ProductFeedbackUpdate productFeedbackUpdate);
}
