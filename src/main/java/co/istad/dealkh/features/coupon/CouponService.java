package co.istad.dealkh.features.coupon;

import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    CouponResponse createCoupon(CouponCreateRequest couponCreateRequest);

    List<CouponResponse> getAllCoupons();

    Optional<CouponResponse> getCouponByCode(String code);

    CouponResponse updateCouponByCode(String code, CouponCreateRequest couponCreateRequest);

    void deleteCouponByCode(String code);
}
