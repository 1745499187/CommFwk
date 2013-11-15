package com.chinawiserv.service.alertserver.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession;
import com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver.typedef.ASTcpMsg;
import com.chinawiserv.service.alertserver.auth.AuthUser;

public class ASTcpServerHandler implements CWTcpHandler {
	private final static Logger logger = LoggerFactory.getLogger(ASTcpServerHandler.class);
	
	private ASTcpServerSessionManager sessionMgr;
	
	public ASTcpServerHandler(ASTcpServerSessionManager sessionMgr) {
		this.sessionMgr = sessionMgr;
	}
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#messageReceived(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(CWTcpSocketSession session, Object message) {
		if(message instanceof ASTcpMsg) {
			ASTcpMsg msg = (ASTcpMsg)message;
			AuthUser authUser = new AuthUser();
			if(authUser.auth("", msg.getContent())) {
				sessionMgr.put("", session);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#messageSent(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Object)
	 */
	@Override
	public void messageSent(CWTcpSocketSession session, Object message) {
	}

}
