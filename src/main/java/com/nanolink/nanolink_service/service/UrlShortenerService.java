package com.nanolink.nanolink_service.service;

import com.nanolink.nanolink_service.dto.UrlRequestDTO;
import com.nanolink.nanolink_service.dto.UrlResponseDTO;
import com.nanolink.nanolink_service.model.Url;
import com.nanolink.nanolink_service.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    public Optional<String> getOriginalUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).map(Url::getOriginalUrl);
    }

    public String shortenUrl(UrlRequestDTO urlRequestDTO) {
        String shortUrl;
        do {
            shortUrl = UUID.randomUUID().toString();
        } while (urlRepository.findByShortUrl(shortUrl).isPresent());

        UrlResponseDTO newUrlDTO = new UrlResponseDTO(shortUrl);
        Url newUrl = new Url(urlRequestDTO.originalURL(), newUrlDTO.shortUrl());
        urlRepository.save(newUrl);
        return newUrlDTO.shortUrl();
    }
}
