package com.chinawiserv.fwk.comm.sms;

import com.chinawiserv.fwk.core.CWException;
import com.chinawiserv.fwk.session.CWAbstractSessionEventListener;
import com.chinawiserv.fwk.session.CWSession;

public class CWSmsSessionEventListener extends CWAbstractSessionEventListener {

	@Override
	public void sessionCreated(CWSession session) throws CWException {
	}

	@Override
	public void sessionOpened(CWSession session) throws CWException {
	}

	@Override
	public void sessionClosed(CWSession session) throws CWException {
	}

	@Override
	public void exceptionCaught(CWSession session, Throwable cause)
			throws CWException {
	}

}
