package com.stark.orderservice.dto.request;

import java.math.BigDecimal;

public record OrderItemRequest (
        String productId,
        int quantity,
        BigDecimal unitPrice

){


}
