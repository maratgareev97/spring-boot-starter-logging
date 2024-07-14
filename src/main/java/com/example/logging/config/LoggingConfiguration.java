package com.example.logging.config;

import com.example.logging.filter.LoggingFilter;
import com.example.logging.filter.LoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoggingConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Value("${logging.level.com.example.logging.filter.LoggingFilter:DEBUG}")
    private String filterLoggingLevel;

    @Value("${logging.level.com.example.logging.config.LoggingInterceptor:DEBUG}")
    private String interceptorLoggingLevel;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    // Деемо уровней логирования
    public void demoLoggingLevelUse() {
        logger.info("Уровень логирования установлен LoggingFilter: {}", filterLoggingLevel);
        logger.info("Уровень логирования установлен LoggingInterceptor: {}", interceptorLoggingLevel);
    }
}


