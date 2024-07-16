package co.istad.dealkh.features.shopreport.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.shopreport.ShopReportService;
import co.istad.dealkh.features.shopreport.dto.ShopReportRequest;
import co.istad.dealkh.features.shopreport.dto.ShopReportResponse;
import co.istad.dealkh.features.shopreport.dto.ShopReportUpdateRequest;
import co.istad.dealkh.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop-reports")
@RequiredArgsConstructor
public class ShopReportController {

    private final ShopReportService shopReportService;


    @PostMapping
    @Operation(summary = "report the shop")
    public BaseResponse<ShopReportResponse> reportShop(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid ShopReportRequest shopReportRequest) {
        return BaseResponse.<ShopReportResponse>createSuccess("Successfully created shop report!")
                .setPayload(shopReportService.reportShop(customUserDetails.getUsername(), shopReportRequest));
    }

    @GetMapping("/{shopSlug}")
    @Operation(summary = "Get shop reports by shop slug")
    public BaseResponse<List<ShopReportResponse>> getAllShopReport(@PathVariable String shopSlug) {
        return BaseResponse.<List<ShopReportResponse>>ok("Successfully retrieved shop reports!")
                .setPayload(shopReportService.getAllShopReport(shopSlug));
    }


    @GetMapping("/{uuid}/uuid")
    @Operation(summary = "Get shop report by uuid")
    public BaseResponse<ShopReportResponse> getShopReportByUuid(@PathVariable String uuid) {
        return BaseResponse.<ShopReportResponse>ok("Successfully retrieved shop report!")
                .setPayload(shopReportService.getShopReportByUuid(uuid));
    }


    @GetMapping("/{uuid}")
    @Operation(summary = "Update shop report")
    public BaseResponse<ShopReportResponse> updateShopReport(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String uuid, @RequestBody @Valid ShopReportUpdateRequest shopReportUpdateRequest) {
        return BaseResponse.<ShopReportResponse>ok("Successfully updated shop report!")
                .setPayload(shopReportService.updateShopReport(customUserDetails.getUsername(), uuid, shopReportUpdateRequest));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete shop report")
    public BaseResponse<?> deleteShopReport(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String uuid) {
        shopReportService.deleteShopReport(customUserDetails.getUsername(), uuid);
        return BaseResponse.ok("Successfully deleted shop report!");
    }


}
