package com.stark.orderservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final String customerId;
    private final List<OrderItem> orderItems;
    private final OrderStatus status;
    private final BigDecimal totalAmount;
    private final Instant createdAt;

    private Order(UUID id, String customerId, List<OrderItem> orderItems, OrderStatus status, BigDecimal totalAmount, Instant createdAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.customerId = Objects.requireNonNull(customerId, "Customer Id must not be null");
        this.orderItems = List.copyOf(Objects.requireNonNull(orderItems, "items must not be null"));
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "totalAmount must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static Order createNew(String customerId, List<OrderItem> items) {
        Instant now = Instant.now();

        BigDecimal total = items.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Order(
                UUID.randomUUID(),
                customerId,
                items,
                OrderStatus.CONFIRMED,  // initial post-checkout state
                total,
                now
        );
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
