package com.stark.orderservice.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Request used to create an order")
public record OrderRequest( @Schema(example = "customer-123") String customerId,
                           List<OrderItemRequest> orderItems) {
}
