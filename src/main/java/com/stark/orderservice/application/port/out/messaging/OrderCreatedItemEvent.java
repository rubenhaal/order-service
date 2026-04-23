package com.stark.orderservice.application.port.out.messaging;

import java.math.BigDecimal;

public record OrderCreatedItemEvent(String productId,
                                    int quantity,
                                    BigDecimal unitPrice) {
}
