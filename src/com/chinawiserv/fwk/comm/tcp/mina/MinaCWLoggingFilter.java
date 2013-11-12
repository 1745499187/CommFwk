package com.chinawiserv.fwk.comm.tcp.mina;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaCWLoggingFilter extends IoFilterAdapter {
	
	protected Logger logger;
	
	protected static String LINE_DELIMITER = "\r\n";
	
	public MinaCWLoggingFilter() {
		logger = LoggerFactory.getLogger(MinaCWLoggingFilter.class);
	}
	
	public MinaCWLoggingFilter(String name) {
		if(name == null) {
			logger = LoggerFactory.getLogger(MinaCWLoggingFilter.class);
		}
		else {
			logger = LoggerFactory.getLogger(name);
		}
	}
	
	public MinaCWLoggingFilter(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}
	
	@Override
    public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) throws Exception {
		logger.warn("Session [" + session + "] exception :", cause);
        nextFilter.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
    	logger.debug("Session [" + session + "] received :" + LINE_DELIMITER + message);
        nextFilter.messageReceived(session, message);
    }

    @Override
    public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
    	logger.info("Session [" + session + "] sent :" + LINE_DELIMITER + writeRequest.getMessage());
        nextFilter.messageSent(session, writeRequest);
    }

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
        logger.debug("Session [" + session + "] created");
        nextFilter.sessionCreated(session);
    }

    @Override
    public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
    	logger.debug("Session [" + session + "] opened");
        nextFilter.sessionOpened(session);
    }

    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
    	logger.debug("Session [" + session + "] idle");
        nextFilter.sessionIdle(session, status);
    }

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
    	logger.debug("Session [" + session + "] closed");
        nextFilter.sessionClosed(session);
    }
}
