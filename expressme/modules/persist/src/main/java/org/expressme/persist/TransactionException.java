package org.expressme.persist;

/**
 * When transaction failed.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class TransactionException extends DataAccessException {

    public TransactionException() {
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

}
