package com.cdvcloud.rms.web.servlet;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.util.MD5Util;



/**
 * 文件上传接口（断点续传）
 * 
 * @author TYW
 * 
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(UploadServlet.class);
//	private ApiService apiService = null;

	public UploadServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.warn("调用v3文件上传接口的方式为GET方式，不通过此请求！");
		map.put("status", 1);
		map.put("message", "系统出错！");
		String json = toJson(map);
		try {
			write(json, response);
		} catch (Exception e) {
			logger.error("v3接口回写信息异常，异常信息：" + e.getMessage());
		}
	}

	/**
	 * 上传文件
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String savePath = null;// 保存路径
		String appKey = ""; // 应用标识
		Integer randomId = 0; // 随机数
		Long currentTime = 0L; // 当前时间
		String veryCode = ""; // 验证信息
		String userId = "";// 用户ID
		String resName = ""; // 资源名称
		String resDesc = ""; // 资源描述信息
		String md5 = "";// 文件的MD5值
		String token = "";// 令牌
		String tipoffName = "";// 爆料人姓名
		String phoneNumber = "";// 电话号码
		String remark = "";// 备注
		String location = "";// 地理位置
		InputStream in = null;
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iter;
		try {
			iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				in = item.openStream();
				if (item.isFormField()) { // 获取form中的表单
					String value = Streams.asString(in, "UTF-8");
					if ("appKey".equals(name)) {
						appKey = value;
					}
					if ("randomId".equals(name)) {
						randomId = Integer.parseInt(value);
					}
					if ("currentTime".equals(name)) {
						currentTime = Long.valueOf(value);
					}
					if ("veryCode".equals(name)) {
						veryCode = value;
					}
					if ("userId".equals(name)) {
						userId = value;
					}
					if ("resName".equals(name)) {
						resName = value;
					}
					if ("resDesc".equals(name)) {
						resDesc = value;
					}
					if ("token".equals(name)) {
						token = value;
					}
					if ("md5".equals(name)) {
						md5 = value;
					}
					if ("tipoffName".equals(name)) {
						tipoffName = value;
					}
					if ("phoneNumber".equals(name)) {
						phoneNumber = value;
					}
					if ("remark".equals(name)) {
						remark = value;
					}
					if ("location".equals(name)) {
						location = value;
					}
				} else {
					// 验证请求参数
					if ("".equals(appKey) || "".equals(veryCode) || "".equals(token) || "".equals(resName) || "".equals(userId)) {
						map.put("status", 1);
						map.put("message", "参数不合法！");
						String json = toJson(map);
						try {
							write(json, response);
						} catch (Exception e) {
							logger.error("v3接口回写信息异常，token=：" + token + "，异常信息：" + e.getMessage());
						}
						return;
					}
					// 验证请求权限
					try {
						boolean flag = checkParam(appKey, randomId, currentTime, veryCode);
						if (!flag) {
							map.put("status", 1);
							map.put("message", "参数不合法！");
							String json = toJson(map);
							try {
								write(json, response);
							} catch (Exception e) {
								logger.error("v3接口回写信息异常（验证请求参数不通过），token=：" + token + "，异常信息：" + e.getMessage());
							}
							return;
						}
					} catch (NoSuchAlgorithmException e2) {
						logger.error("v3接口获取验证字符串的md5异常：，token=：" + token + "，异常信息：" + e2.getMessage());
						map.put("status", 1);
						map.put("message", "系统出错！");
						String json = toJson(map);
						try {
							write(json, response);
						} catch (Exception e1) {
							logger.error("v3接口回写信息异常（获取的veryCode不匹配），token=：" + token + "，异常信息：" + e1.getMessage());
						}
						return;
					}
					String fileName = item.getName();
					// 验证通过，进行流的接收并保存
					savePath = streaming(in, token, fileName);
					if (null != savePath) {
						if (null != md5 && !"".equalsIgnoreCase(md5)) {
							String fileMd5 = MD5Util.getFileMD5String(new File(savePath));
							if (!md5.equalsIgnoreCase(fileMd5)) {
								logger.error("获取" + fileName + "的MD5=" + md5 + "与参数中的MD5=" + md5 + "不相符！存储路径为：" + savePath);
								map.put("status", 1);
								map.put("message", "文件的MD5不匹配");
								String json = toJson(map);
								try {
									write(json, response);
								} catch (Exception e1) {
									logger.error("v3接口回写信息异常（获取的MD5不匹配），token=：" + token + "，异常信息：" + e1.getMessage());
								}
								return;
							}
							logger.info("md5校验通过！md5=" + fileMd5);
						}
					} else {
						map.put("status", 1);
						map.put("message", "系统出错！");
						String json = toJson(map);
						try {
							write(json, response);
						} catch (Exception e) {
							logger.error("v3接口回写信息异常，token=：" + token + "，异常信息：" + e.getMessage());
						}
						return;
					}
				}
			}
			// 保存文件，分析，转码
//			resObject = apiService.doV3(userId, resName, resDesc, savePath, md5, tipoffName, phoneNumber, remark, location);
			map.put("url", savePath);
			String json = toJson(map);
			try {
				write(json, response);
				logger.info("上传完成，token=：" + token + "，返回信息：,data=" + json);
			} catch (Exception e) {
				logger.error("v3接口回写信息异常（已上传完成），token=：" + token + "，异常信息：" + e.getMessage());
			}
		} catch (FileUploadException e) {
			logger.error("v3接口上传文件异常：" + e.getMessage());
			map.put("status", 1);
			map.put("message", "系统出错！");
			String json = toJson(map);
			try {
				write(json, response);
			} catch (Exception e1) {
				logger.error("v3接口回写信息异常（上传文件异常的返回信息），token=：" + token + "，异常信息：" + e.getMessage());
			}
		} catch (IOException e) {
			logger.error("v3接口IO异常（可能是客户端中断操作或网络故障导致的），token=：" + token + "，异常信息：" + e.getMessage());
		}
	}

	public void init() throws ServletException {
//		apiService = (ApiService) SpringUtil.getInstance("apiServiceImpl");
	}

	/**
	 * 保存文件流到指定存储
	 * 
	 * @param in
	 * @param token
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private String streaming(InputStream in, String token, String fileName) throws IOException {
		OutputStream out = null;
		String savePath = Constants.TOKEN_MAP.get(token);
		if (null == savePath || "".equals(savePath)) {
			logger.warn("token无效，token = " + token);
			return null;
		}
		String tokenPath = savePath + token;
		File f = new File(tokenPath);
		if (!f.exists()) {
			logger.warn("无法访问根据token获取的路径，token=" + token + "，path=" + tokenPath);
			return null;
		}
		try {
			out = new FileOutputStream(f, true);
			logger.info("token=" + token + "已上传" + f.length() + "字节。");
			int read = 0;
			final byte[] bytes = new byte[Constants.BUFFER_LENGTH];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			String filename = renameFile(token, fileName);
			savePath = savePath.replace("//", "/");
			logger.info("token=" + token + "的文件已经上传完成，原文件名为：" + fileName + "，重命名为：" + filename + "，存储路径：" + savePath);
			Constants.TOKEN_MAP.remove(token);
			return savePath + filename;
		} finally {
			if (null != out) {
				out.close();
			}
			if (null != in) {
				in.close();
			}
		}
	}

	/**
	 * 重命名文件
	 * 
	 * @param token
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private String renameFile(String token, String fileName) throws IOException {
		String newName = newFileName(fileName);
		String newSavePath = Constants.TOKEN_MAP.get(token) + newName;
		String oldSavePath = Constants.TOKEN_MAP.get(token) + token;
		File newFile = new File(newSavePath);
		File oldFile = new File(oldSavePath);
		while (true) {
			if (oldFile.renameTo(newFile)) {
				break;
			}
		}
		String filename = newFile.getName();
		return filename;
	}

	/**
	 * 生成UUID文件名
	 * 
	 * @param token
	 * @return
	 */
	private String newFileName(String fileName) {
		if (null != fileName && fileName.lastIndexOf(".") > 0) {
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			String name = (UUID.randomUUID().toString()).replace("-", "");
			return name + suffix;
		}
		return fileName;
	}

	/**
	 * 对象转json
	 * 
	 * @param object
	 *            要转换为json的对象
	 * @return
	 */
	private String toJson(Object object) {
		ObjectMapper om = new ObjectMapper();
		Writer writer = new StringWriter();
		String json = null;
		try {
			om.writeValue(writer, object);
			json = writer.toString();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * ajax回写信息
	 * 
	 * @param msg
	 * @throws Exception
	 */
	private void write(String msg, HttpServletResponse response) throws Exception {
		PrintWriter write = null;
		response.setContentType("application/text;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			write = response.getWriter();
			write.write(msg);
			write.flush();
		} catch (Exception e) {
			throw new Exception("ajax write error:" + e.getMessage());
		} finally {
			if (write != null) {
				write.close();
			}
		}
	}
	/**
	 * 验证请求参数
	 * 
	 * @param appKey
	 * @param randomId
	 * @param currentTime
	 * @param veryCode
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkParam(String appKey, Integer randomId, Long currentTime, String veryCode) throws NoSuchAlgorithmException {
		long now = System.currentTimeMillis();
		long totalTiem = now - currentTime;
		if (Constants.OUT_TIME_BEFORE > totalTiem) {
			logger.warn("请求时间：" + currentTime + "小于服务器时间：" + now + "!");
			return false;
		} else if (Constants.OUT_TIME_LATE < totalTiem) {
			logger.warn("请求时间超时，请求时间：" + currentTime + "，服务器时间：" + now + "!");
			return false;
		}
		String strmd5 = md5s(appKey + randomId + currentTime + Constants.SECRET);
		if (veryCode.equalsIgnoreCase(strmd5)) {
			return true;
		} else {
			logger.warn("传递的veryCode值：" + veryCode + "与服务器获取的veryCode值:" + strmd5 + "不一致,验证不通过");
			return false;
		}
	}
	/**
	 * md5加密
	 * 
	 * @param plainText
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5s(String plainText) throws NoSuchAlgorithmException {
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest md = null;
		StringBuffer buf = new StringBuffer("");
		md = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		md.update(plainText.getBytes());
		// 获得密文
		byte b[] = md.digest();
		int i;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
}