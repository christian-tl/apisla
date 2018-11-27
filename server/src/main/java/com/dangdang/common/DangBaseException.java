package com.dangdang.common;

public class DangBaseException extends Exception{

	
	public DangBaseException() {
        super();
    }

    public DangBaseException(String message) {
        super(message);
    }

    public DangBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DangBaseException(Throwable cause) {
        super(cause);
    }
}
