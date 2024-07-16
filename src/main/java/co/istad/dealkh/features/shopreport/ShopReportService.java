package co.istad.dealkh.features.shopreport;

import co.istad.dealkh.features.shopreport.dto.ShopReportRequest;
import co.istad.dealkh.features.shopreport.dto.ShopReportResponse;
import co.istad.dealkh.features.shopreport.dto.ShopReportUpdateRequest;

import java.util.List;

public interface ShopReportService {

    ShopReportResponse reportShop(String username, ShopReportRequest shopReportRequest);

    List<ShopReportResponse> getAllShopReport(String shopSlug);

    ShopReportResponse getShopReportByUuid(String uuid);

    ShopReportResponse updateShopReport(String username, String uuid, ShopReportUpdateRequest shopReportUpdateRequest);

    void deleteShopReport(String username, String uuid);
}
