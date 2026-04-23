package com.stark.orderservice.adapter.out.messaging.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitOrderCreateConfig {

    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
    public static final String ORDER_EVENTS_EXCHANGE = "order.events.exchange";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true);
    }

    @Bean
    public DirectExchange orderEventsExchange() {
        return new DirectExchange(ORDER_EVENTS_EXCHANGE);
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder
                .bind(orderCreatedQueue())
                .to(orderEventsExchange())
                .with(ORDER_CREATED_ROUTING_KEY);
    }
}
