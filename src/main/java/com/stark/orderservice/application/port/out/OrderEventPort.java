package com.stark.orderservice.application.port.out;

import com.stark.orderservice.application.port.out.messaging.OrderCreatedEvent;

public interface OrderEventPort {
    void publishOrderCreated(OrderCreatedEvent event);
}
