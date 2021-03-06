package com.softengine.newschain.respository;

import com.softengine.newschain.models.entity.Author;
import com.softengine.newschain.models.types.AuthorStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository<Author, Integer> {

    Optional<List<Author>> findByName(String name);
    Optional<List<Author>> findByAuthorStatus(AuthorStatus authorStatus);
}
