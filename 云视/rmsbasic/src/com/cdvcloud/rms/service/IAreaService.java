package com.cdvcloud.rms.service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

/**
 * 用户业务接口
 * 
 * @author huangaigang
 * 
 */
public interface IAreaService {

	/**
	 * 添加地区到指定应用
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject addAreaToAppCode(CommonParameters query, String strJson);
	
	/**
	 * 获取指定应用的地区信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getAreasByAppCode(CommonParameters query, String strJson);
	
	public ResponseObject deleteAreaToAppCode(CommonParameters query, String strJson);
	
	/**
	 * 获取指定地区的详细信息
	 * 
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject getAreaInfoByAreaId(CommonParameters query, String strJson);
	
	public ResponseObject updateAreaToAppCode(CommonParameters query, String strJson);

}
