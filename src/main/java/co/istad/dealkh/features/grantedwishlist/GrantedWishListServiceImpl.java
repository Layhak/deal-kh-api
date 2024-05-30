package co.istad.dealkh.features.grantedwishlist;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.GrantedWishList;
import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.features.discount.DiscountRepository;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListRequest;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListResponse;
import co.istad.dealkh.features.wishlist.WishListRepository;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.mapper.GrantedWishListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GrantedWishListServiceImpl implements GrantedWishListService {

    private final GrantedWishListRepository grantedWishListRepository;
    private final GrantedWishListMapper grantedWishListMapper;
    private final DiscountRepository discountRepository;
    private final WishListRepository wishListRepository;

    @Override
    public GrantedWishListResponse grantedWishList(GrantedWishListRequest grantedWishListRequest) {

        Discount discount = discountRepository.findById(grantedWishListRequest.discountId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Discount with id %d not found! ", grantedWishListRequest.discountId())
                ));

        WishList wishList = wishListRepository.findById(grantedWishListRequest.wishListId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("WishList with id %d not found! ", grantedWishListRequest.wishListId())
                ));

        if(wishList.isGranted()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("WishList with id %d is already granted! ", grantedWishListRequest.wishListId())
            );
        }

        GrantedWishList newGrantedWishList = new GrantedWishList();
        newGrantedWishList.setGrantedDate(LocalDate.now());
        newGrantedWishList.setDiscount(discount);
        newGrantedWishList.setWishList(wishList);
        wishList.setGranted(true);
        wishListRepository.save(wishList);
        grantedWishListRepository.save(newGrantedWishList);

        return grantedWishListMapper.mapToGrantedWishListResponse(newGrantedWishList);
    }

    @Override
    public List<GrantedWishListResponse> getAllGrantedWishList() {
        return grantedWishListRepository.findAll()
                .stream()
                .map(grantedWishListMapper::mapToGrantedWishListResponse)
                .toList();
    }


    @Override
    public void deleteGrantedWishListById(Long id) {
        GrantedWishList grantedWishList = grantedWishListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("GrantedWishList with id %d not found! ", id)
                ));

        grantedWishListRepository.delete(grantedWishList);
    }
}
