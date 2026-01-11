package com.stark.orderservice.service;

import com.stark.orderservice.domain.OrderStatus;
import com.stark.orderservice.dto.request.OrderItemRequest;
import com.stark.orderservice.dto.request.OrderRequest;
import com.stark.orderservice.dto.response.OrderResponse;
import com.stark.orderservice.mappers.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @Spy
    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);
    @InjectMocks
    private OrderService service;


    @Test
    void shouldCreateOrder_WhenBuildConfirmedOrder(){
        //given
        OrderItemRequest item1 = new OrderItemRequest("p1", 2, new BigDecimal("10.50"));
        OrderItemRequest item2 = new OrderItemRequest("p2", 1, new BigDecimal("5.00"));
        OrderRequest orderRequest = new OrderRequest("test", List.of(item1,item2));

        //when
        OrderResponse result = service.createOrder(orderRequest);

        assertThat(result).isNotNull();
        assertThat(result.id()).isNotNull();
        assertThat(result.customerId()).isEqualTo("test");
        assertThat(result.status()).isEqualTo(OrderStatus.CONFIRMED);
        assertThat(result.createdAt()).isNotNull();

        assertThat(result.orderItems())
                .hasSize(2)
                .satisfiesExactly(
                        item -> {
                            assertThat(item.productId()).isEqualTo("p1");
                            assertThat(item.quantity()).isEqualTo(2);
                            assertThat(item.unitPrice()).isEqualByComparingTo("10.50");
                        },
                        item -> {
                            assertThat(item.productId()).isEqualTo("p2");
                            assertThat(item.quantity()).isEqualTo(1);
                            assertThat(item.unitPrice()).isEqualByComparingTo("5.00");
                        }
                );

        assertThat(result.totalAmount()).isEqualByComparingTo("26.00");
    }

}