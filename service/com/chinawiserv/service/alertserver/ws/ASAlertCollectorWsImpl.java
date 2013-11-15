
package com.chinawiserv.service.alertserver.ws;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;


@WebService(endpointInterface = "com.chinawiserv.service.alertserver.ws.AlertCollectorWs", targetNamespace="http://ws.itm.onecenter.com")
public class ASAlertCollectorWsImpl implements ASAlertCollectorWs{
	private final static Logger logger = LoggerFactory.getLogger(ASAlertCollectorWsImpl.class);
	
	private ASTcpServerSessionManager sessionMgr;
	
	public ASAlertCollectorWsImpl(ASTcpServerSessionManager sessionMgr) {
		this.sessionMgr = sessionMgr;
	}
	
    public void sendInfo(String info){
        logger.debug("Reveived info: "+info);
    }
}
