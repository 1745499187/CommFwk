package com.chinawiserv.fwk.comm.tcp.mina.protocol.cwbinary;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.CWTcpException;
import com.chinawiserv.fwk.comm.tcp.buffer.CWIoBuffer;

public class CWBinaryEncoder extends ProtocolEncoderAdapter {
	private static final Logger logger = LoggerFactory.getLogger(CWBinaryEncoder.class);
	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if(message instanceof CWIoBuffer) {
			CWIoBuffer msgToSend = (CWIoBuffer)message;
			IoBuffer buf = IoBuffer.allocate(msgToSend.remaining());
			buf.put(msgToSend.array());
			buf.flip();
			out.write(buf);
		}
		else {
			throw new CWTcpException("No encoder for message type: "+message.getClass());
		}
	}
}
