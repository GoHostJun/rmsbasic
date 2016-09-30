package com.cdvcloud.rms.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.upload.config.Configurations;
import com.cdvcloud.upload.util.IoUtil;
import com.cdvcloud.upload.util.TokenUtil;

/**
 * File工具类
 * 
 * @version： v1.0
 * @author huangaigang
 * @date 2015-11-29 23:06:20
 */
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);
	private static final BlockingQueue<File> queue = new ArrayBlockingQueue<File>(100);
	private static final int _BUFFER_BYTE_LENGTH = 1024;

	/**
	 * 获取日期格式的路径 年/月/日/
	 * 
	 * @return
	 */
	public static String datePath() {
		return File.separator + DateUtil.getYear() + File.separator + DateUtil.getMonth() + File.separator + DateUtil.getDay() + File.separator;
	}

	public static String linuxDatePath() {
		return "/" + DateUtil.getYear() + "/" + DateUtil.getMonth() + "/" + DateUtil.getDay() + "/";
	}

	/**
	 * 检查路径，如果没有则创建
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createPath(String filePath) {
		boolean isCreate = false;
		File file = new File(filePath);
		if (!file.exists()) {
			isCreate = file.mkdirs();
		}
		return isCreate;
	}

	/**
	 * 获取指定目录的第一个文件
	 * 
	 * @param filepath
	 * @return
	 */
	public static File getFirstFile(String filepath) {
		File file = new File(filepath);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (null != files && files.length > 0) {
				return files[0];
			}
		}
		return null;
	}

	/**
	 * 获取指定目录的文件集合
	 * 
	 * @param filepath
	 * @return
	 */
	public static List<File> getFiles(String filepath) {
		List<File> list = new ArrayList<File>();
		File file = new File(filepath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.renameTo(file2)) {
					list.add(file2);
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * 获取指定目录的文件集合
	 * 
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	public static BlockingQueue<File> getQueue(String filepath) {
		BlockingQueue<File> q = new ArrayBlockingQueue<File>(1000);
		try {
			File file = new File(filepath);
			if (file.exists()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					if (file2.renameTo(file2)) {
						q.put(file2);
					}
				}
				return q;
			}
			return null;
		} catch (Exception e) {
			logger.error("获取文件队列出错：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 扫描指定目录的文件
	 * 
	 * @param filepath
	 *            目录
	 * @param rname
	 *            添加的后缀名
	 * @param fileNameFilter
	 *            名称过滤
	 * @return
	 * @throws Exception
	 */
	public static BlockingQueue<File> doFileList(String filepath, String rname, FilenameFilter fileNameFilter) throws Exception {
		File file = new File(filepath);
		if (!file.exists()) {
			logger.error("路径不存在：" + filepath);
			return null;
		}
		File[] files = file.listFiles(fileNameFilter);
		if (null != rname) {
			for (File f : files) {
				if (f.renameTo(f)) {
					f = renameFile(f, rname);
					queue.put(f);
				}
			}
		} else {
			for (File f : files) {
				if (f.renameTo(f)) {
					queue.put(f);
				}
			}
		}
		return queue;
	}

	/**
	 * 扫描指定目录的文件
	 * 
	 * @param filepath
	 *            目录
	 * @param rname
	 *            添加的后缀名
	 * @param fileNameFilter
	 *            名称过滤
	 * @return
	 * @throws InterruptedException
	 */
	public static BlockingQueue<File> doRecordFileList(String filepath, String rname, FilenameFilter fileNameFilter) throws InterruptedException {
		File file = new File(filepath);
		if (!file.exists()) {
			logger.error("路径不存在：" + filepath);
			return null;
		}
		File[] files = file.listFiles(fileNameFilter);
		if (null != rname) {
			for (File f : files) {
				if (f.renameTo(f)) {
					f = renameFile(f, rname);
					queue.put(f);
				}
			}
		} else {
			for (File f : files) {
				if (f.renameTo(f)) {
					queue.put(f);
				}
			}
		}
		return queue;
	}

	/**
	 * 重命名文件
	 * 
	 * @param file
	 *            E:\test.avi
	 * @param replaceName
	 *            _vms
	 * @return E:\test_vms.avi
	 */
	public static File renameFile(File file, String replaceName) {
		String newName = file.getName();
		newName = newName.replace(".", replaceName + ".");
		String parentPath = file.getParent();
		String newFileName = parentPath + File.separator + newName;
		File newFile = new File(newFileName);
		file.renameTo(newFile);// 重命名
		return newFile;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path) {
		File f = new File(path);
		if (f.exists()) {
			return f.delete();
		}
		return false;
	}

	/**
	 * 使用文件通道的方式复制文件
	 * 
	 * @param srcFilename
	 * @param destFilename
	 */
	public static boolean fileChannelCopy(String srcFilename, String destFilename) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			File srcFile = new File(srcFilename);
			if (srcFile.exists()) {
				File destFile = new File(destFilename);
				if (!destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				if (!destFile.exists()) {
					destFile.createNewFile();
				}
				fi = new FileInputStream(srcFile);
				fo = new FileOutputStream(destFile);
				in = fi.getChannel();// 得到对应的文件通道
				out = fo.getChannel();// 得到对应的文件通道
				ByteBuffer dsts = ByteBuffer.allocate(1024000);
				try {
					while ((in.read(dsts)) != -1) {
						dsts.flip();// 做好被写的准备
						out.write(dsts);
						dsts.clear();// 做好被读的准备
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// in.transferTo(0, in.size(), out);//
				// 连接两个通道，并且从in通道读取，然后写入out通道
				return true;
			}
			return false;
		} catch (IOException e) {
			logger.error("拷贝文件错误：" + e.getMessage());
			return false;
		} finally {
			try {
				if (null != fi) {
					fi.close();
				}
				if (null != in) {
					in.close();
				}
				if (null != fo) {
					fo.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("关闭流错误：" + e.getMessage());
			}
		}
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getSuffix(String filename) {
		String suffix = null;
		if (!filename.isEmpty()) {
			suffix = filename.substring(filename.lastIndexOf(".") + 1);
		}
		return suffix;
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileName(String filename) {
		if (!filename.isEmpty() && filename.indexOf(".") > 0) {
			filename = filename.substring(0, filename.lastIndexOf("."));
		}
		return filename;
	}

	/**
	 * 将源文件的数据写入到目标文件中， 不会检查源文件是否存在， 若目标文件存在则直接写入， 否则创建目标文件后再进行写入。
	 * 
	 * @param srcPath
	 * @param desPath
	 */
	public static void copyFile(String srcPath, String desPath) {
		File fileSrc = new File(srcPath);
		File fileDest = new File(desPath);
		if (!fileSrc.isFile()) {
			logger.error("图片不存在" + srcPath);
		}
		if (!fileDest.exists()) {
			String strPath = fileDest.getParent();
			createPath(strPath);
		}
		try {
			FileInputStream in = new FileInputStream(srcPath);
			FileOutputStream out = new FileOutputStream(desPath);
			byte[] bt = new byte[1024 * 1024];
			int count;
			while ((count = in.read(bt)) > 0) {
				out.write(bt, 0, count);
			}
			in.close();
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
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

	/**
	 * 获取对应的文件夹
	 * 
	 * @param suffix
	 * @return /type/yyyy/MM/dd/
	 */
	public static String savePath(String suffix) {
		String video = Configurations.getStreamVideo();
		String audio = Configurations.getStreamAudio();
		String text = Configurations.getStreamText();
		String picture = Configurations.getStreamPicture();
		String type = "upload";
		suffix = suffix.toLowerCase();
		if (null != video && !video.isEmpty()) {
			if ((video.toLowerCase()).contains(suffix)) {
				type = "video";
			}
		}
		if (null != audio && !audio.isEmpty()) {
			if ((audio.toLowerCase()).contains(suffix)) {
				type = "audio";
			}
		}
		if (null != text && !text.isEmpty()) {
			if ((text.toLowerCase()).contains(suffix)) {
				type = "text";
			}
		}
		if (null != picture && !picture.isEmpty()) {
			if ((picture.toLowerCase()).contains(suffix)) {
				type = "picture";
			}
		}
		return File.separator + type + datePath();
	}

	/**
	 * 获取对应的文件夹
	 * 
	 * @param putUrl
	 * @return type
	 */
	public static String getMaterialType(String putUrl) {
		getSuffix(putUrl);
		String video = Configurations.getStreamVideo();
		String audio = Configurations.getStreamAudio();
		String text = Configurations.getStreamText();
		String picture = Configurations.getStreamPicture();
		String type = "upload";
		String suffix = getSuffix(putUrl).toLowerCase();
		if (null != video && !video.isEmpty()) {
			if ((video.toLowerCase()).contains(suffix)) {
				type = Constants.VIDEO_TYPE;
			}
		}
		if (null != audio && !audio.isEmpty()) {
			if ((audio.toLowerCase()).contains(suffix)) {
				type = Constants.AUDIO_TYPE;
			}
		}
		if (null != text && !text.isEmpty()) {
			if ((text.toLowerCase()).contains(suffix)) {
				type = Constants.TEXT_TYPE;
			}
		}
		if (null != picture && !picture.isEmpty()) {
			if ((picture.toLowerCase()).contains(suffix)) {
				type = Constants.PICTURE_TYPE;
			}
		}
		return type;
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
	 * 根据文件路径获取文件的字节大小
	 * 
	 * @param strFilepath
	 * @return
	 */
	public static Long getFileSize(String strFilepath) {
		File file = new File(strFilepath);
		if (file.isFile()) {
			return file.length();
		}
		return 0L;
	}

	/**
	 * 获取媒体类型
	 * 
	 * @param mtype
	 *            0：视频；1音频；2图片；3文件；
	 * @return
	 */
	public static String getMtype(String mtype) {
		String type = "";
		if ((mtype.toLowerCase()).contains(Constants.VIDEO_TYPE)) {
			type = "0";
		}
		if ((mtype.toLowerCase()).contains(Constants.AUDIO_TYPE)) {
			type = "1";
		}
		if ((mtype.toLowerCase()).contains(Constants.PICTURE_TYPE)) {
			type = "2";
		}
		if ((mtype.toLowerCase()).contains(Constants.TEXT_TYPE)) {
			type = "3";
		}
		return type;
	}

	public static String getSystemPath(String strFilepath) {
		if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			strFilepath = strFilepath.replaceAll("\\\\", "/");
			strFilepath = strFilepath.replaceAll("//", "/");
		} else {
			// strFilepath = strFilepath.replaceAll("/", "\\");
		}
		return strFilepath;
	}

	/**
	 * 压缩文件并下载
	 * 
	 * @param request
	 * @param response
	 * @param mapParams
	 *            文件集合，如：{测试_PC_SHD.mp4=http://123.12.1.3/1234/qwert1234.mp4}
	 * @param strZipName
	 *            压缩包名称，如：测试
	 */
	public static void downloadHTTP4Zip(HttpServletRequest request, HttpServletResponse response, Map<String, String> mapParams, String strZipName) {
		if (null != mapParams) {
			ZipOutputStream zos = null;
			InputStream in = null;
			try {
				response.setContentType("APPLICATION/OCTET-STREAM");
				strZipName += ".zip";
				strZipName = (strZipName.trim()).replace(" ", "");
				strZipName = new String(strZipName.getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + strZipName + "\"");
				zos = new ZipOutputStream(response.getOutputStream());
				URL url = null;
				byte[] b = new byte[_BUFFER_BYTE_LENGTH];
				Set<String> setKeys = mapParams.keySet();
				for (String strKey : setKeys) {
					String strUrls = mapParams.get(strKey);
					zos.putNextEntry(new ZipEntry(strKey));
					if (!"info.xml".equalsIgnoreCase(strKey)) {
						url = new URL(strUrls);
						URLConnection urlconn = url.openConnection();
						in = urlconn.getInputStream();
						int intLength = 0;
						while ((intLength = in.read(b)) != -1) {
							zos.write(b, 0, intLength);
						}
						in.close();
					}else{
						File fileXml = new File(strUrls);
						in = new FileInputStream(fileXml);
						int intLength = 0;
						while ((intLength = in.read(b)) != -1) {
							zos.write(b, 0, intLength);
						}
						in.close();
					}
				}
				zos.flush();
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("压缩文件下载异常：" + e.getMessage());
			} finally {
				if (null != zos) {
					try {
						zos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 下载http路径的文件
	 * 
	 * @param request
	 * @param response
	 * @param strUrl
	 *            文件路径，如：http://123.12.1.3/1234/qwert1234.mp4
	 * @param strTitle
	 *            下载时显示的文件名称
	 */
	public static void downloadHTTP(HttpServletRequest request, HttpServletResponse response, String strUrl, String strTitle) {
		OutputStream os = null;
		BufferedOutputStream bos = null;
		InputStream in = null;
		try {
			os = response.getOutputStream();
			bos = new BufferedOutputStream(os);
			URL url = new URL(strUrl);
			URLConnection urlconn = url.openConnection();
			in = urlconn.getInputStream();
			response.reset();
			strTitle = new String(strTitle.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + strTitle + "\"");
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentLength(urlconn.getContentLength());
			byte[] b = new byte[_BUFFER_BYTE_LENGTH];
			int intLength = 0;
			while ((intLength = in.read(b)) != -1) {
				bos.write(b, 0, intLength);
				bos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("下载文件时异常：" + e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据文件名和文件大小获取文存放路径
	 * 
	 * @param fileName
	 * @param fileSize
	 * @param productCode
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String getFileDepositUrl(String fileName, String fileSize, String productCode) {
		logger.info("根据文件名和文件大小获取文存放路径:fileName="+fileName+";fileSize="+fileSize+";productCode="+productCode);
		String token = TokenUtil.findToken(fileName, productCode, fileSize);
		logger.info("根据文件名和文件大小获取文存放路径:token="+token);
		String filepath = Configurations.getFileRepository(token);
		logger.info("根据文件名和文件大小获取文存放路径:filepath="+filepath);
		if (!filepath.endsWith(File.separator)) {
			filepath += File.separator;
		}
		logger.info("根据文件名和文件大小获取文存放路径:new_filepath="+filepath);
		String name = IoUtil.mapFileName.get(token);
		logger.info("根据文件名和文件大小获取文存放路径:name="+name);
		String fileDepositUrl = filepath + name;
		logger.info("根据文件名和文件大小获取文存放路径:fileDepositUrl="+fileDepositUrl);
		IoUtil.mapFileName.remove(token);
		return fileDepositUrl;
	}

	/**
	 * 获取文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getName(String filename) {
		filename = filename.replace("\\", "/");
		if (!filename.isEmpty() && filename.indexOf("/") > 0) {
			filename = filename.substring(filename.lastIndexOf("/") + 1);
		}
		return filename;
	}
}
