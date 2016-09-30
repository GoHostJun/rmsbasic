package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.domain.PushSet;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.DownloadService;
import com.cdvcloud.rms.service.IPushService;
import com.cdvcloud.rms.service.IPushSetService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class PushServiceImpl implements IPushService {
	private static final Logger logger = Logger.getLogger(PushServiceImpl.class);

	@Autowired
	private IPushSetService pushSetService;
	@Autowired
	private IWeChatService weChatService;
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private ConfigurationService configurationService;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean sendToNewsphere(Map<String, Object> newsMap, CommonParameters commonParameters) {
		if (null == newsMap || newsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", "news");
		paramMap.put("subscriptionType", "云通联");
		paramMap.put("source", "云通联");
		paramMap.put("userId", commonParameters.getUserId());
		paramMap.put("userName", commonParameters.getUserName());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
		// 添加视频
		List<Map<String, Object>> videos = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.VIDEOS) && null != pushMap.get(News.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(News.VIDEOS);
			Map<String, Object> videosMap = null;
			for (Map<String, Object> mapV : videosFlag) {
				videosMap = new HashMap<String, Object>();
				String name = mapV.get("name") + "." + mapV.get("fmt");
				videosMap.put("name", name);
				videosMap.put("url", mapV.get("wanurl"));
				videos.add(videosMap);
			}
		}
		// 添加音频
		if (pushMap.containsKey(News.AUDIOS) && null != pushMap.get(News.AUDIOS)) {
			List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) pushMap.get(News.AUDIOS);
			Map<String, Object> audiosMap = null;
			for (Map<String, Object> mapA : audiosFlag) {
				audiosMap = new HashMap<String, Object>();
				String name = mapA.get("name") + "." + mapA.get("fmt");
				audiosMap.put("name", name);
				audiosMap.put("url", mapA.get("wanurl"));
				videos.add(audiosMap);
			}
		}
		dataMap.put("videos", videos);
		// 添加图片
		List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.PICS) && null != pushMap.get(News.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(News.PICS);
			Map<String, Object> imagesMap = null;
			for (Map<String, Object> mapP : imagesFlag) {
				imagesMap = new HashMap<String, Object>();
				String name = mapP.get("name") + "." + mapP.get("fmt");
				imagesMap.put("name", name);
				imagesMap.put("url", mapP.get("wanurl"));
				images.add(imagesMap);
			}
		}
		// 获取并赋值文稿里的字段
		Map<String, Object> mapTemplate = null;
		if (newsMap.containsKey(News.TEMPLATE) && null != newsMap.get(News.TEMPLATE)) {
			mapTemplate = (Map<String, Object>) newsMap.get(News.TEMPLATE);
			if (null != mapTemplate.get("reporter")) {
				dataMap.put("author", mapTemplate.get("reporter"));// 记者
			}
			String source_site = "云通联汇聚_";
			if (null != mapTemplate.get("source")) {
				source_site = source_site + mapTemplate.get("source");
			}
			dataMap.put("source_site", source_site);// 分类_来源(分类和来源两个字段以下划线作为连接符拼凑一起赋给source_site字段即可)
			if (null != mapTemplate.get("program")) {
				dataMap.put("columnName", mapTemplate.get("program"));// 栏目
			}
			if (null != mapTemplate.get("cameraMan")) {
				dataMap.put("cameramen", mapTemplate.get("cameraMan"));// 摄像
			}
			if (null != mapTemplate.get("docsContentHTML")) {
				dataMap.put("content", mapTemplate.get("docsContentHTML"));// 正文
			}
			if (null != mapTemplate.get("repProviders")) {
				dataMap.put("rep_providers", mapTemplate.get("repProviders"));// 通讯员
			}
			if (null != mapTemplate.get("subtitleWords")) {
				dataMap.put("subtitlewords", mapTemplate.get("subtitleWords"));// 字幕
			}
			if (null != mapTemplate.get("tvStationName")) {
				dataMap.put("prostation", mapTemplate.get("tvStationName"));// 供片台
			}
			if (null != mapTemplate.get("keyWords")) {
				dataMap.put("keywords", mapTemplate.get("keyWords"));// 关键词
			}
			if (null != mapTemplate.get("presenter")) {
				dataMap.put("hoster", mapTemplate.get("presenter"));// 主持人
			}
			if (null != mapTemplate.get("dubbingMan")) {
				dataMap.put("dubbing", mapTemplate.get("dubbingMan"));// 配音
			}
			if (null != mapTemplate.get("editor")) {
				dataMap.put("filmcutters", mapTemplate.get("editor"));// 编辑
			}
		}
		dataMap.put("images", images);
		dataMap.put("type", "news");
		dataMap.put("source_id", newsMap.get(News.ID));
		dataMap.put("title", newsMap.get(News.TITLE));

		paramMap.put("data", dataMap);
		String paramJson = JsonUtil.writeMap2JSON(paramMap);
		String paramUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
		logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
		String result = HttpUtil.doPost(paramUrl, paramJson);
		if (null != result) {
			Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
			String status = String.valueOf(mapRes.get("status"));
			String message = String.valueOf(mapRes.get("message"));
			// 调用微信

			if ("200".equals(status) && "ok".equalsIgnoreCase(message)) {
				weChatService.pushWx(newsMap, "成功", "push");
				return true;
			} else {
				weChatService.pushWx(newsMap, "失败", "push");
				logger.error("推送失败：status=" + status + ",message=" + message);
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean sendToJSNewsphere(Map<String, Object> newsMap, CommonParameters commonParameters) {
		if (null == newsMap || newsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", "news");
		paramMap.put("subscriptionType", "云报道");
		paramMap.put("source", "云报道");
		paramMap.put("userId", commonParameters.getUserId());
		paramMap.put("userName", commonParameters.getLoginName());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
		// 添加视频
		List<Map<String, Object>> videos = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.VIDEOS) && null != pushMap.get(News.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(News.VIDEOS);
			Map<String, Object> videosMap = null;
			for (Map<String, Object> mapV : videosFlag) {
				videosMap = new HashMap<String, Object>();
				String name = mapV.get("name") + "." + mapV.get("fmt");
				videosMap.put("name", name);
				videosMap.put("url", mapV.get("wanurl"));
				videos.add(videosMap);
			}
		}
		// 添加音频
		if (pushMap.containsKey(News.AUDIOS) && null != pushMap.get(News.AUDIOS)) {
			List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) pushMap.get(News.AUDIOS);
			Map<String, Object> audiosMap = null;
			for (Map<String, Object> mapA : audiosFlag) {
				audiosMap = new HashMap<String, Object>();
				String name = mapA.get("name") + "." + mapA.get("fmt");
				audiosMap.put("name", name);
				audiosMap.put("url", mapA.get("wanurl"));
				videos.add(audiosMap);
			}
		}
		dataMap.put("videos", videos);
		// 添加图片
		List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
		if (pushMap.containsKey(News.PICS) && null != pushMap.get(News.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(News.PICS);
			Map<String, Object> imagesMap = null;
			for (Map<String, Object> mapP : imagesFlag) {
				imagesMap = new HashMap<String, Object>();
				String name = mapP.get("name") + "." + mapP.get("fmt");
				imagesMap.put("name", name);
				imagesMap.put("url", mapP.get("wanurl"));
				images.add(imagesMap);
			}
		}
		// 获取并赋值文稿里的字段
		Map<String, Object> mapTemplate = null;
		if (newsMap.containsKey(News.TEMPLATE) && null != newsMap.get(News.TEMPLATE)) {
			mapTemplate = (Map<String, Object>) newsMap.get(News.TEMPLATE);
			if (null != mapTemplate.get("reporter")) {
				dataMap.put("author", mapTemplate.get("reporter"));// 记者
			}
			String source_site = "文稿_";
			if (newsMap.containsKey(PushSet.QUERYURL) && !StringUtil.isEmpty(newsMap.get(PushSet.QUERYURL))) {
				source_site = String.valueOf(newsMap.get(PushSet.QUERYURL));
			}
			if (null != mapTemplate.get("source")) {
				source_site = source_site + mapTemplate.get("source");
			}
			dataMap.put("source_site", source_site);// 分类_来源(分类和来源两个字段以下划线作为连接符拼凑一起赋给source_site字段即可)
			if (null != mapTemplate.get("program")) {
				dataMap.put("columnName", "");// 栏目
			}
			if (null != mapTemplate.get("cameraMan")) {
				dataMap.put("cameramen", mapTemplate.get("cameraMan"));// 摄像
			}
			if (null != mapTemplate.get("docsContentHTML")) {
				dataMap.put("content", mapTemplate.get("docsContentHTML"));// 正文
			}
			if (null != mapTemplate.get("repProviders")) {
				dataMap.put("rep_providers", mapTemplate.get("repProviders"));// 通讯员
			}
			if (null != mapTemplate.get("subtitleWords")) {
				dataMap.put("subtitlewords", mapTemplate.get("subtitleWords"));// 字幕
			}
			if (null != mapTemplate.get("tvStationName")) {
				dataMap.put("prostation", mapTemplate.get("tvStationName"));// 供片台
			}
			if (null != mapTemplate.get("keyWords")) {
				dataMap.put("keywords", mapTemplate.get("keyWords"));// 关键词
			}
			if (null != mapTemplate.get("presenter")) {
				dataMap.put("hoster", mapTemplate.get("presenter"));// 主持人
			}
			if (null != mapTemplate.get("dubbingMan")) {
				dataMap.put("dubbing", mapTemplate.get("dubbingMan"));// 配音
			}
			if (null != mapTemplate.get("editor")) {
				dataMap.put("filmcutters", mapTemplate.get("editor"));// 编辑
			}
		}
		dataMap.put("images", images);
		dataMap.put("type", "news");
		dataMap.put("source_id", newsMap.get(News.ID));
		dataMap.put("title", newsMap.get(News.TITLE));

		paramMap.put("data", dataMap);
		String paramJson = JsonUtil.writeMap2JSON(paramMap);
		String paramUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
		logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
		String result = HttpUtil.doPost(paramUrl, paramJson);
		if (null != result) {
			Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
			String status = String.valueOf(mapRes.get("status"));
			String message = String.valueOf(mapRes.get("message"));
			// 调用微信

			if ("200".equals(status) && "ok".equalsIgnoreCase(message)) {
				weChatService.pushWx(newsMap, "成功", "push");
				return true;
			} else {
				weChatService.pushWx(newsMap, "失败", "push");
				logger.error("推送失败：status=" + status + ",message=" + message);
			}
		}
		return false;
	}

	@Override
	public boolean sendToJSDirectNewsphere(String mediaid, CommonParameters commonParameters) {
		try {
			// 查询媒体
			Map<String, Object> newsMap = mediaDao.queryOne(mediaid);
			if (null == newsMap || newsMap.isEmpty()) {
				return false;
			}
			Map<String, Object> uMap= userService.getUserInforById(commonParameters.getUserId());
			String type = String.valueOf(newsMap.get(Media.MTYPE));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("type", "news");
			paramMap.put("subscriptionType", "云报道");
			paramMap.put("source", "云报道");
			paramMap.put("userId", "");
			paramMap.put("userName", uMap.get(User.EMAIL));
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, Object> pushMap =  pushSetService.findOne("jsnewsphere");
			logger.info("查询推送表信息："+pushMap);
			// 添加视频源文件
			List<Map<String, Object>> videos = new ArrayList<Map<String, Object>>();
			if ("0".equals(type)) {
				Map<String, Object> videosMap = new HashMap<String, Object>();
				videosMap.put("name", String.valueOf(newsMap.get(Media.NAME)) + "." + newsMap.get(Media.FMT));
				videosMap.put("url", newsMap.get(Media.TRANSCODE_ATTR_WANURL));
				videos.add(videosMap);
			}
			dataMap.put("videos", videos);
			// 添加图片
			List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
			if("2".equals(type)){
				Map<String, Object> imagesMap = new HashMap<String, Object>();
				String name = newsMap.get("name") + "." + newsMap.get("fmt");
				imagesMap.put("name", name);
				imagesMap.put("url", newsMap.get("wanurl"));
				images.add(imagesMap);
			}
			// 获取并赋值文稿里的字段
			String source_site = "荔枝直传_";
			dataMap.put("author", uMap.get(User.NAME));// 记者
			dataMap.put("source_site", source_site);// 分类_来源(分类和来源两个字段以下划线作为连接符拼凑一起赋给source_site字段即可)
			dataMap.put("images", images);
			dataMap.put("type", "news");
			dataMap.put("source_id", newsMap.get(News.ID));
			dataMap.put("title", newsMap.get("name"));
			paramMap.put("data", dataMap);
			String paramJson = JsonUtil.writeMap2JSON(paramMap);
			String paramUrl = String.valueOf(pushMap.get(PushSet.PUSHURL));
			logger.info("发送地址：" + paramUrl + ",发送参数：" + paramJson);
			String result = HttpUtil.doPost(paramUrl, paramJson);
			if (null != result) {
				Map<String, Object> mapRes = JsonUtil.readJSON2Map(result);
				String status = String.valueOf(mapRes.get("status"));
				String message = String.valueOf(mapRes.get("message"));
				newsMap.put(News.TITLE, String.valueOf(newsMap.get("name")));
				// 调用微信
				if ("200".equals(status) && "ok".equalsIgnoreCase(message)) {
					weChatService.pushWx(newsMap, "成功", "push");
					return true;
				} else {
					weChatService.pushWx(newsMap, "失败", "push");
					logger.error("推送失败：status=" + status + ",message=" + message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean sendToJSCityChannel(Map<String, Object> newsMap, CommonParameters commonParameters) {
		if (null == newsMap || newsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> mapXml = downloadService.uploadXMLContent(newsMap, commonParameters);
		Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
		if (null == pushMap && null == mapXml) {
			logger.warn("推送文件信息不合格！pushMap= " + pushMap + " mapXml= " + mapXml);
			return false;
		}
		String fileName = String.valueOf(newsMap.get(News.TITLE));
		String tarpathid = String.valueOf(newsMap.get(PushSet.QUERYURL));// 目标路径ID
		String destFile = "\\input\\" + commonParameters.getLoginName() + "\\" + DateUtil.toISODateString(new Date()) + "\\";
		String xmlUrl = null;
		String xmlLocalUrl = null;
		if (!StringUtil.isEmpty(mapXml) && mapXml.containsKey(Media.WANURL)) {
			xmlUrl = String.valueOf(mapXml.get(Media.WANURL));
			xmlLocalUrl = String.valueOf(mapXml.get("xmlLocalUrl"));
		}
		String backPara = getMoveData(xmlUrl, pushMap, fileName, tarpathid, destFile);
		StringBuffer params = new StringBuffer();
		params.append("accessKey=" + configurationService.getDirectPassing_accessKey());
		params.append("&timeStamp=" + String.valueOf(System.currentTimeMillis()));
		String tokenUrl = configurationService.getDirectPassing_getaccesstoken();
		String accessResultJson = HttpUtil.sendPost(tokenUrl, params.toString());
		Map<String, Object> accessResultMap = JsonUtil.readJSON2Map(accessResultJson);
		Map<String, String> accessdata = (HashMap<String, String>) accessResultMap.get("data");
		String token = String.valueOf(accessdata.get("accessToken"));
		StringBuffer moveparams = new StringBuffer();
		moveparams.append("accessToken=" + token);
		moveparams.append("&timeStamp=" + System.currentTimeMillis());
		moveparams.append("&data=" + backPara);
		String moveUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
		logger.info("下发任务url=" + moveUrl + ";参数：" + moveparams.toString());
		String moveResultJson = HttpUtil.sendPost(moveUrl, moveparams.toString());
		logger.info("下发任务返回=" + moveResultJson);
		Map<String, Object> result = JsonUtil.readJSON2Map(moveResultJson);
		if (Integer.valueOf(String.valueOf(result.get("code"))) == 0) {
			if (null != xmlLocalUrl) {
				if (!FileUtil.delFile(xmlLocalUrl)) {
					logger.warn("删除临时文件xml失败：" + xmlLocalUrl);
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private String getMoveData(String xmlUrl,Map<String, Object> pushMap,String name,String tarpathid,String destFile){
		Map<String, Object> params = new HashMap<String, Object>();
		String moveTaskPriority = String.valueOf(5); // 任务优先级，值越大优先级越高。取值范围0-10。建议5
		params.put("name", name);
		params.put("priority", moveTaskPriority);
		params.put("to_url", "");// 文件从外网迁移到内网以后，通知目标系统进行对应操作处理。（目前没有业务需要，暂时不填）
		params.put("notification_request","");// 文件从外网迁移到内网以后，通知目标系统进行对应操作处理时，携带的请求参数。（目前没有业务需要，暂时不填）
		String moveTaskCallBackUrl = configurationService.getCallback() + "/api/moveCityChannelBack/"; // TODO:任务回调url
		params.put("callback_url", moveTaskCallBackUrl); // (-----------------重要---------------)处理完成后，调用的回调地址。（暂时不填）
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("dest_section_id", tarpathid); // (-----------------重要---------------)迁移任务的目标版块ID，由公有云迁移到私有云，为固定值。如：
		inputMap.put("continue_on_error", false); // 部分文件失败是否继续任务，否
		inputMap.put("action_if_exists", 1); // 目标文件已存在应该采取的操作,1:覆盖
		List<Map<String, Object>> transferList = new ArrayList<Map<String, Object>>();
		Map<String, Object> transfer = null;
		if (!StringUtil.isEmpty(xmlUrl)) {
			transfer = new HashMap<String, Object>();
			transfer.put("src_file", xmlUrl); // (-----------------重要---------------)源文件HTTP、共享或FTP全路径地址
			String destFileUrl = destFile + name + ".xml";
			transfer.put("dest_file", destFileUrl); // 目标文件路径，dest_section_id指向了根目录，dest_file定义了存储目录结构
			transferList.add(transfer);
		}
		if (!StringUtil.isEmpty(pushMap)) {
			if (pushMap.containsKey(News.VIDEOS)) {
				List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(News.VIDEOS);
				for (Map<String, Object> mapV : videosFlag) {
					transfer = new HashMap<String, Object>();
					transfer.put("src_file", mapV.get(Media.WANURL)); // (-----------------重要---------------)源文件HTTP、共享或FTP全路径地址
					String destVideoFileUrl = destFile + mapV.get(Media.NAME) + "." + mapV.get(Media.FMT);
					transfer.put("dest_file", destVideoFileUrl); // 目标文件路径，dest_section_id指向了根目录，dest_file定义了存储目录结构
					transferList.add(transfer);
				}
			}
			if (pushMap.containsKey(News.AUDIOS)) {
				List<Map<String, Object>> audiosFlag = (List<Map<String, Object>>) pushMap.get(News.AUDIOS);
				for (Map<String, Object> mapA : audiosFlag) {
					transfer = new HashMap<String, Object>();
					transfer.put("src_file", mapA.get(Media.WANURL)); // (-----------------重要---------------)源文件HTTP、共享或FTP全路径地址
					String destAudioFileUrl = destFile + mapA.get(Media.NAME) + "." + mapA.get(Media.FMT);
					transfer.put("dest_file", destAudioFileUrl); // 目标文件路径，dest_section_id指向了根目录，dest_file定义了存储目录结构
					transferList.add(transfer);
				}
			}
			if (pushMap.containsKey(News.PICS)) {
				List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(News.PICS);
				for (Map<String, Object> mapP : imagesFlag) {
					transfer = new HashMap<String, Object>();
					transfer.put("src_file", mapP.get(Media.WANURL)); // (-----------------重要---------------)源文件HTTP、共享或FTP全路径地址
					String destPFileUrl = destFile + mapP.get(Media.NAME) + "." + mapP.get(Media.FMT);
					transfer.put("dest_file", destPFileUrl); // 目标文件路径，dest_section_id指向了根目录，dest_file定义了存储目录结构
					transferList.add(transfer);
				}
			}
		}
		inputMap.put("transfers", transferList);
		params.put("input", inputMap);
		String resultData = JsonUtil.map2Json(params);
		return resultData;
	}

	@Override
	public boolean sendToJSSBContent(Map<String, Object> newsMap, CommonParameters commonParameters) {
		if (null == newsMap || newsMap.isEmpty()) {
			return false;
		}
		Map<String, Object> pushMap = pushSetService.getPushAddress(newsMap);
		if (null == newsMap ||newsMap.isEmpty() || null == pushMap) {
			logger.warn("推送文件信息不合格！pushMap= " + pushMap);
			return false;
		}
		String strPic = getPicMapStr(newsMap, pushMap, commonParameters);
		List<String> listVA = getVideoAudioMapStr(newsMap, pushMap, commonParameters);
		String moveUrl = String.valueOf(newsMap.get(PushSet.PUSHURL));
		if (!StringUtil.isEmpty(strPic)) {
			boolean res = sendFormPost(moveUrl, strPic);
			if (!res) {
				return res;
			}
		}
		if (null != listVA && !listVA.isEmpty()) {
			for (String strva : listVA) {
				boolean res = sendFormPost(moveUrl, strva);
				if (!res) {
					return res;
				}
			}
		}
		return true;
	}
	
	/**
	 * 获取推送内容、图片字符串
	 * @param newsMap
	 * @param commonParameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getPicMapStr(Map<String, Object> newsMap, Map<String, Object> pushMap, CommonParameters commonParameters){
		StringBuffer strPic = new StringBuffer();
		Map<String, Object> template = (Map<String, Object>) newsMap.get(News.TEMPLATE);
		strPic.append("title=")
		.append(StringUtil.encode(String.valueOf(newsMap.get(News.TITLE))))//标题
		.append("&id=")
		.append(newsMap.get(News.ID)+"00000000")
		.append("&link=")
		.append("")
		.append("&context=")
		.append(StringUtil.encode(String.valueOf(getContentValue(template.get("content"))))) //内容
		.append("&pubdate=")
		.append(newsMap.get(News.CTIME))
		.append("&from=")
		.append(StringUtil.encode(String.valueOf(getContentValue(template.get("source")))))//来源
		.append("&newsExtendDatas[0].name=businesstype")
		.append("&newsExtendDatas[0].value=4")
		.append("&newsExtendDatas[1].name=author")
		.append("&newsExtendDatas[1].value=")
		.append(StringUtil.encode(commonParameters.getUserName()))//用户名称
		.append("&newsExtendDatas[2].name=sourcesystem")
		.append("&newsExtendDatas[2].value=")
		.append(Constants.APPCODEVALUE)//appcode值
		.append("&newsExtendDatas[3].name=usercode")
		.append("&newsExtendDatas[3].value=")
		.append(commonParameters.getCasUserId())
		.append("&newsExtendDatas[4].name=username")
		.append("&newsExtendDatas[4].value=")
		.append(StringUtil.encode(commonParameters.getUserName()));
		if (null != pushMap && pushMap.containsKey(News.PICS) && null != pushMap.get(News.PICS)) {
			List<Map<String, Object>> imagesFlag = (List<Map<String, Object>>) pushMap.get(News.PICS);
			int ipic = 0;
			for (Map<String, Object> mapP : imagesFlag) {
				strPic.append("&material.materialFiles["+ipic+"].filetype=1")
				      .append("&material.materialFiles["+ipic+"].filename=")
				      .append(mapP.get("wanurl"));
				ipic++;
			}
		}
		return strPic.toString();
	}
	
	/**
	 * 获取推送视音频推送字符串
	 * @param newsMap
	 * @param commonParameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> getVideoAudioMapStr(Map<String, Object> newsMap, Map<String, Object> pushMap, CommonParameters commonParameters){
		List<String> listVA = new ArrayList<String>();
		if (pushMap.containsKey(News.VIDEOS) && null != pushMap.get(News.VIDEOS)) {
			List<Map<String, Object>> videosFlag = (List<Map<String, Object>>) pushMap.get(News.VIDEOS);
			for (Map<String, Object> mapV : videosFlag) {
				StringBuffer strPic = new StringBuffer();
				Map<String, Object> template = (Map<String, Object>) newsMap.get(News.TEMPLATE);
				strPic.append("title=")
				.append(StringUtil.encode(String.valueOf(newsMap.get(News.TITLE))))//标题
				.append("&id=")
				.append(mapV.get(News.ID)+"00000000")
				.append("&link=")
				.append("")
				.append("&context=")
				.append(StringUtil.encode(String.valueOf(getContentValue(template.get("content")))))//内容
				.append("&pubdate=")
				.append(mapV.get("ctime"))
				.append("&from=")
				.append(StringUtil.encode(String.valueOf(getContentValue(template.get("source")))))//来源
				.append("&material.title=")
				.append(StringUtil.encode(String.valueOf(mapV.get("name"))))//素材标题
				.append("&newsExtendDatas[0].name=businesstype")
				.append("&newsExtendDatas[0].value=3")
				.append("&newsExtendDatas[1].name=usercode")
				.append("&newsExtendDatas[1].value=")
				.append(commonParameters.getCasUserId())//用户用户的用户id
				.append("&newsExtendDatas[2].name=username")
				.append("&newsExtendDatas[2].value=")
				.append(StringUtil.encode(commonParameters.getUserName()))//用户名称
				.append("&newsExtendDatas[3].name=sourcesystem")
				.append("&newsExtendDatas[3].value=")
				.append(Constants.APPCODEVALUE)//appcode值
				.append("&material.materialFiles[0].filetype=2")
				.append("&material.materialFiles[0].filename=")
				.append(mapV.get("wanurl"));
				listVA.add(strPic.toString());
			}
		}
		return listVA;
	}
	
	private Object getContentValue(Object o){
		if (StringUtil.isEmpty(o)) {
			return "";
		}else{
			return o;
		}
	}
	
	private boolean sendFormPost(String moveUrl, String strParam){
		String moveResultJson = "{\"newsStatuData\":{\"success\":false}}";
		try {
			logger.info("下发任务url=" + moveUrl + ";参数：" + strParam);
			moveResultJson = HttpUtil.sendFormPost(moveUrl, strParam);
			logger.info("下发任务返回=" + moveResultJson);
			JSONObject jsonObj = new JSONObject(moveResultJson);
			JSONObject jsonObjS = jsonObj.getJSONObject("newsStatuData");
			return jsonObjS.getBoolean("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("解析返回参数失败！moveResultJson=" + moveResultJson);
		}
		return false;
	}

}
