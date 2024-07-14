package com.example.logging.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long startTime = System.currentTimeMillis();
        try {
            ClientHttpResponse response = execution.execute(request, body);
            long duration = System.currentTimeMillis() - startTime;

            log.info("Исходящий запрос: method={}, url={}, headers={}, body={}, duration={}ms",
                    request.getMethod(),
                    request.getURI(),
                    request.getHeaders(),
                    new String(body, StandardCharsets.UTF_8),
                    duration);

            log.info("Входящий ответ: status={}, headers={}",
                    response.getStatusCode(),
                    response.getHeaders());

            return response;
        } catch (IOException e) {
            log.error("Ошибка при выполнении HTTP-запроса: {}", e.getMessage());
            throw e;
        }
    }
}
