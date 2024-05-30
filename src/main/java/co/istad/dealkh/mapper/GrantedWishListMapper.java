package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.GrantedWishList;
import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListRequest;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListResponse;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper(componentModel = "spring")
public interface GrantedWishListMapper {

//    @Mapping(target = "wishList.isGranted", expression = "java(true)")
    @Mapping(target = "wishList.userId", source = "wishList.user.id")
    @Mapping(target = "wishList.productId", source = "wishList.product.id")
    @Mapping(target = "discountPercentage", source = "discount", qualifiedByName = "discountToDouble")
    GrantedWishListResponse mapToGrantedWishListResponse(GrantedWishList grantedWishList);


    GrantedWishList mapRequestToGrantedWishList(GrantedWishListRequest grantedWishListRequest);

    @Named("discountToDouble")
    default Double mapDiscount(Discount discount){
        return discount.getDiscountPercentage();
    }

    @Named("mapToUser")
    default Long matUser(WishListResponse wishListResponse){
        return wishListResponse.userId();
    }

    @Named("mapToProduct")
    default Long matProduct(WishListResponse wishListResponse){
        return wishListResponse.productId();
    }

}
