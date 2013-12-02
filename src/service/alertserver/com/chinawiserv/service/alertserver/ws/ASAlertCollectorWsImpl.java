package com.chinawiserv.service.alertserver.ws;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;
import com.chinawiserv.service.alertserver.typedef.ASMsg;

@WebService(endpointInterface = "com.chinawiserv.service.alertserver.ws.ASAlertCollectorWs", targetNamespace="http://ws.itm.onecenter.com")
public class ASAlertCollectorWsImpl implements ASAlertCollectorWs{
	private final static Logger logger = LoggerFactory.getLogger(ASAlertCollectorWsImpl.class);
	
	private AlertDistributor alertDistributor;
	
	public ASAlertCollectorWsImpl(AlertDistributor alertDistributor) {
		this.alertDistributor = alertDistributor;
	}
	
    public void sendInfo(String info){
        logger.debug("Reveived info: "+info);
        
        try {
			this.alertDistributor.putAlert(new ASMsg(info));
		} catch (InterruptedException e) {
			logger.error("Error when put alert to distributor", e);
		}
    }
}
