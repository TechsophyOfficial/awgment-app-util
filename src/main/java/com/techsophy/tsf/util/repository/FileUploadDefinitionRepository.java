package com.techsophy.tsf.util.repository;

import com.techsophy.tsf.util.entity.FileUploadDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

import static com.techsophy.tsf.util.constants.FileUploadConstants.FIND_ALL_BY_ID_QUERY;

@Repository
public interface FileUploadDefinitionRepository extends MongoRepository<FileUploadDefinition, Long>
{

    Optional<FileUploadDefinition> findById(BigInteger id);
    boolean existsById(BigInteger id);
    int deleteById(BigInteger id);
    @Query(FIND_ALL_BY_ID_QUERY)
    Optional<Stream<FileUploadDefinition>> findAllByDocumentId(BigInteger documentId, Sort sort);
    @Query(FIND_ALL_BY_ID_QUERY)
    Optional<Page<FileUploadDefinition>> findAllByDocumentId(BigInteger documentId, Pageable pageable);
    @Query(value = FIND_ALL_BY_ID_QUERY,delete = true)
    void deleteAll(BigInteger documentId);
}
