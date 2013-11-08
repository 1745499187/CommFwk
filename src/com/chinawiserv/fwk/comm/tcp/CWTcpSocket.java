package com.chinawiserv.fwk.comm.tcp;

import com.chinawiserv.fwk.comm.tcp.mina.MinaTcpServerImpl;
import com.chinawiserv.fwk.comm.tcp.mina.MinaTcpSocketImpl;
import com.chinawiserv.fwk.session.CWSessionEventListener;

/**
 * <li>文件名称: CWTcpSocket.java</li>
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
public class CWTcpSocket {
	private CWTcpSocketImpl impl = null;
	
	public CWTcpSocket( String _remoteIp, int _remotePort ) {
		if( impl == null )
			impl = new MinaTcpSocketImpl( _remoteIp, _remotePort );
		else {
			impl.setRemoteIp(_remoteIp);
			impl.setRemotePort(_remotePort);
		}
	} 
	
	public void setCWTcpServerImpl( CWTcpSocketImpl _impl )
	{
		impl = _impl;
	}
	
	public boolean open() {
		return impl.open();
	}
	
	public boolean close() {
		return impl.close();
	}
	
	public void setCWTcpHandler( CWTcpHandler _handler ) {
		impl.setCWTcpHandler(_handler);
	}
	
	public void setCWSessionEventListener( CWSessionEventListener _listener ) {
		impl.setCWSessionEventListener(_listener);
	}
}
