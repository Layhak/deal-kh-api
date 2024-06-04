package co.istad.dealkh.features.order;

import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
        OrderResponse createOrder(OrderRequest orderRequest);
        List<OrderResponse> getOrdersByUserId(Long userId);

        void deleteOrder(Long orderId);

}
