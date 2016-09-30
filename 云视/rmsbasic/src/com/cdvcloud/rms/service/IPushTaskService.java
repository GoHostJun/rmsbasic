package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IPushTaskService {
	/**
	 * 查询媒体库
	 * @param query
	 * @return
	 */
	public ResponseObject findList(CommonParameters query,String json);
	
	/**
	 * 创建媒体素材
	 * @param insert
	 * @return
	 */
	public ResponseObject insert(Map<String, Object> map);
	/**
	 * 验证推送，加创建推送任务列表
	 * @param insert
	 * @return
	 */
	public ResponseObject pushVerify(CommonParameters commonParameters, String strJson);
	
	
	/**
	 * 查询进度
	 * @param query
	 * @return
	 */
	public ResponseObject queryProgress(CommonParameters common,String json);
}
