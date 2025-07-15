package com.nanolink.nanolink_service.controller;

import com.nanolink.nanolink_service.dto.UrlRequestDTO;
import com.nanolink.nanolink_service.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController()
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlShortenerService service;
    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody UrlRequestDTO urlRequestDTO) {

        String url = service.shortenUrl(urlRequestDTO);

        return "shorted url: " + url;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortUrl) {
        return urlShortenerService.getOriginalUrl(shortUrl).map(originalUrl -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(originalUrl));

            return new ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY);
        }).orElse(ResponseEntity.notFound().build());
    }
}
