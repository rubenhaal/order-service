package com.stark.orderservice.adapter.in.web.dto;

import java.math.BigDecimal;

public record OrderItemResponse(String productId, int quantity, BigDecimal unitPrice) {
}
