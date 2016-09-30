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
import com.cdvcloud.rms.domain.CustomUsers;
import com.cdvcloud.rms.domain.Scan;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.IScanService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class ScanServiceImpl  extends BasicService implements IScanService {
	private static final Logger logger = Logger.getLogger(ScanServiceImpl.class);
	@Autowired
	private IScanDao scanDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserService userService;
	
	@Override
	public ResponseObject createObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		resObj=createScan(mapJson,commonParameters);
		return resObj;
	}

	private ResponseObject createScan(Map<String, Object> mapJson, CommonParameters commonParameters) {
		ResponseObject resObj=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");;
		Map<String,Object>scanMaps=new HashMap<String,Object>();
		String companyid=String.valueOf(mapJson.get(Scan.COMPANYID));
		if(!StringUtil.isEmpty(companyid)){
			scanMaps.put(Scan.COMPANYID,companyid );
		}
		String dictype=String.valueOf(mapJson.get(Scan.DICTYPE));
		if(!StringUtil.isEmpty(dictype)){
			scanMaps.put(Scan.DICTYPE,dictype );
		}
		String isused=String.valueOf(mapJson.get(Scan.ISUSED));
		if(!StringUtil.isEmpty(isused)){
			scanMaps.put(Scan.ISUSED,isused );
		}
		String scantype=String.valueOf(mapJson.get(Scan.SCANTYPE));
		if(!StringUtil.isEmpty(scantype)){
			scanMaps.put(Scan.SCANTYPE,scantype );
		}
		String srcdic=String.valueOf(mapJson.get(Scan.SRCDIC));
		if(!StringUtil.isEmpty(srcdic)){
			scanMaps.put(Scan.SRCDIC,srcdic );
		}
		String tardic=String.valueOf(mapJson.get(Scan.TARDIC));
		if(!StringUtil.isEmpty(tardic)){
			scanMaps.put(Scan.TARDIC,tardic );
		}
		String taruserid=String.valueOf(mapJson.get(Scan.TARUSERID));
		if(!StringUtil.isEmpty(taruserid)){
			scanMaps.put(Scan.TARUSERID,taruserid );
		}
		String appcode=String.valueOf(mapJson.get(Scan.APPCODE));
		if(!StringUtil.isEmpty(appcode)){
			scanMaps.put(Scan.APPCODE,appcode );
		}
		String url=String.valueOf(mapJson.get(Scan.URL));
		if(!StringUtil.isEmpty(url)){
			scanMaps.put(Scan.URL,url );
		}
		String service=String.valueOf(mapJson.get(Scan.SERVICE));
		if(!StringUtil.isEmpty(service)){
			Map<String,Object> httpMap=new HashMap<String,Object>();
			httpMap.putAll(scanMaps);
			httpMap.put(CommonParameters.ACCESSTOKEN, String.valueOf(mapJson.get(CommonParameters.ACCESSTOKEN)));
			httpMap.put(CommonParameters.TIMESTAMP, String.valueOf(mapJson.get(CommonParameters.TIMESTAMP)));
			String json=	JsonUtil.map2Json(httpMap);
			String backjson=HttpUtil.doPost(service+"/v1/scanning/setTask/", json);
			Map<String,Object> backMap=	JsonUtil.readJSON2Map(backjson);
			Map<String,Object> data=(Map<String,Object>)backMap.get("data");
			String serviceid =String.valueOf(data.get(Scan.SCANID));
			if(!StringUtil.isEmpty(serviceid)){
				scanMaps.put(Scan.SCANID, serviceid);
			}
			scanMaps.put(Scan.SERVICE,service );
			
		}
		scanMaps.put(Scan.CTIME, DateUtil.getCurrentDateTime());
		scanMaps.put(Scan.UUTIME, DateUtil.getCurrentDateTime());
		//校验用户id和companyid
		if(!StringUtil.isEmpty(taruserid)){
			Map <String,Object> usermap=userService.getUserInforById(taruserid);
			String comsumeid=String.valueOf(usermap.get(User.CONSUMERID)) ;
			if(!comsumeid.equals(companyid)){
				inputError(resObj);
				return resObj;
			}
			
		}
		
		String scanId =scanDao.insertObject(scanMaps);
		if(!StringUtil.isEmpty(scanId)){
			executeSuccess(resObj);
		}else{
			logger.error("创建扫描失败");
		}
		return resObj;
	}

	@Override
	public ResponseObject findObjectAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");;
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(String.valueOf(commonParameters.getCompanyId()))){
			whereMap.put(Scan.COMPANYID, String.valueOf(commonParameters.getCompanyId()));
		}
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(Scan.ISUSED)))){
			whereMap.put(Scan.ISUSED, String.valueOf(mapJson.get(Scan.ISUSED)));
		}
		
		// 排序参数
		Map<String, Object> sortMap = getSortParam(Scan.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = scanDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = scanDao.countObject(whereMap);
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
		String scanId = String.valueOf(mapJson.get(Scan.ID));
		Map<String, Object> scan = scanDao.findOne(scanId);
		if (!scan.isEmpty()) {
			executeSuccess(resObj, scan);
		} else {
			logger.warn("获取记录为空！ScanId：" + strJson);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数
		String scanId = String.valueOf(mapJson.get(Scan.ID));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(Scan.ID, new ObjectId(scanId));
		String service=String.valueOf(mapJson.get(Scan.SERVICE));
		if(!StringUtil.isEmpty(service)){
			Map<String,Object> httpMap=new HashMap<String,Object>();
			httpMap.putAll(filter);
			httpMap.put(CommonParameters.ACCESSTOKEN, String.valueOf(mapJson.get(CommonParameters.ACCESSTOKEN)));
			httpMap.put(CommonParameters.TIMESTAMP, String.valueOf(mapJson.get(CommonParameters.TIMESTAMP)));
			String json=	JsonUtil.map2Json(httpMap);
			String backjson=HttpUtil.doPost(service+"/v1/scanning/stopTask/", json);
		}
		long result = scanDao.deleteOne(filter);
		//v1/scanning/stopTask/
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
		String scanId = String.valueOf(mapJson.get(Scan.ID));
		
		Map<String,Object>scanMaps=new HashMap<String,Object>();
		String companyid=String.valueOf(mapJson.get(Scan.COMPANYID));
			scanMaps.put(Scan.COMPANYID,companyid );
		String dictype=String.valueOf(mapJson.get(Scan.DICTYPE));
			scanMaps.put(Scan.DICTYPE,dictype );
		String isused=String.valueOf(mapJson.get(Scan.ISUSED));
			scanMaps.put(Scan.ISUSED,isused );
		String scantype=String.valueOf(mapJson.get(Scan.SCANTYPE));
			scanMaps.put(Scan.SCANTYPE,scantype );
		String srcdic=String.valueOf(mapJson.get(Scan.SRCDIC));
			scanMaps.put(Scan.SRCDIC,srcdic );
		String tardic=String.valueOf(mapJson.get(Scan.TARDIC));
			scanMaps.put(Scan.TARDIC,tardic );
		String taruserid=String.valueOf(mapJson.get(Scan.TARUSERID));
			scanMaps.put(Scan.TARUSERID,taruserid );
		String appcode=String.valueOf(mapJson.get(Scan.APPCODE));
			scanMaps.put(Scan.APPCODE,appcode );
		String url=String.valueOf(mapJson.get(Scan.URL));
			scanMaps.put(Scan.URL,url );
		String scanid=String.valueOf(mapJson.get(Scan.SCANID));
			scanMaps.put(Scan.SCANID,scanid );
		String service=String.valueOf(mapJson.get(Scan.SERVICE));
			Map<String,Object> httpMap=new HashMap<String,Object>();
			httpMap.putAll(scanMaps);
			httpMap.put(CommonParameters.ACCESSTOKEN, String.valueOf(mapJson.get(CommonParameters.ACCESSTOKEN)));
			httpMap.put(CommonParameters.TIMESTAMP, String.valueOf(mapJson.get(CommonParameters.TIMESTAMP)));
			String json=	JsonUtil.map2Json(httpMap);
			String backjson=HttpUtil.doPost(service+"/v1/scanning/updateTask/", json);
			scanMaps.put(Scan.SERVICE,service );
			
		//校验用户id和companyid
			Map <String,Object> usermap=userService.getUserInforById(taruserid);
			String comsumeid=String.valueOf(usermap.get(User.CONSUMERID)) ;
			if(!comsumeid.equals(companyid)){
				inputError(resObj);
				return resObj;
			}
			
		scanMaps.put(Scan.UUTIME, DateUtil.getCurrentDateTime());
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(Scan.ID, new ObjectId(scanId));
		long result = scanDao.updateOneBySet(filter, scanMaps,true);
		if (result > Constants.ZERO) {
			executeSuccess(resObj);
		}
		return resObj;
	}

}
