package co.istad.dealkh.features.order;

import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;

import java.util.List;

/**
 * OrderService is a service class that provides methods for creating, retrieving, updating, and deleting orders.
 * It handles the business logic for managing orders in the application.
 */
public interface OrderService {
    OrderResponse createOrder(String username, OrderRequest orderRequest);

    List<OrderResponse> getOrderByUsername(String username);

    void deleteOrder(String orderUuid);

}
