package co.istad.dealkh.mapper;

import co.istad.dealkh.converter.ImageListConverter;
import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Product;
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

    ProductResponseDetail mapProductToProductResponseDetail(Product product);

    Product mapProductRequestToProduct(ProductCreateRequest productCreateRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapProductToUpdateRequest(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);


//    @Mapping(source = "images", target = "images", qualifiedByName = "convertImagesToListOfString")
//    @Mapping(target = "images", expression = "java(mapImages(product.getImages()))")
//    @Mapping(source = "images", target = "images", qualifiedByName = "convertImagesToListOfString")

//    @Named("convertImagesToListOfString")
//    default List<String> convertImagesToListOfString(List<Image> images) {
//        return images.stream().map(Image::getUrl).collect(Collectors.toList());
//    }

}
