	package com.chinawiserv.fwk.session;

/**
 * <li>文件名称: CWSessionEventType.java</li>
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
public enum CWSessionEventType { 
	EXCEPTION("EXCEPTION", -1), 
	PRE_CREATE("PRE_CREATE", 0), CREATING("CREATING", 1), CREATED("CREATED", 2),
	PRE_OPEN("PRE_OPEN", 3), OPENING("OPENING", 4), OPENED("OPENED", 5), 
	PRE_CLOSE("PRE_CLOSE", 6), CLOSING("CLOSING", 7), CLOSED("CLOSED", 8);
 
	private String name;
	private int index;
 
	private CWSessionEventType(String _name, int _index) {
		name = _name;
		index = _index;
	}
 
	public static String getName(int _index) {
		for (CWSessionEventType c : CWSessionEventType.values()) {
			if (c.getIndex() == _index) {
				return c.name;
			}
		}
		return null;
	}
 
	public String getName() {
		return name;
	}
	
	public void setName(String _name) {
		this.name = _name;
	}
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int _index) {
		this.index = _index;
	}
}
