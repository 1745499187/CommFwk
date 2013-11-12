package com.chinawiserv.service.test;

import org.apache.log4j.PropertyConfigurator;

import com.chinawiserv.fwk.comm.tcp.CWTcpServer;
import com.chinawiserv.fwk.constant.ETcpAppProtocol;

/**
 * <li>文件名称: TestMain.java</li>
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
public class TestCWServer {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CWTcpServer server = new CWTcpServer(5000);
		TestCWTcpServerHandler hnd = new TestCWTcpServerHandler();
		server.setCWTcpHandler(hnd);
		server.setCWSessionEventListener(hnd);
		server.open(ETcpAppProtocol.P_TEXT_UTF8);
	}

}
