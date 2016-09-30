package com.cdvcloud.rms.service.impl;

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
import com.cdvcloud.rms.dao.IWeChatMessageDao;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.domain.WeChatMessage;
import com.cdvcloud.rms.service.IWeChatMessageService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JSONUtils;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
@Service
public class WeChatMessageServiceImpl extends BasicService implements IWeChatMessageService{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(WeChatMessageServiceImpl.class);
	@Autowired
	private IWeChatMessageDao weChatMessageDao;
	@Autowired
	private IWeChatService weChatService;

	@Override
	public ResponseObject getAllWeChatMessage(String json) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		Map<String,Object> jsonMap=JsonUtil.readJSON2Map(json);
		String consumerid=String.valueOf(jsonMap.get(WeChatMessage.CONSUMERID));
		String email=String.valueOf(jsonMap.get(WeChatMessage.EMAIL));
		String status=String.valueOf(jsonMap.get(WeChatMessage.STATUS));
		Map<String, Object> whereMap=new HashMap<String, Object>();
		if(!StringUtil.isEmpty(consumerid)){
			whereMap.put(WeChatMessage.CONSUMERID, consumerid);
		}
		if(!StringUtil.isEmpty(email)){
			String regxValue = ".*" + email + ".*";
			Map<String, Object> regxMap = new HashMap<String, Object>();
			regxMap.put("$regex", regxValue);
			regxMap.put("$options", "i");
			whereMap.put(WeChatMessage.EMAIL, regxMap);
		}
		if(!StringUtil.isEmpty(status)){
			whereMap.put(WeChatMessage.STATUS, Integer.parseInt(status));
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(WeChatMessage.CTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(jsonMap);
		int pageNum = getPageNum(jsonMap);
		List<Map<String, Object>>results =weChatMessageDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = weChatMessageDao.countObject(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}
	@Override
	public ResponseObject updateOrInsertWeChatMessage(String res, Map<String, Object> userMap,
			String url, String weChatJson,
			String weChatMessageId) {
		ResponseObject resObjback = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> resObj;
		try {
			resObj = JSONUtils.json2map(res);
		
		//保存微信message
		if(StringUtil.isEmpty(weChatMessageId)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put(WeChatMessage.BACKMESSAGE, resObj.get("data").toString());
			map.put(WeChatMessage.COUNT, "0");
			map.put(WeChatMessage.EMAIL, userMap.get(User.EMAIL));
			map.put(WeChatMessage.STATUS, resObj.get("status"));
			map.put(WeChatMessage.TEMPLATEMESSAGE, weChatJson);
			map.put(WeChatMessage.USERID, userMap.get(User.USERID));
			map.put(WeChatMessage.WXURL, url);
			map.put(WeChatMessage.CONSUMERID, userMap.get(User.CONSUMERID));
			map.put(WeChatMessage.CONSUMERNAME, userMap.get(User.CONSUMERNAME));
			map.put(WeChatMessage.CTIME, DateUtil.getCurrentDateTime());
			//新增
			String insert=weChatMessageDao.insertObject(map);
			if(!StringUtil.isEmpty(insert)){
				resObjback=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, insert);
			}
		}else{
			//修改
			Map<String,Object> weChatMessageMap=weChatMessageDao.findOne(weChatMessageId);
			int count=Integer.parseInt(String.valueOf(weChatMessageMap.get(WeChatMessage.COUNT)));
			Map<String,Object>update=new HashMap<String,Object>();
			count++;
			update.put(WeChatMessage.COUNT, String.valueOf(count));
			update.put(WeChatMessage.STATUS,   resObj.get("status"));
			update.put(WeChatMessage.UUTIME,   DateUtil.getCurrentDateTime());
			Map<String, Object> filter=new HashMap<String,Object>();
			filter.put(WeChatMessage.ID, new ObjectId(weChatMessageId));
			long l=weChatMessageDao.updateOneBySet(filter, update, false);
			if(l>0){
				resObjback=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, l);

			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resObjback;
	}
	@Override
	public ResponseObject retryPushWx(String strJson) {
		Map<String,Object> jsonMap=JsonUtil.readJSON2Map(strJson);
		String _id=String.valueOf(jsonMap.get("_id"));
		Map<String,Object> weChatMessage=weChatMessageDao.findOne(_id);
		String pushMessageStr=String.valueOf(weChatMessage.get(WeChatMessage.TEMPLATEMESSAGE));
		Map<String, Object> pushMessage = null;
		try {
			pushMessage = JSONUtils.json2map(pushMessageStr);
			pushMessage.put("weChatMessageId", _id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weChatService.sendTempMessage(JsonUtil.writeMap2JSON(pushMessage));
	}
	
	



}
