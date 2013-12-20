package com.chinawiserv.fwk.comm.sms;

import java.util.Map;

import com.chinawiserv.fwk.comm.sms.impl.CWSmsCclImpl;
import com.chinawiserv.fwk.comm.sms.typedef.ESmsGatewayType;
import com.chinawiserv.fwk.session.CWAbstractSession;

public class CWSmsSession extends CWAbstractSession {
	private int sessionId = -9999;
	
	private Map<Integer, CWSmsSession> sessionHolder = null;
	
	public CWSmsSession(int sessionId) {
		this.sessionId = sessionId;
	}

	
	@Deprecated
	public void write(Object message) {
	}

	
	@Deprecated
	public Object read() {
		return null;
	}

	
	@Deprecated
	public Object read(int timeout) {
		return null;
	}

	
	public boolean close() {
		this.clearAttributes();
		this.sessionHolder.remove(this.sessionId);
		return true;
	}
	
	public int getSessionId() {
		return this.sessionId;
	}

	/**
	 * @return the sessionHolder
	 */
	public Map<Integer, CWSmsSession> getSessionHolder() {
		return sessionHolder;
	}

	/**
	 * @param sessionHolder the sessionHolder to set
	 */
	public void setSessionHolder(Map<Integer, CWSmsSession> sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public void sendSms(String mobile, String message, ESmsGatewayType gateway) {
		CWSmsImpl smsImpl = CWSmsCclImpl.getInstance();
		smsImpl.sendSms(mobile, message, gateway);
	}
	
}
