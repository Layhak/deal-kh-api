package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Coupon;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;
import co.istad.dealkh.features.coupon.dto.CouponUpdateRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    @Mapping(target = "shop", source = "shop", qualifiedByName = "shopToString")
    CouponResponse mapToCouponResponse(Coupon coupon);

    Coupon mapRequestCoupon(CouponCreateRequest couponCreateRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCouponUpdateRequest(@MappingTarget Coupon coupon, CouponUpdateRequest couponUpdateRequest);

    @Named("getShopName")
    default String getShopName(Shop shop) {
        return shop.getName();
    }

    @Named("shopToString")
    default String mapShop(Shop shop) {
        return shop.getName();
    }


}
