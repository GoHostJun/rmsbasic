package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.IWeChatTemplateDao;
import com.cdvcloud.rms.domain.WeChatMessage;
import com.cdvcloud.rms.domain.WechatTemplate;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.service.IWeChatTemplateService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
@Service
public class WeChatTemplateServiceImpl extends BasicService implements IWeChatTemplateService{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(WeChatTemplateServiceImpl.class);
	@Autowired
	private IWeChatTemplateDao weChatTemplateDao;
	@Autowired
	private IWeChatService weChatService;
	@Override
	public ResponseObject addWeChatTemplate(String json) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		Map<String,Object> jsonMap=JsonUtil.readJSON2Map(json);
		//校验tempid是否存在
		String templateId=String.valueOf(jsonMap.get(WechatTemplate.TEMPLATEID));
		Map<String,Object> weChatTemps=weChatService.getTemplates();
		boolean contains=weChatTemps.containsKey(templateId);
		if(!contains){
			return resObj;
		}
		String retStr=weChatTemplateDao.insertObject(jsonMap);
		if(!StringUtil.isEmpty(retStr)){
			resObj=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, retStr);
		}
		return resObj;
	}
	@Override
	public ResponseObject modefyWeChatTemplate(String json) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		Map<String,Object> jsonMap=JsonUtil.readJSON2Map(json);
		//校验tempid是否存在
		String templateId=String.valueOf(jsonMap.get(WechatTemplate.TEMPLATEID));
		Map<String,Object> weChatTemps=weChatService.getTemplates();
		boolean contains=weChatTemps.containsKey(templateId);
		if(!contains){
			return resObj;
		}
		Map<String, Object> filter=new HashMap<String, Object>();
		Map<String, Object> update=new HashMap<String, Object>();
		String _id=String.valueOf(jsonMap.get(WechatTemplate.ID));
		if(!StringUtil.isEmpty(_id)){
			filter.put(WechatTemplate.ID, new ObjectId(_id));
		}
		update.put(WechatTemplate.TITLE, jsonMap.get(WechatTemplate.TITLE));
		update.put(WechatTemplate.TEMPLATEID, jsonMap.get(WechatTemplate.TEMPLATEID));
		long retlong=weChatTemplateDao.updateOneBySet(filter, update, false);
		if(retlong>0){
			resObj=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, retlong);
		}
		return resObj;
	}
	@Override
	public ResponseObject deleWeChatTemplate(String json) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		Map<String,Object> jsonMap=JsonUtil.readJSON2Map(json);
		String _id=String.valueOf(jsonMap.get(WechatTemplate.ID));
		Map<String, Object> filter=new HashMap<String, Object>();
		if(!StringUtil.isEmpty(_id)){
			filter.put(WechatTemplate.ID, new ObjectId(_id));
		}
		long retlong=weChatTemplateDao.deleteOne(filter);
		if(retlong>0){
			resObj=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, retlong);
		}
		return resObj;
	}
	@Override
	public ResponseObject findAllWeChatTemplate(String json) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		Map<String,Object> jsonMap=JsonUtil.readJSON2Map(json);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(WeChatMessage.CTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(jsonMap);
		int pageNum = getPageNum(jsonMap);
		Map<String, Object> filter=new HashMap<String, Object>();
		List<Map<String, Object>>results=weChatTemplateDao.find(sortMap, filter, currentPage, pageNum);
		List<Map<String, Object>> filterResults=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> result : results) {
			//校验tempid是否存在
			String templateId=String.valueOf(result.get(WechatTemplate.TEMPLATEID));
			Map<String,Object> weChatTemps=weChatService.getTemplates();
			boolean contains=weChatTemps.containsKey(templateId);
			Map<String, Object> weChatTemp=new HashMap<String, Object>();
			if(!contains){
				weChatTemp.put(WechatTemplate.TEMPLATEID, "<span style='color:red'>"+result.get(WechatTemplate.TEMPLATEID)+"此id已作废!</span>");
			}else{
				weChatTemp.put(WechatTemplate.TEMPLATEID, result.get(WechatTemplate.TEMPLATEID));
				
			}
			weChatTemp.put(WechatTemplate.TITLE, result.get(WechatTemplate.TITLE));
			weChatTemp.put(WechatTemplate.TYPE, result.get(WechatTemplate.TYPE));
			weChatTemp.put(WechatTemplate.ID, result.get(WechatTemplate.ID));
			
			filterResults.add(weChatTemp);
		}
		// 获取总数
		long totalRecord = weChatTemplateDao.countObject(filter);
		Map<String, Object> resMap = getPages(filterResults, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}
	@Override
	public ResponseObject findWeChatTemplateById(String json) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		Map<String,Object> jsonMap=JsonUtil.readJSON2Map(json);
		String _id=String.valueOf(jsonMap.get(WechatTemplate.ID));
		Map<String, Object> retMap=null;
		if(!StringUtil.isEmpty(_id)){
			 retMap=weChatTemplateDao.findOne(_id);
		}
		if(null!=retMap){
			resObj=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, retMap);
		}
		return resObj;
	}
	/**
	 * 根据type查找对应的对象
	 */
	@Override
	public ResponseObject findWeChatTemplateByType(String type) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		Map<String, Object> whereMap=new HashMap<String, Object>();
		List<Map<String, Object>> weChatTemplates=weChatTemplateDao.findObjectAll(whereMap, 1, Integer.MAX_VALUE);
		if(weChatTemplates.size()>0){
			for (Map<String, Object> weChatTemplate : weChatTemplates) {
				if(type.equals(String.valueOf(weChatTemplate.get(WechatTemplate.TYPE)))){
					resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, weChatTemplate);
					
				}
			}
		}
		return resObj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject initWeChatTemplates(Map<String, Object> weChatTemId) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		Map<String, Object> onairMap=new HashMap<String, Object>();
		onairMap.put("currentPage", 1);
		onairMap.put("pageNum", Integer.MAX_VALUE);
		ResponseObject weChatTemplateRO=findAllWeChatTemplate(JsonUtil.map2Json(onairMap));
		if(weChatTemplateRO.getCode()==0){
			Map<String, Object> resMap =(Map<String, Object>) weChatTemplateRO.getData();
			List<Map<String, Object>> filterResults=(List<Map<String, Object>>) resMap.get("results");
			Map<String,Object> weChatTemplateMap=new HashMap<String, Object>();
			for (Map<String, Object> weChatTemplate : filterResults) {
				weChatTemplateMap.put(String.valueOf(weChatTemplate.get(WechatTemplate.TYPE)), weChatTemplate);
			}
			//unbind bind task check
			if(!weChatTemplateMap.containsKey("unbind")){
				Map<String,Object> saveMap=new HashMap<String, Object>();
				saveMap.put(WechatTemplate.TITLE, "解绑通知");
				saveMap.put(WechatTemplate.TYPE, "unbind");
				saveMap.put(WechatTemplate.TEMPLATEID, weChatTemId.get("unbind"));
				resObj=addWeChatTemplate(JsonUtil.map2Json(saveMap));
				if(resObj.getCode()!=0){
					return resObj;
				}
			}
			if(!weChatTemplateMap.containsKey("bind")){
				Map<String,Object> saveMap=new HashMap<String, Object>();
				saveMap.put(WechatTemplate.TITLE, "绑定通知");
				saveMap.put(WechatTemplate.TYPE, "bind");
				saveMap.put(WechatTemplate.TEMPLATEID, weChatTemId.get("bind"));
				resObj=addWeChatTemplate(JsonUtil.map2Json(saveMap));
				if(resObj.getCode()!=0){
					return resObj;
				}
			}
			if(!weChatTemplateMap.containsKey("task")){
				Map<String,Object> saveMap=new HashMap<String, Object>();
				saveMap.put(WechatTemplate.TITLE, "完成任务提醒");
				saveMap.put(WechatTemplate.TYPE, "task");
				saveMap.put(WechatTemplate.TEMPLATEID, weChatTemId.get("task"));
				resObj=addWeChatTemplate(JsonUtil.map2Json(saveMap));
				if(resObj.getCode()!=0){
					return resObj;
				}
			}
			if(!weChatTemplateMap.containsKey("check")){
				Map<String,Object> saveMap=new HashMap<String, Object>();
				saveMap.put(WechatTemplate.TITLE, "审核通知");
				saveMap.put(WechatTemplate.TYPE, "check");
				saveMap.put(WechatTemplate.TEMPLATEID, weChatTemId.get("check"));
				resObj=addWeChatTemplate(JsonUtil.map2Json(saveMap));
				if(resObj.getCode()!=0){
					return resObj;
				}
			}
			
			
		}
		return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
	}
	
	
	

}
