package org.expressme.search;

/**
 * Indicate an index problem such as index failed.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class IndexException extends SearchException {

    public IndexException() {
        super();
    }

    public IndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexException(String message) {
        super(message);
    }

    public IndexException(Throwable cause) {
        super(cause);
    }

}
