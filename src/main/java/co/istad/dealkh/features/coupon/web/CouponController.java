package co.istad.dealkh.features.coupon.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.coupon.CouponService;
import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;
import co.istad.dealkh.features.coupon.dto.CouponUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return BaseResponse.<List<CouponResponse>>createSuccess("Get all coupons successfully!")
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
    public BaseResponse<CouponResponse> updateCouponByCode(@PathVariable String code, @RequestBody @Valid CouponUpdateRequest couponUpdateRequest) {
        return BaseResponse.<CouponResponse>ok("Update coupon by code successfully!")
                .setPayload(couponService.updateCouponByCode(code, couponUpdateRequest));
    }

    // delete by code
    @DeleteMapping("/{code}")
    public BaseResponse<?> deleteCouponByCode(@PathVariable String code) {
        couponService.deleteCouponByCode(code);
        return BaseResponse.ok("Delete coupon by code successfully!")
                .setPayload("No content");
    }

    // claim coupon
    @PostMapping("/{code}/claim")
    public BaseResponse<?> claimCoupon(@PathVariable String code, @RequestParam String username) {
        couponService.claimCoupon(code, username);
        return BaseResponse.ok("Claim coupon successfully!");
    }


}
