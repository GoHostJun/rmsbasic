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
import com.cdvcloud.rms.service.IWeChatTemplateService;
import com.cdvcloud.rms.service.impl.WeChatMessageServiceImpl;

@Controller
@RequestMapping(value="api/wechattemplate")
public class WeChatTemplateController {
	private static final Logger logger = Logger.getLogger(WeChatMessageServiceImpl.class);
	@Autowired
	private IWeChatTemplateService weChatTemplateService;
	@Autowired
	private ValidateCommonParam validateCommonParam;
	/** 获取所有微信消息 */
	@RequestMapping(value = "/addWeChatTemplate/")
	@ResponseBody
	public ResponseObject addWeChatTemplate(@RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[添加微信template接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatTemplateService.addWeChatTemplate(strJson);
		logger.info("[添加微信template接口]，返回值：" + responseObject);
		return responseObject;
		
	};
	@RequestMapping(value = "/modefyWeChatTemplate/")
	@ResponseBody
	public ResponseObject modefyWeChatTemplate(@RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[修改微信template接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatTemplateService.modefyWeChatTemplate(strJson);
		logger.info("[修改微信template接口]，返回值：" + responseObject);
		return responseObject;
	};
	@RequestMapping(value = "/deleWeChatTemplate/")
	@ResponseBody
	public ResponseObject deleWeChatTemplate(@RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[删除微信template接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatTemplateService.deleWeChatTemplate(strJson);
		logger.info("[删除微信template接口]，返回值：" + responseObject);
		return responseObject;
	};
	@RequestMapping(value = "/findAllWeChatTemplate/")
	@ResponseBody
	public ResponseObject findAllWeChatTemplate(@RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[查询所有微信template接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatTemplateService.findAllWeChatTemplate(strJson);
		logger.info("[查询所有微信template接口]，返回值：" + responseObject);
		return responseObject;
	};
	@RequestMapping(value = "/findWeChatTemplateById/")
	@ResponseBody
	public ResponseObject findWeChatTemplateById(@RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[查询微信template接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatTemplateService.findWeChatTemplateById(strJson);
		logger.info("[查询微信template接口]，返回值：" + responseObject);
		return responseObject;
	};
	
	@RequestMapping(value = "/findWeChatTemplateByType/")
	@ResponseBody
	public ResponseObject findWeChatTemplateByType(@RequestBody String strJson,
			HttpServletRequest request){
		ResponseObject resObj = new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");;
		logger.info("[通过type查询微信template接口]，请求参数：" + strJson);
		CommonParameters commonParameters=new CommonParameters();
		strJson=SuperDeal.getCommonPara(commonParameters,strJson);
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return resObj;
		}
		ResponseObject responseObject = weChatTemplateService.findWeChatTemplateByType(strJson);
		logger.info("[通过type查询微信template接口]，返回值：" + responseObject);
		return responseObject;
	};
	
}
