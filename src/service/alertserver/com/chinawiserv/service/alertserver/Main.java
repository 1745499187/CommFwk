package com.chinawiserv.service.alertserver;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
	private String configFile;
	
	public Main(String name, String cfg) {
		super(name);
		
		this.configFile = cfg;
		if(this.configFile == null || this.configFile.length() <= 0) {
			this.configFile = "alertServer.properties";
		}
	}

	@Override
	public void start() {
		// initial configuration
		ASConfig.init(this.configFile);
		
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
		wsMain.start(alertDistributor);
//		wsMain.startTest(alertDistributor);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("c", "conf", true, "specialfy the configuration file");
		
		CommandLineParser parser = new BasicParser();
		
		try {
			CommandLine cl = parser.parse(options, args);

			if(cl.hasOption("c")) {
				CommFwkService svcInstance = new Main("AlertServer", cl.getOptionValue("c"));
				svcInstance.start();
			}
			else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(Main.class.getSimpleName(), options );
				System.exit(1);
			}
		} catch (ParseException e) {
			System.out.println("Parse argments fail: " + e.getMessage());
		}
		
	}
}
