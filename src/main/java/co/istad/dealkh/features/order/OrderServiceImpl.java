package co.istad.dealkh.features.order;

import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

/**
 * OrderServiceImpl is a service implementation of {@link OrderService} that handles order-related operations.
 * It includes methods for creating, retrieving, updating, and deleting orders.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 * </p>
 */
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    /**
     * Creates a new order based on the provided request.
     *
     * @param orderRequest
     * @return
     */
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        order.setDate(LocalDateTime.now());
        List<Product> products = productRepository.findAllById(orderRequest.products());
        order.setProducts(products);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);

    }

    /**
     * Retrieves all orders for a given user ID.
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    /**
     * Deletes an order based on the provided ID.
     *
     * @param orderId
     */
    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}