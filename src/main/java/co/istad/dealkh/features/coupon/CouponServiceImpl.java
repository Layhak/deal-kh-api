package co.istad.dealkh.features.coupon;

import co.istad.dealkh.domain.Coupon;
import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;
import co.istad.dealkh.features.coupon.web.CouponRepository;
import co.istad.dealkh.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponResponse createCoupon(CouponCreateRequest couponCreateRequest) {

        // Check if the coupon already exists
        if (couponRepository.existsByCode(couponCreateRequest.code())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon code already exists!");
        }

        Coupon newCoupon = couponMapper.mapRequestCoupon(couponCreateRequest);
        return couponMapper.mapToCouponResponse(couponRepository.save(newCoupon));
    }

    @Override
    public List<CouponResponse> getAllCoupons() {
        return couponRepository.findAll()
                .stream()
                .map(couponMapper::mapToCouponResponse)
                .toList();
    }

    @Override
    public Optional<CouponResponse> getCouponByCode(String code) {
        return Optional.empty();
    }

    @Override
    public CouponResponse updateCouponByCode(String code, CouponCreateRequest couponCreateRequest) {
        return null;
    }

    @Override
    public void deleteCouponByCode(String code) {

    }
}
