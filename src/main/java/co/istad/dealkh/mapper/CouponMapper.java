package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Coupon;
import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;
import co.istad.dealkh.features.coupon.dto.CouponUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    CouponResponse mapToCouponResponse(Coupon coupon);

    Coupon mapRequestCoupon(CouponCreateRequest couponCreateRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCouponUpdateRequest(@MappingTarget Coupon coupon, CouponUpdateRequest couponUpdateRequest);
}
