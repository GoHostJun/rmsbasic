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
import com.cdvcloud.rms.service.IFieldnewsService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 新闻外场
 * 
 * @author mcxin
 * 
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/fieldNews/")
public class FieldnewsController {
	private static final Logger logger = Logger.getLogger(FieldnewsController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IFieldnewsService fieldnewsService;
	@Autowired
	private IUserService userService;

	/**
	 * 查询外场新闻列表
	 */
	@RequestMapping(value = "findFieldNews/")
	@ResponseBody
	public ResponseObject findFieldNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.findFieldnewsAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取查询外场新闻列表失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 查询外场新闻列表
	 */
	@RequestMapping(value = "findFieldNewsByUser/")
	@ResponseBody
	public ResponseObject findFieldNewsByUser(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.findFieldnewsByUser(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取查询外场新闻列表失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 根据id查询通联
	 */
	@RequestMapping(value = "findFieldNewsById/")
	@ResponseBody
	public ResponseObject findFieldNewsById(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			// 获取日志ip信息
			SystemLogUtil.getIp(commonParameters, request);
			resObj = fieldnewsService.findFieldnewsById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，根据id查询通联失败" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 删除新闻外场
	 */
	@RequestMapping(value = "deleteFieldNews/")
	@ResponseBody
	public ResponseObject deleteFieldNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.cancelFieldnews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，取消通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 新闻外场复制到文稿库
	 */
	@RequestMapping(value = "copyFieldNews/")
	@ResponseBody
	public ResponseObject copyFieldNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.copyFieldnews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，拷贝通联到文稿失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 更新通联
	 */
	@RequestMapping(value = "updateFieldNews/")
	@ResponseBody
	public ResponseObject updateFieldNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.updateFieldnews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 通联添加素材
	 */
	@RequestMapping(value = "addFieldNewsMaterial/")
	@ResponseBody
	public ResponseObject addFieldNewsMaterial(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = fieldnewsService.addFieldnewsMaterial(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联添加素材失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 通联更新素材
	 */
	@RequestMapping(value = "updateFieldNewsMaterial/")
	@ResponseBody
	public ResponseObject updateFieldNewsMaterial(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = fieldnewsService.updateFieldnewsMaterial(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联更新素材失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 通联删除素材
	 */
	@RequestMapping(value = "deleteFieldNewsMaterial/")
	@ResponseBody
	public ResponseObject deleteFieldNewsMaterial(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.deleteFieldnewsMaterial(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联删除素材失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 分享新闻外场
	 */
	@RequestMapping(value = "sendFieldNews/")
	@ResponseBody
	public ResponseObject sendFieldNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.sendFieldnews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联推送失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	@RequestMapping(value = "top/")
	@ResponseBody
	public ResponseObject top(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = fieldnewsService.top(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联推送失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
}
