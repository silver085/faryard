package com.faryard.api.configurators;

import com.faryard.api.configurators.authexceptions.FaryardAuthenticationException;
import com.faryard.api.domain.Roles;
import com.faryard.api.domain.User;
import com.faryard.api.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserAndPassAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UsersRepository usersRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<User> user = usersRepository.findByEmail(email);
        if(user.isPresent()){
            if(encoder.matches(password,user.get().getPassword())) {
                return new UsernamePasswordAuthenticationToken(user, password, new ArrayList<>());
            }
        }

        throw new FaryardAuthenticationException("Username or password not valid");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
