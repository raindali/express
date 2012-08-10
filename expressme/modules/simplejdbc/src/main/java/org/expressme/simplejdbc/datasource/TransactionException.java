package org.expressme.simplejdbc.datasource;

import org.expressme.simplejdbc.DbException;

/**
 * When transaction failed.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@SuppressWarnings("serial")
public class TransactionException extends DbException {

    public TransactionException() {
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

}
