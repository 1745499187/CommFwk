package com.chinawiserv.fwk.util;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>HTML style description</p>
 *
 * @author weibz
 * @version %VER_M%, %VER_S%
 */
public abstract class CWPrintableDO implements Serializable {
	/** The serialVersionUID */
	private static final long serialVersionUID = -6855569707256458664L;

	protected final static String LINE_BREAKER = "\r\n";
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(LINE_BREAKER+">>>>>>>>>>>>>>>>>>>>>>>>>"+LINE_BREAKER);
		sb.append(this.getClass().getName()+":"+LINE_BREAKER);
		Method[] methods = this.getClass().getMethods();
		for(Method m : methods) {
			String mname = m.getName();
			if((mname.startsWith("get") && !mname.equals("getClass")) || mname.startsWith("is")) {
				if(m.getParameterTypes().length == 0) {
					Object result = null;
					try {
						result = m.invoke(this);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(result == null) {
						result = "NULL";
					}
					sb.append("  "+mname+"(): "+result+LINE_BREAKER);
				}
				else{
//					sb.append("  "+mname+"(...): [has param, ignored]"+LINE_BREAKER);
				}
			}
		}
		sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<");
		return sb.toString();
	}
}
