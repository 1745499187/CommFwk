package com.chinawiserv.service.alertserver.ws;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.chinawiserv.fwk.session.CWSession;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;
import com.chinawiserv.service.alertserver.typedef.ASMsg;

public class AlertCacheCleaner extends TimerTask {
	private final static Logger logger = LoggerFactory.getLogger(AlertCacheCleaner.class);
	
	private BlockingQueue<ASMsg> cachedAlerts;
	private ASTcpServerSessionManager sessionMgr;
	
	public AlertCacheCleaner(ASTcpServerSessionManager sessionMgr) {
		this.sessionMgr = sessionMgr;
		this.cachedAlerts = new LinkedBlockingQueue<ASMsg>();
	}
	
	public void putAlert(ASMsg alert) throws InterruptedException {
		this.cachedAlerts.put(alert);
	}
	
	public int getCacheSize() {
		return this.cachedAlerts.size();
	}
	
	@Override
	public void run() {
		int currentMsgCount = this.cachedAlerts.size();
		for(int i=0;i<currentMsgCount;i++) {
			try {
				ASMsg alert = this.cachedAlerts.take();
				
				JSONObject jsonAlert = null;
				jsonAlert = JSONObject.fromObject(alert.getContent());
				JSONArray whoView = jsonAlert.getJSONArray("whoview");
				
				if (whoView == null || whoView.size() <= 0) {
					// no whoview
					this.checkAndStoreBack(alert);
					continue;
				}
				
				for (int j = 0; j < whoView.size(); j++) {
					String userName = whoView.getString(j);
					CWSession session = this.sessionMgr.get(userName);

					if (session == null) {
						this.checkAndStoreBack(alert);
						continue;
					} else {
						session.write(alert.toBuffer());
					}
				}
			} catch(Exception e) {
				logger.error("Error when clean alertCache", e);
			}
		}
	}

	private void checkAndStoreBack(ASMsg alert) throws InterruptedException {
		Date now = new Date();
		long timeDiff = now.getTime() - alert.getTimeStamp().getTime();
		if(timeDiff > 60 * 60 * 1000) {
			this.cachedAlerts.put(alert);
		}
	}
}
