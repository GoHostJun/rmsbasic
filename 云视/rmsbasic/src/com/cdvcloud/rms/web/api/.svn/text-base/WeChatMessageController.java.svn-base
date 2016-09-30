package com.cdvcloud.rms.web.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.SuperDeal;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.IWeChatMessageService;

@Controller
@RequestMapping(value="api/wechatmessage")
public class WeChatMessageController {
	private static final Logger logger = Logger.getLogger(WeChatMessageController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IWeChatMessageService weChatMessageService;
	
	/** 获取所有微信消息 */
	@RequestMapping(value = "/getAllWeChatMessage")
	@ResponseBody
	public ResponseObject getAllWeChatMessage( @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[获取微信message接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatMessageService.getAllWeChatMessage(strJson);
		logger.info("[获取微信message接口]，返回值：" + responseObject);
		return responseObject;
	}
	
	
	/** 获取所有微信消息 */
	@RequestMapping(value = "/retryPushWx")
	@ResponseBody
	public ResponseObject retryPushWx(@RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[重发微信message接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatMessageService.retryPushWx(strJson);
		logger.info("[重发微信message接口]，返回值：" + responseObject);
		return responseObject;

		
	}
}
