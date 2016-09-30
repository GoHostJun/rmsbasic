package com.cdvcloud.rms.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Configuration;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.NewsVo;
import com.cdvcloud.rms.common.vo.PushVo;
import com.cdvcloud.rms.dao.ICustomPushDao;
import com.cdvcloud.rms.dao.IFieldNewsDao;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.dao.IPresentationDao;
import com.cdvcloud.rms.dao.IPushSetDao;
import com.cdvcloud.rms.dao.IPushTaskDao;
import com.cdvcloud.rms.dao.IUserDao;
import com.cdvcloud.rms.domain.Catalogue;
import com.cdvcloud.rms.domain.CustomPush;
import com.cdvcloud.rms.domain.FieldNews;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.domain.PushSet;
import com.cdvcloud.rms.domain.PushTask;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.INewsService;
import com.cdvcloud.rms.service.IPresentationService;
import com.cdvcloud.rms.service.IPushService;
import com.cdvcloud.rms.service.IPushSetService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JPushUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.MD5Util;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.mongodb.BasicDBObject;

@Service
public class NewsServiceImpl extends BasicService implements INewsService {
	private static final Logger logger = Logger.getLogger(NewsServiceImpl.class);
	@Autowired
	private INewsDao newsDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	IPresentationDao materialDao;
	@Autowired
	IPresentationService materialService;
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	ConfigurationService configurationService;
	@Autowired
	private IPushSetDao pushSetDao;
	@Autowired
	private IPushSetService pushSetService;
	@Autowired
	private ICustomPushDao customPushDao;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IFieldNewsDao fieldNewsDao;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private IWeChatService weChatService;
	@Autowired
	private IPushService pushService;
	@Autowired
	private IPushTaskDao pushTaskDao;
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public ResponseObject createNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		// 获取业务参数
		String catalogueId = docJson.getString(NewsVo.CATALOGUEID);
		Integer shareNews = Constants.ONE;
		if (docJson.containsKey(NewsVo.SHARENEWS)) {
			shareNews = docJson.getInteger(NewsVo.SHARENEWS);
		}
		List<Document> checkUser = (List<Document>) docJson.get(NewsVo.DEALJSON);
		List<Document> shareUser = (List<Document>) docJson.get(NewsVo.JOINJSON);
		// 获取文稿对象
		Document docSrc = materialDao.queryOneDocument(catalogueId);
		if (null == docSrc || docSrc.isEmpty()) {
			return super.queryError("没有获取到原文档！catalogueId+" + catalogueId);
		}
		docSrc.remove(News.ID);
		docSrc.append(News.SHARENEWS, shareNews);
		docSrc.append(News.CATALOGUEID, catalogueId);// 添加文稿id
		// 记录审核人
		List<String> listCheckUsers = new ArrayList<String>();
		// 记录通联人
		List<String> listShareUsers = new ArrayList<String>();
		// 增加通联人和审核人，改变文稿状态
		List<Document> checkUserFlag = new ArrayList<Document>();
		if (null != checkUser && checkUser.size() > 0) {
			for (Document docCheckUser : checkUser) {
				docCheckUser.append(News.STATUS, GeneralStatus.checkWait.status);
				docCheckUser.append(News.READSTATUS, Constants.ZERO);
				docCheckUser.append(News.UUTIME, DateUtil.getCurrentDateTime());
				checkUserFlag.add(docCheckUser);
				listCheckUsers.add(docCheckUser.getString(NewsVo.USERID));
			}
			docSrc.append(News.STATUS, GeneralStatus.checkWait.status);// 待审核
		} else {
			logger.warn("没有添加审核人！");
			docSrc.append(News.STATUS, GeneralStatus.checkPass.status);// 审核通过
		}
		List<Document> shareUserFlag = new ArrayList<Document>();
		if (null != shareUser && shareUser.size() > 0) {
			for (Document docShareUser : shareUser) {
				docShareUser.append(News.STATUS, GeneralStatus.waitingPush.status);
				docShareUser.append(News.READSTATUS, Constants.ZERO);
				docShareUser.append(News.UUTIME, DateUtil.getCurrentDateTime());
				shareUserFlag.add(docShareUser);
				listShareUsers.add(docShareUser.getString(NewsVo.USERID));
			}
		} else {
			logger.warn("没有添加通联人！");
		}
		docSrc.append(News.CHECKUSER, checkUserFlag);
		docSrc.append(News.SHAREUSER, shareUserFlag);
		docSrc.append(News.UUTIME, DateUtil.getCurrentDateTime());
		// 新增通联，发送消息，添加日志
		String newsId = newsDao.insertDocNews(docSrc);
		if (null != newsId) {
			// 删除文稿
			materialService.delete(catalogueId);
			executeSuccess(resObj);
			// 给移动端发送消息
			if (null != listCheckUsers && listCheckUsers.size() > 0) {
				String[] sUser = (String[]) listCheckUsers.toArray(new String[listCheckUsers.size()]);
				JPushUtil.pushObjectToPhone("您有待审核的任务", sUser);
			} else if (null != listShareUsers && listShareUsers.size() > 0) {
				String[] sUser = (String[]) listShareUsers.toArray(new String[listShareUsers.size()]);
				JPushUtil.pushObjectToPhone("您有可推送的任务", sUser);
			}
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "创建通联", "创建一条新通联《" + docSrc.get(News.TITLE) + "》"));
			LogUtil.printIntegralLog(commonParameters, "createnews", "创建一条新通联《" + docSrc.get(News.TITLE) + "》");
		}
		return resObj;
	}

	@Override
	public ResponseObject findNewsAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, News.TITLE, News.UUTIME);
		Integer status = StringUtil.obj2Integer(mapJson.get(NewsVo.STATUS));// 总体状态
		Integer checkStatus = StringUtil.obj2Integer(mapJson.get(NewsVo.CHECKSTATUS));// 个人审核状态
		Integer sendStatus = StringUtil.obj2Integer(mapJson.get(NewsVo.SENDSTATUS));// 个人通联状态
		Integer completed = StringUtil.obj2Integer(mapJson.get(NewsVo.COMPLETED));// 个人已完成状态
		String userId = commonParameters.getUserId();

		if (!StringUtil.isEmpty(completed)) {// 已完成包含审核通过和驳回，已推送
			Map<String, Object> mapCheckUserId = new HashMap<String, Object>();
			Map<String, Object> mapCheckStatus = new HashMap<String, Object>();
			Map<String, Object> mapElemMatch = new HashMap<String, Object>();
			Map<String, Object> mapCheck = new HashMap<String, Object>();
			List<Map<String, Object>> listMapCheck = new ArrayList<Map<String, Object>>();
			mapCheckUserId.put("userId", userId);
			mapCheckStatus.put("status", new BasicDBObject(QueryOperators.GTE, GeneralStatus.checkPass.status));
			listMapCheck.add(mapCheckUserId);
			listMapCheck.add(mapCheckStatus);
			mapCheck.put(QueryOperators.AND, listMapCheck);
			mapElemMatch.put(QueryOperators.ELEM_MATCH, mapCheck);
			// 已推送
			Map<String, Object> mapShareUserId = new HashMap<String, Object>();
			Map<String, Object> mapShareStatus = new HashMap<String, Object>();
			List<Map<String, Object>> listMapShare = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapElemMatch1 = new HashMap<String, Object>();
			Map<String, Object> mapShare = new HashMap<String, Object>();
			mapShareUserId.put("userId", userId);
			mapShareStatus.put("status", GeneralStatus.hasPushed.status);
			listMapShare.add(mapShareUserId);
			listMapShare.add(mapShareStatus);
			mapShare.put(QueryOperators.AND, listMapShare);
			mapElemMatch1.put(QueryOperators.ELEM_MATCH, mapShare);
			// 拼装
			Map<String, Object> whereMap1 = new HashMap<String, Object>();
			Map<String, Object> whereMap2 = new HashMap<String, Object>();
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			whereMap1.put("checkuser", mapElemMatch);
			whereMap2.put("shareuser", mapElemMatch1);
			listMap.add(whereMap1);
			listMap.add(whereMap2);
			whereMap.put(QueryOperators.OR, listMap);
			whereMap.remove(News.CUSERID);
			whereMap.remove(News.DEPARTMENTID);
		} else if (!StringUtil.isEmpty(status)) {
			whereMap.put(News.STATUS, status);
		} else if (!StringUtil.isEmpty(checkStatus)) {// 查询待审核
			Map<String, Object> mapCheckUserId = new HashMap<String, Object>();
			Map<String, Object> mapCheckStatus = new HashMap<String, Object>();
			Map<String, Object> mapElemMatch = new HashMap<String, Object>();
			Map<String, Object> mapCheck = new HashMap<String, Object>();
			List<Map<String, Object>> listMapCheck = new ArrayList<Map<String, Object>>();
			mapCheckUserId.put("userId", userId);
			mapCheckStatus.put("status", checkStatus);
			listMapCheck.add(mapCheckUserId);
			listMapCheck.add(mapCheckStatus);
			mapCheck.put(QueryOperators.AND, listMapCheck);
			mapElemMatch.put(QueryOperators.ELEM_MATCH, mapCheck);
			whereMap.put(News.CHECKUSER, mapElemMatch);
			whereMap.remove(News.CUSERID);
			whereMap.remove(News.DEPARTMENTID);
		} else if (!StringUtil.isEmpty(sendStatus)) {// 查询待推送
			Map<String, Object> mapCheck = new HashMap<String, Object>();
			Map<String, Object> mapPush = new HashMap<String, Object>();
			Map<String, Object> mapElemMatch = new HashMap<String, Object>();
			Map<String, Object> mapShare = new HashMap<String, Object>();
			Map<String, Object> mapShareUserId = new HashMap<String, Object>();
			Map<String, Object> mapShareStatus = new HashMap<String, Object>();
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listMapShare = new ArrayList<Map<String, Object>>();
			mapCheck.put(News.STATUS, GeneralStatus.checkPass.status);
			mapPush.put(News.STATUS, GeneralStatus.hasPushed.status);
			listMap.add(mapCheck);
			listMap.add(mapPush);
			mapShareUserId.put("userId", userId);
			mapShareStatus.put("status", sendStatus);
			listMapShare.add(mapShareUserId);
			listMapShare.add(mapShareStatus);
			mapShare.put(QueryOperators.AND, listMapShare);
			mapElemMatch.put(QueryOperators.ELEM_MATCH, mapShare);
			whereMap.put(News.SHAREUSER, mapElemMatch);
			whereMap.put(QueryOperators.OR, listMap);
			whereMap.remove(News.CUSERID);
			whereMap.remove(News.DEPARTMENTID);
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(News.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		// 获取通联列表，区分手机端和pc端
		List<Map<String, Object>> results = null;
		String appCode = commonParameters.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {
			Map<String, Object> mapBack = getNewsMapPhone();
			results = newsDao.find(sortMap, whereMap, mapBack, currentPage, pageNum);
		} else {
			results = newsDao.find(sortMap, whereMap, currentPage, pageNum);
		}
		// 获取总数
		long totalRecord = newsDao.countNews(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findNewsByUser(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, News.TITLE, News.UUTIME);
		String userId = commonParameters.getUserId();
		if (!StringUtil.isEmpty(whereMap.get(News.CUSERID))) {// 已完成包含审核通过和驳回，已推送
			// 审核人
			Map<String, Object> mapCheckUserId = new HashMap<String, Object>();
			mapCheckUserId.put("checkuser.userId", userId);

			// 推送人,总体状态是审核通过或者是已推送的
			Map<String, Object> mapShareUser = new HashMap<String, Object>();
			mapShareUser.put("shareuser.userId", userId);
			Map<String, Object> mapNewsCheckStatus = new HashMap<String, Object>();
			Map<String, Object> mapNewsPushStatus = new HashMap<String, Object>();
			List<Map<String, Object>> listMapStatus = new ArrayList<Map<String, Object>>();
			mapNewsCheckStatus.put(News.STATUS, GeneralStatus.checkPass.status);
			mapNewsPushStatus.put(News.STATUS, GeneralStatus.hasPushed.status);
			listMapStatus.add(mapNewsCheckStatus);
			listMapStatus.add(mapNewsPushStatus);
			mapShareUser.put(QueryOperators.OR, listMapStatus);

			// 创建人
			Map<String, Object> mapUserId = new HashMap<String, Object>();
			mapUserId.put(News.CUSERID, userId);
			// 拼装
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			listMap.add(mapCheckUserId);
			listMap.add(mapShareUser);
			listMap.add(mapUserId);
			whereMap.put(QueryOperators.OR, listMap);
			whereMap.remove(News.CUSERID);
			whereMap.remove(News.DEPARTMENTID);
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(News.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		// 获取通联列表，区分手机端和pc端
		List<Map<String, Object>> results = null;
		String appCode = commonParameters.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {
			Map<String, Object> mapBack = getNewsMapPhone();
			results = newsDao.find(sortMap, whereMap, mapBack, currentPage, pageNum);
		} else {
			results = newsDao.find(sortMap, whereMap, currentPage, pageNum);
		}
		// 获取总数
		long totalRecord = newsDao.countNews(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findNewsByShare(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, News.TITLE, News.UUTIME);
		whereMap.remove(News.CUSERID);// 移除创建人
		whereMap.remove(News.DEPARTMENTID);// 移除部门
		whereMap.put(News.SHARENEWS, Constants.ZERO);// 查询共享的通联条件
		String status = String.valueOf(mapJson.get(NewsVo.STATUS));// 通联的状态
		if (!StringUtil.isEmpty(status)) {
			whereMap.put(News.STATUS, status);
		} else {// 没有状态就查询审核通过的或已推送的
			Map<String, Object> mapCheck = new HashMap<String, Object>();
			Map<String, Object> mapPush = new HashMap<String, Object>();
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			mapCheck.put(News.STATUS, GeneralStatus.checkPass.status);
			mapPush.put(News.STATUS, GeneralStatus.hasPushed.status);
			listMap.add(mapCheck);
			listMap.add(mapPush);
			whereMap.put(QueryOperators.OR, listMap);
		}
		String areaCode = String.valueOf(mapJson.get(News.AREACODE));
		if (!StringUtil.isEmpty(areaCode)) {
			whereMap.put(News.AREACODE, areaCode);
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(News.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		// 获取通联列表，区分手机端和pc端
		List<Map<String, Object>> results = null;
		String appCode = commonParameters.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {
			Map<String, Object> mapBack = getNewsMapPhone();
			results = newsDao.find(sortMap, whereMap, mapBack, currentPage, pageNum);
		} else {
			results = newsDao.find(sortMap, whereMap, currentPage, pageNum);
		}
		// 获取总数
		long totalRecord = newsDao.countNews(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findNewsByCount(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, News.TITLE, News.UUTIME);
		whereMap.remove(News.CUSERID);// 移除创建人
		whereMap.remove(News.DEPARTMENTID);// 移除部门
		String status = String.valueOf(mapJson.get(NewsVo.STATUS));// 通联的状态
		if (!StringUtil.isEmpty(status)) {
			whereMap.put(News.STATUS, status);
		}
		String areaCode = String.valueOf(mapJson.get(News.AREACODE));
		if (!StringUtil.isEmpty(areaCode)) {
			whereMap.put(News.AREACODE, areaCode);
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(News.READETOTAL, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		// 获取通联列表
		Map<String, Object> mapBack = getNewsMapSimple();
		List<Map<String, Object>> results = newsDao.find(sortMap, whereMap, mapBack, currentPage, pageNum);
		// 获取总数
		long totalRecord = newsDao.countNews(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findNewsById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		String newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		// 获取通联
		Map<String, Object> results = null;
		String appCode = commonParameters.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {
			Map<String, Object> mapBack = getNewsMapPhone();
			results = newsDao.findOne(newsId, mapBack);
		} else {
			results = newsDao.findOne(newsId);
		}
		executeSuccess(resObj, results);
		// 统计阅读次数
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		Map<String, Object> mapInc = new HashMap<String, Object>();
		mapUpdate.put(News.READETOTAL, 1);
		mapInc.put(QueryOperators.INC, mapUpdate);
		mapQuery.put(News.ID, new ObjectId(newsId));
		newsDao.updateOne(mapQuery, mapInc);
		return resObj;
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public ResponseObject addNewsUser(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);

		// 获取业务参数
		String newsId = docJson.getString(NewsVo.CATALOGUEID);
		List<Document> shareUser = (List<Document>) docJson.get(NewsVo.JOINJSON);
		// 获取通联信息
		Document docSrc = newsDao.findOneDocument(newsId);
		if (null == docSrc || docSrc.isEmpty()) {
			return super.queryError("没有获取到原通联！catalogueId+" + newsId);
		}
		// 新增通联人添加信息
		List<Document> shareUserFlag = new ArrayList<Document>();
		if (null != shareUser && shareUser.size() > 0) {
			for (Document docShareUser : shareUser) {
				docShareUser.append(News.STATUS, GeneralStatus.waitingPush.status);
				docShareUser.append(News.READSTATUS, Constants.ZERO);
				docShareUser.append(News.UUTIME, DateUtil.getCurrentDateTime());
				shareUserFlag.add(docShareUser);
			}
		} else {
			logger.warn("没有添加通联人！");
			return resObj;
		}
		// 封装查询和更新参数
		Map<String, Object> filterMap = new HashMap<String, Object>();
		Map<String, Object> updateMap = new HashMap<String, Object>();
		Map<String, Object> shareMap = new HashMap<String, Object>();
		Map<String, Object> shareMap1 = new HashMap<String, Object>();
		Map<String, Object> uTimeMap = new HashMap<String, Object>();
		filterMap.put(News.ID, docSrc.getObjectId(News.ID));
		uTimeMap.put(News.UUTIME, DateUtil.getCurrentDateTime());
		shareMap.put(QueryOperators.EACH, shareUserFlag);
		shareMap1.put(News.SHAREUSER, shareMap);
		updateMap.put(QueryOperators.ADDTOSET, shareMap1);
		updateMap.put(QueryOperators.SET, uTimeMap);
		long num = newsDao.updateOne(filterMap, updateMap);
		if (num > 0) {
			executeSuccess(resObj);
		} else {
			logger.warn("更新操作失败！filterMap：" + filterMap + ",updateMap" + updateMap);
		}
		return resObj;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject cancelNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);

		// 获取业务参数
		String newsId = docJson.getString(NewsVo.NEWSID);
		// 获取新闻通联
		Map<String, Object> newsMap = newsDao.findOne(newsId);
		if (null == newsMap || newsMap.isEmpty()) {
			return super.queryError("没有获取到原通联！newsId+" + newsId);
		}
		// 移除多余项，更新到文稿库
		if (newsMap.containsKey(News.CATALOGUEID)) {
			String cateId = String.valueOf(newsMap.get(News.CATALOGUEID));// 文稿id
			newsMap.remove(News.COMMENTS);
			newsMap.remove(News.SHAREUSER);
			newsMap.remove(News.CHECKUSER);
			newsMap.remove(News.CATALOGUEID);
			newsMap.remove(News.ID);
			newsMap.put(News.STATUS, GeneralStatus.success.status);
			newsMap.put(News.UUTIME, DateUtil.getCurrentDateTime());
			boolean resultFlag = materialService.updateNewsToPresentation(cateId, newsMap);
			// 删除新闻通联
			if (resultFlag) {
				Map<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put(News.ID, new ObjectId(newsId));
				long resNum = newsDao.deleteOne(whereMap);
				if (resNum > 0) {
					executeSuccess(resObj);
					systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "取消通联", "取消一条新通联《" + newsMap.get(News.TITLE) + "》"));
				} else {
					logger.warn("删除操作失败！whereMap：" + whereMap);
				}
			} else {
				logger.warn("根据id更新文稿失败！cateId+" + cateId + ",newsMap:" + newsMap);
			}
		} else {
			logger.warn("没有获取到原文稿id！newsMap+" + newsMap);
		}
		return resObj;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject copyNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		String status = docJson.getString("status");

		// 获取业务参数
		String newsId = docJson.getString(NewsVo.NEWSID);
		// 获取新闻通联
		Map<String, Object> newsMap = newsDao.findOne(newsId);
		if (null == newsMap || newsMap.isEmpty()) {
			return super.queryError("没有获取到原通联！newsId+" + newsId);
		}
		// 移除多余项，插入到文稿库
		newsMap.remove(News.COMMENTS);
		newsMap.remove(News.CHECK);
		newsMap.remove(News.SHAREUSER);
		newsMap.remove(News.CHECKUSER);
		newsMap.remove(News.CATALOGUEID);
		newsMap.remove(News.ID);
		newsMap.put(News.STATUS, GeneralStatus.success.status);
		newsMap.put(News.CTIME, DateUtil.getCurrentDateTime());
		newsMap.put(News.UUTIME, DateUtil.getCurrentDateTime());
		newsMap.put(News.CUSERID, commonParameters.getUserId());
		newsMap.put(News.CUSENAME, commonParameters.getUserName());
		newsMap.put(News.DEPARTMENTID, commonParameters.getDepartmentId());
		newsMap.put(News.CONSUMERID, commonParameters.getCompanyId());
		newsMap.put(News.CONSUMERNAME, commonParameters.getConsumerName());
		String cateId = materialDao.insertMaterial(newsMap);
		if (null != cateId) {
			// 获取文稿里所有素材id，添加文稿id到素材里
			List<String> materialIds = new ArrayList<String>();
			materialIds.add(cateId);
			if (status.equals(Constants.SONE)) {
				// 复制文稿里的素材到自己的素材库里
				copyMedia(commonParameters, newsMap, materialIds);
			}
			List<String> mediaids = getMaterialIds(newsMap);
			materialService.addMediaToCataids(mediaids, materialIds);
			resObj.setCode(GeneralStatus.success.status);
			resObj.setMessage(GeneralStatus.success.enDetail);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "拷贝通联", "拷贝一条新通联《" + newsMap.get(News.TITLE) + "》"));
		} else {
			logger.warn("插入文稿失败！cateId+" + cateId + ",newsMap:" + newsMap);
		}
		return resObj;
	}

	/**
	 * 把文稿里的素材复制到当前人的素材列表里
	 * 
	 * @param commonParameters
	 * @param materialMap
	 */
	@SuppressWarnings("unchecked")
	private void copyMedia(CommonParameters commonParameters, Map<String, Object> materialMap, List<String> materialIds) {
		if (null != materialMap && !materialMap.isEmpty()) {
			List<String> old_mediaIds = new ArrayList<String>();
			List<String> new_mediaIds = new ArrayList<String>();
			if (materialMap.containsKey(Catalogue.VIDEOS)) {
				List<Map<String, Object>> videosMap = (List<Map<String, Object>>) materialMap.get(Catalogue.VIDEOS);
				setObjectIdToList(old_mediaIds, videosMap);
				List<Map<String, Object>> new_videos = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map : videosMap) {
					map.remove(Media.ID);
					map.put(Media.CTIME, DateUtil.getCurrentDateTime());
					map.put(Media.UUTIME, DateUtil.getCurrentDateTime());
					map.put(Media.CUSERID, commonParameters.getUserId());
					map.put(Media.UUSERID, commonParameters.getUserId());
					map.put(Media.CUSENAME, commonParameters.getUserName());
					map.put(Media.UUSERNAME, commonParameters.getUserName());
					// map.put(Media.DEPARTMENTID,
					// commonParameters.getDepartmentId());
					map.put(Media.CONSUMERID, commonParameters.getCompanyId());
					map.put(Media.CONSUMERNAME, commonParameters.getConsumerName());
					String id = mediaDao.insertMedia(map);
					map.put(Media.ID, id);
					new_mediaIds.add(id);
					new_videos.add(map);
				}
				Map<String, Object> set = new HashMap<String, Object>();
				set.put(News.VIDEOS, new_videos);
				materialDao.updateOne(materialIds.get(0), set, false);
			}
			if (materialMap.containsKey(Catalogue.AUDIOS)) {
				List<Map<String, Object>> audiosMap = (List<Map<String, Object>>) materialMap.get(Catalogue.AUDIOS);
				setObjectIdToList(old_mediaIds, audiosMap);
				List<Map<String, Object>> new_audios = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map : audiosMap) {
					map.remove(Media.ID);
					map.put(Media.CTIME, DateUtil.getCurrentDateTime());
					map.put(Media.UUTIME, DateUtil.getCurrentDateTime());
					map.put(Media.CUSERID, commonParameters.getUserId());
					map.put(Media.CUSENAME, commonParameters.getUserName());
					// map.put(Media.DEPARTMENTID,
					// commonParameters.getDepartmentId());
					map.put(Media.CONSUMERID, commonParameters.getCompanyId());
					map.put(Media.CONSUMERNAME, commonParameters.getConsumerName());
					String id = mediaDao.insertMedia(map);
					map.put(Media.ID, id);
					new_mediaIds.add(id);
					new_audios.add(map);
				}
				Map<String, Object> set = new HashMap<String, Object>();
				set.put(News.AUDIOS, new_audios);
				materialDao.updateOne(materialIds.get(0), set, false);
			}
			if (materialMap.containsKey(Catalogue.PICS)) {
				List<Map<String, Object>> picsMap = (List<Map<String, Object>>) materialMap.get(Catalogue.PICS);
				setObjectIdToList(old_mediaIds, picsMap);
				List<Map<String, Object>> new_pics = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map : picsMap) {
					map.remove(Media.ID);
					map.put(Media.CTIME, DateUtil.getCurrentDateTime());
					map.put(Media.UUTIME, DateUtil.getCurrentDateTime());
					map.put(Media.CUSERID, commonParameters.getUserId());
					map.put(Media.CUSENAME, commonParameters.getUserName());
					// map.put(Media.DEPARTMENTID,
					// commonParameters.getDepartmentId());
					map.put(Media.CONSUMERID, commonParameters.getCompanyId());
					map.put(Media.CONSUMERNAME, commonParameters.getConsumerName());
					String id = mediaDao.insertMedia(map);
					map.put(Media.ID, id);
					new_mediaIds.add(id);
					new_pics.add(map);
				}
				Map<String, Object> set = new HashMap<String, Object>();
				set.put(News.PICS, new_pics);
				materialDao.updateOne(materialIds.get(0), set, false);
			}
			if (materialMap.containsKey(Catalogue.DOCS)) {
				List<Map<String, Object>> docsMap = (List<Map<String, Object>>) materialMap.get(Catalogue.DOCS);
				setObjectIdToList(old_mediaIds, docsMap);
				List<Map<String, Object>> new_docs = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map : docsMap) {
					map.remove(Media.ID);
					map.put(Media.CTIME, DateUtil.getCurrentDateTime());
					map.put(Media.UUTIME, DateUtil.getCurrentDateTime());
					map.put(Media.CUSERID, commonParameters.getUserId());
					map.put(Media.CUSENAME, commonParameters.getUserName());
					// map.put(Media.DEPARTMENTID,
					// commonParameters.getDepartmentId());
					map.put(Media.CONSUMERID, commonParameters.getCompanyId());
					map.put(Media.CONSUMERNAME, commonParameters.getConsumerName());
					String id = mediaDao.insertMedia(map);
					map.put(Media.ID, id);
					new_mediaIds.add(id);
					new_docs.add(map);
				}
				Map<String, Object> set = new HashMap<String, Object>();
				set.put(News.DOCS, new_docs);
				materialDao.updateOne(materialIds.get(0), set, false);
			}
			materialService.addMediaToCataids(new_mediaIds, materialIds);
			materialService.delMediaToCataids(old_mediaIds, materialIds);
		} else {
			logger.warn("传入map对象为空！materialMap=" + materialMap);
		}
	}

	/**
	 * 获取文稿中所有的素材id
	 */
	@SuppressWarnings("unchecked")
	private List<String> getMaterialIds(Map<String, Object> materialMap) {
		List<String> materialIds = new ArrayList<String>();
		if (null != materialMap && !materialMap.isEmpty()) {
			if (materialMap.containsKey(Catalogue.VIDEOS)) {
				List<Map<String, Object>> videosMap = (List<Map<String, Object>>) materialMap.get(Catalogue.VIDEOS);
				setObjectIdToList(materialIds, videosMap);
			}
			if (materialMap.containsKey(Catalogue.AUDIOS)) {
				List<Map<String, Object>> audiosMap = (List<Map<String, Object>>) materialMap.get(Catalogue.AUDIOS);
				setObjectIdToList(materialIds, audiosMap);
			}
			if (materialMap.containsKey(Catalogue.PICS)) {
				List<Map<String, Object>> picsMap = (List<Map<String, Object>>) materialMap.get(Catalogue.PICS);
				setObjectIdToList(materialIds, picsMap);
			}
			if (materialMap.containsKey(Catalogue.DOCS)) {
				List<Map<String, Object>> docsMap = (List<Map<String, Object>>) materialMap.get(Catalogue.DOCS);
				setObjectIdToList(materialIds, docsMap);
			}
		} else {
			logger.warn("传入map对象为空！materialMap=" + materialMap);
		}
		return materialIds;
	}

	/**
	 * 将素材id放入list集合
	 */
	private void setObjectIdToList(List<String> materialIds, List<Map<String, Object>> objsMap) {
		if (null == objsMap || objsMap.isEmpty()) {
			logger.warn("素材集合为空！objsMap=" + objsMap);
			return;
		}
		for (Map<String, Object> objMap : objsMap) {
			if (objMap.containsKey(Media.ID)) {
				materialIds.add(String.valueOf(objMap.get(Media.ID)));
			} else {
				logger.warn("这个素材没有素材id！objMap=" + objMap);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject updateNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		String newsTitle = null;
		if (mapJson.containsKey(NewsVo.NEWSTITLE) && null != mapJson.get(NewsVo.NEWSTITLE)) {
			newsTitle = String.valueOf(mapJson.get(NewsVo.NEWSTITLE));
		} else {
			return parameterError("newsTitle," + strJson);
		}
		Map<String, Object> template = null;
		if (mapJson.containsKey(NewsVo.TEMPLATE) && null != mapJson.get(NewsVo.TEMPLATE)) {
			template = (Map<String, Object>) mapJson.get(NewsVo.TEMPLATE);
		} else {
			return parameterError("template," + strJson);
		}
		// 更新数据
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(News.ID, new ObjectId(newsId));
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put(News.TITLE, newsTitle);
		updateMap.put(News.TEMPLATE, template);
		updateMap.put(News.UUTIME, DateUtil.getCurrentDateTime());
		long resNum = newsDao.updateManyBySet(whereMap, updateMap, true);
		if (resNum > 0) {
			executeSuccess(resObj);
		} else {
			logger.warn("更新失败！updateMap=" + updateMap);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "更新通联", "更新一条新通联《" + newsTitle + "》"));
		}
		return resObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject addNewsMaterial(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		Map<String, Object> newsMap = newsDao.findOne(newsId); // 获取通联信息
		if (null == newsMap || newsMap.isEmpty()) {
			return queryError("没有获取到原通联！newsId+" + newsId);
		}
		List<String> mediaids = new ArrayList<String>();// 存放素材id
		Map<String, Object> whereMap = new HashMap<String, Object>();// 查询条件
		whereMap.put(News.ID, new ObjectId(newsId));

		// 执行视频的添加
		List<String> videoIds = null;
		List<Map<String, Object>> videos = null;
		if (mapJson.containsKey(NewsVo.VIDEOIDS) && null != mapJson.get(NewsVo.VIDEOIDS)) {
			videoIds = (List<String>) mapJson.get(NewsVo.VIDEOIDS);
			videos = getMaterials(videoIds);
			addMediaToNews(videos, whereMap, News.VIDEOS);
			mediaids.addAll(videoIds);
		}
		// 执行音频的添加
		List<String> audioIds = null;
		List<Map<String, Object>> audios = null;
		if (mapJson.containsKey(NewsVo.AUDIOIDS) && null != mapJson.get(NewsVo.AUDIOIDS)) {
			audioIds = (List<String>) mapJson.get(NewsVo.AUDIOIDS);
			audios = getMaterials(audioIds);
			addMediaToNews(audios, whereMap, News.AUDIOS);
			mediaids.addAll(audioIds);
		}
		// 执行图片的添加
		List<String> picIds = null;
		List<Map<String, Object>> pics = null;
		if (mapJson.containsKey(NewsVo.PICIDS) && null != mapJson.get(NewsVo.PICIDS)) {
			picIds = (List<String>) mapJson.get(NewsVo.PICIDS);
			pics = getMaterials(picIds);
			addMediaToNews(pics, whereMap, News.PICS);
			mediaids.addAll(picIds);
		}
		// 执行文档的添加
		List<String> docIds = null;
		List<Map<String, Object>> docs = null;
		if (mapJson.containsKey(NewsVo.DOCIDS) && null != mapJson.get(NewsVo.DOCIDS)) {
			docIds = (List<String>) mapJson.get(NewsVo.DOCIDS);
			docs = getMaterials(docIds);
			addMediaToNews(docs, whereMap, News.DOCS);
			mediaids.addAll(docIds);
		}
		// 添加文稿id到素材里
		if (mediaids.size() > 0) {
			logger.debug("更新集合中素材id！materialIds=" + mediaids);
			List<String> materialIds = new ArrayList<String>();
			if (newsMap.containsKey(News.CATALOGUEID)) {
				materialIds.add(String.valueOf(newsMap.get(News.CATALOGUEID)));
			} else {
				logger.warn("集合中没有文稿id！newsMap=" + newsMap);
				materialIds.add(String.valueOf(newsMap.get(News.ID)));
			}
			materialService.addMediaToCataids(mediaids, materialIds);
			executeSuccess(resObj);
		} else {
			logger.warn("集合中没有素材id！materialIds=" + mediaids);
		}
		return resObj;
	}

	/**
	 * 根据id集合获取素材集合
	 * 
	 * @param materialIds
	 * @return
	 */
	private List<Map<String, Object>> getMaterials(List<String> materialIds) {
		if (null != materialIds && materialIds.size() > 0) {
			List<Map<String, Object>> materials = new ArrayList<Map<String, Object>>();
			for (String materialId : materialIds) {
				Map<String, Object> mapMaterial = mediaDao.queryOne(materialId);
				materials.add(mapMaterial);
			}
			return materials;
		}
		return null;
	}

	/**
	 * 添加素材到通联中
	 * 
	 * @param medias
	 *            素材集合
	 * @param whereMap
	 *            查询条件
	 * @param keyFlag
	 *            插入字段
	 * @return
	 */
	private long addMediaToNews(List<Map<String, Object>> medias, Map<String, Object> whereMap, String keyFlag) {
		if (null == medias || medias.size() < Constants.ONE || null == whereMap || whereMap.isEmpty()) {
			return 0;
		}
		Map<String, Object> updateMapMedia = new HashMap<String, Object>();// 更新map
		Map<String, Object> optMap1 = new HashMap<String, Object>();// 操作符map
		Map<String, Object> optMap2 = new HashMap<String, Object>();// 操作符map
		optMap2.put(QueryOperators.EACH, medias);
		updateMapMedia.put(keyFlag, optMap2);
		optMap1.put(QueryOperators.ADDTOSET, updateMapMedia);
		long resNum = newsDao.updateMany(whereMap, optMap1);
		return resNum;
	}

	/**
	 * 移除通联中的素材
	 * 
	 * @param medias
	 *            素材集合
	 * @param mediaIds
	 *            移除素材id集合
	 * @param whereMap
	 *            查询条件
	 * @param keyFlag
	 *            操作字段
	 * @return
	 */
	private long deleteMediaFromNews(List<Map<String, Object>> medias, List<String> mediaIds, Map<String, Object> whereMap, String keyFlag) {
		if (null == medias || medias.size() < Constants.ONE || null == mediaIds || mediaIds.size() < Constants.ONE || null == whereMap
				|| whereMap.isEmpty()) {
			return 0;
		}
		for (String strMediaId : mediaIds) {
			for (int i = 0; i < medias.size(); i++) {
				Map<String, Object> mediaMap = medias.get(i);
				if (!mediaMap.isEmpty() && mediaMap.containsKey(Media.ID)) {
					String mediaId = String.valueOf(mediaMap.get(Media.ID));
					if (strMediaId.equals(mediaId)) {
						medias.remove(i);
					} else {
						logger.warn("集合中没有素材id！mediaMap=" + mediaMap);
					}
				} else {
					logger.warn("集合中没有素材！mediaMap=" + mediaMap);
				}
			}
		}

		Map<String, Object> updateMapVideo = new HashMap<String, Object>();// 更新map
		Map<String, Object> optMap1 = new HashMap<String, Object>();// 操作符map
		updateMapVideo.put(keyFlag, medias);
		optMap1.put(QueryOperators.SET, updateMapVideo);
		long resNum = newsDao.updateMany(whereMap, optMap1);
		return resNum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject deleteNewsMaterial(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		Map<String, Object> newsMap = newsDao.findOne(newsId); // 获取通联信息
		if (null == newsMap || newsMap.isEmpty()) {
			return queryError("没有获取到原通联！newsId+" + newsId);
		}
		List<String> mediaids = new ArrayList<String>();// 存放素材id
		Map<String, Object> whereMap = new HashMap<String, Object>();// 查询条件
		whereMap.put(News.ID, new ObjectId(newsId));

		// 执行视频的删除
		List<String> videoIds = null;
		List<Map<String, Object>> videos = null;
		if (mapJson.containsKey(NewsVo.VIDEOIDS) && null != mapJson.get(NewsVo.VIDEOIDS)) {
			videoIds = (List<String>) mapJson.get(NewsVo.VIDEOIDS);
			videos = (List<Map<String, Object>>) newsMap.get(News.VIDEOS);
			deleteMediaFromNews(videos, videoIds, whereMap, News.VIDEOS);
			mediaids.addAll(videoIds);
		}
		// 执行音频的删除
		List<String> audioIds = null;
		List<Map<String, Object>> audios = null;
		if (mapJson.containsKey(NewsVo.AUDIOIDS) && null != mapJson.get(NewsVo.AUDIOIDS)) {
			audioIds = (List<String>) mapJson.get(NewsVo.AUDIOIDS);
			audios = (List<Map<String, Object>>) newsMap.get(News.AUDIOS);
			deleteMediaFromNews(audios, audioIds, whereMap, News.AUDIOS);
			mediaids.addAll(audioIds);
		}
		// 执行图片的删除
		List<String> picIds = null;
		List<Map<String, Object>> pics = null;
		if (mapJson.containsKey(NewsVo.PICIDS) && null != mapJson.get(NewsVo.PICIDS)) {
			picIds = (List<String>) mapJson.get(NewsVo.PICIDS);
			pics = (List<Map<String, Object>>) newsMap.get(News.PICS);
			deleteMediaFromNews(pics, picIds, whereMap, News.PICS);
			mediaids.addAll(picIds);
		}
		// 执行文档的删除
		List<String> docIds = null;
		List<Map<String, Object>> docs = null;
		if (mapJson.containsKey(NewsVo.DOCIDS) && null != mapJson.get(NewsVo.DOCIDS)) {
			docIds = (List<String>) mapJson.get(NewsVo.DOCIDS);
			docs = (List<Map<String, Object>>) newsMap.get(News.DOCS);
			deleteMediaFromNews(docs, docIds, whereMap, News.DOCS);
			mediaids.addAll(docIds);
		}
		// 删除素材里的文稿id
		if (mediaids.size() > 0) {
			logger.debug("集合中素材id！materialIds=" + mediaids);
			List<String> materialIds = new ArrayList<String>();
			if (newsMap.containsKey(News.CATALOGUEID)) {
				materialIds.add(String.valueOf(newsMap.get(News.CATALOGUEID)));
			} else {
				logger.warn("集合中没有文稿id！newsMap=" + newsMap);
				materialIds.add(String.valueOf(newsMap.get(News.ID)));
			}
			materialService.delMediaToCataids(mediaids, materialIds);
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "删除通联", "删除一条新通联《" + newsMap.get(News.TITLE) + "》"));
		} else {
			logger.warn("集合中没有素材id！materialIds=" + mediaids);
		}
		return resObj;
	}

	@Override
	public ResponseObject countNewsByUser(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");

		String userId = commonParameters.getUserId();// 获取用户id
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> whereMap1 = new HashMap<String, Object>();
		Map<String, Object> whereMap2 = new HashMap<String, Object>();
		Map<String, Object> whereMap3 = new HashMap<String, Object>();
		Map<String, Object> mapCheck = new HashMap<String, Object>();
		Map<String, Object> mapPush = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listMapShare = new ArrayList<Map<String, Object>>();
		mapCheck.put(News.STATUS, GeneralStatus.checkPass.status);
		mapPush.put(News.STATUS, GeneralStatus.hasPushed.status);
		listMapShare.add(mapCheck);
		listMapShare.add(mapPush);
		whereMap1.put("checkuser.userId", userId);
		whereMap2.put("shareuser.userId", userId);
		whereMap2.put(QueryOperators.OR, listMapShare);
		whereMap3.put(News.CUSERID, userId);
		listMap.add(whereMap1);
		listMap.add(whereMap2);
		listMap.add(whereMap3);
		whereMap.put(QueryOperators.OR, listMap);
		long newsTotal = newsDao.countNews(whereMap);
		Map<String, Object> mapRes = new HashMap<String, Object>();
		mapRes.put("count", newsTotal);
		executeSuccess(resObj, mapRes);
		return resObj;
	}

	/** 获取待办通联信息（待推送或待审核） */
	@Override
	public ResponseObject unDealNewsByUser(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		String userId = commonParameters.getUserId();// 获取用户id
		String type = String.valueOf(mapJson.get("type"));// 获取查询类型
		// 组合条件
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (Constants.SZERO.equals(type)) {// 待审核
			// 拼装待审核
			Map<String, Object> mapCheckUserId = new HashMap<String, Object>();
			Map<String, Object> mapCheckStatus = new HashMap<String, Object>();
			Map<String, Object> mapReadCheckStatus = new HashMap<String, Object>();
			Map<String, Object> mapElemMatchCheck = new HashMap<String, Object>();
			Map<String, Object> mapCheck = new HashMap<String, Object>();
			List<Map<String, Object>> listMapCheck = new ArrayList<Map<String, Object>>();
			mapCheckUserId.put("userId", userId);
			mapCheckStatus.put("status", GeneralStatus.checkWait.status);
			mapReadCheckStatus.put("readstatus", Constants.ZERO);
			listMapCheck.add(mapCheckUserId);
			listMapCheck.add(mapCheckStatus);
			listMapCheck.add(mapReadCheckStatus);
			mapCheck.put(QueryOperators.AND, listMapCheck);
			mapElemMatchCheck.put(QueryOperators.ELEM_MATCH, mapCheck);
			whereMap.put("checkuser", mapElemMatchCheck);
			whereMap.put(News.STATUS, GeneralStatus.checkWait.status);
		} else {
			// 拼装待推送，待推送时通联状态需要是审核通过或已推送
			Map<String, Object> mapShareUserId = new HashMap<String, Object>();
			Map<String, Object> mapShareStatus = new HashMap<String, Object>();
			Map<String, Object> mapReadShareStatus = new HashMap<String, Object>();
			Map<String, Object> mapNewsCheckStatus = new HashMap<String, Object>();
			Map<String, Object> mapNewsPushStatus = new HashMap<String, Object>();
			List<Map<String, Object>> listMapShare = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapElemMatchShare = new HashMap<String, Object>();
			Map<String, Object> mapShare = new HashMap<String, Object>();
			List<Map<String, Object>> listMapStatus = new ArrayList<Map<String, Object>>();
			mapShareUserId.put("userId", userId);
			mapShareStatus.put("status", GeneralStatus.waitingPush.status);
			mapReadShareStatus.put("readstatus", Constants.ZERO);
			listMapShare.add(mapShareUserId);
			listMapShare.add(mapShareStatus);
			listMapShare.add(mapReadShareStatus);
			mapShare.put(QueryOperators.AND, listMapShare);
			mapElemMatchShare.put(QueryOperators.ELEM_MATCH, mapShare);
			mapNewsCheckStatus.put(News.STATUS, GeneralStatus.checkPass.status);
			mapNewsPushStatus.put(News.STATUS, GeneralStatus.hasPushed.status);
			listMapStatus.add(mapNewsCheckStatus);
			listMapStatus.add(mapNewsPushStatus);
			whereMap.put("shareuser", mapElemMatchShare);
			whereMap.put(QueryOperators.OR, listMapStatus);
		}
		long newsTotal = newsDao.countNews(whereMap);
		Map<String, Object> mapRes = new HashMap<String, Object>();
		mapRes.put("dealState", newsTotal);
		executeSuccess(resObj, mapRes);
		return resObj;
	}

	/** 获取待办通联信息（待推送和待审核） */
	public ResponseObject unDealNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");

		String userId = commonParameters.getUserId();// 获取用户id
		// 拼装待审核
		Map<String, Object> mapCheckUserId = new HashMap<String, Object>();
		Map<String, Object> mapCheckStatus = new HashMap<String, Object>();
		Map<String, Object> mapElemMatchCheck = new HashMap<String, Object>();
		Map<String, Object> mapCheck = new HashMap<String, Object>();
		List<Map<String, Object>> listMapCheck = new ArrayList<Map<String, Object>>();
		mapCheckUserId.put("userId", userId);
		mapCheckStatus.put("status", GeneralStatus.checkWait.status);
		listMapCheck.add(mapCheckUserId);
		listMapCheck.add(mapCheckStatus);
		mapCheck.put(QueryOperators.AND, listMapCheck);
		mapElemMatchCheck.put(QueryOperators.ELEM_MATCH, mapCheck);
		// 拼装待推送，待推送时通联状态需要是审核通过或已推送
		Map<String, Object> mapShareUserId = new HashMap<String, Object>();
		Map<String, Object> mapShareStatus = new HashMap<String, Object>();
		Map<String, Object> mapNewsCheckStatus = new HashMap<String, Object>();
		Map<String, Object> mapNewsPushStatus = new HashMap<String, Object>();
		List<Map<String, Object>> listMapShare = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapElemMatchShare = new HashMap<String, Object>();
		Map<String, Object> mapShare = new HashMap<String, Object>();
		List<Map<String, Object>> listMapStatus = new ArrayList<Map<String, Object>>();
		mapShareUserId.put("userId", userId);
		mapShareStatus.put("status", GeneralStatus.waitingPush.status);
		listMapShare.add(mapShareUserId);
		listMapShare.add(mapShareStatus);
		mapShare.put(QueryOperators.AND, listMapShare);
		mapElemMatchShare.put(QueryOperators.ELEM_MATCH, mapShare);

		mapNewsCheckStatus.put(News.STATUS, GeneralStatus.checkPass.status);
		mapNewsPushStatus.put(News.STATUS, GeneralStatus.hasPushed.status);
		listMapStatus.add(mapNewsCheckStatus);
		listMapStatus.add(mapNewsPushStatus);

		// 组合条件
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> whereMap1 = new HashMap<String, Object>();
		Map<String, Object> whereMap2 = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		whereMap1.put("checkuser", mapElemMatchCheck);
		whereMap2.put("shareuser", mapElemMatchShare);
		whereMap2.put(QueryOperators.OR, listMapStatus);
		listMap.add(whereMap1);
		listMap.add(whereMap2);
		whereMap.put(QueryOperators.OR, listMap);
		long newsTotal = newsDao.countNews(whereMap);
		Map<String, Object> mapRes = new HashMap<String, Object>();
		mapRes.put("dealState", newsTotal);
		executeSuccess(resObj, mapRes);
		return resObj;
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public ResponseObject createService(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);

		// 获取业务参数
		String catalogueId = docJson.getString(NewsVo.CATALOGUEID);
		List<Document> checkUser = (List<Document>) docJson.get(NewsVo.DEALJSON);
		List<Document> shareUser = (List<Document>) docJson.get(NewsVo.JOINJSON);
		// 获取文稿对象
		Document docSrc = materialDao.queryOneDocument(catalogueId);
		if (null == docSrc || docSrc.isEmpty()) {
			return super.queryError("没有获取到原文档！catalogueId+" + catalogueId);
		}
		docSrc.remove(News.ID);
		docSrc.append(News.CATALOGUEID, catalogueId);// 添加文稿id
		// 增加通联人和审核人，改变文稿状态
		List<Document> checkUserFlag = new ArrayList<Document>();
		if (null != checkUser && checkUser.size() > 0) {
			for (Document docCheckUser : checkUser) {
				docCheckUser.append(News.STATUS, GeneralStatus.checkWait.status);
				docCheckUser.append(News.READSTATUS, Constants.ZERO);
				docCheckUser.append(News.UUTIME, DateUtil.getCurrentDateTime());
				checkUserFlag.add(docCheckUser);
			}
			docSrc.append(News.STATUS, GeneralStatus.checkWait.status);// 待审核
		} else {
			logger.warn("没有添加审核人！");
			docSrc.append(News.STATUS, GeneralStatus.checkPass.status);// 审核通过
		}
		List<Document> shareUserFlag = new ArrayList<Document>();
		if (null != shareUser && shareUser.size() > 0) {
			for (Document docShareUser : shareUser) {
				docShareUser.append(News.STATUS, GeneralStatus.waitingPush.status);
				docShareUser.append(News.READSTATUS, Constants.ZERO);
				docShareUser.append(News.UUTIME, DateUtil.getCurrentDateTime());
				shareUserFlag.add(docShareUser);
			}
		} else {
			logger.warn("没有添加通联人！");
		}
		docSrc.append(News.CHECKUSER, checkUserFlag);
		docSrc.append(News.SHAREUSER, shareUserFlag);
		docSrc.append(News.UUTIME, DateUtil.getCurrentDateTime());
		docSrc.append(News.OTHERMSG, docJson.getString(News.OTHERMSG));
		// 新增通联，发送消息，添加日志
		String newsId = newsDao.insertDocNews(docSrc);
		if (null != newsId) {
			// 删除文稿
			// materialService.delete(catalogueId);
			executeSuccess(resObj);
		}
		return resObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject updateNewsMaterial(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		List<String> extend = null;
		if (mapJson.containsKey("extend") && null != mapJson.get("extend")) {
			extend = (List<String>) mapJson.get("extend");
		} else {
			return parameterError("extend," + strJson);
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> updateMap = new HashMap<String, Object>();
		whereMap.put(News.ID, new ObjectId(newsId));
		// 获取video
		String videoId = null;
		String audioId = null;
		if (mapJson.containsKey("videoId") && null != mapJson.get("videoId")) {
			videoId = String.valueOf(mapJson.get("videoId"));
			whereMap.put("videos._id", videoId);
			updateMap.put("videos.$.extend", extend);
		} else if (mapJson.containsKey("audioId") && null != mapJson.get("audioId")) {
			audioId = String.valueOf(mapJson.get("audioId"));
			whereMap.put("audios._id", audioId);
			updateMap.put("audios.$.extend", extend);
		} else {
			return queryError("没有获取到素材id！mapJson+" + strJson);
		}
		// 更新数据
		updateMap.put(News.UUTIME, DateUtil.getCurrentDateTime());
		long resNum = newsDao.updateManyBySet(whereMap, updateMap, true);
		if (resNum > 0) {
			executeSuccess(resObj);
		} else {
			logger.warn("更新失败！updateMap=" + updateMap);
		}
		return resObj;
	}

	/**
	 * 通联推送newphere
	 */
	@Override
	public ResponseObject sendNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		// 获取通联并执行推送
		Map<String, Object> newsMap = newsDao.findOne(newsId); // 获取通联信息
		Map<String, Object> pushWhere = new HashMap<String, Object>();
		pushWhere.put(PushSet.UNIQUENAME, "newsphere");
		Map<String, Object> mapPushSet = pushSetDao.findOne(pushWhere);// 获取推送信息
		if (null == mapPushSet) {
			logger.warn("推送失败：未找到推送关键字newsphere的对象");
			return parameterError("newsphere,newsphere");
		}
		newsMap.put(PushSet.PUSHURL, mapPushSet.get(PushSet.PUSHURL));

		boolean resFlag = sendToNewsphere(newsMap, commonParameters);
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdateOpt = new HashMap<String, Object>();
		mapQuery.put(News.ID, new ObjectId(newsId));
		mapQuery.put("shareuser.userId", commonParameters.getUserId());
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		mapUpdate.put("shareuser.$.uutime", DateUtil.getCurrentDateTime());
		mapUpdate.put("shareuser.$.status", GeneralStatus.hasPushed.status);
		mapUpdate.put(News.STATUS, GeneralStatus.hasPushed.status);
		if (resFlag) {
			mapUpdate.put("shareuser.$.inewstatus", GeneralStatus.success.status);
			Map<String, Object> mapPush = new HashMap<String, Object>();
			mapPush.put(News.PUSHTOTAL, 1);
			mapUpdateOpt.put(QueryOperators.INC, mapPush);
		} else {
			mapUpdate.put("shareuser.$.inewstatus", GeneralStatus.failure.status);
		}
		mapUpdate.put(News.UUTIME, DateUtil.getCurrentDateTime());
		mapUpdateOpt.put(QueryOperators.SET, mapUpdate);
		long resNum = newsDao.updateMany(mapQuery, mapUpdateOpt, true);
		if (0 < resNum && resFlag) {
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "推送通联", "推送一条新通联到newsphere《" + newsMap.get(News.TITLE) + "》"));
		}
		return resObj;
	}

	/**
	 * 推送newsphere
	 * 
	 * @param newsMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean sendToNewsphere(Map<String, Object> newsMap, CommonParameters commonParameters) {
		if (null == newsMap || newsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", "news");
		paramMap.put("subscriptionType", "云通联");
		paramMap.put("source", "云通联");
		paramMap.put("userId", commonParameters.getUserId());
		paramMap.put("userName", commonParameters.getUserName());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
		// 添加视频
		List<Map<String, Object>> videos = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.VIDEOS) && null != pushMap.get(News.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(News.VIDEOS);
			Map<String, Object> videosMap = null;
			for (Map<String, Object> mapV : videosFlag) {
				videosMap = new HashMap<String, Object>();
				String name = mapV.get("name") + "." + mapV.get("fmt");
				videosMap.put("name", name);
				videosMap.put("url", mapV.get("wanurl"));
				videos.add(videosMap);
			}
		}
		// 添加音频
		if (pushMap.containsKey(News.AUDIOS) && null != pushMap.get(News.AUDIOS)) {
			List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) pushMap.get(News.AUDIOS);
			Map<String, Object> audiosMap = null;
			for (Map<String, Object> mapA : audiosFlag) {
				audiosMap = new HashMap<String, Object>();
				String name = mapA.get("name") + "." + mapA.get("fmt");
				audiosMap.put("name", name);
				audiosMap.put("url", mapA.get("wanurl"));
				videos.add(audiosMap);
			}
		}
		dataMap.put("videos", videos);
		// 添加图片
		List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.PICS) && null != pushMap.get(News.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(News.PICS);
			Map<String, Object> imagesMap = null;
			for (Map<String, Object> mapP : imagesFlag) {
				imagesMap = new HashMap<String, Object>();
				String name = mapP.get("name") + "." + mapP.get("fmt");
				imagesMap.put("name", name);
				imagesMap.put("url", mapP.get("wanurl"));
				images.add(imagesMap);
			}
		}
		// 获取并赋值文稿里的字段
		Map<String, Object> mapTemplate = null;
		if (newsMap.containsKey(News.TEMPLATE) && null != newsMap.get(News.TEMPLATE)) {
			mapTemplate = (Map<String, Object>) newsMap.get(News.TEMPLATE);
			if (null != mapTemplate.get("reporter")) {
				dataMap.put("author", mapTemplate.get("reporter"));// 记者
			}
			String source_site = "云通联汇聚_";
			if (null != mapTemplate.get("source")) {
				source_site = source_site + mapTemplate.get("source");
			}
			dataMap.put("source_site", source_site);// 分类_来源(分类和来源两个字段以下划线作为连接符拼凑一起赋给source_site字段即可)
			if (null != mapTemplate.get("program")) {
				dataMap.put("columnName", mapTemplate.get("program"));// 栏目
			}
			if (null != mapTemplate.get("cameraMan")) {
				dataMap.put("cameramen", mapTemplate.get("cameraMan"));// 摄像
			}
			if (null != mapTemplate.get("docsContentHTML")) {
				dataMap.put("content", mapTemplate.get("docsContentHTML"));// 正文
			}
			if (null != mapTemplate.get("repProviders")) {
				dataMap.put("rep_providers", mapTemplate.get("repProviders"));// 通讯员
			}
			if (null != mapTemplate.get("subtitleWords")) {
				dataMap.put("subtitlewords", mapTemplate.get("subtitleWords"));// 字幕
			}
			if (null != mapTemplate.get("tvStationName")) {
				dataMap.put("prostation", mapTemplate.get("tvStationName"));// 供片台
			}
			if (null != mapTemplate.get("keyWords")) {
				dataMap.put("keywords", mapTemplate.get("keyWords"));// 关键词
			}
			if (null != mapTemplate.get("presenter")) {
				dataMap.put("hoster", mapTemplate.get("presenter"));// 主持人
			}
			if (null != mapTemplate.get("dubbingMan")) {
				dataMap.put("dubbing", mapTemplate.get("dubbingMan"));// 配音
			}
			if (null != mapTemplate.get("editor")) {
				dataMap.put("filmcutters", mapTemplate.get("editor"));// 编辑
			}
		}
		dataMap.put("images", images);
		dataMap.put("type", "news");
		dataMap.put("source_id", newsMap.get(News.ID));
		dataMap.put("title", newsMap.get(News.TITLE));

		paramMap.put("data", dataMap);
		String paramJson = JsonUtil.writeMap2JSON(paramMap);
		// String paramUrl = configurationService.getNewsphereUrl();
		String paramUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
		logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
		String result = HttpUtil.doPost(paramUrl, paramJson);
		if (null != result) {
			Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
			String status = String.valueOf(mapRes.get("status"));
			String message = String.valueOf(mapRes.get("message"));
			//调用微信
			newsMap.put(News.CUSERID, commonParameters.getUserId());
			if ("200".equals(status) && "ok".equalsIgnoreCase(message)) {
				weChatService.pushWx(newsMap, "成功", "push");
				return true;
			} else {
				weChatService.pushWx(newsMap, "失败", "push");
				logger.error("推送失败：status=" + status + ",message=" + message);
			}
		}
		return false;
	}
	
	/**
	 * 推送local
	 * 
	 * @param newsMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean sendToLocal(Map<String, Object> docsMap, CommonParameters commonParameters) {
		if (null == docsMap || docsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("src", "ytl");
		Map<String, Object> userMap = userDao.findOne(String.valueOf(commonParameters.getUserId()));
		String realName = String.valueOf(userMap.get(User.REALNAME));
		paramMap.put("username", realName);
		// 添加视频
		List<String> videos = new ArrayList<String>();
		List<String> audios = new ArrayList<String>();
		Map<String, Object> pushMap = pushSetService.getPushAddress(docsMap);
		if (pushMap.containsKey(Catalogue.VIDEOS) && null != pushMap.get(Catalogue.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(Catalogue.VIDEOS);
			for (Map<String, Object> mapV : videosFlag) {
				videos.add(String.valueOf(mapV.get("wanurl")));
			}
		}
		// 添加音频
		if (pushMap.containsKey(Catalogue.AUDIOS) && null != pushMap.get(Catalogue.AUDIOS)) {
			List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) pushMap.get(Catalogue.AUDIOS);
			for (Map<String, Object> mapA : audiosFlag) {
				audios.add(String.valueOf(mapA.get("wanurl")));
			}
		}
		paramMap.put("videos", videos);
		paramMap.put("audios", audios);
		// 添加图片
		List<String> images = new ArrayList<String>();
		if (pushMap.containsKey(Catalogue.PICS) && null != pushMap.get(Catalogue.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(Catalogue.PICS);
			for (Map<String, Object> mapP : imagesFlag) {
				images.add(String.valueOf(mapP.get("wanurl")));
			}
		}
		paramMap.put("pictures", images);
		// 获取并赋值文稿里的字段
		Map<String, Object> mapTemplate = null;
		if (docsMap.containsKey(Catalogue.TEMPLATE) && null != docsMap.get(Catalogue.TEMPLATE)) {
			mapTemplate = (Map<String, Object>) docsMap.get(Catalogue.TEMPLATE);
			if (null != mapTemplate.get("docsContentHTML")) {
				paramMap.put("text", String.valueOf(mapTemplate.get("content")));// 正文
			}

		}
		paramMap.put("title", String.valueOf(docsMap.get(Catalogue.TITLE)));
		String paramJson = JsonUtil.writeMap2JSON(paramMap);
		// String paramUrl = configurationService.getDocsLocalUrl();
		String paramUrl = String.valueOf(docsMap.get(PushSet.PUSHURL));
		logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
		String result = HttpUtil.doPost(paramUrl, paramJson);
		if (null != result) {
			Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
			String status = String.valueOf(mapRes.get("code"));
			String message = String.valueOf(mapRes.get("message"));
			if ("0".equals(status) && "ok".equalsIgnoreCase(message)) {
				return true;
			} else {
				logger.error("推送失败：status=" + status + ",message=" + message);
			}
		}
		return false;
	}

	@Override
	public ResponseObject sendNewsToNetStation(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdateOpt = new HashMap<String, Object>();
		mapQuery.put(News.ID, new ObjectId(newsId));
		mapQuery.put("shareuser.userId", commonParameters.getUserId());
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		mapUpdate.put("shareuser.$.status", GeneralStatus.hasPushed.status);
		mapUpdate.put("shareuser.$.netstatus", GeneralStatus.success.status);
		mapUpdate.put("shareuser.$.uutime", DateUtil.getCurrentDateTime());
		mapUpdate.put(News.STATUS, GeneralStatus.hasPushed.status);
		mapUpdate.put(News.UUTIME, DateUtil.getCurrentDateTime());
		Map<String, Object> mapPush = new HashMap<String, Object>();
		mapPush.put(News.PUSHTOTAL, 1);
		mapUpdateOpt.put(QueryOperators.INC, mapPush);
		mapUpdateOpt.put(QueryOperators.SET, mapUpdate);
		long resNum = newsDao.updateMany(mapQuery, mapUpdate, true);
		if (0 < resNum) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean sendConverge(Map<String, Object> newsMap, CommonParameters commonParameters) {
		if (null == newsMap || newsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> converge = new HashMap<String, Object>();
		List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> news = new HashMap<String, Object>();
		Map<String, Object> template = (Map<String, Object>) newsMap.get(News.TEMPLATE);

		news.put("WebFrom", "");
		news.put("Hit", 0);
		news.put("Language", "中文");
		news.put("Url", "http://news.cdvcloud.com");
		news.put("WebBold", false);
		news.put("TitleYxm", "");
		news.put("Title", newsMap.get(News.TITLE));

		news.put("ChannelID", UUID.randomUUID().toString());
		news.put("Character", "中性");
		news.put("PublishTime", newsMap.get(News.UUTIME));
		news.put("Summary", template.get("content"));
		news.put("Content", template.get("docsContentHTML"));// html标签
		news.put("Author", template.get("createor"));
		news.put("NewsID", newsMap.get(Media.ID));
		news.put("Reply", 0);
		news.put("ChannelName", "云通联");
		news.put("Quality", "正常");
		news.put("CollectTime", DateUtil.getCurrentDateTime());
		
		// 添加视频
		List<Map<String, Object>> videos = new ArrayList<Map<String, Object>>();
		if (newsMap.containsKey(News.VIDEOS) && null != newsMap.get(News.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) newsMap.get(News.VIDEOS);
			Map<String, Object> videosMap = null;
			for (Map<String, Object> mapV : videosFlag) {
				videosMap = new HashMap<String, Object>();
				String name = mapV.get("name") + "." + mapV.get("fmt");
				videosMap.put("VideoId", mapV.get("_id"));
				videosMap.put("Description", "");
				videosMap.put("VideoTitle", name);
				videosMap.put("ThumbnailPath", mapV.get("vslt"));
				videosMap.put("Play", 0);
				videosMap.put("Comment", 0);
				videosMap.put("Fans", 0);
				videosMap.put("VideoPublishTime", mapV.get("ctime"));
				videosMap.put("VideoCollectTime", mapV.get("ctime"));
				List<Map<String, Object>> defaults = (List<Map<String, Object>>) mapV.get("defaults");
				Map<String, Object> subItems = new HashMap<String, Object>();
				for (Map<String, Object> map : defaults) {
					if (map.containsKey("fmt") && "mp4".equals(String.valueOf(map.get("fmt")))) {
						subItems.put("SubID", UUID.randomUUID().toString());
						subItems.put("SubName", map.get("name"));
						subItems.put("SubFilePath", map.get("wanurl"));
					}
				}
				List<Map<String, Object>> subItemslist = new ArrayList<Map<String, Object>>();
				subItemslist.add(subItems);
				videosMap.put("SubItems", subItemslist);
				videos.add(videosMap);
			}
		}
		// 添加音频
		if (newsMap.containsKey(News.AUDIOS) && null != newsMap.get(News.AUDIOS)) {
			List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) newsMap.get(News.AUDIOS);
			Map<String, Object> audiosMap = null;
			for (Map<String, Object> mapA : audiosFlag) {
				audiosMap = new HashMap<String, Object>();
				String name = mapA.get("name") + "." + mapA.get("fmt");
				audiosMap.put("VideoId", mapA.get("_id"));
				audiosMap.put("Description", "");
				audiosMap.put("VideoTitle", name);
				audiosMap.put("ThumbnailPath", "");
				audiosMap.put("Play", 0);
				audiosMap.put("Comment", 0);
				audiosMap.put("Fans", 0);
				audiosMap.put("VideoPublishTime", mapA.get("ctime"));
				audiosMap.put("VideoCollectTime", mapA.get("ctime"));
				Map<String, Object> subItems = new HashMap<String, Object>();
				subItems.put("SubID", UUID.randomUUID().toString());
				subItems.put("SubName", name);
				subItems.put("SubFilePath", mapA.get("wanurl"));
				List<Map<String, Object>> subItemslist = new ArrayList<Map<String, Object>>();
				subItemslist.add(subItems);
				audiosMap.put("subItems", subItemslist);
				videos.add(audiosMap);
			}
		}
		news.put("Videos", videos);
		// 图片
		List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
		if (newsMap.containsKey(News.PICS) && null != newsMap.get(News.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) newsMap.get(News.PICS);
			Map<String, Object> imagesMap = null;
			for (Map<String, Object> mapP : imagesFlag) {
				imagesMap = new HashMap<String, Object>();
				String name = mapP.get("name") + "." + mapP.get("fmt");
				imagesMap.put("ImageId", mapP.get("_id"));
				imagesMap.put("ImageName", name);
				imagesMap.put("ImageUrl", mapP.get("wanurl"));
				imagesMap.put("FilePath", mapP.get("wanurl"));
				imagesMap.put("ThumbnailPath", mapP.get(Media.VSLT));
				images.add(imagesMap);
			}
		}
		news.put("Images", images);
		newsList.add(news);
		converge.put("News", newsList);
		converge.put("WBs", Collections.EMPTY_LIST);
		converge.put("StorageType", "");
		Map<String, Object> securityCertificate = getSecurityCertificate();
		converge.put("SecurityCertificate", securityCertificate);

		String paramJson = JsonUtil.writeMap2JSON(converge);
		// String paramUrl = configurationService.getConvergeUrl();
		String paramUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
		logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
		String result = HttpUtil.doPost(paramUrl, paramJson);
		if (null != result) {
			Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
			String status = String.valueOf(mapRes.get("Status"));
			String message = String.valueOf(mapRes.get("Message"));
			if ("200".equals(status) && "ok".equalsIgnoreCase(message)) {
				return true;
			} else {
				logger.error("推送智云失败：status=" + status + ",message=" + message);
			}
		}
		return false;

	}

	/**
	 * 获取推送智云的密码
	 * 
	 * @return
	 */
	private synchronized Map<String, Object> getSecurityCertificate() {
		Map<String, Object> securityCertificate = new HashMap<String, Object>();
		securityCertificate.put("appKey", Configuration.getConfigValue("APPKEY"));
		securityCertificate.put("userId", Configuration.getConfigValue("USERID"));
		String secret = Configuration.getConfigValue("SECRET");
		Random random = new Random();
		String randomId = "";
		for (int i = 0; i < 6; i++) {
			randomId += random.nextInt(10);
		}
		long randomId_long = Long.valueOf(randomId);
		securityCertificate.put("randomId", randomId_long);
		long currentTime = System.currentTimeMillis();
		securityCertificate.put("currentTime", currentTime);
		String md5 = MD5Util.string2MD5(secret + randomId_long + currentTime);
		securityCertificate.put("verifyCode", md5);
		return securityCertificate;
	}
	/**
	 * 2016-09-22 暂停使用该方法
	 */
	@Override
	public ResponseObject pushNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String pushType = null;
		if (mapJson.containsKey("pushType") && null != mapJson.get("pushType")) {
			pushType = String.valueOf(mapJson.get("pushType"));
		} else {
			return parameterError("pushType," + strJson);
		}
		String pushSetId = null;
		if (mapJson.containsKey(PushVo.PUSHSETID) && null != mapJson.get(PushVo.PUSHSETID)) {
			pushSetId = String.valueOf(mapJson.get(PushVo.PUSHSETID));
		} else {
			return parameterError("pushSetId," + strJson);
		}
		// 获取推送地址等信息
		Map<String, Object> mapPushSet = pushSetDao.findOne(pushSetId);
		if (null == mapPushSet || mapPushSet.isEmpty()) {
			return parameterError("mapPushSet," + strJson);
		}
		// 添加常用推送地址信息
		Map<String, Object> mapQueryCustom = new HashMap<String, Object>();
		mapQueryCustom.put("pushset._id", pushSetId);
		mapQueryCustom.put(CustomPush.CUSERID, commonParameters.getUserId());
		long resCustom = customPushDao.countObject(mapQueryCustom);
		if (resCustom > Constants.ZERO) {
			Map<String, Object> mapCustomPush = new HashMap<String, Object>();
			Map<String, Object> mapCustomPushUpdate = new HashMap<String, Object>();
			Map<String, Object> mapCustomPushInc = new HashMap<String, Object>();
			mapCustomPush.put(CustomPush.PUSHSET, mapPushSet);
			mapCustomPush.put(CustomPush.UUTIME, DateUtil.getCurrentDateTime());
			mapCustomPushInc.put(CustomPush.PUSHTOTAL, 1);
			mapCustomPushUpdate.put(QueryOperators.INC, mapCustomPushInc);
			mapCustomPushUpdate.put(QueryOperators.SET, mapCustomPush);
			customPushDao.updateOne(mapQueryCustom, mapCustomPushUpdate);
		} else {
			createCustomPush(commonParameters, mapPushSet);
		}
		// 判断文稿推送类型
		if ("NEWS".equals(pushType)) {// 推送通联
			resObj = pushNewsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else if ("DOCS".equals(pushType)) {// 推送文稿
			resObj = pushDocsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else if ("SHARENEWS".equals(pushType)) {// 推送共享通联
			resObj = pushShareNewsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else if ("SHAREDOCS".equals(pushType)) {// 推送共享文稿
			resObj = pushShareDocsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else {
			logger.warn("推送失败：没有找到推送文稿类型");
		}
		return resObj;
	}

	/**
	 * 判断推送终端
	 */
	private boolean pushToExternal(String uniqueName, Map<String, Object> newsMap, CommonParameters commonParameters) {
		boolean resFlag = false;
		// 判断推送接口类型
		if ("newsphere".equals(uniqueName)) {// newsphere
			resFlag = pushService.sendToNewsphere(newsMap, commonParameters);
		} else if ("jsnewsphere".equals(uniqueName)) {// jsnewsphere
			resFlag = pushService.sendToJSNewsphere(newsMap, commonParameters);
		} else if ("jscitychannel".equals(uniqueName)) {// 城市频道
			resFlag = pushService.sendToJSCityChannel(newsMap, commonParameters);
		}else if ("jssbcontenlib".equals(uniqueName)) {// 索贝内容库
			resFlag = pushService.sendToJSSBContent(newsMap, commonParameters);
		} else if ("pushLocal".equals(uniqueName)) {// 本地
			resFlag = sendToLocal(newsMap, commonParameters);
		} else if ("pushNet".equals(uniqueName)) {// 新媒体拍拍客户端
			resFlag = sendToNetStation(newsMap, commonParameters);
		} else if ("pushConverge".equals(uniqueName)) {// 智云
			resFlag = sendConverge2(newsMap, commonParameters);
		} else if ("coolCloud".equals(uniqueName)) {// 炫云
			resFlag = sendCoolCloud(newsMap, commonParameters);
		} else {
			logger.warn("推送失败：没有找到唯一标识符");
		}
		return resFlag;
	}
	@SuppressWarnings("unchecked")
	private boolean sendToNetStation(Map<String, Object> newsMap, CommonParameters commonParameters) {
		Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
		if (null == pushMap || pushMap.isEmpty()) {
			return false;
		}
		try {
			//推送文稿
			Map<String, Object> userMap = userDao.findOne(commonParameters.getUserId()); // 获取用户信息
			String uid = String.valueOf(userMap.get("uid"));
			if (null == userMap.get("uid") || "".equals(uid)) {
				logger.error("推送失败：没有用户id："+uid);
				return false;
			}
			String title = String.valueOf(newsMap.get(News.TITLE));
			String content = "";
			if (pushMap.containsKey(News.TEMPLATE) && null != pushMap.get(News.TEMPLATE)) {
				Map<String, Object> mapTemplate = (Map<String, Object>) pushMap.get(News.TEMPLATE);
				content = String.valueOf(mapTemplate.get("docsContent"));
			}
			String paramJson = "uid=" + uid + "&title=" + title + "&remark=" + content + "&tag=荔枝云";
			String paramUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
			logger.info("发送拍拍文稿，地址：" + paramUrl + "，参数：" + paramJson);
			String result = HttpUtil.sendPost(paramUrl, paramJson);
			if (!StringUtil.isEmpty(result)) {
				JSONObject jsonObj = new JSONObject(result);
				String status = jsonObj.getString("status");
				JSONObject paramz = jsonObj.getJSONObject("paramz");
				if ("ok".equalsIgnoreCase(status)) {
					//推送素材
					String fid = paramz.getString("id");
					List<Map<String, Object>> listFiles = new ArrayList<Map<String,Object>>();
					// vidos
					List<Map<String, Object>> vidos = (List<Map<String, Object>>) pushMap.get(Catalogue.VIDEOS);
					if (null != vidos && vidos.size() > 0) {
						for (Map<String, Object> map : vidos) {
							Map<String, Object> vidoMap = new HashMap<String, Object>();
							vidoMap.put("lenurl", URLEncoder.encode(String.valueOf(map.get("lenurl")), "utf-8"));
							vidoMap.put("wanurl", URLEncoder.encode(String.valueOf(map.get("wanurl")), "utf-8"));
							vidoMap.put("fmt", map.get("fmt"));
							listFiles.add(vidoMap);
						}
					}
					;
					// audios
					List<Map<String, Object>> audios = (List<Map<String, Object>>) pushMap.get(Catalogue.AUDIOS);
					if (null != audios && audios.size() > 0) {
						for (Map<String, Object> map : audios) {
							Map<String, Object> audioMap = new HashMap<String, Object>();
							audioMap.put("lenurl", URLEncoder.encode(String.valueOf(map.get("lenurl")), "utf-8"));
							audioMap.put("wanurl", URLEncoder.encode(String.valueOf(map.get("wanurl")), "utf-8"));
							audioMap.put("fmt", map.get("fmt"));
							listFiles.add(audioMap);
						}
					}
					// images
					List<Map<String, Object>> images = (List<Map<String, Object>>) pushMap.get(Catalogue.PICS);
					if (null != images && images.size() > 0) {
						for (Map<String, Object> map : images) {
							Map<String, Object> imgMap = new HashMap<String, Object>();
							imgMap.put("lenurl", URLEncoder.encode(String.valueOf(map.get("lenurl")), "utf-8"));
							imgMap.put("wanurl", URLEncoder.encode(String.valueOf(map.get("wanurl")), "utf-8"));
							imgMap.put("fmt", map.get("fmt"));
							listFiles.add(imgMap);
						}
					}
//					if (mapNews.containsKey(News.VIDEOS) && null != mapNews.get(News.VIDEOS)) {
//						List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) mapNews.get(News.VIDEOS);
//						for (Map<String, Object> mapV : videosFlag) {
//							if (mapV.containsKey(Media.DEFAULTS) && null != mapV.get(Media.DEFAULTS)) {
//								List<Map<String, Object>> videosDefaults = (List<Map<String, Object>>) mapV.get(Media.DEFAULTS);
//								Map<String, Object> mapVideo = null;
//								for (Map<String, Object> mapD : videosDefaults) {
//									String fmt = String.valueOf(mapD.get(Media.FMT));
//									if ("mp4".equals(fmt)) {
//										mapVideo = new HashMap<String, Object>();
//										mapVideo.put("lenurl", mapD.get("lenurl"));
//										mapVideo.put("wanurl", mapD.get("wanurl"));
//										mapVideo.put("fmt", mapD.get("fmt"));
//										listFiles.add(mapVideo);
//									}
//								}
//							}
//						}
//					}
//					if (mapNews.containsKey(News.PICS) && null != mapNews.get(News.PICS)) {
//						List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) mapNews.get(News.PICS);
//						Map<String, Object> imagesMap = null;
//						for (Map<String, Object> mapP : imagesFlag) {
//							imagesMap = new HashMap<String, Object>();
//							imagesMap.put("lenurl", mapP.get("lenurl"));
//							imagesMap.put("wanurl", mapP.get("wanurl"));
//							imagesMap.put("fmt", mapP.get("fmt"));
//							listFiles.add(imagesMap);
//						}
//					}
					if (listFiles.size() > 0) {
						String sendUrl = String.valueOf(newsMap.get(PushSet.QUERYURL));
						threadPoolTaskExecutor.execute(new UploadFileToPPThread(listFiles, fid, uid, sendUrl));
					}
					return true;
				}else{
					logger.error("推送失败：status="+status);
				}
			}
		} catch (Exception e) {
			logger.error("处理异常，推送失败！");
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean sendCoolCloud(Map<String, Object> newsMap, CommonParameters commonParameters) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("accessToken",commonParameters.getAccessToken());
			paramMap.put("timeStamp" ,commonParameters.getTimeStamp());
//			paramMap.put("productId","yntv");
			String companyId = commonParameters.getCompanyId();
			if ("cdv-yuntonglian".equals(companyId)) {
				companyId = "yntv";
			}
			paramMap.put("productId",companyId);
			paramMap.put("username" ,commonParameters.getUserName());
			/** para */
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("templateId", "");
			/** detail */
			Map<String, Object> detailMap = new HashMap<String, Object>();
			Map<String, Object> template = (Map<String, Object>) newsMap.get(Catalogue.TEMPLATE);
			detailMap.put("title", template.get("title"));
			detailMap.put("gongpiantai", template.get("tvStationName"));
			detailMap.put("laiyuan", template.get(Catalogue.SRC));
			detailMap.put("jizhe", template.get("reporter"));
			detailMap.put("lanmu", template.get("program"));
			detailMap.put("syslaiyuan", 7);
			detailMap.put("zhengwen", template.get("content"));
			detailMap.put("zhengwenhtml", template.get("docsContentHTML"));
			paraMap.put("detail", detailMap);
			Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
			// vidos
			List<Map<String, Object>> vidosMap = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> vidos = (List<Map<String, Object>>) pushMap.get(Catalogue.VIDEOS);
			if (null != vidos && vidos.size() > 0) {
				for (Map<String, Object> map : vidos) {
					Map<String, Object> vidoMap = new HashMap<String, Object>();
					vidoMap.put("vname", map.get("name"));
					vidoMap.put("vurl", URLEncoder.encode(String.valueOf(map.get("wanurl")), "utf-8"));
					vidosMap.add(vidoMap);
				}
			}
			paraMap.put("videos", vidosMap);
			// audios
			List<Map<String, Object>> audiosMap = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> audios = (List<Map<String, Object>>) pushMap.get(Catalogue.AUDIOS);
			if (null != audios && audios.size() > 0) {
				for (Map<String, Object> map : audios) {
					Map<String, Object> audioMap = new HashMap<String, Object>();
					audioMap.put("aname", map.get("name"));
					audioMap.put("aurl", URLEncoder.encode(String.valueOf(map.get("wanurl")), "utf-8"));
					audiosMap.add(audioMap);
				}
			}
			paraMap.put("audios", audiosMap);
			// images
			List<Map<String, Object>> imagesMap = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> images = (List<Map<String, Object>>) pushMap.get(Catalogue.PICS);
			if (null != images && images.size() > 0) {
				for (Map<String, Object> map : images) {
					Map<String, Object> imgMap = new HashMap<String, Object>();
					imgMap.put("iname", map.get("name"));
					imgMap.put("iurl", URLEncoder.encode(String.valueOf(map.get("wanurl")), "utf-8"));
					imagesMap.add(imgMap);
				}
			}
			paraMap.put("images", imagesMap);
			paramMap.put("param" ,paraMap);
			String paramJson = JsonUtil.writeMap2JSON(paramMap);
			// 推送
			String paramUrl = String.valueOf(newsMap.get(PushSet.PUSHURL)) + commonParameters.getAppCode() + "/3.0/"
					+ companyId +"/" + commonParameters.getCasUserId() + "/vms/api4wx/uploadLocal/";
			logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
			String result = HttpUtil.doPost(paramUrl, paramJson);
			if (null != result) {
				Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
				String status = String.valueOf(mapRes.get("status"));
				String message = String.valueOf(mapRes.get("message"));
				if ("0".equals(status)) {
					return true;
				} else {
					logger.error("推送失败：status=" + status + ",message=" + message);
				}
			}
		} catch (Exception e) {
			logger.error("推送失败：" + e.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * 推送通联
	 */
	private ResponseObject pushNewsToExternal(ResponseObject resObj, Map<String, Object> mapJson, Map<String, Object> mapPushSet,
			CommonParameters commonParameters) {
		boolean resFlag = false;
		String uniqueName = String.valueOf(mapPushSet.get(PushSet.UNIQUENAME));
		String pushurl = String.valueOf(mapPushSet.get(PushSet.PUSHURL));
		String othermsg = String.valueOf(mapPushSet.get(PushSet.OTHERMSG));
		// 获取业务参数,并进行相关业务操作
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + mapJson);
		}
		// 获取通联并执行推送
		Map<String, Object> newsMap = newsDao.findOne(newsId); // 获取通联信息
		newsMap.put(PushSet.PUSHURL, pushurl);
		newsMap.put(PushSet.OTHERMSG, othermsg);
		if(mapPushSet.containsKey(PushSet.QUERYURL)){
			newsMap.put(PushSet.QUERYURL, mapPushSet.get(PushSet.QUERYURL));
		}
		// 判断推送接口类型
		resFlag = pushToExternal(uniqueName, newsMap, commonParameters);

		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdateOpt = new HashMap<String, Object>();
		mapQuery.put(News.ID, new ObjectId(newsId));
		mapQuery.put("shareuser.userId", commonParameters.getUserId());
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		mapUpdate.put("shareuser.$.uutime", DateUtil.getCurrentDateTime());
		mapUpdate.put("shareuser.$.status", GeneralStatus.hasPushed.status);
		mapUpdate.put(News.STATUS, GeneralStatus.hasPushed.status);
		if (resFlag) {
			mapUpdate.put("shareuser.$.inewstatus", GeneralStatus.success.status);
			Map<String, Object> mapPush = new HashMap<String, Object>();
			mapPush.put(News.PUSHTOTAL, 1);
			mapUpdateOpt.put(QueryOperators.INC, mapPush);
		} else {
			mapUpdate.put("shareuser.$.inewstatus", GeneralStatus.failure.status);
		}
		mapUpdate.put(News.UUTIME, DateUtil.getCurrentDateTime());
		mapUpdateOpt.put(QueryOperators.SET, mapUpdate);
		long resNum = newsDao.updateMany(mapQuery, mapUpdateOpt, true);
		if (0 < resNum && resFlag) {
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "推送通联", "推送一条新通联《" + newsMap.get(News.TITLE) + "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》"));
			LogUtil.printIntegralLog(commonParameters, "pushnews", "推送一条新通联《" + newsMap.get(News.TITLE) + "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》");
		}
		return resObj;
	}

	/**
	 * 推送文稿
	 */
	private ResponseObject pushDocsToExternal(ResponseObject resObj, Map<String, Object> mapJson, Map<String, Object> mapPushSet,
			CommonParameters commonParameters) {
		boolean resFlag = false;
		String uniqueName = String.valueOf(mapPushSet.get(PushSet.UNIQUENAME));
		String pushurl = String.valueOf(mapPushSet.get(PushSet.PUSHURL));
		String othermsg = String.valueOf(mapPushSet.get(PushSet.OTHERMSG));
		String docId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			docId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("docId," + mapJson);
		}
		Map<String, Object> docsMap = materialDao.queryOne(docId);
		docsMap.put(PushSet.PUSHURL, pushurl);
		docsMap.put(PushSet.OTHERMSG, othermsg);
		if(mapPushSet.containsKey(PushSet.QUERYURL)){
			docsMap.put(PushSet.QUERYURL, mapPushSet.get(PushSet.QUERYURL));
		}
		// 判断推送接口类型
		resFlag = pushToExternal(uniqueName, docsMap, commonParameters);
		if (resFlag) {
			//更新文稿推送状态
			Map<String, Object> mapQuery = new HashMap<String, Object>();
			Map<String, Object> mapUpdateOpt = new HashMap<String, Object>();
			getPushSetMap(mapQuery, mapUpdateOpt, commonParameters, docId, mapPushSet);
			if (!mapQuery.isEmpty() && !mapUpdateOpt.isEmpty()) {
				materialDao.update(mapQuery, mapUpdateOpt);
			}else{
				logger.warn("-----------------拼装更新map数据为空！-----------------------");
			}
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "推送文稿", "推送一条文稿信息《" + String.valueOf(docsMap.get(Catalogue.TITLE))
					+ "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》"));
			LogUtil.printIntegralLog(commonParameters, "pushdoc", "推送一条文稿信息《" + String.valueOf(docsMap.get(Catalogue.TITLE))
					+ "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》");
		}
		return resObj;
	}

	private void getPushSetMap(Map<String, Object> mapQuery, Map<String, Object> mapUpdateOpt, CommonParameters commonParameters, String docId,
			Map<String, Object> mapPushSet) {
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		mapQuery.put(Catalogue.ID, new ObjectId(docId));
		Map<String, Object> mapPush = new HashMap<String, Object>();
		mapPush.put(FieldNews.PUSHTOTAL, 1);
		mapUpdateOpt.put(QueryOperators.INC, mapPush);
		// 添加推送id和用户id到通联中
		Map<String, Object> mapPushSetNews = new HashMap<String, Object>();
		Map<String, Object> mapPushSetNewsValue = new HashMap<String, Object>();
		mapPushSetNewsValue.put(News.CUSERID, commonParameters.getUserId());
		mapPushSetNewsValue.put(User.NAME, commonParameters.getUserName());
		mapPushSetNewsValue.put(User.EMAIL, commonParameters.getLoginName());
		mapPushSetNewsValue.put(PushSet.UNIQUENAME, mapPushSet.get(PushSet.UNIQUENAME));
		mapPushSetNewsValue.put(PushSet.PUSHNAME, mapPushSet.get(PushSet.PUSHNAME));
		mapPushSetNews.put(News.PUSHSET, mapPushSetNewsValue);
		mapUpdateOpt.put(QueryOperators.ADDTOSET, mapPushSetNews);
		mapUpdate.put(FieldNews.UUTIME, DateUtil.getCurrentDateTime());
		mapUpdateOpt.put(QueryOperators.SET, mapUpdate);
	}
	
	/**
	 * 推送共享文稿
	 */
	private ResponseObject pushShareDocsToExternal(ResponseObject resObj, Map<String, Object> mapJson, Map<String, Object> mapPushSet,
			CommonParameters commonParameters) {
		boolean resFlag = false;
		String uniqueName = String.valueOf(mapPushSet.get(PushSet.UNIQUENAME));
		String pushurl = String.valueOf(mapPushSet.get(PushSet.PUSHURL));
		String othermsg = String.valueOf(mapPushSet.get(PushSet.OTHERMSG));
		String docId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			docId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("docId," + mapJson);
		}
		Map<String, Object> docsMap = fieldNewsDao.findOne(docId);
		docsMap.put(PushSet.PUSHURL, pushurl);
		docsMap.put(PushSet.OTHERMSG, othermsg);
		if(mapPushSet.containsKey(PushSet.QUERYURL)){
			docsMap.put(PushSet.QUERYURL, mapPushSet.get(PushSet.QUERYURL));
		}
		// 判断推送接口类型
		resFlag = pushToExternal(uniqueName, docsMap, commonParameters);
		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdateOpt = new HashMap<String, Object>();
		if (resFlag) {
			//更新文稿推送状态
			getPushSetMap(mapQuery, mapUpdateOpt, commonParameters, docId, mapPushSet);
			long resNum = fieldNewsDao.updateMany(mapQuery, mapUpdateOpt, true);
			if (0 < resNum) {
				executeSuccess(resObj);
				systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "推送共享文稿", "推送一条共享文稿信息《" + String.valueOf(docsMap.get(Catalogue.TITLE))
						+ "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》"));
				LogUtil.printIntegralLog(commonParameters, "pushdoc", "推送一条共享文稿信息《" + String.valueOf(docsMap.get(Catalogue.TITLE))
						+ "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》");
			}
		}
		return resObj;
	}

	/**
	 * 推送共享通联
	 */
	private ResponseObject pushShareNewsToExternal(ResponseObject resObj, Map<String, Object> mapJson, Map<String, Object> mapPushSet,
			CommonParameters commonParameters) {
		boolean resFlag = false;
		String uniqueName = String.valueOf(mapPushSet.get(PushSet.UNIQUENAME));
		String pushurl = String.valueOf(mapPushSet.get(PushSet.PUSHURL));
		String pushSetId = String.valueOf(mapPushSet.get(PushSet.ID));
		String othermsg = String.valueOf(mapPushSet.get(PushSet.OTHERMSG));
		// 获取通联并执行推送
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + mapJson);
		}
		Map<String, Object> newsMap = newsDao.findOne(newsId); // 获取通联信息
		newsMap.put(PushSet.PUSHURL, pushurl);
		newsMap.put(PushSet.OTHERMSG, othermsg);
		if(mapPushSet.containsKey(PushSet.QUERYURL)){
			newsMap.put(PushSet.QUERYURL, mapPushSet.get(PushSet.QUERYURL));
		}
		
		// 判断推送接口类型
		resFlag = pushToExternal(uniqueName, newsMap, commonParameters);

		Map<String, Object> mapQuery = new HashMap<String, Object>();
		Map<String, Object> mapUpdateOpt = new HashMap<String, Object>();
		mapQuery.put(News.ID, new ObjectId(newsId));
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		if (resFlag) {
			// 增加推送次数
			Map<String, Object> mapPush = new HashMap<String, Object>();
			mapPush.put(News.PUSHTOTAL, 1);
			mapUpdateOpt.put(QueryOperators.INC, mapPush);
			// 添加推送id和用户id到通联中
			Map<String, Object> mapPushSetNews = new HashMap<String, Object>();
			Map<String, Object> mapPushSetNewsValue = new HashMap<String, Object>();
			mapPushSetNewsValue.put(News.CUSERID, commonParameters.getUserId());
			mapPushSetNewsValue.put("pushsetid", pushSetId);
			mapPushSetNews.put(News.PUSHSET, mapPushSetNewsValue);
			mapUpdateOpt.put(QueryOperators.ADDTOSET, mapPushSetNews);
		}
		mapUpdate.put(News.UUTIME, DateUtil.getCurrentDateTime());
		mapUpdateOpt.put(QueryOperators.SET, mapUpdate);
		long resNum = newsDao.updateMany(mapQuery, mapUpdateOpt, true);
		if (0 < resNum && resFlag) {
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "推送共享通联",
					"推送一条共享通联《" + newsMap.get(News.TITLE) + "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》"));
			LogUtil.printIntegralLog(commonParameters, "pushnews", "推送一条共享通联《" + newsMap.get(News.TITLE) + "》到《" + mapPushSet.get(PushSet.PUSHNAME) + "》");
			
		}
		return resObj;
	}

	private Map<String, Object> createCustomPush(CommonParameters commonParameters, Map<String, Object> mapPushSet) {
		Map<String, Object> mapCustomPush = new HashMap<String, Object>();
		mapCustomPush.put(CustomPush.PUSHSET, mapPushSet);
		mapCustomPush.put(CustomPush.PUSHTOTAL, 1);
		mapCustomPush.put(CustomPush.CUSERID, commonParameters.getUserId());
		mapCustomPush.put(CustomPush.CUSENAME, commonParameters.getUserName());
		mapCustomPush.put(CustomPush.CTIME, DateUtil.getCurrentDateTime());
		mapCustomPush.put(CustomPush.UUTIME, DateUtil.getCurrentDateTime());
		mapCustomPush.put(CustomPush.CONSUMERID, commonParameters.getCompanyId());
		mapCustomPush.put(CustomPush.CONSUMERNAME, commonParameters.getConsumerName());
		mapCustomPush.put(CustomPush.DEPARTMENTID, commonParameters.getDepartmentId());
		String customPushId = customPushDao.insertObject(mapCustomPush);
		if (null != customPushId) {
			mapCustomPush.put(CustomPush.ID, customPushId);
			return mapCustomPush;
		}
		logger.error("创建记录失败！mapCustomPush=" + mapCustomPush);
		return null;
	}

	/** 移动端查询通联时返回的字段 */
	private Map<String, Object> getNewsMapPhone() {
		Map<String, Object> mapBack = new HashMap<String, Object>();
		mapBack.put(News.TITLE, 1);
		mapBack.put(News.TEMPLATE, 1);
		mapBack.put(News.STATUS, 1);
		mapBack.put(News.UUTIME, 1);
		mapBack.put(News.READETOTAL, 1);
		mapBack.put(News.PUSHTOTAL, 1);
		mapBack.put("videos._id", 1);
		mapBack.put("videos.name", 1);
		mapBack.put("videos.vslt", 1);
		mapBack.put("videos.defaults.fmt", 1);
		mapBack.put("videos.defaults.ctype", 1);
		mapBack.put("videos.defaults.wanurl", 1);
		mapBack.put("pics._id", 1);
		mapBack.put("pics.name", 1);
		mapBack.put("pics.vslt", 1);
		mapBack.put("pics.wanurl", 1);
		mapBack.put("pics.mtype", 1);
		mapBack.put("audios._id", 1);
		mapBack.put("audios.name", 1);
		mapBack.put("audios.defaults.fmt", 1);
		mapBack.put("audios.defaults.wanurl", 1);
		mapBack.put("audios.wanurl", 1);
		mapBack.put(News.CHECKUSER, 1);
		mapBack.put(News.SHAREUSER, 1);
		mapBack.put(News.COMMENTS, 1);
		mapBack.put(News.CUSERID, 1);
		return mapBack;
	}

	/** 查询通联时返回的字段 */
	private Map<String, Object> getNewsMapSimple() {
		Map<String, Object> mapBack = new HashMap<String, Object>();
		mapBack.put(News.TITLE, 1);
		mapBack.put(News.STATUS, 1);
		mapBack.put(News.UUTIME, 1);
		mapBack.put(News.READETOTAL, 1);
		mapBack.put(News.PUSHTOTAL, 1);
		mapBack.put(News.CHECKUSER, 1);
		mapBack.put(News.SHAREUSER, 1);
		mapBack.put(News.COMMENTS, 1);
		mapBack.put(News.CUSERID, 1);
		return mapBack;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean sendConverge2(Map<String, Object> newsMap, CommonParameters commonParameters) {

		if (null == newsMap || newsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> converge = new HashMap<String, Object>();
		List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> news = new HashMap<String, Object>();
		Map<String, Object> template = (Map<String, Object>) newsMap.get(News.TEMPLATE);

		news.put("WebFrom", "");
		news.put("Hit", 0);
		news.put("Language", "中文");
		news.put("Url", "http://news.cdvcloud.com");
		news.put("WebBold", false);
		news.put("TitleYxm", "");
		news.put("Title", newsMap.get(News.TITLE));

		news.put("ChannelID", UUID.randomUUID().toString());
		news.put("Character", "中性");
		news.put("PublishTime", newsMap.get(News.UUTIME));
		news.put("Summary", template.get("content"));
		news.put("Content", template.get("docsContentHTML"));// html标签
		news.put("Author", template.get("createor"));
		news.put("NewsID", newsMap.get(Media.ID));
		news.put("Reply", 0);
		news.put("ChannelName", "云通联");
		news.put("Quality", "正常");
		news.put("CollectTime", DateUtil.getCurrentDateTime());
		
		Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
		// 添加视频
		List<Map<String, Object>> videos = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.VIDEOS) && null != pushMap.get(News.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(News.VIDEOS);
			Map<String, Object> videosMap = null;
			for (Map<String, Object> mapV : videosFlag) {
				videosMap = new HashMap<String, Object>();
				String name = mapV.get("name") + "." + mapV.get("fmt");
				videosMap.put("VideoId", mapV.get("_id"));
				videosMap.put("Description", "");
				videosMap.put("VideoTitle", name);
				videosMap.put("ThumbnailPath", mapV.get("vslt"));
				videosMap.put("Play", 0);
				videosMap.put("Comment", 0);
				videosMap.put("Fans", 0);
				videosMap.put("VideoPublishTime", mapV.get("ctime"));
				videosMap.put("VideoCollectTime", mapV.get("ctime"));
				Map<String, Object> subItems = new HashMap<String, Object>();
				subItems.put("SubID", UUID.randomUUID().toString());
				subItems.put("SubName", mapV.get("name"));
				subItems.put("SubFilePath", mapV.get("wanurl"));
				List<Map<String, Object>> subItemslist = new ArrayList<Map<String, Object>>();
				subItemslist.add(subItems);
				videosMap.put("SubItems", subItemslist);
				videos.add(videosMap);
			}
		}
		// 添加音频
		if (pushMap.containsKey(News.AUDIOS) && null != pushMap.get(News.AUDIOS)) {
			List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) pushMap.get(News.AUDIOS);
			Map<String, Object> audiosMap = null;
			for (Map<String, Object> mapA : audiosFlag) {
				audiosMap = new HashMap<String, Object>();
				String name = mapA.get("name") + "." + mapA.get("fmt");
				audiosMap.put("VideoId", mapA.get("_id"));
				audiosMap.put("Description", "");
				audiosMap.put("VideoTitle", name);
				audiosMap.put("ThumbnailPath", "");
				audiosMap.put("Play", 0);
				audiosMap.put("Comment", 0);
				audiosMap.put("Fans", 0);
				audiosMap.put("VideoPublishTime", mapA.get("ctime"));
				audiosMap.put("VideoCollectTime", mapA.get("ctime"));
				Map<String, Object> subItems = new HashMap<String, Object>();
				subItems.put("SubID", UUID.randomUUID().toString());
				subItems.put("SubName", name);
				subItems.put("SubFilePath", mapA.get("wanurl"));
				List<Map<String, Object>> subItemslist = new ArrayList<Map<String, Object>>();
				subItemslist.add(subItems);
				audiosMap.put("subItems", subItemslist);
				videos.add(audiosMap);
			}
		}
		news.put("Videos", videos);
		// 图片
		List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.PICS) && null != pushMap.get(News.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(News.PICS);
			Map<String, Object> imagesMap = null;
			for (Map<String, Object> mapP : imagesFlag) {
				imagesMap = new HashMap<String, Object>();
				String name = mapP.get("name") + "." + mapP.get("fmt");
				imagesMap.put("ImageId", mapP.get("_id"));
				imagesMap.put("ImageName", name);
				imagesMap.put("ImageUrl", mapP.get("wanurl"));
				imagesMap.put("FilePath", mapP.get("wanurl"));
				imagesMap.put("ThumbnailPath", mapP.get(Media.VSLT));
				images.add(imagesMap);
			}
		}
		news.put("Images", images);
		newsList.add(news);
		converge.put("News", newsList);
		converge.put("WBs", Collections.EMPTY_LIST);
		converge.put("StorageType", "");
		Map<String, Object> securityCertificate = getSecurityCertificate();
		converge.put("SecurityCertificate", securityCertificate);

		String paramJson = JsonUtil.writeMap2JSON(converge);
		// String paramUrl = configurationService.getConvergeUrl();
		String paramUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
		logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
		String result = HttpUtil.doPost(paramUrl, paramJson);
		if (null != result) {
			Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
			String status = String.valueOf(mapRes.get("Status"));
			String message = String.valueOf(mapRes.get("Message"));
			if ("200".equals(status) && "ok".equalsIgnoreCase(message)) {
				return true;
			} else {
				logger.error("推送智云失败：status=" + status + ",message=" + message);
			}
		}
		return false;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject unshareNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		// 获取业务参数
		String newsId = docJson.getString(NewsVo.NEWSID);
		Integer shareNews = Constants.ONE;
		if (docJson.containsKey(NewsVo.SHARENEWS)) {
			shareNews = docJson.getInteger(NewsVo.SHARENEWS);
		}
		// 取消通联共享
		Map<String, Object> mapWhere = new HashMap<String, Object>();
		Map<String, Object> mapUpdate = new HashMap<String, Object>();
		mapWhere.put(News.ID, new ObjectId(newsId));
		mapUpdate.put(News.SHARENEWS, shareNews);
		long resNum = newsDao.updateManyBySet(mapWhere, mapUpdate, true);
		if (resNum > 0) {
			executeSuccess(resObj);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "更新共享通联", "取消一条共享通联《" + newsId + "》"));
		}
		return resObj;
	}
	/**
	 * 2016-09-22 增加新版推送，，，之前版本暂时不使用（pushNews 两个参数暂不使用）
	 */
	@Override
	public ResponseObject pushNews(CommonParameters commonParameters, String strJson, String pushTaskId) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String pushType = null;
		if (mapJson.containsKey("pushType") && null != mapJson.get("pushType")) {
			pushType = String.valueOf(mapJson.get("pushType"));
		} else {
			return parameterError("pushType," + strJson);
		}
		String pushSetId = null;
		if (mapJson.containsKey(PushVo.PUSHSETID) && null != mapJson.get(PushVo.PUSHSETID)) {
			pushSetId = String.valueOf(mapJson.get(PushVo.PUSHSETID));
		} else {
			return parameterError("pushSetId," + strJson);
		}
		// 获取推送地址等信息
		Map<String, Object> mapPushSet = pushSetDao.findOne(pushSetId);
		if (null == mapPushSet || mapPushSet.isEmpty()) {
			return parameterError("mapPushSet," + strJson);
		}
		// 添加常用推送地址信息
		Map<String, Object> mapQueryCustom = new HashMap<String, Object>();
		mapQueryCustom.put("pushset._id", pushSetId);
		mapQueryCustom.put(CustomPush.CUSERID, commonParameters.getUserId());
		long resCustom = customPushDao.countObject(mapQueryCustom);
		if (resCustom > Constants.ZERO) {
			Map<String, Object> mapCustomPush = new HashMap<String, Object>();
			Map<String, Object> mapCustomPushUpdate = new HashMap<String, Object>();
			Map<String, Object> mapCustomPushInc = new HashMap<String, Object>();
			mapCustomPush.put(CustomPush.PUSHSET, mapPushSet);
			mapCustomPush.put(CustomPush.UUTIME, DateUtil.getCurrentDateTime());
			mapCustomPushInc.put(CustomPush.PUSHTOTAL, 1);
			mapCustomPushUpdate.put(QueryOperators.INC, mapCustomPushInc);
			mapCustomPushUpdate.put(QueryOperators.SET, mapCustomPush);
			customPushDao.updateOne(mapQueryCustom, mapCustomPushUpdate);
		} else {
			createCustomPush(commonParameters, mapPushSet);
		}
		// 判断文稿推送类型
		if ("NEWS".equals(pushType)) {// 推送通联
			resObj = pushNewsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else if ("DOCS".equals(pushType)) {// 推送文稿
			resObj = pushDocsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else if ("SHARENEWS".equals(pushType)) {// 推送共享通联
			resObj = pushShareNewsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else if ("SHAREDOCS".equals(pushType)) {// 推送共享文稿
			resObj = pushShareDocsToExternal(resObj, mapJson, mapPushSet, commonParameters);
		} else {
			logger.warn("推送失败：没有找到推送文稿类型");
		}
		//新增方法
		if(!StringUtil.isEmpty(pushTaskId)){
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(PushTask.ID, new ObjectId(pushTaskId));
			Map<String, Object> update = new HashMap<String, Object>();
			if(resObj.getCode()==0){
				update.put(PushTask.STATUS, 0);
			}else{
				update.put(PushTask.STATUS, 1);
			}
			pushTaskDao.updateManyBySet(filter, update,true);
		}
		return resObj;
	}

}
