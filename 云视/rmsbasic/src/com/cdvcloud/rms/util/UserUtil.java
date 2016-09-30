package com.cdvcloud.rms.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.domain.Custom;
import com.cdvcloud.rms.domain.Department;
import com.cdvcloud.rms.domain.Role;
import com.cdvcloud.rms.domain.User;

public class UserUtil {
	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static CommonParameters getUserInfo(HttpServletRequest request, CommonParameters commonParameters) {
		HttpSession session = request.getSession();
		commonParameters.setUserName(String.valueOf(session.getAttribute(Constants.USERNAME)));
		commonParameters.setAreaName(String.valueOf(session.getAttribute(Constants.AREANAME)));
		commonParameters.setAreaCode(String.valueOf(session.getAttribute(Constants.AREACODE)));
		commonParameters.setRoleName(String.valueOf(session.getAttribute(Constants.USERROLE)));
		commonParameters.setConsumerName(String.valueOf(session.getAttribute(Constants.CONSUMERNAME)));
		commonParameters.setDepartmentId((List<Object>) session.getAttribute(Constants.DEPARTMENTID));
		commonParameters.setCompanyId(String.valueOf(session.getAttribute(Constants.CONSUMERID)));
		//如果包含统一用户id
		if (null != session.getAttribute(Constants.CASUSERID)) {
			commonParameters.setCasUserId(String.valueOf(session.getAttribute(Constants.CASUSERID)));
		}else{
			commonParameters.setCasUserId(String.valueOf(session.getAttribute(Constants.USERID)));
		}
		return commonParameters;
	}
	/**
	 * 获取用户信息 重载
	 * @param useInfo 传入用户信息
	 * @param commonParameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static CommonParameters getUserInfo(Map<String,Object> useInfo,CommonParameters commonParameters){
		commonParameters.setUserName(String.valueOf(useInfo.get(Constants.USERNAME)));
		commonParameters.setAreaName(String.valueOf(useInfo.get(Constants.AREANAME)));
		commonParameters.setAreaCode(String.valueOf(useInfo.get(Constants.AREACODE)));
		commonParameters.setRoleName(String.valueOf(useInfo.get(Constants.USERROLE)));
		commonParameters.setConsumerName(String.valueOf(useInfo.get(Constants.CONSUMERNAME)));
		commonParameters.setDepartmentId((List<Object>)useInfo.get(Constants.DEPARTMENTID));
		return commonParameters;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static CommonParameters getUserInfo(CommonParameters commonParameters, Map<String, Object> userMap) {
		String userName = String.valueOf(userMap.get(User.NAME));
		String loginName = String.valueOf(userMap.get(User.EMAIL));
		String roleName = null;
		if (null != userMap.get(User.ROLE)) {
			// 用户角色
			List<Map<String, Object>> roleList = (List<Map<String, Object>>) userMap.get(User.ROLE);
			if (null != roleList && roleList.size() > 0) {
				Map<String, Object> roleMap = roleList.get(0);
				roleName = String.valueOf(roleMap.get(Role.NAME));
			}
		}
		List<Object> departmentid = null;
		if (null != userMap.get(User.DEPARTMENT)) {
			// 用户部门
			List<Map<String, Object>> roleList = (List<Map<String, Object>>) userMap.get(User.DEPARTMENT);
			if (null != roleList && roleList.size() > 0) {
				List<Object> depIds = new ArrayList<Object>();
				for (Map<String, Object> dep : roleList) {
					depIds.add(dep.get(Department.ID));
				}
				departmentid = depIds;
			}
		}
		String areaCode = "";
		String areaName = "";
		if (null != userMap.get(User.AREA)) {
			// 用户地区
			Map<String, Object> areaMap = (Map<String, Object>) userMap.get(User.AREA);
			areaCode = String.valueOf(areaMap.get("code"));
			areaName = String.valueOf(areaMap.get("name"));
		}
		String consumerName = String.valueOf(userMap.get(User.CONSUMERNAME));
		commonParameters.setRoleName(roleName);
		commonParameters.setUserName(userName);
		commonParameters.setLoginName(loginName);
		commonParameters.setAreaName(areaName);
		commonParameters.setAreaCode(areaCode);
		commonParameters.setConsumerName(consumerName);
		commonParameters.setDepartmentId(departmentid);
		//如果包含统一用户id
		if (userMap.containsKey(User.USERID) && null != userMap.get(User.USERID) && "" != userMap.get(User.USERID)) {
			commonParameters.setCasUserId(String.valueOf(userMap.get(User.USERID)));
		}else{
			commonParameters.setCasUserId(commonParameters.getUserId());
		}
		return commonParameters;
	}

	/**
	 * 获取用户角色
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	public static String getUserRole(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userRole = String.valueOf(session.getAttribute(Constants.USERROLE));
		return userRole;
	}

	/**
	 * 获取用户名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = String.valueOf(session.getAttribute(Constants.USERNAME));
		return userName;
	}

	/**
	 * 获取用户单位id
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getUserDepartmentId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Object> departmentId = (List<Object>) session.getAttribute(Constants.DEPARTMENTID);
		return departmentId;
	}

	/**
	 * 获取地区code
	 * 
	 * @param request
	 * @return
	 */
	public static String getAreaCode(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String areaCode = String.valueOf(session.getAttribute(Constants.AREACODE));
		return areaCode;
	}

	/**
	 * 获取地区名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getAreaName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String areaName = String.valueOf(session.getAttribute(Constants.AREANAME));
		return areaName;
	}

	/**
	 * 获取用户所属商id
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserConsumerId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String consumerId = String.valueOf(session.getAttribute(Constants.CONSUMERID));
		return consumerId;
	}

	/**
	 * 获取用户所属商名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserConsumerName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String consumerName = String.valueOf(session.getAttribute(Constants.CONSUMERNAME));
		return consumerName;
	}

	/**
	 * 保存用户名称和用户信息到session
	 * 
	 * @param request
	 * @param userMap
	 */
	@SuppressWarnings("unchecked")
	public static void saveUserSession(HttpServletRequest request, Map<String, Object> userMap) {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.USERID, String.valueOf(userMap.get(User.ID)));
		session.setAttribute(Constants.USERNAME, String.valueOf(userMap.get(User.NAME)));
		session.setAttribute(Constants.USERREALNAME, String.valueOf(userMap.get(User.REALNAME)));
		if (null != userMap.get(User.ROLE)) {
			// 用户角色
			List<Map<String, Object>> roleList = (List<Map<String, Object>>) userMap.get(User.ROLE);
			if (null != roleList && roleList.size() > 0) {
				Map<String, Object> roleMap = roleList.get(0);
				session.setAttribute(Constants.USERROLE, String.valueOf(roleMap.get(Role.NAME)));
				session.setAttribute(Constants.ROLEALIAS, String.valueOf(roleMap.get(Role.ALIAS)));
			}
		}
		if (null != userMap.get(User.DEPARTMENT)) {
			// 用户部门
			List<Map<String, Object>> roleList = (List<Map<String, Object>>) userMap.get(User.DEPARTMENT);
			if (null != roleList && roleList.size() > 0) {
				List<Object> depIds = new ArrayList<Object>();
				for (Map<String, Object> dep : roleList) {
					depIds.add(dep.get(Department.ID));
				}
				session.setAttribute(Constants.DEPARTMENTID, depIds);
			}
		}
		if (null != userMap.get(User.AREA)) {
			// 用户地区
			Map<String, Object> areaMap = (Map<String, Object>) userMap.get(User.AREA);
			session.setAttribute(Constants.AREACODE, String.valueOf(areaMap.get("code")));
			session.setAttribute(Constants.AREANAME, String.valueOf(areaMap.get("name")));
		}
		session.setAttribute(Constants.CONSUMERID, String.valueOf(userMap.get(User.CONSUMERID)));
		session.setAttribute(Constants.CONSUMERNAME, String.valueOf(userMap.get(User.CONSUMERNAME)));
		if (null != userMap.get(User.USERID) && !"".equals(userMap.get(User.USERID)) && !"null".equals(userMap.get(User.USERID))) {
			session.setAttribute(Constants.CASUSERID, String.valueOf(userMap.get(User.USERID)));
		}else{
			session.setAttribute(Constants.CASUSERID, String.valueOf(userMap.get(User.ID)));
		}
	}
	
	/**
	 * 保存高级用户名称和用户信息到session
	 * 
	 * @param request
	 * @param userMap
	 */
	public static void saveCustomSession(HttpServletRequest request, Map<String, Object> customMap) {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.USERID, String.valueOf(customMap.get(Custom.ID)));
		session.setAttribute(Constants.USERNAME, String.valueOf(customMap.get(Custom.USERNAME)));
		session.setAttribute(Constants.USERREALNAME, String.valueOf(customMap.get(Custom.REALNAME)));
		session.setAttribute(Constants.COMPANYID, String.valueOf(customMap.get(Custom.COMPANYID)));
		session.setAttribute(Constants.APPCODE, String.valueOf(customMap.get(Custom.APPCODE)));
		session.setAttribute(Constants.SERVICECODE, String.valueOf(customMap.get(Custom.SERVICECODE)));
		session.setAttribute(Constants.OTHERCONFIG, String.valueOf(customMap.get(Custom.OTHERCONFIG)));
		//防止通login登录进去未退出，保存CONSUMERID还存在
		session.setAttribute(Constants.CONSUMERID,null);
	}

	/**
	 * 销毁session
	 * 
	 * @param request
	 */
	public static void removeUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (null != session) {
			session.invalidate();
		}
	}
	

	/**
	 * 设置session属性
	 * 
	 * @param request
	 * @return
	 */
	public static void setSessionProperty(HttpServletRequest request,String propertyKey,String propertyValue) {
		HttpSession session = request.getSession();
		session.setAttribute(propertyKey, propertyValue);
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param params
	 *            根据用户字段获取用户信息
	 * @return
	 * @throws IOException
	 */
	public static String getUser4CAS(HttpServletRequest request, String params) {
		String result = "";
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		if (null != principal) {
			Map<String, Object> mapUserInfo = principal.getAttributes();
			result = StringUtil.decodeString(String.valueOf(mapUserInfo.get(params)));
		}
		return result;
	}
	
}
