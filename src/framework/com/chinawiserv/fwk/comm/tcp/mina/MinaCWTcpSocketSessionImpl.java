package com.chinawiserv.fwk.comm.tcp.mina;

import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;

import com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl;

/**
 * <li>文件名称: MinaCWTcpSocketSessionImpl.java</li>
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
public class MinaCWTcpSocketSessionImpl implements CWTcpSocketSessionImpl {
	
	private IoSession minaSession;
	
	public MinaCWTcpSocketSessionImpl(IoSession _minaSession)
	{
		minaSession = _minaSession;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl#read()
	 */
	@Override
	public Object read() {
		ReadFuture rf = minaSession.read();
		//ReadFuture rf = minaSession.read().awaitUninterruptibly();
		return rf.getMessage();
	}
	
	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl#read(int)
	 */
	@Override
	public Object read(int timeout) {
		ReadFuture rf = minaSession.read();
		if(rf.awaitUninterruptibly(timeout)) {
			return rf.getMessage();
		}
		else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl#write(java.lang.Object)
	 */
	@Override
	public void write(Object message) {
		minaSession.write(message);
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl#close()
	 */
	@Override
	public boolean close() { 
		minaSession.close( true );
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return minaSession.isConnected();
	}

	/* (non-Javadoc)
	 * @see com.chinawiserv.fwk.comm.tcp.CWTcpSocketSessionImpl#isClosing()
	 */
	@Override
	public boolean isClosing() {
		return minaSession.isClosing();
	}

}
