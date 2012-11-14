package org.expressme.persist;

/**
 * When configuration of DAO interface has something wrong.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@SuppressWarnings("serial")
public class DaoConfigException extends RuntimeException {

    public DaoConfigException() {
        super();
    }

    public DaoConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoConfigException(String message) {
        super(message);
    }

    public DaoConfigException(Throwable cause) {
        super(cause);
    }

}
