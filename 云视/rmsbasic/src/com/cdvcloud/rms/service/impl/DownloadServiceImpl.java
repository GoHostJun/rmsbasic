package com.cdvcloud.rms.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.service.DownloadService;
import com.cdvcloud.rms.service.IOssService;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class DownloadServiceImpl implements DownloadService {

	private static final Logger logger = Logger.getLogger(DownloadServiceImpl.class);

	public static String SAVEPATH = null;
	static {
		// 本地储存文件路径
		try {
			SAVEPATH = System.getProperty("catalina.home") + File.separator + "webapps" + File.separator + "xmls" + File.separator;
			File file = new File(SAVEPATH);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			logger.error("创建xml文件储存文件夹出错:" + e.getMessage());
		}
	}

	@Autowired
	private BasicDao basicDao;
	@Autowired
	private IOssService ossService;

	@Override
	public void download(HttpServletRequest request, HttpServletResponse response, CommonParameters commonParameters, String strId) {
		if (!StringUtil.isEmpty(strId)) {
			logger.info("[下载单个文件]开始，id=" + strId);
			long longStrat = System.currentTimeMillis();
			// TODO 1、根据标识查询详情；2、获取视频内网http访问地址；3、获取视频的标题信息。
			String strUrl = "";
			String strTitle = "";
			FileUtil.downloadHTTP(request, response, strUrl, strTitle);
			long longUsed = System.currentTimeMillis() - longStrat;
			logger.info("[下载单个文件]完成，用时 " + longUsed + " 毫秒！");
		} else {
			logger.warn("参数中没有传递资源标识信息，无法下载！");
		}
	}

	@Override
	public void download4zip(HttpServletRequest request, HttpServletResponse response, CommonParameters commonParameters, String strId) {
		if (!StringUtil.isEmpty(strId)) {
			logger.info("[打包下载文件]开始，id=" + strId);
			long longStrat = System.currentTimeMillis();
			Map<String, Object> mapNews = basicDao.findOne(News.NEWS, strId);
			Map<String, String> mapMedia = new HashMap<String, String>();
			putMedia(mapNews, mapMedia, request);
			String strTitle = String.valueOf(mapNews.get(News.TITLE));
			FileUtil.downloadHTTP4Zip(request, response, mapMedia, strTitle);
			long longUsed = System.currentTimeMillis() - longStrat;
			logger.info("[打包下载文件]完成，用时 " + longUsed + " 毫秒！");
			// 删除打包时生成xml文件
			String strFilepath = String.valueOf(mapMedia.get("info.xml"));
//			String strFilename = FileUtil.getName(strXmlURL);
//			String strFilepath = SAVEPATH + strFilename;
			if (FileUtil.delFile(strFilepath)) {
				logger.info("删除临时文件xml成功：" + strFilepath);
			} else {
				logger.warn("删除临时文件xml失败：" + strFilepath);
			}
		} else {
			logger.warn("参数中没有传递资源标识信息，无法下载！");
		}
	}

	/**
	 * 拼接打包下载的信息
	 * 
	 * @param mapNews
	 * @param mapMedia
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void putMedia(Map<String, Object> mapNews, Map<String, String> mapMedia, HttpServletRequest request) {
		Document document = DocumentHelper.createDocument();
		Element xmlBody = document.addElement("xml-body");
		Element catalogue = xmlBody.addElement("catalogue");
		Map<String, Object> mapTemplate = (Map<String, Object>) mapNews.get(News.TEMPLATE);
		if(!StringUtil.isEmpty(mapTemplate.get("title"))){
			catalogue.addElement("title").setText(String.valueOf(mapTemplate.get("title")));
		}
		if(!StringUtil.isEmpty(mapTemplate.get("program"))){
			catalogue.addElement("program").setText(String.valueOf(mapTemplate.get("program")));
		}
		if(!StringUtil.isEmpty(mapTemplate.get("createor"))){
			catalogue.addElement("createor").setText(String.valueOf(mapTemplate.get("createor")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("reporter"))){
			catalogue.addElement("reporter").setText(String.valueOf(mapTemplate.get("reporter")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("tvStationName"))){
			catalogue.addElement("tvStationName").setText(String.valueOf(mapTemplate.get("tvStationName")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("tvStationCode"))){
			catalogue.addElement("tvStationCode").setText(String.valueOf(mapTemplate.get("tvStationCode")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("customType"))){
			catalogue.addElement("customType").setText(String.valueOf(mapTemplate.get("customType")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("keyWords"))){
			catalogue.addElement("keyWords").setText(String.valueOf(mapTemplate.get("keyWords")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("source"))){
			catalogue.addElement("source").setText(String.valueOf(mapTemplate.get("source")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("presenter"))){
			catalogue.addElement("presenter").setText(String.valueOf(mapTemplate.get("presenter")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("dubbingMan"))){
			catalogue.addElement("dubbingMan").setText(String.valueOf(mapTemplate.get("dubbingMan")));
	
		}
		if(!StringUtil.isEmpty(mapTemplate.get("cameraMan"))){
			catalogue.addElement("cameraMan").setText(String.valueOf(mapTemplate.get("cameraMan")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("editor"))){
			catalogue.addElement("editor").setText(String.valueOf(mapTemplate.get("editor")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("repProviders"))){
			catalogue.addElement("repProviders").setText(String.valueOf(mapTemplate.get("repProviders")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("titleType"))){
			catalogue.addElement("titleType").setText(String.valueOf(mapTemplate.get("titleType")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("playDate"))){
			catalogue.addElement("playDate").setText(String.valueOf(mapTemplate.get("playDate")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("titleDesign"))){
			catalogue.addElement("titleDesign").setText(String.valueOf(mapTemplate.get("titleDesign")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("assistants"))){
			catalogue.addElement("assistants").setText(String.valueOf(mapTemplate.get("assistants")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("subtitleWords"))){
			catalogue.addElement("subtitleWords").setText(String.valueOf(mapTemplate.get("subtitleWords")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("specialReporters"))){
			catalogue.addElement("specialReporters").setText(String.valueOf(mapTemplate.get("specialReporters")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("content"))){
			catalogue.addElement("content").setText(String.valueOf(mapTemplate.get("content")));

		}
		if(!StringUtil.isEmpty(mapTemplate.get("docsContentHTML"))){
			catalogue.addElement("docsContentHTML").setText(String.valueOf(mapTemplate.get("docsContentHTML")));

		}
		Element media = xmlBody.addElement("media");
		List<Map<String, Object>> listVideos = (List<Map<String, Object>>) mapNews.get(News.VIDEOS);
		if (null != listVideos && !listVideos.isEmpty()) {
			Element video = media.addElement("videos").addElement("video");
			for (Map<String, Object> map : listVideos) {
				Map<String, Object> mapPrewar = (Map<String, Object>) map.get("prewar");
				if (null != mapPrewar && !mapPrewar.isEmpty()) {
					String strName = String.valueOf(map.get("name"));
					String strURL = String.valueOf(mapPrewar.get("lenurl"));
					getName(strName, strURL);
					strName += "." + String.valueOf(mapPrewar.get("fmt"));
					mapMedia.put(strName, strURL);
					video.setText(strName);
				}
				List<Map<String, Object>> mapDefaults = (List<Map<String, Object>>) map.get("defaults");
				if (null != mapDefaults && !mapDefaults.isEmpty()) {
					for (Map<String, Object> defaults : mapDefaults) {
						String strName = String.valueOf(map.get("name"));
						String strURL = String.valueOf(defaults.get("lenurl"));
						getName(strName, strURL);
						strName += "." + String.valueOf(defaults.get("fmt"));
						mapMedia.put(strName, strURL);
						video.setText(strName);
					}
				}
				String lenurl =String.valueOf( map.get("lenurl"));
				if(!StringUtil.isEmpty(lenurl)){
					String strName = String.valueOf(map.get("name"));
					String strURL = lenurl;
					getName(strName, strURL);
					if(!StringUtil.isEmpty(String.valueOf(map.get("fmt")))){
						strName += "." + String.valueOf(map.get("fmt"));
					}
					mapMedia.put(strName, strURL);
					video.setText(strName);
				}
			}
		}
		List<Map<String, Object>> listAudios = (List<Map<String, Object>>) mapNews.get(News.AUDIOS);
		if (null != listAudios && !listAudios.isEmpty()) {
			Element audio = media.addElement("audios").addElement("audio");
			for (Map<String, Object> map : listAudios) {
				Map<String, Object> mapPrewar = (Map<String, Object>) map.get("prewar");
				if (null != mapPrewar && !mapPrewar.isEmpty()) {
					String strName = String.valueOf(map.get("name"));
					String strURL = String.valueOf(mapPrewar.get("lenurl"));
					getName(strName, strURL);
					strName += "." + String.valueOf(mapPrewar.get("fmt"));
					mapMedia.put(strName, strURL);
					audio.setText(strName);
				}
				String lenurl =String.valueOf( map.get("lenurl"));
				if(!StringUtil.isEmpty(lenurl)){
					String strName = String.valueOf(map.get("name"));
					String strURL = lenurl;
					getName(strName, strURL);
					if(!StringUtil.isEmpty(String.valueOf(map.get("fmt")))){
						strName += "." + String.valueOf(map.get("fmt"));
					}
					mapMedia.put(strName, strURL);
					audio.setText(strName);
				}
			}
		}
		List<Map<String, Object>> listPics = (List<Map<String, Object>>) mapNews.get(News.PICS);
		if (null != listPics && !listPics.isEmpty()) {
			Element picture = media.addElement("pictures").addElement("picture");
			for (Map<String, Object> map : listPics) {
				if (null != map && !map.isEmpty()) {
					String strName = String.valueOf(map.get("name"));
					String strURL = String.valueOf(map.get("lenurl"));
					getName(strName, strURL);
					strName += "." + String.valueOf(map.get("fmt"));
					mapMedia.put(strName, strURL);
					picture.setText(strName);
				}
				
			}
		}
		XMLWriter xmlWriter = null;
		try {
			String strFilename = SAVEPATH;
			String strName = StringUtil.randomUUID() + ".xml";
			strFilename += strName;
			Writer fileWriter = new FileWriter(strFilename);
			xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.flush();
			xmlWriter.close();
//			String strXMLURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/xmls/" + strName;
			mapMedia.put("info.xml", strFilename);
		} catch (IOException e) {
			logger.error("生成xml文件时异常：" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != xmlWriter) {
				try {
					xmlWriter.close();
				} catch (IOException e) {
					logger.error("关闭xmlWriter时异常：" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据url拼接name
	 * 
	 * @param strName
	 * @param strURL
	 */
	private void getName(String strName, String strURL) {
		String[] arrStr = strURL.split("&");
		for (String string : arrStr) {
			if (string.startsWith("cType") || string.startsWith("tType")) {
				strName += "_" + String.valueOf(string.split("=")[1]).toUpperCase();
			}
		}
	}

	@Override
	public void downLoadMaterialZip(HttpServletRequest request, HttpServletResponse response, CommonParameters commonParameters, String strId,String type) {
		if(!StringUtil.isEmpty(strId)){
			logger.info("[打包下载文件]开始，id=" + strId);
			Map<String,Object> media=basicDao.findOne(Media.MATERIAL, strId);
			Map<String, String> mediaUrl = new HashMap<String, String>();
			if("0".equals(type)){
				putTransMedia(media, mediaUrl, request);
			}else if("1".equals(type)){
				putOrainMedia(media, mediaUrl, request);
			}else{
				putSingleMedia(media, mediaUrl, request);
			}
			String title=String.valueOf(media.get(Media.NAME));
			long longStrat = System.currentTimeMillis();
			
			if(mediaUrl.isEmpty()){
				
			}
			FileUtil.downloadHTTP4Zip(request, response, mediaUrl, title);
			long longUsed = System.currentTimeMillis() - longStrat;
			logger.info("[打包下载文件]完成，用时 " + longUsed + " 毫秒！");
		}else {
			logger.warn("参数中没有传递资源标识信息，无法下载！");
		}
		
	}
	/**
	 * 转码
	 * @param media
	 * @param mediaUrl
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void putTransMedia(Map<String,Object> media,Map<String,String> mediaUrl,HttpServletRequest request){
		if("0".equals(media.get("mtype"))||"1".equals(media.get("mtype"))){
			Map<String, Object> mapPrewar = (Map<String, Object>) media.get("prewar");
			if (null != mapPrewar && !mapPrewar.isEmpty()) {
				String strName = String.valueOf(media.get("name"));
				String strURL = String.valueOf(mapPrewar.get("lenurl"));
				getName(strName, strURL);
				strName +="("+String.valueOf(mapPrewar.get("width"))+
						"*"+String.valueOf(mapPrewar.get("height"))+","
							+String.valueOf(mapPrewar.get("rate"))+"Kbps)";
				strName += "." + String.valueOf(mapPrewar.get("fmt"));
//				if(!StringUtil.isEmpty(mediaUrl.get(name))){
//					String  reName="";
//					reName=strName+"." + String.valueOf(mapPrewar.get("fmt"));
//					mediaUrl.put(reName, strURL);
//				}
			}
			List<Map<String, Object>> mapDefaults = (List<Map<String, Object>>) media.get("defaults");
			if (null != mapDefaults && !mapDefaults.isEmpty()) {
				for (Map<String, Object> defaults : mapDefaults) {
					String strName = String.valueOf(media.get("name"));
					String strURL = String.valueOf(defaults.get("lenurl"));
					getName(strName, strURL);
					strName +="("+String.valueOf(defaults.get("width"))+
							"*"+String.valueOf(defaults.get("height"))+","
								+String.valueOf(defaults.get("rate"))+"Kbps)";
					strName += "." + String.valueOf(defaults.get("fmt"));
					mediaUrl.put(strName, strURL);
				}
			}
			
		}else if("2".equals(media.get("mtype"))){
			
		}
	}
	/**
	 * 原地址
	 * @param media
	 * @param mediaUrl
	 * @param request
	 */
	private void putOrainMedia(Map<String,Object> media,Map<String,String> mediaUrl,HttpServletRequest request){
		String lenurl =String.valueOf( media.get("lenurl"));
		if(!StringUtil.isEmpty(lenurl)){
			String strName = String.valueOf(media.get("name"));
			String strURL = lenurl;
			getName(strName, strURL);
			if(!StringUtil.isEmpty(String.valueOf(media.get("fmt")))){
				strName += "." + String.valueOf(media.get("fmt"));
			}
			mediaUrl.put(strName, strURL);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void putSingleMedia(Map<String,Object> media,Map<String,String> mediaUrl,HttpServletRequest request){
		if("0".equals(media.get("mtype"))||"1".equals(media.get("mtype"))){
			Map<String, Object> mapPrewar = (Map<String, Object>) media.get("prewar");
			if (null != mapPrewar && !mapPrewar.isEmpty()) {
				String strName = String.valueOf(media.get("name"));
				String strURL = String.valueOf(mapPrewar.get("lenurl"));
				getName(strName, strURL);
				strName += "." + String.valueOf(mapPrewar.get("fmt"));
				mediaUrl.put(strName, strURL);
			}
			List<Map<String, Object>> mapDefaults = (List<Map<String, Object>>) media.get("defaults");
			if (null != mapDefaults && !mapDefaults.isEmpty()) {
				for (Map<String, Object> defaults : mapDefaults) {
					String strName = String.valueOf(media.get("name"));
					String strURL = String.valueOf(defaults.get("lenurl"));
					getName(strName, strURL);
					strName += "." + String.valueOf(defaults.get("fmt"));
					mediaUrl.put(strName, strURL);
				}
			}
			
		}else if("2".equals(media.get("mtype"))){
			
		}
		String lenurl =String.valueOf( media.get("lenurl"));
		if(!StringUtil.isEmpty(lenurl)){
			String strName = String.valueOf(media.get("name"));
			String strURL = lenurl;
			getName(strName, strURL);
			if(!StringUtil.isEmpty(String.valueOf(media.get("fmt")))){
				strName += "." + String.valueOf(media.get("fmt"));
			}
			mediaUrl.put(strName, strURL);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> uploadXMLContent(Map<String, Object> newsMap, CommonParameters commonParameters) {
		Map<String, Object> mapContent = null;
		if (newsMap.containsKey(News.TEMPLATE) && !StringUtil.isEmpty(newsMap.get(News.TEMPLATE))) {
			Map<String, Object> mapTemplate = (Map<String, Object>) newsMap.get(News.TEMPLATE);
			Document document = DocumentHelper.createDocument();
			Element programRoot = document.addElement("NewsItem");
			programRoot.addElement("Creater").addText(commonParameters.getLoginName());//创建人（在文稿中自己的目录下能看到则必填）
			programRoot.addElement("OwnColumn").addText(String.valueOf(mapTemplate.get("program")));//所属栏目（在文稿中小组的目录下能看到则必填）
			programRoot.addElement("CreateTime").addText(String.valueOf(newsMap.get(News.CTIME)));//创建时间（线索的创建时间）
			programRoot.addElement("CreatMethod").addText("");//创建方式
			if (StringUtil.isEmpty(mapTemplate.get("source"))) {
				programRoot.addElement("NewsSource").addText("");//来源
			}else{
				programRoot.addElement("NewsSource").addText(String.valueOf(mapTemplate.get("source")));//来源
			}
			programRoot.addElement("NewsType").addText("");//类别
			programRoot.addElement("NewsLanguage").addText("");//语言
			programRoot.addElement("NewsTitle").addText(String.valueOf(newsMap.get(News.TITLE)));//标题
			programRoot.addElement("NewsTitle1").addText("");//标题别名
			programRoot.addElement("NewsTitle2").addText("");//标题别名
			programRoot.addElement("NewsRemark").addText("");//备注
			if (StringUtil.isEmpty(mapTemplate.get("keyWords"))) {
				programRoot.addElement("NewsKeyword").addText("");//关键字
			}else{
				programRoot.addElement("NewsKeyword").addText(String.valueOf(mapTemplate.get("keyWords")));//关键字
			}
			if (StringUtil.isEmpty(mapTemplate.get("docsContentHTML"))) {
				programRoot.addElement("NewsContent").addText("");//内容
			}else{
				programRoot.addElement("NewsContent").addText(String.valueOf(mapTemplate.get("content")));//内容
			}
			if (StringUtil.isEmpty(mapTemplate.get("reporter"))) {
				programRoot.addElement("Reporter").addText("");//记者
			}else{
				programRoot.addElement("Reporter").addText(String.valueOf(mapTemplate.get("reporter")));//记者
			}
			programRoot.addElement("GuestMan").addText("");//特约记者
			if (StringUtil.isEmpty(mapTemplate.get("cameraMan"))) {
				programRoot.addElement("Camerist").addText("");//摄像员
			}else{
				programRoot.addElement("Camerist").addText(String.valueOf(mapTemplate.get("cameraMan")));//摄像员
			}
			programRoot.addElement("BatMan").addText("");//通讯员
			try {
				OutputFormat format = OutputFormat.createCompactFormat();
				format.setEncoding("UTF-8");
				String strFilename = SAVEPATH;
				String strName = StringUtil.randomUUID() + ".xml";
				strFilename += strName;
				File file = new File(strFilename);
				XMLWriter output = new StandaloneWriter(new FileWriter(file), format);
				output.write(document);
				output.flush();
				output.close();
				mapContent= ossService.uploadOss(commonParameters, strFilename, strName, "text");
				mapContent.put("xmlLocalUrl", strFilename);
			} catch (Exception e) {
				logger.error("生成xml，出现错误[" + e + "]");
			}
		}
		return mapContent;
	}
	
	protected class StandaloneWriter extends XMLWriter {
		public StandaloneWriter(Writer writer, OutputFormat format)throws UnsupportedEncodingException{  
            super(writer,format);  
        }
		protected void writeDeclaration() throws IOException {
			OutputFormat format = getOutputFormat();
			String encoding = format.getEncoding();
			if (!format.isSuppressDeclaration()) {
				writer.write("<?xml version=\"1.0\"");
				if (!format.isOmitEncoding()) {
					if (encoding.equals("UTF8"))
						writer.write(" encoding=\"UTF-8\"");
					else
						writer.write(" encoding=\"" + encoding + "\"");
				}
//				writer.write(" standalone=\"no\"");
				writer.write("?>");
				if (format.isNewLineAfterDeclaration()) { println(); }
			}
		}
	}

}
