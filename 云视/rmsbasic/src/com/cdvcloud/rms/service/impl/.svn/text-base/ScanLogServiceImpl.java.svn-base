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
import com.cdvcloud.rms.dao.IScanDao;
import com.cdvcloud.rms.dao.IUserDao;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.CustomUsers;
import com.cdvcloud.rms.domain.Scan;
import com.cdvcloud.rms.domain.ScanLog;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.IScanLogService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class ScanLogServiceImpl  extends BasicService implements IScanLogService {
	private static final Logger logger = Logger.getLogger(ScanLogServiceImpl.class);
	@Autowired
	private BasicDao basicDao;
	
	@Override
	public ResponseObject findObjectAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");;
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, ScanLog.FILENAME, null);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(ScanLog.STARTTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = basicDao.find(ScanLog.SCANLOG,sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = basicDao.count(ScanLog.SCANLOG,whereMap);
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
		String scanId = String.valueOf(mapJson.get(ScanLog.ID));
		Map<String, Object> scan = basicDao.findOne(scanId);
		if (!scan.isEmpty()) {
			executeSuccess(resObj, scan);
		} else {
			logger.warn("获取记录为空！ID：" + strJson);
		}
		return resObj;
	}



}
