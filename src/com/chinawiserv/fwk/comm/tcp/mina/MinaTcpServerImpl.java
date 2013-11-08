package com.chinawiserv.fwk.comm.tcp.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.chinawiserv.fwk.comm.tcp.CWTcpHandler; 
import com.chinawiserv.fwk.comm.tcp.CWTcpServerImpl; 
import com.chinawiserv.fwk.session.CWSessionEventListener;

/**
 * <li>文件名称: MinaTcpServerImpl.java</li>
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
public class MinaTcpServerImpl implements CWTcpServerImpl {
	
	private SocketAcceptor acceptor;	
	private String ipAddr;
	private int port;
	private CWTcpHandler handler = null;
	CWSessionEventListener sessionEventListener = null;
	 
	public MinaTcpServerImpl( String _ipAddr, int _port ) {
		ipAddr = _ipAddr;
		port = _port;
	}
	
	public MinaTcpServerImpl( int _port ) {
		port = _port;
	}
	
	public boolean open() {
		
		InetSocketAddress address = null;
		if ("127.0.0.1".equals(ipAddr) || "localhost".equalsIgnoreCase(ipAddr) ||  ipAddr==null ) {
			address = new InetSocketAddress(port);
		} else {
			address = new InetSocketAddress(ipAddr, port);
		}
		
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors());
//		acceptor.setReuseAddress(true);
		
//		String readBufferSize = (String) getAttribute( TcpConstants.Attribute.TCP_READ_BUFFER_SIZE, TcpConstants.Attribute.DEFAULT_TCP_READ_BUFFER_SIZE + "" );
//		String recvBufferSize = (String) getAttribute( TcpConstants.Attribute.TCP_RECV_BUFFER_SIZE, TcpConstants.Attribute.DEFAULT_TCP_RECV_BUFFER_SIZE + "" );
//		String idleTimeout = (String) getAttribute( TcpConstants.Attribute.TCP_IDLE_TIMEOUT, TcpConstants.Attribute.DEFAULT_TCP_IDLE_TIMEOUT + "" );
// 
//		SocketSessionConfig sockConfig = acceptor.getSessionConfig();
//		sockConfig.setReadBufferSize( Integer.valueOf( readBufferSize ) );
//		sockConfig.setReceiveBufferSize( Integer.valueOf( recvBufferSize ) );
//		sockConfig.setIdleTime(IdleStatus.BOTH_IDLE, Integer.valueOf( idleTimeout ) ); 
 
		IoHandlerAdapter minaTcpServerHandler = new MinaTcpServerHandler( handler , sessionEventListener );
		//IoHandlerAdapter minaTcpServerHandler = new MinaTcpServerHandlerTest();
		if( handler != null )
			acceptor.setHandler( minaTcpServerHandler );
		else
			return false;

		try {
			acceptor.bind( address ); 
		} catch (IOException e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	public void setCWTcpHandler( CWTcpHandler _handler ) { 
			handler =  _handler;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpServerImpl#setIpAddress(java.lang.String)
	 */
	@Override
	public void setIpAddress(String _ipAddr ) {
		ipAddr = _ipAddr;
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpServerImpl#setPort(int)
	 */
	@Override
	public void setPort(int _port) {
		port = _port;
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpServerImpl#close()
	 */
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpServerImpl#setCWSessionEventListener(com.chinawiserv.fwk.session.CWSessionEventListener)
	 */
	@Override
	public void setCWSessionEventListener(CWSessionEventListener _listener) {
		sessionEventListener = _listener;
		
	}
}
