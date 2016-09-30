package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.ISystemLogDao;
import com.cdvcloud.rms.dao.IUserDao;
import com.cdvcloud.rms.domain.DirectPassing;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.domain.WechatTemplate;
import com.cdvcloud.rms.domain.WechatUser;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.service.IWeChatMessageService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.service.IWeChatTemplateService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JSONUtils;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
@Service
public class WeChatServiceImpl implements IWeChatService{
	private static final Logger logger = Logger.getLogger(WeChatServiceImpl.class);
	public static final String REQACCESSTOKENURL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	public static final String QQREQACCESSTOKENURL = "https://graph.qq.com/oauth2.0/token";
	public static final String REQUSERURL = "https://api.weixin.qq.com/sns/userinfo";
	public static final String QQREQAPPIDURL = "https://graph.qq.com/oauth2.0/me";
	public static final String QQUSERINFOR = "https://graph.qq.com/user/get_user_info";
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ISystemLogDao systemLogDao;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private IWeChatMessageService weChatMessageService;
	@Autowired
	private IWeChatTemplateService weChatTemplateService;

	@Override
	public Map<String, Object> getWeChatUserInfor(String appid, String serect, String code) throws Exception {
		// 通过code获取access_token
		String reqAccessTokenParam = "appid=" + appid + "&secret=" + serect + "&code=" + code + "&grant_type=authorization_code";
		String accessJson = HttpUtil.sendGet(REQACCESSTOKENURL, reqAccessTokenParam);
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(accessJson);
		// 获取用户个人信息
		String reqUserParam = "access_token=" + String.valueOf(mapJson.get("access_token")) + "&openid=" + String.valueOf(mapJson.get("openid"));
		String strJsonUser = HttpUtil.sendGet(REQUSERURL, reqUserParam);
		Map<String, Object> mapJsonUser = JsonUtil.readJSON2Map(strJsonUser);
		String openid = String.valueOf(mapJsonUser.get("openid"));
		String nickname = String.valueOf(mapJsonUser.get("nickname"));
		String sex = String.valueOf(mapJsonUser.get("sex"));
		String headimgurl = String.valueOf(mapJsonUser.get("headimgurl"));
		String unionid = String.valueOf(mapJsonUser.get("unionid"));
		String province = String.valueOf(mapJsonUser.get("province"));
		String city = String.valueOf(mapJsonUser.get("city"));
		Map<String, Object> weChatUserInforMap = new HashMap<String, Object>();
		weChatUserInforMap.put("openid", openid);
		weChatUserInforMap.put("nickname", nickname);
		weChatUserInforMap.put("sex", sex);
		weChatUserInforMap.put("headimgurl", headimgurl);
		weChatUserInforMap.put("unionid", unionid);
		weChatUserInforMap.put("province", province);
		weChatUserInforMap.put("city", city);
		return weChatUserInforMap;
	}

	@Override
	public Map<String, Object> getQQUserInfor(String appid, String serect, String code, String uri) throws Exception {
		// 通过code获取access_token
		String reqAccessTokenParam = "grant_type=authorization_code&client_id=" + appid + "&client_secret=" + serect + "&code=" + code
				+ "&redirect_uri=" + uri;
		String accessJson = HttpUtil.sendGet(QQREQACCESSTOKENURL, reqAccessTokenParam);
		String[] strs = accessJson.split("&");
		String access_token = "";
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].contains("access_token")) {
				String[] stsSub = strs[i].split("=");
				access_token = stsSub[1];
			}

		}
		// 利用获取到的accessToken 去获取当前用的openid -------- start
		OpenID openIDObj = new OpenID(access_token);
		String openID = openIDObj.getUserOpenID();

		UserInfo qzoneUserInfo = new UserInfo(access_token, openID);
		UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
		Map<String, Object> weChatUserInforMap = new HashMap<String, Object>();

		if (userInfoBean.getRet() == 0) {
			String nickname = userInfoBean.getNickname();
			String gender = userInfoBean.getGender();// 性别。 如果获取不到则默认返回"男"
			String figureurl = userInfoBean.getAvatar().getAvatarURL30();// 大小为30×30像素的QQ空间头像URL。
			String figureurl_1 = userInfoBean.getAvatar().getAvatarURL50();// 大小为50×50像素的QQ空间头像URL。
			String figureurl_2 = userInfoBean.getAvatar().getAvatarURL100();// 大小为100×100像素的QQ空间头像URL。

			weChatUserInforMap.put("openid", openID);
			weChatUserInforMap.put("gender", gender);
			weChatUserInforMap.put("nickname", nickname);
			weChatUserInforMap.put("figureurl_qq_0", figureurl);
			weChatUserInforMap.put("figureurl_qq_1", figureurl_1);
			weChatUserInforMap.put("figureurl_2", figureurl_2);

		}
		return weChatUserInforMap;
	}

	/**
	 * 未登录 1、判断当前微信号是否存在(查询用户数据库) 1.1、存在 直接获取当前用户名，保存在session，进行登录跳转 1.2、不存在
	 * 跳转到类似注册的登录页 1.2.1 输入用户名密码正确 用户绑定微信（判断当前头像存在否）保存在session，并且跳转到首页 1.2.2
	 * 输入用户名密码错误 提示输入有误
	 */
	@Override
	public void disLoginDealWeChatOrQQ(Map<String, Object> weChatUserInforMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		Map<String, Object> filter = new HashMap<String, Object>();
		String unionid = String.valueOf(weChatUserInforMap.get("unionid"));
		String type = String.valueOf(weChatUserInforMap.get("type"));
		if ("weChat".equals(type)) {
			filter.put(User.WECHATUNIONID, unionid);
		} else if ("qq".equals(type)) {
			filter.put(User.QQOPENID, unionid);
		}
		Map<String, Object> userMap = userDao.findOne(filter);

		if (userMap != null) {
			UserUtil.saveUserSession(request, userMap);
			// dealLog(userMap, request);
			response.getWriter().write(
					"<script languge='javascript'>window.opener.location.href='" + request.getContextPath() + "/index.jsp';window.close();</script>");
		} else {

			if ("weChat".equals(type)) {
				session.setAttribute("unionid", unionid);
				session.setAttribute("headimgurl", weChatUserInforMap.get("headimgurl"));
				session.setAttribute("nickname", weChatUserInforMap.get("nickname"));
			} else if ("qq".equals(type)) {
				session.setAttribute("qqopenid", unionid);
				session.setAttribute("headimgurl", weChatUserInforMap.get("figureurl_qq_1"));
				session.setAttribute("qqnickname", weChatUserInforMap.get("nickname"));
			}
			session.setAttribute("nickname", weChatUserInforMap.get("nickname"));

			response.getWriter().write(
					"<script languge='javascript'>window.opener.location.href='" + request.getContextPath() + "/redistLogin.jsp?type=" + type
							+ "';window.close();</script>");
			// response.getWriter().write("<script languge='javascript'>window.location.href='"
			// + request.getContextPath() +
			// "/redistLogin.jsp?type="+type+"';</script>");
		}

	}

	/**
	 * //已登录 2、判断当前用户是否绑定过微信(查询数据库) 2.1 绑定过 直接跳转首页 2.2 未绑定 当前用户是否绑定微信 是 -提示请先解绑
	 * 否 -进一步校验 当前微信是否已经绑定其他用户 是 -提示当前已经微信已经绑定其他用户 否 -绑定操作（判断当前头像存在否）跳转首页
	 */
	@Override
	public void LoginDealWeChat(Map<String, Object> weChatUserInforMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		logger.info("用户的Id为" + String.valueOf(session.getAttribute(Constants.USERID)));
	    Map<String, Object> userLoginInfor = userDao.findOne(String.valueOf(session.getAttribute(Constants.USERID)));
		String type = String.valueOf(weChatUserInforMap.get("type"));
		String unionid = "";
		if ("weChat".equals(type)) {
			unionid = String.valueOf(userLoginInfor.get(User.WECHATUNIONID));
			logger.info("微信的openid为" + unionid);
		} else if ("qq".equals(type)) {
			unionid = String.valueOf(userLoginInfor.get(User.QQOPENID));
			logger.info("qq的openid为" + unionid);
		}
		// String
		// weChatOpenid=String.valueOf(userLoginInfor.get(User.WECHATOPENID));

		if (!StringUtil.isEmpty(unionid)) {
			if ("weChat".equals(type)) {
				response.getWriter().write("<script languge='javascript'>window.opener.toastr.error('当前用户已经绑定微信');window.close();</script>");
			} else if ("qq".equals(type)) {
				response.getWriter().write("<script languge='javascript'>window.opener.toastr.error('当前用户已经绑定qq');window.close();</script>");
			}
		} else {
			String Unionid = String.valueOf(weChatUserInforMap.get("unionid"));
			Map<String, Object> weChatFilter = new HashMap<String, Object>();

			if ("weChat".equals(type)) {
				weChatFilter.put(User.WECHATUNIONID, Unionid);
			} else if ("qq".equals(type)) {
				weChatFilter.put(User.QQOPENID, Unionid);
			}
			Map<String, Object> mapUser = userDao.findOne(weChatFilter);
			if (mapUser != null) {
				if ("weChat".equals(type)) {
					if (userLoginInfor.get("_id").equals(mapUser.get("_id"))) {
						response.getWriter().write("<script languge='javascript'>window.opener.toastr.error('此微信已经绑定当前用户');window.close();</script>");
					} else {
						response.getWriter().write("<script languge='javascript'>window.opener.toastr.error('此微信已经绑定其他用户');window.close();</script>");
					}
				} else if ("qq".equals(type)) {
					if (userLoginInfor.get("_id").equals(mapUser.get("_id"))) {
						response.getWriter().write("<script languge='javascript'>window.opener.toastr.error('此qq已经绑定当前用户');window.close();</script>");
					} else {
						response.getWriter().write("<script languge='javascript'>window.opener.toastr.error('此qq已经绑定其他用户');window.close();</script>");
					}
				}

			} else {
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put("_id", new ObjectId(String.valueOf(userLoginInfor.get("_id"))));
				Map<String, Object> update = new HashMap<String, Object>();
				// if(userLoginInfor.get(User.SMALLHEAD)==null){
				update.put(User.SMALLHEAD, weChatUserInforMap.get("headimgurl"));
				// }
				if ("weChat".equals(type)) {
					update.put(User.WECHATUNIONID, Unionid);
					update.put(User.WECHATNICKNAME, weChatUserInforMap.get("nickname"));
				} else if ("qq".equals(type)) {
					update.put(User.QQOPENID, Unionid);
					update.put(User.QQNICKNAME, weChatUserInforMap.get("nickname"));
				}
				userDao.updateUserByset(filter, update);

				if ("weChat".equals(type)) {
					response.getWriter().write(
							"<script languge='javascript'>window.opener.location.href='" + request.getContextPath()
									+ "/index.jsp';window.opener.toastr.success('绑定微信成功');window.close();</script>");
				} else if ("qq".equals(type)) {
					response.getWriter().write(
							"<script languge='javascript'>window.opener.location.href='" + request.getContextPath()
									+ "/index.jsp';window.opener.toastr.success('绑定qq成功');window.close();</script>");
				}
				// response.getWriter().write("<script languge='javascript'>window.location.href='"
				// + request.getContextPath() + "/index.jsp';</script>");
			}
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject unbindWeChat(HttpServletRequest request, Map<String, Object> mapJson) {
		// 当前用户是否绑定 否 直接返回尚未绑定 是更新操作
		ResponseObject resObj = null;
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id", new ObjectId(String.valueOf(mapJson.get("userId"))));
		Map<String, Object> userMap=userDao.findOne(String.valueOf(mapJson.get("userId")));
		Map<String, Object> update = new HashMap<String, Object>();
		update.put(User.WECHATUNIONID, "");
		update.put(User.WECHATOPENID, "");
		update.put(User.WECHATNICKNAME, "");
		update.put(User.SMALLHEAD, "");
		long l=userDao.updateUserByset(filter, update);
		if(l>0){
			String templateId="";
			ResponseObject ro=weChatTemplateService.findWeChatTemplateByType("unbind");
			if(0==ro.getCode()){
				Map<String, Object> weChatTem=(Map<String, Object>) ro.getData();
				templateId=String.valueOf(weChatTem.get(WechatTemplate.TEMPLATEID));
			}
			
			Map<String, Object> mapPara=new HashMap<String, Object>();
			mapPara.put(WechatTemplate.USERID, String.valueOf(mapJson.get("userId")));
			mapPara.put(WechatTemplate.TOUSER, String.valueOf(userMap.get(User.WECHATOPENID)));
			mapPara.put(WechatTemplate.TEMPLATEID, templateId);
			mapPara.put(WechatTemplate.FIRST, "你已成功与 云报道 帐号解除绑定 ");
			mapPara.put(WechatTemplate.REMARK, "如需绑定其他帐号，请点击");
			mapPara.put(WechatTemplate.KEYWORD+"1",userMap.get(User.EMAIL) );
			mapPara.put(WechatTemplate.KEYWORD+"2","该微信已不能用作 云报道 登录" );
			Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
			String json=JsonUtil.map2Json(map);
			logger.info("发送模板消息"+json);
			sendTempMessage(json);
			//HttpUtil.doPost(configurationService.getOuterCallback()+"/api/pub/sendTempMessage/", json);
		}
		resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
		return resObj;
	}

	@Override
	public ResponseObject unbindQQ(HttpServletRequest request, Map<String, Object> mapJson) {
		// 当前用户是否绑定 否 直接返回尚未绑定 是更新操作
		ResponseObject resObj = null;
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id", new ObjectId(String.valueOf(mapJson.get("userId"))));
		Map<String, Object> update = new HashMap<String, Object>();
		update.put(User.QQOPENID, "");
		update.put(User.QQNICKNAME, "");
		update.put(User.SMALLHEAD, "");
		userDao.updateUserByset(filter, update);
		resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
		return resObj;
	}

	@Override
	public ResponseObject bindWeChat(HttpServletRequest request, Map<String, Object> userMap) {
		ResponseObject resObj = null;
		HttpSession session = request.getSession();

		Map<String, Object> errorMap = new HashMap<String, Object>();
		if (null != userMap && !userMap.isEmpty()) {
			// 判断此用户有没有绑定
			if (!StringUtil.isEmpty(String.valueOf(userMap.get(User.WECHATUNIONID)))) {
				errorMap.put("error", "此用户已经被其他微信绑定");
				resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, errorMap);
				return resObj;
			} else {
				Map<String, Object> weChatFilter = new HashMap<String, Object>();
				weChatFilter.put(User.WECHATUNIONID, session.getAttribute("unionid"));
				Map<String, Object> mapUser = userDao.findOne(weChatFilter);
				if (mapUser != null) {
					if (userMap.get("_id").equals(mapUser.get("_id"))) {
						errorMap.put("error", "此微信已经绑定当前用户");
						resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, errorMap);
						return resObj;
					} else {
						errorMap.put("error", "此微信已经绑定其他用户");
						resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, errorMap);
						return resObj;
					}
				} else {
					Map<String, Object> filter = new HashMap<String, Object>();
					filter.put("_id", new ObjectId(String.valueOf(userMap.get("_id"))));
					Map<String, Object> update = new HashMap<String, Object>();
					// if(userMap.get(User.SMALLHEAD)==null){
					update.put(User.SMALLHEAD, session.getAttribute("headimgurl"));
					// }
					update.put(User.WECHATUNIONID, session.getAttribute("unionid"));
					update.put(User.WECHATNICKNAME, session.getAttribute("nickname"));
					userDao.updateUserByset(filter, update);
					UserUtil.saveUserSession(request, userMap);
				}
			}
			// 获取用户信息
			CommonParameters common = new CommonParameters();
			common.setUserId(String.valueOf(userMap.get(User.CUSERID)));
			common.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));

			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息『
			SystemLogUtil.getIp(common, request);
			systemLogDao.insert(SystemLogUtil.getMap(common, "0", "登录", "『" + userMap.get(User.NAME) + "』通过微信接口登录了系统"));
			resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, userMap);
		} else {
			resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		}
		return resObj;
	}

	@Override
	public ResponseObject bindQQ(HttpServletRequest request, Map<String, Object> userMap) {
		ResponseObject resObj = null;
		HttpSession session = request.getSession();

		Map<String, Object> errorMap = new HashMap<String, Object>();
		if (null != userMap && !userMap.isEmpty()) {
			// 判断此用户有没有绑定
			if (!StringUtil.isEmpty(String.valueOf(userMap.get(User.QQOPENID)))) {
				errorMap.put("error", "此用户已经被其qq绑定");
				resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, errorMap);
				return resObj;
			} else {
				Map<String, Object> weChatFilter = new HashMap<String, Object>();
				weChatFilter.put(User.QQOPENID, session.getAttribute("qqopenid"));
				Map<String, Object> mapUser = userDao.findOne(weChatFilter);
				if (mapUser != null) {
					if (userMap.get("_id").equals(mapUser.get("_id"))) {
						errorMap.put("error", "此qq已经绑定当前用户");
						resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, errorMap);
						return resObj;
					} else {
						errorMap.put("error", "此qq已经绑定其他用户");
						resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, errorMap);
						return resObj;
					}
				} else {
					Map<String, Object> filter = new HashMap<String, Object>();
					filter.put("_id", new ObjectId(String.valueOf(userMap.get("_id"))));
					Map<String, Object> update = new HashMap<String, Object>();
					// if(userMap.get(User.SMALLHEAD)==null){
					update.put(User.SMALLHEAD, session.getAttribute("headimgurl"));
					// }
					update.put(User.QQOPENID, session.getAttribute("qqopenid"));
					update.put(User.QQNICKNAME, session.getAttribute("qqnickname"));
					userDao.updateUserByset(filter, update);
					UserUtil.saveUserSession(request, userMap);
				}
			}
			// 获取用户信息
			CommonParameters common = new CommonParameters();
			common.setUserId(String.valueOf(userMap.get(User.CUSERID)));
			common.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));

			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息『
			SystemLogUtil.getIp(common, request);
			systemLogDao.insert(SystemLogUtil.getMap(common, "0", "登录", "『" + userMap.get(User.NAME) + "』通过qq接口登录了系统"));
			resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, userMap);
		} else {
			resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		}
		return resObj;
	}

	@Override
	public Map<String, Object> getUserInfoOtherRount(HttpServletRequest request, Map<String, Object> mapJson) {
		Map<String, Object> filter = new HashMap<String, Object>();
		String unionid = String.valueOf(mapJson.get("unionid"));
		String type = String.valueOf(mapJson.get("type"));
		if ("weChat".equals(type)) {
			filter.put(User.WECHATUNIONID, unionid);
		} else if ("qq".equals(type)) {
			filter.put(User.QQOPENID, unionid);
		}
		Map<String, Object> userMap = userDao.findOne(filter);
		return userMap;
	}

	/**
	 * 公众号扫描进来必须登录的用户（不用考虑没有id的情况）
	 * 绑定用户的微信，根据用户id
	 * 
	 */
	@Override
	public long bindPubWeChatByUserId(Map<String, Object> weChatUserInforMap) {
		long updatelong=0;
		try{
			updatelong=bindUser(weChatUserInforMap);
			logger.info("通过公众号绑定微信用户id"+weChatUserInforMap.get("userytlId")+"更新条数"+updatelong);
			
		}catch(Exception e){
			logger.error("通过公众号绑定微信用户id失败"+e.getMessage());
		}
		return 	updatelong;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getPushBindMessage(Map<String,Object> userMap){
		//微信调用
		String templateId="";
		ResponseObject ro=weChatTemplateService.findWeChatTemplateByType("bind");
		if(0==ro.getCode()){
			Map<String, Object> weChatTem=(Map<String, Object>) ro.getData();
			templateId=String.valueOf(weChatTem.get(WechatTemplate.TEMPLATEID));
		}
		Map<String, Object> mapPara=new HashMap<String, Object>();
		mapPara.put(WechatTemplate.USERID, String.valueOf(userMap.get(User.ID)));
		mapPara.put(WechatTemplate.TEMPLATEID, templateId);
		mapPara.put(WechatTemplate.FIRST, "你已成功绑定 云报道 帐号");
		mapPara.put(WechatTemplate.REMARK, "如需解绑，请访问桌面版进行操作");
		mapPara.put(WechatTemplate.KEYWORD+"1",userMap.get(User.EMAIL) );
		mapPara.put(WechatTemplate.KEYWORD+"2","你可以直接使用该微信登录 云报道「桌面版」和「移动版」" );
		Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
		String json=JsonUtil.map2Json(map);
		logger.info("绑定的json数据为"+json);
		return json;
	}
	
	private  long  bindUser (Map<String, Object> weChatUserInforMap){
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id", new ObjectId(String.valueOf(weChatUserInforMap.get("userytlId"))));
		Map<String, Object> update = new HashMap<String, Object>();
		update.put(User.SMALLHEAD, weChatUserInforMap.get(WechatUser.HEADIMGURL));
		update.put(User.WECHATUNIONID, weChatUserInforMap.get(WechatUser.UNIONID));
		update.put(User.WECHATOPENID, weChatUserInforMap.get(WechatUser.OPENID));
		update.put(User.WECHATNICKNAME, weChatUserInforMap.get(WechatUser.NICKNAME));
		if(!StringUtil.isEmpty(String.valueOf(weChatUserInforMap.get(User.PHONE)))){
			update.put(User.PHONE, weChatUserInforMap.get(User.PHONE));
		}
		return userDao.updateUserByset(filter, update);
	}
	
	



	/**
	 * 推送模板消息
	 */
	@Override
	public ResponseObject sendTempMessage(String callbackParams) {
		ResponseObject resObj=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try {
			logger.info("调用云通联向微信发送消息接口.........接收到模板message"+callbackParams);
			Map<String,Object> weChatMap=JsonUtil.readJSON2Map(callbackParams);
			//touser 为userid
			String openId="";
			Map<String,Object> userMap=new HashMap<String, Object>();
			if(!StringUtil.isEmpty(String.valueOf(weChatMap.get(WechatTemplate.TOUSER)))){
				openId=String.valueOf(weChatMap.get(WechatTemplate.TOUSER));
				if(!StringUtil.isEmpty(String.valueOf(weChatMap.get(WechatTemplate.USERID)))){
					userMap=userService.getUserInforById(String.valueOf(weChatMap.get(WechatTemplate.USERID)));
				}
			}else{
				userMap=userService.getUserInforById(String.valueOf(weChatMap.get(WechatTemplate.USERID)));
				if(userMap!=null){
					openId=String.valueOf(userMap.get(User.WECHATOPENID));
				}
			}
			if(StringUtil.isEmpty(openId)){
				return resObj;
			}
			logger.info("openId:"+openId);
			weChatMap.put("touser", openId);
			String weChatJson= JsonUtil.writeMap2JSON(weChatMap);
			String wxUrl=configurationService.getWxclient();
			logger.info("获取向微信客户端发送消息的url：wxUrl:"+wxUrl+"/ytlmain/sendTemlateMessage/ytl/"+"参数："+weChatJson);
			String result=HttpUtil.doPost(wxUrl+"/ytlmain/sendTemlateMessage/ytl/", weChatJson);
			String weChatMessageId=String.valueOf(weChatMap.get("weChatMessageId"));
			String url=wxUrl+"/ytlmain/sendTemlateMessage/ytl/";
			resObj=weChatMessageService.updateOrInsertWeChatMessage(result, userMap, url, weChatJson, weChatMessageId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送微信模板接口error"+e.getMessage());
			return resObj;
		}
		return resObj;
	}

	@Override
	public String getWeChatQcode(String callback) {
		String str="";
		try {
			String wxUrl=configurationService.getWxclient();
			String url=wxUrl+"/ytlmain/getQRCode/ytl/";
			Map<String, Object>paramMap=new HashMap<String, Object>();
			paramMap.put("action_name", "QR_LIMIT_STR_SCENE");
			Map<String, Object> actioninfoMap=new HashMap<String, Object>();
			Map<String, Object> sceneidMap=new HashMap<String, Object>();
			//回调接口
			Map<String,Object> jsonMap=JsonUtil.readJSON2Map(callback);
			logger.info("扫描二维码 userId:"+jsonMap.get("userId"));
			sceneidMap.put("scene_str", configurationService.getOuterCallback()+"_"+jsonMap.get("userId"));
			actioninfoMap.put("scene", sceneidMap);
			paramMap.put("action_info", actioninfoMap);
			String paramJson = JsonUtil.writeMap2JSON(paramMap);
			//调用接口
			String backJson=HttpUtil.doPost(url, paramJson);
			Map<String,Object> backMap=JsonUtil.readJSON2Map(backJson);

			if(!StringUtil.isEmpty(String.valueOf(backMap.get("status")))&&backMap.get("status").equals(0)){
				Map<String, Object> mapRequest = JsonUtil.readJSON2Map(backJson);
				if(!StringUtil.isEmpty(mapRequest)){
					@SuppressWarnings("unchecked")
					Map<String,Object> dataMap=(Map<String, Object>) mapRequest.get("data");
					String	ticket=String.valueOf(dataMap.get("ticket"));
					if(!StringUtil.isEmpty(ticket)){
						str="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" +ticket;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送微信模板接口error"+e.getMessage());
			return "";
		}
		return str;
	}
	
	@Override
	public String mobileWx(String callbackParams){
		Map<String, Object> mapPara=JsonUtil.readJSON2Map(callbackParams);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(WechatTemplate.USERID, String.valueOf(mapPara.get(WechatTemplate.USERID)));
		map.put(WechatTemplate.TEMPLATEID, String.valueOf(mapPara.get(WechatTemplate.TEMPLATEID)));
		map.put(WechatTemplate.FIRST, String.valueOf(mapPara.get(WechatTemplate.FIRST)));
		map.put(WechatTemplate.REMARK, String.valueOf(mapPara.get(WechatTemplate.REMARK)));
		map.put(WechatTemplate.KEYWORD+"1",String.valueOf(mapPara.get("keyword1")) );
		map.put(WechatTemplate.KEYWORD+"2",String.valueOf(mapPara.get("keyword2")));
		Map<String,Object>map2=WechatTemplate.weChatTemplate(map);
		return JsonUtil.map2Json(map2);
	}

	/**
	 *校验当前微信账号是否绑定
	 */
	@Override
	public ResponseObject checkWeChatBind( String strJson) {
		ResponseObject responseObject=new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		logger.info("当前用户是否绑定接口参数"+strJson);
		try{
			Map<String,Object>jsonMap=JsonUtil.readJSON2Map(strJson);
			String unionId=String.valueOf(jsonMap.get(WechatUser.UNIONID));
			if(StringUtil.isEmpty(unionId)){
				logger.error("校验用户是否绑定接口参数缺少字段：weChatUnionid");
				return responseObject;
			}
			Map<String, Object> userMap=getUserByUnion(unionId);
			if(null!=userMap){
				userMap.put("loginId", userMap.get(User.EMAIL));
				return new ResponseObject(GeneralStatus.wechat_bind.status, GeneralStatus.wechat_bind.enDetail, userMap);
			}else{
				return new ResponseObject(GeneralStatus.wechat_unbind.status, GeneralStatus.wechat_unbind.enDetail, "");
			}
		}catch(Exception e){
			logger.error("校验微信是否绑定接口出错"+e.getMessage());
			return responseObject;
		}
	}
	
	@Override
	public Map<String,Object> getUserByUnion(String unionId){
		Map<String, Object> filter=new HashMap<String, Object>();
		filter.put(User.WECHATUNIONID, unionId);
		Map<String, Object> userMap=userDao.findOne(filter);
		return userMap;
	}

	@Override
	public ResponseObject bindUserWechatNotLogin( String strJson) {
		ResponseObject responseObject=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try{
			Map<String,Object>jsonMap=JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			if (StringUtil.isEmpty(jsonMap.get("userName")) || StringUtil.isEmpty(jsonMap.get("password"))) {
				logger.warn("缺少输入参数字段userName或者password");
				return responseObject;
			}
			Map<String, Object> userMap = userService.getUserInfoByName(jsonMap);
			//绑定微信号及其个人信息操作
			String userName = String.valueOf(jsonMap.get("userName"));
			String password = String.valueOf(jsonMap.get("password"));
			Map<String, Object> mapUserRes = new HashMap<String, Object>();
			boolean resFlag = userService.validateUserInfo(userName, password, mapUserRes);
			if (!resFlag) {
				logger.error("统一验证用户失败："+responseObject.getData());
				return responseObject;
			}
			if(userMap==null){
				logger.error("云报道验证用户失败userName:"+jsonMap.get("userName"));
				//用户名密码错误
				logger.warn("用户名错误");
				return responseObject;
			}else{
				if(!StringUtil.isEmpty(String.valueOf(userMap.get(User.WECHATUNIONID)))){
					//当前用户已经绑定
					return new ResponseObject(GeneralStatus.user_bind.status, GeneralStatus.user_bind.detail, userMap);
				}else{
					//绑定操作
					jsonMap.put("userytlId", userMap.get(User.ID));
					long l=bindUser(jsonMap);
					jsonMap=userDao.findOne(String.valueOf(userMap.get(User.ID)));
					if (!mapUserRes.isEmpty() && mapUserRes.containsKey("userId")) {
						jsonMap.put("loginId", mapUserRes.get("loginId"));
						jsonMap.put("userId", mapUserRes.get("userId"));
					}
					if(l>0){
						CommonParameters commonParameters=new CommonParameters();
						commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
						commonParameters.setAppCode(Constants.APPCODEVALUE);
						commonParameters.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
						commonParameters.setUserId(String.valueOf(userMap.get(User.ID)));
						logger.info("返回绑定数据："+jsonMap.toString());
						LogUtil.printIntegralLog(commonParameters, "wxbd", "通过注册用户绑定微信,绑定的微信unionid"+userMap.get(User.WECHATUNIONID));
						sendTempMessage(getPushBindMessage(jsonMap));
						return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, jsonMap);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("绑定未登录用户接口出错"+e.getMessage());
			return responseObject;
		}
		return responseObject;
	}
	@Override
	public ResponseObject bindUserWechatNotLoginNotCas( String strJson) {
		ResponseObject responseObject=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try{
			Map<String,Object>jsonMap=JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			if (StringUtil.isEmpty(jsonMap.get("userName")) || StringUtil.isEmpty(jsonMap.get("password"))) {
				logger.warn("缺少输入参数字段userName或者password");
				return responseObject;
			}
			//绑定微信号及其个人信息操作
			Map<String, Object> userMap = userService.getUserInfo(jsonMap);
			if(userMap==null){
				logger.error("云报道验证用户失败userName:"+jsonMap.get("userName")+"password:"+jsonMap.get("password"));
				logger.warn("用户名密码错误");
				return responseObject;
			}else{
				if(!StringUtil.isEmpty(String.valueOf(userMap.get(User.WECHATUNIONID)))){
					//当前用户已经绑定
					return new ResponseObject(GeneralStatus.user_bind.status, GeneralStatus.user_bind.detail, userMap);
				}else{
					//绑定操作
					jsonMap.put("userytlId", userMap.get(User.ID));
					long l=bindUser(jsonMap);
					jsonMap=userDao.findOne(String.valueOf(userMap.get(User.ID)));
					if(l>0){
						CommonParameters commonParameters=new CommonParameters();
						commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
						commonParameters.setAppCode(Constants.APPCODEVALUE);
						commonParameters.setCompanyId(String.valueOf(userMap.get(User.CONSUMERID)));
						commonParameters.setUserId(String.valueOf(userMap.get(User.ID)));
						logger.info("返回绑定数据："+jsonMap.toString());
						LogUtil.printIntegralLog(commonParameters, "wxbd", "通过注册用户绑定微信,绑定的微信unionid"+userMap.get(User.WECHATUNIONID));
						sendTempMessage(getPushBindMessage(jsonMap));
						return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, jsonMap);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("绑定未登录用户接口出错"+e.getMessage());
			return responseObject;
		}
		return responseObject;
	}

	@Override
	public ResponseObject bindUserWechatLogin(CommonParameters commonParameters, String strJson) {
		ResponseObject responseObject=new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try{
			Map<String,Object>jsonMap=JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			if (StringUtil.isEmpty(String.valueOf(jsonMap.get(WechatUser.UNIONID)))) {
				logger.warn("缺少输入参数unionid");
				return responseObject;
			}
			//此微信号是否绑定
			ResponseObject checkResponse=checkWeChatBind( strJson);
			if(GeneralStatus.wechat_unbind.status==checkResponse.getCode()){
				jsonMap.put("userytlId", commonParameters.getUserId());
				long l=bindUser(jsonMap);
				if(l>0){
					Map<String,Object> userMap=userDao.findOne(String.valueOf(commonParameters.getUserId()));
					userMap.put("loginId", userMap.get(User.EMAIL));
					LogUtil.printIntegralLog(commonParameters, "wxbd", "通过登录绑定微信,绑定的微信unionid"+userMap.get(User.WECHATUNIONID));
					sendTempMessage(getPushBindMessage(jsonMap));
					return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, userMap);
				}
			}else{
			//已经绑定
				return new ResponseObject(GeneralStatus.wechat_bind.status, GeneralStatus.wechat_bind.enDetail, "");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("绑定已登录用户接口出错"+e.getMessage());
			return responseObject;
		}
		return responseObject;
	}

	@Override
	public String getOpenAuthPubWeChat() {
		return  "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	}
	
	/**
	 * 获取模板id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getTemplates(){
		String wxUrl=configurationService.getWxclient();
		logger.info("获取模板id的url：wxUrl:"+wxUrl+"/ytlmain/getTemplates/ytl/");
		String result=HttpUtil.doPost(wxUrl+"/ytlmain/getTemplates/ytl/", "1");
		List<Map<String,Object>> tmps=null;
		Map<String,Object> retMap=new HashMap<String, Object>();
		try {
			Map<String,Object>resultMap=JSONUtils.json2map(result);
			if("0".equals(String.valueOf(resultMap.get("status")))){
				Map<String,Object> data=(Map<String, Object>) resultMap.get("data");
				 tmps=(List<Map<String, Object>>) data.get("template_list");
				 for (Map<String, Object> temp : tmps) {
					 retMap.put(String.valueOf(temp.get(WechatTemplate.TEMPLATEID)), temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
		
	}
	
	
	/**
	 * 上传消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void  pushUploadWx(Map<String, Object> mediaMap){
		String templateId="";
		ResponseObject ro=weChatTemplateService.findWeChatTemplateByType("task");
		if(0==ro.getCode()){
			Map<String, Object> weChatTem=(Map<String, Object>) ro.getData();
			templateId=String.valueOf(weChatTem.get(WechatTemplate.TEMPLATEID));
		}
		Map<String, Object> mapPara=new HashMap<String, Object>();
		mapPara.put(WechatTemplate.USERID, String.valueOf(mediaMap.get(Media.CUSERID)));
		mapPara.put(WechatTemplate.TEMPLATEID, templateId);
		mapPara.put(WechatTemplate.FIRST, "您好，您上传任务未成功，请重新上传");
		mapPara.put(WechatTemplate.REMARK, "请到素材列表查看详情");
		mapPara.put(WechatTemplate.KEYWORD+"1",String.valueOf(mediaMap.get(Media.NAME)) );
		mapPara.put(WechatTemplate.KEYWORD+"2",String.valueOf(mediaMap.get(Media.UUTIME)) );
		Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
		String json=JsonUtil.map2Json(map);
		logger.info("发送模板消息"+json);
		sendTempMessage(json);
		
	}
	

	/**
	 * 直传消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void pushDirectWx(Map<String, Object> directPassingMap,String status){
		String templateId="";
		ResponseObject ro=weChatTemplateService.findWeChatTemplateByType("task");
		if(0==ro.getCode()){
			Map<String, Object> weChatTem=(Map<String, Object>) ro.getData();
			templateId=String.valueOf(weChatTem.get(WechatTemplate.TEMPLATEID));
		}
		//微信调用
		Map<String, Object> mapPara=new HashMap<String, Object>();
		mapPara.put(WechatTemplate.USERID, String.valueOf(directPassingMap.get(DirectPassing.CUSERID)));
		mapPara.put(WechatTemplate.TEMPLATEID, templateId);
		if("成功".equals(status)){
			mapPara.put(WechatTemplate.FIRST, "您好，您的直传任务已成功");
		}else{
			mapPara.put(WechatTemplate.FIRST, "您好，您的直传任务未成功");
		}
		mapPara.put(WechatTemplate.REMARK, "请到直传记录查看详情");
		mapPara.put(WechatTemplate.KEYWORD+"1",String.valueOf(directPassingMap.get(DirectPassing.NAME)) );
		mapPara.put(WechatTemplate.KEYWORD+"2",DateUtil.getCurrentDateTime() );
		Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
		String json=JsonUtil.map2Json(map);
		logger.info("发送模板消息"+json);
		//HttpUtil.doPost(configurationService.getOuterCallback()+"/api/pub/sendTempMessage/", json);
		sendTempMessage(json);
	}
	/**
	 * 审核消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void  pushCheckWx(Map<String, Object> newsMap){
		String templateId="";
		ResponseObject ro=weChatTemplateService.findWeChatTemplateByType("check");
		if(0==ro.getCode()){
			Map<String, Object> weChatTem=(Map<String, Object>) ro.getData();
			templateId=String.valueOf(weChatTem.get(WechatTemplate.TEMPLATEID));
		}
		//微信调用
		String status="";
		logger.info("News.STATUS:"+newsMap.get(News.STATUS));
		if("5".equals(String.valueOf(newsMap.get(News.STATUS)))||"8".equals(String.valueOf(newsMap.get(News.STATUS)))){
			status="审核已通过";
		}else if("6".equals(String.valueOf(newsMap.get(News.STATUS)))){
			status="审核被打回";
		}
		Map<String, Object> mapPara=new HashMap<String, Object>();
		mapPara.put(WechatTemplate.USERID, String.valueOf(newsMap.get(News.CUSERID)));
		mapPara.put(WechatTemplate.TEMPLATEID, templateId);
		mapPara.put(WechatTemplate.FIRST, "您提交的审核任务已完成");
		mapPara.put(WechatTemplate.REMARK, "请到我的通联列表查看详情");
		mapPara.put(WechatTemplate.KEYWORD+"1",String.valueOf(newsMap.get(News.TITLE)) );
		mapPara.put(WechatTemplate.KEYWORD+"2",status );
		Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
		String json=JsonUtil.map2Json(map);
		logger.info("发送模板消息"+json);
		sendTempMessage(json);
	}
	/**
	 * 推送台内消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void  pushWx(Map<String, Object> newsMap, String state, String type){
		String templateId="";
		ResponseObject ro=weChatTemplateService.findWeChatTemplateByType("task");
		if(0==ro.getCode()){
			Map<String, Object> weChatTem=(Map<String, Object>) ro.getData();
			templateId=String.valueOf(weChatTem.get(WechatTemplate.TEMPLATEID));
		}
		//微信调用
		Map<String, Object> mapPara=new HashMap<String, Object>();
		mapPara.put(WechatTemplate.USERID, String.valueOf(newsMap.get(News.CUSERID)));
		mapPara.put(WechatTemplate.TEMPLATEID, templateId);
		if("成功".equals(state)){
			mapPara.put(WechatTemplate.FIRST, "您好，您的文稿已推送至台内系统");
		}else{
			mapPara.put(WechatTemplate.FIRST, "您好，您的文稿未推送至台内系统");
		}
		mapPara.put(WechatTemplate.REMARK, "");
		mapPara.put(WechatTemplate.KEYWORD+"1",String.valueOf(newsMap.get(News.TITLE)) );
		mapPara.put(WechatTemplate.KEYWORD+"2",DateUtil.getCurrentDateTime());
		Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
		String json=JsonUtil.map2Json(map);
		logger.info("发送模板消息"+json);
		sendTempMessage(json);
		
	}

}
