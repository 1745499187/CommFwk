package com.chinawiserv.service.alertserver.constant;

public final class ASSessionAttrKeyConstant {
	public static final class SECURITY {
		public static final String LOGIN_DES_KEY = "security.login.des.key";
		public static final String LOGIN_STATUS = "security.login.status";
		public static final String USER_NAME = "security.username";
	}
	
	public static final class DECODING {
		public static final String ATT_DECODE_STATE = "decode.state";
		public static final String ATT_PARTIAL_MSG = "decode.pm";
		public static final String ATT_MSG_LENGTH = "decode.ml";
	}
}
