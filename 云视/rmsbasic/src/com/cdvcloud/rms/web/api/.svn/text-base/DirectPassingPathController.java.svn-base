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
import com.cdvcloud.rms.service.IDirectPassingPathService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 直传路径
 * 
 * @author yumingjun
 * 
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/directpassingpath")
public class DirectPassingPathController {
	private static final Logger logger = Logger.getLogger(DirectPassingPathController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;

	@Autowired
	private IUserService userService;

	@Autowired
	private IDirectPassingPathService directPassingPathService;

	@RequestMapping(value = "/findAllDirectPassingPath/")
	@ResponseBody
	public ResponseObject findAllDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			boolean checkboolean = commonCheck(commonParameters, strJson, request);
			if (!checkboolean) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = directPassingPathService.findObjectAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，查询失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	@RequestMapping(value = "/findOneDirectPassingPath/")
	@ResponseBody
	public ResponseObject findOneDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			boolean checkboolean = commonCheck(commonParameters, strJson, request);
			if (!checkboolean) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = directPassingPathService.findObjectById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，查询详情失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	@RequestMapping(value = "/addDirectPassingPath/")
	@ResponseBody
	public ResponseObject addDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			boolean checkboolean = commonCheck(commonParameters, strJson, request);
			if (!checkboolean) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = directPassingPathService.createObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	@RequestMapping(value = "/updateDirectPassingPath/")
	@ResponseBody
	public ResponseObject updateDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			boolean checkboolean = commonCheck(commonParameters, strJson, request);
			if (!checkboolean) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = directPassingPathService.updateObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	@RequestMapping(value = "/deleteDirectPassingPath/")
	@ResponseBody
	public ResponseObject deleteDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			boolean checkboolean = commonCheck(commonParameters, strJson, request);
			if (!checkboolean) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = directPassingPathService.deleteObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，删除失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	// 公共校验
	private boolean commonCheck(CommonParameters commonParameters, String strJson, HttpServletRequest request) {
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
		if (!validParam) {
			return validParam;
		}
		// 获取用户信息
		Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
		commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
		UserUtil.getUserInfo(commonParameters, userMap);
		// 获取日志ip信息
		SystemLogUtil.getIp(commonParameters, request);
		return validParam;
	}

}
