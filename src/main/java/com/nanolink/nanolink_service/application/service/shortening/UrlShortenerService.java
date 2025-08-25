package com.nanolink.nanolink_service.application.service.shortening;

import com.nanolink.nanolink_service.domain.model.Url;
import com.nanolink.nanolink_service.domain.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final Base62RandomIdGeneratorService base62RandomIdGeneratorService;

    public UrlShortenerService(UrlRepository urlRepository, Base62RandomIdGeneratorService base62RandomIdGeneratorService) {
        this.urlRepository = urlRepository;
        this.base62RandomIdGeneratorService = base62RandomIdGeneratorService;
    }

    public Optional<String> getOriginalUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).map(Url::getOriginalUrl);
    }

    public Url shorten(String originalUrl) {
        String shortCode = base62RandomIdGeneratorService.generate();

        Url newUrl = new Url(originalUrl, shortCode);

        return urlRepository.save(newUrl);
    }
}
