package com.faryard.api.services.impl.exceptions;

public class NodeNotFoundException extends RuntimeException{
    public NodeNotFoundException(String message) {
        super(message);
    }
}
