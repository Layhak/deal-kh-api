package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.domain.ShopRating;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;
import co.istad.dealkh.features.productrating.dto.ProductRatingUpdateRequest;
import co.istad.dealkh.features.shoprating.dto.ShopRatingRequest;
import co.istad.dealkh.features.shoprating.dto.ShopRatingResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ShopRatingMapper {

    @Mapping(target = "shop", source = "shop.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "isRated", source = "rated")
    ShopRatingResponse mapShopRatingToProductRatingResponse(ShopRating shopRating);

    ShopRating mapShopRatingRequestToShopRating(ShopRatingRequest shopRatingRequest);

}
