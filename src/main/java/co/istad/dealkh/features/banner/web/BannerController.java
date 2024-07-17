package co.istad.dealkh.features.banner.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.banner.BannerService;
import co.istad.dealkh.features.banner.dto.BannerCreateRequest;
import co.istad.dealkh.features.banner.dto.BannerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banners")
public class BannerController {

    private final BannerService bannerService;

    @PostMapping
    public BaseResponse<BannerResponse> createBanner(@Valid @RequestBody BannerCreateRequest bannerCreateRequest) {
        return BaseResponse.<BannerResponse>createSuccess("Created banner successfully!")
                .setPayload(bannerService.createBanner(bannerCreateRequest));
    }

    @GetMapping
    public BaseResponse<List<BannerResponse>> getAllBanner() {
        return BaseResponse.<List<BannerResponse>>ok("Get all banner successfully!")
                .setPayload(bannerService.getAllBanner());
    }

    @DeleteMapping("/{uuid}")
    public BaseResponse<?> deleteBanner(@PathVariable String uuid) {
        bannerService.deleteBanner(uuid);
        return BaseResponse.ok("Successfully delete banner with uuid:" + uuid);
    }

    @GetMapping("/types")
    public BaseResponse<List<BannerResponse>> getAllBannerByBannerType(@RequestParam String bannerType) {
        return BaseResponse.<List<BannerResponse>>ok("Get all banner successfully!")
                .setPayload(bannerService.getAllBannerByType(bannerType));
    }

}
