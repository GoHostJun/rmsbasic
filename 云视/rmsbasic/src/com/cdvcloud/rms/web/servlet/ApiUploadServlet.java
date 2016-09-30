package com.cdvcloud.rms.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.MD5Util;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.upload.util.SpringUtil;

public class ApiUploadServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(ApiUploadServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 7365511075937623525L;

	private IMediaService iMediaService;

	private BasicDao basicDao;

	public ApiUploadServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		iMediaService = (IMediaService) SpringUtil.getInstance("mediaServiceImpl");
		basicDao = (BasicDao) SpringUtil.getInstance("basicDao");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter writer = response.getWriter();
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("code", "10002");
			retMap.put("message", "Inner error");
			retMap.put("data", "");
			String json = JsonUtil.writeMap2JSON(retMap);
			writer.write(json);
		} catch (Exception e) {
			logger.error("Api断点续传接口异常，异常信息：" + e.getMessage());
		}

	}

	/**
	 * 上传文件
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String ipAddr = HttpUtil.getIpAddr(request);
		String savePath = null;// 保存路径
		String accessToken = "";
		String uploadToken = "";
		String timeStamp = "";
		String appCode = "";
		String versionId = "";
		String companyId = "";
		String userId = "";
		String serviceCode = "";
		String md5 = "";
		String mtype = "";
		String remark = "";
		String file_Name = "";
		InputStream in = null;
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter;
			logger.info("Api断点续传接口调用");
			iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				in = item.openStream();
				if (item.isFormField()) { // 获取form中的表单
					String value = Streams.asString(in, "UTF-8");
					if ("accessToken".equals(name)) {
						accessToken = value;
					}
					if ("uploadToken".equals(name)) {
						uploadToken = value;
					}
					if ("timeStamp".equals(name)) {
						timeStamp = value;
					}
					if ("appCode".equals(name)) {
						appCode = value;
					}
					if ("versionId".equals(name)) {
						versionId = value;
					}
					if ("companyId".equals(name)) {
						companyId = value;
					}
					if ("userId".equals(name)) {
						userId = value;
					}
					if ("serviceCode".equals(name)) {
						serviceCode = value;
					}
					if ("md5".equals(name)) {
						md5 = value;
					}
					if ("mtype".equals(name)) {
						mtype = value;
					}
					if ("remark".equals(name)) {
						remark = value;
					}
					if ("fileName".equals(name)) {
						file_Name = value;
					}
				} else {
					logger.info("Api断点续传接口-访问IP地址:" + ipAddr + "|参数:" + "|accessToken=" + accessToken + "|timeStamp=" + timeStamp + "|uploadToken="
							+ uploadToken + "|md5=" + md5 + "|appCode=" + appCode + "|versionId=" + versionId + "|companyId=" + companyId
							+ "|userId=" + userId + "|serviceCode=" + serviceCode + "remark=" + remark);
					if (StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(timeStamp) || StringUtil.isEmpty(uploadToken)
							|| StringUtil.isEmpty(appCode) || StringUtil.isEmpty(versionId) || StringUtil.isEmpty(companyId)
							|| StringUtil.isEmpty(userId) || StringUtil.isEmpty(serviceCode)) {
						retMap.put("code", "10001");
						retMap.put("message", "Input Parameter Invalid");
						retMap.put("data", "");
						String json = JsonUtil.writeMap2JSON(retMap);
						write(json, response);
						return;
					}

					String fileName = item.getName();
					String length = "";
					// 验证通过，进行流的接收并保存
					savePath = streaming(in, uploadToken, fileName);
					if (savePath != null) {
						if (!StringUtil.isEmpty(md5)) {
							File file = new File(savePath);
							length = String.valueOf(file.length());
							String fileMd5 = MD5Util.getFileMD5String(file);
							savePath = file.getPath();
							if (!md5.equalsIgnoreCase(fileMd5)) {
								logger.error("获取" + fileName + "的MD5=" + md5 + "与参数中的MD5=" + md5 + "不相符！存储路径为：" + savePath);
								retMap.put("code", "30005");
								retMap.put("message", "md5 error");
								retMap.put("data", "");
								String json = JsonUtil.writeMap2JSON(retMap);
								try {
									write(json, response);
								} catch (Exception e1) {
									logger.error("Api断点续传接口信息异常(获取的MD5不匹配),uploadToken=：" + uploadToken + "，异常信息：" + e1.getMessage());
								}
								return;
							}
							logger.info("md5校验通过！md5=" + fileMd5);
						}
						retMap.put("code", "0");
						retMap.put("message", "Success");

						Map<String, String> data = new HashMap<String, String>();

						data.put("url", savePath);
						retMap.put("data", data);
						String json = JsonUtil.writeMap2JSON(retMap);
						logger.info("上传返回：" + json);
						write(json, response);
						if (null != mtype && !"".equals(mtype)) {
							// ---------保存上传进度----------
							Map<String, Object> whereMap = new HashMap<String, Object>();
							whereMap.put("uploadToken", uploadToken);
							whereMap.put("status", "2");
							Map<String, Object> mapUploadToken = basicDao.findOne("uploadToken", whereMap);
							if (null == mapUploadToken) {
								return;
							}
							String uploadUUID = String.valueOf(mapUploadToken.get("_id"));
							Map<String, Object> whereMapUpdate = new HashMap<String, Object>();
							whereMapUpdate.put("_id", new ObjectId(uploadUUID));
							Map<String, Object> update = new HashMap<String, Object>();
							update.put("schedule", length);
							update.put("status", "0");
							basicDao.updateOneBySet("uploadToken", whereMap, update);
							// ---------保存上传进度----------
							// -----------------------------------
							CommonParameters common = new CommonParameters();
							common.setAccessToken(accessToken);
							common.setAppCode(appCode);
							common.setCompanyId(companyId);
							common.setVersionId(versionId);
							common.setTimeStamp(timeStamp);
							common.setServiceCode(serviceCode);
							common.setUserId(userId);
							iMediaService.registerResource(common, mtype, savePath, file_Name, remark,uploadUUID);
							// -----------------------------------
						}
						return;
					}
					retMap.put("code", "30005");
					retMap.put("message", "uploadToken error");
					retMap.put("data", "");
					String json = JsonUtil.writeMap2JSON(retMap);
					write(json, response);
					return;
				}
			}
		} catch (Exception e) {
			retMap.put("code", "10002");
			retMap.put("message", "Inner error");
			retMap.put("data", "");
			String json = JsonUtil.writeMap2JSON(retMap);
			try {
				write(json, response);
			} catch (Exception e1) {
				logger.error("Api断点续传接口（上传文件异常的返回信息），uploadToken=：" + uploadToken + "，异常信息：" + e.getMessage());
			}
			return;
		}
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
	private String streaming(InputStream in, String uploadToken, String fileName) throws IOException {
		OutputStream out = null;
		Map<String, Object> dbobj = null;
		try {
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("uploadToken", uploadToken);
			dbobj = basicDao.findOne("uploadToken", whereMap);
		} catch (Exception e) {
			return null;
		}
		if (dbobj != null) {
			String savePath = String.valueOf(dbobj.get("savePath"));
			if (null == savePath || "".equals(savePath)) {
				logger.warn("uploadToken无效，uploadToken = " + uploadToken);
				return null;
			}
			String tokenPath = savePath + uploadToken;
			System.out.println(tokenPath);
			File f = new File(tokenPath);
			if (!f.exists()) {
				logger.warn("无法访问根据uploadToken获取的路径，uploadToken=" + uploadToken + "，path=" + tokenPath);
				return null;
			}
			try {
				out = new FileOutputStream(f, true);
				logger.info("token=" + uploadToken + "已上传" + f.length() + "字节。");
				int read = 0;
				final byte[] bytes = new byte[Constants.BUFFER_LENGTH];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
					out.flush();
				}
				out.close();
				System.out.println("上传完毕，开始重命名。。。");
				String filename = renameFile(uploadToken, savePath, fileName);
				System.out.println("文件名称：" + filename);
				savePath = savePath.replace("//", "/");
				logger.info("uploadToken=" + uploadToken + "的文件已经上传完成，原文件名为：" + fileName + "，重命名为：" + filename + "，存储路径：" + savePath);
				Constants.TOKEN_MAP.remove(uploadToken);
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
		logger.warn("uploadToken无效，uploadToken = " + uploadToken);
		return null;
	}

	/**
	 * 重命名文件
	 * 
	 * @param token
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private String renameFile(String token, String path, String fileName) throws IOException {
		String newName = newFileName(fileName);
		String newSavePath = path + newName;
		String oldSavePath = path + token;
		File newFile = new File(newSavePath);
		File oldFile = new File(oldSavePath);
		System.out.println("开始判断文件是否被占用。。。。");
		while (true) {
			if (oldFile.renameTo(newFile)) {
				break;
			}
		}
		String filename = newFile.getName();
		System.out.println("重命名完成：" + filename);
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

	public static void main(String[] args) {
		String savePath = "G:\\CS\\测试视频.ts";
		String fileMd5 = MD5Util.getFileMD5String(new File(savePath));
		System.out.println(fileMd5);
	}
}
