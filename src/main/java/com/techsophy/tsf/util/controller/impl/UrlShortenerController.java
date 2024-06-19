package com.techsophy.tsf.util.controller.impl;

import com.techsophy.tsf.util.controller.UrlShortener;
import com.techsophy.tsf.util.entity.UrlMapping;
import com.techsophy.tsf.util.service.impl.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class UrlShortenerController implements UrlShortener {

    @Value("${gateway.uri}")
    String gatewayUri;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Override
    public String generateShortUrl(String longUrl) {
        String shortPath = urlShortenerService.shortenUrl(longUrl);
        return gatewayUri + "shrt/" + shortPath;
    }

    @Override
    public ResponseEntity<?> redirectUrl(String shortPath) {
        UrlMapping urlMapping = urlShortenerService.getLongUrl(shortPath);
        if(urlMapping == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else if(!urlMapping.isExpired()) {
            URI longUrl = UriComponentsBuilder.fromHttpUrl(urlMapping.getLongUrl())
                    .build()
                    .toUri();
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(longUrl)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.GONE)
                    .body("Short URL has expired");
        }
    }
}

