package com.cdvcloud.rms.web.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/log")
public class SystemLogController {
	private static final Logger logger = Logger.getLogger(SystemLogController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IUserService userService;
	/**
	 * 日志查询
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping("query/")
	@ResponseBody
	public ResponseObject registerResource(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters query,
			@RequestBody String strJson){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			return systemLogService.query(query,strJson);
		} catch (Exception e) {
			logger.error("日志查询接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	@RequestMapping("insertLog/")
	@ResponseBody
	public ResponseObject insertLog(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters common,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status,GeneralStatus.input_error.enDetail,"");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			//获取日志ip信息
			SystemLogUtil.getIp(common, request);
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			systemLogService.inset(SystemLogUtil.getMap(common, "0", String.valueOf(mapJson.get("action")), String.valueOf(mapJson.get("logdesc"))));
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,"");
		} catch (Exception e) {
			logger.error("日志查询接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
}