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
import com.cdvcloud.rms.service.IAreaService;
import com.cdvcloud.rms.service.IOrganizeService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 组织结构控制器
 * 
 * @author TYW
 * 
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/org")
public class OrganizeController {

	private static final Logger logger = Logger.getLogger(OrganizeController.class);

	@Autowired
	IOrganizeService organizeService;
	@Autowired
	IAreaService areaService;
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IUserService userService;

	/**
	 * 获取指定应用的用户信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUsersByAppCode/")
	@ResponseBody
	public ResponseObject getUsersByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定应用的用户信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getUsersByAppCode(query, strJson);
		logger.info("[获取指定应用的用户信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取组织结构信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getOrgInfo/")
	@ResponseBody
	public ResponseObject getOrgInfo(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取组织结构信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getOrgInfo(query, strJson);
		logger.info("[获取组织结构信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定用户的详细信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUserInfoByUserId/")
	@ResponseBody
	public ResponseObject getUserInfoByUserId(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定用户的详细信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getUserInfoByUserId(query, strJson);
		logger.info("[获取指定用户的详细信息]，返回值：" + responseObject);
		return responseObject;
	}
	
	/**
	 * 获取指定用户的详细信息
	 * 支持统一用户id查询
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUserByUserId/")
	@ResponseBody
	public ResponseObject getUserByUserId(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定用户的详细信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		Map<String, Object> mapUser = userService.getUserInforById(String.valueOf(mapRequest.get("userId")));
		ResponseObject responseObject = new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, mapUser);
		logger.info("[获取指定用户的详细信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定应用的组织结构信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getOrgsByAppCode/")
	@ResponseBody
	public ResponseObject getOrgsByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定应用的组织结构信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getOrgsByAppCode(query, strJson);
		logger.info("[获取指定应用的组织结构信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定机构的用户信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUsersByPartId/")
	@ResponseBody
	public ResponseObject getUsersByPartId(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定机构的用户信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getUsersByPartId(query, strJson);
		logger.info("[获取指定机构的用户信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定机构的用户信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUsersByPartId2/")
	@ResponseBody
	public ResponseObject getUsersByPartId2(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定机构的用户信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getUsersByPartId2(query, strJson);
		logger.info("[获取指定机构的用户信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定应用的角色信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getRolesByAppCode/")
	@ResponseBody
	public ResponseObject getRolesByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定应用的角色信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getRolesByAppCode(query, strJson);
		logger.info("[获取指定应用的角色信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定应用的信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getInfoByAppCode/")
	@ResponseBody
	public ResponseObject getInfoByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定应用的信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getInfoByAppCode(query, strJson);
		logger.info("[获取指定应用的信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定角色的用户信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUsersByRoleId/")
	@ResponseBody
	public ResponseObject getUsersByRoleId(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定角色的用户信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getUsersByRoleId(query, strJson);
		logger.info("[获取指定角色的用户信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定角色的用户信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUsersByRole/")
	@ResponseBody
	public ResponseObject getUsersByRole(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定角色的用户信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getUsersByRole(query, strJson);
		logger.info("[获取指定角色的用户信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getUserByName/")
	@ResponseBody
	public ResponseObject getUserByName(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[根据用户名查询用户]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.getUserByName(query, strJson);
		logger.info("[根据用户名查询用户]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 添加机构到指定应用
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "addDepartmentByAppCode/")
	@ResponseBody
	public ResponseObject addDepartmentByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[添加机构到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.addDepartmentByAppCode(query, strJson);
		logger.info("[添加机构到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 编辑指定应用的机构信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "editDepartmentByAppCode/")
	@ResponseBody
	public ResponseObject editDepartmentByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[编辑指定应用的机构信息]，请求参数：" + query);
		ResponseObject responseObject = organizeService.editDepartmentByAppCode(query, strJson);
		logger.info("[编辑指定应用的机构信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 删除指定应用的机构信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "removeDepartmentByAppCode/")
	@ResponseBody
	public ResponseObject removeDepartmentByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[删除指定应用的机构信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.removeDepartmentByAppCode(query, strJson);
		logger.info("[删除指定应用的机构信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 添加用户到指定应用
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "addUserToAppCode/")
	@ResponseBody
	public ResponseObject addUserToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[添加用户到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.addUserToAppCode(query, strJson);
		logger.info("[添加用户到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 接口批量导入用户
	 * 
	 * @param request
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject batchImportUsers(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		Map<String, Object> mapRequest = JsonUtil.readJSON2Map(strJson);
		// 发送接口用户信息
		String userInfoStr = String.valueOf(mapRequest.get("userInfo"));
		if (StringUtil.isEmpty(userInfoStr)) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		Map<String, Object> userInfo = JsonUtil.readJSON2Map(userInfoStr);
		query = UserUtil.getUserInfo(userInfo, query);
		logger.info("[添加用户到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.batchImportUsers(query, mapRequest);
		logger.info("[添加用户到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 添加角色到指定应用
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "addRoleToAppCode/")
	@ResponseBody
	public ResponseObject addRoleToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[添加角色到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.addRoleToAppCode(query, strJson);
		logger.info("[添加角色到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 修改角色到指定应用
	 * 
	 * @param request
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "updateRoleToAppCode/")
	@ResponseBody
	public ResponseObject updateRoleToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[修改角色到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.updateRoleToAppCode(query, strJson);
		logger.info("[修改角色到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 删除角色到指定应用
	 * 
	 * @param request
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "deleteRoleToAppCode/")
	@ResponseBody
	public ResponseObject deleteRoleToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[删除角色到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.deleteRoleToAppCode(query, strJson);
		logger.info("[删除角色到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 更新用户信息到指定应用
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "updateUserToAppCode/")
	@ResponseBody
	public ResponseObject updateUserToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[更新用户信息到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.updateUserToAppCode(query, strJson);
		logger.info("[更新用户信息到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 删除用户信息到指定应用
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "deleteUserToAppCode/")
	@ResponseBody
	public ResponseObject deleteUserToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		logger.info("[删除用户信息到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.deleteUserToAppCode(query, strJson);
		logger.info("[删除用户信息到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 重置用户密码
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "resetPassword")
	@ResponseBody
	public ResponseObject resetPassword(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[添加用户到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.resetPwdToAppCode(query, strJson);
		logger.info("[添加用户到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 根据id修改用户信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "updateUserByUserId/")
	@ResponseBody
	public ResponseObject updateUserByUserId(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[更新用户信息到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.updateUserByUserId(query, strJson);
		logger.info("[更新用户信息到指定应用]，返回值：" + responseObject);
		Map<String, Object> map = JsonUtil.readJSON2Map(strJson);
		String txtName = String.valueOf(map.get("txtName"));
		UserUtil.setSessionProperty(request, "userName", txtName);
		return responseObject;
	}

	@RequestMapping(value = "deleteUserByIds")
	@ResponseBody
	public ResponseObject deleteUserByIds(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[添加用户到指定应用]，请求参数：" + query);
		ResponseObject responseObject = organizeService.deleteUsersByIds(query, strJson);
		logger.info("[添加用户到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定应用的地区信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getAreasByAppCode/")
	@ResponseBody
	public ResponseObject getAreasByAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定应用的角色信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = areaService.getAreasByAppCode(query, strJson);
		logger.info("[获取指定应用的角色信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 添加地区到指定应用
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "addAreaToAppCode/")
	@ResponseBody
	public ResponseObject addAreaToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[添加角色到指定应用]，请求参数：" + query);
		ResponseObject responseObject = areaService.addAreaToAppCode(query, strJson);
		logger.info("[添加角色到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 删除地区到指定应用
	 * 
	 * @param request
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "deleteAreaToAppCode/")
	@ResponseBody
	public ResponseObject deleteAreaToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[删除角色到指定应用]，请求参数：" + query);
		ResponseObject responseObject = areaService.deleteAreaToAppCode(query, strJson);
		logger.info("[删除角色到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 获取指定地区的详细信息
	 * 
	 * @param companyId
	 * @param appCode
	 * @param areaId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "getAreaInfoByAreaId/")
	@ResponseBody
	public ResponseObject getAreaInfoByAreaId(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		logger.info("[获取指定用户的详细信息]，请求参数：" + query);
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = areaService.getAreaInfoByAreaId(query, strJson);
		logger.info("[获取指定用户的详细信息]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 更新地区信息到指定应用
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "updateAreaToAppCode/")
	@ResponseBody
	public ResponseObject updateAreaToAppCode(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		query = UserUtil.getUserInfo(request, query);
		logger.info("[更新用户信息到指定应用]，请求参数：" + query);
		ResponseObject responseObject = areaService.updateAreaToAppCode(query, strJson);
		logger.info("[更新用户信息到指定应用]，返回值：" + responseObject);
		return responseObject;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "updatePassword/")
	@ResponseBody
	public ResponseObject updatePassword(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		// 校验参数
		boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
		if (!validParam) {
			return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
		}
		ResponseObject responseObject = organizeService.updatePwdToAppCode(strJson);
		return responseObject;
	}

	@RequestMapping(value = "syncUsers/")
	@ResponseBody
	public ResponseObject syncUsers(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			query = UserUtil.getUserInfo(request, query);
			responseObject = organizeService.syncUserByUnion(query, strJson);
			return responseObject;
		} catch (Exception e) {
			e.printStackTrace();
			return responseObject;
		}
	}

	@RequestMapping(value = "updateUsersStatus")
	@ResponseBody
	public ResponseObject updateUsersStatus(HttpServletRequest request, @PathVariable String companyId, @PathVariable String appCode,
			@PathVariable String userId, @PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query,
			@RequestBody String strJson) {
		ResponseObject responseObject = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			responseObject = organizeService.updateUsersStatus(query, strJson);
			return responseObject;
		} catch (Exception e) {
			e.printStackTrace();
			return responseObject;
		}
	}

}
