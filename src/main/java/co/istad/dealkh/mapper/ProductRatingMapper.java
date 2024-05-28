package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface ProductRatingMapper {

    ProductRatingResponse mapProductRatingToProductRatingResponse(ProductRating productRating);

    ProductRating mapProductRatingRequestToProductRating(ProductRatingRequest productRatingRequest);
}
