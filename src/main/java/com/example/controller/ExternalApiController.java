package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExternalApiController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/external")
    public String callExternalApi(@RequestParam String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
