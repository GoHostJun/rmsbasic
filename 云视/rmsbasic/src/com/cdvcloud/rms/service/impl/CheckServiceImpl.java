package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.NewsVo;
import com.cdvcloud.rms.dao.ICheckDao;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.domain.Checks;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.domain.WechatTemplate;
import com.cdvcloud.rms.service.ICheckService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JPushUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;

@Service
public class CheckServiceImpl extends BasicService implements ICheckService {
	private static final Logger logger = Logger.getLogger(CheckServiceImpl.class);
	@Autowired
	private INewsDao newsDao;
	@Autowired
	private ICheckDao checkDao;
	@Autowired
	private IWeChatService weChatService;
	@Autowired
	private ISystemLogService systemLogService;

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject createCheck(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.check_failure.status, GeneralStatus.check_failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		
		// 获取业务参数
		String newsId = docJson.getString(NewsVo.NEWSID);
		Integer checkStatus = docJson.getInteger(NewsVo.CHECKSTATUS);
		String checkOpinion = docJson.getString(NewsVo.CHECKOPINION);
		if (StringUtil.isEmpty(newsId) || StringUtil.isEmpty(checkStatus)) {
			return parameterError("newsId,checkStatus" + strJson);
		}
		// 获取当前通联
		Document newsDoc = newsDao.findOneDocument(newsId);
		if (null == newsDoc || newsDoc.isEmpty()) {
			return queryError("没有获取到原通联！newsId+" + newsId);
		}
		String newsTitle = newsDoc.getString(News.TITLE);
		// 插入一条审核记录
		Map<String, Object> checkMap = createCheck(commonParameters, newsId, newsTitle, checkOpinion,"审核", checkStatus);
		// 更新通联，添加审核记录，更新审核人审核状态和审核意见，
		if (!StringUtil.isEmpty(checkMap)) {
			resObj = updateNewsAddCheck(resObj, commonParameters, newsId, checkMap);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "审核通联","审核通联《" + newsTitle + "》"));
			LogUtil.printIntegralLog(commonParameters, "checknews", "审核通联《" + newsTitle + "》");
		}
		return resObj;
	}

	@Override
	public ResponseObject findCheckAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, Checks.TITLE, Checks.CTIME);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(Checks.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = checkDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = checkDao.countCheck(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@SuppressWarnings({ "static-access" })
	@Override
	public ResponseObject findCheckById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		
		// 获取业务参数
		String checkId = docJson.getString(Checks.ID);
		Map<String, Object> checkDoc = checkDao.findOne(checkId);
		if (!checkDoc.isEmpty()) {
			executeSuccess(resObj, checkDoc);
		} else {
			logger.warn("获取通联审核记录为空！checkId：" + checkId);
		}
		return resObj;
	}

	@Override
	public ResponseObject submitNewsToCheck(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.submit_check_failure.status, GeneralStatus.submit_check_failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		String newsId = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		Map<String, Object> newsMap = newsDao.findOne(newsId);
		if (null == newsMap || newsMap.isEmpty()) {
			return queryError("没有获取到原通联！newsId+" + newsId);
		}
		String title = String.valueOf(newsMap.get(News.TITLE));
		Map<String, Object> checkMap = createCheck(commonParameters, newsId, title, "","提交审核任务", GeneralStatus.checkWait.status);
		if (null == checkMap || checkMap.isEmpty()) {
			return resObj;
		}
		resObj = updateNewsToCheck(resObj, commonParameters, newsId, checkMap);
		return resObj;
	}

	/**
	 * 插入一条审核记录
	 * 
	 * @param commonParameters
	 *            公共参数
	 * @param newsId
	 *            通联id
	 * @param title
	 *            通联标题
	 * @param checkOpinion
	 *            审核意见
	 * @param checkStatus
	 *            审核状态
	 * @return
	 */
	private Map<String, Object> createCheck(CommonParameters commonParameters, String newsId, String title, String checkOpinion,String checkOpt, int checkStatus) {
		Map<String, Object> checkMap = new HashMap<String, Object>();
		checkMap.put(Checks.CATAID, newsId);
		checkMap.put(Checks.TITLE, title);
		checkMap.put(Checks.CHECKINFO, checkOpinion);
		checkMap.put(Checks.CHECKOPT, checkOpt);
		checkMap.put(Checks.STATUS, checkStatus);
		checkMap.put(Checks.CUSERID, commonParameters.getUserId());
		checkMap.put(Checks.CUSENAME, commonParameters.getUserName());
		checkMap.put(Checks.CTIME, DateUtil.getCurrentDateTime());
		checkMap.put(Checks.CONSUMERID, commonParameters.getCompanyId());
		checkMap.put(Checks.CONSUMERNAME, commonParameters.getConsumerName());
		checkMap.put(Checks.DEPARTMENTID, commonParameters.getDepartmentId());
		String checkId = checkDao.insertCheck(checkMap);
		if (null != checkId) {
			checkMap.put(Checks.ID, checkId);
			return checkMap;
		}
		logger.error("创建审核记录失败！checkMap="+checkMap);
		return null;
	}
	
	/**
	 * 更新审核记录到通联表中
	 * @param resObj 返回结果集
	 * @param commonParameters 公共参数
	 * @param newsId 通联id
	 * @param checkMap 审核信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ResponseObject updateNewsAddCheck(ResponseObject resObj,CommonParameters commonParameters, String newsId,Map<String, Object> checkMap){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		Map<String, Object> updateMap = new HashMap<String, Object>();
		Map<String, Object> updateMapEach = new HashMap<String, Object>();
		Map<String, Object> updateMapAdd = new HashMap<String, Object>();
		Map<String, Object> updateMapOther = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		queryMap.put(News.ID, new ObjectId(newsId));
		Map<String, Object> mapNews = newsDao.findOne(newsId);
		Integer checkMapStatus = Integer.valueOf(String.valueOf(checkMap.get(News.STATUS)));
		
		updateMapOther.put(News.STATUS, checkMap.get(Checks.STATUS));
		if (checkMapStatus.equals(GeneralStatus.checkPass.status) &&!StringUtil.isEmpty(mapNews) && mapNews.containsKey(News.CHECKUSER)) {
			List<Map<String, Object>> listCheckUser = (List<Map<String, Object>>) mapNews.get(News.CHECKUSER);
			for (Map<String, Object> mapUser : listCheckUser) {
//				if("0".equals(isCompleCheck)&&mapUser.get(News.STATUS).equals(GeneralStatus.checkWait.status)){
//					isCompleCheck="1";
//				}
				if (null == mapUser.get(News.STATUS)) {
					continue;
				}
				Integer checkStatus = Integer.valueOf(String.valueOf(mapUser.get(News.STATUS)));
				String checkUserId1 = String.valueOf(mapUser.get("userId"));
				String checkUserId2 = String.valueOf(commonParameters.getUserId());
				if (!checkUserId1.equals(checkUserId2) && !checkStatus.equals(GeneralStatus.checkPass.status)) {
					updateMapOther.remove(News.STATUS);
					break;
				}
			}
		}
		queryMap.put("checkuser.userId", commonParameters.getUserId());
		updateMapOther.put(News.UUTIME, DateUtil.getCurrentDateTime());
		updateMapOther.put("checkuser.$.status", checkMap.get(Checks.STATUS));
		updateMapOther.put("checkuser.$.opinion", checkMap.get(Checks.CHECKINFO));
		updateMapOther.put("checkuser.$.uutime", DateUtil.getCurrentDateTime());
		listMap.add(checkMap);
		updateMapEach.put(QueryOperators.EACH, listMap);
		updateMapAdd.put(News.CHECK, updateMapEach);
		updateMap.put(QueryOperators.ADDTOSET, updateMapAdd);
		updateMap.put(QueryOperators.SET, updateMapOther);
		long num = newsDao.updateOne(queryMap, updateMap);
		
		
		
		
		if (num > Constants.ZERO) {
			//发送微信任务
			Map<String,Object>newsMap=newsDao.findOne(newsId);
			Object totalStatus=newsMap.get(News.STATUS);
			logger.info("totalStatus:"+totalStatus);
			List<Map<String, Object>> listCheckUser = (List<Map<String, Object>>) newsMap.get(News.CHECKUSER);
			//0---都已经审批 1---还有未审批
			String isCompleCheck="0";
			for (Map<String, Object> mapUser : listCheckUser) {
				logger.info("mapUser:"+mapUser.toString());
				if(mapUser.get(News.STATUS).equals(GeneralStatus.checkWait.status)){
					logger.info("isCompleCheck inner"+isCompleCheck);

					isCompleCheck="1";
					break;
				}
			}
			logger.info("isCompleCheck out"+isCompleCheck);
			if(!"3".equals(totalStatus)&&"0".equals(isCompleCheck)){
				String state="";
				if("5".equals(String.valueOf(newsMap.get(News.STATUS)))||"8".equals(String.valueOf(newsMap.get(News.STATUS)))){
					state="审核通过";
				}else if("6".equals(String.valueOf(newsMap.get(News.STATUS)))){
					state="审核打回";
				}
				weChatService.pushCheckWx(newsMap);
			}
			executeSuccess(resObj);
			//判断审核整体状态是否为通过，通过则给通联人发送消息
			if (null != updateMapOther.get(News.STATUS)) {
				Integer status = Integer.valueOf(String.valueOf(updateMapOther.get(News.STATUS)));
				if (status.equals(GeneralStatus.checkPass.status) && null != mapNews.get(News.SHAREUSER)) {
					//记录通联人
					List<Map<String, Object>> listShareUser = (List<Map<String, Object>>) mapNews.get(News.SHAREUSER);
					//给移动端发送消息
					pushMessageToPhone(listShareUser, "您有可推送的任务");
				
					
				}
			}
		} else {
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(Checks.ID, new ObjectId(String.valueOf(checkMap.get(Checks.ID))));
			checkDao.deleteOne(filter);
			logger.warn("添加通联表数据失败！" + updateMap);
			resObj.setCode(GeneralStatus.update_error.status);
			resObj.setMessage(GeneralStatus.update_error.enDetail);
		}
		return resObj;
	}
	
	/**
	 * 更新审核记录到通联表中
	 * @param resObj 返回结果集
	 * @param commonParameters 公共参数
	 * @param newsId 通联id
	 * @param checkMap 审核信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ResponseObject updateNewsToCheck(ResponseObject resObj,CommonParameters commonParameters, String newsId,Map<String, Object> checkMap){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		Map<String, Object> updateMap = new HashMap<String, Object>();
		Map<String, Object> updateMapEach = new HashMap<String, Object>();
		Map<String, Object> updateMapAdd = new HashMap<String, Object>();
		Map<String, Object> updateMapOther = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		queryMap.put(News.ID, new ObjectId(newsId));
		Map<String, Object> mapNews = newsDao.findOne(newsId);
		if (null == mapNews || mapNews.isEmpty() || null == mapNews.get(News.CHECKUSER)) {
			deleteCheck(resObj, checkMap);
			return resObj;
		}
		//记录审核人
		List<String> listCheckUsers = new ArrayList<String>();
		List<Map<String, Object>> checkUser = (List<Map<String, Object>>) mapNews.get(News.CHECKUSER);
		List<Map<String, Object>> checkUserNew = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> mapCheckUser : checkUser) {
			mapCheckUser.put("status", checkMap.get(Checks.STATUS));
			mapCheckUser.put("opinion", checkMap.get(Checks.CHECKINFO));
			mapCheckUser.put("uutime", checkMap.get(Checks.CTIME));
			mapCheckUser.put("readstatus", Constants.ZERO);
			checkUserNew.add(mapCheckUser);
			listCheckUsers.add(String.valueOf(mapCheckUser.get(NewsVo.USERID)));
		}
		updateMapOther.put(News.UUTIME, DateUtil.getCurrentDateTime());
		updateMapOther.put(News.STATUS, checkMap.get(Checks.STATUS));
		updateMapOther.put(News.CHECKUSER, checkUserNew);
		listMap.add(checkMap);
		updateMapEach.put(QueryOperators.EACH, listMap);
		updateMapAdd.put(News.CHECK, updateMapEach);
		updateMap.put(QueryOperators.ADDTOSET, updateMapAdd);
		updateMap.put(QueryOperators.SET, updateMapOther);
		long num = newsDao.updateOne(queryMap, updateMap);
		if (num > Constants.ZERO) {
			executeSuccess(resObj);
			//给移动端发送消息
			if (null != listCheckUsers && listCheckUsers.size() > 0) {
				String[] sUser = (String[])listCheckUsers.toArray(new String[listCheckUsers.size()]);
				JPushUtil.pushObjectToPhone("您有待审核的任务", sUser);
			}
		} else {
			logger.warn("添加通联表数据失败！" + updateMap);
			deleteCheck(resObj, checkMap);
		}
		return resObj;
	}
	
	private void deleteCheck(ResponseObject resObj,Map<String, Object> checkMap){
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(Checks.ID, new ObjectId(String.valueOf(checkMap.get(Checks.ID))));
		checkDao.deleteOne(filter);
		resObj.setCode(GeneralStatus.update_error.status);
		resObj.setMessage(GeneralStatus.update_error.enDetail);
	}
	
	//给移动端发送消息
	private void pushMessageToPhone(List<Map<String, Object>> listShareUser, String content){
		List<String> listShareUsers = new ArrayList<String>();
		if (null != listShareUser && listShareUser.size() > 0) {
			for (Map<String, Object> mapUser : listShareUser) {
				listShareUsers.add(String.valueOf(mapUser.get(NewsVo.USERID)));
			}
		}else{
			return;
		}
		if (null != listShareUsers && listShareUsers.size() > 0) {
			String[] sUser = (String[])listShareUsers.toArray(new String[listShareUsers.size()]);
			JPushUtil.pushObjectToPhone(content, sUser);
		}
	}
	
}
