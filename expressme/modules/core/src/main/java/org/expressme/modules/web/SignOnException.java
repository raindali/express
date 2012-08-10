package org.expressme.modules.web;


/**
 * When identity is null or invalid.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SignOnException extends ApplicationException {

    public SignOnException() {
        super();
    }

    public SignOnException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignOnException(String message) {
        super(message);
    }

    public SignOnException(Throwable cause) {
        super(cause);
    }

}
