# Spring Boot Starter для логирования HTTP запросов

## Описание
Этот Spring Boot Starter предназначен для логирования всех входящих и исходящих HTTP запросов в вашем приложении. Он автоматически настраивает логирование для отслеживания детальной информации о запросах и ответах, что обеспечивает полезные данные для анализа и отладки.

## Функциональность
Starter включает следующие функции:
- Логирование всех входящих HTTP запросов с деталями: метод запроса, URL, заголовки и время обработки.
- Логирование всех исходящих HTTP запросов, сделанных через `RestTemplate`, с деталями: метод, URL, заголовки, тело запроса и время выполнения.
- Конфигурация уровня логирования для возможности контроля над объемом логируемой информации.

## Реализация
Starter использует следующие компоненты:
- **LoggingFilter**: Фильтр для логирования входящих запросов и ответов.
- **LoggingInterceptor**: Интерцептор для логирования исходящих запросов через `RestTemplate`.
- **LoggingConfiguration**: Класс конфигурации, который регистрирует фильтр и интерцептор и настраивает `RestTemplate`.

## Установка
Добавьте зависимость в ваш Maven `pom.xml`:
xml
`    <dependency>
        <groupId>com.example</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
        <version>1.0.0</version>
    </dependency>`

## Настройка

Вы можете настроить уровень логирования в файле application.properties или application.yml вашего проекта:

properties

`# application.properties
logging.level.com.example.logging=DEBUG`

Или:

yaml

`# application.yml
logging:
level:
com.example.logging: DEBUG`

## Пример использования

Для активации логирования, просто добавьте этот Starter в ваш Spring Boot проект. Логирование будет автоматически применено ко всем HTTP запросам.
Пример контроллера

java

`package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExternalApiController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/external")
    public String callExternalApi(@RequestParam String url) {
        return restTemplate.getForObject(url, String.class);
    }
}`

## Тестирование

Включите unit-тесты для проверки правильности логирования как входящих, так и исходящих запросов.

## Документация API

Вся функциональность этого Starter документирована в коде через JavaDoc комментарии, обеспечивая подробные сведения по каждому классу и методу.

## Поддержка

Если у вас возникнут вопросы или предложения по улучшению этого Starter, пожалуйста, не стесняйтесь связываться с нами по электронной почте: support@example.com.
Лицензия

Этот проект распространяется под лицензией MIT.