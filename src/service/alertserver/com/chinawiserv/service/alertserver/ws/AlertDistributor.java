package com.chinawiserv.service.alertserver.ws;

import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.chinawiserv.fwk.session.CWSession;
import com.chinawiserv.service.alertserver.tcp.ASTcpServerSessionManager;
import com.chinawiserv.service.alertserver.typedef.ASMsg;

public class AlertDistributor implements Runnable {
	private final static Logger logger = LoggerFactory.getLogger(AlertDistributor.class);

	private ASTcpServerSessionManager sessionMgr;
	private BlockingQueue<ASMsg> alertQueueRaw;
	private AlertCacheCleaner alertCacheCleaner;

	public AlertDistributor(ASTcpServerSessionManager sessionMgr) {
		this.sessionMgr = sessionMgr;
		this.alertQueueRaw = new LinkedBlockingQueue<ASMsg>();
		this.alertCacheCleaner = new AlertCacheCleaner(this.sessionMgr);
		
		this.startCleanTask(1000);
	}

	private void startCleanTask(int period) {
		Timer timer = new Timer(true);
		timer.schedule(alertCacheCleaner, 0, period);
	}
	
	public void putAlert(ASMsg alert) throws InterruptedException {
		this.alertQueueRaw.put(alert);
	}
	
	public int getSize() {
		return this.alertQueueRaw.size();
	}

	@Override
	public void run() {
		while (true) {
			ASMsg alert = null;
			try {
				alert = this.alertQueueRaw.take();
			} catch (InterruptedException e) {
				logger.error("Error when take a alert", e);
				
			}
			if(alert == null)
				continue;
			
			try {
				JSONObject jsonAlert = null;
				jsonAlert = JSONObject.fromObject(alert.getContent());
				JSONArray whoView = jsonAlert.getJSONArray("whoview");
				
				if (whoView == null || whoView.size() <= 0) {
					// no whoview, just store it
					this.alertCacheCleaner.putAlert(alert);
					continue;
				}
				for (int i = 0; i < whoView.size(); i++) {
					alert.registerReader(whoView.getString(i));
				}
				
				for(String reader : alert.getReaders()) {
					CWSession session = this.sessionMgr.get(reader);

					if (session == null) {
						logger.info("User has not connected: " + reader);
						continue;
					} else {
						session.write(alert.toBuffer());
						alert.removeReader(reader);
					}
				}
			} catch (Exception e) {
				logger.error("Error when distribute alerts", e);
			} finally {
				try {
					if(alert.getReaders().size() > 0) {
						this.alertCacheCleaner.putAlert(alert);
						logger.debug("Cached alert: " + alert);
					}
				} catch (InterruptedException e) {
					logger.error("Error when put alert to cache", e);
				}
			}
		}
	}
}
