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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/media")
public class MediaController {
	private static final Logger logger = Logger.getLogger(MediaController.class);
	@Autowired
	private IMediaService materialService;
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IUserService userService;
	@Autowired
	private ISystemLogService systemLogService;
	@RequestMapping("saveMaterial/")
	@ResponseBody
	public String saveMaterial(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters common,
			@RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "fileSize") String fileSize, 
			HttpServletRequest request) {
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return Constants.SERVICE_ERROR;
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			//获取日志ip信息
			SystemLogUtil.getIp(common, request);
			materialService.insetMedia(common, fileName, fileSize,"");
			systemLogService.inset(SystemLogUtil.getMap(common, "0", "上传", "上传文件《"+fileName+"》"));
			LogUtil.printIntegralLog(common, "uploadfile", "上传文件《"+fileName+"》");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传媒体失败："+e);
			return Constants.SERVICE_ERROR;
		}

		return Constants.SERVICE_SUCCESS;

	}
	@RequestMapping("saveMaterialtop/")
	@ResponseBody
	public ResponseObject saveMaterialtop(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters common,
			@RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "fileSize") String fileSize, 
			HttpServletRequest request) {
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(common);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.media_failure.status,GeneralStatus.media_failure.enDetail,"");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			//获取日志ip信息
			SystemLogUtil.getIp(common, request);
			systemLogService.inset(SystemLogUtil.getMap(common, "0", "上传", "上传文件《"+fileName+"》"));
			LogUtil.printIntegralLog(common, "uploadfile", "上传文件《"+fileName+"》");
			return materialService.insetMedia(common, fileName, fileSize,"");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传媒体失败："+e);
			return new ResponseObject(GeneralStatus.media_failure.status,GeneralStatus.media_failure.enDetail,"");
		}

	}
	/**
	 * 页面上传http地址注册素材
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
	@RequestMapping("saveHttpMaterial/")
	@ResponseBody
	public ResponseObject saveHttpMaterial(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
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
			return materialService.insetHttpMedia(common, strJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传媒体失败：" + e);
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}

	}
	/**
	 * 素材重试
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
	@RequestMapping("retry/")
	@ResponseBody
	public String retry(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "file") String file,//地址
			@RequestParam(value = "fileName") String fileName, 
			HttpServletRequest request) {
		try {
			CommonParameters common = new CommonParameters();
			common.setAppCode(Constants.APPCODEVALUE);
			common.setUserId(userId);
			common.setVersionId("v1");
			common.setCompanyId("cdv-yuntonglian");
			common.setServiceCode(Constants.SERVICECODEVALUE);
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			//获取日志ip信息
			SystemLogUtil.getIp(common, request);
			systemLogService.inset(SystemLogUtil.getMap(common, "0", "素材重试", "上传文件《"+fileName+"》"));
			materialService.insetMedia(common, fileName, "",file);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传媒体失败："+e);
			return Constants.SERVICE_ERROR;
		}

		return Constants.SERVICE_SUCCESS;

	}
	/**
	 * 媒体注册接口
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "registerResource/")
	@ResponseBody
	public ResponseObject registerResource(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters insert,
			@RequestBody String strJson,
			HttpServletRequest request){
		//校验参数
		boolean validParam = validateCommonParam.validateCommonParam(insert,strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		//获取用户信息
		Map<String, Object> userMap= userService.getUserInforById(insert.getUserId());
		UserUtil.getUserInfo(insert, userMap);
		//获取日志ip信息
		SystemLogUtil.getIp(insert, request);
		return materialService.registerResource(insert,strJson,"接口注册");

	}
	@RequestMapping(value = "scanRegistration/")
	@ResponseBody
	public ResponseObject scanRegistration(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters insert,
			@RequestBody String strJson,
			HttpServletRequest request){
		//校验参数
		boolean validParam = validateCommonParam.validateCommonParam(insert,strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		//获取用户信息
		Map<String, Object> userMap= userService.getUserInforById(insert.getUserId());
		UserUtil.getUserInfo(insert, userMap);
		//获取日志ip信息
		SystemLogUtil.getIp(insert, request);
		return materialService.scanRegistration(insert,strJson);

	}
	/**
	 * 查询媒体接口
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
	public ResponseObject query(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters query,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(query.getUserId());
			UserUtil.getUserInfo(query, userMap);
			//获取登录ip
			//			SystemLogUtil.getIp(query, request);
			return materialService.findList(query,strJson);
		} catch (Exception e) {
			logger.error("查询媒体接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	/**
	 * 查询媒体根据ID查询接口
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
	public ResponseObject queryById(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters queryById,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(queryById,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(queryById.getUserId());
			UserUtil.getUserInfo(queryById, userMap);
			//获取登录ip
			//			SystemLogUtil.getIp(queryById, request);
			return materialService.findOne(queryById,strJson);
		} catch (Exception e) {
			logger.error("查询媒体根据ID查询接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	@RequestMapping(value = "queryByIds/")
	@ResponseBody
	public ResponseObject queryByIds(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters queryById,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(queryById,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(queryById.getUserId());
			UserUtil.getUserInfo(queryById, userMap);
			//获取登录ip
			//			SystemLogUtil.getIp(queryById, request);
			return materialService.findList(queryById,strJson);
		} catch (Exception e) {
			logger.error("查询媒体根据ID查询接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	/**
	 * 查询媒体接口（可选返回指定字段）
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "queryByVisual/")
	@ResponseBody
	public ResponseObject queryByVisual(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters queryByVisual,
			@RequestBody String strJson){


		return null;

	}
	/**
	 * 修改媒体接口
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
	public ResponseObject update(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters update,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(update,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(update.getUserId());
			UserUtil.getUserInfo(update, userMap);
			//获取登录ip
			SystemLogUtil.getIp(update, request);
			return materialService.update(update, strJson);
		} catch (Exception e) {
			logger.error("修改媒体接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	/**
	 * 统计素材总数
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
	public ResponseObject count(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters count,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(count,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(count.getUserId());
			UserUtil.getUserInfo(count, userMap);
			//获取登录ip
			SystemLogUtil.getIp(count, request);
			return materialService.count(count, strJson);
		} catch (Exception e) {
			logger.error("统计素材总数接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	/**
	 * 报表统计
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
	public ResponseObject statistics(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters count,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(count,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(count.getUserId());
			UserUtil.getUserInfo(count, userMap);
			//获取登录ip
			SystemLogUtil.getIp(count, request);
			return materialService.statistics(count, strJson);
		} catch (Exception e) {
			logger.error("报表统计接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	/**
	 * 删除素材接口
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
	public ResponseObject delete(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters delete,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(delete,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			//获取用户信息
			Map<String, Object> userMap= userService.getUserInforById(delete.getUserId());
			UserUtil.getUserInfo(delete, userMap);
			//获取登录ip
			SystemLogUtil.getIp(delete, request);
			return materialService.deleteList(delete, strJson);
		} catch (Exception e) {
			logger.error("删除素材接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}

	@RequestMapping(value = "queryByUploadIds/")
	@ResponseBody
	public ResponseObject queryByUploadIds(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters queryById,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(queryById,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			return materialService.findList(mapJson);
		} catch (Exception e) {
			logger.error("查询媒体根据ID查询接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	
	/**
	 * 进度查询
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
	@RequestMapping(value = "queryProgress/")
	@ResponseBody
	public ResponseObject queryProgress(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters common,
			@RequestBody String strJson, 
			HttpServletRequest request){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(common,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			return materialService.queryProgress(common,strJson);
		} catch (Exception e) {
			logger.error("进度查询错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
}
