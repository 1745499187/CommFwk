package com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver;

public class AlertServerCodecException extends RuntimeException {
	private static final long serialVersionUID = -487495931484111559L;

	public AlertServerCodecException() {
		super();
	}
	
	public AlertServerCodecException(String msg) {
		super(msg);
	}
	
	public AlertServerCodecException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public AlertServerCodecException(Throwable cause) {
		super(cause);
	}
}
