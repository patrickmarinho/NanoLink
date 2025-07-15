package com.nanolink.nanolink_service.repository;

import com.nanolink.nanolink_service.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByShortUrl(String shortUrl);
}
