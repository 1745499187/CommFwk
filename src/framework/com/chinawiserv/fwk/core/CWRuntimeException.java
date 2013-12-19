package com.chinawiserv.fwk.core;

public class CWRuntimeException extends RuntimeException {

	/** The serialVersionUID */
	private static final long serialVersionUID = 8007145301298931092L;

	public CWRuntimeException(String msg) {
		super(msg);
	}
	
	public CWRuntimeException(Throwable t) {
		super(t);
	}
	
	public CWRuntimeException(String msg, Throwable t) {
		super(msg, t);
	}
}
