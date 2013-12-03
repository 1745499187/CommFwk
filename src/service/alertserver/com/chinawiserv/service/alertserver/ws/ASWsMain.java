
package com.chinawiserv.service.alertserver.ws;


import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.service.alertserver.ASConfig;

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
    	String wsIp = ASConfig.getInstance().getStringValue("WEB_SERVICE_IP", "0.0.0.0");
    	// if port is missing, use 80 as default
    	String wsPort = ASConfig.getInstance().getStringValue("WEB_SERVICE_PORT");
    	this.WS_URI = "http://" + wsIp + ( wsPort == null ? "" : (":"+wsPort) );
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
         
            logger.info("WebService started at: " + realUri);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
