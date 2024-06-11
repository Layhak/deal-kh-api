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

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "category", qualifiedByName = "categoryToString")
    @Mapping(target = "shop", source = "shop", qualifiedByName = "shopToString")
    @Mapping(target = "discountPercentage", source = "discount", qualifiedByName = "discountToDouble")
    @Mapping(target = "images", source = "images")
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

    @Named("discountToDouble")
    default BigDecimal mapDiscount(Discount discount) {
        return discount.getDiscountValue();
    }


}
