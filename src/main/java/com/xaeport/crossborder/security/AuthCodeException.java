package com.xaeport.crossborder.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by xcp on 2017/8/1.
 */
public class AuthCodeException extends AuthenticationException {
    public AuthCodeException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? message : s;
    }
}
