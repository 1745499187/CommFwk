package com.chinawiserv.fwk.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefaultCWSectionConfigImpl implements CWSectionConfig {
	private String configFile;
	private Map<String, Map<String, String>> configHolder;
		
	public DefaultCWSectionConfigImpl(String configFile) throws FileNotFoundException, IOException {
		this.configFile = configFile;
		this.configHolder = new HashMap<String, Map<String, String>>();
		this.init();
	}
	
	private void init() throws FileNotFoundException, IOException {
		File f = new File(this.configFile);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String line = null;
		Map<String, String> currSectionDtl = new HashMap<String, String>();
		while((line = br.readLine()) != null) {
			String trimLine = line.trim();
			
			if(trimLine.length() <= 0) { // empty line
				continue;
			}
			else if(trimLine.startsWith("#")) { // comment line
				continue;
			}
			else if(trimLine.startsWith("[") && trimLine.endsWith("]")) { // section header
				String section = line.substring(1, trimLine.length() - 1);
				Map<String, String> secDtl = this.configHolder.get(section);
				if(secDtl == null) {
					secDtl = new HashMap<String, String>();
					this.configHolder.put(section, secDtl);
				}
				currSectionDtl = secDtl;
				continue;
			}
			else { // key-value
				String[] strArr = line.split("=");
				if(strArr.length == 1) {
					currSectionDtl.put(strArr[0], "");
				}
				else {
					currSectionDtl.put(strArr[0], strArr[1]);
				}
				continue;
			}
		}
		
		br.close();
	}
	
	@Override
	public String getConfigFile() {
		return this.configFile;
	}

	@Override
	public String getStringValue(String section, String key) {
		String ret = null;
		
		Map<String, String> secDtl = this.configHolder.get(section);
		if(secDtl != null) {
			ret = secDtl.get(key);
		}
		
		return ret;
	}

	@Override
	public String getStringValue(String section, String key, String defaultValue) {
		String ret = null;
		
		Map<String, String> secDtl = this.configHolder.get(section);
		if(secDtl != null) {
			ret = secDtl.get(key);
			if(ret == null) {
				ret = defaultValue;
			}
		}
		
		return ret;
	}

	@Override
	@Deprecated
	public int getIntValue(String section, String key) {
		int ret = -1;
		
		Map<String, String> secDtl = this.configHolder.get(section);
		if(secDtl != null) {
			String val = secDtl.get(key);
			try {
				ret = Integer.parseInt(val);
			} catch(Exception e) {
				// do nothing
			}
		}
		
		return ret;
	}

	@Override
	public int getIntValue(String section, String key, int defaultValue) {
		int ret = -1;
		
		Map<String, String> secDtl = this.configHolder.get(section);
		if(secDtl != null) {
			String val = secDtl.get(key);
			if(val != null) {
				try {
					ret = Integer.parseInt(val);
				} catch(Exception e) {
					// do nothing
				}
			}
			else {
				ret = defaultValue;
			}
		}
		
		return ret;
	}

	@Override
	public boolean getBoolValue(String section, String key) {
		boolean ret = false;
		
		Map<String, String> secDtl = this.configHolder.get(section);
		if(secDtl != null) {
			String val = secDtl.get(key);
			if(val != null) {
				if("true".equalsIgnoreCase(val)) {
					ret = true;
				}
				else if("yes".equals(val)) {
					ret = true;
				}
			}
		}
		
		return ret;
	}

	@Override
	public boolean getBoolValue(String section, String key, boolean defaultValue) {
		boolean ret = false;
		
		Map<String, String> secDtl = this.configHolder.get(section);
		if(secDtl != null) {
			String val = secDtl.get(key);
			if(val != null) {
				if("true".equalsIgnoreCase(val)) {
					ret = true;
				}
				else if("yes".equals(val)) {
					ret = true;
				}
			}
			else {
				ret = defaultValue;
			}
		}
		
		return ret;
	}

}
