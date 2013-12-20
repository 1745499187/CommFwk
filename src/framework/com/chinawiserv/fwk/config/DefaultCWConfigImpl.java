package com.chinawiserv.fwk.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DefaultCWConfigImpl implements CWConfig {
	private String configFile;
	private Properties configHolder;
		
	public DefaultCWConfigImpl(String configFile) throws FileNotFoundException, IOException {
		this.configFile = configFile;
		this.configHolder = new Properties();
		this.init();
	}
	
	private void init() throws FileNotFoundException, IOException {
		File f = new File(this.configFile);
		FileReader fr = new FileReader(f);
		this.configHolder.load(fr);
	}
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getConfigFile()
	 */
	
	public String getConfigFile() {
		return this.configFile;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getStringValue(java.lang.String)
	 */
	
	public String getStringValue(String key) {
		return this.configHolder.getProperty(key);
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getStringValue(java.lang.String, java.lang.String)
	 */
	
	public String getStringValue(String key, String defaultValue) {
		return this.configHolder.getProperty(key, defaultValue);
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getIntValue(java.lang.String)
	 */
	
	@Deprecated
	public int getIntValue(String key) {
		int ret = 0;
		String val = this.configHolder.getProperty(key);
		try {
			ret = Integer.parseInt(val);
		} catch(Exception e) {
			ret = -1;
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getIntValue(java.lang.String, int)
	 */
	
	public int getIntValue(String key, int defaultValue) {
		int ret = 0;
		String val = this.configHolder.getProperty(key);
		try {
			ret = Integer.parseInt(val);
		} catch(Exception e) {
			ret = defaultValue;
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getBoolValue(java.lang.String)
	 */
	
	public boolean getBoolValue(String key) {
		boolean ret = false;
		
		String val = this.configHolder.getProperty(key);
		if("true".equalsIgnoreCase(val)) {
			ret = true;
		}
		else if("yes".equals(val)) {
			ret = true;
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getBoolValue(java.lang.String, boolean)
	 */
	
	public boolean getBoolValue(String key, boolean defaultValue) {
		boolean ret = false;
		
		String val = this.configHolder.getProperty(key);
		if(val == null) {
			ret = defaultValue;
		}
		else {
			if("true".equalsIgnoreCase(val)) {
				ret = true;
			}
			else if("yes".equals(val)) {
				ret = true;
			}
		}
		
		return ret;
	}
}
