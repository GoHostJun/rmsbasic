package com.cdvcloud.rms.web.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
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
public class RLoginController {
	private static final Logger logger = Logger.getLogger(RLoginController.class);
	@Autowired
	private ValidateJson validateJson;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	/** 登录校验用户信息 */
	@RequestMapping(value = "rms/user/checkUserInfo/")
	@ResponseBody
	public ResponseObject checkUserInfo(HttpServletRequest request, @RequestBody String strJson) {
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
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			Map<String, Object> userMap = userService.getUserInfo(mapJson);
			if(null !=userMap && !userMap.isEmpty()){
				UserUtil.saveUserSession(request, userMap);
				//获取用户信息
				CommonParameters common = new CommonParameters();
				common.setUserId(String.valueOf(userMap.get(User.CUSERID)));
				common.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
				common.setAppCode(Constants.APPCODEVALUE);
				UserUtil.getUserInfo(common, userMap);
				//获取日志ip信息『
				SystemLogUtil.getIp(common, request);
				systemLogService.inset(SystemLogUtil.getMap(common, "0", "登录", "『"+userMap.get(User.NAME)+"』登录了系统"));
				LogUtil.printIntegralLog(common, userMap, "pclogin", "『"+userMap.get(User.NAME)+"』登录了系统");
				resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
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
	
	/**
	 * 用户注销
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rms/user/logout/")
	@ResponseBody
	public ResponseObject logout(HttpServletRequest request, @RequestBody String strJson) {
		ResponseObject resObj = new ResponseObject();
		UserUtil.removeUserSession(request);
		// 删除cookie
		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setMaxAge(0);
		resObj.setCode(0);
		return resObj;
	}
	
}
