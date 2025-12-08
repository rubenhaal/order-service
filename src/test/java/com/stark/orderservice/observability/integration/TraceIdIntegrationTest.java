package com.stark.orderservice.observability.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class TraceIdIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void responseContainsTraceIdHeader() throws Exception {
        mockMvc.perform(get("/api/order/demo"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Trace-Id"));
    }

    @Test
    void echoesProvidedTraceIdHeader() throws Exception {
        mockMvc.perform(get("/api/order/demo").header("X-Trace-Id", "demo-123"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Trace-Id", "demo-123"));
    }
}
