package org.expressme.modules.web;

/**
 * Validate failed.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ValidateException extends ApplicationException {

    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

}
