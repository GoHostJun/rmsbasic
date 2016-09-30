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
import com.cdvcloud.rms.common.vo.CatalogueVo;
import com.cdvcloud.rms.common.vo.MediaVo;
import com.cdvcloud.rms.dao.IFieldNewsDao;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.IMessageDao;
import com.cdvcloud.rms.dao.IPresentationDao;
import com.cdvcloud.rms.domain.BasicObject;
import com.cdvcloud.rms.domain.Catalogue;
import com.cdvcloud.rms.domain.FieldNews;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.Message;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.service.IFieldnewsService;
import com.cdvcloud.rms.service.IPresentationService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.mongodb.BasicDBList;
@Service
public class FieldnewsServiceImpl extends BasicService implements IFieldnewsService {
	private static final Logger logger = Logger.getLogger(FieldnewsServiceImpl.class);

	@Autowired
	private IFieldNewsDao fieldNewsDao;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	private IPresentationDao materialDao;
	@Autowired
	private IPresentationService materialService;
	@Autowired
	private IMessageDao messageDao;
	@Override
	public ResponseObject findFieldnewsAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, FieldNews.TITLE, FieldNews.UUTIME);
		whereMap.remove(BasicObject.CUSERID);
		whereMap.put(FieldNews.SHOW, "0");
		String userId = commonParameters.getUserId();
		whereMap.put(FieldNews.PARTICIPANTS+".userId", userId);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(FieldNews.UUTIME, Constants.SZERO);
		sortMap.putAll(getSortParam(FieldNews.TOPTIME, Constants.SZERO));
		sortMap.putAll(getSortParam(FieldNews.TOP, Constants.SZERO));
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		// 获取通联列表，区分手机端和pc端
		List<Map<String, Object>> results = null;
//		String appCode = commonParameters.getAppCode();
		//		if (appCode.equals(Constants.APPCODEPHONE)) {
		//			Map<String, Object> mapBack = getNewsMapPhone();
		//			results = fieldNewsDao.find(sortMap, whereMap, mapBack, currentPage, pageNum);
		//		} else {
		results = fieldNewsDao.find(sortMap, whereMap, currentPage, pageNum);
		//		}
		if(null!=results&&results.size()>0){
			// 获取总数
			long totalRecord = fieldNewsDao.countNews(whereMap);
			Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
			resObj.setCode(GeneralStatus.success.status);
			resObj.setMessage(GeneralStatus.success.enDetail);
			executeSuccess(resObj, resMap);
		}else{
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject findFieldnewsByUser(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, FieldNews.TITLE, FieldNews.UUTIME);
		whereMap.put(FieldNews.SHOW, "0");
		// 排序参数
		Map<String, Object> sortMap = getSortParam(FieldNews.UUTIME, Constants.SZERO);
		sortMap.putAll(getSortParam(FieldNews.TOPTIME, Constants.SZERO));
		sortMap.putAll(getSortParam(FieldNews.TOP, Constants.SZERO));
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		// 获取通联列表，区分手机端和pc端
		List<Map<String, Object>> results = null;
//		String appCode = commonParameters.getAppCode();
		//		if (appCode.equals(Constants.APPCODEPHONE)) {
		//			Map<String, Object> mapBack = getNewsMapPhone();
		//			results = fieldNewsDao.find(sortMap, whereMap, mapBack, currentPage, pageNum);
		//		} else {
		results = fieldNewsDao.find(sortMap, whereMap, currentPage, pageNum);
		//		}
		// 获取总数
		long totalRecord = fieldNewsDao.countNews(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findFieldnewsByShare(CommonParameters commonParameters, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject findFieldnewsById(CommonParameters commonParameters, String strJson) {
		logger.info("查看外场素材findFieldnewsById="+strJson);
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		String userId = commonParameters.getUserId();
		String username = commonParameters.getUserName();
		// 获取业务参数,并进行相关业务操作
		String newsId = String.valueOf(mapJson.get(FieldNews.FIELDNEWSID));
		// 获取新闻外场
		Map<String, Object> results = null;
		String appCode = commonParameters.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {
			Map<String, Object> mapBack = getNewsMapPhone();
			results = fieldNewsDao.findOne(newsId, mapBack);
		} else {
			results = fieldNewsDao.findOne(newsId);
		}
		logger.info("查询外场素材结果="+results);
		//这里要添加查看人的信息到参与人里
		Map<String, Object> userMap =  new HashMap<String, Object>();
		userMap.put("userName", username);
		userMap.put("userId", userId);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list.add(userMap);
		logger.info("外场素材需要添加的用户信息="+list);
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(FieldNews.ID, new ObjectId(newsId));
		Map<String, Object> set = new HashMap<String, Object>();
		Map<String, Object> optmap = new HashMap<String, Object>();//第一个操作符
		Map<String, Object> optmap1 = new HashMap<String, Object>();//第一个操作符
		optmap1.put(QueryOperators.EACH, list);
		optmap.put(FieldNews.PARTICIPANTS,optmap1);
		set.put(QueryOperators.ADDTOSET, optmap);//update需要更新的
		long index = fieldNewsDao.updateMany(filter, set);
		logger.info("外场素材更新用户结果="+index);
		if(index>0){
			Map<String, Object> update = new HashMap<String, Object>();
			update.put(FieldNews.SHOW, "0");
			long updateindex = fieldNewsDao.updateOneBySet(filter, update, true);
			logger.info("更新新闻外场表 结果："+updateindex);
		}else{

		}
		//消息表里存
		Map<String, Object> userMap_msg =  new HashMap<String, Object>();
		userMap_msg.put("userName", username);
		userMap_msg.put("userId", userId);
		List<Map<String, Object>> list_msg = new ArrayList<Map<String,Object>>();
		list_msg.add(userMap_msg);
		Map<String, Object> filter_msg = new HashMap<String, Object>();
		filter_msg.put(Message.NEWSID, newsId);
		Map<String, Object> set_msg = new HashMap<String, Object>();
		Map<String, Object> optmap_msg = new HashMap<String, Object>();//第一个操作符
		Map<String, Object> optmap1_msg = new HashMap<String, Object>();//第一个操作符
		optmap1_msg.put(QueryOperators.EACH, list_msg);
		optmap_msg.put(Message.PARTICIPANTS,optmap1_msg);
		set_msg.put(QueryOperators.ADDTOSET, optmap_msg);//update需要更新的
		long index_msg = messageDao.updateMany(filter_msg, set_msg);
		if(index_msg>0){
			logger.info("");
		}else{

		}
		if(null!=results&&results.size()>0){
			resObj.setCode(GeneralStatus.success.status);
			resObj.setMessage(GeneralStatus.success.enDetail);
			executeSuccess(resObj, results);
		}
		return resObj;
	}

	@Override
	public ResponseObject addFieldnewsUser(CommonParameters commonParameters, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject cancelFieldnews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);

		// 获取业务参数
		String ids = docJson.getString("ids");
		String[] id = ids.split(",");
		for (String string : id) {
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("_id",new ObjectId(string.trim()));
			fieldNewsDao.deleteOne(filter);
		}
		executeSuccess(resObj);

		return resObj;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject copyFieldnews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);

		// 获取业务参数
		String newsId = docJson.getString("id");
		// 获取新闻通联
		Map<String, Object> newsMap = fieldNewsDao.findOne(newsId);
		if (null == newsMap || newsMap.isEmpty()) {
			return super.queryError("没有获取到原新闻外场！newsId+" + newsId);
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
			// 复制文稿里的素材到自己的素材库里
			copyMedia(commonParameters, newsMap, materialIds);
			List<String> mediaids = getMaterialIds(newsMap);
			materialService.addMediaToCataids(mediaids, materialIds);
			resObj.setCode(GeneralStatus.success.status);
			resObj.setMessage(GeneralStatus.success.enDetail);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "拷贝新闻外场", "拷贝一条新外场新闻《" + newsMap.get(News.TITLE) + "》"));
		} else {
			logger.warn("插入文稿失败！cateId+" + cateId + ",newsMap:" + newsMap);
		}
		return resObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject updateFieldnews(CommonParameters update, String strJson) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> whereMap = new HashMap<String, Object>();

		//		List<String> ids =  (List<String>)mapJson.get(CatalogueVo.IDS);
		List<String> ids =  new ArrayList<String>();
		String id =  String.valueOf(mapJson.get(CatalogueVo.ID));
		ids.add(id);
		//存放需要更新的媒体id（媒体更新上文稿id）
		List<String> addMediaids = new ArrayList<String>();
		List<String> delMediaids = new ArrayList<String>();

		if (null != id && !"".equals(id)) {
			BasicDBList basicDBList = new BasicDBList();
			//循环选择文稿id
			for (String materialId : ids) {
				basicDBList.add(new ObjectId(materialId));
				//查询当前文稿里的信息
				Map<String, Object> map =  fieldNewsDao.findOne(materialId);
				//获取视频新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.VIDEOIDS))){//判断接口过来的视频id是否存在
					//视频id转换list
					List<String> newVideos = new ArrayList<String>();
					List<Map<String, Object>> videoids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.VIDEOIDS);
					for (Map<String, Object> map2 : videoids) {
						newVideos.add(String.valueOf(map2.get("id")));
					}
					//判断数据库里是否有视频
					if(!StringUtil.isEmpty(map.get(Catalogue.VIDEOS))){
						List<Map<String, Object>> videosMap = (List<Map<String, Object>>)map.get(Catalogue.VIDEOS);
						//获取数据库存储的视频id集合
						List<String> oldVideos = new ArrayList<String>();
						for (Map<String, Object> map2 : videosMap) {
							oldVideos.add(String.valueOf(map2.get(Media.ID)));
						}
						//						//获取新增视频id集合
						//						addMediaids.addAll(this.listScreen(newVideos, oldVideos));
						//						//获取删除的视频id集合
						//						delMediaids.addAll(this.listScreen(oldVideos,newVideos));
						//获取新增视频id集合
						addMediaids.addAll(this.listScreen(oldVideos,newVideos));
						//获取删除的视频id集合
						delMediaids.addAll(this.listScreen(newVideos, oldVideos));
					}else{
						addMediaids.addAll(newVideos);//如果数据库没有视频集合则是新建
					}
				}
				//获取音频新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AUDIOIDS))){
					List<String> newAudios = new ArrayList<String>();
					List<Map<String, Object>> audioids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.AUDIOIDS);
					for (Map<String, Object> map2 : audioids) {
						newAudios.add(String.valueOf(map2.get("id")));
					}
					if(!StringUtil.isEmpty(map.get(Catalogue.AUDIOS))){
						List<Map<String, Object>> audioMap = (List<Map<String, Object>>)map.get(Catalogue.AUDIOS);
						List<String> oldAudios = new ArrayList<String>();
						for (Map<String, Object> map2 : audioMap) {
							oldAudios.add(String.valueOf(map2.get(Media.ID)));
						}
						addMediaids.addAll(this.listScreen(oldAudios,newAudios));
						delMediaids.addAll(this.listScreen(newAudios, oldAudios));
					}else{
						addMediaids.addAll(newAudios);
					}
				}
				//获取图片新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.PICIDS))){
					List<String> newPics = new ArrayList<String>();
					List<Map<String, Object>> picids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.PICIDS);
					for (Map<String, Object> map2 : picids) {
						newPics.add(String.valueOf(map2.get("id")));
					}
					if(!StringUtil.isEmpty(map.get(Catalogue.PICS))){
						List<Map<String, Object>> picMap = (List<Map<String, Object>>)map.get(Catalogue.PICS);
						List<String> oldPics = new ArrayList<String>();
						for (Map<String, Object> map2 : picMap) {
							oldPics.add(String.valueOf(map2.get(Media.ID)));
						}
						delMediaids.addAll(this.listScreen(newPics, oldPics));
						addMediaids.addAll(this.listScreen(oldPics,newPics));
					}else{
						addMediaids.addAll(newPics);
					}
				}
				//获取文本新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.DOCIDS))){
					List<String> newDocs = new ArrayList<String>();
					List<Map<String, Object>> docids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.DOCIDS);
					for (Map<String, Object> map2 : docids) {
						newDocs.add(String.valueOf(map2.get("id")));
					}
					if(!StringUtil.isEmpty(map.get(Catalogue.DOCS))){
						List<Map<String, Object>> docsMap = (List<Map<String, Object>>)map.get(Catalogue.DOCS);
						List<String> oldDocs = new ArrayList<String>();
						for (Map<String, Object> map2 : docsMap) {
							oldDocs.add(String.valueOf(map2.get(Media.ID)));
						}
						delMediaids.addAll(this.listScreen(newDocs, oldDocs));
						addMediaids.addAll(this.listScreen(oldDocs,newDocs));
					}else{
						addMediaids.addAll(newDocs);
					}
				}
			}
			//			whereMap.put(Catalogue.ID, new BasicDBObject("$in", basicDBList));
			whereMap.put(FieldNews.ID, new ObjectId(id));
			Map<String, Object> set = new HashMap<String, Object>();
			//模板
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.TEMPLATE))){
				set.put(FieldNews.TEMPLATE, mapJson.get(CatalogueVo.TEMPLATE));
			}else{
				logger.debug("编目修改：template 模板字段为null！");
			}
			//概要
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.OVERVIEW))){
				set.put(FieldNews.OVERVIEW, mapJson.get(CatalogueVo.OVERVIEW));
			}else{
				logger.debug("编目修改：overview 概要字段为null！");
			}
			//视频ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.VIDEOIDS))){
				List<Map<String, Object>> videoids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.VIDEOIDS);

				//				List<String> videoids =(List<String>) mapJson.get(CatalogueVo.VIDEOIDS);
				List<Map<String, Object>> medias = this.getMedias2(videoids);
				set.put(FieldNews.VIDEOS, medias);
			}else{
				logger.debug("编目修改：videoIds 视频ids字段为null！");
			}
			//音频ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AUDIOIDS))){
				//				List<String> audioids =(List<String>) mapJson.get(CatalogueVo.AUDIOIDS);
				//				List<Map<String, Object>> medias = this.getMedias(audioids);
				List<Map<String, Object>> audioids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.AUDIOIDS);
				if(audioids.size()>0){
					List<Map<String, Object>> medias = this.getMedias2(audioids);
					set.put(FieldNews.AUDIOS, medias);

				}
			}else{
				logger.debug("编目修改：audioIds 音频ids字段为null！");
			}
			//图片ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.PICIDS))){
				//				List<String> picids =(List<String>) mapJson.get(CatalogueVo.PICIDS);
				//				List<Map<String, Object>> medias = this.getMedias(picids);
				List<Map<String, Object>> picids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.PICIDS);
				List<Map<String, Object>> medias = this.getMedias2(picids);
				set.put(FieldNews.PICS, medias);
			}else{
				logger.debug("编目修改：picIds 图片ids字段为null！");
			}
			//文件ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.DOCIDS))){
				//				List<String> docids =(List<String>) mapJson.get(CatalogueVo.DOCIDS);
				//				List<Map<String, Object>> medias = this.getMedias(docids);
				List<Map<String, Object>> docids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.DOCIDS);
				List<Map<String, Object>> medias = this.getMedias2(docids);
				set.put(FieldNews.DOCS, medias);
			}else{
				logger.debug("编目修改：docIds 文件ids字段为null！");
			}
			//其他信息
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.OTHERMSG))){
				set.put(FieldNews.OTHERMSG, mapJson.get(CatalogueVo.OTHERMSG));
			}else{
				logger.debug("编目修改：othermsg 其他信息字段为null！");
			}
			//title
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.TITLE))){
				set.put(FieldNews.TITLE, mapJson.get(CatalogueVo.TITLE));
			}else{
				logger.debug("编目修改：title 其他信息字段为null！");
			}
			//其他信息
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.THUMBNAILURL))){
				set.put(FieldNews.THUMBNAILURL, mapJson.get(CatalogueVo.THUMBNAILURL));
			}else{
				logger.debug("编目修改：thumbnailurl 其他信息字段为null！");
			}
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AREACODE))){
				set.put(FieldNews.AREACODE, String.valueOf(mapJson.get(CatalogueVo.AREACODE)));
			}
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AREANAME))){
				set.put(FieldNews.AREANAME, String.valueOf(mapJson.get(CatalogueVo.AREANAME)));
			}
			set.put(FieldNews.UUTIME, DateUtil.getCurrentDateTime());
			List<Map<String, Object>> logsLists = new ArrayList<Map<String,Object>>();
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put("userId", update.getUserId());
			logMap.put("userName", update.getUserName());
			logMap.put("time", DateUtil.getCurrentDateTime());
			logMap.put("content", "修改了文稿");
			logsLists.add(logMap);
			Map<String, Object> optmap = new HashMap<String, Object>();//第一个操作符
			Map<String, Object> optmap1 = new HashMap<String, Object>();//第一个操作符
			optmap1.put(QueryOperators.EACH, logsLists);
			optmap.put(FieldNews.LOGS,optmap1);
			//			set.put(QueryOperators.PUSHALL, optmap);//update需要更新的
			Map<String, Object> setMap = new HashMap<String, Object>();
			setMap.put(QueryOperators.SET, set);
			setMap.put(QueryOperators.PUSH, optmap);
			long index = fieldNewsDao.updateMany(whereMap, setMap, false);
			if(index>0){
				//保存日志
				Map<String, Object> db = fieldNewsDao.findOne(id);
				systemLogService.inset(SystemLogUtil.getMap(update, "0", "修改", "修改新闻外场文稿《"+db.get(Catalogue.TITLE)+"》"));
				//给素材添加文稿id
				this.addMediaCataids(addMediaids, ids);
				//给素材删除文稿id
				this.delMediaCataids(delMediaids, ids);
				//修改消息表
				Map<String, Object> msgWhereMap = new HashMap<String, Object>();
				msgWhereMap.put(Message.NEWSID, id);
				Map<String, Object> msgSetMap = new HashMap<String, Object>();
				msgSetMap.put(Message.UUTIME, DateUtil.getCurrentDateTime());
				messageDao.updateManyBySet(msgWhereMap, msgSetMap,true);
				return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
			}
		} else {
			logger.info("修改选中的编目id集合为null！");
		}
		return new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
	}

	@Override
	public ResponseObject addFieldnewsMaterial(CommonParameters commonParameters, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject updateFieldnewsMaterial(CommonParameters commonParameters, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject deleteFieldnewsMaterial(CommonParameters commonParameters, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject sendFieldnews(CommonParameters commonParameters, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}
	/** 移动端查询通联时返回的字段 */
	private Map<String, Object> getNewsMapPhone() {
		Map<String, Object> mapBack = new HashMap<String, Object>();
		mapBack.put(FieldNews.TITLE, 1);
		mapBack.put(FieldNews.TEMPLATE, 1);
		mapBack.put(FieldNews.STATUS, 1);
		mapBack.put(FieldNews.UUTIME, 1);
		mapBack.put(FieldNews.PUSHTOTAL, 1);
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
		mapBack.put(FieldNews.CHECKUSER, 1);
		mapBack.put(FieldNews.SHAREUSER, 1);
		mapBack.put(FieldNews.COMMENTS, 1);
		mapBack.put(FieldNews.CUSERID, 1);
		return mapBack;
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject top(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.top_error.status, GeneralStatus.top_error.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);

		// 获取业务参数
		String top = docJson.getString("top");
		if(!StringUtil.isEmpty(top)){
			Map<String, Object> update = new HashMap<String, Object>();
			if("1".equals(top)){
				update.put(FieldNews.TOPTIME,DateUtil.getCurrentDateTime());
			}else{
				update.put(FieldNews.TOPTIME,"");
			}
			String id = docJson.getString("id");
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(FieldNews.ID, new ObjectId(id));

			update.put(FieldNews.TOP, top);
			long index = fieldNewsDao.updateManyBySet(filter, update, true);
			if(index>0){
				executeSuccess(resObj);
			}
		}

		return resObj;
	}
	/**
	 * 处理需要查询的媒体
	 * @param mediaIds
	 * @return
	 */
	private List<Map<String, Object>> getMedias2(List<Map<String, Object>> mediaIds){
		List<Map<String, Object>> medias = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : mediaIds) {
			Map<String, Object> media = mediaDao.queryOne(String.valueOf(map.get("id")));
			if(null!=media){
				media.put(Media.ID,String.valueOf(media.get(Media.ID)));
				if(!StringUtil.isEmpty(map.get(MediaVo.EXTEND))){
					media.put(Media.EXTEND,map.get(MediaVo.EXTEND));

				}
				medias.add(media);

			}
		}
		return medias;
	}
	/**
	 * 追加媒体编目主键
	 * @param mediaids      媒体
	 * @param presentationids  文稿/编目
	 * @return
	 */
	private void addMediaCataids(List<String> mediaids,List<String> presentationids){
		//这里要修改对应的媒体，加入编目id
		Map<String, Object> whereMediaMap = new HashMap<String, Object>();
		Map<String, Object> set = new HashMap<String, Object>();

		for (String id : mediaids) {
			whereMediaMap.put(Media.ID, new ObjectId(id));
			//{$addToSet:{cataids:{$each:[id1,id2]}}}
			Map<String, Object> optmap = new HashMap<String, Object>();//第一个操作符
			Map<String, Object> optmap1 = new HashMap<String, Object>();//第一个操作符
			optmap1.put(QueryOperators.EACH, presentationids);
			optmap.put(Media.CATAIDS,optmap1);
			set.put(QueryOperators.ADDTOSET, optmap);//update需要更新的
			long index = mediaDao.update(whereMediaMap, set);
			if(index>0){
				logger.debug("添加媒体 cataids数组里id成功！");
			}else{
				logger.error("添加媒体 cataids数组里id失败！");
			}
		}

	}
	/**
	 * 删除媒体编目主键
	 * @param mediaids
	 * @param presentationids
	 * @return
	 */
	private void delMediaCataids(List<String> mediaids,List<String> presentationids){
		//这里要修改对应的媒体，加入编目id
		Map<String, Object> whereMediaMap = new HashMap<String, Object>();
		Map<String, Object> set = new HashMap<String, Object>();
		for (String id : mediaids) {
			whereMediaMap.put(Media.ID, new ObjectId(id));
			//{$addToSet:{cataids:{$each:[id1,id2]}}}
			Map<String, Object> optmap = new HashMap<String, Object>();//第一个操作符
			optmap.put(Media.CATAIDS,presentationids);
			set.put(QueryOperators.PULLALL, optmap);//update需要更新的
			long index =mediaDao.update(whereMediaMap, set);
			if(index>0){
				logger.debug("删除媒体 cataids数组里id成功！");
			}else{
				logger.error("删除媒体 cataids数组里id失败！");
			}
		}
	}
	/**
	 * list筛选  把list2里包含list1里的删除 
	 * @param list1
	 * @param list2
	 * @return 返回list2
	 */
	private List<String> listScreen(List<String> list1 ,List<String> list2){
		List<String> list = new ArrayList<String>();
		list.addAll(list2);
		for(String id:list1){
			if(list.contains(id)){
				list.remove(id);
			} 
		}
		return list;
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
}
