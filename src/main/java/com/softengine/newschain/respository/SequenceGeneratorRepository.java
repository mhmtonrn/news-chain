package com.softengine.newschain.respository;

import com.softengine.newschain.models.entity.DatabaseSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceGeneratorRepository extends MongoRepository<DatabaseSequence,String> {

}
