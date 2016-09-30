package com.cdvcloud.rms.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 该类是用户认证相关
 * 
 * @version： v1.0
 * @author huangaigang
 * @date 2015-11-29 23:06:20
 */
@Component
public class UserAuthentication {
	private static final Logger logger = Logger.getLogger(UserAuthentication.class); 
	
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private IUserService userServiceImpl;
	@Autowired
	private ISystemLogService systemLogService;
	/**
	 * 获取用户token
	 * @return
	 */
	public boolean getAccessToken(HttpServletRequest request, String params){
		HttpSession session = request.getSession();
		String strUserId = String.valueOf(session.getAttribute(Constants.USERID));
		if (StringUtil.isEmpty(strUserId)) {//如果用户id为空则获取cas的用户登录名称，根据名称获取用户信息
			return false;//TODO:如果不用cas则打开这句代码
//			boolean resFlag = getCasUser(request, params);
//			if (!resFlag) {
//				return resFlag;
//			}
		}
		//获取token
		getToken(session);
//		String strAccessToken = String.valueOf(session.getAttribute("accessToken"));
//		if (StringUtil.isEmpty(strAccessToken)) {
//			//获取token
//			getToken(session);
//		} else {
//			String tokenTime = String.valueOf(session.getAttribute(strAccessToken));
//			if (!StringUtil.isEmpty(tokenTime)) {
//				//用longTokenTime和当前时间求差，如果大于3600000毫秒，则请求token，否则通过
//				Long longTokenTime = Long.valueOf(tokenTime);
//				Long longCurrentTime = System.currentTimeMillis();
//				if ((longCurrentTime-longTokenTime)>3600000) {
//					getToken(session);
//				}
//			}else{
//				getToken(session);
//			}
//		}
		return true;
	}
	
	/**
	 * 获取token并保持session
	 * @param session
	 */
	private void getToken(HttpSession session){
		String strToken = configurationService.getToken();
		session.setAttribute("accessToken", strToken);
//		session.setAttribute(strToken, System.currentTimeMillis());
	}
	
	protected boolean getCasUser(HttpServletRequest request,String params){
		String strUsername = UserUtil.getUser4CAS(request, params);
		if (StringUtil.isEmpty(strUsername)) {
			logger.debug("cas的request里没有获取到用户信息，用户登录可能失效！");
			return false;
		}else{
			Map<String, Object> mapJson = new HashMap<String, Object>();
			mapJson.put("userName", strUsername);
			Map<String, Object> userMap = userServiceImpl.getUserInfoByName(mapJson);
			if(null !=userMap && !userMap.isEmpty()){
				UserUtil.saveUserSession(request, userMap);
				//添加登录日志
				CommonParameters common = new CommonParameters();
				common.setUserId(String.valueOf(userMap.get(User.CUSERID)));
				common.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
				common.setAppCode(Constants.APPCODEVALUE);
				UserUtil.getUserInfo(common, userMap);
				//获取日志ip信息『
				SystemLogUtil.getIp(common, request);
				systemLogService.inset(SystemLogUtil.getMap(common, "0", "登录", "『" + userMap.get(User.NAME) + "』通过统一门户登录了系统"));
				LogUtil.printIntegralLog(common, userMap, "pclogin", "『"+userMap.get(User.NAME)+"』通过统一门户登录了系统");
				return true;
			}else{
				logger.warn("没有此用户信息，用户名："+strUsername);
				return false;
			}
		}
	}

}
