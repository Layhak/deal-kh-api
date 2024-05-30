package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.features.shoptype.dto.ShopTypeUpdateRequest;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.features.wishlist.dto.WishListUpdate;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WishListMapper {


    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    WishListResponse mapToWishListResponse(WishList wishList);

    WishList mapRequestToWishList(WishListRequest wishListRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapWishListUpdateRequest(@MappingTarget WishList wishList, WishListUpdate wishListUpdate);

}
