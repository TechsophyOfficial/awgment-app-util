package com.techsophy.tsf.util.repository;

import com.techsophy.tsf.util.entity.PropertiesDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesDefinitionRepository extends  MongoRepository<PropertiesDefinition, Long>, PropertiesDefinitionCustomRepository
{

}
