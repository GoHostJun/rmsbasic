package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.CatalogueVo;
import com.cdvcloud.rms.common.vo.MediaVo;
import com.cdvcloud.rms.dao.IFieldNewsDao;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.IMessageDao;
import com.cdvcloud.rms.dao.IPresentationDao;
import com.cdvcloud.rms.domain.Catalogue;
import com.cdvcloud.rms.domain.FieldNews;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.Message;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.service.INewsService;
import com.cdvcloud.rms.service.IPresentationService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
@Service
public class PresentationServiceImpl extends BasicService implements IPresentationService {
	private static final Logger logger = Logger.getLogger(PresentationServiceImpl.class);
	@Autowired
	private IPresentationDao materialDao;
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private INewsService iNewsService;
	@Autowired
	private IFieldNewsDao fieldNewsDao;
	@Autowired
	private IMessageDao messageDao;
	@Override
	public ResponseObject count(CommonParameters count, String strJson) {
		Map<String, Object> jsonMap =  JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(count, jsonMap, "", Catalogue.CTIME);
		whereMap.put(Catalogue.ISDEL, Constants.ZERO);
		Long index = materialDao.count(whereMap);
		ResponseObject res = new ResponseObject();
		res.setCode(GeneralStatus.success.status);
		res.setMessage(GeneralStatus.success.enDetail);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", index);
		res.setData(data);
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject insert(CommonParameters insert, String strJson) {
		//解析json
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		//创建插入map
		Map<String, Object> document = new HashMap<String, Object>();
		document.put(Catalogue.TEMPLATE, mapJson.get(CatalogueVo.TEMPLATE));
		document.put(Catalogue.TITLE, mapJson.get(CatalogueVo.TITLE));
		document.put(Catalogue.SRC, mapJson.get(CatalogueVo.SRC));
		document.put(Catalogue.STATUS, Constants.ZERO);
		document.put(Catalogue.ISDEL, Constants.ZERO);
		document.put(Catalogue.SHARE, Constants.ZERO);
		document.put(Catalogue.OTHERMSG, mapJson.get(CatalogueVo.OTHERMSG));
		document.put(Catalogue.THUMBNAILURL, mapJson.get(CatalogueVo.THUMBNAILURL));
		//存放需要更新的媒体id（媒体更新上文稿id）
		List<String> addMediaids = new ArrayList<String>();
		if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.VIDEOIDS))){

			List<Map<String, Object>> videos = new ArrayList<Map<String,Object>>();
			//			List<String> videoIds = (List<String>)mapJson.get(CatalogueVo.VIDEOIDS);
			//			for(int i=0;i<videoIds.size();i++){
			//				Map<String, Object> video = mediaDao.queryOne(videoIds.get(i));
			//				videos.add(video);
			//			}
			//[{"id":123,"extend":[cp,sc]}]
			List<Map<String, Object>> videoIds = (List<Map<String, Object>>)mapJson.get(CatalogueVo.VIDEOIDS);
			List<String> ids = new ArrayList<String>();
			for(int i=0;i<videoIds.size();i++){
				Map<String, Object> video = mediaDao.queryOne(String.valueOf(videoIds.get(i).get("id")));
				if(!StringUtil.isEmpty(videoIds.get(i).get(MediaVo.EXTEND))){
					video.put(Media.EXTEND, videoIds.get(i).get(MediaVo.EXTEND));
				}
				videos.add(video);
				ids.add(String.valueOf(videoIds.get(i).get("id")));
			}
			addMediaids.addAll(ids);
			document.put(Catalogue.VIDEOS, videos);
		}
		if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AUDIOIDS))){
			List<Map<String, Object>> audios = new ArrayList<Map<String,Object>>();
			//			List<String> audioIds = (List<String>)mapJson.get(CatalogueVo.AUDIOIDS);
			//			for(int i=0;i<audioIds.size();i++){
			//				Map<String, Object> audio = mediaDao.queryOne(audioIds.get(i));
			//				audios.add(audio);
			//			}
			//			addMediaids.addAll(audioIds);
			List<Map<String, Object>> audioIds = (List<Map<String, Object>>)mapJson.get(CatalogueVo.AUDIOIDS);
			List<String> ids = new ArrayList<String>();
			for(int i=0;i<audioIds.size();i++){
				Map<String, Object> audio = mediaDao.queryOne(String.valueOf(audioIds.get(i).get("id")));
				if(!StringUtil.isEmpty(audioIds.get(i).get(MediaVo.EXTEND))){
					audio.put(Media.EXTEND, audioIds.get(i).get(MediaVo.EXTEND));
				}
				audios.add(audio);
				ids.add(String.valueOf(audioIds.get(i).get("id")));
			}
			addMediaids.addAll(ids);
			document.put(Catalogue.AUDIOS, audios);
		}
		if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.PICIDS))){
			List<Map<String, Object>> pics = new ArrayList<Map<String,Object>>();
			//			List<String> picIds = (List<String>)mapJson.get(CatalogueVo.PICIDS);
			//			for(int i=0;i<picIds.size();i++){
			//				Map<String, Object> pic = mediaDao.queryOne(picIds.get(i));
			//				pics.add(pic);
			//			}
			//			addMediaids.addAll(picIds);
			List<Map<String, Object>> picIds = (List<Map<String, Object>>)mapJson.get(CatalogueVo.PICIDS);
			List<String> ids = new ArrayList<String>();
			for(int i=0;i<picIds.size();i++){
				Map<String, Object> pic = mediaDao.queryOne(String.valueOf(picIds.get(i).get("id")));
				if(!StringUtil.isEmpty(picIds.get(i).get(MediaVo.EXTEND))){
					pic.put(Media.EXTEND, picIds.get(i).get(MediaVo.EXTEND));
				}
				pics.add(pic);
				ids.add(String.valueOf(picIds.get(i).get("id")));
			}
			addMediaids.addAll(ids);
			document.put(Catalogue.PICS, pics);
		}
		if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.DOCIDS))){
			List<Map<String, Object>> docs = new ArrayList<Map<String,Object>>();
			List<String> docIds = (List<String>)mapJson.get(CatalogueVo.DOCIDS);
			for(int i=0;i<docIds.size();i++){
				Map<String, Object> doc = mediaDao.queryOne(docIds.get(i));
				docs.add(doc);
			}
			addMediaids.addAll(docIds);
			document.put(Catalogue.DOCS, docs);
		}
		String date = DateUtil.getCurrentDateTime();
		document.put(Catalogue.CTIME, date);
		document.put(Catalogue.CUSERID, insert.getUserId());
		document.put(Catalogue.CUSENAME, insert.getUserName());

		document.put(Catalogue.UUSERID, insert.getUserId());
		document.put(Catalogue.UUSERNAME, insert.getUserName());
		document.put(Catalogue.UUTIME, date);
		document.put(Catalogue.CONSUMERID, insert.getCompanyId());
		document.put(Catalogue.CONSUMERNAME, insert.getConsumerName());

		document.put(Catalogue.AREACODE, String.valueOf(mapJson.get(CatalogueVo.AREACODE)));
		document.put(Catalogue.AREANAME, String.valueOf(mapJson.get(CatalogueVo.AREANAME)));
		String id= materialDao.insertMaterial(document);
		if(!StringUtil.isEmpty(id)){
			//给素材添加文稿id
			List<String> ids = new ArrayList<String>();
			ids.add(id);
			this.addMediaCataids(addMediaids, ids);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", id);
			//保存日志
			systemLogService.inset(SystemLogUtil.getMap(insert, "0", "创建", "创建文稿《"+mapJson.get(CatalogueVo.TITLE)+"》"));
			LogUtil.printIntegralLog(insert, "createdoc", "创建文稿《"+mapJson.get(CatalogueVo.TITLE)+"》");
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, dataMap);
		}
		return new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
	}

	@Override
	public ResponseObject query(CommonParameters query, String strJson) {
		//解析json
		Map<String, Object> mapJson= JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(query, mapJson, Catalogue.TITLE, Catalogue.CTIME);
		//来源
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(CatalogueVo.SRC)))){
			whereMap.put(Catalogue.SRC, String.valueOf(mapJson.get(CatalogueVo.SRC)));
		}
		//状态
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(CatalogueVo.STATUS)))){
			whereMap.put(Catalogue.STATUS, Integer.valueOf(String.valueOf(mapJson.get(CatalogueVo.STATUS))));
		}
		//	地区code
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(CatalogueVo.AREACODE)))){
			whereMap.put(Catalogue.AREACODE, String.valueOf(mapJson.get(CatalogueVo.AREACODE)));
		}
		//地区名称
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(CatalogueVo.AREANAME)))){
			whereMap.put(Catalogue.AREANAME, String.valueOf(mapJson.get(CatalogueVo.AREANAME)));
		}
		//共享标识
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(CatalogueVo.SHARE)))){
			whereMap.put(Catalogue.SHARE, Integer.valueOf(String.valueOf(mapJson.get(CatalogueVo.SHARE))));
		}
		//回收站
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(CatalogueVo.RECYCLE)))){
			whereMap.put(Catalogue.RECYCLE, Integer.valueOf(String.valueOf(mapJson.get(CatalogueVo.RECYCLE))));
		}
		//推送次数
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get(CatalogueVo.PUSHTOTAL)))){
			Map<String, Object> mapPushToal = new HashMap<String, Object>();
			mapPushToal.put(QueryOperators.GTE, Integer.valueOf(String.valueOf(mapJson.get(CatalogueVo.PUSHTOTAL))));
			whereMap.put(Catalogue.PUSHTOTAL, mapPushToal);
		}
		if(!StringUtil.isEmpty(mapJson.get("mediaId"))&&!StringUtil.isEmpty(mapJson.get("mtype"))){
			BasicDBObject  mapNot=new BasicDBObject();
			mapNot.put(QueryOperators.NE, mapJson.get("mediaId"));
			if("0".equals(String.valueOf(mapJson.get("mtype")))){
				whereMap.put("videos._id", mapNot);
			}else if("1".equals(String.valueOf(mapJson.get("mtype")))){
				whereMap.put("audios._id", mapNot);
			}else if("2".equals(String.valueOf(mapJson.get("mtype")))){
				whereMap.put("pics._id", mapNot);
			}
		}
		//查询没被删除的
		whereMap.put(Catalogue.ISDEL, Constants.ZERO);

		// 排序参数
		Map<String, Object> sortFilter = getSortParam(Media.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> lists = null;
		String appCode = query.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {// 区分手机端和pc端
			Map<String, Object> mapBack = getMaterialMapPhone();
			lists = materialDao.query(sortFilter, whereMap, mapBack, currentPage, pageNum);
		}else{
			lists = materialDao.query(sortFilter, whereMap, currentPage, pageNum);
		}
		Long index = materialDao.count(whereMap);
		Pages pages = new Pages(pageNum,index,currentPage,lists);
		return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, pages);
	}

	@Override
	public ResponseObject queryById(CommonParameters queryById, String strJson) {
		//解析json
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> document = materialDao.queryOne(String.valueOf(mapJson.get(CatalogueVo.ID)));
		if(!StringUtil.isEmpty(document)){
			Map<String, Object> backMap = new LinkedHashMap<String, Object>();
			backMap.put(CatalogueVo.RESULT, document);
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,backMap);

		}
		return new ResponseObject(GeneralStatus.failure.status,GeneralStatus.failure.enDetail,"");
	}


	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject update(CommonParameters update, String strJson) {
		//		try {
		//			strJson = URLDecoder.decode(strJson,"utf-8");
		//		} catch (UnsupportedEncodingException e) {
		//			e.printStackTrace();
		//		}
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
				Map<String, Object> map =  materialDao.queryOne(materialId);
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
			whereMap.put(Catalogue.ID, new ObjectId(id));
			Map<String, Object> set = new HashMap<String, Object>();
			//模板
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.TEMPLATE))){
				set.put(Catalogue.TEMPLATE, mapJson.get(CatalogueVo.TEMPLATE));
			}else{
				logger.debug("编目修改：template 模板字段为null！");
			}
			//概要
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.OVERVIEW))){
				set.put(Catalogue.OVERVIEW, mapJson.get(CatalogueVo.OVERVIEW));
			}else{
				logger.debug("编目修改：overview 概要字段为null！");
			}
			//视频ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.VIDEOIDS))){
				List<Map<String, Object>> videoids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.VIDEOIDS);

				//				List<String> videoids =(List<String>) mapJson.get(CatalogueVo.VIDEOIDS);
				List<Map<String, Object>> medias = this.getMedias2(videoids);
				set.put(Catalogue.VIDEOS, medias);
			}else{
				logger.debug("编目修改：videoIds 视频ids字段为null！");
			}
			//音频ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AUDIOIDS))){
				//				List<String> audioids =(List<String>) mapJson.get(CatalogueVo.AUDIOIDS);
				//				List<Map<String, Object>> medias = this.getMedias(audioids);
				List<Map<String, Object>> audioids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.AUDIOIDS);
				List<Map<String, Object>> medias = this.getMedias2(audioids);
				set.put(Catalogue.AUDIOS, medias);
			}else{
				logger.debug("编目修改：audioIds 音频ids字段为null！");
			}
			//图片ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.PICIDS))){
				//				List<String> picids =(List<String>) mapJson.get(CatalogueVo.PICIDS);
				//				List<Map<String, Object>> medias = this.getMedias(picids);
				List<Map<String, Object>> picids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.PICIDS);
				List<Map<String, Object>> medias = this.getMedias2(picids);
				set.put(Catalogue.PICS, medias);
			}else{
				logger.debug("编目修改：picIds 图片ids字段为null！");
			}
			//文件ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.DOCIDS))){
				//				List<String> docids =(List<String>) mapJson.get(CatalogueVo.DOCIDS);
				//				List<Map<String, Object>> medias = this.getMedias(docids);
				List<Map<String, Object>> docids = (List<Map<String, Object>>)mapJson.get(CatalogueVo.DOCIDS);
				List<Map<String, Object>> medias = this.getMedias2(docids);
				set.put(Catalogue.DOCS, medias);
			}else{
				logger.debug("编目修改：docIds 文件ids字段为null！");
			}
			//其他信息
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.OTHERMSG))){
				set.put(Catalogue.OTHERMSG, mapJson.get(CatalogueVo.OTHERMSG));
			}else{
				logger.debug("编目修改：othermsg 其他信息字段为null！");
			}
			//title
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.TITLE))){
				set.put(Catalogue.TITLE, mapJson.get(CatalogueVo.TITLE));
			}else{
				logger.debug("编目修改：title 其他信息字段为null！");
			}
			//其他信息
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.THUMBNAILURL))){
				set.put(Catalogue.THUMBNAILURL, mapJson.get(CatalogueVo.THUMBNAILURL));
			}else{
				logger.debug("编目修改：thumbnailurl 其他信息字段为null！");
			}
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AREACODE))){
				set.put(Catalogue.AREACODE, String.valueOf(mapJson.get(CatalogueVo.AREACODE)));
			}
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AREANAME))){
				set.put(Catalogue.AREANAME, String.valueOf(mapJson.get(CatalogueVo.AREANAME)));
			}
			set.put(Catalogue.UUSERID, update.getUserId());
			set.put(Catalogue.UUSERNAME, "");
			set.put(Catalogue.UUTIME, DateUtil.getCurrentDateTime());
			long index = materialDao.updateBySet(whereMap, set);
			if(index>0){
				//保存日志
				Map<String, Object> db = materialDao.queryOne(id);
				systemLogService.inset(SystemLogUtil.getMap(update, "0", "修改", "修改文稿《"+db.get(Catalogue.TITLE)+"》"));
				//给素材添加文稿id
				this.addMediaCataids(addMediaids, ids);
				//给素材删除文稿id
				this.delMediaCataids(delMediaids, ids);
				return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
			}
		} else {
			logger.info("修改选中的编目id集合为null！");
		}
		return new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
	}

	@Override
	public ResponseObject thumbnail(CommonParameters queryById, String strJson) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Catalogue.ID, new ObjectId(CatalogueVo.ID));
		Map<String, Object> set = new HashMap<String, Object>();
		set.put(Catalogue.THUMBNAILURL, mapJson.get(CatalogueVo.THUMBNAILURL));
		long index = materialDao.updateBySet(whereMap, set);
		if(index>0){
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
		}else{
			return new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject delete(CommonParameters delete, String strJson) {
		Map<String, Object> mapJson =  JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<String> ids = (List<String>)mapJson.get(CatalogueVo.IDS);
		//-------------------------------获取删除文稿的素材id-------------------------------
		//存放需要删除的媒体id（媒体更新上文稿id）
		List<String> delMediaids = new ArrayList<String>();

		//循环选择文稿id
		for (String materialId : ids) {
			//查询当前文稿里的信息
			Map<String, Object> map =  materialDao.queryOne(materialId);
			//获取视频新增和删除的素材id集合
			//判断数据库里是否有视频
			if(!StringUtil.isEmpty(map.get(Catalogue.VIDEOS))){
				List<Map<String, Object>> videosMap = (List<Map<String, Object>>)map.get(Catalogue.VIDEOS);
				//获取数据库存储的视频id集合
				List<String> oldVideos = new ArrayList<String>();
				for (Map<String, Object> map2 : videosMap) {
					oldVideos.add(String.valueOf(map2.get(Media.ID)));
				}
				//获取删除的视频id集合
				delMediaids.addAll(oldVideos);
			}
			//获取音频新增和删除的素材id集合
			if(!StringUtil.isEmpty(map.get(Catalogue.AUDIOS))){
				List<Map<String, Object>> audioMap = (List<Map<String, Object>>)map.get(Catalogue.AUDIOS);
				List<String> oldAudios = new ArrayList<String>();
				for (Map<String, Object> map2 : audioMap) {
					oldAudios.add(String.valueOf(map2.get(Media.ID)));
				}
				delMediaids.addAll(oldAudios);
			}
			//获取图片新增和删除的素材id集合
			if(!StringUtil.isEmpty(map.get(Catalogue.PICS))){
				List<Map<String, Object>> picMap = (List<Map<String, Object>>)map.get(Catalogue.PICS);
				List<String> oldPics = new ArrayList<String>();
				for (Map<String, Object> map2 : picMap) {
					oldPics.add(String.valueOf(map2.get(Media.ID)));
				}
				delMediaids.addAll(oldPics);
			}
			//获取文本新增和删除的素材id集合
			if(!StringUtil.isEmpty(map.get(Catalogue.DOCS))){
				List<Map<String, Object>> docsMap = (List<Map<String, Object>>)map.get(Catalogue.DOCS);
				List<String> oldDocs = new ArrayList<String>();
				for (Map<String, Object> map2 : docsMap) {
					oldDocs.add(String.valueOf(map2.get(Media.ID)));
				}
				delMediaids.addAll(oldDocs);
			}
		}
		//-------------------------------获取删除文稿的素材id-------------------------------

		if (null != ids && !"".equals(ids)) {
			BasicDBList basicDBList = new BasicDBList();
			for (String id: ids) {
				basicDBList.add(new ObjectId(id));
			}
			whereMap.put(Catalogue.ID, new BasicDBObject("$in", basicDBList));
			Map<String, Object> set = new HashMap<String, Object>();
			set.put(Catalogue.ISDEL, Constants.ONE);
			Long media =  materialDao.updateBySet(whereMap, set);
			List<Map<String, Object>> lists = materialDao.query(whereMap);
			StringBuffer sb = new StringBuffer();
			if(null!=lists&&lists.size()>0){
				for (Map<String, Object> map : lists) {
					String name = String.valueOf(map.get(Catalogue.TITLE));
					sb.append("《"+name+"》");
				}
			}
			if(media>0){
				//给素材删除文稿id
				this.delMediaCataids(delMediaids, ids);
				//保存日志
				systemLogService.inset(SystemLogUtil.getMap(delete, "0", "删除", "删除文稿"+sb.toString()));
				return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,media);
			}
		}else{
			logger.info("删除选中的素材id集合为null！");
		}
		return new ResponseObject(GeneralStatus.failure.status,GeneralStatus.failure.enDetail,"");
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject shared(CommonParameters shared, String strJson) {
		Map<String, Object> mapJson =  JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<String> ids = (List<String>)mapJson.get(CatalogueVo.IDS);
		if (null != ids && !"".equals(ids)) {
			BasicDBList basicDBList = new BasicDBList();
			for (String id: ids) {
				basicDBList.add(new ObjectId(id));
			}
			whereMap.put(Catalogue.ID, new BasicDBObject("$in", basicDBList));
			Map<String, Object> set = new HashMap<String, Object>();
			set.put(Catalogue.SHARE, mapJson.get(Catalogue.SHARE));
			Long media =  materialDao.updateBySet(whereMap, set);
			if(media>0){
				return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,media);
			}
		}else{
			logger.info("共享选中的素材id集合为null！");
		}
		return new ResponseObject(GeneralStatus.failure.status,GeneralStatus.failure.enDetail,"");
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject addMedia(CommonParameters addMedia, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		//解析json
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		List<String> ids =  new ArrayList<String>();
		String id =  String.valueOf(mapJson.get(CatalogueVo.ID));//文稿id
		ids.add(id);
		//存放需要更新的媒体id（媒体更新上文稿id）
		List<String> addMediaids = new ArrayList<String>();

		if (null != id && !"".equals(id)) {
			BasicDBList basicDBList = new BasicDBList();
			//循环选择文稿id
			for (String materialId : ids) {
				basicDBList.add(new ObjectId(materialId));
				//查询当前文稿里的信息
				Map<String, Object> map =  materialDao.queryOne(materialId);
				//获取视频新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.VIDEOIDS))){//判断接口过来的视频id是否存在
					//视频id转换list
					List<String> newVideos = (List<String>)mapJson.get(CatalogueVo.VIDEOIDS);
					//判断数据库里是否有视频
					if(!StringUtil.isEmpty(map.get(Catalogue.VIDEOS))){
						List<Map<String, Object>> videosMap = (List<Map<String, Object>>)map.get(Catalogue.VIDEOS);
						//获取数据库存储的视频id集合
						List<String> oldVideos = new ArrayList<String>();
						for (Map<String, Object> map2 : videosMap) {
							oldVideos.add(String.valueOf(map2.get(Media.ID)));
						}
						//获取新增视频id集合
						addMediaids.addAll(this.listScreen(newVideos, oldVideos));
						addMediaids.addAll(this.listScreen(oldVideos,newVideos));
					}else{
						addMediaids.addAll(newVideos);//如果数据库没有视频集合则是新建
					}
				}
				//获取音频新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AUDIOIDS))){
					List<String> newAudios = (List<String>)mapJson.get(CatalogueVo.AUDIOIDS);
					if(!StringUtil.isEmpty(map.get(Catalogue.AUDIOS))){
						List<Map<String, Object>> audioMap = (List<Map<String, Object>>)map.get(Catalogue.AUDIOS);
						List<String> oldAudios = new ArrayList<String>();
						for (Map<String, Object> map2 : audioMap) {
							oldAudios.add(String.valueOf(map2.get(Media.ID)));
						}
						addMediaids.addAll(this.listScreen(newAudios, oldAudios));
						addMediaids.addAll(this.listScreen(oldAudios,newAudios));
					}else{
						addMediaids.addAll(newAudios);
					}
				}
				//获取图片新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.PICIDS))){
					List<String> newPics = (List<String>)mapJson.get(CatalogueVo.PICIDS);
					if(!StringUtil.isEmpty(map.get(Catalogue.PICS))){
						List<Map<String, Object>> picMap = (List<Map<String, Object>>)map.get(Catalogue.PICS);
						List<String> oldPics = new ArrayList<String>();
						for (Map<String, Object> map2 : picMap) {
							oldPics.add(String.valueOf(map2.get(Media.ID)));
						}
						addMediaids.addAll(this.listScreen(newPics, oldPics));
						addMediaids.addAll(this.listScreen(oldPics,newPics));
					}else{
						addMediaids.addAll(newPics);
					}
				}
				//获取文本新增和删除的素材id集合
				if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.DOCIDS))){
					List<String> newDocs = (List<String>)mapJson.get(CatalogueVo.DOCIDS);
					if(!StringUtil.isEmpty(map.get(Catalogue.DOCS))){
						List<Map<String, Object>> docsMap = (List<Map<String, Object>>)map.get(Catalogue.DOCS);
						List<String> oldDocs = new ArrayList<String>();
						for (Map<String, Object> map2 : docsMap) {
							oldDocs.add(String.valueOf(map2.get(Media.ID)));
						}
						addMediaids.addAll(this.listScreen(newDocs, oldDocs));
						addMediaids.addAll(this.listScreen(oldDocs,newDocs));
					}else{
						addMediaids.addAll(newDocs);
					}
				}
			}
			//视频ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.VIDEOIDS))){
				List<String> videoids =(List<String>) mapJson.get(CatalogueVo.VIDEOIDS);
				List<Map<String, Object>> medias = this.getMedias(videoids);
				boolean bool = addMediaToPresention(ids, medias,Catalogue.VIDEOS);
				if(!bool){
					return resObj;
				}
			}else{
				logger.debug("编目修改：videoIds 视频ids字段为null！");
			}
			//音频ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.AUDIOIDS))){
				List<String> audioids =(List<String>) mapJson.get(CatalogueVo.AUDIOIDS);
				List<Map<String, Object>> medias = this.getMedias(audioids);
				boolean bool=addMediaToPresention(ids, medias,Catalogue.AUDIOS);
				if(!bool){
					return resObj;
				}
			}else{
				logger.debug("编目修改：audioIds 音频ids字段为null！");
			}
			//图片ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.PICIDS))){
				List<String> picids =(List<String>) mapJson.get(CatalogueVo.PICIDS);
				List<Map<String, Object>> medias = this.getMedias(picids);
				boolean	bool=addMediaToPresention(ids, medias,Catalogue.PICS);
				if(!bool){
					return resObj;
				}
			}else{
				logger.debug("编目修改：picIds 图片ids字段为null！");
			}
			//文件ids
			if(!StringUtil.isEmpty(mapJson.get(CatalogueVo.DOCIDS))){
				List<String> docids =(List<String>) mapJson.get(CatalogueVo.DOCIDS);
				List<Map<String, Object>> medias = this.getMedias(docids);
				boolean bool = addMediaToPresention(ids, medias,Catalogue.DOCS);
				if(!bool){
					return resObj;
				}
			}else{
				logger.debug("编目修改：docIds 文件ids字段为null！");
			}
			//给素材添加文稿id
			addMediaCataids(addMediaids, ids);
			resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
		}

		return resObj;
	}


	/********************************* 下面是自用方法不是Controller调用 ****************************/


	/**
	 * 处理需要查询的媒体
	 * @param mediaIds
	 * @return
	 */
	private List<Map<String, Object>> getMedias(List<String> mediaIds){
		List<Map<String, Object>> medias = new ArrayList<Map<String,Object>>();
		for (String id : mediaIds) {
			Map<String, Object> media = mediaDao.queryOne(id);
			media.put(Media.ID,String.valueOf(media.get(Media.ID)));
			medias.add(media);
		}
		return medias;
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
	@Override
	public void addMediaToCataids(List<String> mediaids,List<String> presentationids){
		addMediaCataids(mediaids, presentationids);
	}
	/**
	 * 删除媒体编目主键
	 * @param mediaids
	 * @param presentationids
	 * @return
	 */
	@Override
	public void delMediaToCataids(List<String> mediaids,List<String> presentationids){
		delMediaCataids(mediaids, presentationids);
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

	private boolean addMediaToPresention(List<String> mediaids,List<Map<String, Object>> presentationids,String media){
		//这里要修改对应的媒体，加入编目id
		Map<String, Object> whereMediaMap = new HashMap<String, Object>();
		Map<String, Object> set = new HashMap<String, Object>();

		for (String id : mediaids) {
			whereMediaMap.put(Media.ID, new ObjectId(id));
			//{$addToSet:{cataids:{$each:[id1,id2]}}}
			Map<String, Object> optmap = new HashMap<String, Object>();//第一个操作符
			Map<String, Object> optmap1 = new HashMap<String, Object>();//第一个操作符
			optmap1.put(QueryOperators.EACH, presentationids);
			optmap.put(media,optmap1);
			set.put(QueryOperators.ADDTOSET, optmap);//update需要更新的
			long index = materialDao.update(whereMediaMap, set);
			if(index>0){
				logger.debug("添加媒体 cataids数组里id成功！");
			}else{
				logger.error("添加媒体 cataids数组里id失败！");
				return false;
			}
		}
		return true;

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

	@Override
	public boolean updateNewsToPresentation(String id, Map<String, Object> setMap) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Catalogue.ID, new ObjectId(id));
		Long index =  materialDao.updateBySet(whereMap, setMap);
		if(index>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean delete(String id) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Catalogue.ID,new ObjectId(id));
		Map<String, Object> set = new HashMap<String, Object>();
		set.put(Catalogue.ISDEL, Constants.ONE);
		Long media =  materialDao.updateBySet(whereMap, set);
		if(media>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ResponseObject statistics(CommonParameters count, String strJson) {
		try {
			Map<String, Object> jsonMap =  JsonUtil.readJSON2Map(strJson);

			// 获取业务参数,并进行相关业务操作
			Map<String, Object> whereMap = getVommonalityParam(count, jsonMap, null, null);
			whereMap.put(Catalogue.STATUS, GeneralStatus.success.status);
			whereMap.put(Catalogue.ISDEL, Constants.ZERO);
			List<Map<String, Object>> dateList = new ArrayList<Map<String,Object>>();
			List<Object> counts = new ArrayList<Object>();

			String dateType =String.valueOf(jsonMap.get("dateType"));
			// 开始时间
			String startTime ="";
			String endTime ="";
			if("week".equals(dateType)){
				String[] dates = DateUtil.getBeginAndEndTime("本周").split(",");
				startTime=dates[0];
				endTime=dates[1];
			}
			if("month".equals(dateType)){
				String[] dates = DateUtil.getBeginAndEndTime("本月").split(",");
				startTime=dates[0];
				endTime=dates[1];
			}
			if("year".equals(dateType)){
				String[] dates = DateUtil.getBeginAndEndTime("今年").split(",");
				startTime=dates[0];
				endTime=dates[1];
			}
			if (!StringUtil.isEmpty(jsonMap.get("startTime")) && !StringUtil.isEmpty(String.valueOf(jsonMap.get("startTime")))) {
				startTime = String.valueOf(jsonMap.get("startTime"));
			}
			// 结束时间
			if (!StringUtil.isEmpty(jsonMap.get("endTime")) && !StringUtil.isEmpty(String.valueOf(jsonMap.get("endTime")))) {
				endTime = String.valueOf(jsonMap.get("endTime"));
			}
			Date bgDate = DateUtil.stringToDate(startTime);
			Date egDate = DateUtil.stringToDate(endTime);
			List<Date> datelists = DateUtil.findDates(bgDate, egDate);// 获取开始时间到结束时间内的时间点
			//时间算法  小于等于14天  安天算   15到60按周算   60以上按月算
			if (null != datelists && datelists.size() > 61) {//时间超过两个月的按照月为单位进行统计
				List<Map<String, Object>> maplists = DateUtil.findGroupByMonth(startTime, endTime);//按月统计
				for (int i = 0; i < maplists.size(); i++) {

					String strdate = null;
					Map<String, Object> maps = maplists.get(i);
					BasicDBObject dateCondition = new BasicDBObject();
					String datest = "";
					for (String key : maps.keySet()) {
						if ("stime" == key || "stime".equals(key)) {
							strdate = String.valueOf(maps.get(key));
							strdate = strdate.substring(0, strdate.lastIndexOf("-"));
							datest = String.valueOf(maps.get(key));
							String starDate = maps.get(key) + " 00:00:00";
							dateCondition.append(QueryOperators.GTE, starDate);
						}
						if ("etime" == key || "etime".equals(key)) {
							String endDate = maps.get(key) + " 23:59:59";
							dateCondition.append(QueryOperators.LTE, endDate);//查询昨天的日期
						}
					}
					Map<String, Object> date = new HashMap<String, Object>();
					date.put(datest, dateCondition);
					dateList.add(date);
				}

			}else if(null != datelists && datelists.size() > 7){//时间超过7天的按照七天为单位进行统计
				List<Map<String, Object>> maplists = DateUtil.findWeekDate(startTime, endTime);//按周统计
				for (int i = 0; i < maplists.size(); i++) {
					String strdate = null;
					Map<String, Object> maps = maplists.get(i);
					BasicDBObject dateCondition = new BasicDBObject();
					String datest = "";
					for (String key : maps.keySet()) {
						if ("stime" == key || "stime".equals(key)) {
							strdate = String.valueOf(maps.get(key));
							strdate = strdate.substring(0, strdate.lastIndexOf("-")+3);
							datest = String.valueOf(maps.get(key));
							String starDate = maps.get(key) + " 00:00:00";
							dateCondition.append(QueryOperators.GTE, starDate);
						}
						if ("etime" == key || "etime".equals(key)) {
							String endDate = maps.get(key) + " 23:59:59";
							strdate = strdate + "~" + maps.get(key);
							dateCondition.append(QueryOperators.LTE, endDate);//查询昨天的日期
						}
					}
					Map<String, Object> date = new HashMap<String, Object>();
					date.put(datest, dateCondition);
					dateList.add(date);
				}
			}else{
				for (Date dates : datelists) {
					String strdate = DateUtil.dateToString(dates);
					String starDate = strdate + " 00:00:00";
					String endDate = strdate + " 23:59:59";
					BasicDBObject dateCondition = new BasicDBObject();
					dateCondition.append(QueryOperators.GTE, starDate);
					dateCondition.append(QueryOperators.LTE, endDate);//查询昨天的日期
					Map<String, Object> date = new HashMap<String, Object>();
					date.put(strdate, dateCondition);
					dateList.add(date);
				}
			}

			for(int i=0 ;i<dateList.size();i++){
				for(String key : dateList.get(i).keySet()){
					whereMap.put(Media.CTIME, dateList.get(i).get(key));
					Map<String, Object> sts= new HashMap<String, Object>();
					sts.put("date", key);
					Long intex = materialDao.count(whereMap);
					sts.put("presentation", intex);
					counts.add(sts);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("statistics", counts);
			data.put("startTime", startTime);
			data.put("endTime", endTime);
			//保存日志
			systemLogService.inset(SystemLogUtil.getMap(count, "0", "统计", "文稿报表统计"));
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 移动端查询文稿时返回的字段 */
	private Map<String, Object> getMaterialMapPhone() {
		Map<String, Object> mapBack = new HashMap<String, Object>();
		mapBack.put(Catalogue.TITLE, 1);
		mapBack.put(Catalogue.TEMPLATE, 1);
		mapBack.put(Catalogue.UUTIME, 1);
		mapBack.put(Catalogue.CTIME, 1);
		mapBack.put(Catalogue.THUMBNAILURL, 1);
		mapBack.put("pushtotal", 1);
		mapBack.put("pushset", 1);
		mapBack.put("videos._id", 1);
		mapBack.put("videos.name", 1);
		mapBack.put("videos.vslt", 1);
		mapBack.put("videos.mtype", 1);
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
		mapBack.put("audios.wanurl", 1);
		mapBack.put("audios.mtype", 1);
		mapBack.put("audios.defaults.fmt", 1);
		mapBack.put("audios.defaults.wanurl", 1);
		return mapBack;
	}

	@Override
	public ResponseObject pushINEWS(CommonParameters queryById, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		//解析json
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> map = materialDao.queryOne(String.valueOf(mapJson.get(CatalogueVo.ID)));
		boolean bool =  iNewsService.sendToNewsphere(map, queryById);
		if(bool){
			systemLogService.inset(SystemLogUtil.getMap(queryById, "0", "推送", "文稿推送INEWS《"+map.get(News.TITLE)+"》成功！"));
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,"");
		}else{
			systemLogService.inset(SystemLogUtil.getMap(queryById, "0", "推送", "文稿推送INEWS《"+map.get(News.TITLE)+"》失败！"));
			return resObj;
		}
	}
	@Override
	public ResponseObject pushConverge(CommonParameters queryById, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		//解析json
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> map = materialDao.queryOne(String.valueOf(mapJson.get(CatalogueVo.ID)));
		boolean bool =  iNewsService.sendConverge(map, queryById);
		if(bool){
			systemLogService.inset(SystemLogUtil.getMap(queryById, "0", "推送", "文稿推送智云《"+map.get(News.TITLE)+"》成功！"));
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,"");
		}else{
			systemLogService.inset(SystemLogUtil.getMap(queryById, "0", "推送", "文稿推送智云《"+map.get(News.TITLE)+"》失败！"));
			return resObj;
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseObject copyToFieldNews(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		String catalogueid = docJson.getString("catalogueid");
		// 获取新闻通联
		Map<String, Object> newsMap = materialDao.queryOne(catalogueid);
		if (null == newsMap || newsMap.isEmpty()) {
			return super.queryError("没有获取到文稿！Id+" + catalogueid);
		}
		//查询外场新闻表是否已经有分享过的文稿  如果有，不插入新的
		Map<String, Object> fieldNewsWhereMap = new HashMap<String, Object>();
		fieldNewsWhereMap.put(FieldNews.CATALOGUEID, catalogueid);
		List<Map<String, Object>> lists =  fieldNewsDao.findNewAll(fieldNewsWhereMap, 1, 10);
		//判断是否有新闻外场数据
		if(null!=lists&&lists.size()>0){
			resObj.setCode(GeneralStatus.success.status);
			resObj.setMessage(GeneralStatus.success.enDetail);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", lists.get(0).get("_id"));
			resObj.setData(data);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "分享文稿", "分享了文稿《"+newsMap.get(News.TITLE)+"》"));
		}else{
			// 移除多余项，插入到文稿库
			newsMap.remove(FieldNews.ID);
			newsMap.put(FieldNews.SHOW, "1");
			newsMap.put(FieldNews.CATALOGUEID, catalogueid);
			newsMap.put(FieldNews.STATUS, GeneralStatus.success.status);
			newsMap.put(FieldNews.CTIME, DateUtil.getCurrentDateTime());
			newsMap.put(FieldNews.UUTIME, DateUtil.getCurrentDateTime());
			newsMap.put(FieldNews.CUSERID, commonParameters.getUserId());
			newsMap.put(FieldNews.CUSENAME, commonParameters.getUserName());
			newsMap.put(FieldNews.DEPARTMENTID, commonParameters.getDepartmentId());
			newsMap.put(FieldNews.CONSUMERID, commonParameters.getCompanyId());
			newsMap.put(FieldNews.CONSUMERNAME, commonParameters.getConsumerName());
			Map<String, Object> userMap =  new HashMap<String, Object>();
			userMap.put("userName", commonParameters.getUserName());
			userMap.put("userId", commonParameters.getUserId());
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list.add(userMap);
			newsMap.put(Message.PARTICIPANTS, list);
			String cateId = fieldNewsDao.insertNews(newsMap);
			if (null != cateId) {
				// 获取文稿里所有素材id，添加文稿id到素材里
				List<String> materialIds = new ArrayList<String>();
				materialIds.add(cateId);
				List<String> mediaids = getMaterialIds(newsMap);
				this.addMediaToCataids(mediaids, materialIds);
				resObj.setCode(GeneralStatus.success.status);
				resObj.setMessage(GeneralStatus.success.enDetail);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", cateId);
				resObj.setData(data);
				systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "分享文稿", "分享一条文稿《"+newsMap.get(News.TITLE)+"》"));
				//插入消息表信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Message.NEWSID, cateId);
				map.put(Message.TYPE, 5);
				map.put(Message.STATUS, 0);
				map.put(Message.MSGCONTENT, newsMap.get(News.TITLE));
				map.put(Message.MSGTITLE, newsMap.get(News.TITLE));
				map.put(Message.DEPARTMENTID, commonParameters.getDepartmentId());
				map.put(Message.CUSERID, commonParameters.getUserId());
				map.put(Message.CONSUMERID, commonParameters.getCompanyId());
				map.put(Message.CONSUMERNAME, commonParameters.getConsumerName());
				map.put(Message.CUSENAME, commonParameters.getUserName());
				map.put(Message.CTIME, DateUtil.getCurrentDateTime());
				map.put(Message.UUTIME, DateUtil.getCurrentDateTime());
				map.put(Message.PARTICIPANTS, list);
				messageDao.insertMessage(map);
			} else {
				logger.warn("插入文稿失败！cateId+" + cateId + ",newsMap:" + newsMap);
			}
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
			this.addMediaToCataids(new_mediaIds, materialIds);
			this.delMediaToCataids(old_mediaIds, materialIds);
		} else {
			logger.warn("传入map对象为空！materialMap=" + materialMap);
		}
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
}
