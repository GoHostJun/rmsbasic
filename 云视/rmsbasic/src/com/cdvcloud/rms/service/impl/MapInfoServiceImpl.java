package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.service.IMapInfoService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class MapInfoServiceImpl extends BasicService implements IMapInfoService {
	@Autowired
	private INewsDao newsDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject findUnDealCityTasks(CommonParameters commonParameters, String strJson) {
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		List<Map<String, Object>> listResults = new ArrayList<Map<String, Object>>();
		if (StringUtil.isEmpty(mapJson.get("areaCode"))) {
			return super.parameterError("areaCode有误"+mapJson);
		}
		String userId = commonParameters.getUserId();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> mapCheck = new HashMap<String, Object>();
		Map<String, Object> mapPush = new HashMap<String, Object>();
		Map<String, Object> mapShare = new HashMap<String, Object>();
		Map<String, Object> mapElemMatch = new HashMap<String, Object>();
		Map<String, Object> mapShareUserId = new HashMap<String, Object>();
		Map<String, Object> mapShareStatus = new HashMap<String, Object>();
		Map<String, Object> mapShareReadStatus = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listMapShare = new ArrayList<Map<String, Object>>();
		mapShareUserId.put("userId", userId);
		mapShareStatus.put("status", GeneralStatus.waitingPush.status);
		mapShareReadStatus.put("readstatus",  Constants.ZERO);
		listMapShare.add(mapShareUserId);
		listMapShare.add(mapShareStatus);
		listMapShare.add(mapShareReadStatus);
		mapShare.put(QueryOperators.AND, listMapShare);
		mapElemMatch.put(QueryOperators.ELEM_MATCH, mapShare);
		mapCheck.put(News.STATUS,GeneralStatus.checkPass.status);
		mapPush.put(News.STATUS,GeneralStatus.hasPushed.status);
		listMap.add(mapCheck);
		listMap.add(mapPush);
		whereMap.put(QueryOperators.OR, listMap);
		whereMap.put(News.SHAREUSER, mapElemMatch);
		List<String> areaCodes = (List<String>) mapJson.get("areaCode");
		for (String areaCode : areaCodes) {
			whereMap.put(News.AREACODE, areaCode);
			long codeCount = newsDao.countNews(whereMap);
			Map<String,Object> mapCode = new HashMap<String, Object>();
			mapCode.put(News.AREACODE, areaCode);
			mapCode.put("count", codeCount);
			listResults.add(mapCode);
		}
		ResponseObject resObj = new ResponseObject();
		executeSuccess(resObj, listResults);
		return resObj;
	}

}
