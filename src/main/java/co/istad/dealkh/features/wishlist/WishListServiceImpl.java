package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.features.wishlist.dto.WishListUpdate;
import co.istad.dealkh.mapper.WishListMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.specification.filter.PageFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final WishListMapper wishListMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public WishListResponse wishList(WishListRequest wishListRequest) {

        WishList newWishList = wishListMapper.mapRequestToWishList(wishListRequest);

        User user = userRepository.findById(wishListRequest.userId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id %d not found! ", wishListRequest.userId())));

        Product product = productRepository.findById(wishListRequest.productId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with id %d not found! ", wishListRequest.productId())));

        newWishList.setUser(user);
        newWishList.setProduct(product);
        newWishList.setGranted(false);
        wishListRepository.save(newWishList);

        return wishListMapper.mapToWishListResponse(newWishList);
    }

    @Override
    public PageResponse<WishListResponse> getAllWishList(Map<String, String> params) {

        int pageNumber = PageFilter.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageFilter.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageFilter.PAGE_NUMBER));
        }

        int pageLimit = PageFilter.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageFilter.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageFilter.PAGE_LIMIT));
        }

        Pageable pageable = PageFilter.getPageable(pageNumber, pageLimit);

        Page<WishListResponse> page = wishListRepository.findAll(pageable)
                .map(wishListMapper::mapToWishListResponse);

        return new PageResponse<>(page);

    }

    @Override
    public WishListResponse updateWishListById(Long id, WishListUpdate wishListUpdate) {
        WishList wishList = wishListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with id %d not found! ", id)
                ));


        wishList.setUpdatedAt(LocalDateTime.now());

        wishListMapper.mapWishListUpdateRequest(wishList, wishListUpdate);

        wishListRepository.save(wishList);


        return null;
    }

    @Override
    public void deleteWishList(Long id) {

        wishListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with id %d not found! ", id)
                ));

        wishListRepository.deleteById(id);
    }
}
