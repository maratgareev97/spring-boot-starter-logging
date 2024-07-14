package com.example.logging.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class LoggingFilterTests {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    private LoggingFilter loggingFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loggingFilter = new LoggingFilter();
        Logger logger = (Logger) LoggerFactory.getLogger(LoggingFilter.class);
        logger.addAppender(mockAppender);
    }

    @Test
    void testDoFilter() throws Exception {
        when(httpServletRequest.getMethod()).thenReturn("GET");
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8083/test"));
        when(httpServletRequest.getHeaderNames()).thenReturn(Collections.enumeration(Collections.emptyList()));
        when(httpServletResponse.getStatus()).thenReturn(200);
        when(httpServletResponse.getHeaderNames()).thenReturn(Collections.emptyList());

        loggingFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
        verify(mockAppender, atLeastOnce()).doAppend(argThat(argument -> {
            String formattedMessage = argument.getFormattedMessage();
            return formattedMessage.contains("GET") && formattedMessage.contains("http://localhost:8083/test");
        }));
    }
}
