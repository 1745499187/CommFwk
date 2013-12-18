package test.framework;

import com.chinawiserv.fwk.comm.sms.CWSmsGateway;
import com.chinawiserv.fwk.comm.sms.CWSmsSession;
import com.chinawiserv.fwk.comm.sms.typedef.ESmsGatewayType;
import com.chinawiserv.fwk.constant.CWFwkConstant;
import com.chinawiserv.fwk.core.CWFramework;

public class TestCWFramework {
	
	public void doTest() {
//		System.setProperty(CWFwkConstant.JVM_KEY.FWK_CONF, "./conf/framework/framework.properties");
		CWFramework.getInstance();
		
		CWSmsGateway smsGw = CWFramework.getInstance().getSmsGateway();
		CWSmsSession smsSession = smsGw.getSessionManager().openSession();
		smsSession.sendSms("", "", ESmsGatewayType.SGIP);
		
		smsSession.close();
	}
	
	public static void main(String args[]) {
		TestCWFramework tt = new TestCWFramework();
		tt.doTest();
	}
}
