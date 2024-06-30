package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "products", source = "products", qualifiedByName = "mapProductSlugs")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "products", source = "productSlugs", qualifiedByName = "mapProductSlugsToProducts")
    Order toOrder(OrderRequest orderRequest);

    @Named("mapProductSlugs")
    default List<String> mapProductSlugs(List<Product> products) {
        return products.stream().map(Product::getName).toList();
    }

    @Named("mapProductSlugsToProducts")
    default List<Product> mapProductSlugsToProducts(List<String> productSlugs) {
        return productSlugs.stream().map(slug -> {
            Product product = new Product();
            product.setSlug(slug);
            return product;
        }).toList();
    }

}