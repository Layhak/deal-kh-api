package co.istad.dealkh.features.coupon;

import co.istad.dealkh.domain.Coupon;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.coupon.dto.CouponCreateRequest;
import co.istad.dealkh.features.coupon.dto.CouponResponse;
import co.istad.dealkh.features.coupon.dto.CouponUpdateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.CouponMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.validator.page.ValidatePagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    @Override
    public CouponResponse createCoupon(CouponCreateRequest couponCreateRequest) {

        Shop shop = shopRepository.findBySlug(couponCreateRequest.shopSlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shop with slug %s not found! ", couponCreateRequest.shopSlug())));

        Coupon newCoupon = couponMapper.mapRequestCoupon(couponCreateRequest);
        LocalDate expiredAt = LocalDate.parse(couponCreateRequest.expiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        newCoupon.setExpiredAt(expiredAt);

        String couponCode;
        Random random = new Random();
        StringBuilder sb;

// Generate a unique coupon code
        do {
            sb = new StringBuilder(10);
            for (int i = 0; i < 10; i++) {
                sb.append((char) (random.nextInt(26) + 'A'));
            }
            couponCode = sb.toString();
        } while (couponRepository.existsByCode(couponCode));
        newCoupon.setCode(couponCode);
        newCoupon.setIsExpired(false);
        newCoupon.setShop(shop);
        newCoupon.setCreatedAt(LocalDateTime.now());
        if (newCoupon.getExpiredAt().isBefore(LocalDate.now())) {
            newCoupon.setIsExpired(true);
        }
        return couponMapper.mapToCouponResponse(couponRepository.save(newCoupon));
    }

    @Override
    public PageResponse<CouponResponse> getAllCoupons(int page, int size, String field, String order) {

        if (page < 0) {
            page = 1;
        }

        if (size < 0){
            size = 25;
        }

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<CouponResponse> couponResponses = couponRepository.findAll(pageable).map(couponMapper::mapToCouponResponse);

        return new PageResponse<>(couponResponses);
    }

    @Override
    public Optional<CouponResponse> getCouponByCode(String code) {

        CouponResponse couponResponse = couponRepository.findByCode(code)
                .map(couponMapper::mapToCouponResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Coupon with code %s not found! ", code)));

        return Optional.of(couponResponse);
    }

    @Override
    public CouponResponse updateCouponByCode(String username, String code, CouponUpdateRequest couponUpdateRequest) {

        if (couponRepository.findByCreatedByAndCode(username, code).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You're not this resource owner!");
        }

        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Coupon with code %s not found! ", code)));

        if (couponUpdateRequest.expiredAt() == null) {
            coupon.setExpiredAt(coupon.getExpiredAt());
            coupon.setIsExpired(false);
        } else {
            coupon.setExpiredAt(couponUpdateRequest.expiredAt());
            coupon.setIsExpired(!couponUpdateRequest.expiredAt().isAfter(LocalDate.now()));
        }
        coupon.setUpdatedAt(LocalDateTime.now());
        coupon.setUpdatedBy(username);

        couponMapper.mapCouponUpdateRequest(coupon, couponUpdateRequest);
        coupon = couponRepository.save(coupon);
        return couponMapper.mapToCouponResponse(coupon);

    }

    @Override
    public void deleteCouponByCode(String username, String code) {

        if (couponRepository.findByCreatedByAndCode(username, code).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You're not this resource owner!");
        }

        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Coupon with code %s not found! ", code)));

        couponRepository.delete(coupon);
    }

    @Override
    public CouponResponse claimCoupon(String code, String username) {

        // Fetch the user by username
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with username %s not found!", username)));

        // Fetch the coupon by code
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Coupon with code %s not found!", code)));

        // Check if the user already claimed this coupon
        if (user.getCoupons().contains(coupon)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("User %s has already claimed coupon %s!", username, code));
        }

        // Add the coupon to the user's list of claimed coupons
        user.getCoupons().add(coupon);
        userRepository.save(user);

        return couponMapper.mapToCouponResponse(coupon);
    }

    @Override
    public List<CouponResponse> getCouponsByUser(String username) {
        return couponRepository.findAllByUsersUsername(username)
                .stream()
                .map(couponMapper::mapToCouponResponse)
                .toList();
    }

}
