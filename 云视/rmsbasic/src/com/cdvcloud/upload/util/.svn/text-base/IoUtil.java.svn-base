package com.cdvcloud.upload.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.cdvcloud.upload.config.Configurations;
import com.cdvcloud.upload.servlet.FormDataServlet;
import com.cdvcloud.upload.servlet.Range;
import com.cdvcloud.upload.servlet.StreamServlet;


/**
 * IO--closing, getting file name ... main function method
 */
public class IoUtil {
	static final Pattern RANGE_PATTERN = Pattern.compile("bytes \\d+-\\d+/\\d+");
	public static Map<String, String> map = new ConcurrentHashMap<String, String>();
	public static Map<String, String> mapFileName = new ConcurrentHashMap<String, String>();

	/**
	 * According the key, generate a file (if not exist, then create a new
	 * file).
	 * 
	 * @param filename
	 * @param fullPath
	 *            the file relative path(something like `a../bxx/wenjian.txt`)
	 * @return
	 * @throws IOException
	 */
	public static File getFile(String filename, String token) throws IOException {
		if (filename == null || filename.isEmpty())
			return null;
		String name = filename.replaceAll("/", Matcher.quoteReplacement(File.separator));
		File f = new File(Configurations.getFileRepository(token) + File.separator + File.separator + name);
		String strPath = f.getParent();
		createFile(strPath);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();			
		}
		if (!f.exists())
			f.createNewFile();

		return f;
	}

	/**
	 * Acquired the file.
	 * 
	 * @param key
	 * @return
	 * @throws FileNotFoundException
	 *             If key not found, will throws this.
	 */
	public static File getTokenedFile(String key) throws FileNotFoundException {
		if (key == null || key.isEmpty())
			return null;

		String filename4File = Configurations.getFileRepository(key) + File.separator + key;
		filename4File = filename4File.replace("//", "/");
		File f = new File(filename4File);
		String strPath = f.getParent();
		createFile(strPath);
		if (!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		if (!f.exists())
			throw new FileNotFoundException("File `" + f + "` not exist.");

		return f;
	}

	public static void storeToken(String key) throws IOException {
		if (key == null || key.isEmpty())
			return;

		String filename4File = Configurations.getFileRepository(key) + File.separator + key;
		filename4File = filename4File.replace("//", "/");
		File f = new File(filename4File);
		String strPath = f.getParent();
		createFile(strPath);
		if (!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		if (!f.exists())
			f.createNewFile();
	}

	/**
	 * close the IO stream.
	 * 
	 * @param stream
	 */
	public static void close(Closeable stream) {
		try {
			if (stream != null)
				stream.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 获取Range参数
	 * 
	 * @param req
	 * @return
	 * @throws IOException
	 */
	public static Range parseRange(HttpServletRequest req) throws IOException {
		String range = req.getHeader(StreamServlet.CONTENT_RANGE_HEADER);
		Matcher m = RANGE_PATTERN.matcher(range);
		if (m.find()) {
			range = m.group().replace("bytes ", "");
			String[] rangeSize = range.split("/");
			String[] fromTo = rangeSize[0].split("-");

			long from = Long.parseLong(fromTo[0]);
			long to = Long.parseLong(fromTo[1]);
			long size = Long.parseLong(rangeSize[1]);

			return new Range(from, to, size);
		}
		throw new IOException("Illegal Access!");
	}

	/**
	 * From the InputStream, write its data to the given file.
	 */
	public static long streaming(InputStream in, String key, String fileName) throws IOException {
		OutputStream out = null;
		File f = getTokenedFile(key);
		try {
			out = new FileOutputStream(f);
			System.out.println("IoUtil.streaming()-filePath="+f.getPath());
			int read = 0;
			final byte[] bytes = new byte[FormDataServlet.BUFFER_LENGTH];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			System.out.println("IoUtil.streaming()-传输完毕！token="+key+"地址是："+f);
			out.flush();
		} finally {
			close(out);
		}
		String newFileName = newFileName(fileName, key);
		File dst = IoUtil.getFile(newFileName, key);
		IoUtil.map.remove(key);
		IoUtil.mapFileName.put(key, newFileName);
		/** rename the file * fix the `renameTo` bug */
		System.out.println("传输完毕后执行：dst="+dst);
		dst.delete();
		f.renameTo(dst);
		long length = getFile(fileName, key).length();
		/** if `STREAM_DELETE_FINISH`, then delete it. */
		if (Configurations.isDeleteFinished()) {
			dst.delete();
		}
		return length;
	}
	/**
	 * 生成新的文件路径，在原文件名后添加token
	 * 
	 * @param fileName
	 *            \xxx\文件名_时间戳.后缀名
	 * @param token
	 * @return
	 */
	public static String newFileName(String fileName, String token) {
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String name = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
		String parentFilePath = fileName.substring(0, fileName.lastIndexOf(File.separator) + 1);
		String strTime = "";
		// strTime = "_" + System.currentTimeMillis();
		name = UUID.randomUUID().toString();
		String newFileName = parentFilePath + name + strTime + suffix;
		return newFileName;
	}
	
	/**
	 * 把创建文件夹的方法提取公共处理
	 * 
	 * @param file
	 * @author zhangcz
	 * @Date 2014-9-4 上午9:50:35
	 */

	public static void createFile(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
			linuxToken(filePath);
		}
	}
	
	/**
	 * 给linux目录赋权限
	 * 
	 * @param path
	 *            /ss/dd/ff
	 * @return
	 */
	public static boolean linuxToken(String path) {
		return true;
	}
}
