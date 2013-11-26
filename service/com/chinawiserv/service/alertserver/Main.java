package com.chinawiserv.service.alertserver;

import com.chinawiserv.fwk.comm.tcp.CWTcpServer;
import com.chinawiserv.fwk.constant.ETcpAppProtocol;
import com.chinawiserv.service.AbstractCommFwkService;
import com.chinawiserv.service.CommFwkService;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerEventListener;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerHandler;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;
import com.chinawiserv.service.alertserver.ws.ASWsMain;
import com.chinawiserv.service.alertserver.ws.AlertDistributor;

public class Main extends AbstractCommFwkService {

	public Main(String name) {
		super(name);
	}

	@Override
	public void start() {
		// initial configuration
		String configFile = "alertServer.properties";
		ASConfig.init(configFile);
				
		// start TCP server to wait client connect
		int listenPort = ASConfig.getInstance().getIntValue("ALERT_SERVER_PORT");
		CWTcpServer tcpServer = new CWTcpServer(listenPort);
		
		ASTcpServerSessionManager sessionMgr = new ASTcpServerSessionManager();
		tcpServer.setCWSessionEventListener(new ASTcpServerEventListener(sessionMgr));
		tcpServer.setCWTcpHandler(new ASTcpServerHandler(sessionMgr));
		tcpServer.open(ETcpAppProtocol.P_BINARY);
		
		AlertDistributor alertDistributor = new AlertDistributor(sessionMgr);
		new Thread(alertDistributor).start();
		
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ASWsMain wsMain = new ASWsMain();
//		wsMain.start(alertDistributor);
		wsMain.startTest(alertDistributor);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommFwkService svcInstance = new Main("AlertServer");
		svcInstance.start();
	}
}
