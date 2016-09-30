package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Custom;
import com.cdvcloud.rms.domain.Global;
import com.cdvcloud.rms.domain.Role;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ICustomService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class CustomServiceImpl extends BasicService implements ICustomService {
	@Autowired
	BasicDao basicDao;

	/**
	 * 分页查找所有客户
	 */
	@Override
	public ResponseObject getAllCustom(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> mapWhere = super.getVommonalityParam(query, mapRequest, Custom.USERNAME, Custom.CTIME);
		int intCurrentPage = getCurrentPage(mapRequest);
		int intRowNum = getPageNum(mapRequest);
		List<Map<String, Object>> listCustoms = basicDao.find(Custom.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		long totalRecord = basicDao.count(Custom.COLLECTION, mapWhere);
		Map<String, Object> mapResult = getPages(listCustoms, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	/**
	 * 获取客户信息
	 */
	@Override
	public Map<String, Object> getUserInfo(Map<String, Object> mapJson) {
		String userName = String.valueOf(mapJson.get("userName"));
		String password = String.valueOf(mapJson.get("password"));
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Custom.USERNAME, userName);
		whereMap.put(Custom.PASSWORD, password);
		Map<String, Object> userMap = basicDao.findOne(Custom.COLLECTION, whereMap);
		return userMap;
	}

	@Override
	public ResponseObject addCustom(CommonParameters query, String strJson, Map<String, Object> sessionMap) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapReauest = JsonUtil.readJSON2Map(strJson);
		// 返回经过判断map
		Map<String, Object> customMap = getCustomMapByMap(mapReauest).get("customMap");
		Map<String, Object> customMapConditon1 = new HashMap<String, Object>();
		Map<String, Object> customMapConditon2 = new HashMap<String, Object>();
		customMapConditon1.put(Custom.USERNAME, mapReauest.get(Custom.USERNAME));
		customMapConditon2.put(Custom.COMPANYID, mapReauest.get(Custom.COMPANYID));
		// 校验是否有重复记录
		if (!isNotExistByCondition(customMapConditon1, Custom.COLLECTION) || !isNotExistByCondition(customMapConditon2, Custom.COLLECTION)) {
			responseObject = new ResponseObject(GeneralStatus.repeat_error.status, GeneralStatus.repeat_error.enDetail, "");
			return responseObject;
		}
		;
		String customId = basicDao.insert(Custom.COLLECTION, customMap);
		Map<String, Object> filterCustomIdMap = new HashMap<String, Object>();
		filterCustomIdMap.put(Custom.ID, new ObjectId(customId));
		if (StringUtil.isEmpty(customId)) {
			responseObject.setCode(1);
			responseObject.setMessage("插入数据库失败！");
		} else {
			try {
				// 插入user数据库
				Map<String, Object> userMapConditon1 = new HashMap<String, Object>();
				Map<String, Object> userMapConditon2 = new HashMap<String, Object>();
				userMapConditon1.put(User.EMAIL, customMap.get(Custom.USERNAME));
				userMapConditon2.put(User.CONSUMERID, customMap.get(Custom.COMPANYID));
				if (!isNotExistByCondition(userMapConditon1, User.COLLECTION) || !isNotExistByCondition(userMapConditon2, User.COLLECTION)) {
					responseObject = new ResponseObject(GeneralStatus.repeat_error.status, GeneralStatus.repeat_error.enDetail, "");
					basicDao.deleteOne(Custom.COLLECTION, filterCustomIdMap);
					return responseObject;
				}
				if (saveUserMap(query, customMap) == null) {
					basicDao.deleteOne(Custom.COLLECTION, filterCustomIdMap);
					responseObject.setCode(1);
					responseObject.setMessage("插入数据库失败！");
					return createError(responseObject);
				}
				customMap.putAll(sessionMap);
				String userId = basicDao.insert(User.COLLECTION, saveUserMap(query, customMap));
				if (StringUtil.isEmpty(userId)) {
					basicDao.deleteOne(Custom.COLLECTION, filterCustomIdMap);
					responseObject.setCode(1);
					responseObject.setMessage("插入数据库失败！");
				} else {
					//创建3个角色插入数据库
					Map<String, Object> user=basicDao.findOne(User.COLLECTION, userId);
					Map<String,Object> rolejizhe=getRole("记者");
					String jizheroleId=addDefaultRole(user, rolejizhe);
					Map<String,Object> roleAudit=getRole("审核人");
					String AuditroleId=addDefaultRole(user, roleAudit);
					Map<String,Object> roleNews=getRole("通联人");
					String newsroleId=addDefaultRole(user, roleNews);
					responseObject.setCode(0);
					responseObject.setMessage("success!");
				}
			} catch (Exception e) {
				basicDao.deleteOne(Custom.COLLECTION, filterCustomIdMap);
				responseObject.setCode(1);
				responseObject.setMessage("插入数据库失败！");
				return createError(responseObject);
			}
		}

		return responseObject;
	}
	private Map<String,Object> getRole(String roleName){
		Map<String,Object> roleMap=new HashMap<String, Object>();
		if("记者".equals(roleName)){
			roleMap.put(Role.NAME, "记者");
			roleMap.put(Role.ALIAS, "记者");
			roleMap.put(Role.DESC, "记者");
		}else if("通联人".equals(roleName)){
			roleMap.put(Role.NAME, "通联人");
			roleMap.put(Role.ALIAS, "通联人");
			roleMap.put(Role.DESC, "通联人");
		}else if("审核人".equals(roleName)){
			roleMap.put(Role.NAME, "审核人");
			roleMap.put(Role.ALIAS, "审核人");
			roleMap.put(Role.DESC, "审核人");
		}
		return roleMap;
	}
	private String addDefaultRole(Map<String,Object> user,Map<String,Object> role){
		Map<String, Object> mapSet=new HashMap<String, Object>();
		mapSet.put(User.CONSUMERID, user.get(User.CONSUMERID));
		mapSet.put(User.CONSUMERNAME, user.get(User.CONSUMERNAME));
		mapSet.put(User.CUSERID, user.get(User.ID));
		mapSet.put(User.CUSENAME,  user.get(User.EMAIL));
		mapSet.put(User.CTIME, DateUtil.getCurrentDateTime());
		mapSet.put(Role.NAME, role.get(Role.NAME));
		mapSet.put(Role.ALIAS, role.get(Role.ALIAS));
		mapSet.put(Role.DESC, role.get(Role.DESC));
		String strId = basicDao.insert(Role.COLLECTION, mapSet);
		return strId;
	}

	@Override
	public ResponseObject updateCustom(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapReauest = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> customMap = getCustomMapByMap(mapReauest).get("customMap");
		Map<String, Object> userMap = getCustomMapByMap(mapReauest).get("userMap");
		String id = "";
		if (!StringUtil.isEmpty(mapReauest.get("customId"))) {
			id = String.valueOf(mapReauest.get("customId"));
		} else {
			return responseObject;
		}
		Map<String, Object> filter = new HashMap<String, Object>();
		Map<String, Object> filterUser = new HashMap<String, Object>();
		filter.put(Custom.ID, new ObjectId(id));
		filterUser.put(User.EMAIL, customMap.get(Custom.USERNAME));
		long longUpdate = basicDao.updateOneBySet(Custom.COLLECTION, filter, customMap);
		long longUpdateUser = basicDao.updateOneBySet(User.COLLECTION, filterUser, userMap);
		if (0 < longUpdate && 0 < longUpdateUser) {
			executeSuccess(responseObject);
		} else {
			updateError(responseObject);
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteCustomByIds(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		@SuppressWarnings("unchecked")
		List<String> customIds = (List<String>) mapRequest.get("customIds");
		if (null == customIds || Constants.ONE > customIds.size()) {
			return parameterError("customIds," + mapRequest);
		}
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		for (String customId : customIds) {
			mapWhere.put(Custom.ID, new ObjectId(customId));
			// 删除user
			Map<String, Object> customMap = basicDao.findOne(Custom.COLLECTION, mapWhere);
			basicDao.deleteOne(Custom.COLLECTION, mapWhere);
			Map<String, Object> userFilter = new HashMap<String, Object>();
			userFilter.put(User.EMAIL, customMap.get(Custom.USERNAME));
			basicDao.deleteOne(User.COLLECTION, userFilter);
		}
		executeSuccess(responseObject);
		return responseObject;
	}

	@Override
	public ResponseObject deleteCustomById(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		String customId = String.valueOf(mapRequest.get("customId"));
		if (StringUtil.isEmpty(customId)) {
			return parameterError("customId," + mapRequest);
		}
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(Custom.ID, new ObjectId(customId));
		// 删除user
		Map<String, Object> customMap = basicDao.findOne(Custom.COLLECTION, mapWhere);
		Map<String, Object> userFilter = new HashMap<String, Object>();
		userFilter.put(User.EMAIL, customMap.get(Custom.USERNAME));
		long userdelelong = basicDao.deleteOne(User.COLLECTION, userFilter);
		long delelong = basicDao.deleteOne(Custom.COLLECTION, mapWhere);
		if (delelong > 0 && userdelelong > 0) {
			executeSuccess(responseObject);
		} else {
			deleteError(responseObject);
		}
		return responseObject;
	}

	@Override
	public ResponseObject getCustomById(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		String customId = String.valueOf(mapRequest.get("customId"));
		if (StringUtil.isEmpty(customId)) {
			return parameterError("customId," + mapRequest);
		}
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(Custom.ID, new ObjectId(customId));
		Map<String, Object> custom = basicDao.findOne(Custom.COLLECTION, mapWhere);
		if (custom == null) {
			queryError(responseObject);
		} else {
			responseObject.setData(custom);
			executeSuccess(responseObject);
		}
		return responseObject;
	}

	/**
	 * 拼装保存user对象map
	 * 
	 * @param query
	 * @param customMap
	 * @return
	 */
	private Map<String, Object> saveUserMap(CommonParameters query, Map<String, Object> customMap) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		List<Map<String, Object>> listRoles = new ArrayList<Map<String, Object>>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(Role.NAME, "超级管理员");
		// 设置默认的角色
		Map<String, Object> mapRole = new HashMap<String, Object>();
		mapRole.put("_id", "567151d2d9201a0c6c6fd77b");
		mapRole.put("desc", "超级管理员");
		mapRole.put("alias", "superAdmin");
		mapRole.put("name", "超级管理员");
		mapRole.put("ctime", DateUtil.getCurrentDateTime());
		listRoles.add(mapRole);
		// 设置默认部门
		List<Map<String, Object>> listDepartments = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapDepartment = new HashMap<String, Object>();
		mapDepartment.put("_id", "567151d2d9201a0c6c6fd77b");
		mapDepartment.put("id", "567151d2d9201a0c6c6fd77b");
		mapDepartment.put("pId", "");
		mapDepartment.put("name", "superDepartment");
		mapDepartment.put("ctime", DateUtil.getCurrentDateTime());
		listDepartments.add(mapDepartment);
		userMap.put(User.DEPARTMENT, listDepartments);
		// 设置默认地区
		Map<String, Object> mapArea = new HashMap<String, Object>();
		mapArea.put("code", "0086");
		mapArea.put("name", "中国");
		userMap.put(User.AREA, mapArea);

		userMap.put(User.STATUS, "3");
		userMap.put(User.NAME, customMap.get(Custom.REALNAME));
		userMap.put(User.EMAIL, customMap.get(Custom.USERNAME));
		userMap.put(User.ROLE, listRoles);
		userMap.put(User.CONSUMERID, customMap.get(Custom.COMPANYID));
		userMap.put(User.CONSUMERNAME, customMap.get(Custom.CONSUMERNAME));
		userMap.put(User.REALNAME, customMap.get(Custom.REALNAME));
		userMap.put(User.PASSWORD, customMap.get(Custom.PASSWORD));
		userMap.put(User.HEADCOLOUR, "#5cb1e7"); // 默认头像颜色
		userMap.put(User.APPCODE, customMap.get(Custom.APPCODE));
		userMap.put(User.SERVICECODE, customMap.get(Custom.SERVICECODE));
		userMap.put(User.CUSERID, customMap.get("createId"));
		userMap.put(User.CUSENAME, customMap.get("createName"));
		userMap.put(User.CUSENAME, customMap.get("createName"));
		userMap.put(User.CTIME, DateUtil.getCurrentDateTime());
		return userMap;
	}

	private boolean isNotExistByCondition(Map<String, Object> MapConditon, String collection) {
		Map<String, Object> filter = new HashMap<String, Object>();
		for (Entry<String, Object> entry : MapConditon.entrySet()) {
			filter.put(entry.getKey(), entry.getValue());
		}
		Map<String, Object> objectMap = basicDao.findOne(collection, filter);
		return objectMap == null;
	}

	private Map<String, Map<String, Object>> getCustomMapByMap(Map<String, Object> mapReauest) {
		Map<String, Object> customMap = new HashMap<String, Object>();
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(mapReauest.get(Custom.APPCODE))) {
			customMap.put(Custom.APPCODE, mapReauest.get(Custom.APPCODE));
			userMap.put(User.APPCODE, mapReauest.get(Custom.APPCODE));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.COMPANYID))) {
			customMap.put(Custom.COMPANYID, mapReauest.get(Custom.COMPANYID));
			userMap.put(User.CONSUMERID, customMap.get(Custom.COMPANYID));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.OTHERCONFIG))) {
			customMap.put(Custom.OTHERCONFIG, mapReauest.get(Custom.OTHERCONFIG));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.PASSWORD))) {
			customMap.put(Custom.PASSWORD, mapReauest.get(Custom.PASSWORD));
			userMap.put(User.PASSWORD, customMap.get(Custom.PASSWORD));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.REALNAME))) {
			customMap.put(Custom.REALNAME, mapReauest.get(Custom.REALNAME));
			customMap.put(Custom.CONSUMERNAME, mapReauest.get(Custom.REALNAME));
			userMap.put(User.REALNAME, customMap.get(Custom.REALNAME));
			userMap.put(User.CONSUMERNAME, customMap.get(Custom.REALNAME));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.SERVICECODE))) {
			customMap.put(Custom.SERVICECODE, mapReauest.get(Custom.SERVICECODE));
			userMap.put(User.SERVICECODE, mapReauest.get(Custom.SERVICECODE));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.USERNAME))) {
			customMap.put(Custom.USERNAME, mapReauest.get(Custom.USERNAME));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.CONSUMERID))) {
			customMap.put(Custom.CONSUMERID, mapReauest.get(Custom.CONSUMERID));
		}
		if (!StringUtil.isEmpty(mapReauest.get(Custom.CTIME))) {
			customMap.put(Custom.CTIME, DateUtil.getCurrentDateTime());
			userMap.put(User.CTIME, DateUtil.getCurrentDateTime());

		}
		userMap.put(User.UUTIME, DateUtil.getCurrentDateTime());
		customMap.put(Custom.UUTIME, DateUtil.getCurrentDateTime());
		Map<String, Map<String, Object>> backMap = new HashMap<String, Map<String, Object>>();
		backMap.put("customMap", customMap);
		backMap.put("userMap", userMap);
		return backMap;
	}

	@Override
	public ResponseObject getConfiguration(CommonParameters query) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		Map<String, Object> mapGlobal = basicDao.findOne(Global.GLOBAL);
		super.executeSuccess(resObj, mapGlobal);
		return resObj;
	}

	@Override
	public ResponseObject updateConfiguration(CommonParameters query, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		String globalId = String.valueOf(mapJson.get(Global.ID));
		String globalKey = String.valueOf(mapJson.get("globalKey"));
		String globalValue = String.valueOf(mapJson.get("globalValue"));
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		mapQuery.put(Global.ID, new ObjectId(globalId));
		mapUpdate.put(globalKey, globalValue);
		long result = basicDao.updateOneBySet(Global.GLOBAL, mapQuery, mapUpdate);
		if (result > Constants.ZERO) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteConfiguration(CommonParameters query, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		String globalId = String.valueOf(mapJson.get(Global.ID));
		String globalKey = String.valueOf(mapJson.get("globalKey"));
		String globalValue = String.valueOf(mapJson.get("globalValue"));
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		Map<String, Object> updateUnSet = new HashMap<String, Object>();
		mapQuery.put(Global.ID, new ObjectId(globalId));
		updateUnSet.put(globalKey, globalValue);
		mapUpdate.put(QueryOperators.UNSET, updateUnSet);
		long result = basicDao.updateOne(Global.GLOBAL, mapQuery, mapUpdate);
		if (result > Constants.ZERO) {
			executeSuccess(resObj);
		}
		return resObj;
	}

}
