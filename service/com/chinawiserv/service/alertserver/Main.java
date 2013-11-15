package com.chinawiserv.service.alertserver;

import com.chinawiserv.fwk.comm.tcp.CWTcpServer;
import com.chinawiserv.fwk.constant.ETcpAppProtocol;
import com.chinawiserv.service.AbstractCommFwkService;
import com.chinawiserv.service.CommFwkService;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerEventListener;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerHandler;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;
import com.chinawiserv.service.alertserver.ws.ASWsMain;

public class Main extends AbstractCommFwkService {

	public Main(String name) {
		super(name);
	}

	@Override
	public void start() {
		// start TCP server to wait client connect
		CWTcpServer tcpServer = new CWTcpServer(5000);
		ASTcpServerSessionManager sessionMgr = new ASTcpServerSessionManager();
		tcpServer.setCWSessionEventListener(new ASTcpServerEventListener(sessionMgr));
		tcpServer.setCWTcpHandler(new ASTcpServerHandler(sessionMgr));
		tcpServer.open(ETcpAppProtocol.P_ALERT_SERVER);
		
//		ASWsMain wsMain = new ASWsMain();
//		wsMain.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommFwkService svcInstance = new Main("AlertServer");
		svcInstance.start();
	}
}
