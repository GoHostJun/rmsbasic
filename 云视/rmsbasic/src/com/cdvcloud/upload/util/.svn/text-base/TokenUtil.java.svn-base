package com.cdvcloud.upload.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cdvcloud.upload.config.Configurations;

/**
 * Key Util: 1> according file name|size ..., generate a key; 2> the key should
 * be unique.
 */
public class TokenUtil {

	/**
	 * 生成Token， A(hashcode>0)|B + |name的Hash值| +_+size的值
	 * 
	 * @param name
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(String name, String product, String size) throws IOException {

		if (name == null || size == null)
			return "";
		// String uuid = UUID.randomUUID().toString();//如果用uuid就不能断点续传
		int code = name.hashCode();
		try {
			// String token = uuid + "_" + size.trim();
			String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_" + size.trim();
			/** TODO: store your token, here just create a file */
			savePath(name, product, token);
			IoUtil.storeToken(token);
			return token;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * 查询Token， A(hashcode>0)|B + |name的Hash值| +_+size的值
	 * 
	 * @param name
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static String findToken(String name, String product, String size) {
		if (name == null || size == null)
			return "";
		int code = name.hashCode();
		String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_" + size.trim();
		savePath(name, product, token);
		return token;
	}

	/**
	 * 获取日期格式的路径
	 * 
	 * @return
	 */
	public static String datePath() {
		return File.separator + getYear() + File.separator + getMonth() + File.separator + getDay() + File.separator;
	}

	/**
	 * 获取对应的文件夹
	 * 
	 * @param name
	 * @param token
	 * @return
	 */
	public static String savePath(String name, String product, String token) {
		String video = Configurations.getStreamVideo();
		String audio = Configurations.getStreamAudio();
		String text = Configurations.getStreamText();
		String picture = Configurations.getStreamPicture();
		name = name.substring(name.lastIndexOf("."));
		name = name.toLowerCase();
		if (null != video && !video.isEmpty()) {
			if ((video.toLowerCase()).contains(name)) {
				IoUtil.map.put(token, product + File.separator + "video" + datePath());
			}
		}
		if (null != audio && !audio.isEmpty()) {
			if ((audio.toLowerCase()).contains(name)) {
				IoUtil.map.put(token, product + File.separator + "audio" + datePath());
			}
		}
		if (null != text && !text.isEmpty()) {
			if ((text.toLowerCase()).contains(name)) {
				IoUtil.map.put(token, product + File.separator + "text" + datePath());
			}
		}
		if (null != picture && !picture.isEmpty()) {
			if ((picture.toLowerCase()).contains(name)) {
				IoUtil.map.put(token, product + File.separator + "picture" + datePath());
			}
		}
		String path = IoUtil.map.get(token);
		return path;
	}

	/**
	 * 获得当前的年份
	 * 
	 * @return
	 */
	public static int getYear() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.YEAR);
	}

	/**
	 * 获得当前的月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前的日
	 * 
	 * @return
	 */
	public static int getDay() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.DAY_OF_MONTH);
	}

}
