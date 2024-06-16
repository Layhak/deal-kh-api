package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.domain.DiscountType;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.domain.enumType.GrantStatus;
import co.istad.dealkh.features.discounttype.DiscountTypeRepository;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.mapper.WishListMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.specification.filter.PageFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final WishListMapper wishListMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DiscountTypeRepository discountTypeRepository;

    @Override
    public WishListResponse addWishList(String username, WishListRequest wishListRequest) {

        WishList newWishList = wishListMapper.mapRequestToWishList(wishListRequest);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with username %s not found! ", username)
                ));
        Product product = productRepository.findBySlug(wishListRequest.productSlug()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Product with slug %s not found! ", wishListRequest.productSlug())
        ));
        DiscountType discountType = discountTypeRepository.findBySlug(wishListRequest.discountTypeSlug()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("DiscountType with uuid %s not found! ", wishListRequest.discountTypeSlug())
        ));

        newWishList.setUser(user);
        newWishList.setProduct(product);
        newWishList.setDiscountType(discountType);
        wishListRepository.save(newWishList);

        return wishListMapper.mapToWishListResponse(newWishList);
    }

    @Override
    public PageResponse<WishListResponse> getAllWishList(Map<String, String> params) {

        int pageNumber = PageFilter.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageFilter.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageFilter.PAGE_NUMBER));
        }

        int pageLimit = PageFilter.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageFilter.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageFilter.PAGE_LIMIT));
        }

        Pageable pageable = PageFilter.getPageable(pageNumber, pageLimit);

        Page<WishListResponse> page = wishListRepository.findAll(pageable)
                .map(wishListMapper::mapToWishListResponse);

        return new PageResponse<>(page);

    }

    @Override
    public void deleteWishList(String uuid) {

        wishListRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with id %d not found! ", uuid)
                ));

        wishListRepository.deleteByUuid(uuid);
    }

    @Override
    public WishListResponse grantWishListByUuid(String uuid) {
        WishList wishList = wishListRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with uuid %s not found! ", uuid)
                )
        );
        wishList.setIsGranted(GrantStatus.GRANTED);
        wishListRepository.save(wishList);
        return wishListMapper.mapToWishListResponse(wishList);
    }

    @Override
    public WishListResponse denyWishListByUuid(String uuid) {
        WishList wishList = wishListRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with uuid %s not found! ", uuid)
                )
        );
        wishList.setIsGranted(GrantStatus.DENIED);
        wishListRepository.save(wishList);
        return wishListMapper.mapToWishListResponse(wishList);
    }

    @Override
    public WishListResponse getWishListByUuid(String uuid) {
        WishList wishList = wishListRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with uuid %s not found! ", uuid)
                )
        );
        return wishListMapper.mapToWishListResponse(wishList);
    }

    @Override
    public WishListResponse getWishListByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with username %s not found! ", username)
                )
        );
        return wishListMapper.mapToWishListResponse(wishListRepository.findByUser(user));
    }
}
