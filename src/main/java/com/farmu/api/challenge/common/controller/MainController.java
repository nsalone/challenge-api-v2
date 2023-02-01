package com.farmu.api.challenge.common.controller;

import com.farmu.api.challenge.common.dto.api.ApiHealthCheckTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MainController {

    @GetMapping("/health-check")
    public ApiHealthCheckTO healthCheck() {
        final ApiHealthCheckTO response = new ApiHealthCheckTO("UP");
        log.debug("Health check ok");
        return response;
    }
}