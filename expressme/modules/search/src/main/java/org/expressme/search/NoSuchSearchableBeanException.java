package org.expressme.search;

/**
 * Indicate such bean is not registered and cannot be searched.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class NoSuchSearchableBeanException extends SearchException {

    private final Class<?> clazz;

    public Class<?> getUnsearchableClass() {
        return clazz;
    }

    public NoSuchSearchableBeanException(Class<?> clazz) {
        super(clazz.toString());
        this.clazz = clazz;
    }

    public NoSuchSearchableBeanException(Class<?> clazz, String message, Throwable cause) {
        super(message, cause);
        this.clazz = clazz;
    }

    public NoSuchSearchableBeanException(Class<?> clazz, String message) {
        super(message);
        this.clazz = clazz;
    }

    public NoSuchSearchableBeanException(Class<?> clazz, Throwable cause) {
        super(cause);
        this.clazz = clazz;
    }

}
