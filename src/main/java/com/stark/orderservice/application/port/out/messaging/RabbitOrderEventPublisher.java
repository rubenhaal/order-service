package com.stark.orderservice.application.port.out.messaging;

import com.stark.orderservice.adapter.out.messaging.rabbit.config.RabbitOrderCreateConfig;
import com.stark.orderservice.application.port.out.OrderEventPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderEventPublisher implements OrderEventPort {
    private final RabbitTemplate rabbitTemplate;

    public RabbitOrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publishOrderCreated(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitOrderCreateConfig.ORDER_EVENTS_EXCHANGE,
                RabbitOrderCreateConfig.ORDER_CREATED_ROUTING_KEY,
                event
        );
    }

}
