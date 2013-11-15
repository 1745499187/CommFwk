
package com.chinawiserv.service.alertserver.ws;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;

/**
 * <p>HTML style description</p>
 *
 * @author weibz
 * @version %VER_M%, %VER_S%
 */
public class ASWsMain {

    private static String WSNAME = "alertserver";
    
    public void start(ASTcpServerSessionManager sessionMgr){
        String address = "http://127.0.0.1:8081/";

        try{
            ASAlertCollectorWs ws = new ASAlertCollectorWsImpl(sessionMgr);
            JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
            
            factory.setServiceClass(ASAlertCollectorWs.class);
            factory.setServiceBean(ws);
          
            factory.setAddress(address + WSNAME);
            
           
            Server server = factory.create();
            server.start();

         
            System.out.println("webservice start success!");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
}
