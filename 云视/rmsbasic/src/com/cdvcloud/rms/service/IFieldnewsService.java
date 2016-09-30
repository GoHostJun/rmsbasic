package com.cdvcloud.rms.service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

/**
 * 通联业务接口层
 * @author mcxin
 *
 */
public interface IFieldnewsService {


	/**
	 * 获取通联列表
	 */
	public ResponseObject findFieldnewsAll(CommonParameters commonParameters, String strJson);
	
	/**
	 * 获取通联列表
	 */
	public ResponseObject findFieldnewsByUser(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据分享查询通联列表
	 */
	public ResponseObject findFieldnewsByShare(CommonParameters commonParameters, String strJson);
	

	/**
	 * 根据通联id获取通联
	 */
	public ResponseObject findFieldnewsById(CommonParameters commonParameters, String strJson);

	/**
	 * 根据通联id获取通联
	 */
	public ResponseObject addFieldnewsUser(CommonParameters commonParameters, String strJson);

	/**
	 * 取消新闻通联
	 */
	public ResponseObject cancelFieldnews(CommonParameters commonParameters, String strJson);

	/**
	 * 复制新闻通联
	 */
	public ResponseObject copyFieldnews(CommonParameters commonParameters, String strJson);

	/**
	 * 修改新闻通联
	 */
	public ResponseObject updateFieldnews(CommonParameters commonParameters, String strJson);

	/**
	 * 新闻通联添加素材
	 */
	public ResponseObject addFieldnewsMaterial(CommonParameters commonParameters, String strJson);
	
	/**
	 * 新闻通联更新素材
	 */
	public ResponseObject updateFieldnewsMaterial(CommonParameters commonParameters, String strJson);

	/**
	 * 新闻通联删除素材
	 */
	public ResponseObject deleteFieldnewsMaterial(CommonParameters commonParameters, String strJson);
	
	/**
	 * 新闻通联推送
	 */
	public ResponseObject sendFieldnews(CommonParameters commonParameters, String strJson);
	public ResponseObject top(CommonParameters commonParameters, String strJson);
	
	
	
}
