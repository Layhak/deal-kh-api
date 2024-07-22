package co.istad.dealkh.features.order.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.order.OrderService;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import co.istad.dealkh.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderController is a controller for managing orders.
 * It handles creating, retrieving, updating, and deleting orders.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link RestController} - Indicates that this class is a REST controller.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link RequestMapping} - Maps HTTP requests to handler methods of MVC and REST controllers.</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * Creates a new order based on the provided request.
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}")
        public BaseResponse<List<OrderResponse>> getOrdersByUsername(@PathVariable String username) {
        return BaseResponse.<List<OrderResponse>>ok("Success get all order").setPayload(orderService.getOrderByUsername(username));
    }

    /**
     * Creates a new order based on the provided request.
     *
     * @param orderRequest
     * @return
     */
    @PostMapping
        public BaseResponse<OrderResponse> createOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid OrderRequest orderRequest) {
        return BaseResponse.<OrderResponse>createSuccess("Success create order").setPayload(orderService.createOrder(customUserDetails.getUsername(), orderRequest));
    }

    /**
     * Deletes an order based on the provided ID.
     *
     * @param uuid
     * @return
     */
    @DeleteMapping("/{uuid}")
        public BaseResponse<?> deleteOrder(@PathVariable String uuid) {
        orderService.deleteOrder(uuid);
        return BaseResponse.ok("Success delete order").setPayload(new ArrayList<>());
    }

}
