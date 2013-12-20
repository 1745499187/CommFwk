package com.chinawiserv.fwk.session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.chinawiserv.fwk.util.CWIDGenerator;
 

/**
 * <li>文件名称: CWAbstractSession.java</li>
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
public abstract class CWAbstractSession implements CWSession {
	
	private long id = 0;
    private static CWIDGenerator idGen = new CWIDGenerator();
	private CWSessionEventListener listener;
	private Map<Object, Object> attrMap = new ConcurrentHashMap<Object, Object>();
	
    public CWAbstractSession( )
    {
    	id = idGen.nextID();  
    }

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#open()
	 */
	
	public boolean open() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#close()
	 */
	
	public boolean close() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#getStatus()
	 */
	
	public CWSessionStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#getAttribute(java.lang.Object)
	 */
	
	public Object getAttribute(Object key) {  
		if( attrMap.containsKey(key) )
			return attrMap.get(key);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#getAttribute(java.lang.Object, java.lang.Object)
	 */
	
	public Object getAttribute(Object key, Object defaultValue) { 
		if( containsAttribute(key) ) {
			return getAttribute(key);
		} else {
			setAttribute(key, defaultValue);
			return getAttribute(key);
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#setAttribute(java.lang.Object, java.lang.Object)
	 */
	
	public Object setAttribute(Object key, Object value) { 
		if (containsAttribute(key)) {
			Object old_value = attrMap.get(key);
			attrMap.put(key, value);
			return old_value;
		} else { 
			attrMap.put(key, value);
			return value;
		} 
	} 
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#setAttributeIfAbsent(java.lang.Object, java.lang.Object)
	 */
	
	public Object setAttributeIfAbsent(Object key, Object value) {

		if (containsAttribute(key)) {
			 return getAttribute(key);
		} else {
			return setAttribute(key, value);
		} 
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#removeAttribute(java.lang.Object)
	 */
	
	public Object removeAttribute(Object key) {
		if( containsAttribute(key) ) {
			Object value = attrMap.get(key);
			attrMap.remove(key);
			return value;
		} else { 
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#removeAttribute(java.lang.Object, java.lang.Object)
	 */
	
	public boolean removeAttribute(Object key, Object value) {
		if (containsAttribute(key) && getAttribute(key).equals(value)) {
			removeAttribute(key);
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#replaceAttribute(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	
	public boolean replaceAttribute(Object key, Object oldValue, Object newValue) {
		 if (containsAttribute(key) && getAttribute(key).equals(oldValue)) {
			 setAttribute(key, newValue);
			 return true;
		 } else {
			 return false;
		 }
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#containsAttribute(java.lang.Object)
	 */
	
	public boolean containsAttribute(Object key) {
		return attrMap.containsKey(key);
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#getAttributeKeys()
	 */
	
	public Set<Object> getAttributeKeys() {
		return attrMap.keySet();
	} 
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#clearAttributes()
	 */
	
	public void clearAttributes() {
		attrMap.clear();
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#getName()
	 */
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#getId()
	 */
	
	public long getId() {
		return this.id;
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#getListener()
	 */
	
	public CWSessionEventListener getListener() { 
		return listener;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSession#setListener(com.chinawiserv.fwk.session.CWSessionEventListener)
	 */
	
	public void setListener(CWSessionEventListener _listener) {
		listener = _listener;
		
	} 

}
