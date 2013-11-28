package com.chinawiserv.fwk.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * <li>文件名称: DefaultCWConfigImpl.java</li>
 * <li>文件描述: 本类描述</li>
 * <li>版权所有: 版权所有(C)2005-2013</li>
 * <li>公司: 勤智数码</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>完成日期：2013-11-3</li>
 * <li>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</li>
 * @version 1.0
 * @author FWK Team
 */
public class CWConfigFactory {
	
	public static CWConfig build(String configFile) {
		try {
			CWConfig config = new DefaultCWConfigImpl(configFile);
			return config;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
}
