package co.istad.dealkh.features.coupon;

import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;
import co.istad.dealkh.features.coupon.dto.CouponUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    CouponResponse createCoupon(CouponCreateRequest couponCreateRequest);

    List<CouponResponse> getAllCoupons();

    Optional<CouponResponse> getCouponByCode(String code);

    CouponResponse updateCouponByCode(String username, String code, CouponUpdateRequest couponUpdateRequest);

    void deleteCouponByCode(String username, String code);

    CouponResponse claimCoupon(String code, String username);

    List<CouponResponse> getCouponsByUser(String username);


}
