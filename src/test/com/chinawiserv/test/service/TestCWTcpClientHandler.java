package com.chinawiserv.test.service;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.CWTcpException;
import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession;
import com.chinawiserv.fwk.comm.tcp.buffer.CWIoBuffer;
import com.chinawiserv.fwk.comm.tcp.mina.EMinaDecodeState;
import com.chinawiserv.fwk.constant.CWCharset;
import com.chinawiserv.service.alertserver.auth.AuthUser;
import com.chinawiserv.service.alertserver.typedef.ASMsg;
import com.chinawiserv.service.alertserver.util.SecurityUtil;
import com.chinawiserv.service.alertserver.util.StringUtil;

public class TestCWTcpClientHandler implements CWTcpHandler {
	private final static Logger logger = LoggerFactory.getLogger(TestCWTcpClientHandler.class);
	
	private final static String ATT_DECODE_STATE = "status.ds";
	private final static String ATT_PARTIAL_MSG = "status.pm";
	private final static String ATT_MSG_LENGTH = "status.ml";
	
	private final static String LOGIN_STATUS = "status.login";
	
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
	}

	protected void decodeCWIoBuffer(CWTcpSocketSession session, CWIoBuffer in) throws CharacterCodingException, CWTcpException {
		EMinaDecodeState state = (EMinaDecodeState)session.getAttribute(ATT_DECODE_STATE);
		if(state == null) {
			session.setAttribute(ATT_DECODE_STATE, EMinaDecodeState.NEW);
			state = (EMinaDecodeState)session.getAttribute(ATT_DECODE_STATE);
		}
		
		CWIoBuffer buf = in;
		String strTmp = null;
		int intTmp = 0;
		byte byteTmp = (byte)0;
		switch(state) {
		case HEAD:
			// grab the stored a partial HEAD request
            final ByteBuffer oldBuffer = (ByteBuffer)session.getAttribute(ATT_PARTIAL_MSG);
            // concat the old buffer and the new incoming one
            buf = CWIoBuffer.allocate(oldBuffer.remaining() + buf.remaining()).put(oldBuffer).put(buf).flip();
            session.removeAttribute(ATT_PARTIAL_MSG);
            // now let's decode like it was a new message
		case NEW:
			// need more byte to proceed: "wdp"(3)+1(1)+msg_length(4)
			if(buf.remaining() < 8) {
				session.setAttribute(ATT_DECODE_STATE, EMinaDecodeState.HEAD);
				
				final ByteBuffer partial = ByteBuffer.allocate(buf.remaining());
                partial.put(buf.buf());
                partial.flip();
                // no request decoded, we accumulate
                session.setAttribute(ATT_PARTIAL_MSG, partial);
			}
			else {
				session.setAttribute(ATT_DECODE_STATE, EMinaDecodeState.MSG);
				
				strTmp = buf.getString(4, CWCharset.UTF_8.DECODER); // should be "0001"
				intTmp = buf.getInt(); // msg length
				session.setAttribute(ATT_MSG_LENGTH, new Integer(intTmp));
			}
			break;
		case MSG_IMCOMPLETE:
			// grab the stored a partial HEAD request
            final ByteBuffer oldMsgBuffer = (ByteBuffer)session.getAttribute(ATT_PARTIAL_MSG);
            // concat the old buffer and the new incoming one
            buf = CWIoBuffer.allocate(oldMsgBuffer.remaining() + buf.remaining()).put(oldMsgBuffer).put(buf).flip();
            session.removeAttribute(ATT_PARTIAL_MSG);
            // now let's decode like it was a new message
		case MSG:
			int msgLen = ((Integer)session.getAttribute(ATT_MSG_LENGTH)).intValue();
			if(buf.remaining() < msgLen) {
				session.setAttribute(ATT_DECODE_STATE, EMinaDecodeState.MSG_IMCOMPLETE);
				
				final ByteBuffer partial = ByteBuffer.allocate(buf.remaining());
                partial.put(buf.buf());
                partial.flip();
                // no request decoded, we accumulate
                session.setAttribute(ATT_PARTIAL_MSG, partial);
				// need to break;
			}
			else {
				session.setAttribute(ATT_DECODE_STATE, EMinaDecodeState.NEW);
				
				try{
					// start message decoding
					String str = buf.getString(msgLen, CWCharset.GBK.DECODER);
					ASMsg msg = new ASMsg(str);
					this.processASTcpMsg(session, msg);
					
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
	
	protected void processASTcpMsg(CWTcpSocketSession session, ASMsg msg) throws Exception {
		logger.debug("Start process msg: " + msg);
		
		Boolean isLogin = (Boolean)session.getAttribute(LOGIN_STATUS);
		if(isLogin == null || isLogin == false) {
			JSONObject json = JSONObject.fromObject(msg.getContent());
			Integer infoType = (Integer)json.get("infotype");
			String randnum = (String)json.get("randnum");
	
	        String user = "admin";
	        String password = "admin";
	        String content = user + ":" + password;
	        byte[] decrypt = SecurityUtil.desEncrypt(randnum, content.getBytes());
	        String decryptContent = StringUtil.bytesToHexString(decrypt);
	        
	        session.setAttribute(LOGIN_STATUS, true);
	        session.write(new ASMsg(decryptContent).toBuffer());
		}
		else {
			
		}
	}
	
	protected void sendASTcpMsg(CWTcpSocketSession session, ASMsg msg) throws CharacterCodingException {
		session.write(msg.toBuffer());
	}
}
