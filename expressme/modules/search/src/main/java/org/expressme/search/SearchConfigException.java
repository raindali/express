package org.expressme.search;

/**
 * Configuration exception if annotation is not correct set.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SearchConfigException extends SearchException {

    public SearchConfigException() {
    }

    public SearchConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchConfigException(String message) {
        super(message);
    }

    public SearchConfigException(Throwable cause) {
        super(cause);
    }

}
