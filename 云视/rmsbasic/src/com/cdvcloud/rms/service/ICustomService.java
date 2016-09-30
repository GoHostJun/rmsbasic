package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface ICustomService {
	public ResponseObject getAllCustom(CommonParameters query,String strJson);
	/**
	 * 获取用户信息
	 * 
	 * @param mapJson
	 * @return
	 */
	public Map<String, Object> getUserInfo(Map<String, Object> mapJson);
	public ResponseObject addCustom(CommonParameters query, String strJson, Map<String, Object> sessionMap);
	public ResponseObject updateCustom(CommonParameters query, String strJson);
	public ResponseObject deleteCustomByIds(CommonParameters query, String strJson);
	public ResponseObject deleteCustomById(CommonParameters query, String strJson);
	public ResponseObject getCustomById(CommonParameters query, String strJson);
	public ResponseObject getConfiguration(CommonParameters query);
	public ResponseObject updateConfiguration(CommonParameters query, String strJson);
	public ResponseObject deleteConfiguration(CommonParameters query, String strJson);

}
