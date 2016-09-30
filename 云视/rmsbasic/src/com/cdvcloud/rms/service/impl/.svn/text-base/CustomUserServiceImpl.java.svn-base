package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.NewsVo;
import com.cdvcloud.rms.dao.ICustomUserDao;
import com.cdvcloud.rms.domain.CustomUsers;
import com.cdvcloud.rms.service.ICustomUserService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
@Service
public class CustomUserServiceImpl extends BasicService implements ICustomUserService {
	private static final Logger logger = Logger.getLogger(CustomUserServiceImpl.class);
	@Autowired
	private ICustomUserDao customUserDao;
	
	@Override
	public ResponseObject createObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String userId = String.valueOf(mapJson.get(NewsVo.USERID));
		String userName = String.valueOf(mapJson.get(NewsVo.USERNAME));
		Map<String, Object> mapCustomPush = createCustomUser(commonParameters, userId, userName, 1);
		if (null != mapCustomPush) {
			super.executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject findObjectAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, CustomUsers.USERNAME, CustomUsers.CTIME);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(CustomUsers.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = customUserDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = customUserDao.countObject(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		super.executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findObjectById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数
		String customUserId = String.valueOf(mapJson.get(CustomUsers.ID));
		Map<String, Object> customUser = customUserDao.findOne(customUserId);
		if (!customUser.isEmpty()) {
			executeSuccess(resObj, customUser);
		} else {
			logger.warn("获取记录为空！customUserId：" + strJson);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数
		String customUserId = String.valueOf(mapJson.get(CustomUsers.ID));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(CustomUsers.ID, new ObjectId(customUserId));
		long result = customUserDao.deleteOne(filter);
		if (result > Constants.ZERO) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject updateObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String customUserId = String.valueOf(mapJson.get(CustomUsers.ID));
		Map<String, Object> mapCustomUser = new HashMap<String, Object>();
		mapCustomUser.put(CustomUsers.USERID, mapJson.get(NewsVo.USERID));
		mapCustomUser.put(CustomUsers.UUTIME, DateUtil.getCurrentDateTime());
		mapCustomUser.put(CustomUsers.USERNAME, mapJson.get(NewsVo.USERNAME));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(CustomUsers.ID, new ObjectId(customUserId));
		long result = customUserDao.updateOneBySet(filter, mapCustomUser,true);
		if (result > Constants.ZERO) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	private Map<String, Object> createCustomUser(CommonParameters commonParameters, String userId,String userName,int totalnum) {
		Map<String, Object> mapCustomUser = new HashMap<String, Object>();
		mapCustomUser.put(CustomUsers.TOTALNUM, totalnum);
		mapCustomUser.put(CustomUsers.USERNAME, userName);
		mapCustomUser.put(CustomUsers.USERID, userId);
		mapCustomUser.put(CustomUsers.CUSERID, commonParameters.getUserId());
		mapCustomUser.put(CustomUsers.CUSENAME, commonParameters.getUserName());
		mapCustomUser.put(CustomUsers.CTIME, DateUtil.getCurrentDateTime());
		mapCustomUser.put(CustomUsers.UUTIME, DateUtil.getCurrentDateTime());
		mapCustomUser.put(CustomUsers.CONSUMERID, commonParameters.getCompanyId());
		mapCustomUser.put(CustomUsers.CONSUMERNAME, commonParameters.getConsumerName());
		mapCustomUser.put(CustomUsers.DEPARTMENTID, commonParameters.getDepartmentId());
		String customUserId = customUserDao.insertObject(mapCustomUser);
		if (null != customUserId) {
			mapCustomUser.put(CustomUsers.ID, customUserId);
			return mapCustomUser;
		}
		logger.error("创建记录失败！mapCustomUser="+mapCustomUser);
		return null;
	}
}
