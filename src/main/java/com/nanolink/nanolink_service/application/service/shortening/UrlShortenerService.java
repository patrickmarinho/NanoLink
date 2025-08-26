package com.nanolink.nanolink_service.application.service.shortening;

import com.nanolink.nanolink_service.domain.model.Url;
import com.nanolink.nanolink_service.domain.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final IdGeneratorService idGeneratorService;

    public UrlShortenerService(UrlRepository urlRepository, IdGeneratorService idGeneratorService) {
        this.urlRepository = urlRepository;
        this.idGeneratorService = idGeneratorService;
    }

    public Url shorten(String originalUrl) {
        String shortCode = idGeneratorService.generate();

        Url newUrl = new Url(originalUrl, shortCode);

        return urlRepository.save(newUrl);
    }

    public Optional<String> getOriginalUrl(String shortUrl) {
        Long count = 0L;
        return urlRepository.findByShortUrl(shortUrl).map(url -> {
            url.setNumberOfClicks(url.getNumberOfClicks() + 1);
            urlRepository.save(url);
            return url.getOriginalUrl();
        });
    }

    public Optional<Long> getUrlClickCount(String shortUrl){
        return urlRepository.findByShortUrl(shortUrl).map(Url::getNumberOfClicks);
    }
}
