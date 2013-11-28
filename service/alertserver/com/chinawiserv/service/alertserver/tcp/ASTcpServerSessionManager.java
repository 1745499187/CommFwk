package com.chinawiserv.service.alertserver.tcp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.chinawiserv.fwk.session.CWSession;

public class ASTcpServerSessionManager {
	private ConcurrentMap<String, CWSession> sessionHolder = new ConcurrentHashMap<String, CWSession>();
	
	public void put(String key, CWSession session) {
		sessionHolder.put(key, session);
	}
	
	public CWSession get(String key) {
		return sessionHolder.get(key);
	}
	
	public CWSession remove(String key) {
		return sessionHolder.remove(key);
	}
	
	public int size() {
		return sessionHolder.size();
	}
	
	public void clear() {
		sessionHolder.clear();
	}
	
	public boolean containsKey(String key) {
		return sessionHolder.containsKey(key);
	}
	
	public boolean containsValue(CWSession session) {
		return sessionHolder.containsValue(session);
	}
}
