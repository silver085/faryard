package com.kaufeundverkaufe.api.controllers;

import com.kaufeundverkaufe.api.DTO.AuthBody;
import com.kaufeundverkaufe.api.DTO.AuthenticationResponse;
import com.kaufeundverkaufe.api.DTO.UserRegistration;
import com.kaufeundverkaufe.api.repositories.UsersRepository;
import com.kaufeundverkaufe.api.services.impl.JwtTokenProvider;
import com.kaufeundverkaufe.api.services.impl.MongoUserService;
import com.kaufeundverkaufe.api.services.impl.exceptions.UserAlreadyExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private MongoUserService userService;
    @Autowired
    MessageSource messageSource;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try{
            String username = data.getEmail();
            String password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            String token = jwtTokenProvider.createToken(username,  usersRepository.findByEmail(username).get().getRoles());
            AuthenticationResponse response = new AuthenticationResponse();
            response.setEmail(username);
            response.setUsername(username);
            response.setToken(token);
            response.setWelcomeMsg(messageSource.getMessage("auth.welcomeMsg" , null,Locale.forLanguageTag(LocaleContextHolder.getLocale().getLanguage())));
            return ok(response);

        }catch (AuthenticationException e){
            throw new BadCredentialsException(messageSource.getMessage("invalid.auth", null, Locale.forLanguageTag(LocaleContextHolder.getLocale().getLanguage())));
        }
    }

    @PostMapping ResponseEntity register(@RequestBody UserRegistration userRequest){
        try {
            userService.registerNewUser(userRequest);
        }catch (UserAlreadyExistingException e){

        }

        return null;
    }
}
