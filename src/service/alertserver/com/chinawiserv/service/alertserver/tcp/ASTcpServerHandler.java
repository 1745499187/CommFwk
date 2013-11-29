package com.chinawiserv.service.alertserver.tcp;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.CWTcpException;
import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession;
import com.chinawiserv.fwk.comm.tcp.buffer.CWIoBuffer;
import com.chinawiserv.fwk.comm.tcp.mina.EMinaDecodeState;
import com.chinawiserv.fwk.constant.CWCharset;
import com.chinawiserv.service.alertserver.auth.AuthUser;
import com.chinawiserv.service.alertserver.constant.ASSessionAttrKeyConstant;
import com.chinawiserv.service.alertserver.constant.EASLoginStatus;
import com.chinawiserv.service.alertserver.typedef.ASMsg;
import com.chinawiserv.service.alertserver.util.SecurityUtil;
import com.chinawiserv.service.alertserver.util.StringUtil;

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
		try {
			if(message instanceof CWIoBuffer) {
				this.decodeCWIoBuffer(session, (CWIoBuffer)message);
			}
		} catch(Exception e) {
			logger.error("Error when messageReceived()", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#messageSent(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Object)
	 */
	@Override
	public void messageSent(CWTcpSocketSession session, Object message) {
		logger.debug("Sent msg: " + message);
	}

	protected void decodeCWIoBuffer(CWTcpSocketSession session, CWIoBuffer in) throws CharacterCodingException, CWTcpException {
		EMinaDecodeState state = (EMinaDecodeState)session.getAttribute(ASSessionAttrKeyConstant.DECODING.ATT_DECODE_STATE);
		if(state == null) {
			session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_DECODE_STATE, EMinaDecodeState.NEW);
			state = (EMinaDecodeState)session.getAttribute(ASSessionAttrKeyConstant.DECODING.ATT_DECODE_STATE);
		}
		
		CWIoBuffer buf = in;
		String strTmp = null;
		int intTmp = 0;
		byte byteTmp = (byte)0;
		switch(state) {
		case HEAD:
			// grab the stored a partial HEAD request
            final ByteBuffer oldBuffer = (ByteBuffer)session.getAttribute(ASSessionAttrKeyConstant.DECODING.ATT_PARTIAL_MSG);
            // concat the old buffer and the new incoming one
            buf = CWIoBuffer.allocate(oldBuffer.remaining() + buf.remaining()).put(oldBuffer).put(buf).flip();
            session.removeAttribute(ASSessionAttrKeyConstant.DECODING.ATT_PARTIAL_MSG);
            // now let's decode like it was a new message
		case NEW:
			// need more byte to proceed: "wdp"(3)+1(1)+msg_length(4)
			if(buf.remaining() < 8) {
				session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_DECODE_STATE, EMinaDecodeState.HEAD);
				
				final ByteBuffer partial = ByteBuffer.allocate(buf.remaining());
                partial.put(buf.buf());
                partial.flip();
                // no request decoded, we accumulate
                session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_PARTIAL_MSG, partial);
			}
			else {
				session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_DECODE_STATE, EMinaDecodeState.MSG);
				
				strTmp = buf.getString(4, CWCharset.UTF_8.DECODER); // should be "0001"
				intTmp = buf.getInt(); // msg length
				session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_MSG_LENGTH, new Integer(intTmp));
			}
			break;
		case MSG_IMCOMPLETE:
			// grab the stored a partial HEAD request
            final ByteBuffer oldMsgBuffer = (ByteBuffer)session.getAttribute(ASSessionAttrKeyConstant.DECODING.ATT_PARTIAL_MSG);
            // concat the old buffer and the new incoming one
            buf = CWIoBuffer.allocate(oldMsgBuffer.remaining() + buf.remaining()).put(oldMsgBuffer).put(buf).flip();
            session.removeAttribute(ASSessionAttrKeyConstant.DECODING.ATT_PARTIAL_MSG);
            // now let's decode like it was a new message
		case MSG:
			int msgLen = ((Integer)session.getAttribute(ASSessionAttrKeyConstant.DECODING.ATT_MSG_LENGTH)).intValue();
			if(buf.remaining() < msgLen) {
				session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_DECODE_STATE, EMinaDecodeState.MSG_IMCOMPLETE);
				
				final ByteBuffer partial = ByteBuffer.allocate(buf.remaining());
                partial.put(buf.buf());
                partial.flip();
                // no request decoded, we accumulate
                session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_PARTIAL_MSG, partial);
				// need to break;
			}
			else {
				session.setAttribute(ASSessionAttrKeyConstant.DECODING.ATT_DECODE_STATE, EMinaDecodeState.NEW);
				
				try{
					// start message decoding
					String str = buf.getString(msgLen, CWCharset.GBK.DECODER);
					ASMsg msg = new ASMsg(str);
					this.processASMsg(session, msg);
					
				} catch(Exception e) {
					throw new CWTcpException("Decode msg fail", e);
				}
			}
			break;
		default:
			break;
		}
		
		if(buf.hasRemaining()) {
			this.decodeCWIoBuffer(session, buf);
		}
	}
	
	protected void processASMsg(CWTcpSocketSession session, ASMsg msg) throws Exception {
		logger.debug("Received ASMsg: " + msg);
		
		// check whether has login
		EASLoginStatus login = (EASLoginStatus)session.getAttribute(ASSessionAttrKeyConstant.SECURITY.LOGIN_STATUS);
		if(login == null || !login.equals(EASLoginStatus.LOGIN)) { // not login
			String desKey = (String)session.getAttribute(ASSessionAttrKeyConstant.SECURITY.LOGIN_DES_KEY);
			
			byte[] loginData = SecurityUtil.desDecrypt(desKey, StringUtil.hexStringToBytes(msg.getContent()));
            String authString = new String(loginData);
            String[] strArrTmp = authString.split(":");
            String username = strArrTmp[0];
            String password = strArrTmp[1];
			
			AuthUser authUser = new AuthUser();
			if(authUser.auth(username, password)) {
				sessionMgr.put(username, session);
				
				session.setAttribute(ASSessionAttrKeyConstant.SECURITY.LOGIN_STATUS, EASLoginStatus.LOGIN);
				session.setAttribute(ASSessionAttrKeyConstant.SECURITY.USER_NAME, username);
			}
			else {
				session.setAttribute(ASSessionAttrKeyConstant.SECURITY.LOGIN_STATUS, EASLoginStatus.LOGIN_FAIL);
			}
		}
		else { // normal msg transmition
			
		}
	}
	
	protected void sendASMsg(CWTcpSocketSession session, ASMsg msg) throws CharacterCodingException {
		logger.debug("Sent ASMsg: " + msg);
		
		session.write(msg.toBuffer());
	}
}
