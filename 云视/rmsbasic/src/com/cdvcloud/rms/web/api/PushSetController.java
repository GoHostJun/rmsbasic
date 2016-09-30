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
import com.cdvcloud.rms.service.ICustomPushService;
import com.cdvcloud.rms.service.IPushSetService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/")
public class PushSetController {
	private static final Logger logger = Logger.getLogger(PushSetController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private ICustomPushService customPushService;
	@Autowired
	private IPushSetService pushSetService;
	@Autowired
	private IUserService userService;

	/** 添加一条推送接口信息 */
	@RequestMapping(value = "v1/api/push/addPushSet/")
	@ResponseBody
	public ResponseObject addPushSet(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
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
			resObj = pushSetService.createObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，创建失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 获取推送接口信息 */
	@RequestMapping(value = "v1/api/push/getPushSets/")
	@ResponseBody
	public ResponseObject getPushSets(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			resObj = pushSetService.findObjectAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 根据id获取一条信息 */
	@RequestMapping(value = "v1/api/push/getPushSetInfo/")
	@ResponseBody
	public ResponseObject getPushSetInfo(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			resObj = pushSetService.findObjectById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 根据id删除一条信息 */
	@RequestMapping(value = "v1/api/push/deletePushSet/")
	@ResponseBody
	public ResponseObject deletePushSet(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
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
			resObj = pushSetService.deleteObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，删除失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 根据id更新一条信息 */
	@RequestMapping(value = "v1/api/push/updatePushSet/")
	@ResponseBody
	public ResponseObject updatePushSet(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(commonParameters, request);
			resObj = pushSetService.updateObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 获取常用推送列表 */
	@RequestMapping(value = "v1/api/push/getCustomPushs/")
	@ResponseBody
	public ResponseObject getCustomPushs(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			resObj = customPushService.findObjectAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

}
