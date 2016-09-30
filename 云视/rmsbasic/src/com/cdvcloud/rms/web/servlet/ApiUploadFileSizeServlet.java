package com.cdvcloud.rms.web.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.upload.util.SpringUtil;

public class ApiUploadFileSizeServlet extends HttpServlet {

	private static final long serialVersionUID = -6129561373321978301L;

	private BasicDao basicDao;

	private static final Logger logger = Logger.getLogger(ApiUploadFileSizeServlet.class);

	public ApiUploadFileSizeServlet() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", "10002");
		retMap.put("message", "Inner error");
		retMap.put("data", "");
		String json = JsonUtil.writeMap2JSON(retMap);
		writer.write(json);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		String ipAddr = HttpUtil.getIpAddr(request);
		String jsons = IOUtils.toString(request.getInputStream());
		Map<String, Object> reqMap = JsonUtil.readJSON2Map(jsons);
		String accessToken = String.valueOf(reqMap.get("accessToken"));
		String timeStamp = String.valueOf(reqMap.get("timeStamp"));
		String uploadToken = String.valueOf(reqMap.get("uploadToken"));
		String appCode = String.valueOf(reqMap.get("appCode"));
		String versionId = String.valueOf(reqMap.get("versionId"));
		String companyId = String.valueOf(reqMap.get("companyId"));
		String userId = String.valueOf(reqMap.get("userId"));
		String serviceCode = String.valueOf(reqMap.get("serviceCode"));
		logger.info("Api获取断点续传进度接口-访问IP地址:" + ipAddr + "|参数:" + "|accessToken=" + accessToken + "|timeStamp=" + timeStamp + "|uploadToken="
				+ uploadToken + "|appCode=" + appCode + "|versionId=" + versionId + "|companyId=" + companyId + "|userId=" + userId + "|serviceCode="
				+ serviceCode);
		PrintWriter writer = response.getWriter();
		if (StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(timeStamp) || StringUtil.isEmpty(uploadToken) || StringUtil.isEmpty(appCode)
				|| StringUtil.isEmpty(versionId) || StringUtil.isEmpty(companyId) || StringUtil.isEmpty(userId) || StringUtil.isEmpty(serviceCode)) {
			retMap.put("code", "10002");
			retMap.put("message", "Inner error");
			retMap.put("data", "");
			String json = JsonUtil.writeMap2JSON(retMap);
			writer.write(json);
			logger.info("Api获取断点续传进度接口返回：" + json);
			return;
		}
		Map<String, Object> dbobj = null;
		try {
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("uploadToken", uploadToken);
			whereMap.put("status", "2");
			dbobj = basicDao.findOne("uploadToken", whereMap);
		} catch (Exception e) {
			logger.error("获取文件上传进度出错，数据库查询错误！");
			e.printStackTrace();
			retMap.put("code", "10002");
			retMap.put("message", "Inner error");
			retMap.put("data", "");
			String json = JsonUtil.writeMap2JSON(retMap);
			writer.write(json);
			logger.info("Api获取断点续传进度接口返回：" + json);
			return;
		}
		logger.info("数据库查询结果：" + dbobj);
		if (dbobj != null) {
			// 根据token获取对应的存储路径
			String tokenPath = String.valueOf(dbobj.get("savePath"));
			tokenPath += uploadToken;
			// 获取已上传的大小
			File file = new File(tokenPath);
			logger.info("文件地址是否存在：" + file + ";" + file.isFile());
			if (null != file && file.isFile()) {
				String finishLength = String.valueOf(file.length());
				Map<String, String> data = new HashMap<String, String>();
				data.put("finishLength", finishLength);
				retMap.put("code", "0");
				retMap.put("message", "Success");
				retMap.put("data", data);
				String json = JsonUtil.writeMap2JSON(retMap);
				writer.write(json);
				logger.info("Api获取断点续传进度接口返回：" + json);
				return;
			} else {
				if (null != dbobj.get("schedule")) {
					Map<String, String> data = new HashMap<String, String>();
					data.put("finishLength", String.valueOf(dbobj.get("schedule")));
					retMap.put("code", "0");
					retMap.put("message", "Success");
					retMap.put("data", data);
					String json = JsonUtil.writeMap2JSON(retMap);
					writer.write(json);
					logger.info("Api获取断点续传进度接口返回：" + json);
					return;
				}
			}
		}
		retMap.put("code", "10002");
		retMap.put("message", "Inner error");
		retMap.put("data", "");
		String json = JsonUtil.writeMap2JSON(retMap);
		writer.write(json);
		logger.info("Api获取断点续传进度接口返回：" + json);
		return;
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doHead(req, resp);
	}

}
