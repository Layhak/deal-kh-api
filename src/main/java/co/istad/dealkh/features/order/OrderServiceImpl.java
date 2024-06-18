package co.istad.dealkh.features.order;

import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    public OrderResponse createOrder(String username, OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        order.setDate(LocalDateTime.now());
        // Find all products by product slugs
        List<Product> products = productRepository.findAllBySlugIn(orderRequest.productSlugs());
        if (products.isEmpty()) {
            throw new IllegalArgumentException("No products found for the given slugs.");
        }
        order.setProducts(products);
        // Set the user manually since we're using the username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        user.setUsername(username);
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrderByUsername(String username) {
        List<Order> orders = orderRepository.findAllByUserUsername(username);
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("No orders found for the given username.");
        }
        return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(String orderUuid) {
        Order order = orderRepository.findByUuid(orderUuid)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with the given UUID."));
        orderRepository.delete(order);
    }
}
