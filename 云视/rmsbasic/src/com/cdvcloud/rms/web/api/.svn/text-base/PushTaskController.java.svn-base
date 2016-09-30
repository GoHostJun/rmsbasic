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
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.IPushTaskService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/pushTask")
public class PushTaskController {
	
	private static final Logger logger = Logger.getLogger(PushTaskController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPushTaskService pushTaskService;

	/**
	 * 查询推送任务列表
	 * @param appCode
	 * @param versionId
	 * @param companyId
	 * @param userId
	 * @param serviceCode
	 * @param commonParameters
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "pushTaskFindList/")
	@ResponseBody
	public ResponseObject pushTaskFindList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			UserUtil.getUserInfo(commonParameters, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(commonParameters, request);
			resObj = pushTaskService.findList(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，查询推送任务列表失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	@RequestMapping(value = "pushTaskFindById/")
	@ResponseBody
	public ResponseObject pushTaskFindById(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			UserUtil.getUserInfo(commonParameters, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(commonParameters, request);
			resObj = pushTaskService.findList(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，查询推送任务列表失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/**
	 * 查询推送任务转码进度
	 * @param appCode
	 * @param versionId
	 * @param companyId
	 * @param userId
	 * @param serviceCode
	 * @param commonParameters
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryProgress/")
	@ResponseBody
	public ResponseObject queryProgress(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			UserUtil.getUserInfo(commonParameters, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(commonParameters, request);
			resObj = pushTaskService.queryProgress(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，查询推送任务转码进度失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
}
