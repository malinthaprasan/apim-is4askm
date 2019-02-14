package org.wso2.apimgt.keymgt.is4.exception;

public class IntrospectionAPIException extends Exception {

    public IntrospectionAPIException(String msg) {
        super(msg);
    }

    public IntrospectionAPIException(String msg, Throwable e) {
        super(msg, e);
    }
}
