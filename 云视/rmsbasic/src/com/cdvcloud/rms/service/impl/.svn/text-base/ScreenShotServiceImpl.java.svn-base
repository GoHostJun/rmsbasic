package com.cdvcloud.rms.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IAnalysisService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.IOssService;
import com.cdvcloud.rms.service.IScreenShotService;
import com.cdvcloud.rms.util.CompressPicUtil;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.upload.config.Configurations;
@Service
public class ScreenShotServiceImpl implements IScreenShotService {
	private static final Logger logger = Logger.getLogger(ScreenShotServiceImpl.class);
	@Autowired
	ConfigurationService configurationService;
	@Autowired
	IAnalysisService analysisService;
	@Autowired
	IMediaService mediaService;
	@Autowired
	private IOssService ossService;
	@Autowired
	private IHistoricalTaskService historicalTaskService;
	
	@Override
	@SuppressWarnings("unchecked")
	public String getShotByFirst(CommonParameters common,String videoUrl,
			String fixedInfo, String resolution) {
		//获取工具集地址
		String url =configurationService.getDigitalFactoryAPI();
		//拼装首帧截图url地址
		url+=common.getOldPublicAddress("JIET")+"screenShot/shotByFirst/";
		String callbackUrl="";
		if(Configurations.isOpenCollback()){
			callbackUrl=Configurations.getCollback()+"/collback/screenShot/";
		}else{
			callbackUrl=configurationService.getCallback()+"/collback/screenShot/";
			
		}
		//做编码
		try {
			videoUrl=URLEncoder.encode(videoUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//拼装首帧截图参数
		String param="accessToken="+configurationService.getToken(common)+"&timeStamp="+System.currentTimeMillis()+"&videoUrl="+videoUrl+"&callBackUrl="+callbackUrl+"&fixedInfo="+fixedInfo+
				"&resolution="+resolution;
		logger.info("首帧截图url="+url+";内容="+param);
		String ret = HttpUtil.sendPost(url, param);
		logger.info("首帧截图及时返回内容="+ret);
		Map<String, Object> map =  JsonUtil.readJSON2Map(ret);
		if(!StringUtil.isEmpty(map) && !"".equals(map.get("code"))){
			Map<String, Object> data = (Map<String, Object>)map.get("data");
			return String.valueOf(data.get("taskId"));
		}else{
			return "";
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getShotByCount(CommonParameters common,String videoUrl, String count,
			String fixedInfo, String resolution,String historicalTaskId) {
		//获取工具集地址
		String url =configurationService.getDigitalFactoryAPI();
		//拼装定量截图url地址
		url+=common.getOldPublicAddress("JIET")+"screenShot/shotByCount/";
		String callbackUrl="";
		if(Configurations.isOpenCollback()){
			callbackUrl=Configurations.getCollback()+"/collback/screenShot/";
		}else{
			callbackUrl=configurationService.getCallback()+"/collback/screenShot/";
			
		}
		//做编码
		try {
			videoUrl=URLEncoder.encode(videoUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//拼装定量截图参数
		String param="accessToken="+configurationService.getToken(common)+"&timeStamp="+System.currentTimeMillis()+"&videoUrl="+videoUrl+"&count="+count+"&callBackUrl="+callbackUrl+"&fixedInfo="+fixedInfo+
				"&resolution="+resolution;
		//保存到重试表里，去掉token和时间戳，在从新下发位置拼装
				String hparam = "videoUrl="+videoUrl+"&count="+count+"&callBackUrl="+callbackUrl+"&fixedInfo="+fixedInfo+
						"&resolution="+resolution;
		
		//历史任务查询条件
		Map<String, Object> historicalTaskWhereMap = new HashMap<String, Object>();
		historicalTaskWhereMap.put(HistoricalTask.ID, new ObjectId(historicalTaskId));
		//历史任务修改条件
		Map<String, Object> historicalTaskSetMap = new HashMap<String, Object>();
		historicalTaskSetMap.put(HistoricalTask.SCREENURL, url);
		historicalTaskSetMap.put(HistoricalTask.SCREENPARAM, hparam);
		//修改历史任务表，增加转码url和参数
		historicalTaskService.update(historicalTaskWhereMap, historicalTaskSetMap,null);
		
		logger.info("定量截图url="+url+";内容="+param);
		String ret = HttpUtil.sendPost(url, param);
		logger.info("定量截图及时返回内容="+ret);
		Map<String, Object> map =  JsonUtil.readJSON2Map(ret);
		if(!StringUtil.isEmpty(map) && !"".equals(map.get("code"))){
			Map<String, Object> data = (Map<String, Object>)map.get("data");
			return String.valueOf(data.get("taskId"));
		}else{
			return "";
		}
	}
	@Override
	public String addScreenShotAutomatic(CommonParameters common,
			String fileName ,String mtype,String filePath,String src,String remark,String taskid,String md5) {
		//传oss
		filePath = FileUtil.getSystemPath(filePath);
		Map<String, Object> ret= ossService.uploadOss(common, filePath, fileName,mtype);
		if(ret!=null){
			//先分析，然后截图，在转码 
			String date = DateUtil.getCurrentDateTime();
			Map<String, Object> analysis =  analysisService.getAnalysis(common, String.valueOf(ret.get(Media.LENURL)));//分析
			//压缩图片
			CompressPicUtil compressPicUtil = new CompressPicUtil();
			String outputDir =Configurations.getConfig("STREAM_FILE_REPOSITORY");
			String picName = UUID.randomUUID().toString()+".jpg";
			String picret = compressPicUtil.compressPic(String.valueOf(ret.get(Media.LENURL)), outputDir, picName, 400, 225, true);
			String vslt="";
			if("ok".equals(picret)){
				Map<String, Object> picRet= ossService.uploadOss(common, outputDir+picName, fileName,mtype);
				if(null!=picRet){
					vslt = String.valueOf(picRet.get(Media.WANURL));
				}
			}
			Map<String, Object> mediaMap = new HashMap<String, Object>();
			mediaMap.put(Media.NAME, FileUtil.getFileName(fileName));
			String size = String.valueOf(analysis.get(Media.SIZE));
			if(!StringUtil.isEmpty(size)){
				mediaMap.put(Media.SIZE, Double.valueOf(String.valueOf(analysis.get(Media.SIZE))));
			}else{
				mediaMap.put(Media.SIZE, "--");
			}
			mediaMap.put(Media.WIDTH, analysis.get(Media.WIDTH));
			mediaMap.put(Media.HEIGHT, analysis.get(Media.HEIGHT));
			mediaMap.put(Media.FMT, analysis.get(Media.FMT));
			if(null!=analysis.get(Media.WIDTH)&&!StringUtil.isEmpty(String.valueOf(analysis.get(Media.WIDTH)))){
				mediaMap.put(Media.STATUS, GeneralStatus.success.status);
			}else{
				mediaMap.put(Media.STATUS, GeneralStatus.failure.status);
			}
			mediaMap.put(Media.ISDEL, Constants.ZERO);
			mediaMap.put(Media.SRC, src);
			mediaMap.put(Media.CTIME, date);
			mediaMap.put(Media.MTYPE, mtype);
			mediaMap.put(Media.CUSERID, common.getUserId());
			mediaMap.put(Media.CUSENAME, common.getUserName());
			mediaMap.put(Media.CONSUMERID, common.getCompanyId());
			mediaMap.put(Media.CONSUMERNAME, common.getConsumerName());
			mediaMap.put(Media.UUSERID, common.getUserId());
			mediaMap.put(Media.UUSERNAME, common.getUserName());
			mediaMap.put(Media.UUTIME, date);
			mediaMap.put(Media.TASKID, taskid);
			mediaMap.put(Media.MD5, md5);
			if(null!=remark&&!"".equals(remark)){
				mediaMap.put(Media.DESCRIBE, remark);
			}
			
			mediaMap.put(Media.CTIME, DateUtil.getCurrentDateTime());
			mediaMap.put(Media.WANURL, String.valueOf(ret.get(Media.WANURL)));
			if("".equals(vslt)){
				mediaMap.put(Media.VSLT, String.valueOf(ret.get(Media.OSSPROXYURL)));
			}else{
				mediaMap.put(Media.VSLT, vslt);
			}
			mediaMap.put(Media.LENURL, String.valueOf(ret.get(Media.LENURL)));
			mediaMap.put(Media.OSSINTERNETURL, String.valueOf(ret.get(Media.OSSINTERNETURL)));
			mediaMap.put(Media.OSSLANURL, String.valueOf(ret.get(Media.OSSLANURL)));
			mediaMap.put(Media.OSSPROXYURL, String.valueOf(ret.get(Media.OSSPROXYURL)));
			mediaMap.put(Media.CDNURL, String.valueOf(ret.get(Media.CDNURL)));
			mediaMap.put(Media.FILEPATH, filePath);
			
			//创建媒体数据
			String mediaid = mediaService.inset(mediaMap);
			return mediaid;
		}else{
			return "";
		}
	}
	
	@Override
	public boolean addScreenShotAutomatic(CommonParameters common,
			String fileName ,String mtype,String filePath,String src,String remark,String taskid,String md5,String uploadUUID) {
		//传oss
		filePath = FileUtil.getSystemPath(filePath);
		Map<String, Object> ret= ossService.uploadOss(common, filePath, fileName,mtype);
		if(ret!=null){
			//先分析，然后截图，在转码 
			String date = DateUtil.getCurrentDateTime();
			Map<String, Object> analysis =  analysisService.getAnalysis(common, String.valueOf(ret.get(Media.LENURL)));//分析
			//压缩图片
			CompressPicUtil compressPicUtil = new CompressPicUtil();
			String outputDir =Configurations.getConfig("STREAM_FILE_REPOSITORY");
			String picName = UUID.randomUUID().toString()+".jpg";
			String picret = compressPicUtil.compressPic(String.valueOf(ret.get(Media.LENURL)), outputDir, picName, 160, 90, true);
			String vslt="";
			if("ok".equals(picret)){
				Map<String, Object> picRet= ossService.uploadOss(common, outputDir+picName, fileName,mtype);
				if(null!=picRet){
					vslt = String.valueOf(picRet.get(Media.WANURL));
				}
			}
			Map<String, Object> mediaMap = new HashMap<String, Object>();
			mediaMap.put(Media.UPLOADUUID, uploadUUID);
			mediaMap.put(Media.NAME, FileUtil.getFileName(fileName));
			String size = String.valueOf(analysis.get(Media.SIZE));
			if(!StringUtil.isEmpty(size)){
				mediaMap.put(Media.SIZE, Double.valueOf(String.valueOf(analysis.get(Media.SIZE))));
			}else{
				mediaMap.put(Media.SIZE, "--");
			}
			mediaMap.put(Media.WIDTH, analysis.get(Media.WIDTH));
			mediaMap.put(Media.HEIGHT, analysis.get(Media.HEIGHT));
			mediaMap.put(Media.FMT, analysis.get(Media.FMT));
			mediaMap.put(Media.STATUS, GeneralStatus.success.status);
			mediaMap.put(Media.ISDEL, Constants.ZERO);
			mediaMap.put(Media.SRC, src);
			mediaMap.put(Media.CTIME, date);
			mediaMap.put(Media.MTYPE, mtype);
			mediaMap.put(Media.CUSERID, common.getUserId());
			mediaMap.put(Media.CUSENAME, common.getUserName());
			mediaMap.put(Media.CONSUMERID, common.getCompanyId());
			mediaMap.put(Media.CONSUMERNAME, common.getConsumerName());
			mediaMap.put(Media.UUSERID, common.getUserId());
			mediaMap.put(Media.UUSERNAME, common.getUserName());
			mediaMap.put(Media.UUTIME, date);
			mediaMap.put(Media.TASKID, taskid);
			mediaMap.put(Media.MD5, md5);
			if(null!=remark&&!"".equals(remark)){
				mediaMap.put(Media.DESCRIBE, remark);
			}
			
			mediaMap.put(Media.CTIME, DateUtil.getCurrentDateTime());
			mediaMap.put(Media.WANURL, String.valueOf(ret.get(Media.WANURL)));
			if("".equals(vslt)){
				mediaMap.put(Media.VSLT, String.valueOf(ret.get(Media.OSSPROXYURL)));
			}else{
				mediaMap.put(Media.VSLT, vslt);
			}
			mediaMap.put(Media.LENURL, String.valueOf(ret.get(Media.LENURL)));
			mediaMap.put(Media.OSSINTERNETURL, String.valueOf(ret.get(Media.OSSINTERNETURL)));
			mediaMap.put(Media.OSSLANURL, String.valueOf(ret.get(Media.OSSLANURL)));
			mediaMap.put(Media.OSSPROXYURL, String.valueOf(ret.get(Media.OSSPROXYURL)));
			mediaMap.put(Media.CDNURL, String.valueOf(ret.get(Media.CDNURL)));
			mediaMap.put(Media.FILEPATH, filePath);
			
			//创建媒体数据
			String mediaid = mediaService.inset(mediaMap);
			return !StringUtil.isEmpty(mediaid);
		}else{
			return false;
		}
	}
	
}
