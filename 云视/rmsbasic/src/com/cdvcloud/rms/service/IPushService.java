package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;

public interface IPushService {

	/**
	 * 推送newsphere
	 * @param newsMap
	 * @param commonParameters
	 * @return
	 */
	public boolean sendToNewsphere(Map<String, Object> newsMap, CommonParameters commonParameters);
	
	/**
	 * 推送newsphere江苏特别实现接口
	 * @param newsMap
	 * @param commonParameters
	 * @return
	 */
	public boolean sendToJSNewsphere(Map<String, Object> newsMap, CommonParameters commonParameters);
	
	/**
	 * 直传推送newsphere江苏特别实现接口
	 * @param newsMap
	 * @param commonParameters
	 * @return
	 */
	public boolean sendToJSDirectNewsphere(String mediaid,  CommonParameters commonParameters);
	
	/**
	 * 推送城市频道
	 * @param newsMap
	 * @param commonParameters
	 * @return
	 */
	public boolean sendToJSCityChannel(Map<String, Object> newsMap, CommonParameters commonParameters);
	
	/**
	 * 推送索贝内容库
	 * @param newsMap
	 * @param commonParameters
	 * @return
	 */
	public boolean sendToJSSBContent(Map<String, Object> newsMap, CommonParameters commonParameters);
}
