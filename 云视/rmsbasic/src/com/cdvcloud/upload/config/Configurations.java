package com.cdvcloud.upload.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.cdvcloud.upload.util.IoUtil;

/**
 * read the configurations from file `config.properties`.
 */
public class Configurations {
	static final String CONFIG_FILE = "stream-config.properties";
	private static Properties properties = null;
	private static final String REPOSITORY = System.getProperty("java.io.tmpdir", File.separator + "tmp"
			+ File.separator + "upload-repository");

	static {
		new Configurations();
	}

	private Configurations() {
		init();
	}

	void init() {
		try {
			ClassLoader loader = Configurations.class.getClassLoader();
			InputStream in = loader.getResourceAsStream(CONFIG_FILE);
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			System.err.println("reading `" + CONFIG_FILE + "` error!");
			e.printStackTrace();
		}
	}

	public static String getConfig(String key) {
		return getConfig(key, null);
	}

	public static String getConfig(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public static int getConfig(String key, int defaultValue) {
		String val = getConfig(key);
		int setting = 0;
		try {
			setting = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			setting = defaultValue;
		}
		return setting;
	}

	public static String getFileRepository(String token) {
		String val = getConfig("STREAM_FILE_REPOSITORY");
		if (val == null || val.isEmpty())
			val = REPOSITORY;
		String childPath = "";
		if (null != token) {
			childPath = File.separator + IoUtil.map.get(token);
		}
		if (val.endsWith("*")) {
			val = val.replace("*", "");
			return val + childPath;
		}
		return val;
	}

	public static String getStreamVideo() {
		return getConfig("STREAM_VIDEO");
	}

	public static String getStreamAudio() {
		return getConfig("STREAM_AUDIO");
	}

	public static String getStreamPicture() {
		return getConfig("STREAM_PICTURE");
	}

	public static String getStreamText() {
		return getConfig("STREAM_TEXT");
	}

	public static String getCrossServer() {
		return getConfig("STREAM_CROSS_SERVER");
	}

	public static String getCrossOrigins() {
		return getConfig("STREAM_CROSS_ORIGIN");
	}

	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getConfig(key));
	}

	public static boolean isDeleteFinished() {
		return getBoolean("STREAM_DELETE_FINISH");
	}

	public static boolean isCrossed() {
		return getBoolean("STREAM_IS_CROSS");
	}
	public static boolean isOpenCollback() {
		return getBoolean("ISOPENCOLLBACK");
	}
	public static String getCollback() {
		return getConfig("COLLBACK");
	}
}
