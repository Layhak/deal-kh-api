package co.istad.dealkh.features.banner;

import co.istad.dealkh.features.banner.dto.BannerCreateRequest;
import co.istad.dealkh.features.banner.dto.BannerResponse;

import java.util.List;

public interface BannerService {

    BannerResponse createBanner(BannerCreateRequest bannerCreateRequest);

    List<BannerResponse> getAllBanner();

    void deleteBanner(String uuid);
}
