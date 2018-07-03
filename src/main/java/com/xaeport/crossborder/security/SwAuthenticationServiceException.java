package com.xaeport.crossborder.security;

import org.springframework.security.authentication.AuthenticationServiceException;

public class SwAuthenticationServiceException extends AuthenticationServiceException {

    public SwAuthenticationServiceException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? message : s;
    }
}
