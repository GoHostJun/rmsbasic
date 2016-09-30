package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.MessageVo;
import com.cdvcloud.rms.dao.IMessageDao;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.domain.Message;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.service.IMessageService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class MessageServiceImpl extends BasicService implements IMessageService {
	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);
	@Autowired
	private IMessageDao messageDao;
	@Autowired
	private INewsDao newsDao;

	@Override
	public ResponseObject createMessage(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		mapJson.put(Message.STATUS, Constants.ZERO);
		boolean resBoolean = createMessageObj(mapJson);
		if (resBoolean) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject findMessageAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Message.MSGUSERID, commonParameters.getUserId());
		if (!StringUtil.isEmpty(mapJson.get(MessageVo.TYPE))) {
			whereMap.put(Message.TYPE, mapJson.get(MessageVo.TYPE));
		}
		if (!StringUtil.isEmpty(mapJson.get(MessageVo.STATUS))) {
			whereMap.put(Message.STATUS, mapJson.get(MessageVo.STATUS));
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(Message.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = messageDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = messageDao.countMessage(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findMessageById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		String messageId = String.valueOf(mapJson.get(MessageVo.MESSAGEID));
		Map<String, Object> msgMap = messageDao.findOne(messageId);
		executeSuccess(resObj, msgMap);
		return resObj;
	}

	@Override
	public ResponseObject updateMessage(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		String messageId = String.valueOf(mapJson.get(MessageVo.MESSAGEID));
		if (StringUtil.isEmpty(messageId)) {
			return parameterError("messageId,"+mapJson);
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> updateMap = new HashMap<String, Object>();
		whereMap.put(Message.ID, new ObjectId(messageId));
		updateMap.put(Message.STATUS, Constants.ONE);
		updateMap.put(Message.UUTIME, DateUtil.getCurrentDateTime());
		long resNum = messageDao.updateManyBySet(whereMap, updateMap,false);
		if (resNum > 0) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public boolean createMessageObj(Map<String, Object> msgMap) {
		Map<String, Object> messageMap = new HashMap<String, Object>();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(msgMap.get(MessageVo.MSGUSERID))) {
			messageMap.put(Message.MSGUSERID, msgMap.get(MessageVo.MSGUSERID));
			whereMap.put(Message.MSGUSERID, msgMap.get(MessageVo.MSGUSERID));
		} else {
			logger.error("创建消息记录失败！msgUserId=" + msgMap);
			return false;
		}
		if (!StringUtil.isEmpty(msgMap.get(MessageVo.NEWSID))) {
			messageMap.put(Message.NEWSID, msgMap.get(MessageVo.NEWSID));
			whereMap.put(Message.NEWSID, msgMap.get(MessageVo.NEWSID));
		} else {
			logger.error("创建消息记录失败！newsId=" + msgMap);
			return false;
		}
		if (!StringUtil.isEmpty(msgMap.get(MessageVo.TYPE))) {
			messageMap.put(Message.TYPE, msgMap.get(MessageVo.TYPE));
			whereMap.put(Message.TYPE, msgMap.get(MessageVo.TYPE));
		} else {
			logger.error("创建消息记录失败！type=" + msgMap);
			return false;
		}
		// 判断消息是否已存在，存在则更新，不存在则创建
		messageMap.put(Message.NEWSTITLE, msgMap.get(MessageVo.NEWSTITLE));
		messageMap.put(Message.STATUS, Constants.ZERO);
		messageMap.put(Message.UUTIME, DateUtil.getCurrentDateTime());
		long resNum = messageDao.updateManyBySet(whereMap, messageMap,false);
		if (resNum > 0) {
			return true;
		}
		messageMap.put(Message.MSGTITLE, msgMap.get(MessageVo.TITLE));
		messageMap.put(Message.MSGCONTENT, msgMap.get(MessageVo.CONTENT));
		messageMap.put(Message.CTIME, DateUtil.getCurrentDateTime());
		String messageId = messageDao.insertMessage(messageMap);
		if (null != messageId) {
			messageMap.put(Message.ID, messageId);
			return true;
		}
		logger.error("创建消息记录失败！messageMap=" + messageMap);
		return false;
	}

	@Override
	public ResponseObject updateMessageByNewsId(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		String newsId = String.valueOf(mapJson.get(MessageVo.NEWSID));
		String shareUserId = String.valueOf(mapJson.get("shareUser"));
		String checkUserId = String.valueOf(mapJson.get("checkUser"));
		if (StringUtil.isEmpty(newsId)) {
			return parameterError("messageId,"+mapJson);
		}
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		long resNum = 0;
		mapQuery.put(News.ID, new ObjectId(newsId));
		if (!StringUtil.isEmpty(mapJson.get("shareUser")) && !StringUtil.isEmpty(shareUserId)) {
			mapQuery.put("shareuser.userId", commonParameters.getUserId());
			mapUpdate.put("shareuser.$.readstatus", Constants.ONE);
			resNum = newsDao.updateManyBySet(mapQuery, mapUpdate,true);
		}else if(!StringUtil.isEmpty(mapJson.get("checkUser")) && !StringUtil.isEmpty(checkUserId)){
			mapQuery.put("checkuser.userId", commonParameters.getUserId());
			mapUpdate.put("checkuser.$.readstatus", Constants.ONE);
			resNum = newsDao.updateManyBySet(mapQuery, mapUpdate,true);
		}
		if (resNum > 0) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject findFieldNewsMessages(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Message.PARTICIPANTS+".userId", commonParameters.getUserId());
		whereMap.put(Message.TYPE, 5);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(Message.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = messageDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = messageDao.countMessage(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseObject updateBtachNewsStatus(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		List<String> newsIds = (List<String>) mapJson.get(MessageVo.NEWSID);
		String shareUserId = String.valueOf(mapJson.get("shareUser"));
		String checkUserId = String.valueOf(mapJson.get("checkUser"));
		if (StringUtil.isEmpty(newsIds) && newsIds.isEmpty()) {
			return parameterError("messageId,"+mapJson);
		}
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		long resNum = 0;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapNewsId = null;
		for (Iterator iterator = newsIds.iterator(); iterator.hasNext();) {
			String newsId = (String) iterator.next();
			mapNewsId = new HashMap<String, Object>();
			mapNewsId.put(News.ID, new ObjectId(newsId));
			listMap.add(mapNewsId);
		}
		mapQuery.put(QueryOperators.OR, listMap);
		if (!StringUtil.isEmpty(mapJson.get("shareUser")) && !StringUtil.isEmpty(shareUserId)) {
			mapQuery.put("shareuser.userId", commonParameters.getUserId());
			mapUpdate.put("shareuser.$.readstatus", Constants.ONE);
			resNum = newsDao.updateManyBySet(mapQuery, mapUpdate,true);
		}else if(!StringUtil.isEmpty(mapJson.get("checkUser")) && !StringUtil.isEmpty(checkUserId)){
			mapQuery.put("checkuser.userId", commonParameters.getUserId());
			mapUpdate.put("checkuser.$.readstatus", Constants.ONE);
			resNum = newsDao.updateManyBySet(mapQuery, mapUpdate,true);
		}
		if (resNum > 0) {
			executeSuccess(resObj);
		}
		return resObj;
	}
}
