package co.istad.dealkh.features.grantedwishlist.web;

import co.istad.dealkh.domain.GrantedWishList;
import co.istad.dealkh.features.grantedwishlist.GrantedWishListService;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListRequest;
import co.istad.dealkh.features.grantedwishlist.dto.GrantedWishListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/granted-wishlists")
public class GrantedWishListController {

    private final GrantedWishListService grantedWishListService;


    @PostMapping
    GrantedWishListResponse grantedWishList(@RequestBody @Valid GrantedWishListRequest grantedWishListRequest) {
        return grantedWishListService.grantedWishList(grantedWishListRequest);
    }

    @GetMapping
    List<GrantedWishListResponse> getAllGrantedWishList() {
        return grantedWishListService.getAllGrantedWishList();
    }

    @DeleteMapping("/{id}")
    void deleteGrantedWishListById(@PathVariable Long id) {
        grantedWishListService.deleteGrantedWishListById(id);
    }
}
