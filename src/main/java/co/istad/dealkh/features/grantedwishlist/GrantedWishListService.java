package co.istad.dealkh.features.grantedwishlist;

import co.istad.dealkh.domain.GrantedWishList;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListRequest;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListResponse;

import java.util.List;

public interface GrantedWishListService {

    GrantedWishListResponse grantedWishList(GrantedWishListRequest grantedWishListRequest);

    List<GrantedWishListResponse> getAllGrantedWishList();

    void deleteGrantedWishListById(Long id);

}
