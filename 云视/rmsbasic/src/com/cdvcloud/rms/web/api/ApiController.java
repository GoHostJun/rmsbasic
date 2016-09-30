package com.cdvcloud.rms.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.domain.Role;
import com.cdvcloud.rms.domain.Scan;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.IApiService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
public class ApiController {
	private static final Logger logger = Logger.getLogger(ApiController.class);

	@Autowired
	private IApiService apiService;
	@Autowired
	private IMediaService materialService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IWeChatService weChatService;
	@Autowired
	private ValidateCommonParam validateCommonParam;

	/** 获取新闻线索分享页面信息 */
	@RequestMapping(value = "api/share/toNewsCues/")
	public String toNewsCues(HttpServletRequest request) {
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("imgUrl",  request.getParameter("imgUrl"));
		String shareId = request.getParameter("_id");
		String type = request.getParameter("type");
		apiService.getOpenWX(shareId, type, request);
		return "api/newsCues";
	}
	
	/** 获取智云线索分享页面信息 */
	@RequestMapping(value = "api/share/ToZYCues/")
	public String ToZYCues(HttpServletRequest request){
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("imgUrl",  request.getParameter("imgUrl"));
		String shareId = request.getParameter("_id");
		String type = request.getParameter("type");
		apiService.getOpenWX(shareId, type, request);
		return "api/zyCues";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "api/share/weChatCallback/")
	public String weChatCallback(HttpServletRequest request) {
		String type=request.getParameter("redictype");
		//redictype：转发详情页面会用到
		String redictUrl= toRedirectUrl(request, type);
		if(!"".equals(redictUrl)){
			return redictUrl;
		}
		Map<String, String[]> map = request.getParameterMap();
		Map<String, Object> mapJson = new HashMap<String, Object>();
		for (Entry<String, String[]> entry : map.entrySet()) {
			mapJson.put(entry.getKey(), entry.getValue()[0]);
		}
		// 判断当前微信是否绑定
		String json = JsonUtil.map2Json(mapJson);
		logger.info("微信回调" + json);
		ResponseObject checkResponse = null;
		checkResponse = weChatService.checkWeChatBind(json);
		if (GeneralStatus.wechat_unbind.status == checkResponse.getCode()) {
			return "wxshare/regist";
		} else {
			Map<String, Object> userMap = (Map<String, Object>) checkResponse.getData();
			request.setAttribute("userId", userMap.get(User.ID));
			request.setAttribute("consumerId", userMap.get(User.CONSUMERID));
			if ("myclues".equals(request.getParameter("type")) || "clues".equals(request.getParameter("type"))) {
				String roleStr = getRoleStr(userMap);
				request.setAttribute("role",roleStr);
				return "wxshare/detail";
			} else if ("doc".equals(request.getParameter("type"))) {
				return "wxshare/docDetail";
			}else if ("zyclues".equals(request.getParameter("type"))) {
				String roleStr = getRoleStr(userMap);
				request.setAttribute("role",roleStr);
				return "wxshare/zycluesDetail";
			}
		}
		return "";

	}

	private String toRedirectUrl(HttpServletRequest request, String type) {
		if(!StringUtil.isEmpty(type)&&"doc".equals(type)){
			return getShareDocsInfo(request, request.getParameter("_id"), request.getParameter("redictype"), request.getParameter("userId"));
		}else if(!StringUtil.isEmpty(type)&&"zyclues".equals(type)){
			return ToZYCues(request);
		}else if(!StringUtil.isEmpty(type)&&"myclues".equals(type)){
			return toNewsCues(request);
		}else if(!StringUtil.isEmpty(type)&&"clues".equals(type)){
			return toNewsCues(request);
		}
		return "";
	}
	
	
	@RequestMapping(value = "api/share/getMyCueView/")
	public String getMyCueView(HttpServletRequest request) {
		String type=request.getParameter("redictype");
		//redictype：转发详情页面会用到
		String redictUrl= toRedirectUrl(request, type);
		if(!"".equals(redictUrl)){
			return redictUrl;
		}
		
		request.setAttribute("userId", request.getParameter("userId"));
		request.setAttribute("consumerId", request.getParameter("consumerId"));
		Map<String, Object> userMap = userService.getUserInforById(request.getParameter("userId"));
		String roleStr = getRoleStr(userMap);
		request.setAttribute("role",roleStr);
		return "wxshare/detail";

	}
	@RequestMapping(value = "api/share/getZYCueView/")
	public String getZYCueView(HttpServletRequest request) {
		String type=request.getParameter("redictype");
		//redictype：转发详情页面会用到
		String redictUrl= toRedirectUrl(request, type);
		if(!"".equals(redictUrl)){
			return redictUrl;
		}
		
		request.setAttribute("userId", request.getParameter("userId"));
		request.setAttribute("consumerId", request.getParameter("consumerId"));
		Map<String, Object> userMap = userService.getUserInforById(request.getParameter("userId"));
		String roleStr = getRoleStr(userMap);
		request.setAttribute("role",roleStr);
		return "wxshare/zycluesDetail";
		
	}

	@RequestMapping(value = "api/share/getMyDocView/")
	public String getMyDocView(HttpServletRequest request) {
		String type=request.getParameter("redictype");
		//redictype：转发详情页面会用到
		String redictUrl= toRedirectUrl(request, type);
		if(!"".equals(redictUrl)){
			return redictUrl;
		}
		
		request.setAttribute("userId", request.getParameter("userId"));
		request.setAttribute("consumerId", request.getParameter("consumerId"));
		Map<String, Object> userMap = userService.getUserInforById(request.getParameter("userId"));
		String roleStr = getRoleStr(userMap);
		request.setAttribute("role",roleStr);
		return "wxshare/docDetail";

	}
	
	@SuppressWarnings("unchecked")
	private String getRoleStr(Map<String, Object> userMap){
		//默认是记者
		String roleStr="0";
		if (null != userMap && userMap.containsKey("role")) {
			List<Map<String, Object>> roles = (List<Map<String, Object>>) userMap.get("role");
			for (Map<String, Object> role : roles) {
				if ("审核人".equals(String.valueOf(role.get(Role.NAME)))) {
					roleStr = "1";
					break;
				}
			}
		}
		return roleStr;
	}

	/** 获取共享文稿分享页面信息 */
	@RequestMapping(value = "api/share/getShareDocsInfo/")
	public String getShareDocsInfo(HttpServletRequest request, @RequestParam(value = "_id") String _id, @RequestParam(value = "type") String type,
			@RequestParam(value = "userId") String userId) {
		logger.info("共享文稿分享页面信息请求进入公开接口，参数为：id=" + _id + ",type=" + type + ",userId=" + userId);
		try {
			Map<String, Object> mapInfo = apiService.getFilednewsById(_id);
			request.setAttribute("mapInfo", mapInfo);
			request.setAttribute("title", mapInfo.get(News.TITLE));
			request.setAttribute("imgUrl", mapInfo.get(News.THUMBNAILURL));
			request.setAttribute("userId", userId);
			request.setAttribute("type", type);
			apiService.getOpenWX(_id, type, request);
		} catch (Exception e) {
			logger.error("系统内部错误，获取数据失败" + e);
			e.printStackTrace();
		}
		return "api/shareDocs";
	}

	/** 获取共享素材集分享页面信息 */
	@RequestMapping(value = "api/share/getMaterialSetInfo/")
	public String getMaterialSetInfo(HttpServletRequest request, @RequestParam(value = "_id") String _id, @RequestParam(value = "type") String type,
			@RequestParam(value = "userId") String userId) {
		logger.info("共享素材集分享页面信息请求进入公开接口，参数为：id=" + _id + ",type=" + type + ",userId=" + userId);
		try {
			Map<String, Object> mapInfo = apiService.getMaterialSetById(_id);
			request.setAttribute("mapInfo", mapInfo);
			request.setAttribute("userId", userId);
			request.setAttribute("type", type);
		} catch (Exception e) {
			logger.error("系统内部错误，获取数据失败" + e);
			e.printStackTrace();
		}
		return "api/shareMaterialSet";
	}

	@RequestMapping(value = "api/sendlog/")
	@ResponseBody
	public String sendlog(HttpServletRequest request, @RequestBody String strJson) {
		logger.info("扫描日志回传接口，参数为：strJson=" + strJson);
		try {
			return apiService.sendlog(strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取数据失败" + e);
			e.printStackTrace();
		}
		return "error";
	}

	@RequestMapping(value = "api/getscanTask/")
	@ResponseBody
	public List<Map<String, Object>> getscanTask(HttpServletRequest request, @RequestBody String strJson) {
		logger.info("扫描日志回传接口，参数为：strJson=" + strJson);
		try {
			return apiService.getscanTask(strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取数据失败" + e);
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "api/scanRegistration/")
	@ResponseBody
	public String scanRegistration(HttpServletRequest request, @RequestBody String strJson) {
		logger.info("扫描日志回传接口，参数为：strJson=" + strJson);
		try {
			Map<String, Object> map = JsonUtil.readJSON2Map(strJson);
			CommonParameters insert = new CommonParameters();
			insert.setAppCode(String.valueOf(map.get(Scan.APPCODE)));
			insert.setCompanyId(String.valueOf(map.get(Scan.COMPANYID)));
			insert.setServiceCode("sm");
			insert.setUserId(String.valueOf(map.get(Scan.TARUSERID)));
			insert.setVersionId("v1");
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(insert.getUserId());
			UserUtil.getUserInfo(insert, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(insert, request);
			ResponseObject ret = materialService.scanRegistration(insert, strJson);
			if (ret.getCode() == 0) {
				return "ok";
			} else {
				return "error";
			}
		} catch (Exception e) {
			logger.error("系统内部错误，获取数据失败" + e);
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * 根据id获取智云的线索
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/getZYCueById/")
	@ResponseBody
	public ResponseObject getZYCueById(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 校验参数
		try {
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return responseObject;
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			responseObject = apiService.getZYCueById(common, strJson);
			return responseObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取智云新闻线索失败" + e.getMessage());
			return responseObject;
		}
	}
	
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/getCDVCueById/")
	@ResponseBody
	public ResponseObject getCDVCueById(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 校验参数
		try {
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return responseObject;
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			responseObject = apiService.getCDVCueById(common, strJson);
			return responseObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取新奥特新闻线索失败" + e.getMessage());
			return responseObject;
		}
	}

	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/getSBCueById/")
	@ResponseBody
	public ResponseObject getSBCueById(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 校验参数
		try {
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return responseObject;
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			responseObject = apiService.getSBCueById(common, strJson);
			return responseObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取新奥特新闻线索失败" + e.getMessage());
			return responseObject;
		}
	}

	// 审核通过入报题库
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/addClubTask")
	@ResponseBody
	public ResponseObject addClubTask(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return responseObject;
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			responseObject = apiService.addClubTask(common, strJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加报题库失败" + e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	
	/**
	 * 智云审核通过入文稿库
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/addDocsFromZY")
	@ResponseBody
	public ResponseObject addDocsFromZY(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return responseObject;
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			responseObject = apiService.addDocsFromZY(common, strJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加报题库失败" + e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	
	@RequestMapping(value = "api/moveCityChannelBack/")
	public void moveCityChannelBack(HttpServletRequest request, @RequestBody String callbackParams){
		logger.info("[推送城市频道公有云向私有云迁移数据的回调返回参数]，callbackParams = " + callbackParams);
	}
	
	/**
	 * 添加直播频道
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/addlive/")
	@ResponseBody
	public ResponseObject addlive(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common,
			HttpServletRequest request){
		ResponseObject responseObject = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		try {
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return responseObject;
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			responseObject = apiService.addlive(common);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加直播通道失败" + e.getMessage());
			return responseObject;
		}
		return responseObject;
		
	}

}
