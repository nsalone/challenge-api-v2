package com.farmu.api.challenge.domain.shortener_url.controller;

import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlRequest;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlResponse;
import com.farmu.api.challenge.domain.shortener_url.service.ShortenerUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/shortener-url")
public class ShortenerUrlController {

    @Autowired
    private ShortenerUrlService service;

    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable final String id) {
        log.info("[get]::");
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ShortenerUrlResponse shortener(@RequestBody final ShortenerUrlRequest shortenerUrlRequest) {
        log.info("[shortener]::");
        return service.shortener(shortenerUrlRequest);
    }

}