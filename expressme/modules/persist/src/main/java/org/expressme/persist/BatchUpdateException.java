package org.expressme.persist;

/**
 * Indicate a batch update failure.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BatchUpdateException extends UpdateException {

    public BatchUpdateException() {
        super();
    }

    public BatchUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BatchUpdateException(String message) {
        super(message);
    }

    public BatchUpdateException(Throwable cause) {
        super(cause);
    }

}
