package com.chinawiserv.service.test;

import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession;
import com.chinawiserv.fwk.core.CWException;
import com.chinawiserv.fwk.session.CWAbstractSessionEventListener;
import com.chinawiserv.fwk.session.CWSession;

/**
 * <li>文件名称: TestCWTcpSocketHandler.java</li>
 * <li>文件描述: 本类描述</li>
 * <li>版权所有: 版权所有(C)2005-2013</li>
 * <li>公司: 勤智数码</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>完成日期：2013-11-4</li>
 * <li>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</li>
 * @version 1.0
 * @author FWK Team
 */
public class TestCWTcpSocketHandler extends CWAbstractSessionEventListener implements CWTcpHandler {

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#sessionCreated(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession)
	 */
	@Override
	public void sessionCreated(CWSession session) {
		
		
	} 
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#messageReceived(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(CWTcpSocketSession session, Object message) {
		System.out.println( message );
		 try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.write(message);
		
	}


	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSessionEventListener#sessionOpened(com.chinawiserv.fwk.session.CWSession)
	 */
	@Override
	public void sessionOpened(CWSession session) throws CWException {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSessionEventListener#sessionClosed(com.chinawiserv.fwk.session.CWSession)
	 */
	@Override
	public void sessionClosed(CWSession session) throws CWException {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.session.CWSessionEventListener#exceptionCaught(com.chinawiserv.fwk.session.CWSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(CWSession session, Throwable cause)
			throws CWException {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#messageSent(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Object)
	 */
	@Override
	public void messageSent(CWTcpSocketSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		
	} 

}
