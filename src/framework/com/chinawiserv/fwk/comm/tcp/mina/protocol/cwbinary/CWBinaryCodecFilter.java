package com.chinawiserv.fwk.comm.tcp.mina.protocol.cwbinary;

import java.util.Set;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

public class CWBinaryCodecFilter extends ProtocolCodecFilter {
	private static CWBinaryEncoder encoder = new CWBinaryEncoder();
	private static CWBinaryDecoder decoder = new CWBinaryDecoder();
	
	public CWBinaryCodecFilter() {
		super(encoder, decoder);
	}
	
	
    public void sessionClosed(IoFilter.NextFilter nextFilter, IoSession session) throws Exception {
        super.sessionClosed(nextFilter, session);
        // remove attributes
        Set<?> keys = session.getAttributeKeys();
        for(Object key : keys) {
        	session.removeAttribute(key);
        }
    }
}
