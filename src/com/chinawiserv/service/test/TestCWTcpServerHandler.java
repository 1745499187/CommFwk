package com.chinawiserv.service.test;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.IoBufferAllocator;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession;
import com.chinawiserv.fwk.core.CWException;
import com.chinawiserv.fwk.session.CWAbstractSessionEventListener;
import com.chinawiserv.fwk.session.CWSession;

/**
 * <li>文件名称: TestCWTcpHandler.java</li>
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
public class TestCWTcpServerHandler extends CWAbstractSessionEventListener implements CWTcpHandler {
	private final static Logger logger = LoggerFactory.getLogger(TestCWTcpServerHandler.class);
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#sessionCreated(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession)
	 */
	@Override
	public void sessionCreated(CWSession session) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#sessionClosed(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession)
	 */
	@Override
	public void sessionClosed(CWSession session) throws CWException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#sessionOpened(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession)
	 */
	@Override
	public void sessionOpened(CWSession session) throws CWException {
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#exceptionCaught(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(CWSession session, Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#messageReceived(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(CWTcpSocketSession session, Object message) {
		if(message instanceof String) {
			String reply = "Echo: " + (String)message;
			session.write(reply);
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpHandler#messageSent(com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession, java.lang.Object)
	 */
	@Override
	public void messageSent(CWTcpSocketSession session, Object message) {
	}

}
