package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;

public interface IMaterialTemplateService extends IBasicService{
	/**
	 * 根据对象id获取对象信息
	 */
	public Map<String, Object> findObject(CommonParameters commonParameters, Map<String, Object> whereMap);
}
