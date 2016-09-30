package com.cdvcloud.rms.web.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.SuperDeal;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.common.ValidateJson;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;
@Controller
public class WeChatController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private ValidateJson validateJson;
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private IWeChatService weChatService;
	
	
/** ---------------------------------------------以下是第三方登录调用--------------------------------------------------------*/
	
	/** 其他通道获取用户信息 */
	@RequestMapping(value = "api/user/getUserInfoOtherRount/")
	@ResponseBody
	public ResponseObject getUserInfoOtherRount(HttpServletRequest request, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			String type=String.valueOf(mapJson.get("type"));
			String unionid=String.valueOf(mapJson.get("unionid"));
			if (StringUtil.isEmpty(type)||StringUtil.isEmpty(unionid)) {
				logger.warn("");
				return null;
			}
			
			Map<String, Object> userMap = weChatService.getUserInfoOtherRount(request, mapJson);
			if(null !=userMap && !userMap.isEmpty()){
				//获取用户信息
				CommonParameters common = new CommonParameters();
				common.setUserId(String.valueOf(userMap.get(User.CUSERID)));
				common.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
				UserUtil.getUserInfo(common, userMap);
				//获取日志ip信息『
				SystemLogUtil.getIp(common, request);
				systemLogService.inset(SystemLogUtil.getMap(common, "0", "登录", "『"+userMap.get(User.NAME)+"』通过接口登录了系统"));
				resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, userMap);
			}else{
				resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
			}
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	/**手机端调用获取微信用户信息**/
	@RequestMapping(value="api/auth/getUserInfoByWeChat")
	@ResponseBody
	public ResponseObject getUserInfoByWeChat(@RequestBody String strJson){
		ResponseObject resObj = null;
		try{
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			String code=String.valueOf(mapJson.get("code"));
			Map<String ,Object> weChatUserInforMap=new HashMap<String ,Object>();
			if(!StringUtil.isEmpty(code)){
				weChatUserInforMap=weChatService.getWeChatUserInfor(configurationService.getWeChatAppid(), configurationService.getWeChatSecret(), code);
			}
			resObj=new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, weChatUserInforMap);
		}catch(Exception e){
			e.printStackTrace();
			resObj=new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 微信第三方登录 */
	@RequestMapping(value = "api/auth/login/")
	public void oauthLogin(HttpServletRequest request,HttpServletResponse response){
	
		//微信服务操作
		String code=request.getParameter("code");
		try{
			Map<String ,Object> weChatUserInforMap=new HashMap<String ,Object>();
			if(!StringUtil.isEmpty(code)){
				weChatUserInforMap=weChatService.getWeChatUserInfor(configurationService.getWeChatAppid(), configurationService.getWeChatSecret(), code);
			}
			String unionid=String.valueOf(weChatUserInforMap.get("unionid"));
			//获取openid为null
			if(StringUtil.isEmpty(unionid)){
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("<script languge='javascript'>window.location.href='" +redistWeChatOpen(request,response)+"';</script>");
			}
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Object obj = session.getAttribute(Constants.USERNAME);
			weChatUserInforMap.put("type", "weChat");
			if(obj==null){
				weChatService.disLoginDealWeChatOrQQ(weChatUserInforMap, request, response);
			}else{
				weChatService.LoginDealWeChat(weChatUserInforMap, request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("<script languge='javascript'>window.location.href='" +redistWeChatOpen(request,response)+"';</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/** qq第三方登录 */
	@RequestMapping(value = "api/auth/qqlogin/")
	public void qqlogin(HttpServletRequest request,HttpServletResponse response){
	
		//qq服务操作
		String code=request.getParameter("code");
		try{
			Map<String ,Object>qqUserInforMap=new HashMap<String ,Object>();
			if(!StringUtil.isEmpty(code)){
				qqUserInforMap=weChatService.getQQUserInfor(configurationService.getQQclientId(), configurationService.getQQclientSecret(), code,configurationService.getQQRedirectUri());
				//qqUserInforMap=loginService.getMap(request,response);
			}
			String openid=String.valueOf(qqUserInforMap.get("openid"));
			//获取openid为null
			if(StringUtil.isEmpty(openid)){
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("<script languge='javascript'>window.location.href='" +redistQQOpen(request,response)+"';</script>");
			}
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Object obj = session.getAttribute(Constants.USERNAME);
			qqUserInforMap.put("type", "qq");
			if(obj==null){
				weChatService.disLoginDealWeChatOrQQ(qqUserInforMap, request, response);
			}else{
				weChatService.LoginDealWeChat(qqUserInforMap, request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("<script languge='javascript'>window.location.href='" +redistWeChatOpen(request,response)+"';</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/**	跳转到微信扫一扫的url*/
	@RequestMapping(value="api/auth/redistWeChatOpen/")
	@ResponseBody
	public String redistWeChatOpen(HttpServletRequest request,HttpServletResponse response){
		String url="https://open.weixin.qq.com/connect/qrconnect?appid="+configurationService.getWeChatAppid()+"" +
				"&redirect_uri="+configurationService.getRedirectUri()+"" +
				"&response_type=code" +
				"&scope=snsapi_login&state=STATE#wechat_redirect";
		return url;
	}
	
	/**	跳转到QQ的url*/
	@RequestMapping(value="api/auth/redistQQOpen/")
	@ResponseBody
	public String redistQQOpen(HttpServletRequest request,HttpServletResponse response){
		String url="https://graph.qq.com/oauth2.0/authorize?client_id="+configurationService.getQQclientId()+"" +
				"&redirect_uri="+configurationService.getQQRedirectUri()+"" +
				"&response_type=code" +
				"&scope="+configurationService.getQQScope()+"&state=test";
		return url;
	}
	
	
	/**	用户解绑微信用户*/
	@RequestMapping(value="api/auth/unbindWeChat/")
	@ResponseBody
	public ResponseObject unbindWeChat(HttpServletRequest request,@RequestBody String strJson){
		ResponseObject resObj = null;
		try{
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			resObj=weChatService.unbindWeChat(request, mapJson);
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	/**	用户解绑微信用户*/
	@RequestMapping(value="api/auth/unbindqq/")
	@ResponseBody
	public ResponseObject unbindqq(HttpServletRequest request,@RequestBody String strJson){
		ResponseObject resObj = null;
		try{
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			resObj=weChatService.unbindQQ(request, mapJson);
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	/**	用户绑定微信用户*/
	@RequestMapping(value="api/auth/bindWeChat/")
	@ResponseBody
	public ResponseObject bindWeChat(HttpServletRequest request, @RequestBody String strJson){
		ResponseObject resObj = null;
		try {
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			if (StringUtil.isEmpty(mapJson.get("userName")) || StringUtil.isEmpty(mapJson.get("password"))) {
				logger.warn("");
				return null;
			}
			Map<String, Object> userMap = userService.getUserInfo(mapJson);
			resObj=weChatService.bindWeChat(request, userMap);
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/**	用户绑定qq用户*/
	@RequestMapping(value="api/auth/bindQQ/")
	@ResponseBody
	public ResponseObject bindQQ(HttpServletRequest request, @RequestBody String strJson){
		ResponseObject resObj = null;
		try {
			// 校验json
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				logger.warn("传入的json校验不合法！" + strJson);
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			if (StringUtil.isEmpty(mapJson.get("userName")) || StringUtil.isEmpty(mapJson.get("password"))) {
				logger.warn("");
				return null;
			}
			Map<String, Object> userMap = userService.getUserInfo(mapJson);
			resObj=weChatService.bindQQ(request, userMap);
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	@RequestMapping(value = "api/user/isBIndSucess/")
	@ResponseBody
	public ResponseObject isBIndSucess(HttpServletRequest request, @RequestBody String strJson){
		ResponseObject resObj = null;
		try{
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			Map<String,Object> userMap=userService.getUserInforById(String.valueOf(mapJson.get("userId")));
			String openId=	String.valueOf(userMap.get(User.WECHATOPENID));
			if(StringUtil.isEmpty(openId)){
				resObj=new ResponseObject(GeneralStatus.processing.status, GeneralStatus.processing.enDetail, "");
			}else{
				resObj=new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,"");
			}
			
		}catch(Exception e){
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	@RequestMapping(value="api/wechat/checkWeChatBind/")
	@ResponseBody
	public ResponseObject checkWeChatBind(@RequestBody String strJson,HttpServletRequest request){
		ResponseObject responseObject=new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			responseObject=weChatService.checkWeChatBind(strJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("校验微信是否绑定接口出错"+e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	
	@RequestMapping(value="api/wechat/bindUserWechatNotLogin/")
	@ResponseBody
	public ResponseObject bindUserWechatNotLogin( @RequestBody String strJson,
			HttpServletRequest request){
			ResponseObject responseObject=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
			try{
				boolean validJson = validateJson.validate(strJson);
				if (!validJson) {
					return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
				}
				responseObject=weChatService.bindUserWechatNotLogin( strJson);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("绑定未登录用户接口出错"+e.getMessage());
				return responseObject;
			}
			return responseObject;
	}
	@RequestMapping(value="api/wechat/bindUserWechatNotLoginNotCas/")
	@ResponseBody
	public ResponseObject bindUserWechatNotLoginNotCas( @RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject responseObject=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try{
			boolean validJson = validateJson.validate(strJson);
			if (!validJson) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			responseObject=weChatService.bindUserWechatNotLoginNotCas( strJson);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("绑定未登录用户接口出错"+e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	@RequestMapping(value="{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/wechat/bindUserWechatLogin/")
	@ResponseBody
	public ResponseObject bindUserWechatLogin(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject responseObject=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try{
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			responseObject=weChatService.bindUserWechatLogin(commonParameters, strJson);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("绑定已登录用户接口出错"+e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	

	
	/***------------------------------------------------公众号功能------------------------------------------------**/
	/**
	 * 公众号调用的绑定回调接口
	 * @param request
	 * @param callbackParams
	 */
	@RequestMapping(value="api/pub/bindWeChatByPub/" )
	@ResponseBody
	public void bindWeChatByPub(HttpServletRequest request,HttpServletResponse response, @RequestBody String callbackParams){
		try{
			logger.info("接受到公众号回调的绑定接口"+callbackParams);
			Map<String,Object> weChatMap=JsonUtil.readJSON2Map(callbackParams);
			//当前微信是否绑定
			Map<String,Object> userMap=weChatService.getUserByUnion(String.valueOf(weChatMap.get("unionid")));
			if(null!=userMap){
				logger.info("绑定的用户名为："+userMap.get(User.EMAIL));
				response.getWriter().print("此微信已经绑定当前用户");
				return ;
			}
			long updatalong=weChatService.bindPubWeChatByUserId(weChatMap);
			Map<String,Object> user=userService.getUserInforById(String.valueOf(weChatMap.get("userytlId")));
			if(updatalong>0){
				String json=weChatService.getPushBindMessage(user);
				weChatService.sendTempMessage(json);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("绑定接口error"+e.getMessage());
		}
		
	}
	
	
	
	
	/**
	 * 其他应用调用云通联发送微信模板接口
	 * @param request
	 * @param callbackParams
	 */
	@RequestMapping(value="{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/pub/sendTempMessage/" )
	@ResponseBody
	public ResponseObject sendTempMessage(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request, @RequestBody String callbackParams){
		ResponseObject responseObject=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try {
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, callbackParams);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			callbackParams=weChatService.mobileWx(callbackParams);
			responseObject= weChatService.sendTempMessage(callbackParams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送微信模板接口error"+e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	
	
	/**
	 * 公众号生成带参数二维码 
	 * 扫描公众号 已经登录
	 * scene_str 为传参 包括（指定回调地址；当前登录用户id）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="api/pub/selfEvent/")
	@ResponseBody
	public String  selfEvent(HttpServletRequest request,HttpServletResponse response,@RequestBody String callback){
		String str="";
		try {
			str=weChatService.getWeChatQcode(callback);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送微信模板接口error"+e.getMessage());
		}
		return str;
	}
	

}
