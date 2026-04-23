package com.stark.orderservice.application.port.out.messaging;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId,
                                String customerId,
                                List<OrderCreatedItemEvent> items,
                                BigDecimal totalAmount,
                                Instant createdAt) {
}
