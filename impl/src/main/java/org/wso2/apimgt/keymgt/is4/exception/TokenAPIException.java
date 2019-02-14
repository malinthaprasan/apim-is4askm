package org.wso2.apimgt.keymgt.is4.exception;

public class TokenAPIException extends Exception {

    public TokenAPIException(String msg) {
        super(msg);
    }

    public TokenAPIException(String msg, Throwable e) {
        super(msg, e);
    }
}
