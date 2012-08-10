package org.expressme.simplejdbc;

/**
 * Exception when db error.
 * 
 * @author Michael Liao
 */
@SuppressWarnings("serial")
public class DbException extends RuntimeException {

    public DbException() {
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

}
