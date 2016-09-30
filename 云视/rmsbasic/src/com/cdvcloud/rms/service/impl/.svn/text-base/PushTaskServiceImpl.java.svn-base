package com.cdvcloud.rms.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.MediaVo;
import com.cdvcloud.rms.common.vo.NewsVo;
import com.cdvcloud.rms.common.vo.PushVo;
import com.cdvcloud.rms.dao.IFieldNewsDao;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.dao.IPresentationDao;
import com.cdvcloud.rms.dao.IPushSetDao;
import com.cdvcloud.rms.dao.IPushTaskDao;
import com.cdvcloud.rms.dao.ITaskDao;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.domain.PushSet;
import com.cdvcloud.rms.domain.PushTask;
import com.cdvcloud.rms.domain.Task;
import com.cdvcloud.rms.service.INewsService;
import com.cdvcloud.rms.service.IPushTaskService;
import com.cdvcloud.rms.service.ITranscodeService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
@Service
public class PushTaskServiceImpl extends BasicService implements IPushTaskService {
	private static final Logger logger = Logger.getLogger(PushTaskServiceImpl.class);

	@Autowired
	private IPresentationDao materialDao;
	@Autowired
	private IFieldNewsDao fieldNewsDao;
	@Autowired
	private INewsDao newsDao;
	@Autowired
	private IPushTaskDao pushTaskDao;
	@Autowired
	private IPushSetDao pushSetDao;
	@Autowired
	private INewsService newsService;
	@Autowired
	private ITranscodeService transcodeService;
	@Autowired
	private IMediaDao mediaDao;

	@Autowired
	private ITaskDao taskDao;
	@Override
	public ResponseObject findList(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, PushTask.TITLE, PushTask.CTIME);
		// id
		String mediaIds = String.valueOf(mapJson.get("ids"));
		if (null != mediaIds && !"null".equals(mediaIds) && !"".equals(mediaIds)) {
			String[] materialIdArray = mediaIds.split(",");
			BasicDBList basicDBList = new BasicDBList();
			for (String materialId : materialIdArray) {
				basicDBList.add(new ObjectId(materialId));
			}
			whereMap.put(PushTask.ID, new BasicDBObject("$in", basicDBList));
		}
		String userId = commonParameters.getUserId();
		// 排序参数
		Map<String, Object> sortMap = getSortParam(PushTask.CTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		// 获取通联列表，区分手机端和pc端
		List<Map<String, Object>> results = null;
		results = pushTaskDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = pushTaskDao.countObject(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject insert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject queryProgress(CommonParameters common, String json) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(json);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		// id
		String mediaIds = String.valueOf(mapJson.get("ids"));
		if (null != mediaIds && !"null".equals(mediaIds) && !"".equals(mediaIds)) {
			String[] materialIdArray = mediaIds.split(",");
			List<Map<String, String>> progress = new ArrayList<Map<String,String>>();
			int tasksize =0;
			for (String string : materialIdArray) {
				Map<String, String> taskmap = new HashMap<String, String>();
				taskmap.put("id", string);
				whereMap.put(Task.PUSHTASKID, string);
				List<Map<String, Object>> task = taskDao.queryList(whereMap);
				DecimalFormat    df   = new DecimalFormat("######0.00");  
				double count = 0;
				for (Map<String, Object> map : task) {
					if(null!=map&&map.containsKey(Task.OUTPUT)){
						tasksize+=1;
						String taskid = String.valueOf(map.get(Task.TASKID));
						String index = transcodeService.TranscodeQuery(common, taskid);
						count+=Double.valueOf(index);
//						logger.info("调用接口获得：index="+index+"|count="+count);
					}
				}
				if(count>0){
					count=	count/tasksize;
				}
//				logger.info("count="+count+"|tasksize="+tasksize);
				taskmap.put("progress", df.format(count)+"%");
				progress.add(taskmap);
			}
			ResponseObject resObj = new ResponseObject();
			executeSuccess(resObj, progress);
			return resObj;
		}

		return queryError("null");
	}

	@Override
	public ResponseObject pushVerify(CommonParameters commonParameters, String strJson) {
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
		String othermsg = String.valueOf(mapPushSet.get(PushSet.OTHERMSG));
		String unique = String.valueOf(mapPushSet.get(PushSet.UNIQUENAME));
		String uniqueName = String.valueOf(mapPushSet.get(PushSet.PUSHNAME));

		Map<String, Object> contentMap = new HashMap<String, Object>();
		String id = null;
		if (mapJson.containsKey(NewsVo.NEWSID) && null != mapJson.get(NewsVo.NEWSID)) {
			id = String.valueOf(mapJson.get(NewsVo.NEWSID));
		} else {
			return parameterError("newsId," + strJson);
		}
		Map<String, Object> pushTaskMap = new HashMap<String, Object>();
		// 判断文稿推送类型
		if ("NEWS".equals(pushType)) {// 推送通联
			// 获取通联并执行推送
			pushTaskMap.put(PushTask.SRCNAME, "通联");
			contentMap = newsDao.findOne(id); // 获取通联信息
		} else if ("DOCS".equals(pushType)) {// 推送文稿
			pushTaskMap.put(PushTask.SRCNAME, "文稿");
			contentMap = materialDao.queryOne(id);
		} else if ("SHARENEWS".equals(pushType)) {// 推送共享通联
			pushTaskMap.put(PushTask.SRCNAME, "共享通联");
			contentMap = newsDao.findOne(id); // 获取通联信息
		} else if ("SHAREDOCS".equals(pushType)) {// 推送共享文稿
			pushTaskMap.put(PushTask.SRCNAME, "共享文稿");
			contentMap = fieldNewsDao.findOne(id);
		} else {
			logger.warn("推送失败：没有找到推送文稿类型");
		}

		pushTaskMap.put(PushTask.PUSHID, id);
		pushTaskMap.put(PushTask.CONSUMERID, commonParameters.getCompanyId());
		pushTaskMap.put(PushTask.CONSUMERNAME, commonParameters.getConsumerName());
		pushTaskMap.put(PushTask.CUSERID, commonParameters.getUserId());
		pushTaskMap.put(PushTask.CUSENAME, commonParameters.getUserName());
		pushTaskMap.put(PushTask.DEPARTMENTID, commonParameters.getDepartmentId());
		pushTaskMap.put(PushTask.STATUS, 2);
		pushTaskMap.put(PushTask.SRC, pushType);
		pushTaskMap.put(PushTask.UNIQUE, unique);
		pushTaskMap.put(PushTask.UNIQUENAME, uniqueName);
		pushTaskMap.put(PushTask.CTIME, DateUtil.getCurrentDateTime());
		pushTaskMap.put(PushTask.TITLE, contentMap.get(News.TITLE));
		pushTaskMap.put(PushTask.JSON, strJson);
		String pushTaskId = pushTaskDao.insertObject(pushTaskMap);
		Map<String, Object> othermsgMap = JsonUtil.readJSON2Map(othermsg);
		Map<String, Object> othermsg_video = (Map<String, Object>) othermsgMap.get("video");
		Map<String, Object> push_video = (Map<String, Object>) othermsgMap.get("pushVideo");
		StringBuffer sb_video = new StringBuffer();
		StringBuffer sb_audio = new StringBuffer();
		if(null!=push_video&&push_video.size()>0){
//			logger.info("配置信息：push_video="+push_video);
			Set<String> key = push_video.keySet();
			List<String> output = new ArrayList<String>(key);
			Set<String> video_key = othermsg_video.keySet();
//			logger.info("文稿内容：contentMap="+contentMap);
			if (video_key.contains("mf")) {// 多转码后个文件
				List<Map<String, Object>> videos = (List<Map<String, Object>>) contentMap.get("videos");
				if(null!=videos&&videos.size()>0){
					for (Map<String, Object> video : videos) {
						String videoId = String.valueOf(video.get("_id"));
						Map<String, Object> mediaMap = mediaDao.queryOne(videoId);
						List<Map<String, Object>> defaults = (List<Map<String, Object>>) mediaMap.get("defaults");
						String ctype = String.valueOf(push_video.get(output.get(0))).toLowerCase();
//						logger.info("配置信息：ctype="+ctype);
						boolean temporaryboo = false;
						for (Map<String, Object> map1 : defaults) {
							if (map1.containsKey("ctype") && ctype.equals(String.valueOf(map1.get("ctype")).toLowerCase())) {
//								logger.info("视频 "+map1.get("ctype")+" 类型文件！");
								temporaryboo =true;
								break;
							}
						}
//						logger.info("查询视频是否需要转码："+temporaryboo);
						if(!temporaryboo){
							sb_video.append("false");
							logger.info("视频没有 "+ctype+" 类型文件！开始转码！");
							pushTaskMap.put(PushTask.STATUS, 2);
							//这里下发转码
							Map<String, Object> result=transcodeService.addPushTranscode(commonParameters, String.valueOf(video.get(Media.LENURL)), pushTaskId, 
									"1", output, "");
							//创建任务
							Map<String, Object> taskMap = new HashMap<String, Object>();
							taskMap.put(Task.MID, String.valueOf(video.get("_id")));
							taskMap.put(Task.ISTRANSCODE, "1");
							taskMap.put(Task.APPCODE,commonParameters.getAppCode());
							taskMap.put(Task.COMPANYID,commonParameters.getCompanyId());
							taskMap.put(Task.USERID,commonParameters.getUserId());
							taskMap.put(Task.VERSIONID,commonParameters.getVersionId());
							taskMap.put(Task.SERVICECODE,commonParameters.getServiceCode());
							taskMap.put(Task.SRCFILEURL,String.valueOf(video.get(Media.LENURL)));
							taskMap.put(Task.TRANSCODEURL, result.get("https"));
							taskMap.put(Task.TASKID, result.get("taskId"));
							taskMap.put(Task.OUTPUT, push_video);
							taskMap.put(Task.TYPE, 1);
							taskMap.put(Task.PUSHTASKID, pushTaskId);
							taskMap.put(Task.OUTPUTTYPE, "1");
							taskMap.put(Task.STATUS,"2");
							//更新服务任务id  
							String taskid = taskDao.insertTask(taskMap);
//							logger.info("创建推送任务记录id："+taskid);
						}
					}
				}
			}
		}else{
			logger.info("CompanyId="+commonParameters.getCompanyId()+"|"+commonParameters.getAppCode()+"|uniqueName="+uniqueName+"|pushType="+pushType+" 未配置推送视频转码模板!");
		}
		//音频
		Map<String, Object> othermsg_audio = (Map<String, Object>) othermsgMap.get("audio");
		Map<String, Object> push_audio = (Map<String, Object>) othermsgMap.get("pushAudio");
		if(null!=push_audio&&push_audio.size()>0){
			Set<String> audioKey = push_audio.keySet();
			List<String> audioOutput = new ArrayList<String>(audioKey);
			Set<String> audio_key = othermsg_audio.keySet();
			if (audio_key.contains("mf")) {// 多转码后个文件
				List<Map<String, Object>> audios = (List<Map<String, Object>>) contentMap.get("audios");
				if(null!=audios&&audios.size()>0){
					for (Map<String, Object> audio : audios) {
						String audioId = String.valueOf(audio.get("_id"));
						Map<String, Object> mediaMap = mediaDao.queryOne(audioId);
						List<Map<String, Object>> defaults = (List<Map<String, Object>>) mediaMap.get("defaults");
						String ctype = String.valueOf(push_video.get(audioOutput.get(0))).toLowerCase();
						boolean temporaryboo = false;
						for (Map<String, Object> map1 : defaults) {
							if (map1.containsKey("ctype") && ctype.equals(String.valueOf(map1.get("ctype")).toLowerCase())) {
//								logger.info("音频 "+map1.get("ctype")+" 类型文件！");
								temporaryboo =true;
								break;
							}
						}
						if(!temporaryboo){
							sb_audio.append("false");
							logger.info("音频没有 "+ctype+" 类型文件！开始转码！");
							pushTaskMap.put(PushTask.STATUS, 2);
							//这里下发转码
							Map<String, Object> result=transcodeService.addPushTranscode(commonParameters, String.valueOf(audio.get(Media.LENURL)), pushTaskId, 
									"1", audioOutput, "");
							//创建任务
							Map<String, Object> taskMap = new HashMap<String, Object>();
							taskMap.put(Task.MID, String.valueOf(audio.get("_id")));
							taskMap.put(Task.ISTRANSCODE, "1");
							taskMap.put(Task.APPCODE,commonParameters.getAppCode());
							taskMap.put(Task.COMPANYID,commonParameters.getCompanyId());
							taskMap.put(Task.USERID,commonParameters.getUserId());
							taskMap.put(Task.VERSIONID,commonParameters.getVersionId());
							taskMap.put(Task.SERVICECODE,commonParameters.getServiceCode());
							taskMap.put(Task.SRCFILEURL,String.valueOf(audio.get(Media.LENURL)));
							taskMap.put(Task.TRANSCODEURL, result.get("https"));
							taskMap.put(Task.TASKID, result.get("taskId"));
							taskMap.put(Task.OUTPUT, push_audio);
							taskMap.put(Task.TYPE, 1);
							taskMap.put(Task.PUSHTASKID, pushTaskId);
							taskMap.put(Task.OUTPUTTYPE, "1");
							taskMap.put(Task.STATUS,"2");
							//更新服务任务id  
							String taskid = taskDao.insertTask(taskMap);
//							logger.info("创建推送任务记录id："+taskid);
						}
					}
				}
			}
		}else{
			logger.info("CompanyId="+commonParameters.getCompanyId()+"|"+commonParameters.getAppCode()+"|uniqueName="+uniqueName+"|pushType="+pushType+" 未配置推送音频转码模板!");
		}
		
		if((sb_video.indexOf("false")==-1)&&(sb_audio.indexOf("false")==-1)){
			logger.info("发送通联");
			resObj = newsService.pushNews(commonParameters, strJson);
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(PushTask.ID, new ObjectId(pushTaskId));
			Map<String, Object> update = new HashMap<String, Object>();
			if(resObj.getCode()==0){
				update.put(PushTask.STATUS, 0);
			}else{
				update.put(PushTask.STATUS, 1);
			}
			update.put(PushTask.UUTIME,DateUtil.getCurrentDateTime());
			pushTaskDao.updateManyBySet(filter, update, true);
		}
		executeSuccess(resObj);
		return resObj;
	}
	public static void main(String[] args) {
		StringBuffer a = new StringBuffer();
		System.out.println(a.indexOf("aa"));
	}
}
