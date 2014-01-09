package com.chinawiserv.service.alertserver.typedef;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.chinawiserv.fwk.comm.tcp.buffer.CWIoBuffer;
import com.chinawiserv.fwk.constant.CWCharset;
import com.chinawiserv.fwk.util.CWPrintableDO;

public class ASMsg extends CWPrintableDO {
	/** The serialVersionUID */
	private static final long serialVersionUID = -597125387182106725L;
	
	public final static String VERSION = "0001";
	
	/** The content */
	private String content;
	private Set<String> readers;
	
	private Date timeStamp;

	public ASMsg() {
		this.readers = new HashSet<String>();
	}
	
	public ASMsg(String content) {
		this.content = content;
		this.timeStamp = new Date();
		this.readers = new HashSet<String>();
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
	
	public Set<String> getReaders() {
		return this.readers;
	}
	
	public void registerReader(String name) {
		synchronized(this.readers) {
			this.readers.add(name);
		}
	}
	
	public void removeReader(String name) {
		synchronized(this.readers) {
			this.readers.remove(name);
		}
	}
	
//	public Iterator<String> readerIterator() {
//		return this.readers.iterator();
//	}
	
	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public CWIoBuffer toBuffer() throws CharacterCodingException {
		return this.toBuffer(CWCharset.UTF_8.CHARSET);
	}
	
	public CWIoBuffer toBuffer(Charset charset) throws CharacterCodingException {
		CWIoBuffer bufOut = null, bufTmp = null;
		CharsetEncoder encoder = charset.newEncoder();
		
		bufTmp = CWIoBuffer.allocate(512).setAutoExpand(true);
		
		bufTmp.putString(this.getContent(), encoder);
		bufTmp.flip();
		
		// set TCP-PDU header
		int msgBufLen = bufTmp.remaining();
		bufOut = CWIoBuffer.allocate(msgBufLen+8)
				.putString(ASMsg.VERSION, encoder) // version
				.putInt(msgBufLen) // msg len
				.put(bufTmp); // msg
		bufOut.flip();
		
		return bufOut;
	}
}
