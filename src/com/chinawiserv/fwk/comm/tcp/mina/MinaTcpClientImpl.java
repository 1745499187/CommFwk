package com.chinawiserv.fwk.comm.tcp.mina;
 
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.chinawiserv.fwk.comm.tcp.CWTcpHandler;
import com.chinawiserv.fwk.comm.tcp.CWTcpClientImpl;
import com.chinawiserv.fwk.constant.CWCharset;
import com.chinawiserv.fwk.constant.ETcpProtocol;
import com.chinawiserv.fwk.session.CWSessionEventListener;

/**
 * <li>文件名称: MinaTcpSocketImpl.java</li>
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
public class MinaTcpClientImpl implements CWTcpClientImpl {

	private NioSocketConnector connector;	
	private String remoteIp;
	private int remotePort;
	private CWTcpHandler handler = null;
	private CWSessionEventListener sessionEventListener = null;
	
	public MinaTcpClientImpl( String _remoteIp, int _remotePort ) {
		remoteIp = _remoteIp;
		remotePort = _remotePort;
	}
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketImpl#setRemoteIp(java.lang.String)
	 */
	@Override
	public void setRemoteIp(String _remoteIp) {
		remoteIp = _remoteIp;
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketImpl#setRemotePort(int)
	 */
	@Override
	public void setRemotePort(int _remotePort) {
		remotePort = _remotePort;
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketImpl#open()
	 */
	@Override
	public boolean open(ETcpProtocol _protocol) {
		connector = new NioSocketConnector(); 
		connector.setHandler(new MinaTcpClientHandler( handler, sessionEventListener ));
		 
		SocketSessionConfig sessionConfig = connector.getSessionConfig();
		sessionConfig.setUseReadOperation(false);
		 
		DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
		switch(_protocol) {
		case P_TEXT_UTF8:
			filterChain.addLast("codec", 
					new ProtocolCodecFilter(
							new TextLineCodecFactory(CWCharset.UTF_8.CHARSET, LineDelimiter.NUL, LineDelimiter.NUL
					)));
			break;
		case P_BINARY:
			break;
		default:
			break;
		}
		// loggingFilter should be the last one !!
		filterChain.addLast("logging", new CWTcpLoggingFilter(MinaTcpClientImpl.class));
		 
		ConnectFuture cf = connector.connect(new InetSocketAddress(remoteIp, remotePort));
		
		cf.awaitUninterruptibly();
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connector.dispose();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketImpl#close()
	 */
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketImpl#setHandler(com.chinawiserv.fwk.comm.tcp.CWTcpHandler)
	 */
	@Override
	public void setCWTcpHandler(CWTcpHandler _handler) {
		handler = _handler;
		
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketImpl#setCWSessionEventListener(com.chinawiserv.fwk.session.CWSessionEventListener)
	 */
	@Override
	public void setCWSessionEventListener(CWSessionEventListener _listener) {
		sessionEventListener = _listener;
		
	}
}
