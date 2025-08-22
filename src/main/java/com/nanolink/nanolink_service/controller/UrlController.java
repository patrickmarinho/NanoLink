package com.nanolink.nanolink_service.controller;

import com.nanolink.nanolink_service.dto.UrlRequestDTO;
import com.nanolink.nanolink_service.dto.UrlResponseDTO;
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

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestBody UrlRequestDTO urlRequestDTO) {
        String url = service.shortenUrl(urlRequestDTO);

        UrlResponseDTO response = new UrlResponseDTO(url);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortUrl) {
        return service.getOriginalUrl(shortUrl).map(originalUrl -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(originalUrl));

            return new ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY);
        }).orElse(ResponseEntity.notFound().build());
    }
}
