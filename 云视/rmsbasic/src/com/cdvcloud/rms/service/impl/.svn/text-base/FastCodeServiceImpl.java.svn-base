package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.vo.FastcodeVo;
import com.cdvcloud.rms.dao.IFastCodeDao;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.ITaskDao;
import com.cdvcloud.rms.domain.Fastcode_Project_Subtitle;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.Task;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IFastCodeService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.SkipUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.upload.config.Configurations;
import com.cdvcloud.util.FastCodeUtil;
import com.cdvcloud.vo.MediaVo;
import com.cdvcloud.vo.MediasVo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
@Service
public class FastCodeServiceImpl implements IFastCodeService {
	private static final Logger logger = Logger.getLogger(FastCodeServiceImpl.class);
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private IMediaService mediaService;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IFastCodeDao fastCodeDao;
	@Autowired
	private IHistoricalTaskService historicalTaskService;
	@Override
	public String getXml(CommonParameters common,Map<String, Object> map) {
		logger.info("快编获取素材或项目、模板"+common+"|参数："+map);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//选择未删除的
		whereMap.put(Media.ISDEL, Constants.ZERO);
		whereMap.put(Media.STATUS, Constants.ZERO);
		whereMap.put(Media.CUSERID, common.getUserId());

		String  materialIds = String.valueOf(map.get(FastcodeVo.MATERIALIDS));
		if (null != materialIds && !"".equals(materialIds)) {
			String[] materialIdArray = materialIds.split(",");
			BasicDBList basicDBList = new BasicDBList();
			for (String materialId : materialIdArray) {
				basicDBList.add(new ObjectId(materialId));
			}			
			whereMap.put(Media.ID, new BasicDBObject("$in", basicDBList));
		}
		//关键字
		if(!StringUtil.isEmpty(map.get(FastcodeVo.KEYWORDS))){
			String regxValue = ".*" + String.valueOf(map.get(FastcodeVo.KEYWORDS)) + ".*";
			Map<String, Object> regxMap = new HashMap<String, Object>();
			regxMap.put("$regex", regxValue);
			regxMap.put("$options", "i");
			whereMap.put(Media.NAME, regxMap);
		}
		//素材类型 0视频，1音频 2图片
		String catalogType =String.valueOf(map.get(FastcodeVo.CATALOGTYPE));
		if(!StringUtil.isEmpty(catalogType)){
			if("3".equals(catalogType)){
				catalogType = "2";
			}
			whereMap.put(Media.MTYPE,catalogType);
		}
		String xml="";
		if(catalogType.split("_").length<=1){
			Long validTotal = mediaDao.count(whereMap);
			Integer pageNo = Integer.valueOf(String.valueOf(map.get(FastcodeVo.PAGENO)));
			Integer wherePageNo = 1;
			wherePageNo= pageNo+1;
			Map<String, Object> sortFilter = new HashMap<String, Object>();
			String strOrder = String.valueOf(map.get(FastcodeVo.ORDER));
			if ("0".equals(strOrder)) {
				// 降序排列
				sortFilter.put(Media.UUTIME, -1);
			}else{
				sortFilter.put(Media.UUTIME, 1);
			}
			
			List<Map<String, Object>> medias = mediaDao.query(sortFilter, whereMap, wherePageNo, 44);
		
			MediasVo dataList = medias(medias,catalogType,validTotal,String.valueOf(map.get(FastcodeVo.PAGENO)),44);
			xml = FastCodeUtil.getmediaXML(dataList);
		}else{
			whereMap.clear();
			whereMap.put(Fastcode_Project_Subtitle.ISDELETE, "0");
			whereMap.put(Fastcode_Project_Subtitle.TYPE,catalogType);
			Integer pageNo = Integer.valueOf(String.valueOf(map.get(FastcodeVo.PAGENO)));
			Integer wherePageNo = 1;
			wherePageNo= pageNo+1;
			Map<String, Object> sortFilter = new HashMap<String, Object>();
			sortFilter.put(Fastcode_Project_Subtitle.CTIME, -1);
			List<Map<String, Object>> validList=null;
			Long validTotal=0L;
			logger.info("判断查询哪个库：catalogType.split(_)[0].equals(-2)="+catalogType.split("_")[0].equals("-2"));
			if(catalogType.split("_")[0].equals("-2")){
				validList= fastCodeDao.querySubtitles(sortFilter, whereMap,wherePageNo, 44);
				validTotal = fastCodeDao.countSubtitles(whereMap);
			}else{
				whereMap.put(Media.CUSERID, common.getUserId());
				validList= fastCodeDao.queryProject(sortFilter, whereMap,wherePageNo, 44);
				validTotal = fastCodeDao.countProject(whereMap);
			}
			logger.info("validList="+validList);
			logger.info("validTotal="+validTotal);
			xml = assembleprojectorsubtitlexml(validTotal, validList, String.valueOf(pageNo), 44, catalogType);
		}
//		logger.info("快编需要的xml："+xml);
		return xml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MediasVo medias(List<Map<String, Object>> list,String catalogType,Long validTotal,String pageNo,Integer pageSize) {
		MediasVo mediasVo = new MediasVo();
		List<MediaVo> lists = new ArrayList<MediaVo>();
		for (Map<String, Object> map : list) {
			MediaVo media = new MediaVo();
			//从数据库取值
			String name = String.valueOf(map.get(Media.NAME));
			String ctime = String.valueOf(map.get(Media.CTIME));
			String duration = String.valueOf(map.get(Media.DURATION));
			String fmt = String.valueOf(map.get(Media.FMT));
			String frame = String.valueOf(map.get(Media.FRAME));
			String height = String.valueOf(map.get(Media.HEIGHT));
			String width = String.valueOf(map.get(Media.WIDTH));
			String id = String.valueOf(map.get(Media.ID));
			String rate = String.valueOf(map.get(Media.RATE));
			//源文件地址
			String srcFileUrl = "";
			//获取工具集地址
			String orsUrl =configurationService.getORSAPI();
			srcFileUrl =SkipUtil.getHttp(orsUrl,String.valueOf(map.get(Media.LENURL)));
			String thumbnailurl = "";
			thumbnailurl =SkipUtil.getHttp(orsUrl,String.valueOf(map.get(Media.VSLT)));
			String url = "";
			if("2".equals(catalogType)){
				url =SkipUtil.getHttp(orsUrl,String.valueOf(map.get(Media.WANURL)));
			}else{
				//预览地址
				Map<String, Object> prewar = (Map<String, Object>)map.get(Media.PREWAR);
				if(null!=prewar&&!"".equals(prewar)){
					url =SkipUtil.getHttp(orsUrl,String.valueOf(prewar.get(Media.TRANSCODE_ATTR_WANURL)));
				}
			}
			//赋值
			media.setName(name);
			media.setCtime(ctime);
			media.setDuration(duration);
			media.setFmt(fmt);
			media.setFrame(frame);
			media.setHeight(height);
			media.setId(id);
			media.setRate(rate);
			media.setSrcFileUrl(srcFileUrl);
			media.setThumbnailurl(thumbnailurl);
			media.setUrl(url);
			media.setWidth(width);
			lists.add(media);
		}
		if("2".equals(catalogType)){
			catalogType="3";
		}
		mediasVo.setCatalogType(catalogType);
		mediasVo.setMedias(lists);
		mediasVo.setValidTotal(validTotal);
		mediasVo.setPageNo(pageNo);
		mediasVo.setPageSize(pageSize);
		return mediasVo;
	}
	/**
	 * 
	 */
	@Override
	public String saveFastcode(CommonParameters common,String fastXml, String fileName, String formatSample, String fixedInfo, String from) {
		Map<String, Object> mediaMap = new HashMap<String, Object>();
		mediaMap.put(Media.NAME, fileName);
		//		mediaMap.put(Media.SIZE, Double.valueOf(String.valueOf(analysis.get(Media.SIZE))));
		//		mediaMap.put(Media.DURATION, Double.valueOf(String.valueOf(analysis.get(Media.DURATION))));
		//		mediaMap.put(Media.WIDTH, analysis.get(Media.WIDTH));
		//		mediaMap.put(Media.FMT, analysis.get(Media.FMT));
		//		mediaMap.put(Media.HEIGHT, analysis.get(Media.HEIGHT));
		//		mediaMap.put(Media.FRAME, analysis.get(Media.FRAME));
		//		mediaMap.put(Media.RATE, analysis.get(Media.RATE));
		mediaMap.put(Media.SRC, "快编");
		mediaMap.put(Media.STATUS, GeneralStatus.processing.status);
		mediaMap.put(Media.ISDEL, Constants.ZERO);
		mediaMap.put(Media.CTIME, DateUtil.getCurrentDateTime());
		mediaMap.put(Media.MTYPE, "0");
		mediaMap.put(Media.CUSERID, common.getUserId());
		mediaMap.put(Media.CUSENAME, common.getUserName());
		mediaMap.put(Media.CONSUMERID, common.getCompanyId());
		mediaMap.put(Media.CONSUMERNAME, common.getConsumerName());
		String date = DateUtil.getCurrentDateTime();
		mediaMap.put(Media.CTIME, date);
		mediaMap.put(Media.UUSERID, common.getUserId());
		mediaMap.put(Media.UUSERNAME, common.getUserName());
		mediaMap.put(Media.UUTIME, date);

		//创建媒体数据
		String mediaid = mediaService.inset(mediaMap);
		if(StringUtil.isEmpty(mediaid)){
			return "";
		}
		logger.info("创建媒体数据的id="+mediaid);
		//创建任务
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put(Task.MID, mediaid);
		taskMap.put(Task.ISTRANSCODE, "1");
		taskMap.put(Task.APPCODE,common.getAppCode());
		taskMap.put(Task.COMPANYID,common.getCompanyId());
		taskMap.put(Task.USERID,common.getUserId());
		taskMap.put(Task.VERSIONID,common.getVersionId());
		//-----------------------
		Map<String,Object> valueMap = new HashMap<String,Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		//content.put("format_sample", formatSample);//不传模板视频，快编使用默认模板配置
		content.put("tracks", JsonUtil.readJSON2MapList(fastXml));

		valueMap.put("content", content);

		String paramJson = JsonUtil.writeMap2JSON(content);

		Map<String, Object> result=this.buid(common,paramJson,mediaid );
		String ret = "";
		if(null!=result&&result.size()>0){
			ret = FastCodeUtil.generateResponseXML(true);
		}else{
			ret = FastCodeUtil.generateResponseXML(false);
		}
		//保存转码返回路径
		taskMap.put(Task.TRANSCODEURL, result.get("https"));
		taskMap.put(Task.TASKID, result.get("taskId"));
		taskMap.put(Task.HISTORICALTASKID, result.get(Task.HISTORICALTASKID));
		//更新服务任务id  
		taskDao.insertTask(taskMap);

		return ret;
	}
	@SuppressWarnings("unchecked")
	private Map<String, Object> buid(CommonParameters common,String fastJson,String fixedInfo){
		//获取工具集地址
		String url =configurationService.getDigitalFactoryAPI();
		//拼装快编url地址
		url+=common.getOldPublicAddress("KUAIB")+"fastedit/task/create/";
		//回调地址
		String callbackUrl="";
		if(Configurations.isOpenCollback()){
			callbackUrl=Configurations.getCollback()+"/collback/fastedit/";
		}else{
			callbackUrl=configurationService.getCallback()+"/collback/fastedit/";

		}
		String priority="5";
		//拼装快编参数
		//		//做编码
		//		try {
		//			fastJson=URLEncoder.encode(fastJson,"utf-8");
		//		} catch (UnsupportedEncodingException e) {
		//			e.printStackTrace();
		//		}
		String param="accessToken=123&timeStamp="+System.currentTimeMillis()+"&content="+fastJson+"&callbackUrl="+callbackUrl+"&priority="+priority+
				"&fixedInfo="+fixedInfo;
		//-------------记录历史任务--------------
		Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
		historicalTaskMap.put(HistoricalTask.APPCODE, common.getAppCode());
		historicalTaskMap.put(HistoricalTask.COMPANYID, common.getCompanyId());
		historicalTaskMap.put(HistoricalTask.VERSIONID, common.getVersionId());
		historicalTaskMap.put(HistoricalTask.USERID, common.getUserId());
		historicalTaskMap.put(HistoricalTask.SERVICECODE, common.getServiceCode());
		//				historicalTaskMap.put(HistoricalTask.CONSUMERID, productCode);
		//				historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
		//				historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
		//				historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
		historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
		historicalTaskMap.put(HistoricalTask.STATUS, "2");
		//				historicalTaskMap.put(HistoricalTask.TYPE, type);
		//				historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
		historicalTaskMap.put(HistoricalTask.FASTCODEURL, url);
		historicalTaskMap.put(HistoricalTask.FASTCODEPARAM, param);
		historicalTaskMap.put(HistoricalTask.SRC, "快编任务创建：buid");
		String taskid = historicalTaskService.inset(historicalTaskMap);
		//-------------记录历史任务--------------
		logger.info("发送快编任务url="+url+";内容="+param);
		String ret = HttpUtil.sendPost(url, param);
		logger.info("发送快编任务返回="+ret);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Task.HISTORICALTASKID, taskid);
		Map<String, Object> map = JsonUtil.readJSON2Map(ret);
		if(!StringUtil.isEmpty(map)&&"0".equals(String.valueOf(map.get("code")))){
			Map<String, Object> data = (Map<String, Object>)map.get("data");
//			List<Map<String, Object>> lists = (List<Map<String, Object>>)data.get("destUrl");
			List<String> lists = (List<String>)data.get("destUrl");
			result.put("taskId", String.valueOf(data.get("taskId")));
			Map<String, Object> https = new HashMap<String, Object>();
			https.put("wanDestUrl", String.valueOf(lists.get(0)));
			https.put("lenDestUrl", String.valueOf(lists.get(0)));
			result.put("https", https);
		}
		return result;
	}

	@Override
	public String addFastcode(CommonParameters common, String catalogType, String data, String name, String from) {
		Document resultDoc = DocumentHelper.createDocument();
		Element resultElement = resultDoc.addElement("result");
		try {
			//切分用于判断是字幕还是项目
			String[] type = catalogType.split("_");
			Map<String, Object> setMap = new HashMap<String, Object>();
			setMap.put(Fastcode_Project_Subtitle.TYPE, catalogType);
			setMap.put(Fastcode_Project_Subtitle.DATA, data);
			setMap.put(Fastcode_Project_Subtitle.ISDELETE, "0");//是否删除（1：是，0：否）
			setMap.put(Fastcode_Project_Subtitle.CTIME, DateUtil.getCurrentDateTime());
			setMap.put(Fastcode_Project_Subtitle.TITLE, name);
			setMap.put(Fastcode_Project_Subtitle.CUSERID, common.getUserId());
			setMap.put(Fastcode_Project_Subtitle.CUSENAME, common.getUserName());
			setMap.put(Fastcode_Project_Subtitle.CONSUMERID, common.getCompanyId());
			setMap.put(Fastcode_Project_Subtitle.CONSUMERNAME, common.getConsumerName());
			
			String id = "";//保存保存后的id
			if(type[0].equals(Fastcode_Project_Subtitle.negative_two)){//字幕保存
				id = fastCodeDao.insertSubtitles(setMap);
			}else if(type[0].equals(Fastcode_Project_Subtitle.negative_one)){//项目保存
				id = fastCodeDao.insertProject(setMap);
			}
			if (null!=id&&!"".equals(id)) {
				resultElement.addText(id);
			} else {
				resultElement.addText("false");
			}
			return resultDoc.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			resultElement.addText("false");
			return resultDoc.asXML();
		}
	}

	@Override
	public String updateFastcode(CommonParameters common, String catalogType, String data, String fileKEY, String from) {
		Document resultDoc = DocumentHelper.createDocument();
		Element resultElement = resultDoc.addElement("result");
		//切分用于判断是字幕还是项目
		try {
			String[] type = catalogType.split("_");
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put(Fastcode_Project_Subtitle.ID, new ObjectId(fileKEY));
			Map<String, Object> setMap = new HashMap<String, Object>();
			setMap.put(Fastcode_Project_Subtitle.DATA, data);
			setMap.put(Fastcode_Project_Subtitle.CTIME, DateUtil.getCurrentDateTime());
			long id = 0;//保存保存后的id
			if(type[0].equals(Fastcode_Project_Subtitle.negative_two)){//字幕保存
				id = fastCodeDao.updateSubtitles(whereMap,setMap);
			}else if(type[0].equals(Fastcode_Project_Subtitle.negative_one)){//项目保存
				id = fastCodeDao.updateProject(whereMap, setMap);
			}
			if (0!=id) {
				resultElement.addText("true");
			} else {
				resultElement.addText("false");
			}
			return resultDoc.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			resultElement.addText("false");
			return resultDoc.asXML();
		}
	}

	@Override
	public String deleteFastcode(CommonParameters common, String catalogType, String fileKEY, String from) {
		Document resultDoc = DocumentHelper.createDocument();
		Element resultElement = resultDoc.addElement("result");
		//切分用于判断是字幕还是项目
		try {
			String[] type = catalogType.split("_");
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put(Fastcode_Project_Subtitle.ID, new ObjectId(fileKEY));
			Map<String, Object> setMap = new HashMap<String, Object>();
			setMap.put(Fastcode_Project_Subtitle.ISDELETE, "1");
			long id = 0;//保存保存后的id
			if(type[0].equals(Fastcode_Project_Subtitle.negative_two)){//字幕保存
				id = fastCodeDao.updateSubtitles(whereMap,setMap);
			}else if(type[0].equals(Fastcode_Project_Subtitle.negative_one)){//项目保存
				id = fastCodeDao.updateProject(whereMap, setMap);
			}
			if (0!=id) {
				resultElement.addText("true");
			} else {
				resultElement.addText("false");
			}
			return resultDoc.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			resultElement.addText("false");
			return resultDoc.asXML();
		}
	}
	/**
	 * 拼装快编字幕、项目查询结果
	 * @param validTotal
	 * @param validList
	 * @param param
	 * @return
	 */
	private static String assembleprojectorsubtitlexml(Long validTotal, List<Map<String, Object>> validList, String pageNo,Integer pageSize,String catalogType) {
		try {
			Long totalPage = validTotal / pageSize;
			if (validTotal % pageSize > 0) {
				totalPage += 1;
			}
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			if(null!=validList&&validList.size()>0){
				xml.append("<data CatalogType=\""+catalogType+"\" Page=\""+pageNo+"\" TotalPage=\""+totalPage+"\">");
				for(int i=0;i<validList.size();i++){
					Document document = DocumentHelper.parseText(String.valueOf(validList.get(i).get(Fastcode_Project_Subtitle.DATA)));
					Element root = document.getRootElement();
					root.addAttribute("FileKEY", String.valueOf(validList.get(i).get(Fastcode_Project_Subtitle.ID)));
					xml.append(document.asXML());
				}
				xml.append("</data>");
				
			}
			//xml.append("</xml>");
			return xml.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		}
	}
	
	private static class MapComparator implements Comparator<Map<String, Object>> {
		@Override
		public int compare(Map<String, Object> map1, Map<String, Object> map2) {
			String strTime1 = String.valueOf(map1.get("ctime"));
			String strTime2 = String.valueOf(map2.get("ctime"));
			if (null != strTime2) {
				return strTime2.compareTo(strTime1);
			}
			return 0;
		}
	}
}
