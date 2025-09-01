package com.nanolink.nanolink_service.infra.web.controller;

import com.nanolink.nanolink_service.application.service.shortening.UrlShortenerService;
import com.nanolink.nanolink_service.domain.model.Url;
import com.nanolink.nanolink_service.infra.web.dto.UrlRequestDTO;
import com.nanolink.nanolink_service.infra.web.dto.UrlResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping("/v1/urls")
public class UrlController {
    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestBody UrlRequestDTO urlRequestDTO) {
        String originalUrl = urlRequestDTO.originalURL();
        Url domainUrl = urlShortenerService.shorten(originalUrl);
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{originalUrl}")
                .buildAndExpand(domainUrl.getShortUrl())
                .toUriString();
        UrlResponseDTO response = new UrlResponseDTO(redirectUrl);
        URI location = URI.create(redirectUrl);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{shortUrl}/stats")
    public ResponseEntity<Long> getUrlStatistics(@PathVariable String shortUrl) {
        return urlShortenerService.getUrlClickCount(shortUrl)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
