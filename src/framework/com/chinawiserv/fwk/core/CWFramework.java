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
		} catch(Throwable t) {
			logger.error("Error when initial CWFramework", t);
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}
	
	private void init() {
		smsGateway = CWSmsGateway.getInstance();
	}
	
	public static CWFramework getInstance() {
		if(instance == null) {
			synchronized(CWFramework.class) {
				if(instance == null) {
					instance = new CWFramework();
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
		return this.smsGateway;
	}
}
