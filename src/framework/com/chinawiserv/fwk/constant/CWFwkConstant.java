package com.chinawiserv.fwk.constant;

public final class CWFwkConstant {
	
	public static final class JVM_KEY {
		public static final String FWK_CONF = "commfwk.conf";
		public static final String FWK_CONF_DIR = "commfwk.conf.dir";
	}
	
	public static final class CONF {
		public static final class SECTIONS {
			public static final class NAMES {
				public static final String MAIN = "MAIN";
				public static final String SMS_GATEWAY = "SMS_GATEWAY";
			}
		
			public static final class FIELDS {
				public static final class MAIN {
					public static final String ENABLE_SMS = "ENABLE_SMS";
				}
				
				public static final class SMS_GATEWAY {
					public static final String SMS_CONF = "SMS_CONF";
				}
			}
		}
	}
}
