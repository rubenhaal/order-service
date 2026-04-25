package com.stark.orderservice.adapter.in.web;

import com.stark.orderservice.adapter.in.web.dto.OrderRequest;
import com.stark.orderservice.adapter.in.web.dto.OrderResponse;
import com.stark.orderservice.application.port.in.CreateOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders")
@AllArgsConstructor
@Tag(name = "Orders", description = "Operations related to order creation and lifecycle")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    @PostMapping
    @Operation(summary = "Create a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request){

        OrderResponse response = createOrderUseCase.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
