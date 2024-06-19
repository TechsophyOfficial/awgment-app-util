package com.techsophy.tsf.util.repository;

import com.techsophy.tsf.util.entity.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<UrlMapping, String> {
    UrlMapping findByShortPath(String shortPath);
    boolean existsByShortPath(String shortPath);
}