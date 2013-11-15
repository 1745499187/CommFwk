package com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver;

import java.nio.charset.CharacterCodingException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver.typedef.ASTcpMsg;
import com.chinawiserv.fwk.constant.CWCharset;

public class ASEncoder extends ProtocolEncoderAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ASEncoder.class);
	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		IoBuffer bufOut = null, bufTmp = null;
		bufTmp = IoBuffer.allocate(512).setAutoExpand(true);
		
		if(message instanceof ASTcpMsg) {
			ASTcpMsg msgToSend = (ASTcpMsg)message;

			bufTmp.putString(msgToSend.getContent(), CWCharset.GBK.ENCODER);
		}
		else {
			throw new AlertServerCodecException("No encoder for message type: "+message.getClass());
		}
		bufTmp.flip();
		
		// set TCP-PDU header
		int msgBufLen = bufTmp.remaining();
		bufOut = IoBuffer.allocate(msgBufLen+8)
				.putString(ASTcpMsg.VERSION, CWCharset.UTF_8.ENCODER) // version
				.putInt(msgBufLen) // msg len
				.put(bufTmp); // msg
		bufOut.flip();
		
		// send encoded msg out
		out.write(bufOut);
	}

	private void putStringWithLength(IoBuffer buf, String str) throws CharacterCodingException {
		if(str == null) {
			buf.putInt(0);
		}
		else {
			buf.putInt(str.length());
			buf.putString(str, CWCharset.GBK.ENCODER);
		}
	}
}
