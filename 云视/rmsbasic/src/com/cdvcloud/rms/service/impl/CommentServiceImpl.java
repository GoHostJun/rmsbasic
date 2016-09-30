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
import com.cdvcloud.rms.common.vo.MessageVo;
import com.cdvcloud.rms.common.vo.NewsVo;
import com.cdvcloud.rms.dao.ICommentDao;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.domain.Comment;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.service.ICommentService;
import com.cdvcloud.rms.service.IMessageService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class CommentServiceImpl extends BasicService implements ICommentService {
	private static final Logger logger = Logger.getLogger(CommentServiceImpl.class);
	@Autowired
	private ICommentDao commentDao;
	@Autowired
	private INewsDao newsDao;
	@Autowired
	private IMessageService messageService;

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject createComment(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		
		// 获取业务参数
		String newsId = docJson.getString(NewsVo.NEWSID);
		String pId = docJson.getString(NewsVo.PID);
		String content = docJson.getString(NewsVo.CONTENT);
		String userId = commonParameters.getUserId();
		String userName = commonParameters.getUserName();
		String consumerid = commonParameters.getCompanyId();
		String consumername = commonParameters.getConsumerName();
		String userUrl = "";
		List<Object> listDepartment = commonParameters.getDepartmentId();
		// 添加评论对象
		Document document = new Document();
		document.append(Comment.PID, pId).append(Comment.COMCONTENT, content).append(Comment.NEWSID, newsId).append(Comment.CUSENAME,userName)
				.append(Comment.STATUS, 0).append(Comment.TYPE, 0).append(Comment.CUSERID, userId)
				.append(Comment.USERURL, userUrl).append(Comment.CTIME, DateUtil.getCurrentDateTime())
				.append(Comment.UUTIME, DateUtil.getCurrentDateTime()).append(Comment.DEPARTMENTID, listDepartment)
				.append(Comment.CONSUMERID, consumerid).append(Comment.CONSUMERNAME, consumername);
		String commentId = commentDao.insertDocNews(document);
		if (!StringUtil.isEmpty(commentId)) {
			document.append(Comment.ID, commentId);
			// 添加评论对象到通联表
			Map<String, Object> filterMap = new HashMap<String, Object>();
			Map<String, Object> updateMap = new HashMap<String, Object>();
			Map<String, Object> commentMap = new HashMap<String, Object>();
			Map<String, Object> shareMap1 = new HashMap<String, Object>();
			Map<String, Object> uTimeMap = new HashMap<String, Object>();
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			filterMap.put(News.ID, new ObjectId(newsId));
			uTimeMap.put(News.UUTIME, DateUtil.getCurrentDateTime());
			listMap.add(document);
			commentMap.put(QueryOperators.EACH, listMap);
			shareMap1.put(News.COMMENTS, commentMap);
			updateMap.put(QueryOperators.ADDTOSET, shareMap1);
			updateMap.put(QueryOperators.SET, uTimeMap);
			long num = newsDao.updateOne(filterMap, updateMap);
			if (num > Constants.ZERO) {
				sendMessage(commonParameters, newsId);
				executeSuccess(resObj);
			} else {
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put(Comment.ID, new ObjectId(commentId));
				commentDao.deleteOne(filter);
				logger.warn("添加通联表数据失败！" + updateMap);
				resObj.setCode(GeneralStatus.update_error.status);
				resObj.setMessage(GeneralStatus.update_error.enDetail);
			}
		}
		return resObj;
	}
	
	@SuppressWarnings("unchecked")
	private void sendMessage(CommonParameters commonParameters,String newsId){
		Map<String, Object> mapNews = newsDao.findOne(newsId);
		if (null != mapNews) {
			String userId = commonParameters.getUserId();
			String msgUserId = String.valueOf(mapNews.get(News.CUSERID));
			if (!userId.equals(msgUserId)) {
				//发消息
				Map<String, Object> msgMap = getMessageMap(mapNews.get(News.TITLE),"有新评论", newsId,msgUserId);
				messageService.createMessageObj(msgMap);
			}
			if (null != mapNews.get(News.CHECKUSER)) {
				List<Map<String, Object>> listCheckUser = (List<Map<String, Object>>) mapNews.get(News.CHECKUSER);
				for (Map<String, Object> mapUser : listCheckUser) {
					String msgCheckUserId = String.valueOf(mapUser.get("userId"));
					if (!StringUtil.isEmpty(msgCheckUserId) && !userId.equals(msgCheckUserId)) {
						//发消息
						Map<String, Object> msgMap = getMessageMap(mapNews.get(News.TITLE),"有新评论", newsId,msgCheckUserId);
						messageService.createMessageObj(msgMap);
					}
				}
			}
			if (null != mapNews.get(News.SHAREUSER)) {
				List<Map<String, Object>> listShareUser = (List<Map<String, Object>>) mapNews.get(News.SHAREUSER);
				for (Map<String, Object> mapUser : listShareUser) {
					String msgShareUserId = String.valueOf(mapUser.get("userId"));
					if (!StringUtil.isEmpty(msgShareUserId) && !userId.equals(msgShareUserId)) {
						//发消息
						Map<String, Object> msgMap = getMessageMap(mapNews.get(News.TITLE),"有新评论", newsId,msgShareUserId);
						messageService.createMessageObj(msgMap);
					}
				}
			}
		}else{
			logger.warn("获取通联表数据失败！newsId" + newsId);
		}
	}
	
	private Map<String, Object> getMessageMap(Object title,Object content,String newsId,String msgUserId){
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put(MessageVo.MSGUSERID, msgUserId);
		mapMessage.put(MessageVo.NEWSID, newsId);
		mapMessage.put(MessageVo.TYPE, 0);
		mapMessage.put(MessageVo.NEWSTITLE, title);
		mapMessage.put(MessageVo.TITLE, title);
		mapMessage.put(MessageVo.CONTENT, content);
		return mapMessage;
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public ResponseObject findCommentAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		
		// 获取业务参数
		String newsId = docJson.getString(NewsVo.NEWSID);
		Document newsDoc = newsDao.findOneDocument(newsId);
		if (!newsDoc.isEmpty()) {
			List<Document> comments = (List<Document>) newsDoc.get(News.COMMENTS);
			executeSuccess(resObj, comments);
		} else {
			logger.warn("获取通联评论失败！newsId：" + newsId);
		}
		return resObj;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject findCommentById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		
		// 获取业务参数
		String commentId = docJson.getString(NewsVo.COMMENTID);
		Document documentDoc = commentDao.findOneDocument(commentId);
		if (!documentDoc.isEmpty()) {
			documentDoc.put(Comment.ID, String.valueOf(documentDoc.getObjectId(Comment.ID)));
			executeSuccess(resObj, documentDoc);
		} else {
			logger.warn("获取通联评论失败！newsId：" + commentId);
		}
		return resObj;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject deleteComment(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		
		// 获取业务参数
		String commentId = docJson.getString(NewsVo.COMMENTID);
		Document commentDoc = commentDao.findOneDocument(commentId);
		if (!commentDoc.isEmpty()) {
			commentDoc.put(Comment.ID, String.valueOf(commentDoc.getObjectId(Comment.ID)));
			Map<String, Object> filter = new  HashMap<String, Object>();
			Map<String, Object> update = new  HashMap<String, Object>();
			filter.put(News.ID, new ObjectId(commentDoc.getString(Comment.NEWSID)));
			update.put(QueryOperators.PULL, new Document().append(News.COMMENTS, commentDoc));
			long result = newsDao.updateMany(filter, update);
			if (result > Constants.ZERO) {
				filter.put(Comment.ID, new ObjectId(commentId));
				result = commentDao.deleteOne(filter);
				if (result > Constants.ZERO) {
					executeSuccess(resObj);
				}else{
					logger.warn("删除评论表失败！commentDoc：" + commentDoc);
				}
			}else{
				logger.warn("删除通联里的评论失败！commentDoc：" + commentDoc);
			}
		} else {
			logger.warn("获取通联评论失败！newsId：" + commentId);
		}
		return resObj;
	}

	@Override
	public ResponseObject updateComment(CommonParameters commonParameters, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject getCommentInfo(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, null, Comment.CTIME);
		// 获取业务参数
		String newsId = String.valueOf(mapJson.get(NewsVo.NEWSID));
		String userId = String.valueOf(mapJson.get(NewsVo.USERID));
		if (!StringUtil.isEmpty(newsId)) {
			whereMap.put(Comment.NEWSID, newsId);
		}
		if (!StringUtil.isEmpty(userId)) {
			whereMap.remove(Comment.CUSERID);
			whereMap.put(Comment.TOUSERID, userId);
		}
		// 排序参数
		Map<String, Object> sortMap = getSortParam(Comment.CTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = commentDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = commentDao.countComment(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

}
