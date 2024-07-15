package co.istad.dealkh.features.shoprating;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.features.shop.ShopService;
import co.istad.dealkh.features.shoprating.dto.ShopRatingCount;
import co.istad.dealkh.features.shoprating.dto.ShopRatingRequest;
import co.istad.dealkh.features.shoprating.dto.ShopRatingResponse;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ShopRatingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopRatingServiceImpl implements ShopRatingService {

    private final ShopRatingRepository shopRatingRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopRatingMapper shopRatingMapper;
    private final ShopService shopService;

    @Override
    public ShopRatingResponse rateShop(String username, ShopRatingRequest shopRatingRequest) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found!"
                ));

        ShopRating shopRating = shopRatingMapper.mapShopRatingRequestToShopRating(shopRatingRequest);

        Shop shop = shopRepository.findBySlug(shopRatingRequest.shopSlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Shop not found!"
                ));

        if(shopRatingRequest.ratingValue() < 0 || shopRatingRequest.ratingValue() > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Rating must be between 0 and 5!"
            );
        }

        // Check if the user has already rated the product
        if (shopRatingRepository.findByUserUsernameAndShopSlug(username, shopRatingRequest.shopSlug()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You have already rated this shop!"
            );
        }

        shopRating.setUser(user);
        shopRating.setShop(shop);
        shopRating.setRatingValue(shopRating.getRatingValue());
        shopRating.setCreatedAt(LocalDateTime.now());
        shopRating.setRated(true);
        shopRatingRepository.save(shopRating);

        // Calculate the new average rating
        Double averageRating = shopService.getShopRatingAverage(shop.getSlug());
        shop.setRatingAvg(averageRating);
        shopRepository.save(shop);

        return shopRatingMapper.mapShopRatingToProductRatingResponse(shopRating);
    }

    @Override
    public List<ShopRatingResponse> getAllShopRating() {
        return shopRatingRepository.findAll()
                .stream()
                .map(shopRatingMapper::mapShopRatingToProductRatingResponse)
                .toList();
    }

    @Override
    public List<ShopRatingResponse> getAllShopRatingByShopSlug(String shopSlug) {
        return shopRatingRepository.findAllByShop(shopRepository.findBySlug(shopSlug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found!")))
                .stream()
                .map(shopRatingMapper::mapShopRatingToProductRatingResponse)
                .toList();
    }

    @Override
    public ShopRatingCount countByShopSlug(String shopSlug) {
        return new ShopRatingCount(shopRatingRepository.countByShop(
                shopRepository.findBySlug(shopSlug)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "shop not found!"))));
    }

    @Override
    public void deleteByRating(String username, String shopSlug) {
        ShopRating shopRating = shopRatingRepository.findByUserUsernameAndShopSlug(username, shopSlug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Shop rating not found!"
                ));

        if (!shopRating.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You're not the owner of this resource!"
            );
        }

        shopRatingRepository.delete(shopRating);

        // Recalculate the average rating for the shop
        Double averageRating = shopService.getShopRatingAverage(shopSlug);
        if (averageRating == null) {
            averageRating = 0.0; // Set default value if there are no ratings left
        }

        // Update the shop's average rating
        Shop shop = shopRating.getShop();
        shop.setRatingAvg(averageRating);
        shopRepository.save(shop);
    }

}
