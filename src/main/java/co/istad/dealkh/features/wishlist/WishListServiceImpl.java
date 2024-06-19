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
import co.istad.dealkh.paging.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final WishListMapper wishListMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DiscountTypeRepository discountTypeRepository;

    private void validateSortingParams(String field, String order) {
        List<String> validFields = Arrays.asList("discountTypeSlug", "productName", "discountPercentage");

        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be  discountTypeSlug, productName or discountPercentage");
        }
        if (order != null && !order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
    }

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
    public PageResponse<WishListResponse> getAllWishList(int page, int size, String field, String order, Map<String, String> params) {

        validateSortingParams(field, order);


        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<WishListResponse> wishes = wishListRepository.findAll(pageable)
                .map(wishListMapper::mapToWishListResponse);

        return new PageResponse<>(wishes);

    }

    @Override
    public void deleteWishList(String uuid) {

        wishListRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with uuid %s not found! ", uuid)
                ));

        wishListRepository.deleteByUuid(uuid);
    }

    @Override
    public WishListResponse updateWishList(String uuid, String username, WishListRequest wishListRequest) {
        WishList wishList = wishListRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("WishList with uuid %s not found! ", uuid)
                ));
        //if username is not the owner of the wishlist
        if (!wishList.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        wishListMapper.mapWishListToUpdateRequest(wishList, wishListRequest);

        wishList = wishListRepository.save(wishList);

        return wishListMapper.mapToWishListResponse(wishList);
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
    public PageResponse<WishListResponse> getWishListByUsername(int page, int size, String field, String order, Map<String, String> params, String username) {

        validateSortingParams(field, order);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with username %s not found! ", username)
                ));
        Page<WishList> wishesPage = wishListRepository.findByUser(user, pageable);

        // Map WishList entities to WishListResponse objects
        List<WishListResponse> wishListResponses = wishesPage.getContent().stream()
                .map(wishListMapper::mapToWishListResponse)
                .collect(Collectors.toList());

        // Create and return PageResponse object with paginated data
        return new PageResponse<>(
                wishListResponses,
                wishesPage.getNumber(),     // Current page number
                wishesPage.getSize(),       // Page size
                wishesPage.getTotalElements(),  // Total elements count
                wishesPage.getTotalPages(),    // Total pages count
                wishesPage.hasPrevious(),   // Whether there's a previous page
                wishesPage.hasNext()        // Whether there's a next page
        );
    }
}
