package test.service;
 
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.chinawiserv.fwk.comm.tcp.CWTcpClient;
import com.chinawiserv.fwk.constant.ETcpAppProtocol;
import com.chinawiserv.service.CommFwkService;
import com.chinawiserv.service.alertserver.Main;

/**
 * <li>文件名称: TestCWSocket.java</li>
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
public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Options opt = new Options();
		opt.addOption("a", "address", true, "server address");
		opt.addOption("P", "port", true, "server port");
		opt.addOption("u", "username", true, "user name");
		opt.addOption("p", "password", true, "password");
		
		CommandLineParser parser = new BasicParser();
		
		try {
			CommandLine cl = parser.parse(opt, args);
			
			String addr = cl.getOptionValue("a", "127.0.0.1");
			String port = cl.getOptionValue("P", "9901");
			String user = cl.getOptionValue("u", "admin");
			String pwd = cl.getOptionValue("p", "admin");
			
			System.out.println("Try to connect AlertServer ["+addr+":"+port+"] with ID ["+user+"/"+pwd+"]");
			
			CWTcpClient sock = new CWTcpClient(addr, Integer.parseInt(port));
			sock.setCWTcpHandler(new TestClientHandler(user, pwd));
			sock.setCWSessionEventListener(new TestClientSessionEventListener());
			sock.open(ETcpAppProtocol.P_BINARY); 
		} catch (ParseException e) {
			System.out.println("Parse argments fail: " + e.getMessage());
		}
	}
}
