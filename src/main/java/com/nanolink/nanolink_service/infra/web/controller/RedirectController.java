package com.nanolink.nanolink_service.infra.web.controller;

import com.nanolink.nanolink_service.application.service.shortening.UrlShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

    private final UrlShortenerService shortenerService;

    public RedirectController(UrlShortenerService service) {
        this.shortenerService = service;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortUrl) {
        return shortenerService.getOriginalUrl(shortUrl)
                .map(originalUrl -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create(originalUrl));

                    return new ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY);
                }).orElse(ResponseEntity.notFound().build());
    }
}
