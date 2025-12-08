package com.stark.orderservice.observability;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class TraceIdFilterTest {

    private final TraceIdFilter filter = new TraceIdFilter();

    @AfterEach
    void cleanup() {
        MDC.clear();
    }

    @Test
    void generatesTraceId_whenHeaderIsMissing_andCleansMdc() throws Exception {
        var request = new MockHttpServletRequest("GET", "/api/order/demo");
        var response = new MockHttpServletResponse();

        AtomicReference<String> traceIdSeenInsideChain = new AtomicReference<>();

        FilterChain chain = (req, res) -> traceIdSeenInsideChain.set(MDC.get("traceId"));

        filter.doFilter(request, response, chain);

        String headerTraceId = response.getHeader("X-Trace-Id");
        assertNotNull(headerTraceId);
        assertFalse(headerTraceId.isBlank());

        assertEquals(headerTraceId, traceIdSeenInsideChain.get());

        assertNull(MDC.get("traceId"));
    }

    @Test
    void reusesTraceId_whenHeaderIsProvided_andCleansMdc() throws Exception {
        var request = new MockHttpServletRequest("GET", "/api/order/demo");
        request.addHeader("X-Trace-Id", "demo-123");
        var response = new MockHttpServletResponse();

        AtomicReference<String> traceIdSeenInsideChain = new AtomicReference<>();

        FilterChain chain = (req, res) -> traceIdSeenInsideChain.set(MDC.get("traceId"));

        filter.doFilter(request, response, chain);

        assertEquals("demo-123", response.getHeader("X-Trace-Id"));
        assertEquals("demo-123", traceIdSeenInsideChain.get());
        assertNull(MDC.get("traceId"));
    }

}