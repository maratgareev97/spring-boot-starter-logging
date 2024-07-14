package com.example.logging.config;

import com.example.logging.filter.LoggingFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoggingConfigurationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testLoggingFilterRegistration() {
        FilterRegistrationBean<?> filterRegistrationBean = applicationContext.getBean(FilterRegistrationBean.class);
        assertThat(filterRegistrationBean).isNotNull();
        assertThat(filterRegistrationBean.getFilter()).isInstanceOf(LoggingFilter.class);
        assertThat(filterRegistrationBean.getUrlPatterns()).contains("/*"); // Проверяем, что фильтр зарегистрирован для всех URL
    }
}
