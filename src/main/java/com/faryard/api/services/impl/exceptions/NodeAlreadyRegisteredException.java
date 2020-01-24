package com.faryard.api.services.impl.exceptions;

public class NodeAlreadyRegisteredException extends RuntimeException {
    public NodeAlreadyRegisteredException(String message) {
        super(message);
    }
}
