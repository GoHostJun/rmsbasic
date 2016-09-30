package com.cdvcloud.rms.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Area;
import com.cdvcloud.rms.domain.Department;
import com.cdvcloud.rms.domain.Role;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JSONUtils;
import com.cdvcloud.rms.util.MD5Util;

@Service
public class UserServiceImpl implements IUserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private BasicDao basicDao;
	@Autowired
	private ConfigurationService configurationService;
	
	@Override
	public Map<String, Object> getUserInfo(Map<String, Object> mapJson) {
		String userName = String.valueOf(mapJson.get("userName"));
		String password = String.valueOf(mapJson.get("password"));
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(User.EMAIL, userName);
		whereMap.put(User.PASSWORD, password);
		Map<String, Object> userMap = basicDao.findOne(User.COLLECTION, whereMap);
		return userMap;
	}
	
	@Override
	public Map<String, Object> getUserInfoByName(Map<String, Object> mapJson) {
		String userName = String.valueOf(mapJson.get("userName"));
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(User.EMAIL, userName);
		Map<String, Object> userMap = basicDao.findOne(User.COLLECTION, whereMap);
//		if (null == userMap || userMap.isEmpty()) {
//			userMap = addUserToAppCode(whereMap);//TODO:需要是放开
//		}
		return userMap;
	}
	
	@Override
	public Map<String, Object> getUserInforById(String id) {
		Map<String, Object> userMap=basicDao.findOne(User.COLLECTION, id);
		return userMap;
	} 
	
	protected Map<String, Object> addUserToAppCode(Map<String, Object> mapSet) {
		Map<String, Object> mapUser = new HashMap<String, Object>();
		Map<String, Object> mapUserRole = new HashMap<String, Object>();
		mapUserRole.put(Role.DESC, "记者");
		List<Map<String, Object>> listRoles = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapRole = basicDao.findOne(Role.COLLECTION, mapUserRole);
		if (null != mapRole) {
			listRoles.add(mapRole);
		}else{
			return null;
		}
		mapUser.put(User.CONSUMERID, mapRole.get(Role.CONSUMERID));
		mapUser.put(User.CONSUMERNAME, mapRole.get(Role.CONSUMERNAME));
		mapUser.put(User.CUSERID, mapRole.get(Role.CUSERID));
		mapUser.put(User.CUSENAME, mapRole.get(Role.CUSENAME));
		mapUser.put(User.CTIME, DateUtil.getCurrentDateTime());
		List<Map<String, Object>> listDepartments = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapDepartment = basicDao.findOne(Department.COLLECTION);
		if (null != mapDepartment) {
			listDepartments.add(mapDepartment);
		}
		
		mapUser.put(User.EMAIL, mapSet.get(User.EMAIL));
		mapUser.put(User.NAME, mapSet.get(User.EMAIL));
		mapUser.put(User.REALNAME, mapSet.get(User.EMAIL));
		mapUser.put(User.EMPNO, "");
		mapUser.put(User.SEX, "");
		mapUser.put(User.STATUS,"1");   //1：启用   0：停用
		mapUser.put(User.ROLE, listRoles);
		mapUser.put(User.DEPARTMENT, listDepartments);
		mapUser.put(User.EMAILADRESS, "");
		mapUser.put(User.PHONE, "");
		Map<String, Object> mapRequest = basicDao.findOne(Area.AREA);
		Map<String, Object> mapArea = new HashMap<String, Object>();
		if (null != mapRequest) {
			mapArea.put("code", mapRequest.get(Area.CODE));
			mapArea.put("name", mapRequest.get(Area.NAME));
		}
		mapUser.put(User.AREA, mapArea);
		mapUser.put(User.PASSWORD, "ytl123456");
		mapUser.put(User.HEADCOLOUR, "#5cb1e7");  //默认头像颜色
		String strId = basicDao.insert(User.COLLECTION, mapUser);
		mapUser.put(User.ID, strId);
		return mapUser;
	}

	@Override
	public Boolean validateUserInfo(String name, String password, Map<String, Object> mapUserRes) {
		String accessToken = "2e307665f346cbd6";
		Long timeStamp = System.currentTimeMillis();
		boolean resultFlag = false;
		try {
			password = MD5Util.getMD5(name + MD5Util.toMD5(password));
			String paramsStr = "accessToken=" + accessToken + "&timeStamp=" + timeStamp + "&loginId=" + name + "&password=" + password;
			String url = configurationService.getValidateUserUrl();
			String result = HttpUtil.sendPost(url, paramsStr);
			if (null == result) {
				return resultFlag;
			}
			JSONObject jsonObj = new JSONObject(result);
			JSONObject jsonData = jsonObj.getJSONObject("data");
			String code = jsonObj.getString("code");
			if (Constants.SZERO.equals(code) && "true".equalsIgnoreCase(jsonData.getString("result"))) {
				//读取用户信息
				if (jsonData.has("userId")) {
					mapUserRes.put("userId", jsonData.getString("userId"));
					mapUserRes.put("loginId", jsonData.getString("loginId"));
				}
				return true;
			} else {
				logger.warn("用户数据校验失败，返回值：" + result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultFlag;
	}

	@Override
	public ResponseObject queryIntegral(CommonParameters commonParameters, String strJson) throws Exception {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		String url = configurationService.getIntegralUrl() + commonParameters.getCompanyId() + "/ytl/" + commonParameters.getUserId() + "/" + commonParameters.getServiceCode()+"/v1/queryById/";
		logger.info("获取积分地址："+url+",参数："+strJson);
		String result = HttpUtil.doPost(url, strJson);
		if (null == result) {
			return resObj;
		}
		resObj = JSONUtils.toObject(result, ResponseObject.class);
		return resObj;
	}
	
}
