package com.stark.orderservice.adapter.in.web.dto;

import java.util.List;

public record OrderRequest(String customerId,
                           List<OrderItemRequest> orderItems) {
}
