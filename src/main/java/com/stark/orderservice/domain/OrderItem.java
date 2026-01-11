package com.stark.orderservice.domain;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItem {

    private String productId;
    private int quantity;
    private BigDecimal unitPrice;

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
