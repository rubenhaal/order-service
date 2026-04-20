package com.stark.orderservice.adapter.in.web.dto;

import com.stark.orderservice.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID id, String customerId, List<OrderItemResponse> orderItems, OrderStatus status,
                            BigDecimal totalAmount, Instant createdAt) {
}
