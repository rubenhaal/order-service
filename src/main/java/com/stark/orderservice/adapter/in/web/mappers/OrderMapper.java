package com.stark.orderservice.adapter.in.web.mappers;

import com.stark.orderservice.domain.model.Order;
import com.stark.orderservice.domain.model.OrderItem;
import com.stark.orderservice.adapter.in.web.dto.OrderItemRequest;
import com.stark.orderservice.adapter.in.web.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
//    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderItem toOrderItem(OrderItemRequest request);
    OrderResponse toOrderResponse(Order orderItem);
}
