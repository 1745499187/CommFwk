package com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver.typedef;

import com.chinawiserv.fwk.util.CWPrintableDO;

public class ASTcpMsg extends CWPrintableDO {
	/** The serialVersionUID */
	private static final long serialVersionUID = -597125387182106725L;
	
	public final static String VERSION = "0001";
	
	/** The content */
	private String content;

	public ASTcpMsg() {
		
	}
	
	public ASTcpMsg(String content) {
		this.content = content;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
