package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.ProductFeedback;
import co.istad.dealkh.domain.ShopReport;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackRequest;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackResponse;
import co.istad.dealkh.features.productfeedback.dto.ProductFeedbackUpdate;
import co.istad.dealkh.features.shopreport.dto.ShopReportRequest;
import co.istad.dealkh.features.shopreport.dto.ShopReportResponse;
import co.istad.dealkh.features.shopreport.dto.ShopReportUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = {CustomMapper.class})
public interface ShopReportMapper {
    @Mapping(target = "shop", source = "shop.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "profile", source = "user.profile")
    ShopReportResponse toShopReportResponse(ShopReport shopReport);

    @Mapping(target = "shop.name", source = "shopSlug")
    ShopReport toShopReport(ShopReportRequest shopReportRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapShopReportUpdateRequest(@MappingTarget ShopReport shopReport, ShopReportUpdateRequest shopReportUpdateRequest);
}
