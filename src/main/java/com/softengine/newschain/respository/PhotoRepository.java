package com.softengine.newschain.respository;

import com.softengine.newschain.models.dto.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}
