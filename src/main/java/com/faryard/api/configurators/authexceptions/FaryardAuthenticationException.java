package com.faryard.api.configurators.authexceptions;

import org.springframework.security.core.AuthenticationException;

public class FaryardAuthenticationException extends AuthenticationException {

    public FaryardAuthenticationException(String msg) {
        super(msg);

    }
}
