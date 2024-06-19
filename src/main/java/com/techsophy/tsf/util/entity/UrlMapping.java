package com.techsophy.tsf.util.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document("urlMappings")
public class UrlMapping {

    @Id
    private String id;

    private String shortPath;
    private String longUrl;
    private long expiresAt;

    public UrlMapping(String shortPath, String longUrl, long expiresAt) {
        this.shortPath = shortPath;
        this.longUrl = longUrl;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }

}
