package com.hteis.webtest.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PathUtil {
	public static String getResourceFilePath(String fileName) throws UnsupportedEncodingException{
		String dir = PathUtil.class.getClass().getResource("/").getPath();
		dir = URLDecoder.decode(dir, "utf-8");
		return dir.replace('/', '\\').substring(1) + fileName;
	}
}
