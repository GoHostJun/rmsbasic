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
import com.cdvcloud.rms.service.ICheckService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.UserUtil;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/check")
public class CheckController {
	private static final Logger logger = Logger.getLogger(CheckController.class);
	@Autowired
	private ICheckService checkService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ValidateCommonParam validateCommonParam;

	/** 审核通联 */
	@RequestMapping(value = "checkNews/")
	@ResponseBody
	public ResponseObject checkNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = checkService.createCheck(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，创建评论失败" + e);
			e.printStackTrace();
			return new ResponseObject(2, "Inner Error", "");
		}
		return resObj;
	}

	/** 获取审核记录 */
	@RequestMapping(value = "getChecks/")
	@ResponseBody
	public ResponseObject getChecks(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = checkService.findCheckAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取评论失败" + e);
			e.printStackTrace();
			return new ResponseObject(2, "Inner Error", "");
		}
		return resObj;
	}

	/** 提交审核 */
	@RequestMapping(value = "submitNewsToCheck/")
	@ResponseBody
	public ResponseObject submitNewsToCheck(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = checkService.submitNewsToCheck(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，提交审核失败" + e);
			e.printStackTrace();
			return new ResponseObject(2, "Inner Error", "");
		}
		return resObj;
	}

}
