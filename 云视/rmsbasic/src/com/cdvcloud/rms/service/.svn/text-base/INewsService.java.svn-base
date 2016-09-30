package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

/**
 * 通联业务接口层
 * 
 * @author huangaigang
 * 
 */
public interface INewsService {

	/**
	 * 创建新闻通联
	 */
	public ResponseObject createNews(CommonParameters commonParameters, String strJson);
	/**
	 * 创建服务
	 * @param commonParameters
	 * @param strJson
	 * @return
	 */
	public ResponseObject createService(CommonParameters commonParameters, String strJson);

	/**
	 * 获取通联列表
	 */
	public ResponseObject findNewsAll(CommonParameters commonParameters, String strJson);
	
	/**
	 * 获取通联列表
	 */
	public ResponseObject findNewsByUser(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据分享查询通联列表
	 */
	public ResponseObject findNewsByShare(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据点击量查询通联列表
	 */
	public ResponseObject findNewsByCount(CommonParameters commonParameters, String strJson);

	/**
	 * 根据通联id获取通联
	 */
	public ResponseObject findNewsById(CommonParameters commonParameters, String strJson);

	/**
	 * 根据通联id获取通联
	 */
	public ResponseObject addNewsUser(CommonParameters commonParameters, String strJson);

	/**
	 * 取消新闻通联
	 */
	public ResponseObject cancelNews(CommonParameters commonParameters, String strJson);

	/**
	 * 复制新闻通联
	 */
	public ResponseObject copyNews(CommonParameters commonParameters, String strJson);

	/**
	 * 修改新闻通联
	 */
	public ResponseObject updateNews(CommonParameters commonParameters, String strJson);

	/**
	 * 新闻通联添加素材
	 */
	public ResponseObject addNewsMaterial(CommonParameters commonParameters, String strJson);
	
	/**
	 * 新闻通联更新素材
	 */
	public ResponseObject updateNewsMaterial(CommonParameters commonParameters, String strJson);

	/**
	 * 新闻通联删除素材
	 */
	public ResponseObject deleteNewsMaterial(CommonParameters commonParameters, String strJson);
	
	/**
	 * 新闻通联推送
	 */
	public ResponseObject sendNews(CommonParameters commonParameters, String strJson);
	
	/**
	 * 统计用户相关通联数量
	 */
	public ResponseObject countNewsByUser(CommonParameters commonParameters, String strJson);
	
	/**
	 * 统计用户相关通联代办数量
	 */
	public ResponseObject unDealNewsByUser(CommonParameters commonParameters, String strJson);
	
	/**
	 * 新闻通联推送到网台
	 */
	public ResponseObject sendNewsToNetStation(CommonParameters commonParameters, String strJson);
	
	/**
	 * 新闻推送到NS
	 */
	public boolean sendToNewsphere(Map<String, Object> mapNews,CommonParameters commonParameters);
	/**
	 * 推送到汇聚
	 * @param commonParameters
	 * @param strJson
	 * @return
	 */
	public boolean sendConverge(Map<String, Object> mapNews,CommonParameters commonParameters);
	/**
	 * 推送到汇聚
	 * @param commonParameters
	 * @param strJson
	 * @return
	 */
	public boolean sendConverge2(Map<String, Object> mapNews,CommonParameters commonParameters);
	
	/**
	 * 新闻通联推送
	 */
	public ResponseObject pushNews(CommonParameters commonParameters, String strJson);
	/**
	 * 新闻通联推送-2016-09-22(增加待推送任务id) 
	 */
	public ResponseObject pushNews(CommonParameters commonParameters, String strJson,String pushTaskId);
	
	/**
	 * 通联取消分享
	 */
	public ResponseObject unshareNews(CommonParameters commonParameters, String strJson);
}
