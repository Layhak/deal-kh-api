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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        order.setDate(LocalDateTime.now());
        List<Product> products = productRepository.findAllById(orderRequest.products());
        order.setProducts(products);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);

    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}