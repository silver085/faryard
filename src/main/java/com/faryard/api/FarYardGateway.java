package com.faryard.api;

import com.faryard.api.domain.User;
import com.faryard.api.domain.Role;
import com.faryard.api.domain.Roles;
import com.faryard.api.repositories.RoleRepository;
import com.faryard.api.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
@Slf4j
@SpringBootApplication
public class FarYardGateway {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {

		SpringApplication.run(FarYardGateway.class, args);

        //log.info("Application KUV started!");
	}
    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UsersRepository usersRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole(Roles.ADMIN.getRole());
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole(Roles.ADMIN.getRole());
                roleRepository.save(newAdminRole);
                adminRole = newAdminRole;
            }

            Role userRole = roleRepository.findByRole(Roles.USER.getRole());
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole(Roles.USER.getRole());
                roleRepository.save(newUserRole);
                userRole = newUserRole;
            }
            Optional<User> adminUser = usersRepository.findByEmail("admin@faryard.com");
            if(!adminUser.isPresent()){
                bCryptPasswordEncoder = new BCryptPasswordEncoder();
                User newAdmin = new User();
                newAdmin.setEmail("admin@faryard.com");
                String pass = bCryptPasswordEncoder.encode("ciccio");
                newAdmin.setPassword(pass);
                newAdmin.setEnabled(true);
                newAdmin.setName("ADMIN");
                List<Role> userRoles = new ArrayList<>();
                userRoles.add(adminRole);
                userRoles.add(userRole);
                newAdmin.setRoles(userRoles);

                usersRepository.save(newAdmin);
            }
        };
    }
}
