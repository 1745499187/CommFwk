package com.chinawiserv.fwk.comm.sms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.chinawiserv.fwk.session.CWSessionEventListener;

public class CWSmsSessionManagerImpl implements CWSmsSessionManager {
	private int currSessionId = 0;
	
	private final CWSessionEventListener sessionListener = new CWSmsSessionEventListener();
	private Map<Integer, CWSmsSession> sessionHolder = new ConcurrentHashMap<Integer, CWSmsSession>();
	
	
	public CWSmsSession openSession() {
		CWSmsSession session = new CWSmsSession(this.getNextSessionId());
		session.setSessionHolder(this.sessionHolder);
		session.setListener(this.sessionListener);
		sessionHolder.put(session.getSessionId(), session);
		return session;
	}
	
	private synchronized int getNextSessionId() {
		return ++ this.currSessionId;
	}
}
