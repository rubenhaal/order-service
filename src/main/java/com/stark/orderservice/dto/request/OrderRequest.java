package com.stark.orderservice.dto.request;

import java.util.List;

public record OrderRequest(String customerId,
                           List<OrderItemRequest> orderItems) {
}
