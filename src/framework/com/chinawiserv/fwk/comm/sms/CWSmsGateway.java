package com.chinawiserv.fwk.comm.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wiserv.ccl.sms.main.smsapi.util.ReadConfig;

import com.chinawiserv.fwk.constant.CWFwkConstant;
import com.chinawiserv.fwk.core.CWFramework;


public class CWSmsGateway {
	private final static Logger logger = LoggerFactory.getLogger(CWSmsGateway.class);
	
	private static CWSmsGateway instance = null;
	
	private CWSmsSessionManager sessionManager = null;
	
	private CWSmsGateway() {
		String cclSmsConf = CWFramework.getInstance().getConfigManager().getStringValue(CWFwkConstant.CONF.SECTIONS.NAMES.SMS_GATEWAY, CWFwkConstant.CONF.SECTIONS.FIELDS.SMS_GATEWAY.SMS_CONF);
		String fwkConfDir = System.getProperty(CWFwkConstant.JVM_KEY.FWK_CONF_DIR);
		ReadConfig.readConfig(fwkConfDir + "/" + cclSmsConf);
		sessionManager = new CWSmsSessionManagerImpl();
	}
	
	public static CWSmsGateway getInstance() {
		if(instance == null) {
			synchronized(CWSmsGateway.class) {
				if(instance == null) {
					instance = new CWSmsGateway();
				}
			}
		}
		
		return instance;
	}
	
	public CWSmsSessionManager getSessionManager() {
		return this.sessionManager;
	}
}
