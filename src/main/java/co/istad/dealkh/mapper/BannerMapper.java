package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Banner;
import co.istad.dealkh.features.banner.dto.BannerCreateRequest;
import co.istad.dealkh.features.banner.dto.BannerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    BannerResponse mapBannerToBannerResponse(Banner banner);

    Banner mapBannerRequestToBanner(BannerCreateRequest bannerCreateRequest);

}
