package com.cdvcloud.rms.service;

public interface ICallBackService {
	/**
	 * 处理回调截图
	 * @param taskId
	 * @param status
	 * @param pointscreen
	 */
	public void manageScreenshot(String taskId,String status,String pointscreen);
	/**
	 * 处理回调转码
	 * @param taskId
	 * @param status
	 * @param fixedInfo
	 */
	public void manageTranscode(String taskId,String status,String fixedInfo);
	/**
	 * 处理推送转码回调
	 * @param taskId
	 * @param status
	 * @param fixedInfo
	 */
	public void managePushTranscode(String taskId,String status,String fixedInfo);
	/**
	 *  处理回调快编
	 * @param taskId
	 * @param status
	 * @param fixedInfo
	 */
	public void manageFastedit(String taskId,String status,String fixedInfo);
}
