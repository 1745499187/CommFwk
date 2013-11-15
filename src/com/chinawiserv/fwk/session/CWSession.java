package com.chinawiserv.fwk.session;

import java.util.Set; 

/**
 * <li>文件名称: CWSession.java</li>
 * <li>文件描述: 本类描述</li>
 * <li>版权所有: 版权所有(C)2005-2013</li>
 * <li>公司: 勤智数码</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>完成日期：2013-10-27</li>
 * <li>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</li>
 * @version 1.0
 * @author FWK Team
 */
public interface CWSession {
	
	public long getId();
	
	public String getName();
	
	public boolean open();
	
	public boolean close();
	
	public CWSessionStatus getStatus();   
 
	public Object getAttribute(Object key);
 
	public Object getAttribute(Object key, Object defaultValue);
 
	public Object setAttribute(Object key, Object value);
     
	public Object setAttributeIfAbsent(Object key, Object value);
 
	public Object removeAttribute(Object key);
 
	public boolean removeAttribute(Object key, Object value);
 
	public boolean replaceAttribute(Object key, Object oldValue, Object newValue);
 
	public boolean containsAttribute(Object key);
 
    public Set<Object> getAttributeKeys();
    
    public CWSessionEventListener getListener();
    
    public void setListener( CWSessionEventListener listener );

    public void write(Object message);
    
    public Object read();
    
    public Object read(int timeout);
}
