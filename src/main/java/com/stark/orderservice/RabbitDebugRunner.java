package com.stark.orderservice;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RabbitDebugRunner implements CommandLineRunner {

    private final ApplicationContext context;
    private final AmqpAdmin amqpAdmin;
    private final Queue orderCreatedQueue;
    private final DirectExchange orderEventsExchange;
    private final Binding orderCreatedBinding;

    @Value("${spring.rabbitmq.host:NOT_FOUND}")
    private String rabbitHost;

    public RabbitDebugRunner(
            ApplicationContext context,
            AmqpAdmin amqpAdmin,
            Queue orderCreatedQueue,
            DirectExchange orderEventsExchange,
            Binding orderCreatedBinding
    ) {
        this.context = context;
        this.amqpAdmin = amqpAdmin;
        this.orderCreatedQueue = orderCreatedQueue;
        this.orderEventsExchange = orderEventsExchange;
        this.orderCreatedBinding = orderCreatedBinding;
    }

    @Override
    public void run(String... args) {
        System.out.println("RABBIT_HOST = " + rabbitHost);
        System.out.println("Has AmqpAdmin bean? " + !context.getBeansOfType(AmqpAdmin.class).isEmpty());
        System.out.println("Has Queue bean? " + !context.getBeansOfType(Queue.class).isEmpty());
        System.out.println("Queue beans = " + context.getBeansOfType(Queue.class).keySet());

        amqpAdmin.declareExchange(orderEventsExchange);
        amqpAdmin.declareQueue(orderCreatedQueue);
        amqpAdmin.declareBinding(orderCreatedBinding);

        System.out.println("Rabbit declarations executed manually.");
    }
}