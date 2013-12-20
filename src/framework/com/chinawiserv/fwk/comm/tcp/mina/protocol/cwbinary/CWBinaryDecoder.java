package com.chinawiserv.fwk.comm.tcp.mina.protocol.cwbinary;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.buffer.CWIoBuffer;

public class CWBinaryDecoder extends ProtocolDecoderAdapter {
	private final static Logger logger = LoggerFactory.getLogger(CWBinaryDecoder.class);
	
	
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		CWIoBuffer cwBuf = CWIoBuffer.allocate(in.remaining());
		cwBuf.put(in.buf());
		cwBuf.flip();
		
		out.write(cwBuf);
	}
}
