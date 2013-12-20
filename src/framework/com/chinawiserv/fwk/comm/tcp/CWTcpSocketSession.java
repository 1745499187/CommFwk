package com.chinawiserv.fwk.comm.tcp;

import com.chinawiserv.fwk.session.CWAbstractSession;

/**
 * <li>文件名称: TcpSocketSession.java</li>
 * <li>文件描述: 本类描述</li>
 * <li>版权所有: 版权所有(C)2005-2013</li>
 * <li>公司: 勤智数码</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>完成日期：2013-10-28</li>
 * <li>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</li>
 * @version 1.0
 * @author FWK Team
 */
public class CWTcpSocketSession extends CWAbstractSession {
	 private CWTcpSocketSessionImpl impl;
	 
	 public CWTcpSocketSession() { 
	 }
	 
	 public void setCWTcpSocketSessionImpl( CWTcpSocketSessionImpl _impl ) {
		 impl = _impl;
	 }
	 
	 
	public Object read() {
		 return impl.read();
	 }
	 
	 
	public Object read(int timeout) {
		 return impl.read(timeout);
	 }
	 
	 
	public void write( Object message ) {
		 impl.write(message);
	 }
	 
	 
	public boolean close() {
		 return impl.close();
		 
	 }
	 
	 public boolean isConnected() {
		 return impl.isConnected();
		 
	 }
	 
	 public boolean isClosing() {
		 return impl.isClosing();
		 
	 }
//	 
//	 public SocketAddress getRemoteAddress() {
//		 
//	 }
//	 
//	 public SocketAddress getLocalAddress() {
//		 
//	 }
//	 
//	 public void suspendRead() {
//		 
//	 }
//	 
//	 public void suspendWrite() {
//		 
//	 }
//	 
//	 public void resumeRead() {
//		 
//	 }
//	 
//	 public void resumeWrite() {
//		 
//	 }
//	 
//	 public boolean isReadSuspended() {
//		 
//	 }
//	 
//	 public boolean isWriteSuspended() {
//		 
//	 }
//	 
//	 public long getReadBytes() {
//		 
//	 }
//	 
//	 public long getWrittenBytes() {
//		 
//	 }
//	 
//	 public long getReadMessages() {
//		 
//	 } 
}
