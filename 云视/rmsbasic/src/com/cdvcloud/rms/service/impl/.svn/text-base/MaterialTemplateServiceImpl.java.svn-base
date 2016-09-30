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
import com.cdvcloud.rms.dao.IMaterialTemplateDao;
import com.cdvcloud.rms.domain.MaterialTemplate;
import com.cdvcloud.rms.service.IMaterialTemplateService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
@Service
public class MaterialTemplateServiceImpl extends BasicService implements IMaterialTemplateService {
	private static final Logger logger = Logger.getLogger(PushSetServiceImpl.class);
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IMaterialTemplateDao materialTemplateDao;
	
	

	@Override
	public ResponseObject createObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		resObj=createMaterialTemplate(mapJson,commonParameters);
		return resObj;
	}
	/**
	 * 
	 * @param mapJson
	 * @param commonParameters
	 * @return
	 */
	private ResponseObject createMaterialTemplate(Map<String, Object> mapJson, CommonParameters commonParameters) {
		ResponseObject resObj=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");;
		Map<String,Object>materialTemplateMaps=new HashMap<String,Object>();
		getMaterialTemplateMaps(mapJson, materialTemplateMaps);
		String directPassingPathId =materialTemplateDao.insertObject(materialTemplateMaps);
		if(!StringUtil.isEmpty(directPassingPathId)){
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "添加", "添加素材过滤模板信息《"+mapJson.get(MaterialTemplate.NAME)+"》"));
		}else{
			logger.error("创建素材过滤模板失败");
		}
		return resObj;
	}
	/**
	 * 添加与修改，对象属性拼装
	 * @param mapJson
	 * @param materialTemplateMaps
	 */
	private void getMaterialTemplateMaps(Map<String, Object> mapJson,Map<String,Object>materialTemplateMaps){
		String aframe=String.valueOf(mapJson.get(MaterialTemplate.AFRAME));
			materialTemplateMaps.put(MaterialTemplate.AFRAME,aframe );
		String companyid=String.valueOf(mapJson.get(MaterialTemplate.COMPANYID));
			materialTemplateMaps.put(MaterialTemplate.COMPANYID,companyid );
		String desc=String.valueOf(mapJson.get(MaterialTemplate.DESC));
			materialTemplateMaps.put(MaterialTemplate.DESC,desc );
		String fmt=String.valueOf(mapJson.get(MaterialTemplate.FMT));
			materialTemplateMaps.put(MaterialTemplate.FMT,fmt );
		String isused=String.valueOf(mapJson.get(MaterialTemplate.ISUSED));
			materialTemplateMaps.put(MaterialTemplate.ISUSED,isused );
		String name=String.valueOf(mapJson.get(MaterialTemplate.NAME));
			materialTemplateMaps.put(MaterialTemplate.NAME,name );
		String proportion=String.valueOf(mapJson.get(MaterialTemplate.PROPORTION));
			materialTemplateMaps.put(MaterialTemplate.PROPORTION,proportion );
		String rate=String.valueOf(mapJson.get(MaterialTemplate.RATE));
			materialTemplateMaps.put(MaterialTemplate.RATE,rate );
		String height=String.valueOf(mapJson.get(MaterialTemplate.HEIGHT));
			materialTemplateMaps.put(MaterialTemplate.HEIGHT,height );
		String width=String.valueOf(mapJson.get(MaterialTemplate.WIDTH));
			materialTemplateMaps.put(MaterialTemplate.WIDTH,width );
		String type=String.valueOf(mapJson.get(MaterialTemplate.TYPE));
			materialTemplateMaps.put(MaterialTemplate.TYPE,type );
		String vframe=String.valueOf(mapJson.get(MaterialTemplate.VFRAME));
			materialTemplateMaps.put(MaterialTemplate.VFRAME,vframe );
		
	}

	@Override
	public ResponseObject findObjectAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, MaterialTemplate.NAME, MaterialTemplate.CTIME);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(MaterialTemplate.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = materialTemplateDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = materialTemplateDao.countObject(whereMap);
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
		String Id = String.valueOf(mapJson.get(MaterialTemplate.ID));
		Map<String, Object> materialTemplateSet = materialTemplateDao.findOne(Id);
		if (!materialTemplateSet.isEmpty()) {
			executeSuccess(resObj, materialTemplateSet);
		} else {
			logger.warn("获取记录为空！materialTemplateId：" + Id);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String Id = String.valueOf(mapJson.get(MaterialTemplate.ID));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(MaterialTemplate.ID, new ObjectId(Id));
		long result = materialTemplateDao.deleteOne(filter);
		if (result > Constants.ZERO) {
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "删除", "删除素材过滤模板信息《"+Id+"》"));
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
		String Id = String.valueOf(mapJson.get(MaterialTemplate.ID));
		Map<String,Object>materialTemplateMaps=new HashMap<String,Object>();
		getMaterialTemplateMaps(mapJson, materialTemplateMaps);
		materialTemplateMaps.put(MaterialTemplate.UUTIME, DateUtil.getCurrentDateTime());
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(MaterialTemplate.ID, new ObjectId(Id));
		long result = materialTemplateDao.updateOneBySet(filter, materialTemplateMaps,true);
		if (result > Constants.ZERO) {
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "更新", "更新素材过滤模板信息《"+mapJson.get(MaterialTemplate.NAME)+"》"));
			executeSuccess(resObj);
		}
		return resObj;
	}
	@Override
	public Map<String, Object> findObject(CommonParameters commonParameters, Map<String, Object> map) {
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		map.put(MaterialTemplate.COMPANYID, commonParameters.getCompanyId()) ;
		whereMap.putAll(map);
		logger.info("MaterialTemp Where"+whereMap.toString());
		List<Map<String, Object>> results = materialTemplateDao.findObjectAll(whereMap, 1, 10);
		if(null!=results&&results.size()>0){
			return results.get(0);
		}else{
			return null;
		}
	}

}
