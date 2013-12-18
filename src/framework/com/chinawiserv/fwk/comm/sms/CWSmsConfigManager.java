package com.chinawiserv.fwk.comm.sms;

import java.io.FileNotFoundException;
import java.io.IOException;

import wiserv.ccl.sms.main.smsapi.util.ReadConfig;

import com.chinawiserv.fwk.config.CWConfig;
import com.chinawiserv.fwk.config.CWConfigFactory;
import com.chinawiserv.fwk.config.DefaultCWConfigImpl;

public class CWSmsConfigManager extends DefaultCWConfigImpl {
	private static CWConfig instance = null;
	private static String configFile = null;
	
	private CWSmsConfigManager(String configFile) throws FileNotFoundException, IOException {
		super(configFile);
	}
	
	public synchronized static void init(String cfg) {
		CWSmsConfigManager.configFile = cfg;
		if(configFile == null || configFile.length() <= 0) {
			configFile = "alertServer.properties";
		}
		
		CWSmsConfigManager.getInstance();
		
		// init ccl sms component
		ReadConfig.readConfig(CWSmsConfigManager.getInstance().getStringValue("CCL_SMS_PROP_FILE"));
	}
	
	public static CWConfig getInstance() {
		if(instance == null) {
			synchronized(CWSmsConfigManager.class) {
				if(instance == null) {
					instance = CWConfigFactory.build(CWSmsConfigManager.configFile);
				}
			}
		}
		
		return instance;
	}
}
