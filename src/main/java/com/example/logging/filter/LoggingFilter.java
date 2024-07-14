package com.example.logging.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Ошибка обработки запроса: method={}, url={}, error={}",
                    httpRequest.getMethod(),
                    httpRequest.getRequestURL().toString(),
                    e.getMessage());
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("Запрос обработан: method={}, url={}, duration={} ms, status={}",
                    httpRequest.getMethod(),
                    httpRequest.getRequestURL().toString(),
                    duration,
                    httpResponse.getStatus());
        }
    }
}
