package com.stark.orderservice.application.port.in;

import com.stark.orderservice.application.port.out.OrderEventPort;
import com.stark.orderservice.application.port.out.messaging.OrderCreatedEvent;
import com.stark.orderservice.application.port.out.messaging.OrderCreatedItemEvent;
import com.stark.orderservice.domain.model.Order;
import com.stark.orderservice.domain.model.OrderItem;
import com.stark.orderservice.adapter.in.web.dto.OrderRequest;
import com.stark.orderservice.adapter.in.web.dto.OrderResponse;
import com.stark.orderservice.adapter.in.web.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateOrderUseCase {
    private final OrderMapper mapper;
    private final OrderEventPort orderEventPort;

    public OrderResponse createOrder(OrderRequest orderRequest) {

        //check for null

        List<OrderItem> items = orderRequest.orderItems().stream().map(mapper::toOrderItem).toList();
        Order order= Order.createNew(orderRequest.customerId(),items);

        OrderResponse response= mapper.toOrderResponse(order);
        OrderCreatedEvent event = new OrderCreatedEvent(
                response.id(),
                response.customerId(),
                response.orderItems().stream()
                        .map(i -> new OrderCreatedItemEvent(
                                i.productId(),
                                i.quantity(),
                                i.unitPrice()
                        ))
                        .toList(),
                response.totalAmount(),
                response.createdAt()
        );

        orderEventPort.publishOrderCreated(event);

        return response;
    }
}
