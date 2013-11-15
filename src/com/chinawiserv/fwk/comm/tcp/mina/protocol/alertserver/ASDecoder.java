package com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver.typedef.ASTcpMsg;
import com.chinawiserv.fwk.constant.CWCharset;

public class ASDecoder extends ProtocolDecoderAdapter {
	private final static Logger logger = LoggerFactory.getLogger(ASDecoder.class);
	
	private final static String ATT_DECODE_STATE = "status.ds";
	private final static String ATT_PARTIAL_MSG = "status.pm";
	private final static String ATT_MSG_LENGTH = "status.ml";

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		logger.debug("Start decode: " + in.getHexDump(in.remaining()));
		
		EASDecodeState state = (EASDecodeState)session.getAttribute(ATT_DECODE_STATE);
		if(state == null) {
			session.setAttribute(ATT_DECODE_STATE, EASDecodeState.NEW);
			state = (EASDecodeState)session.getAttribute(ATT_DECODE_STATE);
		}
		
		IoBuffer buf = in;
		String strTmp = null;
		int intTmp = 0;
		byte byteTmp = (byte)0;
		switch(state) {
		case HEAD:
			// grab the stored a partial HEAD request
            final ByteBuffer oldBuffer = (ByteBuffer)session.getAttribute(ATT_PARTIAL_MSG);
            // concat the old buffer and the new incoming one
            buf = IoBuffer.allocate(oldBuffer.remaining() + buf.remaining()).put(oldBuffer).put(buf).flip();
            session.removeAttribute(ATT_PARTIAL_MSG);
            // now let's decode like it was a new message
		case NEW:
			// need more byte to proceed: "wdp"(3)+1(1)+msg_length(4)
			if(buf.remaining() < 8) {
				session.setAttribute(ATT_DECODE_STATE, EASDecodeState.HEAD);
				
				final ByteBuffer partial = ByteBuffer.allocate(buf.remaining());
                partial.put(buf.buf());
                partial.flip();
                // no request decoded, we accumulate
                session.setAttribute(ATT_PARTIAL_MSG, partial);
			}
			else {
				session.setAttribute(ATT_DECODE_STATE, EASDecodeState.MSG);
				
				strTmp = buf.getString(4, CWCharset.UTF_8.DECODER); // should be "0001"
				intTmp = buf.getInt(); // msg length
				session.setAttribute(ATT_MSG_LENGTH, new Integer(intTmp));
			}
			break;
		case MSG_IMCOMPLETE:
			// grab the stored a partial HEAD request
            final ByteBuffer oldMsgBuffer = (ByteBuffer)session.getAttribute(ATT_PARTIAL_MSG);
            // concat the old buffer and the new incoming one
            buf = IoBuffer.allocate(oldMsgBuffer.remaining() + buf.remaining()).put(oldMsgBuffer).put(buf).flip();
            session.removeAttribute(ATT_PARTIAL_MSG);
            // now let's decode like it was a new message
		case MSG:
			int msgLen = ((Integer)session.getAttribute(ATT_MSG_LENGTH)).intValue();
			if(buf.remaining() < msgLen) {
				session.setAttribute(ATT_DECODE_STATE, EASDecodeState.MSG_IMCOMPLETE);
				
				final ByteBuffer partial = ByteBuffer.allocate(buf.remaining());
                partial.put(buf.buf());
                partial.flip();
                // no request decoded, we accumulate
                session.setAttribute(ATT_PARTIAL_MSG, partial);
				// need to break;
			}
			else {
				session.setAttribute(ATT_DECODE_STATE, EASDecodeState.NEW);
				
				try{
					// start message decoding
					String str = buf.getString(msgLen, CWCharset.GBK.DECODER);
					ASTcpMsg msg = new ASTcpMsg(str);
					out.write(msg);
					
				} catch(Exception e) {
					throw new AlertServerCodecException("Decode msg fail", e);
				}
			}
			break;
		default:
			break;
		}
		
		if(buf.hasRemaining()) {
			this.decode(session, buf, out);
		}
	}
}
