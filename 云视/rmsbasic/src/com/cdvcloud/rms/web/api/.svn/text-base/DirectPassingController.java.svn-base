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
import com.cdvcloud.rms.service.IDirectPassingService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 直传
 * 
 * @author mcxin
 * 
 */
@Controller
public class DirectPassingController {
	private static final Logger logger = Logger.getLogger(DirectPassingController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDirectPassingService directPassingService;

	/**
	 * 直传内网
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping("/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/directpassing/saveMaterial/")
	@ResponseBody
	public ResponseObject saveHttpMaterial(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(common, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			return directPassingService.saveHttpMaterial(common, strJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("直传媒体失败：" + e);
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}

	}

	@RequestMapping("/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/directpassing/findDirectPassAll/")
	@ResponseBody
	public ResponseObject findDirectPassAll(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(common, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(common, request);
			return directPassingService.findDirectPassAll(common, strJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取媒体直传列表失败：" + e);
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}

	}

	@RequestMapping(value = "/api/moveTaskCallBack/")
	public void moveTaskCallBack(HttpServletRequest request, @RequestBody String callbackParams) {
		logger.info("[公有云向私有云迁移数据的回调返回参数]，callbackParams = " + callbackParams);
		directPassingService.moveTaskCallBack(callbackParams);
	}

	@RequestMapping(value = "/api/NTMCallBack/")
	public void NTMCallBack(HttpServletRequest request, @RequestBody String callbackParams) {
		logger.info("[私有云NTM迁移数据的回调返回参数]，callbackParams = " + callbackParams);
		directPassingService.NTMCallBack(callbackParams);
	}
}
