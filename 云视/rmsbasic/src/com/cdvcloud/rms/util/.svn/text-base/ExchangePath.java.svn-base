package com.cdvcloud.rms.util;

import java.io.File;

/**
 * 系统路径转换工具类
 * @version： v1.0
 * @author huangaigang
 * @date 2015-11-29 23:06:20
 */
public class ExchangePath {

	private static String linuxPath2WindowsPath(String linuxPath) {
		return "\\" + linuxPath.replace("/", "\\");

	}

	public static String windowsPath2LinuxPath(String windowsPath) {
		String linuxPath = windowsPath.replace("\\", "/");
		return linuxPath.substring(1);
	}

	/**
	 * 过滤路径，根据系统的输出相应格式的路径
	 * @param path
	 * @return
	 */
	public static String PathChangeUrl(String path) {
		if (File.separator.equals("\\")) {
			path = ExchangePath.linuxPath2WindowsPath(path);
		}
		return path;
	}

}
