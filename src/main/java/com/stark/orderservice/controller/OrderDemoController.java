package com.stark.orderservice.controller;

import com.stark.orderservice.config.OrderProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class OrderDemoController {

    private final OrderProperties orderProperties;

    @GetMapping("/api/order/demo")
    public ResponseEntity<String> getDemoOrder(){
        return ResponseEntity.ok(orderProperties.getWelcomeMessage());
    }
}
