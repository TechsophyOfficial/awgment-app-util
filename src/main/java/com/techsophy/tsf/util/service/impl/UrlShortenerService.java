package com.techsophy.tsf.util.service.impl;

import com.techsophy.tsf.util.entity.UrlMapping;
import com.techsophy.tsf.util.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    @Value("${url.shortener.expiry.minutes}")
    private long expiryMinutes;

    public String shortenUrl(String longUrl) {
        String shortPath = generateShortPath();
        while (urlRepository.existsByShortPath(shortPath)) {
            shortPath = generateShortPath();
        }
        UrlMapping urlMapping = new UrlMapping(shortPath, longUrl, System.currentTimeMillis() + (expiryMinutes * 60 * 1000));
        urlRepository.save(urlMapping);
        return shortPath;
    }

    public UrlMapping getLongUrl(String shortPath) {
        UrlMapping urlMapping = urlRepository.findByShortPath(shortPath);
        return urlMapping;
    }

    private String generateShortPath() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortPath = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            shortPath.append(characters.charAt(index));
        }
        return shortPath.toString();
    }

}
