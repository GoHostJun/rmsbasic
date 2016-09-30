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
import com.cdvcloud.rms.common.vo.NoticeVo;
import com.cdvcloud.rms.dao.INoticeDao;
import com.cdvcloud.rms.dao.INoticeTypeDao;
import com.cdvcloud.rms.domain.Notice;
import com.cdvcloud.rms.domain.NoticeType;
import com.cdvcloud.rms.service.INoticeService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

/**
 * 公告业务处理
 * 
 * @author huangaigang
 * 
 */
@Service
public class NoticeServiceImpl extends BasicService implements INoticeService {
	private static final Logger logger = Logger.getLogger(NoticeServiceImpl.class);
	@Autowired
	private INoticeDao noticeDao;
	@Autowired
	private INoticeTypeDao noticeTypeDao;
	@Override
	public ResponseObject createNotice(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		if (StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICETYPEID))||StringUtil.isEmpty(mapJson.get(NoticeVo.TITLE)) || StringUtil.isEmpty(mapJson.get(NoticeVo.CONTENT))) {
			return parameterError("title,content:" + strJson);
		}
		String title = String.valueOf(mapJson.get(NoticeVo.TITLE));
		String content = String.valueOf(mapJson.get(NoticeVo.CONTENT));
		String contentHtml = String.valueOf(mapJson.get(NoticeVo.CONTENTHTML));
		String noticeTypeId = String.valueOf(mapJson.get(NoticeVo.NOTICETYPEID));
		Map<String,Object> noticeTypeMap=noticeTypeDao.findOne(noticeTypeId);
		// 创建公告对象
		Map<String, Object> noticeMap = createNoticeObj(commonParameters, title, content,contentHtml, Constants.ZERO, Constants.ZERO,noticeTypeMap);
		if (!StringUtil.isEmpty(noticeMap)) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject findNoticeAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, Notice.NOTICETITLE, Notice.CTIME);
		whereMap.remove(Notice.CUSERID);//移除创建人
		whereMap.remove(Notice.DEPARTMENTID);//移除部门
		//判断是否包含类型，包含则加入条件查询
		String noticeTypeId = String.valueOf(mapJson.get(NoticeVo.NOTICETYPEID));
		if (!StringUtil.isEmpty(noticeTypeId)) {
			whereMap.put("noticetype.uniquename", noticeTypeId);
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(Notice.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		Map<String, Object> mapBack = getNoticeMap();
		List<Map<String, Object>> results = noticeDao.find(sortMap, whereMap, mapBack,currentPage, pageNum);
		// 获取总数
		long totalRecord = noticeDao.countNotice(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	private Map<String, Object> getNoticeMap() {
		Map<String, Object> mapBack = new HashMap<String, Object>();
		mapBack.put(Notice.CTIME, 1);
		mapBack.put(Notice.NOTICETITLE, 1);
		mapBack.put(Notice.NOTICECONTENT, 1);
		mapBack.put(Notice.CUSENAME, 1);
		mapBack.put(Notice.CUSERID, 1);
		mapBack.put(Notice.NOTICETYPE, 1);
		return mapBack;
	}

	@Override
	public ResponseObject findNoticeById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		String noticeId = null;
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICEID))) {
			noticeId = String.valueOf(mapJson.get(NoticeVo.NOTICEID));
		} else {
			return parameterError("缺少公告noticeId," + mapJson);
		}
		Map<String, Object> noticeMap = noticeDao.findOne(noticeId);
		executeSuccess(resObj,noticeMap);
		return resObj;
	}

	@Override
	public ResponseObject deleteNotice(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		String noticeId = String.valueOf(mapJson.get(NoticeVo.NOTICEID));
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICEID)) && !StringUtil.isEmpty(noticeId)) {
			whereMap.put(Notice.ID, new ObjectId(noticeId));
		} else {
			return parameterError("缺少公告noticeId," + mapJson);
		}
		long resNum = noticeDao.deleteOne(whereMap);
		if (resNum > 0) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject updateNotice(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICEID))) {
			String noticeId = String.valueOf(mapJson.get(NoticeVo.NOTICEID));
			whereMap.put(Notice.ID, new ObjectId(noticeId));
		} else {
			return parameterError("缺少公告noticeId," + mapJson);
		}
		Map<String, Object> updateMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.TITLE))) {
			String title = String.valueOf(mapJson.get(NoticeVo.TITLE));
			updateMap.put(Notice.NOTICETITLE, title);
		} else {
			return parameterError("缺少公告title," + mapJson);
		}
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.CONTENT))) {
			String content = String.valueOf(mapJson.get(NoticeVo.CONTENT));
			String contentHtml = String.valueOf(mapJson.get(NoticeVo.CONTENTHTML));
			updateMap.put(Notice.NOTICECONTENT, content);
			updateMap.put(Notice.NOTICECONTENTHTML, contentHtml);
		} else {
			return parameterError("缺少公告content," + mapJson);
		}
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICETYPEID))) {
			String noticeTypeId = String.valueOf(mapJson.get(NoticeVo.NOTICETYPEID));
			Map<String,Object> noticeTypeMap=noticeTypeDao.findOne(noticeTypeId);
			updateMap.put(Notice.NOTICETYPE, noticeTypeMap);
		} else {
			return parameterError("缺少公告noticeTypeId," + mapJson);
		}
		updateMap.put(Notice.UUTIME, DateUtil.getCurrentDateTime());
		long resNum = noticeDao.updateManyBySet(whereMap, updateMap, true);
		if (resNum > 0) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	private Map<String, Object> createNoticeObj(CommonParameters commonParameters, String title, String content, String contentHtml, int status, int type,Map<String,Object>noticetype) {
		Map<String, Object> noticeMap = new HashMap<String, Object>();
		noticeMap.put(Notice.NOTICETITLE, title);
		noticeMap.put(Notice.NOTICECONTENT, content);
		noticeMap.put(Notice.NOTICECONTENTHTML, contentHtml);
		noticeMap.put(Notice.CUSERID, commonParameters.getUserId());
		noticeMap.put(Notice.CUSENAME, commonParameters.getUserName());
		noticeMap.put(Notice.CTIME, DateUtil.getCurrentDateTime());
		noticeMap.put(Notice.UUTIME, DateUtil.getCurrentDateTime());
		noticeMap.put(Notice.CONSUMERID, commonParameters.getCompanyId());
		noticeMap.put(Notice.CONSUMERNAME, commonParameters.getConsumerName());
		noticeMap.put(Notice.DEPARTMENTID, commonParameters.getDepartmentId());
		noticeMap.put(Notice.STATUS, status);
		noticeMap.put(Notice.TYPE, type);
		noticeMap.put(Notice.NOTICETYPE, noticetype);
		String noticeId = noticeDao.insertNotice(noticeMap);
		if (null != noticeId) {
			noticeMap.put(Notice.ID, noticeId);
			return noticeMap;
		}
		logger.error("创建公告记录失败！noticeMap=" + noticeMap);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject deleteNotices(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICEIDS))) {
			return parameterError("noticeIds,"+mapJson);
		}
		List<String> noticeIds = (List<String>) mapJson.get(NoticeVo.NOTICEIDS);
		if (Constants.ONE > noticeIds.size()) {
			return parameterError("noticeIds,"+mapJson);
		}
		for (String noticeId : noticeIds) {
			whereMap.put(Notice.ID, new ObjectId(noticeId));
			noticeDao.deleteOne(whereMap);
		}
		executeSuccess(resObj);
		return resObj;
	}

	@Override
	public ResponseObject createNoticeType(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		if (StringUtil.isEmpty(mapJson.get(NoticeVo.NAME)) || StringUtil.isEmpty(mapJson.get(NoticeVo.STYLE))) {
			return parameterError("name,style:" + strJson);
		}
		String title = String.valueOf(mapJson.get(NoticeVo.NAME));
		String content = String.valueOf(mapJson.get(NoticeVo.STYLE));
		String uniqueName = String.valueOf(mapJson.get(NoticeVo.UNIQUENAME));
		if (StringUtil.isEmpty(uniqueName) ) {
			return parameterError("缺少唯一标识uniqueName:" + strJson);
		}
		// 创建公告类型对象
		Map<String, Object> noticeTypeMap = createNoticeTypeObj(commonParameters, title, content,uniqueName,Constants.ZERO);
		if (!StringUtil.isEmpty(noticeTypeMap)) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject findNoticeTypeAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, NoticeType.NOTICENAME, NoticeType.CTIME);
		whereMap.remove(NoticeType.CUSERID);//移除创建人
		whereMap.remove(NoticeType.DEPARTMENTID);//移除部门
		// 排序参数
		Map<String, Object> sortMap = getSortParam(NoticeType.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = noticeTypeDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = noticeTypeDao.countNoticeType(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject updateNoticeType(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICETYPEID))) {
			String noticeTypeId = String.valueOf(mapJson.get(NoticeVo.NOTICETYPEID));
			whereMap.put(NoticeType.ID, new ObjectId(noticeTypeId));
		} else {
			return parameterError("缺少公告类型noticeTypeId," + mapJson);
		}
		Map<String, Object> updateMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.NAME))) {
			String title = String.valueOf(mapJson.get(NoticeVo.NAME));
			updateMap.put(NoticeType.NOTICENAME, title);
		} else {
			return parameterError("缺少公告类型名称name," + mapJson);
		}
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.STYLE))) {
			String style = String.valueOf(mapJson.get(NoticeVo.STYLE));
			updateMap.put(NoticeType.NOTICESTYLE, style);
		} else {
			return parameterError("缺少公告类型样式style," + mapJson);
		}
		updateMap.put(NoticeType.UUTIME, DateUtil.getCurrentDateTime());
		long resNum = noticeTypeDao.updateManyBySet(whereMap, updateMap, true);
		if (resNum > 0) {
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteNoticeType(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		String noticeTypeId = String.valueOf(mapJson.get(NoticeVo.NOTICETYPEID));
		if (!StringUtil.isEmpty(noticeTypeId)) {
			whereMap.put(NoticeType.ID, new ObjectId(noticeTypeId));
		} else {
			return parameterError("缺少公告类型noticeTypeId," + mapJson);
		}
		long resNum = noticeTypeDao.deleteOne(whereMap);
		if (resNum > 0) {
			executeSuccess(resObj);
		}
		return resObj;
	}
	
	@Override
	public ResponseObject findNoticeTypeById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String noticeTypeId = null;
		if (!StringUtil.isEmpty(mapJson.get(NoticeVo.NOTICETYPEID))) {
			noticeTypeId = String.valueOf(mapJson.get(NoticeVo.NOTICETYPEID));
		} else {
			return parameterError("缺少公告类型noticeTypeId," + mapJson);
		}
		Map<String, Object> noticeTypeMap = noticeTypeDao.findOne(noticeTypeId);
		executeSuccess(resObj,noticeTypeMap);
		return resObj;
	}
	
	private Map<String, Object> createNoticeTypeObj(CommonParameters commonParameters, String name, String style,String uniqueName, int status) {
		Map<String, Object> noticeTypeMap = new HashMap<String, Object>();
		noticeTypeMap.put(NoticeType.NOTICENAME, name);
		noticeTypeMap.put(NoticeType.NOTICESTYLE, style);
		noticeTypeMap.put(NoticeType.UNIQUENAME, uniqueName);
		noticeTypeMap.put(NoticeType.CUSERID, commonParameters.getUserId());
		noticeTypeMap.put(NoticeType.CUSENAME, commonParameters.getUserName());
		noticeTypeMap.put(NoticeType.CTIME, DateUtil.getCurrentDateTime());
		noticeTypeMap.put(NoticeType.UUTIME, DateUtil.getCurrentDateTime());
		noticeTypeMap.put(NoticeType.CONSUMERID, commonParameters.getCompanyId());
		noticeTypeMap.put(NoticeType.CONSUMERNAME, commonParameters.getConsumerName());
		noticeTypeMap.put(NoticeType.DEPARTMENTID, commonParameters.getDepartmentId());
		noticeTypeMap.put(NoticeType.STATUS, status);
		String noticeTypeId = noticeTypeDao.insertNoticeType(noticeTypeMap);
		if (null != noticeTypeId) {
			noticeTypeMap.put(NoticeType.ID, noticeTypeId);
			return noticeTypeMap;
		}
		logger.error("创建公告类型记录失败！noticeTypeMap=" + noticeTypeMap);
		return null;
	}

}
