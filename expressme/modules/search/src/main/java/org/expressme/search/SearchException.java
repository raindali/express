package org.expressme.search;

/**
 * Root exception for all search exceptions.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SearchException extends RuntimeException {

    public SearchException() {
        super();
    }

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchException(String message) {
        super(message);
    }

    public SearchException(Throwable cause) {
        super(cause);
    }

}
