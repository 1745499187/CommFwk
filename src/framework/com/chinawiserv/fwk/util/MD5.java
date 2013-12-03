package com.chinawiserv.fwk.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>HTML style description</p>
 *
 * @author weibz
 * @version %VER_M%, %VER_S%
 */
public final class MD5 {
	private static MessageDigest md5;
	
	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] digestRaw(byte[] message) {
		return md5.digest(message);
	}
	
	public static String digest(byte[] message) {
		byte[] ret = md5.digest(message);
		StringBuilder sb = new StringBuilder();
		for(byte b : ret) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
	
	public static String digest(String message) {
		byte[] rawMsg = message.getBytes();
		return digest(rawMsg);
	}
	
	public static String digest(String message, String charset) throws UnsupportedEncodingException {
		byte[] rawMsg = message.getBytes(charset); 
		return digest(rawMsg);
	}
	
	public static String digest(int message) {
		return digest(Integer.toString(message));
	}
	
	public static String digest(long message) {
		return digest(Long.toString(message));
	}
}
