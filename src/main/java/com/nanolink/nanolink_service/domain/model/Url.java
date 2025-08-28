package com.nanolink.nanolink_service.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urls")
public class Url {
    @Id
    private String id;

    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private Long numberOfClicks;

    public Url(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdAt = LocalDateTime.now();
        this.numberOfClicks = 0L;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getNumberOfClicks() {
        return numberOfClicks; }

    public void setNumberOfClicks(Long numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }
}
