package com.techsophy.tsf.util.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import static com.techsophy.tsf.util.constants.PropertiesConstants.CREATE_OR_ALL_ACCESS;

@RequestMapping("/shrt")
public interface UrlShortener {

    @PostMapping("/shorten")
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    String generateShortUrl(@RequestBody String longUrl);

    @GetMapping("/{shortPath}")
    ResponseEntity<?> redirectUrl(@PathVariable String shortPath);
}
