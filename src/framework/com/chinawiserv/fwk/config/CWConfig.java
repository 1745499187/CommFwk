package com.chinawiserv.fwk.config;

/**
 * <p>Interface of configuration</p>
 *
 * @author weibz
 * @version %VER_M%, %VER_S%
 */
public interface CWConfig {	
	/**
	 * Get config file path
	 * @return config file path
	 */
	public String getConfigFile();
	
	/**
	 * Get value as String
	 * @param key
	 * @return string value, if key is not found, null will be returned
	 */
	public String getStringValue(String key);
	
	/**
	 * Get value as String
	 * @param key
	 * @param defaultValue
	 *   default value while key not found
	 * @return string value
	 */
	public String getStringValue(String key, String defaultValue);
	
	/**
	 * Get value as int
	 * @deprecated In case of key not found, -1 will be returned. 
	 * This will cause misunderstanding since user won't know it's configured as -1 or default. 
	 * We strongly recommend to use {@link #getIntValue(String, int)} to instead.
	 * @param key
	 * @return int value, if key is not found, -1 will be returned
	 */
	@Deprecated
	public int getIntValue(String key);
	
	/**
	 * Get value as int
	 * @param key
	 * @param defaultValue
	 *   default value while key not found
	 * @return
	 */
	public int getIntValue(String key, int defaultValue);
	
	/**
	 * Get value as boolean. 
	 * {@code true} will be returned only when the value is "true" or "yes" with case ignoring. 
	 * All the else values will caused a {@code false} return.
	 * @param key
	 * @return boolean value, if key is not found, false will be returned
	 */
	public boolean getBoolValue(String key);
	
	/**
	 * Get value as boolean. 
	 * {@code true} will be returned only when the value is "true" or "yes" with case ignoring.
	 * All the else values will caused a {@code false} return.
	 * @param key
	 * @param defaultValue
	 *   default value while key not found
	 * @return boolean value
	 */
	public boolean getBoolValue(String key, boolean defaultValue);
	
}
