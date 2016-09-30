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
import com.cdvcloud.rms.service.IMaterialSetService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 素材集管理
 * 
 * @author mcxin
 * 
 */
@Controller
@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/materialSet")
public class MaterialSetController {
	private static final Logger logger = Logger.getLogger(MaterialSetController.class);
	@Autowired
	private IMaterialSetService materialSetService;
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IUserService userService;

	/**
	 * 创建素材集
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param fileName
	 * @param fileSize
	 * @param request
	 * @return
	 */
	@RequestMapping("savematerialSet/")
	@ResponseBody
	public ResponseObject savematerialSet(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(common);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}
		// 获取用户信息
		Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
		UserUtil.getUserInfo(common, userMap);
		// 获取日志ip信息
		SystemLogUtil.getIp(common, request);
		return materialSetService.savematerialSet(common, strJson);
	}

	/**
	 * 查询媒体接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "query/")
	@ResponseBody
	public ResponseObject query(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(query.getUserId());
			UserUtil.getUserInfo(query, userMap);
			// 获取登录ip
			// SystemLogUtil.getIp(query, request);
			return materialSetService.findList(query, strJson);
		} catch (Exception e) {
			logger.error("查询媒体接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	@RequestMapping(value = "queryMediaList/")
	@ResponseBody
	public ResponseObject queryMediaList(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(query.getUserId());
			UserUtil.getUserInfo(query, userMap);
			// 获取登录ip
			// SystemLogUtil.getIp(query, request);
			return materialSetService.queryMediaList(query, strJson);
		} catch (Exception e) {
			logger.error("查询媒体接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 查询媒体根据ID查询接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "queryById/")
	@ResponseBody
	public ResponseObject queryById(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters queryById, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(queryById, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(queryById.getUserId());
			UserUtil.getUserInfo(queryById, userMap);
			// 获取登录ip
			// SystemLogUtil.getIp(queryById, request);
			return materialSetService.findMediaList(queryById, strJson);
		} catch (Exception e) {
			logger.error("查询媒体根据ID查询接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	@RequestMapping(value = "queryByUser/")
	@ResponseBody
	public ResponseObject queryByUser(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters queryById, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(queryById, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(queryById.getUserId());
			UserUtil.getUserInfo(queryById, userMap);
			// 获取登录ip
			// SystemLogUtil.getIp(queryById, request);
			return materialSetService.queryByUser(queryById, strJson);
		} catch (Exception e) {
			logger.error("查询媒体根据ID查询接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 修改媒体接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "update/")
	@ResponseBody
	public ResponseObject update(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters update, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(update, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(update.getUserId());
			UserUtil.getUserInfo(update, userMap);
			// 获取登录ip
			SystemLogUtil.getIp(update, request);
			return materialSetService.update(update, strJson);
		} catch (Exception e) {
			logger.error("修改媒体接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 删除素材接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param delete
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "delete/")
	@ResponseBody
	public ResponseObject delete(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters delete, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(delete, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(delete.getUserId());
			UserUtil.getUserInfo(delete, userMap);
			// 获取登录ip
			SystemLogUtil.getIp(delete, request);
			return materialSetService.deleteList(delete, strJson);
		} catch (Exception e) {
			logger.error("删除素材接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 素材集素材复制到素材库
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param copy
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "copyMaterialSet/")
	@ResponseBody
	public ResponseObject copyMaterialSet(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters copy, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(copy, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(copy.getUserId());
			UserUtil.getUserInfo(copy, userMap);
			// 获取登录ip
			SystemLogUtil.getIp(copy, request);
			return materialSetService.copyMaterialSet(copy, strJson);
		} catch (Exception e) {
			logger.error("素材集素材复制到素材库接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 设置文稿缩略图接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param copy
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "thumbnail/")
	@ResponseBody
	public ResponseObject thumbnail(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters thumbnailCommons, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(thumbnailCommons, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(thumbnailCommons.getUserId());
			UserUtil.getUserInfo(thumbnailCommons, userMap);
			// 获取日志ip信息
			// SystemLogUtil.getIp(thumbnailCommons, request);
			return materialSetService.thumbnail(thumbnailCommons, strJson);
		} catch (Exception e) {
			logger.error("设置文稿缩略图接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

}
