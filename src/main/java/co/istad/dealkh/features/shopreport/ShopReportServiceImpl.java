package co.istad.dealkh.features.shopreport;

import co.istad.dealkh.features.shopreport.dto.ShopReportRequest;
import co.istad.dealkh.features.shopreport.dto.ShopReportResponse;
import co.istad.dealkh.features.shopreport.dto.ShopReportUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopReportServiceImpl implements ShopReportService {
    @Override
    public ShopReportResponse reportShop(ShopReportRequest shopReportRequest) {
        return null;
    }

    @Override
    public List<ShopReportResponse> getAllShopReport() {
        return List.of();
    }

    @Override
    public ShopReportResponse getShopReportById(String uuid) {
        return null;
    }

    @Override
    public ShopReportResponse updateShopReport(String username, String uuid, ShopReportUpdateRequest shopReportUpdateRequest) {
        return null;
    }

    @Override
    public void deleteShopReport(String username, String uuid) {

    }
}
