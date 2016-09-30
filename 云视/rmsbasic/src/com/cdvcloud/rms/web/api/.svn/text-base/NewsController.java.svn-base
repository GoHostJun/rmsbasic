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
import com.cdvcloud.rms.service.INewsService;
import com.cdvcloud.rms.service.IPushTaskService;
import com.cdvcloud.rms.service.IStatisticService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 新闻通联控制层
 * 
 * @author huangaigang
 * 
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/news")
public class NewsController {
	private static final Logger logger = Logger.getLogger(NewsController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private INewsService newsService;
	@Autowired
	private IStatisticService statisticService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPushTaskService pushTaskService;

	/**
	 * 获取相关通联数量
	 */
	@RequestMapping(value = "getNewsTotal/")
	@ResponseBody
	public ResponseObject getNewsTotal(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.countNewsByUser(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取通联数量失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 获取代办任务数量
	 */
	@RequestMapping(value = "getNewsUnDeal/")
	@ResponseBody
	public ResponseObject getNewsUnDeal(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.unDealNewsByUser(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取通联数量失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 创建通联
	 */
	@RequestMapping(value = "createNews/")
	@ResponseBody
	public ResponseObject createNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.createNews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，创建通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 创建服务
	 * 
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
	@RequestMapping(value = "createService/")
	@ResponseBody
	public ResponseObject createService(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = newsService.createService(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，创建通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 查询通联列表
	 */
	@RequestMapping(value = "findNews/")
	@ResponseBody
	public ResponseObject findNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.findNewsAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 查询通联列表
	 */
	@RequestMapping(value = "findNewsByUser/")
	@ResponseBody
	public ResponseObject findNewsByUser(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.findNewsByUser(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 根据id查询通联
	 */
	@RequestMapping(value = "findNewsById/")
	@ResponseBody
	public ResponseObject findNewsById(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = newsService.findNewsById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，根据id查询通联失败" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 添加通联人
	 */
	@RequestMapping(value = "addNewsUser/")
	@ResponseBody
	public ResponseObject addNewsUser(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = newsService.addNewsUser(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，添加通联人失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 取消通联
	 */
	@RequestMapping(value = "cancelNews/")
	@ResponseBody
	public ResponseObject cancelNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.cancelNews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，取消通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 拷贝通联到文稿
	 */
	@RequestMapping(value = "copyNews/")
	@ResponseBody
	public ResponseObject copyNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.copyNews(commonParameters, strJson);
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
	@RequestMapping(value = "updateNews/")
	@ResponseBody
	public ResponseObject updateNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.updateNews(commonParameters, strJson);
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
	@RequestMapping(value = "addNewsMaterial/")
	@ResponseBody
	public ResponseObject addNewsMaterial(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = newsService.addNewsMaterial(commonParameters, strJson);
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
	@RequestMapping(value = "updateNewsMaterial/")
	@ResponseBody
	public ResponseObject updateNewsMaterial(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = newsService.updateNewsMaterial(commonParameters, strJson);
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
	@RequestMapping(value = "deleteNewsMaterial/")
	@ResponseBody
	public ResponseObject deleteNewsMaterial(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.deleteNewsMaterial(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联删除素材失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 推送通联
	 */
	@RequestMapping(value = "sendNews/")
	@ResponseBody
	public ResponseObject sendNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.sendNews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联推送失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 推送通联到网台
	 */
	@RequestMapping(value = "sendNewsToNetStation/")
	@ResponseBody
	public ResponseObject sendNewsToNetStation(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
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
			resObj = newsService.sendNewsToNetStation(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联推送失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 根据分享查询通联列表
	 */
	@RequestMapping(value = "findNewsByShare/")
	@ResponseBody
	public ResponseObject findNewsByShare(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.findNewsByShare(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 根据点击量查询通联列表
	 */
	@RequestMapping(value = "findNewsByCount/")
	@ResponseBody
	public ResponseObject findNewsByCount(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.findNewsByCount(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	@RequestMapping(value = "findCommunicationsByCount/")
	@ResponseBody
	public ResponseObject findCommunicationsByCount(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = statisticService.getNewsStatistic(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取通量排行失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 推送通联
	 */
	@RequestMapping(value = "pushNews/")
	@ResponseBody
	public ResponseObject pushNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = pushTaskService.pushVerify(commonParameters, strJson);
//			resObj = newsService.pushNews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，通联推送失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/**
	 * 通联取消分享
	 */
	@RequestMapping(value = "unshareNews/")
	@ResponseBody
	public ResponseObject unshareNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
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
			resObj = newsService.unshareNews(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，取消共享通联失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
}
