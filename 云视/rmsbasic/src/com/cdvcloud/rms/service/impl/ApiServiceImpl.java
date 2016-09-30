package com.cdvcloud.rms.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Cues;
import com.cdvcloud.rms.domain.FieldNews;
import com.cdvcloud.rms.domain.MaterialSet;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.Scan;
import com.cdvcloud.rms.domain.ScanLog;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IApiService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.IPresentationService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Service
public class ApiServiceImpl extends BasicService implements IApiService {
	private static final Logger logger = Logger.getLogger(ApiServiceImpl.class);
	@Autowired
	private BasicDao basicDao;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private IWeChatService weChatService;
	@Autowired
	private IPresentationService materialService;
	@Autowired
	private IMediaService mediaService;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IUserService userService;
	@Override
	public Map<String, Object> getFilednewsById(String id) {
		Map<String, Object> mapQueryBack = new HashMap<String, Object>();
		mapQueryBack.put(FieldNews.TITLE, 1);
		mapQueryBack.put(FieldNews.CUSENAME, 1);
		mapQueryBack.put(FieldNews.THUMBNAILURL, 1);
		mapQueryBack.put(FieldNews.PARTICIPANTS, 1);
		mapQueryBack.put(FieldNews.PUSHTOTAL, 1);
		mapQueryBack.put(FieldNews.CTIME, 1);
		Map<String, Object> mapResult = basicDao.findOne(FieldNews.FIELDNEWS, id, mapQueryBack);
//		if (null != mapResult && mapResult.containsKey(FieldNews.PARTICIPANTS) && null != mapResult.get(FieldNews.PARTICIPANTS)) {
//			List<Map<String, Object>> participantsMap = (List<Map<String, Object>>) mapResult.get(FieldNews.PARTICIPANTS);
//			mapResult.put(FieldNews.PARTICIPANTS, participantsMap.size());
//		}
		return mapResult;
	}

	@Override
	public Map<String, Object> getMaterialSetById(String id) {
		Map<String, Object> mapQueryBack = new HashMap<String, Object>();
		mapQueryBack.put(MaterialSet.TITLE, 1);
		mapQueryBack.put(MaterialSet.CUSENAME, 1);
		mapQueryBack.put(MaterialSet.THUMBNAILURL, 1);
		mapQueryBack.put(MaterialSet.PARTICIPANTS, 1);
		mapQueryBack.put(MaterialSet.SRC, 1);
		mapQueryBack.put(MaterialSet.CTIME, 1);
		Map<String, Object> mapResult = basicDao.findOne(MaterialSet.MATERIAL, id, mapQueryBack);
//		if (null != mapResult && mapResult.containsKey(FieldNews.PARTICIPANTS) && null != mapResult.get(FieldNews.PARTICIPANTS)) {
//			List<Map<String, Object>> participantsMap = (List<Map<String, Object>>) mapResult.get(FieldNews.PARTICIPANTS);
//			mapResult.put(FieldNews.PARTICIPANTS, participantsMap.size());
//		}
		return mapResult;
	}

	@Override
	public String sendlog(String json) {
		Map<String, Object> map = JsonUtil.readJSON2Map(json);
		map.put(ScanLog.CONSUMERID, String.valueOf(map.get(ScanLog.COMPANYID)));
		return basicDao.insert(ScanLog.SCANLOG, map);
	}

	@Override
	public List<Map<String, Object>> getscanTask(String json) {
		Map<String, Object> jsonMap = JsonUtil.readJSON2Map(json);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put(Scan.TARDIC, String.valueOf(jsonMap.get(Scan.TARDIC)));
		List<Map<String, Object>> list = basicDao.find(Scan.SCAN, whereMap, 1, Integer.MAX_VALUE);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject getCDVCueById(CommonParameters common, String json) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			JSONObject jb = new JSONObject();
			Map<String, Object> strMap = JsonUtil.readJSON2Map(json);
			jb.put("firstRowNum", 0);
			jb.put("maxResults", 10);
			jb.put("fullText", "");
			JSONObject facetjson = new JSONObject();
			facetjson.put("limit", 50);
			JSONArray ja = new JSONArray();
			JSONObject jface = new JSONObject();
			jface.put("attributeDefID", "platform");
			ja.put(jface);
			facetjson.put("fieldList", ja);

			jb.put("facet", facetjson);

			JSONObject groupjson = new JSONObject();
			groupjson.put("operator", "AND");

			JSONArray searchGroupListjson = new JSONArray();
			JSONObject j = new JSONObject();
			j.put("operator", "OR");

			j.put("searchGroupList", new JSONArray());
			searchGroupListjson.put(j);
			groupjson.put("searchGroupList", searchGroupListjson);

			JSONArray attributeConditionListjson = new JSONArray();

			JSONObject on1 = new JSONObject();
			on1.put("attributeDefID", "assetcategoryid");
			on1.put("comparator", "EQ");
			on1.put("value", "clue");
			attributeConditionListjson.put(on1);
			JSONObject on2 = new JSONObject();
			on2.put("attributeDefID", "editstatus");
			on2.put("comparator", "GE");
			on2.put("value", "2");
			attributeConditionListjson.put(on2);
			JSONObject on3 = new JSONObject();
			on3.put("attributeDefID", "deletedflag");
			on3.put("comparator", "EQ");
			on3.put("value", 0);
			attributeConditionListjson.put(on3);
			JSONObject on4 = new JSONObject();
			on4.put("attributeDefID", "moid");
			on4.put("comparator", "EQ");
			on4.put("value", strMap.get("_id"));
			attributeConditionListjson.put(on4);
			groupjson.put("attributeConditionList", attributeConditionListjson);
			jb.put("searchGroup", groupjson);
			JSONArray orderarr = new JSONArray();
			JSONObject orjson = new JSONObject();
			orjson.put("attributeDefID", "creationdate");
			orjson.put("desc", true);
			orderarr.put(orjson);
			jb.put("orderList", orderarr);
			JSONArray defjson = new JSONArray();
			defjson.put("moid");
			defjson.put("assetname");
			defjson.put("assetcategoryid");
			defjson.put("editstatus");
			defjson.put("creationdate");
			defjson.put("submittime");
			defjson.put("description");
			defjson.put("content");
			defjson.put("author");
			defjson.put("thumbnailfileid");
			defjson.put("videoflag");
			defjson.put("audioflag");
			defjson.put("picflag");
			defjson.put("site");
			defjson.put("tags");
			defjson.put("refcount");
			defjson.put("playcount");
			defjson.put("importlevel");
			defjson.put("createdto");
			defjson.put("platform");
			defjson.put("channelpath");
			defjson.put("prostation");
			defjson.put("rep_actor");
			jb.put("attributeDefIDList", defjson);
			String cdvUrl = configurationService.getCDVUrl();
			String backStr = HttpUtil.doPost(cdvUrl + "/web/api/app/v3/searchAsset", jb.toString());
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, Object> backMap = JsonUtil.readJSON2Map(backStr);
			if ("0".equals(String.valueOf(backMap.get("code")))) {
				Map<String, Object> resultMap = (Map<String, Object>) backMap.get("result");
				// Map<String,Object>facetResultMap=(Map<String, Object>)
				// resultMap.get("facetResult");
				// Map<String,Object>fieldList=(Map<String, Object>)
				// backMap.get("fieldList");

				List<Map<String, Object>> maps = (List<Map<String, Object>>) resultMap.get("assetList");
				Map<String, Object> innerMap = maps.get(0);
				List<Map<String, Object>> attributeMap = (List<Map<String, Object>>) innerMap.get("attributeList");
				List<Map<String, Object>> thumbs = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map : attributeMap) {
					if ("creationdate".equals(String.valueOf(map.get("attributeDefID")))) {
						dataMap.put("creationdate", map.get("dataValue"));
					} else if ("content".equals(String.valueOf(map.get("attributeDefID")))) {
						dataMap.put("content", map.get("dataValue"));
					} else if ("assetname".equals(String.valueOf(map.get("attributeDefID")))) {
						dataMap.put("assetname", map.get("dataValue"));
					} else if ("prostation".equals(String.valueOf(map.get("attributeDefID")))) {
						dataMap.put("prostation", map.get("dataValue"));
					} else if ("thumbnailfileid".equals(String.valueOf(map.get("attributeDefID")))) {
						Map<String, Object> thumb = new HashMap<String, Object>();
						thumb.put("thumb", map.get("dataValue"));
						thumbs.add(thumb);
						dataMap.put("thumbnailfileid", thumbs);
					}
				}
				return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, dataMap);
			}
			return responseObject;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取新奥特新闻线索失败" + e.getMessage());
			return responseObject;

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject getSBCueById(CommonParameters common, String json) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			Map<String, Object> strMap = JsonUtil.readJSON2Map(json);
			String cdvUrl = configurationService.getSBUrl();
			StringBuffer params = new StringBuffer();
			String url = cdvUrl + "/tpp/rest/o/fts.json";
			params.append("?psoData.orderType=T").append("&extendData[businesstype]=4").append("&extendData[entitycolumn]=uid")
					.append("&extendData[label]=" + "{'uid':'" + strMap.get("_id") + "'}");
			String backStr = HttpUtil.sendGet(url, params.toString());
			Map<String, Object> backMap = JsonUtil.readJSON2Map(backStr);
			Map<String, Object> newsStatuDataMap = (Map<String, Object>) backMap.get("newsStatuData");
			if (!"true".equals(String.valueOf(newsStatuDataMap.get("success")))) {
				return responseObject;
			}
			Map<String, Object> pageMap = (Map<String, Object>) backMap.get("page");
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) pageMap.get("result");
			Map<String, Object> resultMap = resultList.get(0);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("content", resultMap.get("context"));
			dataMap.put("assetname", resultMap.get("title"));
			dataMap.put("prostation", resultMap.get("from"));
			long l = (Long) resultMap.get("pubdate");
			Date date = new Date(l);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = sdf.format(date);
			dataMap.put("creationdate", str);
			Map<String, Object> materialMap = (Map<String, Object>) resultMap.get("material");
			if (null != materialMap) {
				List<Map<String, Object>> materialFiles = (List<Map<String, Object>>) materialMap.get("materialFiles");
				List<Map<String, Object>> thumbs = new ArrayList<Map<String, Object>>();
				if (materialFiles != null && materialFiles.size() > 0) {
					for (Map<String, Object> files : materialFiles) {
						Map<String, Object> thumb = new HashMap<String, Object>();
						thumb.put("thumb", files.get("filename"));
						thumbs.add(thumb);
					}
					dataMap.put("thumbnailfileid", thumbs);
				}
			}

			responseObject = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, dataMap);
			return responseObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取索贝新闻线索失败" + e.getMessage());
			return responseObject;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject getZYCueById(CommonParameters common, String json) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try{
			Map<String, Object> strMap = JsonUtil.readJSON2Map(json);
			String zyUrl = configurationService.getZYurl();
			String params = "";
			String url = zyUrl +"appCode/V1/"
			+common.getCompanyId()+"/"
					+common.getUserId()+"/serviceCode/detail/getDetail";
//			params.append("?psoData.orderType=T").append("&extendData[businesstype]=4").append("&extendData[entitycolumn]=uid")
//					.append("&extendData[label]=" + "{'uid':'" + strMap.get("_id") + "'}");
			params="newsId="+strMap.get("_id")+"&sourceFlag=aaaa";
			String backStr = HttpUtil.sendGet(url, params.toString());
			Map<String, Object> backMap = JsonUtil.readJSON2Map(backStr);
			String status=String.valueOf(backMap.get("status"));
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if("200".equals(status)){
				Map<String, Object> resultMap = (Map<String, Object>) backMap.get("data");
				dataMap.put("content", resultMap.get("content"));
				dataMap.put("assetname", resultMap.get("newsTitle"));
				dataMap.put("prostation", resultMap.get("newsSource"));
				dataMap.put("creationdate", resultMap.get("publishTime"));
				List<Map<String,Object>> images=(List<Map<String, Object>>) resultMap.get("images");
				List<Map<String, Object>> thumbs = new ArrayList<Map<String, Object>>();
				if(null!=images&&images.size()>0){
					for (Map<String, Object> image : images) {
						Map<String, Object> thumb = new HashMap<String, Object>();
						thumb.put("thumb", image.get("url"));
						thumbs.add(thumb);
					}
					dataMap.put("thumbnailfileid", thumbs);
				}
				responseObject=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, dataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取智云新闻线索失败" + e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	
	@Override
	public void getOpenWX(String shareId, String type, HttpServletRequest request) {
		String openAuthUrl = weChatService.getOpenAuthPubWeChat();
		String appID = configurationService.getWeChatPubAppid();
		String subbmit=request.getParameter("subbmit");
		String loginid=request.getParameter("loginid");
		String nikename=request.getParameter("nikename");
		String wxUrl = configurationService.getWxclient() + "/ytlmain/getWeChatUserInforByShare/ytl/?callback="
				+ configurationService.getOuterCallback() + "/api/share/weChatCallback/&shareId=" + shareId
				+ "&type=" + type+"&subbmit="+subbmit+"&loginid="+loginid+"&nikename="+nikename+"&title="+request.getAttribute("title")
				+"&imgUrl="+request.getAttribute("imgUrl")+"&from="+request.getParameter("from")
				+"&isappinstalled="+request.getParameter("isappinstalled");
		try {
			wxUrl = URLEncoder.encode(wxUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String openUrl = openAuthUrl.replace("APPID", appID).replace("REDIRECT_URI", wxUrl);
		request.setAttribute("openUrl", openUrl);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject addDocsFromZY(CommonParameters common, String jsonstr) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			logger.info("智云审核通过参数"+jsonstr);
			Map<String, Object> strMap = JsonUtil.readJSON2Map(jsonstr);
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(Cues.CUESID, strMap.get("id"));
			List<Map<String, Object>> cues = basicDao.find(Cues.CUES, filter, 1, Integer.MAX_VALUE);
			if (cues.size() > 0) {
				return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
			}
			JSONObject json = new JSONObject();
			JSONObject jb = new JSONObject();
			jb.put("source", "云报道");
			jb.put("userId", strMap.get("subbmit"));
			jb.put("subscriptionType", "云报道");
			jb.put("userName", strMap.get("loginid"));
			jb.put("type", "news");
			json.put("source_id", strMap.get("id"));
			json.put("content", strMap.get("cueContent"));
			json.put("author", strMap.get("nikename"));
			json.put("title", strMap.get("title"));
			json.put("prostation", strMap.get("prostation"));
			json.put("source_site", "app报题");
			json.put("videos", new JSONArray());
			JSONArray ja = new JSONArray();
			List<String> thumbs = (List<String>) strMap.get("thumbs");
			List<Map<String, Object>> imgList = new ArrayList<Map<String, Object>>();
			if (null != thumbs && thumbs.size() > 0) {
				for (int i = 0; i < thumbs.size(); i++) {
					JSONObject j = new JSONObject();
					Map<String, Object> img = new HashMap<String, Object>();
					String m = thumbs.get(i);
					String[] str = m.split("/");
					j.put("name", str[str.length - 1]);
					j.put("url", m);
					ja.put(j);
					img.put("name", str[str.length - 1]);
					img.put("url", m);
					imgList.add(img);
				}
			}
			json.put("images", ja);
			logger.info("智云审核通过参数imgList"+imgList.toString());
			jb.put("data", json);
				Map<String, Object> cue = new HashMap<String, Object>();
				cue.put(Cues.CUESID, strMap.get("id"));
				basicDao.insert(Cues.CUES, cue);
				Map<String, Object> imgMap = new HashMap<String, Object>();
				//重新查询用户
				Map<String,Object> userMap=userService.getUserInforById(String.valueOf(strMap.get("subbmit")));
				CommonParameters commonParameters=new CommonParameters();
				commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
				commonParameters.setAppCode(Constants.APPCODEVALUE);
				commonParameters.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
				commonParameters.setUserId(String.valueOf(userMap.get(User.ID)));
				if (imgList.size() > 0) {
					imgMap.put("images", imgList);
					responseObject = registResource(commonParameters, imgMap);
					if (0 == responseObject.getCode()) {
						json.put("picIds", responseObject.getData());
					} else {
						json.put("picIds", "");
					}
				} else {
					json.put("picIds", "");
				}
				
				responseObject = addMyDoc(commonParameters, json);
				if (0 == responseObject.getCode()) {
					// 日志加入
					systemLogService.inset(SystemLogUtil.getMap(common, "0", "pc端智云线索审核通过", "线索id:" + strMap.get("id")));
					LogUtil.printIntegralLog(common, "checkclew", "pc端智云线索审核通过，线索id:" + strMap.get("id"));
					return responseObject;
				}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加文稿库失败" + e.getMessage());
			return responseObject;
		}
		return responseObject;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject addClubTask(CommonParameters common, String jsonstr) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			Map<String, Object> strMap = JsonUtil.readJSON2Map(jsonstr);
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(Cues.CUESID, strMap.get("id"));
			List<Map<String, Object>> cues = basicDao.find(Cues.CUES, filter, 1, Integer.MAX_VALUE);
			if (cues.size() > 0) {
				return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
			}
			JSONObject json = new JSONObject();
			JSONObject jb = new JSONObject();
			jb.put("source", "云报道");
			jb.put("userId", strMap.get("subbmit"));
			jb.put("subscriptionType", "云报道");
			jb.put("userName", strMap.get("loginid"));
			jb.put("type", "news");
			json.put("source_id", strMap.get("id"));
			json.put("content", strMap.get("cueContent"));
			json.put("author", strMap.get("nikename"));
			json.put("title", strMap.get("title"));
			json.put("prostation", strMap.get("prostation"));
			json.put("source_site", "app报题");
			json.put("videos", new JSONArray());
			JSONArray ja = new JSONArray();
			List<String> thumbs = (List<String>) strMap.get("thumbs");
			List<Map<String, Object>> imgList = new ArrayList<Map<String, Object>>();
			if (null != thumbs && thumbs.size() > 0) {
				for (int i = 0; i < thumbs.size(); i++) {
					JSONObject j = new JSONObject();
					Map<String, Object> img = new HashMap<String, Object>();
					String m = thumbs.get(i);
					String[] str = m.split("/");
					j.put("name", str[str.length - 1]);
					j.put("url", m);
					ja.put(j);
					img.put("name", str[str.length - 1]);
					img.put("url", m);
					imgList.add(img);
				}
			}
			json.put("images", ja);

			jb.put("data", json);
			String cdvUrl = configurationService.getCDVUrl();
			logger.info("插入报题库参数：" + jb.toString());
			String backStr = HttpUtil.doPost(cdvUrl + "/web/api/onair/register", jb.toString());
			Map<String, Object> backMap = JsonUtil.readJSON2Map(backStr);
			logger.info("插入报题库返回：" + backStr);
			if ("200".equals(String.valueOf(backMap.get("status")))) {
				Map<String, Object> cue = new HashMap<String, Object>();
				cue.put(Cues.CUESID, strMap.get("id"));
				basicDao.insert(Cues.CUES, cue);
				Map<String, Object> imgMap = new HashMap<String, Object>();
				//重新查询用户
				Map<String,Object> userMap=userService.getUserInforById(String.valueOf(strMap.get("subbmit")));
				CommonParameters commonParameters=new CommonParameters();
				commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
				commonParameters.setAppCode(Constants.APPCODEVALUE);
				commonParameters.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
				commonParameters.setUserId(String.valueOf(userMap.get(User.ID)));
				if (imgList.size() > 0) {
					imgMap.put("images", imgList);
					responseObject = registResource(commonParameters, imgMap);
					if (0 == responseObject.getCode()) {
						json.put("picIds", responseObject.getData());
					} else {
						json.put("picIds", "");
					}
				} else {
					json.put("picIds", "");
				}
				
				responseObject = addMyDoc(commonParameters, json);
				if (0 == responseObject.getCode()) {
					// 日志加入
					systemLogService.inset(SystemLogUtil.getMap(common, "0", "pc端新闻线索审核通过", "线索id:" + strMap.get("id")));
					LogUtil.printIntegralLog(common, "checkclew", "pc端新闻线索审核通过，线索id:" + strMap.get("id"));
					return responseObject;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加报题库失败" + e.getMessage());
			return responseObject;
		}
		return responseObject;

	}

	@SuppressWarnings("unchecked")
	public ResponseObject addMyDoc(CommonParameters common, JSONObject json) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			JSONObject jb = new JSONObject();
			jb.put("accessToken", common.getAccessToken());
			jb.put("timeStamp", common.getTimeStamp());
			jb.put("title", json.get("title"));
			jb.put("src", "");
			JSONObject j = new JSONObject();
			j.put("title", json.get("title"));
			j.put("program", "");
			j.put("createor", json.get("author"));
			j.put("reporter", json.get("author"));
			j.put("tvStationName", common.getAreaName());
			j.put("tvStationCode", common.getAreaCode());
			j.put("customType", "");
			j.put("keyWords", "");
			j.put("source", "");
			j.put("presenter", "");
			j.put("dubbingMan", "");
			j.put("cameraMan", "");
			j.put("editor", "");
			j.put("repProviders", "");
			j.put("titleType", "");
			j.put("playDate", "");
			j.put("titleDesign", "");
			j.put("assistants", "");
			j.put("subtitleWords", "");
			j.put("specialReporters", "");
			j.put("docsContentHTML", json.get("content"));
			j.put("content", StringUtil.HtmlText(String.valueOf(json.get("content"))));
			jb.put("template", j);
			jb.put("areaname", common.getAreaName());
			jb.put("areacode", common.getAreaCode());
			jb.put("pushtoCheck", "");
			jb.put("currenttype", "");
			
			if (!"".equals(String.valueOf(json.get("picIds")))) {
				logger.info("picIds:"+String.valueOf(json.get("picIds")));
				Map<String,Object> picIdMap = (Map<String, Object> )json.get("picIds");
				List<String> picIds=(List<String>) picIdMap.get("ids");
				List<Map<String, Object>> pics = new ArrayList<Map<String, Object>>();
				String thumbnailurl="";
				for (String picId : picIds) {
					logger.info("picId为"+picId);
					Map<String, Object> pic = new HashMap<String, Object>();
					pic.put("id", picId);
					pics.add(pic);
					if("".equals(thumbnailurl)){
						Map<String,Object>picMap=basicDao.findOne(Media.MATERIAL, picId);
						thumbnailurl=String.valueOf(picMap.get(Media.WANURL));
					}
				}
				jb.put("picIds", pics);
				jb.put("thumbnailurl", thumbnailurl);
			}
			responseObject = materialService.insert(common, jb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加我的文稿库失败" + e.getMessage());
			return responseObject;
		}
		return responseObject;
	}

	// 如果有图片
	public ResponseObject registResource(CommonParameters common, Map<String, Object> json) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		Map<String, Object> jb = new HashMap<String, Object>();
		try {
			jb.put("accessToken", common.getAccessToken());
			jb.put("timeStamp", common.getTimeStamp());
			jb.put("mtype", "2");
			jb.putAll(json);
			logger.info("注册素材库参数json:" + JsonUtil.map2Json(jb));
			responseObject = mediaService.registerResource(common, JsonUtil.map2Json(jb), "新闻线索");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册素材失败");
			return responseObject;
		}
		return responseObject;

	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject addlive(CommonParameters common) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			jsonMap.put("accessToken", configurationService.getToken());
			jsonMap.put("timeStamp", DateUtil.getCurrentDateTime());
			jsonMap.put("loginName", common.getLoginName());
			jsonMap.put("companyCode", common.getCompanyId());
			String json=JsonUtil.map2Json(jsonMap);
			logger.info("添加直播通道参数json:" + json);
			String url=configurationService.getLiveurl()+"/"+common.getCompanyId()+"/"
			+common.getAppCode()+"/"+common.getUserId()+"/"
			+common.getServiceCode()+"/"+common.getVersionId()+"/live/open/";
			String result=HttpUtil.doPost(url, json);
			Map<String,Object> resultMap=JsonUtil.readJSON2Map(result);
			String pushUrl="";
			if("0".equals(String.valueOf(resultMap.get("code")))){
				Map<String,Object> data=(Map<String, Object>) resultMap.get("data");
				pushUrl=String.valueOf(data.get("pushUrl"));
				responseObject=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.detail, pushUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加直播通道失败");
			return responseObject;
		}
		return responseObject;
	}


	

}
