package co.istad.dealkh.features.order;

import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.mail.MailService;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.telegram.TelegramService;
import co.istad.dealkh.features.telegram.TelegramServiceImpl;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final TelegramService telegramService;

    @Override
    public OrderResponse createOrder(String username, OrderRequest orderRequest) {
        // Convert order request to order entity
        Order order = orderMapper.toOrder(orderRequest);
        order.setDate(LocalDateTime.now());

        // Find all products by product slugs
        List<Product> products = productRepository.findAllBySlugIn(orderRequest.productSlugs());
        if (products.isEmpty()) {
            throw new IllegalArgumentException("No products found for the given slugs.");
        }
        order.setProducts(products);

        // Set the user manually since we're using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        order.setUser(user);

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Group products by shop
        Map<Shop, List<Product>> productsByShop = products.stream()
                .collect(Collectors.groupingBy(Product::getShop));

        // Prepare and send email to each shop owner
        for (Map.Entry<Shop, List<Product>> entry : productsByShop.entrySet()) {
            Shop shop = entry.getKey();
            List<Product> shopProducts = entry.getValue();

            String emailContent = "New order placed:\n\n" +
                    "Order ID: " + savedOrder.getId() + "\n" +
                    "Customer: " + user.getUsername() + "\n" +
                    "Order Date: " + savedOrder.getDate() + "\n" +
                    "Products:\n" + shopProducts.stream().map(product -> "- " + product.getName()).collect(Collectors.joining("\n")) + "\n\n" +
                    "Please review the order details.";

            mailService.sendEmail(shop.getEmail(), "New Order Notification", emailContent, "order");
        }

        // Map saved order to response and return
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
