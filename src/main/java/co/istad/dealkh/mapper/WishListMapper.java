package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DiscountMapper.class})
public interface WishListMapper {


    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "discountType.id", target = "discountTypeId")
    WishListResponse mapToWishListResponse(WishList wishList);


    WishList mapRequestToWishList(WishListRequest wishListRequest);
}
