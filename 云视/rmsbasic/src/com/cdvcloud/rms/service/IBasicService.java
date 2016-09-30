package com.cdvcloud.rms.service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IBasicService {

	/**
	 * 创建对象
	 */
	public ResponseObject createObject(CommonParameters commonParameters, String strJson);

	/**
	 * 获取对象列表
	 */
	public ResponseObject findObjectAll(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据对象id获取对象信息
	 */
	public ResponseObject findObjectById(CommonParameters commonParameters, String strJson);

	/**
	 * 根据对象id删除对象
	 */
	public ResponseObject deleteObject(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据对象id更新对象
	 */
	public ResponseObject updateObject(CommonParameters commonParameters, String strJson);

}
