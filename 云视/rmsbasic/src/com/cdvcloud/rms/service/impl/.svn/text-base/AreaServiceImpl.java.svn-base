package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Area;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.IAreaService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class AreaServiceImpl  extends BasicService implements IAreaService {
	@Autowired
	private BasicDao basicDao;
	
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
	public ResponseObject addAreaToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapArea = new HashMap<String, Object>();
		mapArea = setCommonAttr(query, mapArea);
		mapArea.put(Area.NAME, mapRequest.get("txtName"));
		mapArea.put(Area.CODE, mapRequest.get("txtCode"));
		mapArea.put(Area.TVSTATION, mapRequest.get("txtTVStation"));
		String strId = basicDao.insert(Area.AREA, mapArea);
		if (!StringUtil.isEmpty(strId)) {
			executeSuccess(responseObject);
		}
		return responseObject;
	}
	
	@Override
	public ResponseObject getAreasByAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> mapWhere = super.getVommonalityParam(query, mapRequest, Area.NAME, Area.CTIME);
		int intCurrentPage = getCurrentPage(mapRequest);
		int intRowNum = getPageNum(mapRequest);
		long longCount = basicDao.count(Area.AREA, mapWhere);
		Map<String, Object> mapSoft = new HashMap<String, Object>();
		mapSoft.put(Area.CTIME, -1);
		List<Map<String, Object>> listAreas = basicDao.find(Area.AREA, mapSoft, mapWhere,intCurrentPage, intRowNum);
		Map<String, Object> mapResult = getPages(listAreas,longCount, intCurrentPage, intRowNum);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapResult);
		return responseObject;
	}
	
	@Override
	public ResponseObject deleteAreaToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(Area.ID, new ObjectId(String.valueOf(mapRequest.get("_id"))));
		long longDelete = basicDao.deleteOne(Area.AREA, mapWhere);
		if (0 < longDelete) {
			executeSuccess(responseObject);
		} else {
			deleteError(responseObject);
		}
		return responseObject;
	}
	
	@Override
	public ResponseObject getAreaInfoByAreaId(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> mapArea = basicDao.findOne(Area.AREA, String.valueOf(mapRequest.get("areaId")));
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		executeSuccess(responseObject, mapArea);
		return responseObject;
	}
	
	@Override
	public ResponseObject updateAreaToAppCode(CommonParameters query, String strJson) {
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mapArea = new HashMap<String, Object>();
		mapArea = setCommonAttr(query, mapArea);
		mapArea.put(Area.NAME, mapRequest.get("txtName"));
		mapArea.put(Area.CODE, mapRequest.get("txtCode"));
		mapArea.put(Area.TVSTATION, mapRequest.get("txtTVStation"));
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		mapWhere.put(Area.ID, new ObjectId(String.valueOf(mapRequest.get("areaId"))));
		long longUpdate = basicDao.updateManyBySet(Area.AREA, mapWhere, mapArea, true);
		if (0 < longUpdate) {
			executeSuccess(responseObject);
		} else {
			updateError(responseObject);
		}
		return responseObject;
	}

}
