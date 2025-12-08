package com.stark.orderservice.controller;

import com.stark.orderservice.config.OrderProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderDemoController {

    private final OrderProperties orderProperties;

    @GetMapping("/api/order/demo")
    public ResponseEntity<String> getDemoOrder(){
        log.info("Demo endpoint called");
        return ResponseEntity.ok(orderProperties.getWelcomeMessage());
    }
}
