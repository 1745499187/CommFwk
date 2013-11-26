package com.chinawiserv.fwk.comm.tcp;

public class CWTcpException extends Exception {
	
	/** The serialVersionUID */
	private static final long serialVersionUID = 1684533979533111283L;

	public CWTcpException(String msg) {
		super(msg);
	}
	
	public CWTcpException(String msg, Throwable t) {
		super(msg, t);
	}
}
