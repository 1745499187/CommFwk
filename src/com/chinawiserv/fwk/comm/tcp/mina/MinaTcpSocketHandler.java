package com.chinawiserv.fwk.comm.tcp.mina;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSession;
import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl;
import com.chinawiserv.fwk.core.CWException;
import com.chinawiserv.fwk.session.CWSessionEventListener;

/**
 * <li>文件名称: MinaTcpSocketHandler.java</li>
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
public class MinaTcpSocketHandler extends IoHandlerAdapter {
	 
		private CWTcpHandler cwTcpHandler; 
		private CWSessionEventListener cwSessionEventListener;
		private CWTcpSocketSession cwTcpSocketSession;
		
		private Map<IoSession, CWTcpSocketSession> sessionMap = new ConcurrentHashMap<IoSession, CWTcpSocketSession>();
		 
		public MinaTcpSocketHandler( CWTcpHandler _cwTcpHandler, CWSessionEventListener _cwSessionEventListener ) {
			cwTcpHandler = _cwTcpHandler;
			cwSessionEventListener = _cwSessionEventListener;
		} 
		
		@Override
		public void sessionCreated(IoSession session) {
			CWTcpSocketSessionImpl sessionImpl = new MinaCWTcpSocketSessionImpl( session );
			cwTcpSocketSession = new CWTcpSocketSession();
			cwTcpSocketSession.setCWTcpSocketSessionImpl(sessionImpl);
			
			sessionMap.put(session, cwTcpSocketSession);
			
			try {
				cwSessionEventListener.sessionCreated(cwTcpSocketSession);
			} catch (CWException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception { 
			CWTcpSocketSession cwTcpSocketSession = sessionMap.get(session);
			if( cwTcpSocketSession == null )
				return;
			
			cwSessionEventListener.sessionClosed(cwTcpSocketSession);
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception { 
			CWTcpSocketSession cwTcpSocketSession = sessionMap.get(session);
			if( cwTcpSocketSession == null )
				return;
			
			cwSessionEventListener.sessionOpened(cwTcpSocketSession);
		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus status) { 
		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause) { 
			CWTcpSocketSession cwTcpSocketSession = sessionMap.get(session);
			if( cwTcpSocketSession == null )
				return;
			
			try {
				cwSessionEventListener.exceptionCaught(cwTcpSocketSession, cause);
			} catch (CWException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void messageReceived(IoSession session, Object message) {
			CWTcpSocketSession cwTcpSocketSession = sessionMap.get(session);
			if( cwTcpSocketSession == null )
				return;
			
			cwTcpHandler.messageReceived(cwTcpSocketSession, message);
		}
}
