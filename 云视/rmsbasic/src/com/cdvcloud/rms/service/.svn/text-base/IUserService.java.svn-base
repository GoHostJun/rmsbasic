package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

/**
 * 用户业务接口
 * 
 * @author huangaigang
 * 
 */
public interface IUserService {

	/**
	 * 获取用户信息
	 * 
	 * @param mapJson
	 * @return
	 */
	public Map<String, Object> getUserInfo(Map<String, Object> mapJson);
	
	/**
	 * 获取用户信息
	 * 
	 * @param mapJson
	 * @return
	 */
	public Map<String, Object> getUserInfoByName(Map<String, Object> mapJson);
	
	public Map<String,Object>getUserInforById(String id);
	
	/**
	 * 统一用户校验接口
	 * @param name 用户名
	 * @param password 密码
	 * @param mapUserRes 返回包含用户loginId，userId
	 * @return
	 */
	public Boolean validateUserInfo(String name, String password,Map<String, Object> mapUserRes);
	
	/**
	 * 获取用户积分
	 * @param commonParameters
	 * @param strJson
	 * @return
	 * @throws Exception 
	 */
	public ResponseObject queryIntegral(CommonParameters commonParameters, String strJson) throws Exception;
	
}
