package com.nanolink.nanolink_service.domain.repository;

import com.nanolink.nanolink_service.domain.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByShortUrl(String shortUrl);
}
