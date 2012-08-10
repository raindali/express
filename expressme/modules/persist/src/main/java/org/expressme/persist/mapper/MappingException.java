package org.expressme.persist.mapper;

import org.expressme.persist.DataAccessException;

public class MappingException extends DataAccessException {

    public MappingException() {
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

}
