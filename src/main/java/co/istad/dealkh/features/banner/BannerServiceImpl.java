package co.istad.dealkh.features.banner;

import co.istad.dealkh.domain.Banner;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.banner.dto.BannerCreateRequest;
import co.istad.dealkh.features.banner.dto.BannerResponse;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.mapper.BannerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    private final ShopRepository shopRepository;

    @Override
    public BannerResponse createBanner(BannerCreateRequest bannerCreateRequest) {

        Banner banner = bannerMapper.mapBannerRequestToBanner(bannerCreateRequest);

        if(bannerCreateRequest.expiredAt().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expired date must be after today");
        }

        banner.setIsExpired(banner.getExpiredAt().isBefore(LocalDate.now()));

        banner.setDescription(bannerCreateRequest.description());
        banner.setIsExpired(banner.getIsExpired());
        bannerRepository.save(banner);

        return bannerMapper.mapBannerToBannerResponse(banner);
    }

    @Override
    public List<BannerResponse> getAllBanner() {
        return bannerRepository.findAll()
                .stream()
                .map(bannerMapper::mapBannerToBannerResponse)
                .toList();
    }

    @Override
    public void deleteBanner(String uuid) {
        Banner banner = bannerRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Banner uuid not found"));
        bannerRepository.delete(banner);
    }

    @Override
    public List<BannerResponse> getAllBannerByType(String bannerType) {
        return bannerRepository.findAllByBannerTypeIgnoreCase(bannerType)
                .stream()
                .map(bannerMapper::mapBannerToBannerResponse)
                .toList();
    }
}
