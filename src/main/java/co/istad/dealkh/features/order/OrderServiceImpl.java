package co.istad.dealkh.features.order;

import co.istad.dealkh.domain.Order;
import co.istad.dealkh.domain.Product;
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

        Order savedOrder = orderRepository.save(order);

        Set<String> ownerEmails = products.stream()
                .map(product -> product.getShop().getEmail())
                .collect(Collectors.toSet());

        String emailContent = "New order placed:\n\n" +
                "Order ID: " + savedOrder.getId() + "\n" +
                "Customer: " + user.getUsername() + "\n" +
                "Order Date: " + savedOrder.getDate() + "\n" +
                "Products: " + products.stream().map(Product::getName).collect(Collectors.joining(",\n")) + "\n\n" +
                "Please review the order details.";

        for (String ownerEmail : ownerEmails) {
            mailService.sendEmail(ownerEmail, "New Order Notification", emailContent);
        }


        // Send notification to shop owner via Telegram
        Set<String> ownerChatIds = Set.of("-1002003901907");

        String message = "New order placed:\n\n" +
                "Order ID: " + savedOrder.getId() + "\n" +
                "Customer: " + user.getUsername() + "\n" +
                "Order Date: " + savedOrder.getDate() + "\n" +
                "Products: " + products.stream().map(Product::getName)
                .collect(Collectors.joining(",\n")) + "\n\n" +
                "Please review the order details.";

        for (String chatId : ownerChatIds) {
            telegramService.sendMessage(chatId, message); // Replace with actual Telegram sending logic
        }

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
