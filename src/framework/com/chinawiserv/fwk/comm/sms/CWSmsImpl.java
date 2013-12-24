package com.chinawiserv.fwk.comm.sms;

import com.chinawiserv.fwk.comm.sms.typedef.ESmsGatewayType;

public interface CWSmsImpl {
	public boolean sendSms(String mobile, String message, ESmsGatewayType gateway);
}
