package com.kaufeundverkaufe.api.repositories;


import com.kaufeundverkaufe.api.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(String role);
}
