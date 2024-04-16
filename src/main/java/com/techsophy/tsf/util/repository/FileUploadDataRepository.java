package com.techsophy.tsf.util.repository;

import com.techsophy.tsf.util.entity.FileDataDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface FileUploadDataRepository extends MongoRepository<FileDataDefinition, Long> {

  @Query("{'userData.mobileNumber': ?0, 'userData.empId': ?1}")
  FileDataDefinition findByMobileNumberOrEmpId(String mobileNumber, String empId);

  Optional<FileDataDefinition> findById(BigInteger id);


}
