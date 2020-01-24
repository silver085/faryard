package com.kaufeundverkaufe.api.repositories;

import com.kaufeundverkaufe.api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}
