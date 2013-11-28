package com.chinawiserv.fwk.comm.tcp.mina;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import com.chinawiserv.fwk.comm.tcp.mina.protocol.cwbinary.CWBinaryCodecFilter;
import com.chinawiserv.fwk.constant.CWCharset;
import com.chinawiserv.fwk.constant.ETcpAppProtocol;

public class MinaUtil {
	public static ProtocolCodecFilter pickProtocol(ETcpAppProtocol p) {
		switch(p) {
		case P_TEXT_UTF8_NUL:
			return new ProtocolCodecFilter(
				new TextLineCodecFactory(CWCharset.UTF_8.CHARSET, LineDelimiter.NUL, LineDelimiter.NUL
			));
		case P_BINARY:
		default:
			// default codec is binary
			return new CWBinaryCodecFilter();
		}
	}
}
