package com.cdvcloud.rms.service;

import java.util.Map;

public interface IPushSetService extends IBasicService {
	/**
	 * 创建对象
	 */
	public Map<String, Object> getPushAddress (Map<String, Object> pushMap);
	/**
	 * 获取推送地址
	 */
	public Map<String, Object> findOne(String type);
}
