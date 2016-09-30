package com.cdvcloud.rms.web.api;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.common.ValidateJson;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
public class LoginController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private ValidateJson validateJson;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	@Autowired
	private ValidateCommonParam validateCommonParam;
	
	/** 登录校验用户信息 */
	@RequestMapping(value = "api/user/getUserInfo/")
	@ResponseBody
	public ResponseObject getUserInfo(HttpServletRequest request, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			if (StringUtil.isEmpty(mapJson.get("userName")) || StringUtil.isEmpty(mapJson.get("password"))) {
				logger.warn("");
				return null;
			}
			Map<String, Object> userMap = userService.getUserInfo(mapJson);
			if(null !=userMap && !userMap.isEmpty()){
				//获取用户信息
				CommonParameters common = new CommonParameters();
				common.setUserId(String.valueOf(userMap.get(User.CUSERID)));
				common.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
				common.setAppCode(Constants.APPCODEVALUE);
				UserUtil.getUserInfo(common, userMap);
				//获取日志ip信息『
				SystemLogUtil.getIp(common, request);
				systemLogService.inset(SystemLogUtil.getMap(common, "0", "登录", "『"+userMap.get(User.NAME)+"』通过接口登录了系统"));
				LogUtil.printIntegralLog(common, userMap, "phonelogin", "『"+userMap.get(User.NAME)+"』通过接口登录了系统");
				resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, userMap);
			}else{
				resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
			}
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	
	/** cas登录校验用户信息 */
	@RequestMapping(value = "api/user/getUserInfo4CAS/")
	@ResponseBody
	public ResponseObject getUserInfo4CAS(HttpServletRequest request, @RequestBody String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		try {
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return resObj;
			}
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			if (StringUtil.isEmpty(mapJson.get("userName")) || StringUtil.isEmpty(mapJson.get("password"))) {
				logger.warn("");
				return resObj;
			}
			String userName = String.valueOf(mapJson.get("userName"));
			String password = String.valueOf(mapJson.get("password"));
			Map<String, Object> mapUserRes = new HashMap<String, Object>();
			boolean resFlag = userService.validateUserInfo(userName, password, mapUserRes);
			if (!resFlag) {
				return resObj;
			}
			Map<String, Object> userMap = userService.getUserInfoByName(mapJson);
			if (null != userMap && !userMap.isEmpty()) {
				// 获取用户信息
				CommonParameters common = new CommonParameters();
				common.setUserId(String.valueOf(userMap.get(User.CUSERID)));
				common.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
				common.setAppCode(Constants.APPCODEVALUE);
				UserUtil.getUserInfo(common, userMap);
				// 获取日志ip信息『
				SystemLogUtil.getIp(common, request);
				systemLogService.inset(SystemLogUtil.getMap(common, "0", "登录", "『" + userMap.get(User.NAME) + "』通过接口登录了系统"));
				if (!mapUserRes.isEmpty() && mapUserRes.containsKey("userId")) {
					userMap.put("loginId", mapUserRes.get("loginId"));
					userMap.put("userId", mapUserRes.get("userId"));
				}
				LogUtil.printIntegralLog(common, userMap, "phonelogin", "『"+userMap.get(User.NAME)+"』通过统一认证接口登录了系统");
				resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, userMap);
			} else {
				resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
			}
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 获取用户积分信息 */
	@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/v1/api/queryIntegral/")
	@ResponseBody
	public ResponseObject queryIntegral(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = userService.queryIntegral(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("获取积分失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		}
		return resObj;
	}
	
}
