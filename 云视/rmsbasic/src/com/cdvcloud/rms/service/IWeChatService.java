package com.cdvcloud.rms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IWeChatService {
	/**
	 * 根据微信扫一扫code值返回当前用户的信息
	 * 
	 * @param code
	 * @return
	 */
	public Map<String, Object> getWeChatUserInfor(String appid, String serect, String code) throws Exception;

	public Map<String, Object> getQQUserInfor(String appid, String serect, String code, String uri) throws Exception;

	public void disLoginDealWeChatOrQQ(Map<String, Object> weChatUserInforMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	public void LoginDealWeChat(Map<String, Object> weChatUserInforMap, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ResponseObject unbindWeChat(HttpServletRequest request, Map<String, Object> mapJson);

	public ResponseObject unbindQQ(HttpServletRequest request, Map<String, Object> mapJson);

	public ResponseObject bindWeChat(HttpServletRequest request, Map<String, Object> userMap);

	public ResponseObject bindQQ(HttpServletRequest request, Map<String, Object> userMap);

	public Map<String, Object> getUserInfoOtherRount(HttpServletRequest request, Map<String, Object> mapJson);

	public long bindPubWeChatByUserId(Map<String, Object> weChatUserInforMap);

	public ResponseObject sendTempMessage(String json);

	public String getWeChatQcode(String callback);

	public String getPushBindMessage(Map<String, Object> userMap);

	public void pushWx(Map<String, Object> newsMap, String state, String type);
	public String mobileWx(String callbackParams);
	
	public ResponseObject checkWeChatBind(String strJson);

	public ResponseObject bindUserWechatNotLogin( String strJson);
	public ResponseObject bindUserWechatNotLoginNotCas( String strJson);

	public ResponseObject bindUserWechatLogin(CommonParameters commonParameters, String strJson);
	
	public String getOpenAuthPubWeChat();
	public void  pushUploadWx(Map<String, Object> mediaMap);
	public void  pushCheckWx(Map<String, Object> newsMap);
	public void pushDirectWx(Map<String, Object> directPassingMap,String status);
	public Map<String,Object> getTemplates();
	public Map<String,Object> getUserByUnion(String unionId);
}
