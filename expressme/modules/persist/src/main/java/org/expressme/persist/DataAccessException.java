package org.expressme.persist;

/**
 * Indicate a general SQL exception when access JDBC APIs.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

}
