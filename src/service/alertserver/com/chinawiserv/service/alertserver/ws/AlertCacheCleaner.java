package com.chinawiserv.service.alertserver.ws;

import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.session.CWSession;
import com.chinawiserv.service.alertserver.ASConfig;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;
import com.chinawiserv.service.alertserver.typedef.ASMsg;

public class AlertCacheCleaner extends TimerTask {
	private final static Logger logger = LoggerFactory.getLogger(AlertCacheCleaner.class);
	
	private BlockingQueue<ASMsg> cachedAlerts;
	private ASTcpServerSessionManager sessionMgr;
	
	private int MSG_CACHE_TIME = 0;
	
	public AlertCacheCleaner(ASTcpServerSessionManager sessionMgr) {
		this.sessionMgr = sessionMgr;
		this.cachedAlerts = new LinkedBlockingQueue<ASMsg>();
		
		this.MSG_CACHE_TIME = ASConfig.getInstance().getIntValue("MSG_CACHE_TIME", 60);
	}
	
	public void putAlert(ASMsg alert) throws InterruptedException {
		this.cachedAlerts.put(alert);
		logger.debug("Cached alert: " + alert);
	}
	
	public int getCacheSize() {
		return this.cachedAlerts.size();
	}
	
	@Override
	public void run() {
		int currentMsgCount = this.cachedAlerts.size();
		
		logger.debug("Start process cached alerts, cache size: " + currentMsgCount);
		
		if(currentMsgCount > 0) {
			for(int i=0;i<currentMsgCount;i++) {
				try {
					ASMsg alert = this.cachedAlerts.take();
					
					if (alert.getReaders().size() <= 0) {
						// no whoview
						this.checkAndStoreBack(alert);
						continue;
					}
					else {
						Iterator<String> ite = alert.readerIterator();
						while(ite.hasNext()) {
							String reader = ite.next();
							CWSession session = this.sessionMgr.get(reader);
		
							if (session == null) {
								continue;
							} else {
								session.write(alert.toBuffer());
								ite.remove();
							}
						}
						
						if(alert.getReaders().size() > 0) {
							this.checkAndStoreBack(alert);
						}
					}
				} catch(Exception e) {
					logger.error("Error when clean alertCache", e);
				}
			}
		}
	}

	private void checkAndStoreBack(ASMsg alert) throws InterruptedException {
		Date now = new Date();
		long timeDiff = now.getTime() - alert.getTimeStamp().getTime();
		if(timeDiff < this.MSG_CACHE_TIME * 60 * 1000) {
			this.cachedAlerts.put(alert);
		}
		else {
			logger.debug("Alert is expired, system has dropped. Alert: " + alert);
		}
	}
}
