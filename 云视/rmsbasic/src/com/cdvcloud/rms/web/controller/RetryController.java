package com.cdvcloud.rms.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.SuperDeal;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.IRetryService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
public class RetryController {
	private static final Logger logger = Logger.getLogger(RetryController.class);
	@Autowired
	private IUserService userService;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IMediaService materialService;
	@Autowired
	private IHistoricalTaskService historicalTaskService;

	@Autowired
	IRetryService retryService;

	/**
	 * 素材重试
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param fileName
	 * @param fileSize
	 * @param request
	 * @return
	 */
	@RequestMapping("/retry/retry/")
	@ResponseBody
	public String retry(@RequestParam(value = "userId") String userId, @RequestParam(value = "file") String file,// 地址
			@RequestParam(value = "fileName") String fileName, HttpServletRequest request) {
		try {
			CommonParameters common = new CommonParameters();
			common.setAppCode(Constants.APPCODEVALUE);
			common.setUserId(userId);
			common.setVersionId("v1");
			common.setCompanyId("cdv-yuntonglian");
			common.setServiceCode(Constants.SERVICECODEVALUE);
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			systemLogService.inset(SystemLogUtil.getMap(common, "0", "素材重试", "上传文件《" + fileName + "》"));
//			materialService.insetMedia(common, fileName, "", file);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", file);
			map.put("name", fileName);
			map.put("md5", "");
			materialService.insetHttpMedia(common, JsonUtil.map2Json(map));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重试失败：" + e);
			return Constants.SERVICE_ERROR;
		}
		return Constants.SERVICE_SUCCESS;
	}

	@RequestMapping("/retry/retryUrl/")
	@ResponseBody
	public String retryUrl(
			@RequestParam(value = "_id") String _id, 
			 @RequestParam(value = "taskType")  String taskType,
			HttpServletRequest request) {
		try {
			//获取当前token和时间戳
//			param = "accessToken="+configurationService.getToken(common)+"&timeStamp="+System.currentTimeMillis()+"&"+param;
//			logger.info("重试发送任务url=" + url + ";内容=" + param);
//			String ret = HttpUtil.sendPost(url, param);
//			logger.info("重试发送任务返回=" + ret);
			CommonParameters commonParameters=new CommonParameters();
			retryService.retryUrl(commonParameters, _id,taskType);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重试失败：" + e);
			return Constants.SERVICE_ERROR;
		}
		return Constants.SERVICE_SUCCESS;
	}

	@RequestMapping("/retry/retryQurey/")
	@ResponseBody
	public ResponseObject retryQurey(HttpServletRequest request, @RequestBody String strJson) {
		try {
			return historicalTaskService.query(strJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重试失败：" + e);
		}
		return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
	}

	@RequestMapping("/retry/findMedias/")
	@ResponseBody
	public ResponseObject findMedias(@RequestBody String strJson, HttpServletRequest request) {
		CommonParameters common = new CommonParameters();
		common.setAppCode(Constants.APPCODEVALUE);
		common.setUserId("566fd73c84e353224410c0b6");
		common.setVersionId("v1");
		common.setCompanyId("");
		common.setServiceCode(Constants.SERVICECODEVALUE);
		return materialService.findList(common, strJson);
	}
}
