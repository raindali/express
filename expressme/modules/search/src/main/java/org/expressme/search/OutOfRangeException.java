package org.expressme.search;

/**
 * Specified page is out of search range.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class OutOfRangeException extends SearchException {

    public OutOfRangeException() {
    }

    public OutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfRangeException(String message) {
        super(message);
    }

    public OutOfRangeException(Throwable cause) {
        super(cause);
    }

}
