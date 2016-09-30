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
import com.cdvcloud.rms.common.vo.PushVo;
import com.cdvcloud.rms.dao.ICustomPushDao;
import com.cdvcloud.rms.dao.IPushSetDao;
import com.cdvcloud.rms.domain.BasicObject;
import com.cdvcloud.rms.domain.CustomPush;
import com.cdvcloud.rms.service.ICustomPushService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
@Service
public class CustomPushServiceImpl extends BasicService implements ICustomPushService {
	private static final Logger logger = Logger.getLogger(CustomPushServiceImpl.class);
	@Autowired
	private ICustomPushDao customPushDao;
	@Autowired
	private IPushSetDao pushSetDao;
	
	@Override
	public ResponseObject createObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String pushSetId = String.valueOf(mapJson.get(PushVo.PUSHSETID));
		Map<String, Object> mapPushSet = pushSetDao.findOne(pushSetId);
		if (null == mapPushSet || mapPushSet.isEmpty()) {
			logger.error("没有获取到数据，业务参数pushSetId"+strJson);
			return resObj;
		}
		Map<String, Object> mapCustomPush = createCustomPush(commonParameters, mapPushSet);
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
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, null, CustomPush.CTIME);
		whereMap.put(BasicObject.CUSERID, commonParameters.getUserId());//为了适应管理员也能查询常用设置而添加
		// 排序参数
		Map<String, Object> sortMap = getSortParam(CustomPush.PUSHTOTAL, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = customPushDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = customPushDao.countObject(whereMap);
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
		String customPushId = String.valueOf(mapJson.get(CustomPush.ID));
		Map<String, Object> customPush = customPushDao.findOne(customPushId);
		if (!customPush.isEmpty()) {
			executeSuccess(resObj, customPush);
		} else {
			logger.warn("获取记录为空！customPushId：" + strJson);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数
		String customPushId = String.valueOf(mapJson.get(CustomPush.ID));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(CustomPush.ID, new ObjectId(customPushId));
		long result = customPushDao.deleteOne(filter);
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
		String customPushId = String.valueOf(mapJson.get(CustomPush.ID));
		String pushSetId = String.valueOf(mapJson.get(PushVo.PUSHSETID));
		Map<String, Object> mapPushSet = pushSetDao.findOne(pushSetId);
		if (null == mapPushSet || mapPushSet.isEmpty()) {
			logger.error("没有获取到数据，业务参数pushSetId"+strJson);
			return resObj;
		}
		Map<String, Object> mapCustomPush = new HashMap<String, Object>();
		mapCustomPush.put(CustomPush.PUSHSET, mapPushSet);
		mapCustomPush.put(CustomPush.UUTIME, DateUtil.getCurrentDateTime());
		mapCustomPush.put(CustomPush.PUSHTOTAL, mapJson.get(PushVo.PUSHTOTAL));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(CustomPush.ID, new ObjectId(customPushId));
		long result = customPushDao.updateOneBySet(filter, mapCustomPush,true);
		if (result > Constants.ZERO) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	private Map<String, Object> createCustomPush(CommonParameters commonParameters, Map<String, Object> mapPushSet) {
		Map<String, Object> mapCustomPush = new HashMap<String, Object>();
		mapCustomPush.put(CustomPush.PUSHSET, mapPushSet);
		mapCustomPush.put(CustomPush.PUSHTOTAL, 1);
		mapCustomPush.put(CustomPush.CUSERID, commonParameters.getUserId());
		mapCustomPush.put(CustomPush.CUSENAME, commonParameters.getUserName());
		mapCustomPush.put(CustomPush.CTIME, DateUtil.getCurrentDateTime());
		mapCustomPush.put(CustomPush.UUTIME, DateUtil.getCurrentDateTime());
		mapCustomPush.put(CustomPush.CONSUMERID, commonParameters.getCompanyId());
		mapCustomPush.put(CustomPush.CONSUMERNAME, commonParameters.getConsumerName());
		mapCustomPush.put(CustomPush.DEPARTMENTID, commonParameters.getDepartmentId());
		String customPushId = customPushDao.insertObject(mapCustomPush);
		if (null != customPushId) {
			mapCustomPush.put(CustomPush.ID, customPushId);
			return mapCustomPush;
		}
		logger.error("创建记录失败！mapCustomPush="+mapCustomPush);
		return null;
	}
}
