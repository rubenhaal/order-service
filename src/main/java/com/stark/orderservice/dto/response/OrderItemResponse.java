package com.stark.orderservice.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(String productId, int quantity, BigDecimal unitPrice) {
}
