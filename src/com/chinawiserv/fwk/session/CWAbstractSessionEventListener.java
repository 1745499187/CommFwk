package com.chinawiserv.fwk.session;

import com.chinawiserv.fwk.core.CWException;
import com.chinawiserv.fwk.event.CWEvent; 

/**
 * <li>文件名称: CWAbstractSessionEventListener.java</li>
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
public abstract class CWAbstractSessionEventListener implements CWSessionEventListener {

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.event.CWEventListener#eventOccured(com.chinawiserv.fwk.event.CWEvent)
	 */
	@Override
	public void eventOccured(CWEvent _event) { 
		CWSessionEvent event = (CWSessionEvent)_event;
		CWSession session = event.getSession();
		 
		switch( event.type() ) {
			case PRE_CREATE:
			case CREATING:
				break; // NOT IMPLEMENTED
			case CREATED: {
				try {
					this.sessionCreated(session);
				} catch (CWException e1) { 
					e1.printStackTrace();
				}
			}
			case PRE_OPEN:  
			case OPENING: 
				break; //NOT IMPLEMENTED
			case OPENED: {
				try {
					this.sessionOpened(session);
				} catch (CWException e) { 
					e.printStackTrace();
				}
			}
			
			case PRE_CLOSE: 
			case CLOSING: 
				break; //NOT IMPLMENTED
			case CLOSED:  {
				try {
					this.sessionClosed(session);
				} catch (CWException e) { 
					e.printStackTrace();
				}
				break;
			}
			case EXCEPTION: {
				Object[] args = event.getArguments();
				if( args == null || args.length<=0 ||  !(args[0] instanceof Throwable) )
					break;
				
				Throwable cause = (Throwable)args[0];
				try {
					this.exceptionCaught(session, cause );
				} catch (CWException e) { 
					e.printStackTrace();
				}
			}
				
			default:
				break;
		}
		
	}
 
}
