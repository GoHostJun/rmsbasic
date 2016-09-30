package com.cdvcloud.rms.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.upload.config.Configurations;
import com.cdvcloud.upload.util.SpringUtil;

/**
 * ClassName: ApiUploadTokenServlet
 * 
 * @Description: 获取上传uploadToken令牌
 * @author H.J
 * @date 2015-6-29下午6:18:35
 */
public class ApiUploadTokenServlet extends HttpServlet {
	private static final long serialVersionUID = -388552504162058581L;

	private BasicDao basicDao;

	private static final Logger logger = Logger.getLogger(ApiUploadTokenServlet.class);

	public ApiUploadTokenServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		basicDao = (BasicDao) SpringUtil.getInstance("basicDao");
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", "10002");
		retMap.put("message", "Inner error");
		retMap.put("data", "");
		String json = JsonUtil.writeMap2JSON(retMap);
		writer.write(json);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String json = IOUtils.toString(request.getInputStream());
			Map<String, Object> reqMap = JsonUtil.readJSON2Map(json);
			String ipAddr = HttpUtil.getIpAddr(request);
			String accessToken = String.valueOf(reqMap.get("accessToken"));
			String timeStamp = String.valueOf(reqMap.get("timeStamp"));
			String resName = String.valueOf(reqMap.get("resName"));
			String resSize = String.valueOf(reqMap.get("resSize"));
			String appCode = String.valueOf(reqMap.get("appCode"));
			String versionId = String.valueOf(reqMap.get("versionId"));
			String companyId = String.valueOf(reqMap.get("companyId"));
			String userId = String.valueOf(reqMap.get("userId"));
			String serviceCode = String.valueOf(reqMap.get("serviceCode"));

			logger.info("Api获取断点续传token接口-访问IP地址:" + ipAddr + "|参数:" + "|accessToken=" + accessToken + "|timeStamp=" + timeStamp + "|resName="
					+ resName + "|resSize=" + resSize + "|appCode=" + appCode + "|versionId=" + versionId + "|companyId=" + companyId + "|userId="
					+ userId + "|serviceCode=" + serviceCode);

			PrintWriter writer = response.getWriter();
			if (StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(timeStamp) || StringUtil.isEmpty(resName) || StringUtil.isEmpty(resSize)
					|| StringUtil.isEmpty(appCode) || StringUtil.isEmpty(versionId) || StringUtil.isEmpty(companyId) || StringUtil.isEmpty(userId)
					|| StringUtil.isEmpty(serviceCode)) {
				retMap.put("code", "10001");
				retMap.put("message", "Input Parameter Invalid");
				retMap.put("data", "");
				String jsons = JsonUtil.writeMap2JSON(retMap);
				writer.write(jsons);
				return;
			}

			String uploadToken = generateToken(resName, resSize, userId);
			String savePath = Configurations.getFileRepository("") + File.separator;
			File file = new File(savePath + uploadToken);
			if (!file.exists()) {
				File dir = new File(file.getParent());
				if (!dir.exists()) {
					dir.mkdirs();
				}
				file.createNewFile();
			}
			// 保存token对应的存储路径
			Constants.TOKEN_MAP.put(uploadToken, savePath);
			Map<String, Object> whereMap = new HashMap<String, Object>();
			String uploadUUID = null;
			whereMap.put("uploadToken", uploadToken);
			whereMap.put("status", "2");
			//需要先查询一下，有就直接返回
			Map<String, Object> mapUploadToken = basicDao.findOne("uploadToken", whereMap);
			if (null != mapUploadToken) {
				uploadUUID = String.valueOf(mapUploadToken.get("_id"));
			}else{
				whereMap.put("savePath", savePath);
				uploadUUID = basicDao.insert("uploadToken", whereMap);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (null != uploadUUID) {
				retMap.put("code", "0");
				retMap.put("message", "Success");
				dataMap.put("uploadToken", uploadToken);
				dataMap.put("uploadUUID", uploadUUID);
			}else{
				retMap.put("code", "10001");
				retMap.put("message", "Input Parameter Invalid");
				dataMap.put("uploadToken", "");
				dataMap.put("uploadUUID", "");
			}
			retMap.put("data", dataMap);
			String jsons = JsonUtil.writeMap2JSON(retMap);
			writer.write(jsons);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doHead(req, resp);
	}

	/**
	 * 根据文件的用户名和大小生成token
	 * 
	 * @param name
	 * @param size
	 * @param userId
	 * @return
	 */
	public static String generateToken(String name, String size, String userId) {
		if (name == null || size == null) {
			logger.warn("用户名或文件大小不符合要求：filename=" + name + ",filesize=" + size);
			return null;
		}
		int code = name.hashCode();
		String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_" + size.trim();
		String time = String.valueOf(new Date().getTime());
		token += "_" + userId + "_" + time;
		return token;
	}
}
