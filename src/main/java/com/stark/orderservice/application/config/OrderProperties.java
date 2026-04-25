package com.stark.orderservice.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "order-service")
@Data
public class OrderProperties {
    private String welcomeMessage;
    private String environment;

}
