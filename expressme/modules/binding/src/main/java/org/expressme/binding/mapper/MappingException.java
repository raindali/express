package org.expressme.binding.mapper;


@SuppressWarnings("serial")
public class MappingException extends RuntimeException {

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
