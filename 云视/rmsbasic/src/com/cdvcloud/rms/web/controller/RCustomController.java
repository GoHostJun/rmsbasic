package com.cdvcloud.rms.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateJson;
import com.cdvcloud.rms.service.ICustomService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 客户Controller
 */
@Controller
@RequestMapping(value = "rms/custom")
public class RCustomController {
	private static final Logger logger = Logger.getLogger(RCustomController.class);
	@Autowired
	private ValidateJson validateJson;
	@Autowired
	ICustomService customService;

	/** 获取所有客户信息 */
	@RequestMapping(value = "/getAllCustom/")
	@ResponseBody
	public ResponseObject getAllCustom(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取客户列表信息]，请求参数：" + query);
		ResponseObject responseObject = customService.getAllCustom(query, strJson);
		logger.info("[获取客户列表信息]，返回值：" + responseObject);
		return responseObject;
	}

	/** 新增客户信息 */
	@RequestMapping(value = "/addCustom/")
	@ResponseBody
	public ResponseObject addCustom(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[新增客户列表信息]，请求参数：" + query);
		HttpSession session=request.getSession();
		Map<String, Object> sessionMap=new HashMap<String, Object>();
		sessionMap.put("createId", session.getAttribute(Constants.USERID));
		sessionMap.put("createName", session.getAttribute(Constants.USERNAME));
		ResponseObject responseObject = customService.addCustom(query, strJson,sessionMap);
		logger.info("[新增客户列表信息]，返回值：" + responseObject);
		return responseObject;
	}

	/** 修改客户信息 */
	@RequestMapping(value = "/updateCustom/")
	@ResponseBody
	public ResponseObject updateCustom(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[修改客户列表信息]，请求参数：" + query);
		ResponseObject responseObject = customService.updateCustom(query, strJson);
		logger.info("[修改客户列表信息]，返回值：" + responseObject);
		return responseObject;

	}

	/**
	 * 批量删除客户信息
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/deleteCustomByIds/")
	@ResponseBody
	public ResponseObject deleteCustomByIds(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[删除客户列表信息]，请求参数：" + query);
		ResponseObject responseObject = customService.deleteCustomByIds(query, strJson);
		logger.info("[删除客户列表信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 查询客户信息详情
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/getCustomById/")
	@ResponseBody
	public ResponseObject getCustomById(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[查看客户详细信息]，请求参数：" + query);
		ResponseObject responseObject = customService.getCustomById(query, strJson);
		logger.info("[查看客户详细信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 删除客户信息
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/deleteCustomById/")
	@ResponseBody
	public ResponseObject deleteCustomById(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[删除客户列表信息]，请求参数：" + query);
		ResponseObject responseObject = customService.deleteCustomById(query, strJson);
		logger.info("[删除客户列表信息]，返回值：" + responseObject);
		return responseObject;
	}

	/** 登录校验用户信息 */
	@RequestMapping(value = "/checkUserInfo/")
	@ResponseBody
	public ResponseObject checkUserInfo(HttpServletRequest request, @RequestBody String strJson) {
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
			if (StringUtil.isEmpty(mapJson.get("userName")) ||!String.valueOf(mapJson.get("userName")).equals("system")|| StringUtil.isEmpty(mapJson.get("password"))) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			Map<String, Object> customMap = customService.getUserInfo(mapJson);
			if (null != customMap && !customMap.isEmpty()) {
				UserUtil.saveCustomSession(request, customMap);
				resObj = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
			} else {
				resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
			}
		} catch (Exception e) {
			logger.error("系统内部错误，数据校验失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	@RequestMapping(value = "/toSetting/")
	public String toSetting(HttpServletRequest request,HttpServletResponse response) {
		return "custom/system";
	}
	@RequestMapping(value = "/toMaterial/")
	public String toMaterial(HttpServletRequest request,HttpServletResponse response) {
		return "custom/material_list";
	}
	@RequestMapping(value = "/toCustom/")
	public String toCustom(HttpServletRequest request,HttpServletResponse response) {
		return "custom/custom_list";
	}
	@RequestMapping(value = "/toWechatTemp/")
	public String toWechatTemp(HttpServletRequest request,HttpServletResponse response) {
		return "custom/wechattemplate_list";
	}
	@RequestMapping(value = "/toMaterialtemplate/")
	public String toMaterialtemplate(HttpServletRequest request,HttpServletResponse response) {
		return "custom/materialtemplate_list";
	}
	@RequestMapping(value = "/toEmegency/")
	public String toEmegency(HttpServletRequest request,HttpServletResponse response) {
		return "emegency/emegency_list";
	}
	@RequestMapping(value = "/toConfig/")
	public String toConfig(HttpServletRequest request,HttpServletResponse response) {
		return "custom/config_list";
	}
	@RequestMapping(value = "/toWelcom/")
	public String toWelcom(HttpServletRequest request,HttpServletResponse response) {
		return "custom/welcom";
	}
	@RequestMapping(value = "/toScan/")
	public String toScan(HttpServletRequest request,HttpServletResponse response) {
		return "custom/scan_list";
	}
	@RequestMapping(value = "/toScanlog/")
	public String toScanlog(HttpServletRequest request,HttpServletResponse response) {
		return "custom/scanlog_list";
	}
	
	@RequestMapping(value = "/toWeChatMessage/")
	public String toWeChatMessage(HttpServletRequest request,HttpServletResponse response) {
		return "custom/wechatmessage_list";
	}
	
	/**
	 * 查询配置信息详情
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/getConfiguration/")
	@ResponseBody
	public ResponseObject getConfiguration(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query
			) {
		ResponseObject responseObject = customService.getConfiguration(query);
		return responseObject;
	}
	
	/**
	 * 更新信息详情
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/updateConfiguration/")
	@ResponseBody
	public ResponseObject updateConfiguration(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		ResponseObject responseObject = customService.updateConfiguration(query, strJson);
		return responseObject;
	}
	
	/**
	 * 删除信息详情
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/deleteConfiguration/")
	@ResponseBody
	public ResponseObject deleteConfiguration(HttpServletRequest request, HttpServletResponse response, @Valid CommonParameters query,
			@RequestBody String strJson) {
		ResponseObject responseObject = customService.deleteConfiguration(query, strJson);
		return responseObject;
	}
}
