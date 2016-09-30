package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.SystemLogVo;
import com.cdvcloud.rms.dao.ISystemLogDao;
import com.cdvcloud.rms.domain.SystemLog;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
@Service
public class SystemLogServiceImpl extends BasicService implements ISystemLogService {
	@Autowired
	private ISystemLogDao systemLogDao;

	@Override
	public boolean inset(Map<String, Object> systemLogMap) {
		String id = systemLogDao.insert(systemLogMap);
		if(null!=id&&!"".equals(id)){
			return true;
		}
		return false;
	}

	@Override
	public ResponseObject query(CommonParameters query, String strJson) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(query, mapJson, SystemLogVo.LOGDESC, SystemLogVo.CTIME);
		//		//关键字
		//		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.KEYWORD))){
		//			String regxValue = ".*" + String.valueOf(mapJson.get(SystemLogVo.KEYWORD)) + ".*";
		//			Map<String, Object> regxMap = new HashMap<String, Object>();
		//			regxMap.put("$regex", regxValue);
		//			regxMap.put("$options", "i");
		//			whereMap.put(SystemLog.NAME, regxMap);
		//		}
		//操作项
		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.OPERATION))){
			whereMap.put(SystemLog.OPERATION, String.valueOf(mapJson.get(SystemLogVo.OPERATION)));
		}
		//功能项
		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.ACTION))){
			whereMap.put(SystemLog.ACTION, Integer.valueOf(String.valueOf(mapJson.get(SystemLogVo.ACTION))));
		}
		//ip
		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.IP))){
			whereMap.put(SystemLog.IP, Integer.valueOf(String.valueOf(mapJson.get(SystemLogVo.IP))));
		}
		//操作人
		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.CUSERID))){
			whereMap.put(SystemLog.CUSERID, Integer.valueOf(String.valueOf(mapJson.get(SystemLogVo.CUSERID))));
		}
		//创建时间
		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.CTIME))){
			whereMap.put(SystemLog.CTIME, Integer.valueOf(String.valueOf(mapJson.get(SystemLogVo.CTIME))));
		}
		//日志类型
		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.TYPE))){
			whereMap.put(SystemLog.TYPE, Integer.valueOf(String.valueOf(mapJson.get(SystemLogVo.TYPE))));
		}
		
		//创建人
		if(!StringUtil.isEmpty(mapJson.get(SystemLogVo.CUSENAME))){
			String regxValue = ".*" + String.valueOf(mapJson.get(SystemLogVo.CUSENAME)) + ".*";
			Map<String, Object> regxMap = new HashMap<String, Object>();
			regxMap.put("$regex", regxValue);
			regxMap.put("$options", "i");
			whereMap.put(SystemLogVo.CUSENAME, regxMap);
		}

		// 排序参数
		Map<String, Object> sortFilter = getSortParam(SystemLog.CTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		Long total = systemLogDao.count(whereMap);
		List<Map<String, Object>> lists = systemLogDao.query(sortFilter,whereMap, currentPage, pageNum);
		if(null!=lists &&lists.size()>=0){
			Pages pages = new Pages(pageNum,total,currentPage,lists);
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,pages);
		}else{
			return new ResponseObject(GeneralStatus.query_error.status,GeneralStatus.query_error.enDetail,"");
		}
	}

	@Override
	public ResponseObject queryByVisual(CommonParameters query, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

}
