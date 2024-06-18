package co.istad.dealkh.features.coupon.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.coupon.CouponService;
import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;
import co.istad.dealkh.features.coupon.dto.CouponUpdateRequest;
import co.istad.dealkh.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public BaseResponse<CouponResponse> createCoupon(@RequestBody CouponCreateRequest couponCreateRequest) {
        return BaseResponse.<CouponResponse>createSuccess("Create new coupon successfully!")
                .setPayload(couponService.createCoupon(couponCreateRequest));
    }

    // get all coupons
    @GetMapping
    public BaseResponse<List<CouponResponse>> getAllCoupons() {
        return BaseResponse.<List<CouponResponse>>ok("Get all coupons successfully!")
                .setPayload(couponService.getAllCoupons());
    }

    // get coupon by code
    @GetMapping("/{code}")
    public BaseResponse<Optional<CouponResponse>> getCouponByCode(@PathVariable String code) {
        return BaseResponse.<Optional<CouponResponse>>createSuccess("Get coupon by code successfully!")
                .setPayload(couponService.getCouponByCode(code));
    }

    // update by code
    @PutMapping("/{code}")
    public BaseResponse<CouponResponse> updateCouponByCode(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String code, @RequestBody CouponUpdateRequest couponUpdateRequest) {
        return BaseResponse.<CouponResponse>createSuccess("Update coupon by code successfully!")
                .setPayload(couponService.updateCouponByCode(customUserDetails.getUsername(), code, couponUpdateRequest));
    }

    // delete by code
    @DeleteMapping("/{code}")
    public BaseResponse<?> deleteCouponByCode(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String code) {
        couponService.deleteCouponByCode(customUserDetails.getUsername(), code);
        return BaseResponse.ok("Delete coupon by code successfully!")
                .setPayload("No content");
    }

    // claim coupon
    @PostMapping("/claim/{code}")
    public BaseResponse<CouponResponse> claimCoupon(@PathVariable String code, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.<CouponResponse>createSuccess("Claim coupon successfully!")
                .setPayload(couponService.claimCoupon(code, customUserDetails.getUsername()));
    }

    @GetMapping("/users/claims")
    public BaseResponse<List<CouponResponse>> getAllCouponsByUserClaim(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.<List<CouponResponse>>createSuccess("Get all coupons that you have claimed!")
                .setPayload(couponService.getCouponsByUser(customUserDetails.getUsername()));
    }

}
