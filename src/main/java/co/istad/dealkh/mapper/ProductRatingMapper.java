package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import co.istad.dealkh.features.productrating.dto.ProductRatingUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductRatingMapper {

    //    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "isRated", source = "rated")
    ProductRatingResponse mapProductRatingToProductRatingResponse(ProductRating productRating);

    ProductRating mapProductRatingRequestToProductRating(ProductRatingRequest productRatingRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapProductRatingUpdateRequest(@MappingTarget ProductRating productRating, ProductRatingUpdateRequest productRatingUpdateRequest);
}
