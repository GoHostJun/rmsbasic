package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.IPushTaskDao;
import com.cdvcloud.rms.dao.ITaskDao;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.PushTask;
import com.cdvcloud.rms.domain.Task;
import com.cdvcloud.rms.service.IAnalysisService;
import com.cdvcloud.rms.service.ICallBackService;
import com.cdvcloud.rms.service.IDirectPassingService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.service.INewsService;
import com.cdvcloud.rms.service.IPushService;
import com.cdvcloud.rms.service.ITranscodeService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.UserUtil;
import com.mongodb.BasicDBObject;

@Service
public class CallBackServiceImpl implements ICallBackService {
	private static final Logger logger = Logger.getLogger(CallBackServiceImpl.class);
	@Autowired
	private ITaskDao taskDao;

	@Autowired
	private IMediaDao mediaDao;

	@Autowired
	private ITranscodeService transcodeService;

	@Autowired
	private IAnalysisService analysisService;

	@Autowired
	private IHistoricalTaskService historicalTaskService;

	@Autowired
	private IDirectPassingService directPassingService;

	@Autowired
	private IWeChatService weChatService;
	@Autowired
	private IPushService pushService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPushTaskDao pushTaskDao;
	@Autowired
	private INewsService newsService;
	@Override
	public void  manageScreenshot(String taskId, String status, String pointscreen) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Task.TASKID, taskId);
		Map<String, Object> task = taskDao.query(whereMap);
		CommonParameters common = new CommonParameters();
		common.setAppCode(String.valueOf(task.get(Task.APPCODE)));
		common.setCompanyId(String.valueOf(task.get(Task.COMPANYID)));
		common.setUserId(String.valueOf(task.get(Task.USERID)));
		common.setVersionId(String.valueOf(task.get(Task.VERSIONID)));
		common.setServiceCode(String.valueOf(task.get(Task.SERVICECODE)));
		logger.info("截图：task=" + task);
		String mediaid = String.valueOf(task.get(Task.MID));
		// 查询素材表
		Map<String, Object> mediaMap = mediaDao.queryOne(mediaid);
		// 取出历史任务id
		String historicalTaskId = String.valueOf(mediaMap.get(Media.TASKID));
		// 历史任务查询条件
		Map<String, Object> historicalTaskWhereMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(historicalTaskId)) {
			historicalTaskWhereMap.put(HistoricalTask.ID, new ObjectId(historicalTaskId));
		}
		// 历史任务修改条件
		Map<String, Object> historicalTaskSetMap = new HashMap<String, Object>();
		// 截图失败 ！ 成功：status等于2
		if (!"2".equals(status)) {
			if (!StringUtil.isEmpty(historicalTaskId)) {
				historicalTaskSetMap.put(HistoricalTask.STATUS, "1");
				historicalTaskSetMap.put(HistoricalTask.ERRORMESSAGE, "截图错误:底层截图失败");
				historicalTaskService.update(whereMap, historicalTaskSetMap, String.valueOf(historicalTaskSetMap.get(HistoricalTask.ERRORMESSAGE)));
			}
			return;
		}
		Map<String, Object> set = new HashMap<String, Object>();
		List<Map<String, Object>> point = JsonUtil.readJSON2MapList(pointscreen);
		// 压缩图片
		//	CompressPicUtil compressPicUtil = new CompressPicUtil();
		//		String outputDir = Configurations.getConfig("STREAM_FILE_REPOSITORY") + File.separator;
		//		String picName = UUID.randomUUID().toString() + ".jpg";
		logger.info("压缩图片原地址：" + point.get(0).get("imageUrl"));

		//String picret = compressPicUtil.compressPic(String.valueOf(point.get(0).get("imageUrl")), outputDir, picName, 160, 90, true);
		String picret =String.valueOf(point.get(0).get("imageUrl"));
		String vslt = picret;
		//logger.info("压缩图片，图片地址：" + outputDir + picName + "图片压缩返回内容：" + picret);
		//		if ("ok".equals(picret)) {
		//			String fileName = "";
		//			String mtype = "2";
		//			Map<String, Object> picRet = ossService.uploadOss(common, outputDir + picName, fileName, mtype);
		//			if (null != picRet) {
		//				vslt = String.valueOf(picRet.get(Media.WANURL));
		//			}
		//		}
		// 保存视频缩略图
		if ("".equals(vslt)) {
			set.put(Media.VSLT, String.valueOf(point.get(0).get("imageUrl")));
		} else {
			set.put(Media.VSLT, vslt);
		}
		List<Map<String, Object>> analysisPoints = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < point.size(); i++) {
			Map<String, Object> analysis = analysisService.getAnalysis(common, String.valueOf(point.get(i).get("imageUrl")));// 分析
			Map<String, Object> analysispoint = new LinkedHashMap<String, Object>();
			analysispoint.put(Media.TRANSCODE_ATTR_WIDTH, analysis.get(Media.WIDTH));
			analysispoint.put(Media.TRANSCODE_ATTR_HEIGHT, analysis.get(Media.HEIGHT));
			analysispoint.put("pixels", analysis.get(Media.WIDTH) + "*" + analysis.get(Media.HEIGHT));
			analysispoint.put(Media.TRANSCODE_ATTR_WANURL, String.valueOf(point.get(i).get("imageUrl")));
			analysispoint.put(Media.TRANSCODE_ATTR_LENURL, String.valueOf(point.get(i).get("imageUrl")));
			if (i == 0) {
				analysispoint.put(Media.TRANSCODE_ATTR_TYPE, "1");
			} else {
				analysispoint.put(Media.TRANSCODE_ATTR_TYPE, "");
			}
			analysisPoints.add(analysispoint);
		}
		set.put(Media.THUMBNAILS, analysisPoints);
		long index = mediaDao.updateOne(mediaid, set, true);
		logger.info("截图返回更新数据条数：" + index);
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized void manageTranscode(String taskId, String status, String fixedInfo) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put(Task.TASKID, taskId);
		Map<String, Object> taskmap = taskDao.query(query);
		if (null == taskmap || taskmap.isEmpty()) {
			logger.error("没有获取到任务记录！taskmap=" + taskmap);
			return;
		}
		logger.info("回调任务id="+taskId+"，回调状态status="+status+"，任务表内容：" + taskmap);
		CommonParameters common = new CommonParameters();
		common.setAppCode(String.valueOf(taskmap.get(Task.APPCODE)));
		common.setCompanyId(String.valueOf(taskmap.get(Task.COMPANYID)));
		common.setUserId(String.valueOf(taskmap.get(Task.USERID)));
		common.setVersionId(String.valueOf(taskmap.get(Task.VERSIONID)));
		String mediaid = String.valueOf(taskmap.get(Task.MID));
		List<Map<String, Object>> https = (List<Map<String, Object>>) taskmap.get(Task.TRANSCODEURL);
		// 查询媒体
		Map<String, Object> media = mediaDao.queryOne(mediaid);
		// 历史任务修改条件
		Map<String, Object> setMap = new HashMap<String, Object>();
		Map<String, Object> set = new HashMap<String, Object>();
		// 取出历史任务id
		String historicalTaskId = String.valueOf(media.get(Media.TASKID));
		// 历史任务查询条件
		Map<String, Object> whereMap = new HashMap<String, Object>();
		try {
			if (!StringUtil.isEmpty(historicalTaskId)) {
				whereMap.put(HistoricalTask.ID, new ObjectId(historicalTaskId));
			}
			if ("2".equals(status)) {
				List<Map<String, Object>> defaults = new ArrayList<Map<String, Object>>();
				logger.info("https=" + https);
				for (Map<String, Object> http : https) {
					Map<String, Object> output = (Map<String, Object>) taskmap.get(Task.OUTPUT);
					logger.info("outputs=" + output);
					Set<String> setkey = output.keySet();
					for (String string : setkey) {
						logger.info("output=" + http.get(string));
						Map<String, Object> desturls = (Map<String, Object>) http.get(string);
						if (null != desturls) {
							Map<String, Object> analysis = analysisService.getAnalysis(common, String.valueOf(desturls.get("lenDestUrl")));
							logger.info("转码回调分析完成");
							// 比对转码回来的文件是否跟源文件时常一样
							double duration = Double.valueOf(String.valueOf(analysis.get(Media.DURATION)));
							double media_Duration = Double.valueOf(String.valueOf(media.get(Media.DURATION)));
							if ("flv".equals(String.valueOf(analysis.get(Media.FMT)))) {
								if (media_Duration - duration < 1000) {
									logger.info("视频时间比对成功！flv时常：" + duration + ";源文件时常：" + media_Duration);
									Map<String, Object> def = new HashMap<String, Object>();
									def.put(Media.TRANSCODE_ATTR_NAME, media.get(Media.NAME));
									def.put(Media.TRANSCODE_ATTR_FMT, analysis.get(Media.FMT));
									def.put(Media.TRANSCODE_ATTR_DURATION, analysis.get(Media.DURATION));
									def.put(Media.TRANSCODE_ATTR_WIDTH, analysis.get(Media.WIDTH));
									def.put(Media.TRANSCODE_ATTR_HEIGHT, analysis.get(Media.HEIGHT));
									def.put(Media.TRANSCODE_ATTR_ARATE, analysis.get(Media.ARATE));
									def.put(Media.TRANSCODE_ATTR_RATE, analysis.get(Media.RATE));
									def.put(Media.TRANSCODE_ATTR_SIZE, analysis.get(Media.SIZE));
									def.put(Media.TRANSCODE_ATTR_FRAME, analysis.get(Media.FRAME));
									def.put(Media.TRANSCODE_ATTR_FORMAT, analysis.get(Media.FORMAT));
									def.put(Media.TRANSCODE_ATTR_VEDIOSIZE, analysis.get(Media.VEDIOSIZE));
									def.put(Media.TRANSCODE_ATTR_LENURL, desturls.get("lenDestUrl"));
									def.put(Media.TRANSCODE_ATTR_WANURL, desturls.get("wanDestUrl"));
									def.put(Media.TRANSCODE_ATTR_CTYPE, output.get(string));
									defaults.add(def);
								} else {
									logger.info("视频时间比对失败！flv时常：" + duration + ";源文件时常：" + media_Duration);
									set.put(Media.STATUS, GeneralStatus.failure.status);
									setMap.put(HistoricalTask.STATUS, "1");
									setMap.put(HistoricalTask.ERRORMESSAGE, "转码错误:源视频和转码回来视频时常不一致");
									logger.info("更新数据内容：" + set);
									mediaDao.updateOne(mediaid, set, true);
									if (!StringUtil.isEmpty(historicalTaskId)) {
										historicalTaskService.update(whereMap, setMap, null);
									}
									updateTaskDate(query);
									return;
								}
							} else {
								Map<String, Object> def = new HashMap<String, Object>();
								def.put(Media.TRANSCODE_ATTR_NAME, media.get(Media.NAME));
								def.put(Media.TRANSCODE_ATTR_FMT, analysis.get(Media.FMT));
								def.put(Media.TRANSCODE_ATTR_DURATION, analysis.get(Media.DURATION));
								def.put(Media.TRANSCODE_ATTR_WIDTH, analysis.get(Media.WIDTH));
								def.put(Media.TRANSCODE_ATTR_HEIGHT, analysis.get(Media.HEIGHT));
								def.put(Media.TRANSCODE_ATTR_ARATE, analysis.get(Media.ARATE));
								def.put(Media.TRANSCODE_ATTR_RATE, analysis.get(Media.RATE));
								def.put(Media.TRANSCODE_ATTR_SIZE, analysis.get(Media.SIZE));
								def.put(Media.TRANSCODE_ATTR_FRAME, analysis.get(Media.FRAME));
								def.put(Media.TRANSCODE_ATTR_FORMAT, analysis.get(Media.FORMAT));
								def.put(Media.TRANSCODE_ATTR_VEDIOSIZE, analysis.get(Media.VEDIOSIZE));
								def.put(Media.TRANSCODE_ATTR_LENURL, desturls.get("lenDestUrl"));
								def.put(Media.TRANSCODE_ATTR_WANURL, desturls.get("wanDestUrl"));
								def.put(Media.TRANSCODE_ATTR_CTYPE, output.get(string));
								defaults.add(def);
							}
						}
					}
				}
				logger.info("转码都完成，outputType:" + String.valueOf(taskmap.get(Task.OUTPUTTYPE)));
				if (!StringUtil.isEmpty(taskmap.get(Task.OUTPUTTYPE)) && "0".equals(String.valueOf(taskmap.get(Task.OUTPUTTYPE)))) {
					set.put(Media.PREWAR, defaults.get(0));
				}
				if (!StringUtil.isEmpty(taskmap.get(Task.OUTPUTTYPE)) && "1".equals(String.valueOf(taskmap.get(Task.OUTPUTTYPE)))) {
					set.put(Media.DEFAULTS, defaults);
					sentNTMPlace(media, defaults, common);
				}
				if (!StringUtil.isEmpty(taskmap.get(Task.OUTPUTTYPE)) && "2".equals(String.valueOf(taskmap.get(Task.OUTPUTTYPE)))) {
					set.put(Media.PUBLISH, defaults);

				}
				Map<String, Object> tSet = new HashMap<String, Object>();
				Map<String, Object> mapSet = new HashMap<String, Object>();
				tSet.put(Task.STATUS, "0");
				mapSet.put(QueryOperators.SET, tSet);
				taskDao.update(query, mapSet);
				logger.info("任务表更新数据内容成功：" + mapSet+"回调任务id="+taskId);
			} else {
				set.put(Media.STATUS, GeneralStatus.failure.status);
				setMap.put(HistoricalTask.STATUS, "1");
				setMap.put(HistoricalTask.ERRORMESSAGE, "转码错误:底层转码失败");
				updateTaskDate(query);
			}
			// -----查询任务是否都完成
			// mid
			Map<String, Object> taskWhereMap = new HashMap<String, Object>();
			taskWhereMap.put(Task.MID, mediaid);
			BasicDBObject dateConditionSex = new BasicDBObject();
			dateConditionSex.append(QueryOperators.NE, "0"); // 状态 !=0，0表示任务已完成
			taskWhereMap.put(Task.STATUS, dateConditionSex);
			List<Map<String, Object>> tasks = taskDao.queryList(taskWhereMap);
			setMap.put(HistoricalTask.STATUS, "0");
			if (null != tasks && tasks.size() > 0) {
				for (Map<String, Object> map : tasks) {
					String tstatus = String.valueOf(map.get(Task.STATUS));
					if ("2".equals(tstatus)) {
						set.put(Media.STATUS, GeneralStatus.processing.status);
						break;
					} else if ("1".equals(tstatus)) {
						set.put(Media.STATUS, GeneralStatus.failure.status);
						setMap.put(HistoricalTask.STATUS, "1");
						break;
					} else {
						set.put(Media.STATUS, GeneralStatus.success.status);
						continue;
					}
				}
			} else {
				logger.info("没有获取到任务信息，任务全部处理成功，执行更新数据素材表状态！");
				set.put(Media.STATUS, GeneralStatus.success.status);
			}

			logger.info("更新数据内容：" + set);
			long index = mediaDao.updateOne(mediaid, set, true);
			logger.info("转码回调更新数据条数：" + index);
			logger.info("转码回调Media.STATUS：" + String.valueOf(set.get(Media.STATUS)));
			if("1".equals(String.valueOf(set.get(Media.STATUS)))){
				Map<String,Object> mediaMap=mediaDao.queryOne(mediaid);
				weChatService.pushUploadWx(mediaMap);
			}
			if("0".equals(String.valueOf(set.get(Media.STATUS)))){
				//选择直传的，直接转码完成直接推送newsphere
				if (media.containsKey(Media.DIRECTNEWS) && !StringUtil.isEmpty(String.valueOf(media.get(Media.DIRECTNEWS)))) {
					pushService.sendToJSDirectNewsphere(mediaid, common);
				}
			}
			if (!StringUtil.isEmpty(historicalTaskId)) {
				historicalTaskService.update(whereMap, setMap, String.valueOf(setMap.get(HistoricalTask.ERRORMESSAGE)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			set.put(Media.STATUS, GeneralStatus.failure.status);
			setMap.put(HistoricalTask.STATUS, "1");
			setMap.put(HistoricalTask.ERRORMESSAGE, "转码错误:未知错误");
			logger.info("更新数据内容：" + set);
			long index = mediaDao.updateOne(mediaid, set, true);
			logger.info("转码回调更新数据条数：" + index);
			if (!StringUtil.isEmpty(historicalTaskId)) {
				historicalTaskService.update(whereMap, setMap, String.valueOf(setMap.get(HistoricalTask.ERRORMESSAGE)));
			}
		}
	}

	/**
	 * 更新任务表 
	 */
	private void updateTaskDate(Map<String, Object> query){
		Map<String, Object> tSet = new HashMap<String, Object>();
		Map<String, Object> mapSet = new HashMap<String, Object>();
		tSet.put(Task.STATUS, "1");
		mapSet.put(QueryOperators.SET, tSet);
		taskDao.update(query, mapSet);
		logger.info("任务表更新数据内容成功：" + mapSet);
	}

	/**
	 * 推送NTM 
	 */
	private void sentNTMPlace(Map<String, Object> media, List<Map<String, Object>> defaults, CommonParameters common) {
		// 终端的转完可以发送ntm
		if (media.containsKey(Media.DIRECTIDS) && !StringUtil.isEmpty(String.valueOf(media.get(Media.DIRECTIDS)))) {
			String directIds = String.valueOf(media.get(Media.DIRECTIDS));
			String fileName = "";
			String destFile = "";
			String size = "";
			String srcFile = "";
			for (Map<String, Object> map : defaults) {
				if ("hd".equals(map.get(Media.TRANSCODE_ATTR_CTYPE))) {
					fileName = String.valueOf(map.get(Media.NAME)) + "." + map.get(Media.FMT);
					// 获取用户信息
					Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
					UserUtil.getUserInfo(common, userMap);
					destFile = "\\input\\"+common.getLoginName()+"\\"+ DateUtil.toISODateString(new Date()) + "\\" + fileName;
					size = String.valueOf(map.get(Media.TRANSCODE_ATTR_SIZE));
					srcFile = String.valueOf(map.get(Media.TRANSCODE_ATTR_LENURL));
				}
			}
			logger.info("素材接口调用迁移服务开始");
			directPassingService.postRemove(srcFile, fileName, size, String.valueOf(media.get(Media.MD5)), directIds,
					String.valueOf(media.get(Media.MTYPE)), destFile, common);
			logger.info("素材接口调用迁移服务结束");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manageFastedit(String taskId, String status, String fixedInfo) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put(Task.MID, fixedInfo);
		Map<String, Object> taskmap = taskDao.query(query);
		if (null == taskmap || taskmap.isEmpty()) {
			logger.error("没有获取到任务记录！taskmap="+taskmap);
			return;
		}
		logger.info("任务表内容：" + taskmap);
		CommonParameters common = new CommonParameters();
		common.setAppCode(String.valueOf(taskmap.get(Task.APPCODE)));
		common.setCompanyId(String.valueOf(taskmap.get(Task.COMPANYID)));
		common.setUserId(String.valueOf(taskmap.get(Task.USERID)));
		common.setVersionId(String.valueOf(taskmap.get(Task.VERSIONID)));
		String mediaid = String.valueOf(taskmap.get(Task.MID));
		Map<String, Object> set = new HashMap<String, Object>();
		// 任务历史表查询条件
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(HistoricalTask.ID, new ObjectId(String.valueOf(taskmap.get(Task.HISTORICALTASKID))));
		Map<String, Object> setMap = new HashMap<String, Object>();
		Map<String, Object> mediaMap = mediaDao.queryOne(mediaid);
		if ("2".equals(status)) {
			Map<String, Object> https = (Map<String, Object>) taskmap.get(Task.TRANSCODEURL);
			String url = String.valueOf(https.get("wanDestUrl"));
			Map<String, Object> analysis = analysisService.getAnalysis(common, url);
			set.put(Media.SIZE, Double.valueOf(String.valueOf(analysis.get(Media.SIZE))));
			set.put(Media.DURATION, Double.valueOf(String.valueOf(analysis.get(Media.DURATION))));
			set.put(Media.WIDTH, analysis.get(Media.WIDTH));
			set.put(Media.FMT, analysis.get(Media.FMT));
			set.put(Media.HEIGHT, analysis.get(Media.HEIGHT));
			set.put(Media.FRAME, analysis.get(Media.FRAME));
			set.put(Media.RATE, analysis.get(Media.RATE));
			set.put(Media.FORMAT, analysis.get(Media.FORMAT));
			set.put(Media.VEDIOSIZE, analysis.get(Media.VEDIOSIZE));
			set.put(Media.LENURL, https.get("lenDestUrl"));
			set.put(Media.WANURL, https.get("wanDestUrl"));
			// set.put(Media.STATUS,GeneralStatus.success.status);
			long index = mediaDao.updateOne(mediaid, set, true);
			logger.info("快编回调更新数据条数：" + index);
			// -------------记录历史任务--------------
			setMap.put(HistoricalTask.STATUS, "0");
			historicalTaskService.update(whereMap, setMap, null);// 更新快编任务完成
			Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
			historicalTaskMap.put(HistoricalTask.APPCODE, common.getAppCode());
			historicalTaskMap.put(HistoricalTask.COMPANYID, common.getCompanyId());
			historicalTaskMap.put(HistoricalTask.VERSIONID, common.getVersionId());
			historicalTaskMap.put(HistoricalTask.USERID, common.getUserId());
			historicalTaskMap.put(HistoricalTask.SERVICECODE, common.getServiceCode());
			// historicalTaskMap.put(HistoricalTask.CONSUMERID, productCode);
			historicalTaskMap.put(HistoricalTask.FILENAME, mediaMap.get(Media.NAME));
			// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
			// historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL,
			// fileDepositUrl);
			historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
			historicalTaskMap.put(HistoricalTask.STATUS, "2");
			// historicalTaskMap.put(HistoricalTask.TYPE, type);
			// historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
			historicalTaskMap.put(HistoricalTask.SRC, "快编任务-转码");
			String historicalTaskId = historicalTaskService.inset(historicalTaskMap);
			logger.info("快编任务-转码：manageFastedit；历史任务记录数据插入返回id=" + historicalTaskId);
			// -------------记录历史任务--------------
			transcodeService.taskTranscode(common, mediaid, "0", url, historicalTaskId);
		} else {
			set.put(Media.STATUS, GeneralStatus.failure.status);
			long index = mediaDao.updateOne(mediaid, set, true);
			setMap.put(HistoricalTask.STATUS, "1");
			setMap.put(HistoricalTask.ERRORMESSAGE, "快编错误:底层快编失败");
			historicalTaskService.update(whereMap, setMap, String.valueOf(setMap.get(HistoricalTask.ERRORMESSAGE)));
			logger.info("转码回调更新数据条数：" + index);
		}
	}

	@Override
	public void managePushTranscode(String taskId, String status, String fixedInfo) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put(Task.TASKID, taskId);
		query.put(Task.TYPE, 1);
		Map<String, Object> taskmap = taskDao.query(query);
		if (null == taskmap || taskmap.isEmpty()) {
			logger.error("没有获取到任务记录！taskmap=" + taskmap);
			return;
		}
		logger.info("回调任务id="+taskId+"，回调状态status="+status+"，任务表内容：" + taskmap);
		CommonParameters common = new CommonParameters();
		common.setAppCode(String.valueOf(taskmap.get(Task.APPCODE)));
		common.setCompanyId(String.valueOf(taskmap.get(Task.COMPANYID)));
		common.setUserId(String.valueOf(taskmap.get(Task.USERID)));
		common.setVersionId(String.valueOf(taskmap.get(Task.VERSIONID)));
		String mediaid = String.valueOf(taskmap.get(Task.MID));
		List<Map<String, Object>> https = (List<Map<String, Object>>) taskmap.get(Task.TRANSCODEURL);
		// 查询媒体
		Map<String, Object> media = mediaDao.queryOne(mediaid);
		Map<String, Object> set = new HashMap<String, Object>();

		try {
			if ("2".equals(status)) {
				List<Map<String, Object>> defaults = new ArrayList<Map<String, Object>>();
				logger.info("https=" + https);
				for (Map<String, Object> http : https) {
					Map<String, Object> output = (Map<String, Object>) taskmap.get(Task.OUTPUT);
					logger.info("outputs=" + output);
					Set<String> setkey = output.keySet();
					for (String string : setkey) {
						logger.info("output=" + http.get(string));
						Map<String, Object> desturls = (Map<String, Object>) http.get(string);
						if (null != desturls) {
							Map<String, Object> analysis = analysisService.getAnalysis(common, String.valueOf(desturls.get("lenDestUrl")));
							logger.info("转码回调分析完成");
							// 比对转码回来的文件是否跟源文件时常一样
							Map<String, Object> def = new HashMap<String, Object>();
							def.put(Media.TRANSCODE_ATTR_NAME, media.get(Media.NAME));
							def.put(Media.TRANSCODE_ATTR_FMT, analysis.get(Media.FMT));
							def.put(Media.TRANSCODE_ATTR_DURATION, analysis.get(Media.DURATION));
							def.put(Media.TRANSCODE_ATTR_WIDTH, analysis.get(Media.WIDTH));
							def.put(Media.TRANSCODE_ATTR_HEIGHT, analysis.get(Media.HEIGHT));
							def.put(Media.TRANSCODE_ATTR_ARATE, analysis.get(Media.ARATE));
							def.put(Media.TRANSCODE_ATTR_RATE, analysis.get(Media.RATE));
							def.put(Media.TRANSCODE_ATTR_SIZE, analysis.get(Media.SIZE));
							def.put(Media.TRANSCODE_ATTR_FRAME, analysis.get(Media.FRAME));
							def.put(Media.TRANSCODE_ATTR_FORMAT, analysis.get(Media.FORMAT));
							def.put(Media.TRANSCODE_ATTR_VEDIOSIZE, analysis.get(Media.VEDIOSIZE));
							def.put(Media.TRANSCODE_ATTR_LENURL, desturls.get("lenDestUrl"));
							def.put(Media.TRANSCODE_ATTR_WANURL, desturls.get("wanDestUrl"));
							def.put(Media.TRANSCODE_ATTR_CTYPE, output.get(string));
							defaults.add(def);
						}
					}
				}
				logger.info("转码都完成，outputType:" + String.valueOf(taskmap.get(Task.OUTPUTTYPE)));
				if (!StringUtil.isEmpty(taskmap.get(Task.OUTPUTTYPE)) && "1".equals(String.valueOf(taskmap.get(Task.OUTPUTTYPE)))) {
					Map<String, Object> optmap = new HashMap<String, Object>();//第一个操作符
					Map<String, Object> optmap1 = new HashMap<String, Object>();//第一个操作符
					optmap1.put(QueryOperators.EACH, defaults);
					optmap.put(Media.DEFAULTS,optmap1);
					set.put(QueryOperators.ADDTOSET, optmap);//update需要更新的
					Map<String, Object> where = new HashMap<String, Object>();
					where.put(Media.ID, new ObjectId(String.valueOf(taskmap.get(Task.MID))));
					long index = mediaDao.update(where, set);
					logger.info("转码回调更新数据条数：" + index);
				}
				Map<String, Object> tSet = new HashMap<String, Object>();
				Map<String, Object> mapSet = new HashMap<String, Object>();
				tSet.put(Task.STATUS, "0");
				mapSet.put(QueryOperators.SET, tSet);
				taskDao.update(query, mapSet);
				logger.info("任务表更新数据内容成功：" + mapSet+"回调任务id="+taskId);
			} else {
				set.put(Media.STATUS, GeneralStatus.failure.status);
				updateTaskDate(query);
				//修改任务未失败
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put(PushTask.ID, fixedInfo);
				Map<String, Object> update = new HashMap<String, Object>();
				update.put(PushTask.STATUS, 4);
				update.put(PushTask.UUTIME,DateUtil.getCurrentDateTime());
				pushTaskDao.updateManyBySet(filter, update, true);
			}
			// -----查询任务是否都完成
			// mid
			Map<String, Object> taskWhereMap = new HashMap<String, Object>();
			taskWhereMap.put(Task.PUSHTASKID, fixedInfo);
			BasicDBObject dateConditionSex = new BasicDBObject();
			dateConditionSex.append(QueryOperators.NE, "0"); // 状态 !=0，0表示任务已完成
			taskWhereMap.put(Task.STATUS, dateConditionSex);
			List<Map<String, Object>> tasks = taskDao.queryList(taskWhereMap);
			if (null != tasks && tasks.size() > 0) {
				for (Map<String, Object> map : tasks) {
					String tstatus = String.valueOf(map.get(Task.STATUS));
					if ("2".equals(tstatus)) {
						set.put(Media.STATUS, GeneralStatus.processing.status);
						break;
					} else if ("1".equals(tstatus)) {
						set.put(Media.STATUS, GeneralStatus.failure.status);
						break;
					} else {
						set.put(Media.STATUS, GeneralStatus.success.status);
						continue;
					}
				}
			} else {
				logger.info("没有获取到任务信息，任务全部处理成功，执行更新数据素材表状态！");
				set.put(Media.STATUS, GeneralStatus.success.status);
				Map<String, Object> pushMap =  pushTaskDao.findOne(fixedInfo);
				CommonParameters commonParameters = new CommonParameters();
				commonParameters.setUserId(String.valueOf(pushMap.get(PushTask.CUSERID)));
				commonParameters.setUserName(String.valueOf(pushMap.get(PushTask.CUSENAME)));
				commonParameters.setCompanyId(String.valueOf(pushMap.get(PushTask.CONSUMERID)));
				commonParameters.setConsumerName(String.valueOf(pushMap.get(PushTask.CONSUMERNAME)));
				commonParameters.setDepartmentId((List<Object>)pushMap.get(PushTask.DEPARTMENTID));
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put(PushTask.ID, new ObjectId(fixedInfo));
				Map<String, Object> update = new HashMap<String, Object>();
				update.put(PushTask.STATUS, 3);
				pushTaskDao.updateManyBySet(filter, update, true);
				//发送推送
				ResponseObject res = newsService.pushNews(commonParameters, String.valueOf(pushMap.get(PushTask.JSON)));
				if(res.getCode()==0){
					update.put(PushTask.STATUS, 0);
				}else{
					update.put(PushTask.STATUS, 1);
				}
				update.put(PushTask.UUTIME,DateUtil.getCurrentDateTime());
				pushTaskDao.updateManyBySet(filter, update, true);
			}

//			logger.info("更新数据内容：" + set);
//			long index = mediaDao.updateOne(mediaid, set, true);
//			logger.info("转码回调更新数据条数：" + index);
//			logger.info("转码回调Media.STATUS：" + String.valueOf(set.get(Media.STATUS)));
//			if("1".equals(String.valueOf(set.get(Media.STATUS)))){
//				Map<String,Object> mediaMap=mediaDao.queryOne(mediaid);
//				weChatService.pushUploadWx(mediaMap);
//			}
//			if("0".equals(String.valueOf(set.get(Media.STATUS)))){
//				//选择直传的，直接转码完成直接推送newsphere
//				if (media.containsKey(Media.DIRECTNEWS) && !StringUtil.isEmpty(String.valueOf(media.get(Media.DIRECTNEWS)))) {
//					pushService.sendToJSDirectNewsphere(mediaid, common);
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
			set.put(Media.STATUS, GeneralStatus.failure.status);
			logger.info("更新数据内容：" + set);
		}
	}

}
