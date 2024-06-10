package co.istad.dealkh.features.order.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.order.OrderService;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get all orders")
    public BaseResponse<List<OrderResponse>> getOrders(@RequestParam Long userId) {
        return BaseResponse.<List<OrderResponse>>ok("Success get all order").setPayload(orderService.getOrdersByUserId(userId));
    }

    /**
     * Creates a new order based on the provided request.
     *
     * @param orderRequest
     * @return
     */
    @PostMapping
    @Operation(summary = "Create order")
    public BaseResponse<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return BaseResponse.<OrderResponse>createSuccess("Success create order").setPayload(orderService.createOrder(orderRequest));
    }

    /**
     * Deletes an order based on the provided ID.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order")
    public BaseResponse<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return BaseResponse.<Void>ok("Success delete order");
    }

}
