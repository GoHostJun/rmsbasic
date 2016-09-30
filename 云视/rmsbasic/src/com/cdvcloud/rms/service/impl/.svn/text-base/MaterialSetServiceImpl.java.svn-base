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
import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.CatalogueVo;
import com.cdvcloud.rms.common.vo.MediaVo;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.MaterialSetDao;
import com.cdvcloud.rms.domain.BasicObject;
import com.cdvcloud.rms.domain.FieldNews;
import com.cdvcloud.rms.domain.MaterialSet;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.service.IMaterialSetService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.util.DateUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
/**
 * 素材集实现
 * @author mcxin
 *
 */
@Service
public class MaterialSetServiceImpl extends BasicService implements IMaterialSetService {
	private static final Logger logger = Logger.getLogger(MaterialSetServiceImpl.class);
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	private MaterialSetDao MaterialSetDao;
	@Autowired
	private ISystemLogService systemLogService;

	@Override
	public ResponseObject findMediaList(CommonParameters queryById,String strJson) {
		Map<String, Object> json =  JsonUtil.readJSON2Map(strJson);
		if(!StringUtil.isEmpty(json.get(MediaVo.ID))){
			String id = String.valueOf(json.get(MediaVo.ID));
			Map<String, Object> materialSet =  MaterialSetDao.queryOne(id);
			//这里要添加查看人的信息到参与人里
			Map<String, Object> userMap =  new HashMap<String, Object>();
			userMap.put("userName", queryById.getUserName());
			userMap.put("userId", queryById.getUserId());
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list.add(userMap);
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(MaterialSet.ID, new ObjectId(id));
			Map<String, Object> set = new HashMap<String, Object>();
			Map<String, Object> optmap = new HashMap<String, Object>();//第一个操作符
			Map<String, Object> optmap1 = new HashMap<String, Object>();//第一个操作符
			optmap1.put(QueryOperators.EACH, list);
			optmap.put(FieldNews.PARTICIPANTS,optmap1);
			set.put(QueryOperators.ADDTOSET, optmap);//update需要更新的
			long index = MaterialSetDao.update(filter, set);
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,materialSet);
		}else{
			return new ResponseObject(GeneralStatus.query_error.status,GeneralStatus.query_error.enDetail,"");
		}
	}

	@Override
	public ResponseObject findList(CommonParameters query,String json) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(json);

		// 获取业务参数,并进行相关业务操作
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(query, mapJson, MaterialSet.TITLE, MaterialSet.UUTIME);
		whereMap.remove(BasicObject.CUSERID);
		String userId = query.getUserId();
		whereMap.put(MaterialSet.PARTICIPANTS+".userId", userId);
		//来源
		if(!StringUtil.isEmpty(mapJson.get(MaterialSet.SRC))){
			whereMap.put(MaterialSet.SRC, String.valueOf(mapJson.get(MaterialSet.SRC)));
		}
		// 排序参数
		Map<String, Object> sortFilter = getSortParam(MaterialSet.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		Long total = MaterialSetDao.count(whereMap);
		List<Map<String, Object>> lists = MaterialSetDao.query(sortFilter, whereMap, currentPage, pageNum);
		if(null!=lists &&lists.size()>=0){
			Pages pages = new Pages(pageNum,total,currentPage,lists);
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,pages);
		}else{
			ResponseObject resObj =new ResponseObject(GeneralStatus.query_error.status,GeneralStatus.query_error.enDetail,"");
			executeSuccess(resObj, lists);
			return resObj;
		}
	}



	@Override
	public ResponseObject update(CommonParameters update, String strJson) {

		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		//获取修改数据主键
		String id = String.valueOf(mapJson.get(MediaVo.ID));
		if (null != id && !"".equals(id)) {
			whereMap.put(Media.ID, new ObjectId(id));

			//修改条件
			Map<String, Object> set = new HashMap<String, Object>();
			if(!StringUtil.isEmpty(String.valueOf(mapJson.get(MaterialSet.SRC)))){
				set.put(MaterialSet.SRC, mapJson.get(MaterialSet.SRC));
			}
			if(!StringUtil.isEmpty(String.valueOf(mapJson.get(MaterialSet.REMARK)))){
				set.put(MaterialSet.REMARK, mapJson.get(MaterialSet.REMARK));
			}
			if(!StringUtil.isEmpty(String.valueOf(mapJson.get(MaterialSet.TITLE)))){
				set.put(MaterialSet.TITLE, mapJson.get(MaterialSet.TITLE));
			}
			if(!StringUtil.isEmpty(String.valueOf(mapJson.get(MaterialSet.THUMBNAILURL)))){
				set.put(MaterialSet.THUMBNAILURL, mapJson.get(MaterialSet.THUMBNAILURL));
			}
			

			long index = MaterialSetDao.updateBySet(whereMap, set);
			/**  开始记录日志*/
			Map<String,Object> LogMap =new HashMap<String, Object>();
			LogMap.put(CommonParameters.APPCODE, update.getAppCode());
			LogMap.put(CommonParameters.VERSIONID,update.getVersionId());
			LogMap.put(CommonParameters.COMPANYID,update.getCompanyId());
			LogMap.put(CommonParameters.USERID,update.getUserId());
			LogMap.put(CommonParameters.SERVICECODE,update.getServiceCode());
			LogMap.put("info",set);
			logger.info(LogUtil.getLogMessage(LogMap,"OnAirLogR","ytl"));
			/**  开始记录日志完成*/
			//保存日志
			systemLogService.inset(SystemLogUtil.getMap(update, "0", "修改", "修改素材集《"+id+"》"));
			if(index>0){
				return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,"");
			}
		}else{
			logger.info("修改选中的素材id集合为null！");
		}
		return new ResponseObject(GeneralStatus.update_error.status,GeneralStatus.update_error.enDetail,"");
	}


	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject deleteList(CommonParameters delete, String strJson) {
		Map<String, Object> mapJson =  JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<String> ids = (List<String>)mapJson.get(MediaVo.IDS);
		if (null != ids && !"".equals(ids)) {
			BasicDBList basicDBList = new BasicDBList();
			
			for (String id: ids) {
				basicDBList.add(new ObjectId(id));
			}
			whereMap.put(Media.ID, new BasicDBObject("$in", basicDBList));
			List<Map<String, Object>> lists = mediaDao.query(whereMap);
			StringBuffer sb = new StringBuffer();
			if(null!=lists&&lists.size()>0){
				for (Map<String, Object> map : lists) {
					String name = String.valueOf(map.get(MaterialSet.TITLE));
					sb.append("《"+name+"》");
				}
			}
			Long media =  MaterialSetDao.delete(whereMap);
			
			if(media>0){
				//保存日志
				systemLogService.inset(SystemLogUtil.getMap(delete, "0", "删除", "删除素材集"+sb.toString()));
				return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,media);
			}
		}else{
			logger.info("删除选中的素材集id集合为null！");
		}
		return new ResponseObject(GeneralStatus.delete_error.status,GeneralStatus.delete_error.enDetail,"");
	}


	


	
	
	
	@Override
	public ResponseObject findList(Map<String, Object> mapUploadIds) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> backMap = new HashMap<String, Object>();
		backMap.put(Media.NAME, 1);
		backMap.put(Media.STATUS, 1);
		backMap.put(Media.FMT, 1);
		backMap.put(Media.UPLOADUUID, 1);
		backMap.put(Media.VSLT, 1);
		backMap.put(Media.WANURL, 1);
		backMap.put(Media.MTYPE, 1);
		String mediaIds = String.valueOf(mapUploadIds.get("uploadIds"));
		if (null != mediaIds && !"null".equals(mediaIds)&&!"".equals(mediaIds)) {
			String[] materialIdArray = mediaIds.split(",");
			BasicDBList basicDBList = new BasicDBList();
			for (String materialId : materialIdArray) {
				basicDBList.add(materialId);
			}
			whereMap.put(Media.UPLOADUUID, new BasicDBObject("$in", basicDBList));
		}
		// 排序参数
		Map<String, Object> sortFilter = getSortParam(Media.CTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapUploadIds);
		int pageNum = getPageNum(mapUploadIds);
		List<Map<String, Object>> lists = mediaDao.query(sortFilter, whereMap, backMap, currentPage, pageNum);
		ResponseObject resObj = new ResponseObject();
		executeSuccess(resObj, lists);
		return resObj;
	}

	/** 移动端查询素材时返回的字段 */
	private Map<String, Object> getMediaMapPhone() {
		Map<String, Object> mapBack = new HashMap<String, Object>();
		mapBack.put(Media.MTYPE, 1);
		mapBack.put(Media.NAME, 1);
		mapBack.put(Media.STATUS, 1);
		mapBack.put(Media.CTIME, 1);
		mapBack.put(Media.VSLT, 1);
		mapBack.put(Media.HEIGHT, 1);
		mapBack.put(Media.WIDTH, 1);
		mapBack.put(Media.DESCRIBE, 1);
		mapBack.put(Media.OTHERMSG, 1);
		mapBack.put(Media.SRC, 1);
		mapBack.put(Media.REMARK, 1);
		mapBack.put(Media.DESCRIBE, 1);
		mapBack.put(Media.FMT, 1);
		mapBack.put(Media.SIZE, 1);
		mapBack.put(Media.UUTIME, 1);
		mapBack.put(Media.RATE, 1);
		mapBack.put(Media.WANURL, 1);
		mapBack.put("defaults.fmt", 1);
		mapBack.put("defaults.ctype", 1);
		mapBack.put("defaults.wanurl", 1);
		mapBack.put("defaults.duration", 1);
		return mapBack;
	}

	@Override
	public ResponseObject savematerialSet(CommonParameters common, String strJson) {
		Map<String, Object> mapJson =  JsonUtil.readJSON2Map(strJson);
		String src = String.valueOf(mapJson.get(MaterialSet.SRC));
		String remark= String.valueOf(mapJson.get(MaterialSet.REMARK));
		String title= String.valueOf(mapJson.get(MaterialSet.TITLE));
		String thumbnailurl= String.valueOf(mapJson.get(MaterialSet.THUMBNAILURL));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MaterialSet.SRC, src);
		map.put(MaterialSet.REMARK, remark);
		map.put(MaterialSet.TITLE, title);
		map.put(MaterialSet.THUMBNAILURL, thumbnailurl);
		map.put(MaterialSet.CONSUMERID, common.getCompanyId());
		map.put(MaterialSet.CONSUMERNAME, common.getConsumerName());
		map.put(MaterialSet.DEPARTMENTID, common.getDepartmentId());
		map.put(MaterialSet.CTIME, DateUtil.getCurrentTime());
		map.put(MaterialSet.UUTIME, DateUtil.getCurrentTime());
		map.put(MaterialSet.CUSERID, common.getUserId());
		map.put(MaterialSet.CUSENAME, common.getUserName());
		//这里要添加查看人的信息到参与人里
		Map<String, Object> userMap =  new HashMap<String, Object>();
		userMap.put("userName", common.getUserName());
		userMap.put("userId", common.getUserId());
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list.add(userMap);
		map.put(MaterialSet.PARTICIPANTS, list);
		String id = MaterialSetDao.insertMedia(map);
		if(!StringUtil.isEmpty(id)){
			systemLogService.inset(SystemLogUtil.getMap(common, "0", "创建", "素材集《"+title+"》"));
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,"");
		}else{
			return new ResponseObject(GeneralStatus.media_failure.status,GeneralStatus.media_failure.enDetail,"");
		}
	}
	@Override
	public ResponseObject thumbnail(CommonParameters queryById, String strJson) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(MaterialSet.ID, new ObjectId(CatalogueVo.ID));
		Map<String, Object> set = new HashMap<String, Object>();
		set.put(MaterialSet.THUMBNAILURL, mapJson.get(MaterialSet.THUMBNAILURL));
		long index = MaterialSetDao.updateBySet(whereMap, set);
		if(index>0){
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
		}else{
			return new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		}
	}

	@Override
	public ResponseObject copyMaterialSet(CommonParameters copy, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Document docJson = new Document().parse(strJson);
		String id = docJson.getString("id");
		Map<String, Object> media =  mediaDao.queryOne(id);
		if(null!=media&&media.size()>0){
			media.remove("_id");
			media.put(Media.SRC,"共享素材");
			media.put(Media.CUSERID, copy.getUserId());
			media.put(Media.CUSENAME, copy.getUserName());
			media.put(Media.UUSERID, copy.getUserId());
			media.put(Media.UUSERNAME, copy.getUserName());
			String date = DateUtil.getCurrentTime();
			media.put(Media.UUTIME, date);
			media.put(Media.CTIME, date);
			String copyid = mediaDao.insertMedia(media);
			if(!StringUtil.isEmpty(copyid)){
				systemLogService.inset(SystemLogUtil.getMap(copy, "0", "复制", "素材《"+media.get(Media.NAME)+"》"));
				executeSuccess(resObj);
			}
			return resObj;
		}else{
			return resObj;
		}
	}

	@Override
	public ResponseObject queryByUser(CommonParameters queryByUser, String strJson) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(queryByUser, mapJson, "", MaterialSet.CTIME);
		whereMap.put(MaterialSet.CUSERID, queryByUser.getUserId());
		// 排序参数
		Map<String, Object> sortFilter = getSortParam(Media.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		Long total = MaterialSetDao.count(whereMap);
		List<Map<String, Object>> lists = MaterialSetDao.query(sortFilter, whereMap, currentPage, pageNum);
		if(null!=lists &&lists.size()>=0){
			Pages pages = new Pages(pageNum,total,currentPage,lists);
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,pages);
		}else{
			ResponseObject resObj =new ResponseObject(GeneralStatus.query_error.status,GeneralStatus.query_error.enDetail,"");
			executeSuccess(resObj, lists);
			logger.info("查询返回内容："+resObj);
			return resObj;
			
		}
	}

	@Override
	public ResponseObject queryMediaList(CommonParameters query, String json) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(json);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		// 获取业务参数,并进行封装
		String companyId = String.valueOf(query.getCompanyId());
		if (!StringUtil.isEmpty(query.getCompanyId()) && !StringUtil.isEmpty(companyId)) {
			whereMap.put(BasicObject.CONSUMERID, companyId);
		}
		String keyWord = Media.NAME;
		// 关键字
		String keyWordFlag = String.valueOf(mapJson.get("keyWord"));
		if (!StringUtil.isEmpty(keyWord) && !StringUtil.isEmpty(mapJson.get("keyWord")) && !StringUtil.isEmpty(keyWordFlag)) {
			String regxValue = ".*" + keyWordFlag + ".*";
			Map<String, Object> regxMap = new HashMap<String, Object>();
			regxMap.put("$regex", regxValue);
			regxMap.put("$options", "i");
			whereMap.put(keyWord, regxMap);
		}
		String keyTime = Media.CTIME;
		// 开始时间
		String startTime = String.valueOf(mapJson.get("startTime"));
		// 结束时间
		String endTime = String.valueOf(mapJson.get("endTime"));
		if (!StringUtil.isEmpty(keyTime) && !StringUtil.isEmpty(mapJson.get("startTime")) && !StringUtil.isEmpty(startTime)
				&& !StringUtil.isEmpty(mapJson.get("endTime")) && !StringUtil.isEmpty(endTime)) {
			Map<String, Object> mapTime = new HashMap<String, Object>();
			mapTime.put(QueryOperators.GTE, startTime);
			mapTime.put(QueryOperators.LTE, endTime);
			whereMap.put(keyTime, mapTime);
		}else if (!StringUtil.isEmpty(keyTime) && !StringUtil.isEmpty(mapJson.get("startTime")) && !StringUtil.isEmpty(startTime)) {
			Map<String, Object> gteMap = new HashMap<String, Object>();
			gteMap.put(QueryOperators.GTE, startTime);
			whereMap.put(keyTime, gteMap);
		}else if (!StringUtil.isEmpty(keyTime) && !StringUtil.isEmpty(mapJson.get("endTime")) && !StringUtil.isEmpty(endTime)) {
			Map<String, Object> lteMap = new HashMap<String, Object>();
			lteMap.put(QueryOperators.LTE, endTime);
			whereMap.put(keyTime, lteMap);
		}
		whereMap.put(Media.SRC, String.valueOf(mapJson.get(MediaVo.SRC)));
		// 排序参数
		Map<String, Object> sortFilter = getSortParam(Media.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		Long total = mediaDao.count(whereMap);
		List<Map<String, Object>> lists = null;
		String appCode = query.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {// 区分手机端和pc端
			Map<String, Object> mapBack = getMediaMapPhone();
			lists = mediaDao.query(sortFilter, whereMap, mapBack, currentPage, pageNum);
		}else{
			lists = mediaDao.query(sortFilter, whereMap, currentPage, pageNum);
		}
		//保存日志
//		systemLogService.inset(SystemLogUtil.getMap(query, "0", "查询", "查询《"+log_mtype+"》"));
		if(null!=lists &&lists.size()>=0){
			Pages pages = new Pages(pageNum,total,currentPage,lists);
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,pages);
		}else{
			ResponseObject resObj =new ResponseObject(GeneralStatus.query_error.status,GeneralStatus.query_error.enDetail,"");
			executeSuccess(resObj, lists);
			return resObj;
		}
	}
}
