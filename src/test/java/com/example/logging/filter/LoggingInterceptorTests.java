package com.example.logging.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoggingInterceptorTests {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer server;
    private Appender<ILoggingEvent> mockAppender;

    @BeforeEach
    public void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
        mockAppender = mock(Appender.class);
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(LoggingInterceptor.class);
        logger.addAppender(mockAppender);
    }

    @Test
    public void testOutgoingRequestLogging() {
        server.expect(MockRestRequestMatchers.requestTo("https://jsonplaceholder.typicode.com/todos/1"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("{\"id\": 1, \"title\": \"Test\"}", MediaType.APPLICATION_JSON));

        restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", String.class);

        verify(mockAppender, atLeastOnce()).doAppend(argThat(argument -> {
            String formattedMessage = argument.getFormattedMessage();
            return formattedMessage.contains("GET") && formattedMessage.contains("https://jsonplaceholder.typicode.com/todos/1");
        }));
        server.verify();
    }
}
