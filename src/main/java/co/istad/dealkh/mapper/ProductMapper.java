package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface ProductMapper {

    @Mapping(target = "seller", source = "product.createdBy")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categorySlug", source = "category.slug")
    @Mapping(target = "shop", source = "shop", qualifiedByName = "shopToString")
    @Mapping(target = "shopSlug", source = "shop", qualifiedByName = "shopSlugToString")
    @Mapping(target = "location", source = "shop", qualifiedByName = "mapLocation")
    @Mapping(target = "openAt", source = "shop", qualifiedByName = "mapOpenAt")
    @Mapping(target = "closeAt", source = "shop", qualifiedByName = "mapCloseAt")
    @Mapping(target = "discountValue", source = "discount", qualifiedByName = "discountToDouble")
    @Mapping(target = "isPercentage", source = "discount", qualifiedByName = "discountToBoolean")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "discountPrice", source = "product", qualifiedByName = "toDiscountPrice")
    @Mapping(target = "discountType", source = "discount", qualifiedByName = "discountToDiscountType")
    @Mapping(target = "discountTypeSlug", source = "discount", qualifiedByName = "discountToDiscountTypeSlug")
    @Mapping(target = "expiredAt", source = "discount", qualifiedByName = "discountToExpiredAt")
    @Mapping(target = "ratingCount", source = "product", qualifiedByName = "mapRatingCount")
        // Use the mapTotalRating method
    ProductResponse mapProductToProductResponseDetail(Product product);

    Product mapProductRequestToProduct(ProductCreateRequest productCreateRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapProductToUpdateRequest(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);

    @Named("categoryToString")
    default String mapCategory(Category category) {
        return category.getName();
    }

    @Named("shopToString")
    default String mapShop(Shop shop) {
        return shop.getName();
    }

    @Named("shopSlugToString")
    default String mapShopSlug(Shop shop) {
        return shop.getSlug();
    }

    @Named("discountToDouble")
    default BigDecimal mapDiscount(Discount discount) {
        return discount.getDiscountValue();
    }

    @Named("discountToBoolean")
    default Boolean mapIsPercentage(Discount discount) {
        return discount.getIsPercentage();
    }

    @Named("toDiscountPrice")
    default BigDecimal mapDiscountPrice(Product product) {
        if (product.getDiscount().getIsPercentage()) {
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            BigDecimal discountValue = product.getDiscount().getDiscountValue();
            BigDecimal discountRate = discountValue.divide(BigDecimal.valueOf(100));
            return price.multiply(discountRate);
        }
        return product.getDiscount().getDiscountValue();
    }

    @Named("discountToDiscountType")
    default String mapDiscountType(Discount discount) {
        return discount.getDiscountType().getName();
    }

    @Named("discountToDiscountTypeSlug")
    default String mapDiscountTypeSlug(Discount discount) {
        return discount.getDiscountType().getSlug();
    }

    @Named("discountToExpiredAt")
    default LocalDate mapExpiredAt(Discount discount) {
        return discount.getExpiredAt();
    }

    @Named("mapLocation")
    default String mapLocation(Shop shop) {
        return shop.getLocation();
    }

    @Named("mapOpenAt")
    default LocalTime mapOpenAt(Shop shop) {
        return shop.getOpenAt();
    }

    @Named("mapCloseAt")
    default LocalTime mapCloseAt(Shop shop) {
        return shop.getCloseAt();
    }
}
