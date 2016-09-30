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
import com.cdvcloud.rms.service.IPresentationService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/presentation")
public class PresentationController {
	private static final Logger logger = Logger.getLogger(PresentationController.class);
	@Autowired
	private IPresentationService materialService;
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IUserService userService;

	/**
	 * 文稿统计
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param count
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "count/")
	@ResponseBody
	public ResponseObject count(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters count, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(count, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(count.getUserId());
			UserUtil.getUserInfo(count, userMap);
			// 获取日志ip信息
			// SystemLogUtil.getIp(count, request);
			return materialService.count(count, strJson);
		} catch (Exception e) {
			logger.error("文稿统计接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}

	}

	/**
	 * 文稿统计报表
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param count
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "statistics/")
	@ResponseBody
	public ResponseObject statistics(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters count, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(count, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(count.getUserId());
			UserUtil.getUserInfo(count, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(count, request);
			return materialService.statistics(count, strJson);
		} catch (Exception e) {
			logger.error("文稿统计报表接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}

	}

	/**
	 * 创建文稿接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param count
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "insert/")
	@ResponseBody
	public ResponseObject insert(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters insert, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(insert, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(insert.getUserId());
			UserUtil.getUserInfo(insert, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(insert, request);
			return materialService.insert(insert, strJson);
		} catch (Exception e) {
			logger.error("创建文稿接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 查询文稿接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param count
	 * @param strJson
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
			// 获取日志ip信息
			// SystemLogUtil.getIp(query, request);
			return materialService.query(query, strJson);
		} catch (Exception e) {
			logger.error("查询文稿接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 查询文稿根据ID查询接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param count
	 * @param strJson
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
			return materialService.queryById(queryById, strJson);
		} catch (Exception e) {
			logger.error("查询文稿根据ID查询接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 修改文稿接口
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param update
	 * @param strJson
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
			// 获取日志ip信息
			SystemLogUtil.getIp(update, request);
			return materialService.update(update, strJson);
		} catch (Exception e) {
			logger.error("修改文稿接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 文稿添加素材
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param update
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "addMedia/")
	@ResponseBody
	public ResponseObject addMedia(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters addMedia, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(addMedia, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(addMedia.getUserId());
			UserUtil.getUserInfo(addMedia, userMap);
			// 获取日志ip信息
			SystemLogUtil.getIp(addMedia, request);
			return materialService.addMedia(addMedia, strJson);
		} catch (Exception e) {
			logger.error("修改文稿接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 删除文稿接口
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
			// 获取日志ip信息
			SystemLogUtil.getIp(delete, request);
			return materialService.delete(delete, strJson);
		} catch (Exception e) {
			logger.error("删除文稿接口错误：" + e);
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
			return materialService.thumbnail(thumbnailCommons, strJson);
		} catch (Exception e) {
			logger.error("设置文稿缩略图接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 推送到INEWS
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param queryById
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "pushINEWS/")
	@ResponseBody
	public ResponseObject pushINEWS(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
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
			// 获取日志ip信息
			SystemLogUtil.getIp(queryById, request);
			return materialService.pushINEWS(queryById, strJson);
		} catch (Exception e) {
			logger.error("查询文稿根据ID查询接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 推送到汇聚
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param queryById
	 * @param strJson
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "pushConverge/")
	@ResponseBody
	public ResponseObject pushConverge(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
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
			// 获取日志ip信息
			SystemLogUtil.getIp(queryById, request);
			return materialService.pushConverge(queryById, strJson);
		} catch (Exception e) {
			logger.error("推送到汇聚错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	@RequestMapping(value = "toFieldNews/")
	@ResponseBody
	public ResponseObject toFieldNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = materialService.copyToFieldNews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，拷贝通联到文稿失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
}
