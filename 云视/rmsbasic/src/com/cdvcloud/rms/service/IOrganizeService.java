package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

/**
 * 组织结构业务
 * 
 * @author TYW
 * 
 */
public interface IOrganizeService {

	/**
	 * 获取指定应用的用户信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getUsersByAppCode(CommonParameters query, String strJson);

	/**
	 * 获取指定应用的组织结构信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getOrgsByAppCode(CommonParameters query, String strJson);

	/**
	 * 获取组织结构信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getOrgInfo(CommonParameters query, String strJson);

	/**
	 * 获取指定用户的详细信息
	 * 
	 * 注：需要校验用户标识与应用标识是否匹配
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getUserInfoByUserId(CommonParameters query, String strJson);

	/**
	 * 获取指定机构的用户信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getUsersByPartId(CommonParameters query, String strJson);
	/**
	 * 获取指定机构的用户信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getUsersByPartId2(CommonParameters query, String strJson);

	/**
	 * 获取指定应用的角色信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getRolesByAppCode(CommonParameters query, String strJson);

	/**
	 * 获取指定应用的信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getInfoByAppCode(CommonParameters query, String strJson);

	/**
	 * 获取指定角色的用户信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getUsersByRoleId(CommonParameters query, String strJson);
	
	/**
	 * 获取指定角色的用户信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getUsersByRole(CommonParameters query, String strJson);

	/**
	 * 根据用户名查询用户
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getUserByName(CommonParameters query, String strJson);

	/**
	 * 添加机构到指定应用
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject addDepartmentByAppCode(CommonParameters query, String strJson);

	/**
	 * 编辑指定应用的机构信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject editDepartmentByAppCode(CommonParameters query, String strJson);

	/**
	 * 删除指定应用的机构信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject removeDepartmentByAppCode(CommonParameters query, String strJson);

	/**
	 * 添加用户到指定应用
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject addUserToAppCode(CommonParameters query, String strJson);

	/**
	 * 批量导入用户
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject batchImportUsers(CommonParameters query, Map<String, Object> mapRequest);
	
	/**
	 * 添加角色到指定应用
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject addRoleToAppCode(CommonParameters query, String strJson);
	
	public ResponseObject updateRoleToAppCode(CommonParameters query, String strJson);
	public ResponseObject deleteRoleToAppCode(CommonParameters query, String strJson);

	/**
	 * 更新用户信息到指定应用
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject updateUserToAppCode(CommonParameters query, String strJson);

	/**
	 * 删除用户信息到指定应用
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject deleteUserToAppCode(CommonParameters query, String strJson);
	
	/**
	 * 重置用户密码
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject resetPwdToAppCode(CommonParameters query, String strJson);
	
	/**
	 * 根据UserId修改用户信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject updateUserByUserId(CommonParameters query, String strJson);
	
	/**
	 * 批量删除用户信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject deleteUsersByIds(CommonParameters query, String strJson);
	/**
	 * 查询该字段下的用户个数
	 * @param emailString
	 * @return
	 */
	public long findUserCountByProperty(String emailString);
	
	/**
	 * 修改用户密码
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject updatePwdToAppCode(String strJson);
	
	/**
	 * 获取统一用户的token
	 * @param query
	 * @return
	 */
	public Map<String,Object> getAccessFromUnion(CommonParameters query);
	/**
	 * 获取指定应用的所有用户
	 * @param query
	 * @param access
	 * @return
	 */
	public Map<String,Object> getUnionUsersByAppid(CommonParameters query,String access);
	/**
	 * 将荔枝云上统一用户同步到云通联
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject syncUserByUnion(CommonParameters query, String strJson);

	/**
	 * 批量更新用户状态
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject updateUsersStatus(CommonParameters query, String strJson);
}
