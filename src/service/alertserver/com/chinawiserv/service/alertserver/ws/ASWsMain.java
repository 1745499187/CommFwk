
package com.chinawiserv.service.alertserver.ws;

import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.service.alertserver.ASConfig;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;
import com.chinawiserv.service.alertserver.typedef.ASMsg;

/**
 * <p>HTML style description</p>
 *
 * @author weibz
 * @version %VER_M%, %VER_S%
 */
public class ASWsMain {
	private final static Logger logger = LoggerFactory.getLogger(ASWsMain.class); 
	
	private String WS_URI = null;
    private String WS_NAME = null;
    
    public ASWsMain() {
    	this.WS_URI = ASConfig.getInstance().getStringValue("WEB_SERVICE_URL");
    	this.WS_NAME = ASConfig.getInstance().getStringValue("WEB_SERVICE_NAME");
    }
    
    public void start(AlertDistributor alertDistributor){
        String realUri = this.WS_URI+"/"+this.WS_NAME;

        try{
            ASAlertCollectorWs ws = new ASAlertCollectorWsImpl(alertDistributor);
            JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
            
            factory.setServiceClass(ASAlertCollectorWs.class);
            factory.setServiceBean(ws);
          
            factory.setAddress(realUri);
           
            Server server = factory.create();
            server.start();
         
            logger.info("Webservice started successfully!");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void startTest(AlertDistributor alertDistributor){
        try{
            logger.info("Webservice started successfully!");
            
            Timer timer = new Timer();
            timer.schedule(new TestTimerTask(alertDistributor), 0, 1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}

class TestTimerTask extends TimerTask {
	private AlertDistributor alertDistributor;
	private int count = 0;
	
	public TestTimerTask(AlertDistributor alertDistributor) {
		this.alertDistributor = alertDistributor;
	}
	
	@Override
	public void run() {
		try {
			count ++;
			
			JSONObject json = new JSONObject();
	        JSONArray whoview = new JSONArray();
	        whoview.add("admin");
	        whoview.add("zhouzhi");
	        json.put("whoview", whoview);
	        json.put("info", "this is a alert");
	        json.put("count", count);
	        
	        ASMsg alert = new ASMsg(json.toString());
	        this.alertDistributor.putAlert(alert);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
