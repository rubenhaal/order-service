package com.stark.orderservice.service;

import com.stark.orderservice.domain.Order;
import com.stark.orderservice.domain.OrderItem;
import com.stark.orderservice.dto.request.OrderRequest;
import com.stark.orderservice.dto.response.OrderResponse;
import com.stark.orderservice.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderMapper mapper;

    public OrderResponse createOrder(OrderRequest orderRequest) {

        //check for null

        List<OrderItem> items = orderRequest.orderItems().stream().map(mapper::toOrderItem).toList();
        Order order= Order.createNew(orderRequest.customerId(),items);

        return mapper.toOrderResponse(order);
    }
}
