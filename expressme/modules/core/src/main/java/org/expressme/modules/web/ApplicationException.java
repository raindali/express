package org.expressme.modules.web;

/**
 * Root excepion of exception hierarchy.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

}
