package com.chinawiserv.fwk.core;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.sms.CWSmsGateway;
import com.chinawiserv.fwk.constant.CWFwkConstant;

public final class CWFramework {
	private static final Logger logger = LoggerFactory.getLogger(CWFramework.class);
	
	private static CWFramework instance = null;
	
	private CWFrameworkConfigManager fwkCfgMgr = null;
	
	private boolean enableSmsGateway = true;
	private CWSmsGateway smsGateway = null;
	
	private CWFramework() {
		String configFile = System.getProperty(CWFwkConstant.JVM_KEY.FWK_CONF);
		if(configFile == null) {
			CWRuntimeException exp = new CWRuntimeException("Missing framework config file, please special it in system property: " + CWFwkConstant.JVM_KEY.FWK_CONF);
			logger.error(exp.getMessage(), exp);
			throw exp;
		}
		
		try {
			File f = new File(configFile);
			if(f.exists()) {
				String confDir = f.getParentFile().getAbsolutePath();
				System.setProperty(CWFwkConstant.JVM_KEY.FWK_CONF_DIR, confDir);
			}
			
			fwkCfgMgr = new CWFrameworkConfigManager(configFile);
		} catch(Throwable t) {
			logger.error("Error when initial CWFramework", t);
			t.printStackTrace();
			throw new CWRuntimeException(t);
		}
	}
	
	private void init() {
		// init sms gateway if this feature has been configured as enable
		this.enableSmsGateway = this.fwkCfgMgr.getBoolValue(CWFwkConstant.CONF.SECTIONS.NAMES.SMS_GATEWAY, 
				CWFwkConstant.CONF.SECTIONS.FIELDS.SMS_GATEWAY.ENABLE, true);
		if(this.enableSmsGateway) {
			this.smsGateway = CWSmsGateway.getInstance();
		}
	}
	
	public static CWFramework getInstance() {
		if(instance == null) {
			synchronized(CWFramework.class) {
				if(instance == null) {
					instance = new CWFramework();
					// once object created, init it at first time
					// must init after object constructed, because there might be some operation related to this instance
					instance.init();
				}
			}
		}
		return instance;
	}
	
	public CWFrameworkConfigManager getConfigManager() {
		return this.fwkCfgMgr;
	}
	
	public CWSmsGateway getSmsGateway() {
		if(this.enableSmsGateway) {
			return this.smsGateway;
		}
		else {
			throw new CWRuntimeException("SMS gateway is disabled by configuration. If you want to use this feature, enable it first.");
		}
	}
}
