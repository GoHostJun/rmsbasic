package com.cdvcloud.rms.service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface ICommentService {

	/**
	 * 创建对象
	 */
	public ResponseObject createComment(CommonParameters commonParameters, String strJson);

	/**
	 * 获取对象列表
	 */
	public ResponseObject findCommentAll(CommonParameters commonParameters, String strJson);

	/**
	 * 获取评论对象列表
	 */
	public ResponseObject getCommentInfo(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据对象id获取对象信息
	 */
	public ResponseObject findCommentById(CommonParameters commonParameters, String strJson);

	/**
	 * 根据对象id删除对象
	 */
	public ResponseObject deleteComment(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据对象id更新对象
	 */
	public ResponseObject updateComment(CommonParameters commonParameters, String strJson);

}
