package org.bytemark.bytewheel.test.util;

public class UriUtil {

	public static String getURI(int port, String path){
		return "http://localhost:"+port+path;
	}
}
