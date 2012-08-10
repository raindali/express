package org.expressme.persist;

/**
 * Indicate a select SQL returns empty ResultSet when query by a specified 
 * primary key.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ResourceNotFoundException extends DataAccessException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

}
