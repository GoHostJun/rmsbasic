package com.cdvcloud.rms.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.cdvcloud.rms.common.Configuration;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.ITaskDao;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.Task;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IAnalysisService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.IOssService;
import com.cdvcloud.rms.service.IScreenShotService;
import com.cdvcloud.rms.service.ITranscodeService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.upload.config.Configurations;
@Service
public class TranscodeServiceImpl implements ITranscodeService {
	private static final Logger logger = Logger.getLogger(TranscodeServiceImpl.class);
	@Autowired
	ConfigurationService configurationService;
	@Autowired
	ITaskDao taskDao;
	@Autowired
	IAnalysisService analysisService;
	@Autowired
	IMediaService mediaService;
	@Autowired
	IScreenShotService ScreenShotService;
	@Autowired
	private IOssService ossService;
	@Autowired
	private IHistoricalTaskService historicalTaskService;
	@Autowired
	private IMediaDao mediaDao;
	/**
	 * 创建转码
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> addTranscode(CommonParameters common,String srcFileUrl,
			String fixedInfo,String outputType,List<String> output,String isSplit,String historicalTaskId) {
		if(StringUtil.verificationParameters(srcFileUrl,output)){
			return null;
		}
		//获取转码工具集地址
		String url =configurationService.getDigitalFactoryAPI();
		//拼装转码url地址
		url+=common.getOldPublicAddress("ZHUANM")+"transcode/task/create/";
		//回调地址
		String callbackUrl="";
		if(Configurations.isOpenCollback()){
			callbackUrl=Configurations.getCollback()+"/collback/transcode/";
		}else{
			callbackUrl=configurationService.getCallback()+"/collback/transcode/";

		}
		String priority="5";
		String cf_priority = Configuration.getConfigValue("ZM_PRIORITY");
		if(!StringUtil.isEmpty(cf_priority)){
			priority=cf_priority;
		}
		//拼装转码参数
		//做编码
		try {
			srcFileUrl=URLEncoder.encode(srcFileUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String param="accessToken="+configurationService.getToken(common)+"&timeStamp="+System.currentTimeMillis()+"&srcFileUrl="+srcFileUrl+"&callbackUrl="+callbackUrl+"&priority="+priority+
				"&fixedInfo="+fixedInfo+"&output="+output+"&isSplit="+isSplit;
		//保存到重试表里，去掉token和时间戳，在从新下发位置拼装
		String hparam = "srcFileUrl="+srcFileUrl+"&callbackUrl="+callbackUrl+"&priority="+priority+
				"&fixedInfo="+fixedInfo+"&output="+output+"&isSplit="+isSplit;
		//历史任务查询条件
		Map<String, Object> historicalTaskWhereMap = new HashMap<String, Object>();
		historicalTaskWhereMap.put(HistoricalTask.ID, new ObjectId(historicalTaskId));
		//历史任务修改条件
		Map<String, Object> historicalTaskSetMap = new HashMap<String, Object>();
		historicalTaskSetMap.put(HistoricalTask.TRANSCODEURL, url);
		historicalTaskSetMap.put(HistoricalTask.TRANSCODEPARAM, hparam);
		//修改历史任务表，增加转码url和参数
		historicalTaskService.update(historicalTaskWhereMap, historicalTaskSetMap,null);

		logger.info("发送转码任务url="+url+";内容="+param);
		String ret = HttpUtil.sendPost(url, param);
		logger.info("发送转码任务返回="+ret);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = JsonUtil.readJSON2Map(ret);
		if(!StringUtil.isEmpty(map)&&"0".equals(String.valueOf(map.get("code")))){
			Map<String, Object> data = (Map<String, Object>)map.get("data");
			result.put("taskId", String.valueOf(data.get("taskId")));
			//			List<String> https = (List<String>)data.get("destUrl");
			//			result.put("https", https);
			//			List<Map<String, Object>> https = (List<Map<String, Object>>)data.get("destUrl");
			List<Map<String, Object>> https = new ArrayList<Map<String,Object>>();
			List<String> http = (List<String>)data.get("destUrl");
			for (String str : http) {
				String[] a = str.split("_");//切分地址
				String b = a[1].split("\\.")[0];//模板id
				Map<String, Object> index = new HashMap<String, Object>();
				Map<String, Object> ht = new HashMap<String, Object>();
				ht.put("lenDestUrl", str);
				ht.put("wanDestUrl", str);
				index.put(b, ht);
				https.add(index);
			}
			result.put("https", https);
			result.put("outputType",outputType);
		}
		return result;
	}

	//	@Override
	//	public String addTranscodeAutomatic(CommonParameters common,
	//			String fileName ,String mtype,String filePath,String src,String remark,String taskid,String md5) {
	//		//传oss
	//		filePath = FileUtil.getSystemPath(filePath);
	//		Map<String, Object> ret= ossService.uploadOss(common, filePath, fileName,mtype);
	//		if(ret!=null){
	//			String srcFileUrl = String.valueOf(ret.get(Media.LENURL));
	//			//先分析，然后截图，在转码 
	//			Map<String, Object> analysis =  analysisService.getAnalysis(common, srcFileUrl);//分析
	//			Map<String, Object> mediaMap = new HashMap<String, Object>();
	//			mediaMap.put(Media.NAME, FileUtil.getFileName(fileName));
	//			String size = String.valueOf(analysis.get(Media.SIZE));
	//			if(!StringUtil.isEmpty(size)){
	//				mediaMap.put(Media.SIZE, Double.valueOf(String.valueOf(analysis.get(Media.SIZE))));
	//			}else{
	//				mediaMap.put(Media.SIZE, "--");
	//			}
	//			String duration = String.valueOf(analysis.get(Media.DURATION));
	//			if(!StringUtil.isEmpty(duration)){
	//				mediaMap.put(Media.DURATION, Double.valueOf(String.valueOf(analysis.get(Media.DURATION))));
	//			}else{
	//				mediaMap.put(Media.DURATION, "--");
	//			}
	//			mediaMap.put(Media.WIDTH, analysis.get(Media.WIDTH));
	//			mediaMap.put(Media.FMT, analysis.get(Media.FMT));
	//			mediaMap.put(Media.SRC, src);
	//			mediaMap.put(Media.HEIGHT, analysis.get(Media.HEIGHT));
	//			mediaMap.put(Media.FRAME, analysis.get(Media.FRAME));
	//			mediaMap.put(Media.RATE, analysis.get(Media.RATE));
	//			mediaMap.put(Media.FORMAT, analysis.get(Media.FORMAT));
	//			mediaMap.put(Media.VEDIOSIZE, analysis.get(Media.VEDIOSIZE));
	//			mediaMap.put(Media.STATUS, GeneralStatus.processing.status);
	//			mediaMap.put(Media.ISDEL, Constants.ZERO);
	//			mediaMap.put(Media.CTIME, DateUtil.getCurrentDateTime());
	//			mediaMap.put(Media.MTYPE, mtype);
	//			mediaMap.put(Media.CUSERID, common.getUserId());
	//			mediaMap.put(Media.CUSENAME, common.getUserName());
	//			mediaMap.put(Media.CONSUMERID, common.getCompanyId());
	//			mediaMap.put(Media.CONSUMERNAME, common.getConsumerName());
	//			String date = DateUtil.getCurrentDateTime();
	//			mediaMap.put(Media.CTIME, date);
	//			mediaMap.put(Media.UUSERID, common.getUserId());
	//			mediaMap.put(Media.UUSERNAME, common.getUserName());
	//			mediaMap.put(Media.UUTIME, date);
	//			mediaMap.put(Media.TASKID, taskid);
	//			mediaMap.put(Media.MD5, md5);
	//			if(null!=remark&&!"".equals(remark)){
	//				mediaMap.put(Media.DESCRIBE, remark);
	//			}
	//			//各种地址
	//			mediaMap.put(Media.WANURL, String.valueOf(ret.get(Media.WANURL)));
	//			mediaMap.put(Media.LENURL, String.valueOf(ret.get(Media.LENURL)));
	//			mediaMap.put(Media.OSSINTERNETURL, String.valueOf(ret.get(Media.OSSINTERNETURL)));
	//			mediaMap.put(Media.OSSLANURL, String.valueOf(ret.get(Media.OSSLANURL)));
	//			mediaMap.put(Media.OSSPROXYURL, String.valueOf(ret.get(Media.OSSPROXYURL)));
	//			mediaMap.put(Media.CDNURL, String.valueOf(ret.get(Media.CDNURL)));
	//			mediaMap.put(Media.FILEPATH, filePath);
	//			//创建媒体数据
	//			String mediaid = mediaService.inset(mediaMap);
	//			if(StringUtil.isEmpty(mediaid)){
	//				return "";
	//			}
	//			taskTranscode(common, mediaid, mtype, srcFileUrl,taskid);
	//			return mediaid;
	//		}
	//
	//		return "";
	//	}
	@Override
	public String addTranscodeAutomatic(Map<String, Object> map) {
		CommonParameters common = (CommonParameters)map.get("common");
		String fileName = String.valueOf(map.get("fileName"));
		String mtype = String.valueOf(map.get("mtype"));
		String taskid = String.valueOf(map.get("taskid"));
		String filePath = String.valueOf(map.get("fileDepositUrl"));
		String md5 = String.valueOf(map.get("md5"));
		String src = String.valueOf(map.get("src"));
		String remark = String.valueOf(map.get("remark"));
		Map<String, Object> other = (Map<String, Object>)common.getOther();
		logger.info("[观察日志]  [查看传参] [other="+other+"]");
		//传oss
		filePath = FileUtil.getSystemPath(filePath);
		Map<String, Object> ret= ossService.uploadOss(common, filePath, fileName,mtype);
		if(ret!=null){
			String srcFileUrl = String.valueOf(ret.get(Media.LENURL));
			//先分析，然后截图，在转码 
			Map<String, Object> analysis =  analysisService.getAnalysis(common, srcFileUrl);//分析
			Map<String, Object> mediaMap = new HashMap<String, Object>();
			mediaMap.put(Media.NAME, FileUtil.getFileName(fileName));
			String size = String.valueOf(analysis.get(Media.SIZE));
			if(!StringUtil.isEmpty(size)){
				mediaMap.put(Media.SIZE, Double.valueOf(String.valueOf(analysis.get(Media.SIZE))));
			}else{
				mediaMap.put(Media.SIZE, "--");
			}
			String duration = String.valueOf(analysis.get(Media.DURATION));
			if(!StringUtil.isEmpty(duration)){
				mediaMap.put(Media.DURATION, Double.valueOf(String.valueOf(analysis.get(Media.DURATION))));
			}else{
				mediaMap.put(Media.DURATION, "--");
			}
			mediaMap.put(Media.WIDTH, analysis.get(Media.WIDTH));
			mediaMap.put(Media.FMT, analysis.get(Media.FMT));
			mediaMap.put(Media.SRC, src);
			mediaMap.put(Media.HEIGHT, analysis.get(Media.HEIGHT));
			mediaMap.put(Media.FRAME, analysis.get(Media.FRAME));
			mediaMap.put(Media.RATE, analysis.get(Media.RATE));
			mediaMap.put(Media.FORMAT, analysis.get(Media.FORMAT));
			mediaMap.put(Media.VEDIOSIZE, analysis.get(Media.VEDIOSIZE));
			mediaMap.put(Media.STATUS, GeneralStatus.processing.status);
			mediaMap.put(Media.ISDEL, Constants.ZERO);
			mediaMap.put(Media.CTIME, DateUtil.getCurrentDateTime());
			mediaMap.put(Media.MTYPE, mtype);
			mediaMap.put(Media.CUSERID, common.getUserId());
			mediaMap.put(Media.CUSENAME, common.getUserName());
			mediaMap.put(Media.CONSUMERID, common.getCompanyId());
			mediaMap.put(Media.CONSUMERNAME, common.getConsumerName());
			String date = DateUtil.getCurrentDateTime();
			mediaMap.put(Media.CTIME, date);
			mediaMap.put(Media.UUSERID, common.getUserId());
			mediaMap.put(Media.UUSERNAME, common.getUserName());
			mediaMap.put(Media.UUTIME, date);
			mediaMap.put(Media.TASKID, taskid);
			if(null!=other&&other.size()>0){
				if(other.containsKey("directIds")){
					mediaMap.put(Media.DIRECTIDS, other.get("directIds"));
				}else{
					mediaMap.put(Media.DIRECTIDS, "");
				}
				if(other.containsKey("toNewp")){
					mediaMap.put(Media.DIRECTNEWS,other.get("toNewp"));
				}
			}

			mediaMap.put(Media.MD5, md5);
			if(null!=remark&&!"".equals(remark)){
				mediaMap.put(Media.DESCRIBE, remark);
			}
			//各种地址
			mediaMap.put(Media.WANURL, String.valueOf(ret.get(Media.WANURL)));
			mediaMap.put(Media.LENURL, String.valueOf(ret.get(Media.LENURL)));
			mediaMap.put(Media.OSSINTERNETURL, String.valueOf(ret.get(Media.OSSINTERNETURL)));
			mediaMap.put(Media.OSSLANURL, String.valueOf(ret.get(Media.OSSLANURL)));
			mediaMap.put(Media.OSSPROXYURL, String.valueOf(ret.get(Media.OSSPROXYURL)));
			mediaMap.put(Media.CDNURL, String.valueOf(ret.get(Media.CDNURL)));
			mediaMap.put(Media.FILEPATH, filePath);
			logger.info("[观察日志]  [查看传参] [mediaMap="+mediaMap+"]");
			//创建媒体数据
			String mediaid = mediaService.inset(mediaMap);
			if(StringUtil.isEmpty(mediaid)){
				return "";
			}
			taskTranscode(common, mediaid, mtype, srcFileUrl,taskid);
			return mediaid;
		}

		return "";
	}
	@Override
	public boolean addTranscodeAutomatic(CommonParameters common,
			String fileName ,String mtype,String filePath,String src,String remark,String taskid,String md5,String uploadUUID) {
		//传oss
		filePath = FileUtil.getSystemPath(filePath);
		Map<String, Object> ret= ossService.uploadOss(common, filePath, fileName,mtype);
		if(ret!=null){
			String srcFileUrl = String.valueOf(ret.get(Media.LENURL));
			//先分析，然后截图，在转码 
			Map<String, Object> analysis =  analysisService.getAnalysis(common, srcFileUrl);//分析
			Map<String, Object> mediaMap = new HashMap<String, Object>();
			mediaMap.put(Media.UPLOADUUID, uploadUUID);
			mediaMap.put(Media.NAME, FileUtil.getFileName(fileName));
			String size = String.valueOf(analysis.get(Media.SIZE));
			if(!StringUtil.isEmpty(size)){
				mediaMap.put(Media.SIZE, Double.valueOf(String.valueOf(analysis.get(Media.SIZE))));
			}else{
				mediaMap.put(Media.SIZE, "--");
			}
			String duration = String.valueOf(analysis.get(Media.DURATION));
			if(!StringUtil.isEmpty(duration)){
				mediaMap.put(Media.DURATION, Double.valueOf(String.valueOf(analysis.get(Media.DURATION))));
			}else{
				mediaMap.put(Media.DURATION, "--");
			}
			mediaMap.put(Media.WIDTH, analysis.get(Media.WIDTH));
			mediaMap.put(Media.FMT, analysis.get(Media.FMT));
			mediaMap.put(Media.SRC, src);
			mediaMap.put(Media.HEIGHT, analysis.get(Media.HEIGHT));
			mediaMap.put(Media.FRAME, analysis.get(Media.FRAME));
			mediaMap.put(Media.RATE, analysis.get(Media.RATE));
			mediaMap.put(Media.FORMAT, analysis.get(Media.FORMAT));
			mediaMap.put(Media.VEDIOSIZE, analysis.get(Media.VEDIOSIZE));
			mediaMap.put(Media.STATUS, GeneralStatus.processing.status);
			mediaMap.put(Media.ISDEL, Constants.ZERO);
			mediaMap.put(Media.CTIME, DateUtil.getCurrentDateTime());
			mediaMap.put(Media.MTYPE, mtype);
			mediaMap.put(Media.CUSERID, common.getUserId());
			mediaMap.put(Media.CUSENAME, common.getUserName());
			mediaMap.put(Media.CONSUMERID, common.getCompanyId());
			mediaMap.put(Media.CONSUMERNAME, common.getConsumerName());
			String date = DateUtil.getCurrentDateTime();
			mediaMap.put(Media.CTIME, date);
			mediaMap.put(Media.UUSERID, common.getUserId());
			mediaMap.put(Media.UUSERNAME, common.getUserName());
			mediaMap.put(Media.UUTIME, date);
			mediaMap.put(Media.TASKID, taskid);
			mediaMap.put(Media.MD5, md5);
			if(null!=remark&&!"".equals(remark)){
				mediaMap.put(Media.DESCRIBE, remark);
			}
			//各种地址
			mediaMap.put(Media.WANURL, String.valueOf(ret.get(Media.WANURL)));
			mediaMap.put(Media.LENURL, String.valueOf(ret.get(Media.LENURL)));
			mediaMap.put(Media.OSSINTERNETURL, String.valueOf(ret.get(Media.OSSINTERNETURL)));
			mediaMap.put(Media.OSSLANURL, String.valueOf(ret.get(Media.OSSLANURL)));
			mediaMap.put(Media.OSSPROXYURL, String.valueOf(ret.get(Media.OSSPROXYURL)));
			mediaMap.put(Media.CDNURL, String.valueOf(ret.get(Media.CDNURL)));
			mediaMap.put(Media.FILEPATH, filePath);
			//创建媒体数据
			String mediaid = mediaService.inset(mediaMap);
			if(StringUtil.isEmpty(mediaid)){
				return false;
			}
			taskTranscode(common, mediaid, mtype, srcFileUrl,taskid);
			return true;
		}

		return false;
	}

	@Override
	public void retryTask(CommonParameters common,String mediaid ,String mtype,String srcFileUrl,String historicalTaskId,String taskType) 
	{
		//创建任务
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put(Task.MID, mediaid);
		taskMap.put(Task.ISTRANSCODE, "1");
		taskMap.put(Task.APPCODE,common.getAppCode());
		taskMap.put(Task.COMPANYID,common.getCompanyId());
		taskMap.put(Task.USERID,common.getUserId());
		taskMap.put(Task.VERSIONID,common.getVersionId());
		taskMap.put(Task.SERVICECODE,common.getServiceCode());
		taskMap.put(Task.SRCFILEURL,srcFileUrl);
		String taskid = taskDao.insertTask(taskMap);
		if(StringUtil.isEmpty(taskid)){
		}
		if("transcode".equals(taskType)){
			Map<String,Object> mediaUpdate=new HashMap<String, Object>();
			mediaUpdate.put(Media.STATUS, GeneralStatus.processing.status);
			long upLong=mediaDao.updateOne(mediaid, mediaUpdate, false);
			//删除历史任务
			Map<String, Object> whereMap=new HashMap<String, Object>();
			whereMap.put(Task.MID, mediaid);
			List<Map<String,Object>> tasks=taskDao.queryList(whereMap);
			for (Map<String, Object> map : tasks) {
				Map<String, Object> filter=new HashMap<String, Object>();
				filter.put(Task.ID, new ObjectId(String.valueOf(map.get(Task.ID))));
				if(map.containsKey(Task.TRANSCODEURL)){
					long l=taskDao.delete(filter);
					if(l==0){
						return ;
					}
				}

			}
			if("0".equals(mtype)||"1".equals(mtype)){
				String fixedInfo = mediaid;
				if("0".equals(mtype)){
					String vprewar = configurationService.getPrewar();
					if(!StringUtil.isEmpty(vprewar)){
						Map<String, Object> map = JsonUtil.readJSON2Map(vprewar);
						Set<String> key = map.keySet();
						List<String> prewars = new ArrayList<String>(key);
						//						String prewars = vprewar;//设置转码模板
						String type = "0";//转码类型 0:预览 ，1:默认，2:终端
						Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, prewars, "",historicalTaskId);
						taskMap.remove(Task.TRANSCODEURL);
						taskMap.remove(Task.TASKID);
						taskMap.remove(Task.OUTPUT);
						taskMap.remove(Task.OUTPUTTYPE);
						//保存转码返回路径
						taskMap.put(Task.TRANSCODEURL, result.get("https"));
						taskMap.put(Task.TASKID, result.get("taskId"));
						taskMap.put(Task.OUTPUT, map);
						taskMap.put(Task.OUTPUTTYPE, type);
						taskMap.put(Task.STATUS,"2");
						//更新服务任务id  
						taskDao.insertTask(taskMap);
					}
					String defults = configurationService.getvideoDefaults();
					if(!StringUtil.isEmpty(defults)){
						Map<String, Object> map = JsonUtil.readJSON2Map(defults);
						Set<String> key = map.keySet();
						List<String> defultss = new ArrayList<String>(key);
						//						String defultss = defults;//设置转码模板
						String type = "1";//转码类型 0:预览 ，1:默认，2:终端
						Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, defultss, "",historicalTaskId);
						taskMap.remove(Task.TRANSCODEURL);
						taskMap.remove(Task.TASKID);
						taskMap.remove(Task.OUTPUT);
						taskMap.remove(Task.OUTPUTTYPE);
						//保存转码返回路径
						taskMap.put(Task.TRANSCODEURL, result.get("https"));
						taskMap.put(Task.TASKID, result.get("taskId"));
						taskMap.put(Task.OUTPUT, map);
						taskMap.put(Task.OUTPUTTYPE, type);
						taskMap.put(Task.STATUS,"2");
						//更新服务任务id  
						taskDao.insertTask(taskMap);

					}
					String publish = configurationService.getvideoPublish();
					if(!StringUtil.isEmpty(publish)){
						Map<String, Object> map = JsonUtil.readJSON2Map(publish);
						Set<String> key = map.keySet();
						List<String> publishs = new ArrayList<String>(key);
						//						String publishs = publish;//设置转码模板
						String type = "2";//转码类型 0:预览 ，1:默认，2:终端
						Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, publishs, "",historicalTaskId);
						taskMap.remove(Task.TRANSCODEURL);
						taskMap.remove(Task.TASKID);
						taskMap.remove(Task.OUTPUT);
						taskMap.remove(Task.OUTPUTTYPE);
						//保存转码返回路径
						taskMap.put(Task.TRANSCODEURL, result.get("https"));
						taskMap.put(Task.TASKID, result.get("taskId"));
						taskMap.put(Task.OUTPUT, map);
						taskMap.put(Task.OUTPUTTYPE, type);
						taskMap.put(Task.STATUS,"2");

						//更新服务任务id  
						taskDao.insertTask(taskMap);
					}
				}
				if("1".equals(mtype)){
					String audioPrewar = configurationService.getaudioPrewar();
					if(!StringUtil.isEmpty(audioPrewar)){
						Map<String, Object> map = JsonUtil.readJSON2Map(audioPrewar);
						Set<String> key = map.keySet();
						List<String> aprewar = new ArrayList<String>(key);
						//						String aprewar = audioPrewar;//设置转码模板
						String type = "0";//转码类型 0:预览 ，1:默认，2:终端
						Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, aprewar, "",historicalTaskId);
						taskMap.remove(Task.TRANSCODEURL);
						taskMap.remove(Task.TASKID);
						taskMap.remove(Task.OUTPUT);
						taskMap.remove(Task.OUTPUTTYPE);
						//保存转码返回路径
						taskMap.put(Task.TRANSCODEURL, result.get("https"));
						taskMap.put(Task.TASKID, result.get("taskId"));
						taskMap.put(Task.OUTPUT, map);
						taskMap.put(Task.OUTPUTTYPE, type);
						taskMap.put(Task.STATUS,"2");

						//更新服务任务id  
						taskDao.insertTask(taskMap);
					}
					String defults = configurationService.getaudioDefaults();
					if(!StringUtil.isEmpty(defults)){
						Map<String, Object> map = JsonUtil.readJSON2Map(defults);
						Set<String> key = map.keySet();
						List<String> defultss = new ArrayList<String>(key);
						//						String defultss = defults;//设置转码模板
						String type = "1";//转码类型 0:预览 ，1:默认，2:终端
						Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, defultss, "",historicalTaskId);
						taskMap.remove(Task.TRANSCODEURL);
						taskMap.remove(Task.TASKID);
						taskMap.remove(Task.OUTPUT);
						taskMap.remove(Task.OUTPUTTYPE);
						//保存转码返回路径
						taskMap.put(Task.TRANSCODEURL, result.get("https"));
						taskMap.put(Task.TASKID, result.get("taskId"));
						taskMap.put(Task.OUTPUT, map);
						taskMap.put(Task.OUTPUTTYPE, type);
						taskMap.put(Task.STATUS,"2");

						//更新服务任务id  
						taskDao.insertTask(taskMap);
					}
					String publish = configurationService.getaudioPublish();
					if(!StringUtil.isEmpty(publish)){
						Map<String, Object> map = JsonUtil.readJSON2Map(publish);
						Set<String> key = map.keySet();
						List<String> publishs = new ArrayList<String>(key);
						//						String publishs = publish;//设置转码模板
						String type = "2";//转码类型 0:预览 ，1:默认，2:终端
						Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, publishs, "",historicalTaskId);
						taskMap.remove(Task.TRANSCODEURL);
						taskMap.remove(Task.TASKID);
						taskMap.remove(Task.OUTPUT);
						taskMap.remove(Task.OUTPUTTYPE);
						//保存转码返回路径
						taskMap.put(Task.TRANSCODEURL, result.get("https"));
						taskMap.put(Task.TASKID, result.get("taskId"));
						taskMap.put(Task.OUTPUT, map);
						taskMap.put(Task.OUTPUTTYPE, type);
						taskMap.put(Task.STATUS,"2");

						//更新服务任务id  
						taskDao.insertTask(taskMap);
					}
				}

			}

		}else if("screenshot".equals(taskType)){

			//截图
			String id = ScreenShotService.getShotByCount(common, srcFileUrl,"6", taskid, "400*225",historicalTaskId);
			Map<String, Object> set = new HashMap<String, Object>();
			set.put(Task.TASKID, id);//保存任务id 在回调时候查询任务用
			taskDao.updateOne(taskid, set,true);

		}

	}
	@Override
	public void taskTranscode(CommonParameters common,
			String mediaid ,String mtype,String srcFileUrl,String historicalTaskId){
		//创建任务
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put(Task.MID, mediaid);
		taskMap.put(Task.ISTRANSCODE, "1");
		taskMap.put(Task.APPCODE,common.getAppCode());
		taskMap.put(Task.COMPANYID,common.getCompanyId());
		taskMap.put(Task.USERID,common.getUserId());
		taskMap.put(Task.VERSIONID,common.getVersionId());
		taskMap.put(Task.SERVICECODE,common.getServiceCode());
		taskMap.put(Task.SRCFILEURL,srcFileUrl);
		String taskid = taskDao.insertTask(taskMap);
		if(StringUtil.isEmpty(taskid)){
		}

		//转码
		//		Map<String, Object> result=this.addTranscode(common, srcFileUrl, "",optputType, output, "false");
		//---下发转码---
		if("0".equals(mtype)||"1".equals(mtype)){
			String fixedInfo = mediaid;
			if("0".equals(mtype)){
				String vprewar = configurationService.getPrewar();
				if(!StringUtil.isEmpty(vprewar)){
					Map<String, Object> map = JsonUtil.readJSON2Map(vprewar);
					Set<String> key = map.keySet();
					List<String> prewars = new ArrayList<String>(key);
					//					String prewars = vprewar;//设置转码模板
					String type = "0";//转码类型 0:预览 ，1:默认，2:终端
					Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, prewars, "",historicalTaskId);
					taskMap.remove(Task.TRANSCODEURL);
					taskMap.remove(Task.TASKID);
					taskMap.remove(Task.OUTPUT);
					taskMap.remove(Task.OUTPUTTYPE);
					//保存转码返回路径
					taskMap.put(Task.TRANSCODEURL, result.get("https"));
					taskMap.put(Task.TASKID, result.get("taskId"));
					taskMap.put(Task.OUTPUT, map);
					taskMap.put(Task.OUTPUTTYPE, type);
					taskMap.put(Task.STATUS,"2");
					//更新服务任务id  
					taskDao.insertTask(taskMap);
				}
				String defults = configurationService.getvideoDefaults();
				if(!StringUtil.isEmpty(defults)){
					Map<String, Object> map = JsonUtil.readJSON2Map(defults);
					Set<String> key = map.keySet();
					List<String> defultss = new ArrayList<String>(key);
					//					String defultss = defults;//设置转码模板
					String type = "1";//转码类型 0:预览 ，1:默认，2:终端
					Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, defultss, "",historicalTaskId);
					taskMap.remove(Task.TRANSCODEURL);
					taskMap.remove(Task.TASKID);
					taskMap.remove(Task.OUTPUT);
					taskMap.remove(Task.OUTPUTTYPE);
					//保存转码返回路径
					taskMap.put(Task.TRANSCODEURL, result.get("https"));
					taskMap.put(Task.TASKID, result.get("taskId"));
					taskMap.put(Task.OUTPUT, map);
					taskMap.put(Task.OUTPUTTYPE, type);
					taskMap.put(Task.STATUS,"2");
					//更新服务任务id  
					taskDao.insertTask(taskMap);

				}
				String publish = configurationService.getvideoPublish();
				if(!StringUtil.isEmpty(publish)){
					Map<String, Object> map = JsonUtil.readJSON2Map(publish);
					Set<String> key = map.keySet();
					List<String> publishs = new ArrayList<String>(key);
					//					String publishs = publish;//设置转码模板
					String type = "2";//转码类型 0:预览 ，1:默认，2:终端
					Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, publishs, "",historicalTaskId);
					taskMap.remove(Task.TRANSCODEURL);
					taskMap.remove(Task.TASKID);
					taskMap.remove(Task.OUTPUT);
					taskMap.remove(Task.OUTPUTTYPE);
					//保存转码返回路径
					taskMap.put(Task.TRANSCODEURL, result.get("https"));
					taskMap.put(Task.TASKID, result.get("taskId"));
					taskMap.put(Task.OUTPUT, map);
					taskMap.put(Task.OUTPUTTYPE, type);
					taskMap.put(Task.STATUS,"2");

					//更新服务任务id  
					taskDao.insertTask(taskMap);
				}
				//截图
				String id = ScreenShotService.getShotByCount(common, srcFileUrl,"6", taskid, "400*225",historicalTaskId);
				Map<String, Object> set = new HashMap<String, Object>();
				set.put(Task.TASKID, id);//保存任务id 在回调时候查询任务用
				taskDao.updateOne(taskid, set,true);
			}
			if("1".equals(mtype)){
				String audioPrewar = configurationService.getaudioPrewar();
				if(!StringUtil.isEmpty(audioPrewar)){
					Map<String, Object> map = JsonUtil.readJSON2Map(audioPrewar);
					Set<String> key = map.keySet();
					List<String> aprewar = new ArrayList<String>(key);
					//					String aprewar = audioPrewar;//设置转码模板
					String type = "0";//转码类型 0:预览 ，1:默认，2:终端
					Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, aprewar, "",historicalTaskId);
					taskMap.remove(Task.TRANSCODEURL);
					taskMap.remove(Task.TASKID);
					taskMap.remove(Task.OUTPUT);
					taskMap.remove(Task.OUTPUTTYPE);
					//保存转码返回路径
					taskMap.put(Task.TRANSCODEURL, result.get("https"));
					taskMap.put(Task.TASKID, result.get("taskId"));
					taskMap.put(Task.OUTPUT, map);
					taskMap.put(Task.OUTPUTTYPE, type);
					taskMap.put(Task.STATUS,"2");

					//更新服务任务id  
					taskDao.insertTask(taskMap);
				}
				String defults = configurationService.getaudioDefaults();
				if(!StringUtil.isEmpty(defults)){
					Map<String, Object> map = JsonUtil.readJSON2Map(defults);
					Set<String> key = map.keySet();
					List<String> defultss = new ArrayList<String>(key);
					//					String defultss = defults;//设置转码模板
					String type = "1";//转码类型 0:预览 ，1:默认，2:终端
					Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, defultss, "",historicalTaskId);
					taskMap.remove(Task.TRANSCODEURL);
					taskMap.remove(Task.TASKID);
					taskMap.remove(Task.OUTPUT);
					taskMap.remove(Task.OUTPUTTYPE);
					//保存转码返回路径
					taskMap.put(Task.TRANSCODEURL, result.get("https"));
					taskMap.put(Task.TASKID, result.get("taskId"));
					taskMap.put(Task.OUTPUT, map);
					taskMap.put(Task.OUTPUTTYPE, type);
					taskMap.put(Task.STATUS,"2");

					//更新服务任务id  
					taskDao.insertTask(taskMap);
				}
				String publish = configurationService.getaudioPublish();
				if(!StringUtil.isEmpty(publish)){
					Map<String, Object> map = JsonUtil.readJSON2Map(publish);
					Set<String> key = map.keySet();
					List<String> publishs = new ArrayList<String>(key);
					//					String publishs = publish;//设置转码模板
					String type = "2";//转码类型 0:预览 ，1:默认，2:终端
					Map<String, Object> result=this.addTranscode(common, srcFileUrl, fixedInfo,type, publishs, "",historicalTaskId);
					taskMap.remove(Task.TRANSCODEURL);
					taskMap.remove(Task.TASKID);
					taskMap.remove(Task.OUTPUT);
					taskMap.remove(Task.OUTPUTTYPE);
					//保存转码返回路径
					taskMap.put(Task.TRANSCODEURL, result.get("https"));
					taskMap.put(Task.TASKID, result.get("taskId"));
					taskMap.put(Task.OUTPUT, map);
					taskMap.put(Task.OUTPUTTYPE, type);
					taskMap.put(Task.STATUS,"2");

					//更新服务任务id  
					taskDao.insertTask(taskMap);
				}
			}

		}
	}

	@Override
	public String TranscodeQuery(CommonParameters common,String id) {
		String progress = "0";
		//获取转码工具集地址
		String url =configurationService.getDigitalFactoryAPI();
		//拼装转码url地址
		url+=common.getOldPublicAddress("ZHUANM")+"transcode/task/query/";
		
		String param="accessToken="+configurationService.getToken(common)+"&timeStamp="+System.currentTimeMillis()+"&taskId="+id;
//		logger.info("发送转码任务url="+url+";内容="+param);
		String ret = HttpUtil.sendPost(url, param);
//		logger.info("发送转码任务返回="+ret);
		
		Map<String, Object> map = JsonUtil.readJSON2Map(ret);
		if(!StringUtil.isEmpty(map)&&"0".equals(String.valueOf(map.get("code")))){
			Map<String, Object> data = (Map<String, Object>)map.get("data");
			if(null!=data && data.containsKey("progress")){
				progress= String.valueOf(data.get("progress"));
			}
		}
		return progress;
	}

	@Override
	public Map<String, Object> addPushTranscode(CommonParameters common, String srcFileUrl, String fixedInfo, String outputType, List<String> output,
			String isSplit) {
		if(StringUtil.verificationParameters(srcFileUrl,output)){
			return null;
		}
		//获取转码工具集地址
		String url =configurationService.getDigitalFactoryAPI();
		//拼装转码url地址
		url+=common.getOldPublicAddress("ZHUANM")+"transcode/task/create/";
		//回调地址
		String callbackUrl=configurationService.getCallback()+"/collback/pushTranscode/";

		String priority="5";
		String cf_priority = Configuration.getConfigValue("ZM_PRIORITY");
		if(!StringUtil.isEmpty(cf_priority)){
			priority=cf_priority;
		}
		//拼装转码参数
		//做编码
		try {
			srcFileUrl=URLEncoder.encode(srcFileUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String param="accessToken="+configurationService.getToken(common)+"&timeStamp="+System.currentTimeMillis()+"&srcFileUrl="+srcFileUrl+"&callbackUrl="+callbackUrl+"&priority="+priority+
				"&fixedInfo="+fixedInfo+"&output="+output+"&isSplit="+isSplit;

		logger.info("发送推送定制转码任务url="+url+";内容="+param);
		String ret = HttpUtil.sendPost(url, param);
		logger.info("发送推送定制任务返回="+ret);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = JsonUtil.readJSON2Map(ret);
		if(!StringUtil.isEmpty(map)&&"0".equals(String.valueOf(map.get("code")))){
			Map<String, Object> data = (Map<String, Object>)map.get("data");
			result.put("taskId", String.valueOf(data.get("taskId")));
			//			List<String> https = (List<String>)data.get("destUrl");
			//			result.put("https", https);
			//			List<Map<String, Object>> https = (List<Map<String, Object>>)data.get("destUrl");
			List<Map<String, Object>> https = new ArrayList<Map<String,Object>>();
			List<String> http = (List<String>)data.get("destUrl");
			for (String str : http) {
				String[] a = str.split("_");//切分地址
				String b = a[1].split("\\.")[0];//模板id
				Map<String, Object> index = new HashMap<String, Object>();
				Map<String, Object> ht = new HashMap<String, Object>();
				ht.put("lenDestUrl", str);
				ht.put("wanDestUrl", str);
				index.put(b, ht);
				https.add(index);
			}
			result.put("https", https);
			result.put("outputType",outputType);
		}
		return result;
	}
}
