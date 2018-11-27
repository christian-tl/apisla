package com.dangdang.common;

public class StackApiException extends DangBaseException{

	public StackApiException() {
        super();
    }

    public StackApiException(String message) {
        super(message);
    }

    public StackApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public StackApiException(Throwable cause) {
        super(cause);
    }
}
