package com.cdvcloud.rms.common;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

/**
 * 该类是验证页面输入的json格式是否正确
 * 
 * @Description: validate方法验证,返回true为通过,反之return false.
 * @version： v1.0
 * @author huangaigang
 * @date 2015-11-29 23:06:20
 */
@Component
public class ValidateCommonParam {
	private static final Logger logger = Logger.getLogger("ValidateCommonParam");
	@Autowired
	private ConfigurationService configurationService;
	
	public ValidateCommonParam() {
	}

	/**
	 * 获取公共参数并验证
	 * 
	 * @param commonParameters
	 * @param jsonMap
	 * @return
	 */
	public boolean validateCommonParam(CommonParameters commonParameters, Map<String, Object> jsonMap) {
		if (StringUtil.isEmpty(jsonMap) || jsonMap.isEmpty()) {
			logger.info("集合为空,jsonMap=" + jsonMap);
			return false;
		}
		// 获取公共参数
		Object accessToken = jsonMap.get(CommonParameters.ACCESSTOKEN);
		Object timeStamp = jsonMap.get(CommonParameters.TIMESTAMP);
		if (!StringUtil.isEmpty(accessToken)) {
			boolean result = configurationService.getValidateToken(String.valueOf(accessToken));
			if (!result) {
				logger.info("AccessToken校验失败,AccessToken=" + commonParameters);
				return false;
			}
			commonParameters.setAccessToken(String.valueOf(accessToken));
		} else {
			logger.info("没有获取到accessToken,jsonMap=" + jsonMap);
			return false;
		}
		if (!StringUtil.isEmpty(timeStamp)) {
			commonParameters.setTimeStamp(String.valueOf(timeStamp));
		} else {
			logger.info("没有获取到timeStamp,jsonMap=" + jsonMap);
		}
		return validateCommonParam(commonParameters);
	}
	
	/**
	 * 获取公共参数并验证
	 * 
	 * @param commonParameters
	 * @param jsonMap
	 * @return
	 */
	public boolean validateCommonParam(CommonParameters commonParameters, String strJson) {
		logger.info("公共参数：" + commonParameters + "业务参数：" + strJson);
		if (StringUtil.isEmpty(strJson)) {
			logger.info("集合为空,strJson=" + strJson);
			return false;
		}
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取公共参数
		Object accessToken = mapJson.get(CommonParameters.ACCESSTOKEN);
		Object timeStamp = mapJson.get(CommonParameters.TIMESTAMP);
		if (!StringUtil.isEmpty(accessToken)) {
			//boolean result = configurationService.getValidateToken(String.valueOf(accessToken));
			boolean result=true;
			if (!result) {
				logger.info("AccessToken校验失败,AccessToken=" + commonParameters);
				return false;
			}
			commonParameters.setAccessToken(String.valueOf(accessToken));
		} else {
			logger.info("没有获取到accessToken,mapJson=" + mapJson);
			return false;
		}
		if (!StringUtil.isEmpty(timeStamp)) {
			commonParameters.setTimeStamp(String.valueOf(timeStamp));
		} else {
			logger.info("没有获取到timeStamp,mapJson=" + mapJson);
		}
		return validateCommonParam(commonParameters);
	}

	public boolean validateCommonParam(CommonParameters commonParameters) {
		// 校验公共参数
		if (StringUtil.isEmpty(commonParameters.getUserId())) {
			logger.info("没有获取到用户id,userid=" + commonParameters);
			return false;
		}
		return true;
	}

}
