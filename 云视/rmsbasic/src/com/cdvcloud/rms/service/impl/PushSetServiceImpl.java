package com.cdvcloud.rms.service.impl;

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
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.PushVo;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.IPushSetDao;
import com.cdvcloud.rms.domain.Catalogue;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.domain.PushSet;
import com.cdvcloud.rms.service.IPushSetService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
@Service
public class PushSetServiceImpl extends BasicService implements IPushSetService {
	private static final Logger logger = Logger.getLogger(PushSetServiceImpl.class);
	@Autowired
	private IPushSetDao pushSetDao;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IMediaDao mediaDao;
	@Override
	public ResponseObject createObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> mapPushSet = createPushSet(commonParameters, mapJson, 0);
		if (null != mapPushSet) {
			super.executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject findObjectAll(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, PushSet.PUSHNAME, PushSet.CTIME);
		whereMap.remove(PushSet.CUSERID);
		whereMap.remove(PushSet.DEPARTMENTID);
		whereMap.put(PushSet.STATUS, Constants.ZERO);
		// 排序参数
		Map<String, Object> sortMap = getSortParam(PushSet.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		List<Map<String, Object>> results = pushSetDao.find(sortMap, whereMap, currentPage, pageNum);
		// 获取总数
		long totalRecord = pushSetDao.countObject(whereMap);
		Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
		super.executeSuccess(resObj, resMap);
		return resObj;
	}

	@Override
	public ResponseObject findObjectById(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数
		String pushSetId = String.valueOf(mapJson.get(PushSet.ID));
		Map<String, Object> pushSet = pushSetDao.findOne(pushSetId);
		if (!pushSet.isEmpty()) {
			executeSuccess(resObj, pushSet);
		} else {
			logger.warn("获取记录为空！pushSetId：" + mapJson);
		}
		return resObj;
	}

	@Override
	public ResponseObject deleteObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数
		String pushSetId = String.valueOf(mapJson.get(PushSet.ID));
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(PushSet.ID, new ObjectId(pushSetId));
		long result = pushSetDao.deleteOne(filter);
		if (result > Constants.ZERO) {
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "删除", "删除推送配置信息《"+pushSetId+"》"));
			executeSuccess(resObj);
		}
		return resObj;
	}

	@Override
	public ResponseObject updateObject(CommonParameters commonParameters, String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		// 获取业务参数,并进行相关业务操作
		String pushSetId = String.valueOf(mapJson.get(PushSet.ID));
		Map<String, Object> mapPushSet = new HashMap<String, Object>();
		if (null != mapJson.get("otherMsg")) {
			mapPushSet.put(PushSet.OTHERMSG, mapJson.get(PushVo.OTHERMSG));
		}
		if (null != mapJson.get("pushName")) {
			mapPushSet.put(PushSet.PUSHNAME, mapJson.get(PushVo.PUSHNAME));
		}
		if (null != mapJson.get("pushUrl")) {
			mapPushSet.put(PushSet.PUSHURL, mapJson.get(PushVo.PUSHURL));
		}
		if (null != mapJson.get("queryUrl")) {
			mapPushSet.put(PushSet.QUERYURL, mapJson.get(PushVo.QUERYURL));
		}
		mapPushSet.put(PushSet.UUTIME, DateUtil.getCurrentDateTime());
		Map<String, Object> filter = new  HashMap<String, Object>();
		filter.put(PushSet.ID, new ObjectId(pushSetId));
		long result = pushSetDao.updateOneBySet(filter, mapPushSet,true);
		if (result > Constants.ZERO) {
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "更新", "更新推送配置信息《"+mapJson.get(PushVo.PUSHNAME)+"》"));
			executeSuccess(resObj);
		}
		return resObj;
	}

	private Map<String, Object> createPushSet(CommonParameters commonParameters, Map<String, Object> mapJson,int status) {
		Map<String, Object> mapPushSet = new HashMap<String, Object>();
		mapPushSet.put(PushSet.OTHERMSG, mapJson.get(PushVo.OTHERMSG));
		mapPushSet.put(PushSet.PUSHNAME, mapJson.get(PushVo.PUSHNAME));
		mapPushSet.put(PushSet.PUSHURL, mapJson.get(PushVo.PUSHURL));
		mapPushSet.put(PushSet.QUERYURL, mapJson.get(PushVo.QUERYURL));
		mapPushSet.put(PushSet.UNIQUENAME, mapJson.get(PushVo.UNIQUENAME));
		mapPushSet.put(PushSet.STATUS, status);
		mapPushSet.put(PushSet.CUSERID, commonParameters.getUserId());
		mapPushSet.put(PushSet.CUSENAME, commonParameters.getUserName());
		mapPushSet.put(PushSet.CTIME, DateUtil.getCurrentDateTime());
		mapPushSet.put(PushSet.UUTIME, DateUtil.getCurrentDateTime());
		mapPushSet.put(PushSet.CONSUMERID, commonParameters.getCompanyId());
		mapPushSet.put(PushSet.CONSUMERNAME, commonParameters.getConsumerName());
		mapPushSet.put(PushSet.DEPARTMENTID, commonParameters.getDepartmentId());
		String pushSetId = pushSetDao.insertObject(mapPushSet);
		if (null != pushSetId) {
			mapPushSet.put(PushSet.ID, pushSetId);
			systemLogService.inset(SystemLogUtil.getMap(commonParameters, "0", "添加", "添加推送配置信息《"+mapJson.get(PushVo.PUSHNAME)+"》"));
			return mapPushSet;
		}
		logger.error("创建记录失败！mapPushSet="+mapPushSet);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getPushAddress(Map<String, Object> pushMap) {
		// Map<String, Object> othermsg = (Map<String, Object>)pushMap.get(PushSet.OTHERMSG);
		String othermsg_sb = String.valueOf(pushMap.get(PushSet.OTHERMSG));
		Map<String, Object> othermsg = JsonUtil.readJSON2Map(othermsg_sb);
		Map<String, Object> cb = new HashMap<String, Object>();
		cb.put(News.TITLE, String.valueOf(pushMap.get(News.TITLE)));
		if (pushMap.containsKey(News.TEMPLATE) && null != pushMap.get(News.TEMPLATE)) {
			cb.put(News.TITLE, pushMap.get(News.TEMPLATE));
		}
		List<Map<String, Object>> videos = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(Catalogue.VIDEOS) && null != pushMap.get(Catalogue.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(Catalogue.VIDEOS);
			Map<String, Object> othermsg_video = (Map<String, Object>) othermsg.get("video");
			Set<String> video_key = othermsg_video.keySet();
			for (Map<String, Object> map1 : videosFlag) {
				//查询素材
				Map<String, Object> media = mediaDao.queryOne(String.valueOf(map1.get(Media.ID)));
				if (video_key.contains("source")) {// 源文件
					Map<String, Object> video = new HashMap<String, Object>();
//					video.putAll(map1);
					video.putAll(media);
					video.remove("defaults");
					video.remove("thumbnails");
					video.remove("prewar");
					video.remove("cataids");
					videos.add(video);
				} else if (video_key.contains("mf")) {// 多转码后个文件
					String ctype = String.valueOf(othermsg_video.get("mf")).toLowerCase();
					List<Map<String, Object>> defaults = (List<Map<String, Object>>) media.get("defaults");
					Map<String, Object> def = new HashMap<String, Object>();
					for (Map<String, Object> map : defaults) {
						map.put(Media.VSLT, media.get(Media.VSLT));
						map.put(Media.CTIME, media.get(Media.CTIME));
						map.put(Media.UUTIME, media.get(Media.UUTIME));
						map.put(Media.ID, media.get(Media.ID));
						if (map.containsKey("ctype") && ctype.contains(String.valueOf(map.get("ctype")).toLowerCase())) {
							videos.add(map);
						} else {
							if (map.containsKey("fmt") && "mp4".equals(String.valueOf(map.get("fmt")))) {
								def.putAll(map);
							} else {
								logger.warn("没有默认推送mp4类型文件！");
							}
						}
					}
					if (null != videos && videos.size() > 0) {

					} else {
						videos.add(def);
					}

				} else if (video_key.contains("all")) {// 全部文件
					Map<String, Object> video_source = new HashMap<String, Object>();
					video_source.putAll(media);
					video_source.remove("defaults");
					video_source.remove("thumbnails");
					video_source.remove("prewar");
					video_source.remove("cataids");
					videos.add(video_source);
					String ctype = String.valueOf(othermsg_video.get("all")).toLowerCase();
					if (!StringUtil.isEmpty(ctype)) {
						Map<String, Object> def = new HashMap<String, Object>();
						List<Map<String, Object>> defaults = (List<Map<String, Object>>) media.get("defaults");
						for (Map<String, Object> map : defaults) {
							map.put(Media.VSLT, media.get(Media.VSLT));
							map.put(Media.CTIME, media.get(Media.CTIME));
							map.put(Media.UUTIME, media.get(Media.UUTIME));
							map.put(Media.ID, media.get(Media.ID));
							if (map.containsKey("ctype") && ctype.contains(String.valueOf(map.get("ctype")).toLowerCase())) {
								videos.add(map);
							} else {
								/** 2016-09-22 修改 ； 修改原因：全部一次转码时间太长，在推送增加转码，如果没有对应文件则发起转码，等待转码完成后在推送 */
//								if (map.containsKey("fmt") && "mp4".equals(String.valueOf(map.get("fmt")))) {
//									def.putAll(map);
//								} else {
//									logger.warn("没有默认推送mp4类型文件！");
//								}
								
							}
						}
						if (null != videos && videos.size() > 0) {

						} else {
							videos.add(def);
						}
					} else {
						// 没有选择全部推送
						List<Map<String, Object>> defaults = (List<Map<String, Object>>) media.get("defaults");
						for (Map<String, Object> map : defaults) {
							map.put(Media.VSLT, media.get(Media.VSLT));
							map.put(Media.CTIME, media.get(Media.CTIME));
							map.put(Media.UUTIME, media.get(Media.UUTIME));
							map.put(Media.ID, media.get(Media.ID));
							videos.add(map);
						}
					}
				} else {
					Map<String, Object> video_source = new HashMap<String, Object>();
					video_source.putAll(media);
					video_source.remove("defaults");
					video_source.remove("thumbnails");
					video_source.remove("prewar");
					video_source.remove("cataids");
					videos.add(video_source);
				}
			}
		}
		cb.put(Catalogue.VIDEOS, videos);
		// 音频
		List<Map<String, Object>> audios = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(Catalogue.AUDIOS) && null != pushMap.get(Catalogue.AUDIOS)) {
			List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) pushMap.get(Catalogue.AUDIOS);
			Map<String, Object> othermsg_audio = (Map<String, Object>) othermsg.get("audio");
			Set<String> audio_key = othermsg_audio.keySet();
			for (Map<String, Object> map1 : audiosFlag) {
				//查询素材
				Map<String, Object> media = mediaDao.queryOne(String.valueOf(map1.get(Media.ID)));
				if (audio_key.contains("source")) {// 源文件
					Map<String, Object> audio = new HashMap<String, Object>();
					audio.putAll(media);
					audio.remove("defaults");
					audio.remove("thumbnails");
					audio.remove("prewar");
					audio.remove("cataids");
					audios.add(audio);
				} else if (audio_key.contains("mf")) {// 多转码后个文件
					String ctype = String.valueOf(othermsg_audio.get("mf")).toLowerCase();
					Map<String, Object> def = new HashMap<String, Object>();
					List<Map<String, Object>> defaults = (List<Map<String, Object>>) media.get("defaults");
					for (Map<String, Object> map : defaults) {
						map.put(Media.CTIME, media.get(Media.CTIME));
						map.put(Media.UUTIME, media.get(Media.UUTIME));
						map.put(Media.ID, media.get(Media.ID));
						if (map.containsKey("ctype") && ctype.contains(String.valueOf(map.get("ctype")).toLowerCase())) {
							audios.add(map);
						} else {
							if (map.containsKey("fmt") && "mp3".equals(String.valueOf(map.get("fmt")))) {
								def.putAll(map);
							} else {
								logger.warn("没有默认推送mp4类型文件！");
							}
						}
					}
					if (null != audios && audios.size() > 0) {

					} else {
						audios.add(def);
					}
				} else if (audio_key.contains("all")) {// 全部文件
					Map<String, Object> audio_source = new HashMap<String, Object>();
					audio_source.putAll(media);
					audio_source.remove("defaults");
					audio_source.remove("thumbnails");
					audio_source.remove("prewar");
					audio_source.remove("cataids");
					audios.add(audio_source);
					String ctype = String.valueOf(othermsg_audio.get("all")).toLowerCase();
					if (!StringUtil.isEmpty(ctype)) {
						List<Map<String, Object>> defaults = (List<Map<String, Object>>) media.get("defaults");
						Map<String, Object> def = new HashMap<String, Object>();
						for (Map<String, Object> map : defaults) {
							map.put(Media.CTIME, media.get(Media.CTIME));
							map.put(Media.UUTIME, media.get(Media.UUTIME));
							map.put(Media.ID, media.get(Media.ID));
							if (map.containsKey("ctype") && ctype.contains(String.valueOf(map.get("ctype")).toLowerCase())) {
								audios.add(map);
							} else {
								if (map.containsKey("fmt") && "mp3".equals(String.valueOf(map.get("fmt")))) {
									def.putAll(map);
								} else {
									logger.warn("没有默认推送mp3类型文件！");
								}
							}
						}
						if (null != audios && audios.size() > 0) {

						} else {
							audios.add(def);
						}
					} else {
						// 没有选择全部推送
						List<Map<String, Object>> defaults = (List<Map<String, Object>>) media.get("defaults");
						for (Map<String, Object> map : defaults) {
							map.put(Media.CTIME, media.get(Media.CTIME));
							map.put(Media.UUTIME, media.get(Media.UUTIME));
							map.put(Media.ID, media.get(Media.ID));
							audios.add(map);
						}
					}
				} else {
					Map<String, Object> audio_source = new HashMap<String, Object>();
					audio_source.putAll(media);
					audio_source.remove("defaults");
					audio_source.remove("thumbnails");
					audio_source.remove("prewar");
					audio_source.remove("cataids");
					audios.add(audio_source);
				}
			}
		}
		cb.put(Catalogue.AUDIOS, audios);
		List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(Catalogue.PICS) && null != pushMap.get(Catalogue.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(Catalogue.PICS);
			Map<String, Object> othermsg_pic = (Map<String, Object>) othermsg.get("pic");
			String suffix = String.valueOf(othermsg_pic.get("suffix")).toLowerCase();
			for (Map<String, Object> mapP : imagesFlag) {
				//查询素材
				Map<String, Object> media = mediaDao.queryOne(String.valueOf(mapP.get(Media.ID)));
				if (!StringUtil.isEmpty(suffix)) {
					if (media.containsKey("fmt") && suffix.contains(String.valueOf(media.get("fmt")).toLowerCase())) {
						images.add(media);
					} else {
						logger.warn("没有《" + suffix + "》推送图片后缀类型文件！");
					}
				} else {
					images.add(media);
				}
			}
		}
		cb.put(Catalogue.PICS, images);
		return cb;
	}

	@Override
	public Map<String, Object> findOne(String type) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(PushSet.UNIQUENAME, type);
		return pushSetDao.findOne(whereMap);
	}
}
