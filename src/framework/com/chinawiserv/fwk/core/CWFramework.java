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
		try {
			String configFile = System.getProperty(CWFwkConstant.JVM_KEY.FWK_CONF);
			File f = new File(configFile);
			if(f.exists()) {
				String confDir = f.getParentFile().getAbsolutePath();
				System.setProperty(CWFwkConstant.JVM_KEY.FWK_CONF_DIR, confDir);
			}
			
			fwkCfgMgr = new CWFrameworkConfigManager(configFile);
			
			this.init();
		} catch(Throwable t) {
			logger.error("Error when initial CWFramework", t);
			t.printStackTrace();
			throw new RuntimeException(t);
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
