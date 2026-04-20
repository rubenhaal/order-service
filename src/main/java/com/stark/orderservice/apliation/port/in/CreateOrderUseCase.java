package com.stark.orderservice.apliation.port.in;

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

    public OrderResponse createOrder(OrderRequest orderRequest) {

        //check for null

        List<OrderItem> items = orderRequest.orderItems().stream().map(mapper::toOrderItem).toList();
        Order order= Order.createNew(orderRequest.customerId(),items);

        return mapper.toOrderResponse(order);
    }
}
