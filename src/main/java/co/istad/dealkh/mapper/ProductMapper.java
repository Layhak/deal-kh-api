package co.istad.dealkh.mapper;

import co.istad.dealkh.converter.ImageListConverter;
import co.istad.dealkh.domain.*;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.features.category.CategoryService;
import co.istad.dealkh.features.discount.DiscountService;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponseDetail;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.features.shop.ShopService;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "category", qualifiedByName = "categoryToString")
    @Mapping(target = "shop", source = "shop", qualifiedByName = "shopToString")
    @Mapping(target = "discountPercentage", source = "discount", qualifiedByName = "discountToDouble")
    ProductResponseDetail mapProductToProductResponseDetail(Product product);

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
    default Double mapDiscount(Discount discount) {
        return discount.getDiscountPercentage();
    }



}
