package com.chinawiserv.service.alertserver.util;

public class StringUtil {
	private final static char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	/**
	 * 将十六进制字符串转换成byte
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (hexCharToByte(hexChars[pos]) << 4 | hexCharToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte hexCharToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	private static char byteToHexChar(byte b) {
		if(b >= 0 && b <= 15) {
			return HEX_CHAR[b];
		}
		else {
			throw new RuntimeException("Invalid hex num: " + b);
		}
	}

	public static String bytesToHexString(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			sb.append(byteToHexChar((byte)((b >> 4) & 0xf)));
			sb.append(byteToHexChar((byte)(b & 0xf)));
		}
		return sb.toString();
	}
	
	public static void main(String args[]) {
		byte[] bytes = new byte[]{(byte)127, (byte)128, (byte)255, (byte)0};
		String hexString = bytesToHexString(bytes);
		System.out.println(hexString);
		System.out.println(hexStringToBytes(hexString)[2]);
	}
}
