package com.chinawiserv.fwk.comm.tcp;

import com.chinawiserv.fwk.comm.tcp.mina.MinaTcpServerImpl;
import com.chinawiserv.fwk.constant.ETcpAppProtocol;
import com.chinawiserv.fwk.session.CWSessionEventListener;
 
/**
 * <li>文件名称: TcpServerSession.java</li>
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
public class CWTcpServer {
	private CWTcpServerImpl impl = null;
	private ETcpAppProtocol protocol = null;
	
	public CWTcpServer( String _ipAddr, int _port ) {
		impl = new MinaTcpServerImpl( _ipAddr, _port );
	}
	
	public CWTcpServer( int _port ) {
		impl = new MinaTcpServerImpl( _port );
	}
	
	public void setCWTcpServerImpl( CWTcpServerImpl _impl )
	{
		impl = _impl;
	}
	
	public boolean open() {
		this.protocol = ETcpAppProtocol.P_TEXT_UTF8;
		return impl.open(this.protocol);
	}
	
	public boolean open(ETcpAppProtocol _protocol) {
		this.protocol = _protocol;
		return impl.open(this.protocol);
	}
	
	public boolean close() {
		return true;
	}
	
	public void setCWTcpHandler( CWTcpHandler _handler ) {
		impl.setCWTcpHandler(_handler);
	}
	
	public void setCWSessionEventListener( CWSessionEventListener _listener ) {
		impl.setCWSessionEventListener(_listener);
	}
	 
}
