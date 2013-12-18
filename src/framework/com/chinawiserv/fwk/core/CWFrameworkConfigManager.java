package com.chinawiserv.fwk.core;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.chinawiserv.fwk.config.DefaultCWSectionConfigImpl;

public class CWFrameworkConfigManager extends DefaultCWSectionConfigImpl {

	public CWFrameworkConfigManager(String configFile) throws FileNotFoundException, IOException {
		super(configFile);
	}
	
}
