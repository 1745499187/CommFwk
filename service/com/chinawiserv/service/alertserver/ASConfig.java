package com.chinawiserv.service.alertserver;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.chinawiserv.fwk.config.CWConfig;
import com.chinawiserv.fwk.config.CWConfigFactory;
import com.chinawiserv.fwk.config.DefaultCWConfigImpl;

public class ASConfig extends DefaultCWConfigImpl {
	private static CWConfig instance = null;
	private static String configFile = null;
	
	private ASConfig(String configFile) throws FileNotFoundException, IOException {
		super(configFile);
	}
	
	public synchronized static void init(String configFile) {
		ASConfig.configFile = configFile;
		ASConfig.instance = null;
		ASConfig.getInstance();
	}
	
	public static CWConfig getInstance() {
		if(instance == null) {
			synchronized(ASConfig.class) {
				if(instance == null) {
					instance = CWConfigFactory.build(ASConfig.configFile);
				}
			}
		}
		
		return instance;
	}
}
