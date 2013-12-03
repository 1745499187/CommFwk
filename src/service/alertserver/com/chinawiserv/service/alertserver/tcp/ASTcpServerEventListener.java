package com.chinawiserv.service.alertserver.tcp;

import java.nio.charset.CharacterCodingException;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.core.CWException;
import com.chinawiserv.fwk.session.CWAbstractSessionEventListener;
import com.chinawiserv.fwk.session.CWSession;
import com.chinawiserv.service.alertserver.constant.ASSessionAttrKeyConstant;
import com.chinawiserv.service.alertserver.typedef.ASMsg;

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
		String userName = (String)session.getAttribute(ASSessionAttrKeyConstant.SECURITY.USER_NAME);
		if(userName != null) {
			sessionMgr.remove(userName);
			
			logger.info("Client ["+userName+"] has disconnected");
		}
		
		// clean all attr at the last
		session.clearAttributes();
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
        
        ASMsg msg = new ASMsg(jsonObj.toString());
        
        try {
        	session.setAttribute(ASSessionAttrKeyConstant.SECURITY.LOGIN_DES_KEY, randnum);
			session.write(msg.toBuffer());
		} catch (CharacterCodingException e) {
			throw new CWException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#exceptionCaught(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(CWSession session, Throwable cause) {
	}
}
