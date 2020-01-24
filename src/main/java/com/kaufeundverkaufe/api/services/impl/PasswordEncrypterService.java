package com.kaufeundverkaufe.api.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncrypterService {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encodePassword(String password){
        return encoder.encode(password);
    }

}
