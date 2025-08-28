package com.nanolink.nanolink_service.infra.web.controller;

import com.nanolink.nanolink_service.application.service.shortening.UrlShortenerService;
import com.nanolink.nanolink_service.domain.model.Url;
import com.nanolink.nanolink_service.infra.web.dto.UrlRequestDTO;
import com.nanolink.nanolink_service.infra.web.dto.UrlResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping("/v1/nanolink")
public class UrlController {

    private final UrlShortenerService shortenerService;

    public UrlController(UrlShortenerService service) {
        this.shortenerService = service;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestBody UrlRequestDTO request) {
        String originalUrl = request.originalURL();
        Url domainUrl = shortenerService.shorten(originalUrl);

        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/s/{originalUrl}")
                .buildAndExpand(domainUrl.getShortUrl())
                .toUriString();

        UrlResponseDTO response = new UrlResponseDTO(redirectUrl);
        URI location = URI.create(redirectUrl);

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/s/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortUrl) {
        return shortenerService.getOriginalUrl(shortUrl)
                .map(originalUrl -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create(originalUrl));

                    return new ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/s/stats/{shortUrl}")
    public ResponseEntity<Long> getUrlStatistics(@PathVariable String shortUrl) {
        return shortenerService.getUrlClickCount(shortUrl)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
