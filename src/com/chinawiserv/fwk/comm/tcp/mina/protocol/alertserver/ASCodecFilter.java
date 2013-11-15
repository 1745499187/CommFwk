package com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver;

import java.util.Set;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

public class ASCodecFilter extends ProtocolCodecFilter {
	private static ASEncoder encoder = new ASEncoder();
	private static ASDecoder decoder = new ASDecoder();
	
	public ASCodecFilter() {
		super(encoder, decoder);
	}
	
	@Override
    public void sessionClosed(IoFilter.NextFilter nextFilter, IoSession session) throws Exception {
        super.sessionClosed(nextFilter, session);
        // remove attributes
        Set<?> keys = session.getAttributeKeys();
        for(Object key : keys) {
        	session.removeAttribute(key);
        }
    }
}
