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
	@Override
	public String getConfigFile() {
		return this.configFile;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getStringValue(java.lang.String)
	 */
	@Override
	public String getStringValue(String key) {
		return this.configHolder.getProperty(key);
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getStringValue(java.lang.String, java.lang.String)
	 */
	@Override
	public String getStringValue(String key, String defaultValue) {
		return this.configHolder.getProperty(key, defaultValue);
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.config.CWConfig#getIntValue(java.lang.String)
	 */
	@Override
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
	@Override
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

}
