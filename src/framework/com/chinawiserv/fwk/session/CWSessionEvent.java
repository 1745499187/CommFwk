package com.chinawiserv.fwk.session;
 
import com.chinawiserv.fwk.event.CWEvent;

/**
 * <li>文件名称: CWSessionEvent.java</li>
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
public class CWSessionEvent extends CWEvent { 
	private CWSessionEventType type;	
	private CWSession session;
	private Object[] arguments;
	
	public CWSessionEvent( CWSession _session, Object[] _arguments, CWSessionEventType _type ) {
		super();
		
		type = _type;
		session = _session;
		arguments = _arguments;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.core.CWEvent#toString()
	 */
	
	public String toString() { 
		return type.name() + "(" + type.ordinal() + ")";
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.event.CWEvent#name()
	 */
	
	public String name() {
		return type.name();
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.event.CWEvent#value()
	 */
	
	public int value() {
		return type.ordinal();
	}
	
	public CWSessionEventType type() {
		return type;
	}
	
	public CWSession getSession() {
		return session;
	}
	
	public Object[] getArguments() {
		return arguments;
	}

}
