package com.wso2.services.apim.extension.exception;

public class TokenAPIException extends Exception {

    public TokenAPIException(String msg) {
        super(msg);
    }

    public TokenAPIException(String msg, Throwable e) {
        super(msg, e);
    }
}
