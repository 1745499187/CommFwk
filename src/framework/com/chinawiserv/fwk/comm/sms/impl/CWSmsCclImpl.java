package com.chinawiserv.fwk.comm.sms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wiserv.ccl.sms.main.smsapi.DefaultSms;
import wiserv.ccl.sms.main.smsapi.Sms;

import com.chinawiserv.fwk.comm.sms.CWSmsImpl;
import com.chinawiserv.fwk.comm.sms.typedef.ESmsGatewayType;

public class CWSmsCclImpl implements CWSmsImpl {
	private static final Logger logger = LoggerFactory.getLogger(CWSmsCclImpl.class);
	
	private static CWSmsImpl instance = null;
	
	private Sms smsImpl = new DefaultSms();
	
	public static CWSmsImpl getInstance() {
		if(instance == null) {
			synchronized(CWSmsCclImpl.class) {
				if(instance == null) {
					instance = new CWSmsCclImpl();
				}
			}
		}
		
		return instance;
	}
	
	public boolean sendSms(String mobile, String message, ESmsGatewayType gatewayType) {
		logger.debug("try to send sms to [" + mobile + "] by [" + gatewayType + "] ... ");
		try {
			switch(gatewayType) {
				case SGIP: // 联通
					smsImpl.sendSmsBySGIP(mobile, message);
					break;
				case CMPP: // 移动
					smsImpl.sendSmsByCMPP(mobile, message);
					break;
				case SMGP: // 电信
					smsImpl.sendSmsBySMGP(mobile, message);
					break;
				case CMBC: // 民生银行API
					smsImpl.sendSmsByCMBC(mobile, message);
					break;
				case SMS_CAT: // 短信猫
					smsImpl.sendSmsByCat(mobile, message);
					break;
				case GPRS_CAT: // GPRS 短信猫
					smsImpl.sendSmsByGprsCat(mobile, message);
					break;
				case WEB_SERVICE:
					smsImpl.sendSmsByWS(mobile, message);
					break;
				case WEB:
					smsImpl.sendSmsByWeb(mobile, message);
					break;
				case OTHER:
				default:
					smsImpl.sendSmsByOther(mobile, message);
					break;
			}
		} catch(Exception e) {
			logger.error("sendSms fail", e);
			return false;
		}
		logger.debug("send sms done");
		return true;
	}

}
