package co.istad.dealkh.features.shopreport;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.features.shoprating.ShopRatingRepository;
import co.istad.dealkh.features.shopreport.dto.ShopReportRequest;
import co.istad.dealkh.features.shopreport.dto.ShopReportResponse;
import co.istad.dealkh.features.shopreport.dto.ShopReportUpdateRequest;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ShopReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopReportServiceImpl implements ShopReportService {

    private final UserRepository userRepository;
    private final ShopReportRepository shopReportRepository;
    private final ShopReportMapper shopReportMapper;
    private final ShopRatingRepository shopRatingRepository;
    private final ShopRepository shopRepository;

    @Override
    public ShopReportResponse reportShop(String username, ShopReportRequest shopReportRequest) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"));

        // Check if the user has already rated the product
        if (shopRatingRepository.findByUserUsernameAndShopSlug(username, shopReportRequest.shopSlug()).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You have to rate shop before leave a feedback!"
            );
        }
        if(shopReportRepository.findByUserUsernameAndShopSlug(username, shopReportRequest.shopSlug()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You can this shop only one time!"
            );
        }

        Shop shop = shopRepository.findBySlug(shopReportRequest.shopSlug())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Shop not found"
                ));

        ShopReport shopReport = shopReportMapper.toShopReport(shopReportRequest);
        shopReport.setUser(user);
        shopReport.setShop(shop);
        return shopReportMapper.toShopReportResponse(shopReportRepository.save(shopReport));
    }

    @Override
    public List<ShopReportResponse> getAllShopReport(String shopSlug) {
        return shopReportRepository.findByShopSlug(shopSlug)
                .stream()
                .map(shopReportMapper::toShopReportResponse)
                .toList();
    }

    @Override
    public ShopReportResponse getShopReportByUuid(String uuid) {
        return shopReportRepository.findByUuid(uuid)
                .map(shopReportMapper::toShopReportResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Shop report not found"
                ));
    }

    @Override
    public ShopReportResponse updateShopReport(String username, String uuid, ShopReportUpdateRequest shopReportUpdateRequest) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
        ));

        ShopReport shopReport = shopReportRepository.findByUuid(uuid).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Shop report not found"
        ));

        if(shopReportRepository.findByUserUsernameAndUuid(username, uuid).isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        shopReportMapper.mapShopReportUpdateRequest(shopReport, shopReportUpdateRequest);
        shopReport.setUpdatedAt(LocalDateTime.now());
        shopReport.setUpdatedBy(username);
        shopReport.setUser(user);
        shopReport.setDescription(shopReportUpdateRequest.description());
        return shopReportMapper.toShopReportResponse(shopReportRepository.save(shopReport));
    }

    @Override
    public void deleteShopReport(String username, String uuid) {

        ShopReport shopReport = shopReportRepository.findByUuid(uuid).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Shop report not found"
        ));

        if(shopReportRepository.findByUserUsernameAndUuid(username, uuid).isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        shopReportRepository.delete(shopReport);

    }
}
