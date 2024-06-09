package co.istad.dealkh.features.order.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.order.OrderService;
import co.istad.dealkh.features.order.dto.OrderRequest;
import co.istad.dealkh.features.order.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get all orders")
    public BaseResponse<List<OrderResponse>> getOrders(@RequestParam Long userId) {
        return BaseResponse.<List<OrderResponse>>ok("Success get all order").setPayload(orderService.getOrdersByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create order")
    public BaseResponse<OrderResponse> createOrder(OrderRequest orderRequest) {
        return BaseResponse.<OrderResponse>createSuccess("Success create order").setPayload(orderService.createOrder(orderRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order")
    public BaseResponse<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return BaseResponse.<Void>ok("Success delete order");
    }

}
