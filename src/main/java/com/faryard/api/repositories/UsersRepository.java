package com.faryard.api.repositories;

import com.faryard.api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}
