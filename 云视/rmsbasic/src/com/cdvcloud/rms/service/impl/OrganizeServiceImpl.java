package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Area;
import com.cdvcloud.rms.domain.Custom;
import com.cdvcloud.rms.domain.Department;
import com.cdvcloud.rms.domain.Role;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IOrganizeService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.mongodb.BasicDBObject;

@Service
public class OrganizeServiceImpl extends BasicService implements IOrganizeService {

	@Autowired
	private BasicDao basicDao;
	@Autowired
	ConfigurationService configurationService;

	/**
	 * 拼接公共参数信息
	 * 
	 * @param request
	 * @param query
	 * @param mapSet
	 * @return
	 */
	private Map<String, Object> setCommonAttr(CommonParameters query, Map<String, Object> mapSet) {
		mapSet.put(User.CONSUMERID, query.getCompanyId());
		mapSet.put(User.CONSUMERNAME, query.getConsumerName());
		mapSet.put(User.CUSERID, query.getUserId());
		mapSet.put(User.CUSENAME, query.getUserName());
		mapSet.put(User.CTIME, DateUtil.getCurrentDateTime());
		return mapSet;
	}

	@Override
	public ResponseObject getUsersByAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> mapWhere = super.getVommonalityParam(query, mapRequest, User.NAME, User.CTIME);
		//and
		Map<String, Object> mapCheckStatus = new HashMap<String, Object>();
		Map<String, Object> mapCheckStatus2 = new HashMap<String, Object>();
		mapCheckStatus.put(User.STATUS,  new BasicDBObject(QueryOperators.NE, "3"));
		mapCheckStatus2.put(User.STATUS,  new BasicDBObject(QueryOperators.NE, "4"));
		List<Map<String, Object>> staus=new ArrayList<Map<String,Object>>();
		staus.add(mapCheckStatus);
		staus.add(mapCheckStatus2);
		staus.add(mapWhere);
		HashMap<String, Object> mapCheck=new HashMap<String, Object>();
		mapCheck.put(QueryOperators.AND, staus);
		
		//mapWhere.put(User.STATUS, dateConditionType);
		int intCurrentPage = getCurrentPage(mapRequest);
		int intRowNum = getPageNum(mapRequest);
		long totalRecord = basicDao.count(User.COLLECTION, mapCheck);
		Map<String, Object> mapSoft = new HashMap<String, Object>();
		mapSoft.put(User.CTIME, -1);
		List<Map<String, Object>> listUsers = basicDao.find(User.COLLECTION, mapSoft, mapCheck, intCurrentPage, intRowNum);
		Map<String, Object> mapResult = getPages(listUsers, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	@Override
	public ResponseObject getOrgsByAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		int intCurrentPage = getCurrentPage(mapRequest);
		//		int intRowNum = getPageNum(mapRequest);
		int intRowNum = Integer.MAX_VALUE;
		Map<String, Object> mapWhere = getVommonalityParam(query, mapRequest, Department.NAME, Department.CTIME);
		List<Map<String, Object>> listOrgs = basicDao.find(Department.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		long totalRecord = basicDao.count(Department.COLLECTION, mapWhere);
		Map<String, Object> mapResult = getPages(listOrgs, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	@Override
	public ResponseObject getOrgInfo(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		int intCurrentPage = getCurrentPage(mapRequest);
		//		int intRowNum = getPageNum(mapRequest);
		int intRowNum = Integer.MAX_VALUE;
		Map<String, Object> mapWhere = getVommonalityParam(query, mapRequest, Department.NAME, Department.CTIME);
		List<Map<String, Object>> listOrgs = basicDao.find(Department.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		long totalRecord = basicDao.count(Department.COLLECTION, mapWhere);
		Map<String, Object> mapResult = getPages(listOrgs, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	@Override
	public ResponseObject getUserInfoByUserId(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> mapUser = basicDao.findOne(User.COLLECTION, String.valueOf(mapRequest.get("userId")));
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapUser);
		return responseObject;
	}

	@Override
	public ResponseObject getUsersByPartId(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		int intCurrentPage = getCurrentPage(mapRequest);
		//int intRowNum = getPageNum(mapRequest);
		Integer intRowNum = Integer.MAX_VALUE;
		Map<String, Object> mapWhere = getVommonalityParam(query, mapRequest, User.REALNAME, User.CTIME);
		mapWhere.put(User.STATUS, "1");
		String partId=String.valueOf(mapRequest.get("partId"));
		if(!StringUtil.isEmpty(partId)){
			mapWhere.put("department._id", partId);
		}
		String roleName = String.valueOf(mapRequest.get("name"));
		if (!StringUtil.isEmpty(mapRequest.get("name")) && !StringUtil.isEmpty(roleName)) {
			mapWhere.put("role.name", String.valueOf(mapRequest.get("name")));
		}
		List<Map<String, Object>> listUsers = basicDao.find(User.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		long totalRecord = basicDao.count(User.COLLECTION, mapWhere);
		Map<String, Object> mapResult = getPages(listUsers, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}
	@Override
	public ResponseObject getUsersByPartId2(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		int intCurrentPage = getCurrentPage(mapRequest);
		//int intRowNum = getPageNum(mapRequest);
		Integer intRowNum = Integer.MAX_VALUE;
		Map<String, Object> mapWhere = getVommonalityParam(query, mapRequest, User.REALNAME, User.CTIME);
		mapWhere.put(User.STATUS, "1");
		String partId=String.valueOf(mapRequest.get("partId"));
		if(!StringUtil.isEmpty(partId)){
			mapWhere.put("department._id", partId);
		}
		String roleName = String.valueOf(mapRequest.get("name"));
		if (!StringUtil.isEmpty(mapRequest.get("name")) && !StringUtil.isEmpty(roleName)) {
			mapWhere.put("role.name", String.valueOf(mapRequest.get("name")));
		}
		
		Set<Map<String, Object>> listusers = new HashSet<Map<String,Object>>();
		List<Map<String, Object>> listUsers = new ArrayList<Map<String,Object>>() ;
		if(!StringUtil.isEmpty(partId)){
			listusers.addAll(getListUsers(partId, roleName,listusers));
		}else{
			 listUsers = basicDao.find(User.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		}
		long totalRecord = basicDao.count(User.COLLECTION, mapWhere);
		Map<String, Object> mapResult ;
		if(!StringUtil.isEmpty(partId)){
			mapResult=getPages(listusers, totalRecord, intCurrentPage, intRowNum);
		}else{
			mapResult =getPages(listUsers, totalRecord, intCurrentPage, intRowNum);
		}
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}
	private Set<Map<String, Object>> getListUsers(String id,String name,Set<Map<String, Object>> listusers){
		int intCurrentPage = 1;
		Integer intRowNum = Integer.MAX_VALUE;
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(name)) {
			mapWhere.put("role.name", name);
		}
		Map<String, Object> dep2 = basicDao.findOne(Department.COLLECTION, id);
		mapWhere.put("department._id", String.valueOf(dep2.get("_id")));
		List<Map<String, Object>> listUsers2 = basicDao.find(User.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		listusers.addAll(listUsers2);
		Map<String, Object> depMap = new HashMap<String, Object>();
		depMap.put("pId", dep2.get("id"));
		List<Map<String, Object>> dep3 = basicDao.find(Department.COLLECTION, depMap, intCurrentPage, intRowNum);
		for (Map<String, Object> map : dep3) {
			getListUsers(String.valueOf(map.get("_id")), name,listusers);
		}
		return listusers;
	}

	@Override
	public ResponseObject getRolesByAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> mapWhere = super.getVommonalityParam(query, mapRequest, Role.NAME, Role.CTIME);
		int intCurrentPage = getCurrentPage(mapRequest);
		int intRowNum = getPageNum(mapRequest);
		long longCount = basicDao.count(Role.COLLECTION, mapWhere);
		Map<String, Object> mapSoft = new HashMap<String, Object>();
		mapSoft.put(Role.CTIME, -1);
		List<Map<String, Object>> listRoles = basicDao.find(Role.COLLECTION, mapSoft, mapWhere,intCurrentPage, intRowNum);
		Map<String, Object> mapResult = getPages(listRoles,longCount, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	@Override
	public ResponseObject getUsersByRoleId(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		int intCurrentPage = getCurrentPage(mapRequest);
		int intRowNum = getPageNum(mapRequest);
		Map<String, Object> mapWhere = getVommonalityParam(query, mapRequest, null, User.CTIME);
		mapWhere.put("role._id", String.valueOf(mapRequest.get("roleId")));
		List<Map<String, Object>> listUsers = basicDao.find(User.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		long totalRecord = basicDao.count(User.COLLECTION, mapWhere);
		Map<String, Object> mapResult = getPages(listUsers, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	@Override
	public ResponseObject getUsersByRole(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		int intCurrentPage = getCurrentPage(mapRequest);
		Integer intRowNum = Integer.MAX_VALUE;
		String pageNumFlag = String.valueOf(mapRequest.get("pageNum"));
		if (!StringUtil.isEmpty(mapRequest.get("pageNum")) && !StringUtil.isEmpty(pageNumFlag)) {
			intRowNum = Integer.valueOf(pageNumFlag);
		}
		Map<String, Object> mapWhere = getVommonalityParam(query, mapRequest, null, User.CTIME);
		mapWhere.put(User.STATUS, "1");
		String roleName = String.valueOf(mapRequest.get("name"));
		if (!StringUtil.isEmpty(mapRequest.get("name")) && !StringUtil.isEmpty(roleName)) {
			mapWhere.put("role.name", String.valueOf(mapRequest.get("name")));
		}
		List<Map<String, Object>> listUsers = basicDao.find(User.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		long totalRecord = basicDao.count(User.COLLECTION, mapWhere);
		Map<String, Object> mapResult = getPages(listUsers, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	@Override
	public ResponseObject getUserByName(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		int intCurrentPage = getCurrentPage(mapRequest);
		//int intRowNum = getPageNum(mapRequest);
		Integer intRowNum = Integer.MAX_VALUE;
		String pageNumFlag = String.valueOf(mapRequest.get("pageNum"));
		if (!StringUtil.isEmpty(mapRequest.get("pageNum")) && !StringUtil.isEmpty(pageNumFlag)) {
			intRowNum = Integer.valueOf(pageNumFlag);
		}
		Map<String, Object> mapWhere = getVommonalityParam(query, mapRequest, User.NAME, User.CTIME);
		mapWhere.put(User.STATUS, "1");
		List<Map<String, Object>> listUsers = basicDao.find(User.COLLECTION, mapWhere, intCurrentPage, intRowNum);
		long totalRecord = basicDao.count(User.COLLECTION, mapWhere);
		Map<String, Object> mapResult = getPages(listUsers, totalRecord, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}

	@Override
	public ResponseObject addDepartmentByAppCode(CommonParameters query, String strJson) {

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapReauest = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> mapDepartment = JsonUtil.readJSON2Map(strJson);
		mapDepartment = setCommonAttr(query, mapDepartment);
		mapDepartment.put(Department.NAME, String.valueOf(mapReauest.get(Department.NAME)));
		String strId="";
		//生成顶级部门
		if(! StringUtil.isEmpty(mapReauest.get(Department.PID)) ){
			mapDepartment.put(Department.PID, String.valueOf(mapReauest.get(Department.PID)));
			mapDepartment.put(Department.KEYID, String.valueOf(mapReauest.get(Department.KEYID)));
			strId = basicDao.insert(Department.COLLECTION, mapDepartment);
			if (StringUtil.isEmpty(strId)) {
					responseObject.setCode(1);
					responseObject.setMessage("插入数据库失败！");
			}else{
					responseObject.setCode(0);
					responseObject.setMessage("success!");
			}
		}else{
			//重名
			Map<String,Object>mapfilter=new HashMap<String, Object>();
			mapfilter.put(Department.NAME, String.valueOf(mapReauest.get(Department.NAME)));
			mapfilter.put(User.CONSUMERID,mapDepartment.get(User.CONSUMERID));
			Map<String,Object>mapone=	basicDao.findOne(Department.COLLECTION, mapfilter);
			if(!StringUtil.isEmpty(mapone)){
				responseObject.setCode(2);
				responseObject.setMessage("名称重复");
				return responseObject;
			}
			//生成uuid
			String uuid=StringUtil.randomUUID();
		    mapDepartment.put(Department.PID, uuid);
			strId = basicDao.insert(Department.COLLECTION, mapDepartment);
			if (StringUtil.isEmpty(strId)) {
				responseObject.setCode(1);
				responseObject.setMessage("插入数据库失败！");
			} else {
				Map<String,Object> dep=	basicDao.findOne(Department.COLLECTION, strId);
				Map<String,Object> filter=new HashMap<String, Object>();
				String _id=	String.valueOf(dep.get(Department.ID));
				filter.put(Department.ID, new ObjectId(_id));
				Map<String,Object>  update=new HashMap<String, Object>();
				update.put(Department.KEYID, _id);
				long l=basicDao.updateManyBySet(Department.COLLECTION, filter, update);
				if(l>0){
					responseObject.setCode(0);
					responseObject.setMessage("success!");
				}else{
					responseObject.setCode(1);
					responseObject.setMessage("插入数据库失败！");
				}
			}
			
		}
	
		
		return responseObject;
	}

	@Override
	public ResponseObject editDepartmentByAppCode(CommonParameters query, String strJson) {

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapReauest = JsonUtil.readJSON2Map(strJson);

		String _id=String.valueOf(mapReauest.get("_id"));
		String pId=String.valueOf(mapReauest.get("pId"));
		Map<String,Object> filter=new HashMap<String,Object>();
		if(!StringUtil.isEmpty(_id)){
			filter.put("_id", new ObjectId(_id));
		}
		Map<String,Object> update=new HashMap<String,Object>();
		if(!StringUtil.isEmpty(pId)){
			update.put("pId", pId);
		}
		String id=String.valueOf(mapReauest.get("id"));
		if(!StringUtil.isEmpty(id)){
			update.put(Department.KEYID, id);
		}
		update.put(Department.NAME, String.valueOf(mapReauest.get("name")));
		update = setCommonAttr(query, update);
		if(StringUtil.isEmpty(_id)){
			String strId = basicDao.insert(Department.COLLECTION, update);
			if (StringUtil.isEmpty(strId)) {
				responseObject.setCode(1);
				responseObject.setMessage("插入数据库失败！");
			} else {
				responseObject.setCode(0);
				responseObject.setMessage("success!");
			}
		}else{
			long count = basicDao.updateOneBySet(Department.COLLECTION, filter, update);
			if(count>0){
				executeSuccess(responseObject);
			}else{
				updateError(responseObject);
			}
		}


		return responseObject;
	}

	@Override
	public ResponseObject removeDepartmentByAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put("_id", new ObjectId(String.valueOf(mapRequest.get("id"))));
		long longRemove = basicDao.deleteOne(Department.COLLECTION, mapWhere);
		if (0 < longRemove) {
			executeSuccess(responseObject);
		} else {
			deleteError(responseObject);
		}
		return responseObject;
	}

	@Override
	public ResponseObject getInfoByAppCode(CommonParameters query, String strJson) {

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, "此功能暂不开放！");
		return responseObject;
	}

	@Override
	public ResponseObject addUserToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		//校验用户相关信息
		boolean resObject=checkUserInfo(query,mapRequest);
		if(!resObject){
			return parameterError(strJson);
		}
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		String emailString=String.valueOf(mapRequest.get("txtEmail"));
		long userCount = this.findUserCountByProperty(emailString);
		if(userCount > 0){
			return parameterError(strJson);
		}
		Map<String, Object> mapUser = new HashMap<String, Object>();
		mapUser = setCommonAttr(query, mapUser);
		String[] arrRoles = String.valueOf(mapRequest.get("selRoles")).split(",");
		List<Map<String, Object>> listRoles = new ArrayList<Map<String, Object>>();
		for (String strRoleId : arrRoles) {
			Map<String, Object> mapRole = basicDao.findOne(Role.COLLECTION, strRoleId);
			listRoles.add(mapRole);
		}
		String[] arrDepartments = String.valueOf(mapRequest.get("txtDepartments")).split(",");
		List<Map<String, Object>> listDepartments = new ArrayList<Map<String, Object>>();
		for (String straDepartmentId : arrDepartments) {
			Map<String, Object> mapDepartment = basicDao.findOne(Department.COLLECTION, straDepartmentId);
			listDepartments.add(mapDepartment);
		}
		mapUser.put(User.NAME, mapRequest.get("txtUsername"));
		mapUser.put(User.EMPNO, mapRequest.get("txtEmpNo"));
		mapUser.put(User.SEX, mapRequest.get("selSex"));
		mapUser.put(User.STATUS,"1");   //1：启用   0：停用
		mapUser.put(User.ROLE, listRoles);
		mapUser.put(User.DEPARTMENT, listDepartments);
		mapUser.put(User.REALNAME, mapRequest.get("txtRealUsername"));
		mapUser.put(User.EMAIL, mapRequest.get("txtEmail"));
		mapUser.put(User.EMAILADRESS, mapRequest.get("emailAddress"));
		mapUser.put(User.PHONE, mapRequest.get("txtPhone"));
		mapUser.put(User.NAME, mapRequest.get("txtUsername"));
		Map<String, Object> mapArea = new HashMap<String, Object>();
		mapArea.put("code", mapRequest.get("selAreaCode"));
		mapArea.put("name", mapRequest.get("selAreaName"));
		mapUser.put(User.AREA, mapArea);
		mapUser.put(User.PASSWORD, "ytl123456");
		mapUser.put(User.HEADCOLOUR, "#5cb1e7");  //默认头像颜色
		String strId = basicDao.insert(User.COLLECTION, mapUser);
		if (!StringUtil.isEmpty(strId)) {
			//同时更新用户id到表里的userid
			Map<String, Object> mapUserId = new HashMap<String, Object>();
			Map<String, Object> mapFilter = new HashMap<String, Object>();
			mapUserId.put("userid", strId);
			mapFilter.put(User.EMAIL, emailString);
			basicDao.updateOneBySet(User.COLLECTION, mapFilter, mapUserId);
			executeSuccess(responseObject);
		}
		return responseObject;
	}
	@Override
	public ResponseObject batchImportUsers(CommonParameters query, Map<String, Object>  mapRequest) {

//		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapUser = new HashMap<String, Object>();
		mapUser = setCommonAttr(query, mapUser);
		String userListStr=String.valueOf(mapRequest.get("userList"));
		if(StringUtil.isEmpty(userListStr)){
			return parameterError(query);
		}
//		List<Map<String,Object>> userList=(List<Map<String,Object>> )mapRequest.get("userList");
//		for (Map<String, Object> user : userList) {
			//校验用户相关信息
//			ResponseObject resObject=checkUserInfo(query,user);
			//判断校验成功失败
			//保存用户操作
			//判断保存成功失败
			//保存失败错误信息插入list
//		}
		return null;
	}
	
	/**
	 * 校验用户信息是否合法
	 * @param query
	 * @param user
	 * @return
	 */
	private boolean checkUserInfo(CommonParameters query,Map<String, Object> user){
		if(StringUtil.isEmpty(String.valueOf(user.get("txtUsername")))||StringUtil.isEmpty(String.valueOf(user.get("selSex")))
				||StringUtil.isEmpty(String.valueOf(user.get("txtEmail")))||StringUtil.isEmpty(String.valueOf(user.get("selAreaCode")))
				||StringUtil.isEmpty(String.valueOf(user.get("selAreaName")))){
			return false;
		}
		return true;
	}

	@Override
	public ResponseObject addRoleToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapRole = new HashMap<String, Object>();
		mapRole = setCommonAttr(query, mapRole);
		mapRole.put(Role.NAME, mapRequest.get("txtRolename"));
		mapRole.put(Role.ALIAS, mapRequest.get("txtRolealias"));
		mapRole.put(Role.DESC, mapRequest.get("txtRoleDesc"));
		String strId = basicDao.insert(Role.COLLECTION, mapRole);
		if (!StringUtil.isEmpty(strId)) {
			executeSuccess(responseObject);
		}
		return responseObject;
	}

	@Override
	public ResponseObject updateUserToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapUser = new HashMap<String, Object>();
		mapUser = setCommonAttr(query, mapUser);
		String[] arrRoles = String.valueOf(mapRequest.get("selRoles")).split(",");
		List<Map<String, Object>> listRoles = new ArrayList<Map<String, Object>>();
		for (String strRoleId : arrRoles) {
			Map<String, Object> mapRole = basicDao.findOne(Role.COLLECTION, strRoleId);
			listRoles.add(mapRole);
		}
		String[] arrDepartments = String.valueOf(mapRequest.get("txtDepartments")).split(",");
		List<Map<String, Object>> listDepartments = new ArrayList<Map<String, Object>>();
		for (String straDepartmentId : arrDepartments) {
			Map<String, Object> mapDepartment = basicDao.findOne(Department.COLLECTION, straDepartmentId);
			listDepartments.add(mapDepartment);
		}
		mapUser.put(User.NAME, mapRequest.get("txtUsername"));
		mapUser.put(User.EMPNO, mapRequest.get("txtEmpNo"));
		mapUser.put(User.SEX, mapRequest.get("selSex"));
		mapUser.put(User.STATUS, mapRequest.get("selStatus"));
		mapUser.put(User.ROLE, listRoles);
		mapUser.put(User.DEPARTMENT, listDepartments);
		mapUser.put(User.REALNAME, mapRequest.get("txtRealUsername"));
		mapUser.put(User.EMAILADRESS, mapRequest.get("emailAddress"));
		mapUser.put(User.PHONE, mapRequest.get("txtPhone"));
		mapUser.put(User.NAME, mapRequest.get("txtUsername"));
		Map<String, Object> mapArea = new HashMap<String, Object>();
		mapArea.put("code", mapRequest.get("selAreaCode"));
		mapArea.put("name", mapRequest.get("selAreaName"));
		mapUser.put(User.AREA, mapArea);
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(User.ID, new ObjectId(String.valueOf(mapRequest.get("userId"))));
		long longUpdate = basicDao.updateManyBySet(User.COLLECTION, mapWhere, mapUser, true);
		if (0 < longUpdate) {
			executeSuccess(responseObject);
		} else {
			updateError(responseObject);
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteUserToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(User.ID, new ObjectId(String.valueOf(mapRequest.get("userId"))));
		long longDelete = basicDao.deleteOne(User.COLLECTION, mapWhere);
		if (0 < longDelete) {
			executeSuccess(responseObject);
		} else {
			deleteError(responseObject);
		}
		return responseObject;
	}

	@Override
	public ResponseObject updateRoleToAppCode(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		Map<String , Object> filter = new HashMap<String, Object>();
		filter.put(User.ROLE+"._id",mapRequest.get(Role.ID));
		List<Map<String, Object>> ret =  basicDao.find(User.COLLECTION, filter, 1, 10000000);
		if(null!=ret&&ret.size()>0){
			updateError(responseObject);
		}else{
			Map<String, Object> set = new HashMap<String, Object>();
			set.put(Role.ALIAS, mapRequest.get(Role.ALIAS));
			set.put(Role.DESC, mapRequest.get(Role.DESC));
			set.put(Role.NAME, mapRequest.get(Role.NAME));
			Long index = basicDao.updateOneBySet(Role.COLLECTION, filter, set);
			if(index>0){
				executeSuccess(responseObject);
			}
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteRoleToAppCode(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		Map<String , Object> filterUser = new HashMap<String, Object>();
		filterUser.put(User.ROLE+"._id",mapRequest.get(Role.ID));
		List<Map<String, Object>> ret =  basicDao.find(User.COLLECTION, filterUser, 1, Integer.MAX_VALUE);
		if(null!=ret&&ret.size()>0){
			updateError(responseObject);
		}else{
			Map<String , Object> filter = new HashMap<String, Object>();
			filter.put(Role.ID,new ObjectId(String.valueOf(mapRequest.get(Role.ID))));
			Long index = basicDao.deleteOne(Role.COLLECTION, filter);
			if(index>0){
				executeSuccess(responseObject);
			}
		}
		return responseObject;
	}

	@Override
	public ResponseObject resetPwdToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapUser = new HashMap<String, Object>();
		mapUser.put(User.PASSWORD, "ytl123456");
		mapUser.put(User.UUTIME, DateUtil.getCurrentDateTime());
		@SuppressWarnings("unchecked")
		List<String> userIds = (List<String>) mapRequest.get("userIds");
		if (null == userIds || Constants.ONE > userIds.size()) {
			return parameterError("userIds,"+mapRequest);
		}
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		for (String userId : userIds) {
			mapWhere.put(User.ID, new ObjectId(userId));
			basicDao.updateManyBySet(User.COLLECTION, mapWhere, mapUser, true);
		}
		executeSuccess(responseObject);
		return responseObject;
	}

	@Override
	public ResponseObject updateUserByUserId(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapUser = new HashMap<String, Object>();
		if (null == mapRequest.get("txtName") || null == mapRequest.get("txtColour") || null == mapRequest.get("emailAddress") || null == mapRequest.get("txtPhone")) {
			return responseObject;
		}
		mapUser.put(User.NAME, mapRequest.get("txtName"));
		mapUser.put(User.EMAILADRESS, mapRequest.get("emailAddress"));
		mapUser.put(User.PHONE, mapRequest.get("txtPhone"));
		mapUser.put(User.HEADCOLOUR, mapRequest.get("txtColour"));
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(User.ID, new ObjectId(String.valueOf(mapRequest.get("userId"))));
		long longUpdate = basicDao.updateManyBySet(User.COLLECTION, mapWhere, mapUser, true);
		if (0 < longUpdate) {
			executeSuccess(responseObject);
		} else {
			updateError(responseObject);
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteUsersByIds(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		@SuppressWarnings("unchecked")
		List<String> userIds = (List<String>) mapRequest.get("userIds");
		if (null == userIds || Constants.ONE > userIds.size()) {
			return parameterError("userIds,"+mapRequest);
		}
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		for (String userId : userIds) {
			mapWhere.put(User.ID, new ObjectId(userId));
			basicDao.deleteOne(User.COLLECTION, mapWhere);
		}
		executeSuccess(responseObject);
		return responseObject;
	}

	@Override
	public long findUserCountByProperty(String emailString) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(emailString)) {
			whereMap.put(User.EMAIL, emailString);
		}
		long totalRecord = basicDao.count(User.COLLECTION, whereMap);
		return totalRecord;
	}

	@Override
	public ResponseObject updatePwdToAppCode(String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);

		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapUser = new HashMap<String, Object>();
		String passwordString = String.valueOf(mapRequest.get("password"));
		if (!StringUtil.isEmpty(passwordString)) {
			mapUser.put(User.PASSWORD, passwordString);
		}
		mapUser.put(User.UUTIME, DateUtil.getCurrentDateTime());
		String userId = (String) mapRequest.get("userId");
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(userId)) {
			mapWhere.put(User.ID, new ObjectId(userId));
		}
		long longUpdate = basicDao.updateManyBySet(User.COLLECTION, mapWhere, mapUser, true);
		if (0 < longUpdate) {
			executeSuccess(responseObject);
		} else {
			updateError(responseObject);
		}
		return responseObject;
	}

	@Override
	public Map<String, Object> getAccessFromUnion(CommonParameters query) {
		StringBuffer params = new StringBuffer();
		params.append("accessKey=" + configurationService.getUnionKey());
		params.append("&timeStamp=" + String.valueOf(System.currentTimeMillis()));
		String tokenUrl = configurationService.getUnionTokenUrl() + "auth/token/getAccessToken/";
		String accessResultJson = HttpUtil.sendPost(tokenUrl, params.toString());
		Map<String, Object> map = JsonUtil.readJSON2Map(accessResultJson);
		return map;
	}

	@Override
	public Map<String, Object> getUnionUsersByAppid(CommonParameters query, String access) {
		StringBuffer params = new StringBuffer();
		params.append("accessToken=" + access);
		params.append("&timeStamp=" + String.valueOf(System.currentTimeMillis()));
		params.append("&appId="+configurationService.getAppid());
		String tokenUrl = configurationService.getUnionUserUrl() + "users/getUsersByAppId/";
		String accessResultJson = HttpUtil.sendPost(tokenUrl, params.toString());
		Map<String, Object> map = JsonUtil.readJSON2Map(accessResultJson);
		return map;
	}

	public ResponseObject setUserIdNotNull(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(User.USERID, "");
		Map<String, Object> sortFilter = getSortParam(User.USERID, Constants.SZERO);
		basicDao.find(User.COLLECTION, sortFilter, mapWhere, 1, Integer.MAX_VALUE);
		return responseObject;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject syncUserByUnion(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try {
			String isTken=configurationService.getIsUnionToken();
			 Map<String, Object> tokenMap=new HashMap<String, Object>();
			if("0".equals(isTken)){
				tokenMap = getAccessFromUnion(query);
			}else {
				tokenMap.put("code", "0");
				Map<String, Object> dataMap=new HashMap<String, Object>();
				dataMap.put("accessToken", "");
				tokenMap.put("data", dataMap);
			}
			 if(null!=tokenMap&&"0".equals(tokenMap.get("code"))){
				 Map<String,Object>tokenData=(Map<String, Object>)
				 tokenMap.get("data");
				 String accessToken=String.valueOf(tokenData.get("accessToken"));
				 Map<String, Object> unionUsersMap = getUnionUsersByAppid(query, accessToken);
				 if (null!=unionUsersMap&&"0".equals(String.valueOf(unionUsersMap.get("code")))) {
					Map<String, Object> unionDataUsers = (Map<String, Object>) unionUsersMap.get("data");
					List<Map<String, Object>> unionUsers = (List<Map<String, Object>>) unionDataUsers.get("users");
					Map<String, Object> mapWhere = new HashMap<String, Object>();
					Map<String, Object> sortFilter = getSortParam(User.USERID, Constants.SZERO);
					List<Map<String, Object>> users = basicDao.find(User.COLLECTION, sortFilter, mapWhere, 1, Integer.MAX_VALUE);
					List<Map<String, Object>> syncUsers = new ArrayList<Map<String, Object>>();
					Map<String, Object> mapBack = new HashMap<String, Object>();
					mapBack.put(Custom.COMPANYID, 1);
					List<Map<String, Object>> listCustoms = basicDao.find(Custom.COLLECTION, new HashMap<String, Object>(),
							new HashMap<String, Object>(), mapBack, 1, Integer.MAX_VALUE);
					List<String> customCompanys = getCustoms(listCustoms);
					if (unionUsers.size() > 0) {
						for (Map<String, Object> unionUser : unionUsers) {
							// 未启用直接过
							if ("1".equals(String.valueOf(unionUser.get("userStatus")))) {
								continue;
							}
							String unionUserCompany = String.valueOf(unionUser.get("companyId"));
							long l = 0;
							if (users.size() > 0) {
								Map<String, Map<String,Object>> usersMap=new HashMap<String, Map<String,Object>>();
								for (Iterator<Map<String, Object>> iterator = users.iterator(); iterator.hasNext();) {
									Map<String, Object> user = (Map<String, Object>) iterator.next();
									String key=String.valueOf(user.get(User.EMAIL));
									usersMap.put(key, user);
								}
								String unionKey=String.valueOf(unionUser.get("loginId"));
								Map<String,Object>userMap= usersMap.get(unionKey);
								if(userMap==null){
									syncUsers.add(unionUser);
								}else{
									boolean boUsEq = unionUser.get("userId").equals(userMap.get(User.USERID));
									Object company = "";
									if (unionUser.get("companyId") != null) {
										company = unionUser.get("companyId");
									}
									boolean boComEq = userMap.get(User.CONSUMERID).equals(company);
									if (!StringUtil.isEmpty(unionUserCompany)) {
										if (!customCompanys.contains(unionUserCompany)) {
											l = updateUserByUnion(query, boUsEq, boComEq, userMap, unionUser, 1);
										} else {
											l = updateUserByUnion(query, boUsEq, boComEq, userMap, unionUser, 2);
										}
									} else {
										l = updateUserByUnion(query, boUsEq, boComEq, userMap, unionUser, 3);
									}
									if (l <= 0) {
										return responseObject;
									}
								}
							}
						}
						
						if (syncUsers.size() > 0) {
							Map<String, Object> mapUser =  getUnionUserMap(query);
							setCommonAttr(query, mapUser);
							List<Map<String, Object>> insertList=new ArrayList<Map<String,Object>>();
							for (Map<String, Object> syncUser : syncUsers) {
								Map<String, Object> innerMapUser = new HashMap<String, Object>();
								if (!StringUtil.isEmpty(syncUser.get("companyId"))) {
									if (!customCompanys.contains(syncUser.get("companyId"))) {
										continue;
									} else {
										innerMapUser.remove(User.CONSUMERID);
										innerMapUser.put(User.CONSUMERID, syncUser.get("companyId"));
									}
								}
								innerMapUser.put(User.NAME, syncUser.get("userName"));
								innerMapUser.put(User.REALNAME, syncUser.get("userName"));
								innerMapUser.put(User.EMAIL, syncUser.get("loginId"));
								innerMapUser.put(User.USERID, syncUser.get("userId"));  
								innerMapUser.putAll(mapUser);
								innerMapUser.put(User.STATUS, "0"); // 1：启用 0：停用
								insertList.add(innerMapUser);
							}
							if(insertList.size()>0){
								basicDao.insertMaps(User.COLLECTION, insertList);
							}
						}
						
						return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.detail, "");
					}
				}
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return responseObject;
		}
		return responseObject;
	}
	
	private long updateUserByUnion(CommonParameters query, boolean boUsEq, boolean boComEq, Map<String, Object> user, Map<String, Object> unionUser,
			int type) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(User.ID, new ObjectId(String.valueOf(user.get(User.ID))));
		Map<String, Object> mapSet = new HashMap<String, Object>();
		;
		Map<String, Object> update = setCommonAttr(query, mapSet);
		update.remove(User.CONSUMERID);
		update.remove(User.CTIME);
		update.put(User.UUTIME, DateUtil.getCurrentDateTime());
		long l = 0;
		switch (type) {
		case 1:
			if (!boUsEq) {
				update.put(User.NAME, unionUser.get("userName"));
				update.put(User.REALNAME, unionUser.get("userName"));
				update.put(User.USERID, unionUser.get("userId"));
				l = basicDao.updateOneBySet(User.COLLECTION, filter, update, false);
			}else{
				return 1;
			}
			break;

		case 2:
			// 用户==
			if (boUsEq) {
				// 更新company及其删除role和部门等信息
				if (!boComEq) {
					update.putAll(getComUpdMap(query, unionUser));
					l = basicDao.updateOneBySet(User.COLLECTION, filter, update, false);
				}else{
					return 1;
				}

			} else {
				update.put(User.NAME, unionUser.get("userName"));
				update.put(User.REALNAME, unionUser.get("userName"));
				update.put(User.USERID, unionUser.get("userId"));
				if (!boComEq) {
					update.putAll(getComUpdMap(query, unionUser));
					l = basicDao.updateOneBySet(User.COLLECTION, filter, update, false);
				} else {
					l = basicDao.updateOneBySet(User.COLLECTION, filter, update, false);
				}
			}
			break;
		case 3:
			update = setCommonAttr(query, mapSet);
			if (!boUsEq) {
				update.put(User.NAME, unionUser.get("userName"));
				update.put(User.REALNAME, unionUser.get("userName"));
				update.put(User.USERID, unionUser.get("userId"));
			}
			l = basicDao.updateOneBySet(User.COLLECTION, filter, update, false);

			break;
		default:
			break;
		}

		return l;
	}
	/**
	 * 
	 * @param customs
	 * @return
	 */
	private List<String> getCustoms(List<Map<String, Object>> customs ){
		List<String> customCompany=new ArrayList<String>();
		if(customs.size()>0){
			for (Map<String, Object> custom : customs) {
				customCompany.add(String.valueOf(custom.get(Custom.COMPANYID)));
			}
		}
		return customCompany;
		
	}
	
	/**
	 * 返回由于更新comanyid导致更新的数据
	 * @param query
	 * @param unionUser
	 * @return
	 */
	private Map<String,Object> getComUpdMap(CommonParameters query,Map<String,Object>unionUser){
		Map<String, Object> update = new HashMap<String, Object>();
		 update.put(User.CONSUMERID, unionUser.get("companyId"));
		 query.setCompanyId(String.valueOf(unionUser.get("companyId")));
		 Map<String, Object> mapWhere = super.getVommonalityParam(query,  new HashMap<String, Object>(), Role.NAME, Role.CTIME);
		 mapWhere.put(Role.NAME, "记者");
		 List<Map<String, Object>> listRoles = basicDao.find(Role.COLLECTION, mapWhere,1, Integer.MAX_VALUE);
		 List<Map<String, Object>> listDepartments = new ArrayList<Map<String, Object>>();
			mapWhere.remove(Role.NAME);
			List<Map<String, Object>> mapDepartment = basicDao.find(Department.COLLECTION,mapWhere,1,Integer.MAX_VALUE);
			if(null != mapDepartment&&mapDepartment.size()>0){
				listDepartments.add(mapDepartment.get(0));
				update.put(User.DEPARTMENT, listDepartments);
			}
			List<Map<String, Object>> mapAreas = basicDao.find(Area.AREA,mapWhere,1,Integer.MAX_VALUE);
			if (null != mapAreas&&mapAreas.size()>0) {
				Map<String, Object> mapArea=new HashMap<String, Object>();
				mapArea.put("code", mapAreas.get(0).get(Area.CODE));
				mapArea.put("name", mapAreas.get(0).get(Area.NAME));
				update.put(User.AREA, mapArea);
			}
		 update.put(User.ROLE, listRoles);
		 return update;
	}
	
	
	private Map<String,Object> getUnionUserMap(CommonParameters query){
		Map<String, Object> mapWhere = super.getVommonalityParam(query,  new HashMap<String, Object>(), Role.NAME, Role.CTIME);
		Map<String, Object> mapUser = new HashMap<String, Object>();
		mapWhere.put(Role.NAME, "记者");
		List<Map<String, Object>> listRoles = basicDao.find(Role.COLLECTION, mapWhere,1, Integer.MAX_VALUE);
		List<Map<String, Object>> listDepartments = new ArrayList<Map<String, Object>>();
		mapWhere.remove(Role.NAME);
		List<Map<String, Object>> mapDepartment = basicDao.find(Department.COLLECTION,mapWhere,1,Integer.MAX_VALUE);
		if(null != mapDepartment&&mapDepartment.size()>0){
			listDepartments.add(mapDepartment.get(0));
			mapUser.put(User.DEPARTMENT, listDepartments);
		}
		List<Map<String, Object>> mapAreas = basicDao.find(Area.AREA,mapWhere,1,Integer.MAX_VALUE);
		if (null != mapAreas&&mapAreas.size()>0) {
			Map<String, Object> mapArea=new HashMap<String, Object>();
			mapArea.put("code", mapAreas.get(0).get(Area.CODE));
			mapArea.put("name", mapAreas.get(0).get(Area.NAME));
			mapUser.put(User.AREA, mapArea);
		}
		mapUser.put(User.ROLE, listRoles);
		mapUser.put(User.PASSWORD, "ytl123456");
		mapUser.put(User.HEADCOLOUR, "#5cb1e7");  //默认头像颜色
		return mapUser;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject updateUsersStatus(CommonParameters query, String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try{
			Map<String,Object> jsonMap=JsonUtil.readJSON2Map(strJson);
			if(null!=jsonMap){
				if(!StringUtil.isEmpty(String.valueOf(jsonMap.get(User.ID)))&&!StringUtil.isEmpty(String.valueOf(jsonMap.get(User.ID)))){
					List<String>userIds= (List<String>) jsonMap.get(User.ID);
					String status=String.valueOf(jsonMap.get(User.STATUS));
					for (String id : userIds) {
						Map<String, Object> filter=new HashMap<String, Object>();
						Map<String, Object> update=new HashMap<String, Object>();
						filter.put(User.ID,new ObjectId(id));
						update.put(User.STATUS, status);
						long l=basicDao.updateOneBySet(User.COLLECTION, filter, update);
						if(0>=l){
							return responseObject;
						}
					}
					return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.detail,"");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return responseObject;
		}
		return responseObject;
	}

	public static void main(String[] args) {
		List<String> l1=new ArrayList<String>();
		l1.add("tom");
		l1.add("jac");
		l1.add("lili");
		List<Map<String,Object>>maps=new ArrayList<Map<String,Object>>();
		Map<String,Object>m1=new HashMap<String, Object>();
		Map<String,Object>m2=new HashMap<String, Object>();
		Map<String,Object>m3=new HashMap<String, Object>();
		m1.put("name", "tom");
		m2.put("name", "jac");
		m3.put("name", "zs");
		maps.add(m1);
		maps.add(m2);
		maps.add(m3);
		for (Map<String, Object> map : maps) {
			boolean b=l1.contains(map.get("name"));
			
			System.out.println(b);
		}
		
	}
}
