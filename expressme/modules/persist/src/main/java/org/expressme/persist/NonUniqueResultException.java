package org.expressme.persist;

/**
 * Indicate a select SQL returns empty ResultSet when query by a specified 
 * primary key.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class NonUniqueResultException extends DataAccessException {

    public NonUniqueResultException() {
        super();
    }

    public NonUniqueResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonUniqueResultException(String message) {
        super(message);
    }

    public NonUniqueResultException(Throwable cause) {
        super(cause);
    }

}
