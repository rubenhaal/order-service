package com.stark.orderservice.mappers;

import com.stark.orderservice.domain.Order;
import com.stark.orderservice.domain.OrderItem;
import com.stark.orderservice.dto.request.OrderItemRequest;
import com.stark.orderservice.dto.response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
//    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderItem toOrderItem(OrderItemRequest request);
    OrderResponse toOrderResponse(Order orderItem);
}
