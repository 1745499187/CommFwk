
package com.chinawiserv.service.alertserver.ws;

import javax.jws.WebService;


@WebService(targetNamespace="http://ws.itm.onecenter.com")
public interface ASAlertCollectorWs {
    
    public void sendInfo(String info);

}
