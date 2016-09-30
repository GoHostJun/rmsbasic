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
import com.cdvcloud.rms.dao.IDirectPassingPathDao;
import com.cdvcloud.rms.domain.DirectPassingPath;
import com.cdvcloud.rms.domain.PushSet;
import com.cdvcloud.rms.domain.Scan;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.IDirectPassingPathService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
@Service
public class DirectPassingPathServiceImpl extends BasicService implements IDirectPassingPathService {
	private static final Logger logger = Logger.getLogger(PushSetServiceImpl.class);
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IDirectPassingPathDao directPassingPathDao;
	
	

	@Override
	public ResponseObject createObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		resObj=createDirectPassingPath(mapJson,commonParameters);
		return resObj;
	}
	/**
	 * 
	 * @param mapJson
	 * @param commonParameters
	 * @return
	 */
	private ResponseObject createDirectPassingPath(Map<String, Object> mapJson, CommonParameters commonParameters) {
		ResponseObject resObj=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");;
		Map<String,Object>directPassingPathMaps=new HashMap<String,Object>();
		getDirectPassingPathMaps(mapJson, directPassingPathMaps);
		directPassingPathMaps.put(DirectPassingPath.CUSERID, commonParameters.getUserId());
		directPassingPathMaps.put(DirectPassingPath.CUSENAME, commonParameters.getUserName());
		directPassingPathMaps.put(DirectPassingPath.CTIME, DateUtil.getCurrentDateTime());
		directPassingPathMaps.put(DirectPassingPath.UUTIME, DateUtil.getCurrentDateTime());
		directPassingPathMaps.put(DirectPassingPath.CONSUMERID, commonParameters.getCompanyId());
		directPassingPathMaps.put(DirectPassingPath.CONSUMERNAME, commonParameters.getConsumerName());
		directPassingPathMaps.put(DirectPassingPath.DEPARTMENTID, commonParameters.getDepartmentId());
		String directPassingPathId =directPassingPathDao.insertObject(directPassingPathMaps);
		if(!StringUtil.isEmpty(directPassingPathId)){
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "添加", "添加直传路径信息《"+mapJson.get(DirectPassingPath.TARPATHNAME)+"》"));
		}else{
			logger.error("创建直传路径失败");
		}
		return resObj;
	}
	/**
	 * 添加与修改，对象属性拼装
	 * @param mapJson
	 * @param directPassingPathMaps
	 */
	private void getDirectPassingPathMaps(Map<String, Object> mapJson,Map<String,Object>directPassingPathMaps){
		String othermsg=String.valueOf(mapJson.get(DirectPassingPath.OTHERMSG));
			directPassingPathMaps.put(DirectPassingPath.OTHERMSG,othermsg );
		String tarpathaddr=String.valueOf(mapJson.get(DirectPassingPath.TARPATHADDR));
			directPassingPathMaps.put(DirectPassingPath.TARPATHADDR,tarpathaddr );
		String tarpathid=String.valueOf(mapJson.get(DirectPassingPath.TARPATHID));
			directPassingPathMaps.put(DirectPassingPath.TARPATHID,tarpathid );
		String tarpathname=String.valueOf(mapJson.get(DirectPassingPath.TARPATHNAME));
			directPassingPathMaps.put(DirectPassingPath.TARPATHNAME,tarpathname );
		String pushpathaddr=String.valueOf(mapJson.get(DirectPassingPath.PUSHPATHADDR));
			directPassingPathMaps.put(DirectPassingPath.PUSHPATHADDR,pushpathaddr );
		String pushpathname=String.valueOf(mapJson.get(DirectPassingPath.PUSHPATHNAME));
			directPassingPathMaps.put(DirectPassingPath.PUSHPATHNAME,pushpathname );
	}

	@Override
	public ResponseObject findObjectAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> whereMap=new HashMap<String, Object>();
		if(String.valueOf(mapJson.get("isIndex")).isEmpty()){
			// 获取业务参数,并进行相关业务操作
			whereMap = getVommonalityParam(commonParameters, mapJson, DirectPassingPath.TARPATHNAME, DirectPassingPath.CTIME);
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(DirectPassingPath.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = directPassingPathDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = directPassingPathDao.countObject(whereMap);
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
		String Id = String.valueOf(mapJson.get(DirectPassingPath.ID));
		Map<String, Object> directPassingPathSet = directPassingPathDao.findOne(Id);
		if (!directPassingPathSet.isEmpty()) {
			executeSuccess(resObj, directPassingPathSet);
		} else {
			logger.warn("获取记录为空！directPassingPathSetId：" + Id);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String Id = String.valueOf(mapJson.get(DirectPassingPath.ID));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(DirectPassingPath.ID, new ObjectId(Id));
		long result = directPassingPathDao.deleteOne(filter);
		if (result > Constants.ZERO) {
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "删除", "删除直传路径信息《"+Id+"》"));
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject updateObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String Id = String.valueOf(mapJson.get(DirectPassingPath.ID));
		Map<String,Object>directPassingPathMaps=new HashMap<String,Object>();
		getDirectPassingPathMaps(mapJson, directPassingPathMaps);
		directPassingPathMaps.put(DirectPassingPath.UUTIME, DateUtil.getCurrentDateTime());
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(DirectPassingPath.ID, new ObjectId(Id));
		long result = directPassingPathDao.updateOneBySet(filter, directPassingPathMaps,true);
		if (result > Constants.ZERO) {
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "更新", "更新直传路径信息《"+mapJson.get(DirectPassingPath.TARPATHNAME)+"》"));
			executeSuccess(resObj);
		}
		return resObj;
	}

}
