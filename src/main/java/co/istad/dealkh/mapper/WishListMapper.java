package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.domain.enumType.GrantStatus;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {DiscountMapper.class})
public interface WishListMapper {

    @Mapping(source = "wishList.uuid", target = "id")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "discountType.slug", target = "discountTypeSlug")
    @Mapping(source = "isGranted", target = "isGranted", qualifiedByName = "mapGrantStatusToString")
    WishListResponse mapToWishListResponse(WishList wishList);


    @Mapping(source = "productSlug", target = "product.slug")
    @Mapping(source = "discountTypeSlug", target = "discountType.slug")
    WishList mapRequestToWishList(WishListRequest wishListRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isGranted", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapWishListToUpdateRequest(@MappingTarget WishList wishList, WishListRequest wishListRequest);

    @Named("mapGrantStatusToString")
    static String mapGrantStatusToString(GrantStatus status) {
        return status == null ? null : status.name();
    }
}
