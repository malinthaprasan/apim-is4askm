package com.wso2.services.apim.extension.exception;

public class IntrospectionAPIException extends Exception {

    public IntrospectionAPIException(String msg) {
        super(msg);
    }

    public IntrospectionAPIException(String msg, Throwable e) {
        super(msg, e);
    }
}
