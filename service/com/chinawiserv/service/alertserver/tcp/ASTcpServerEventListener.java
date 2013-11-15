package com.chinawiserv.service.alertserver.tcp;

import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession;
import com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver.typedef.ASTcpMsg;
import com.chinawiserv.fwk.constant.CWCharset;
import com.chinawiserv.fwk.core.CWException;
import com.chinawiserv.fwk.session.CWAbstractSessionEventListener;
import com.chinawiserv.fwk.session.CWSession;

public class ASTcpServerEventListener extends CWAbstractSessionEventListener {
	private final static Logger logger = LoggerFactory.getLogger(ASTcpServerEventListener.class);
	
	private ASTcpServerSessionManager sessionMgr;
	
	public ASTcpServerEventListener(ASTcpServerSessionManager sessionMgr) {
		this.sessionMgr = sessionMgr;
	}
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#sessionCreated(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession)
	 */
	@Override
	public void sessionCreated(CWSession session) {
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#sessionClosed(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession)
	 */
	@Override
	public void sessionClosed(CWSession session) throws CWException {
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#sessionOpened(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession)
	 */
	@Override
	public void sessionOpened(CWSession session) throws CWException {
		String randnum =  UUID.randomUUID().toString().substring(0, 24);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("infotype", 256);
        jsonObj.put("randnum", randnum);
        
        session.write(new ASTcpMsg(jsonObj.toString()));
        
        //ASTcpMsg reply = (ASTcpMsg)session.read();
        //logger.debug("Got reply: "+reply);
//        
//        if(reply == null) {
//        	logger.warn("got problem");
//        	session.close();
//        	return;
//        }
//        else {
//        	reply.getContent();
//        	sessionMgr.put("", session);
//        }
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#exceptionCaught(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(CWSession session, Throwable cause) {
	}
}
