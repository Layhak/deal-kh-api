package co.istad.dealkh.features.shoprating;

import co.istad.dealkh.features.shoprating.dto.ShopRatingCount;
import co.istad.dealkh.features.shoprating.dto.ShopRatingRequest;
import co.istad.dealkh.features.shoprating.dto.ShopRatingResponse;

import java.util.List;

public interface ShopRatingService {

    ShopRatingResponse rateShop(String username, ShopRatingRequest shopRatingRequest);

    List<ShopRatingResponse> getAllShopRating();

    List<ShopRatingResponse> getAllShopRatingByShopSlug(String shopSlug);

    ShopRatingCount countByShopSlug(String shopSlug);

    void deleteByRating(String username, String shopSlug);
}
