package com.chinawiserv.fwk.constant;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CWCharset {
	public static final class CHARSET_NAME {
		public static final String UTF_8 = "UTF-8";
		public static final String GBK = "GBK";
	}
	
	public static final class UTF_8 {
		public static final Charset CHARSET = Charset.forName(CHARSET_NAME.UTF_8);
		public static final CharsetEncoder ENCODER = CHARSET.newEncoder();
		public static final CharsetDecoder DECODER = CHARSET.newDecoder();
	}
	
	public static final class GBK {
		public static final Charset CHARSET = Charset.forName(CHARSET_NAME.GBK);
		public static final CharsetEncoder ENCODER = CHARSET.newEncoder();
		public static final CharsetDecoder DECODER = CHARSET.newDecoder();
	}
}
