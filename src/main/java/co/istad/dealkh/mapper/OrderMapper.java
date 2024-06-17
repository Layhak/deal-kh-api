package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import co.istad.dealkh.features.product.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "products", source = "products")
    @Mapping(target = "date", source = "date")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "products", ignore = true)
    Order toOrder(OrderRequest orderRequest);
    
    @Mapping(target = "name", source = "name")
    @Mapping(target = "shop", source = "shop.name")
    @Mapping(target = "category", source = "category.name")
        // Map the category name to the category field in ProductResponse
    ProductResponse productToProductResponse(Product product);

    default String map(Shop shop) {
        return shop.getName();
    }

    default String map(Category category) {
        return category.getName();
    }
}