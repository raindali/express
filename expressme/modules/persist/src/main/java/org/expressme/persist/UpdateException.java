package org.expressme.persist;

/**
 * Indicate an update, insert, or delete SQL failure.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class UpdateException extends DataAccessException {

    public UpdateException() {
        super();
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }

}
