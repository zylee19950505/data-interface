package com.xaeport.crossborder.security;

import org.springframework.security.authentication.BadCredentialsException;

public class SwBadCredentialsException extends BadCredentialsException {

    public SwBadCredentialsException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? message : s;
    }
}
