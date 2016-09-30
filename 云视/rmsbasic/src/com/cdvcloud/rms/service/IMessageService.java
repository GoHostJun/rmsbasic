package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IMessageService {

	/**
	 * 创建对象
	 */
	public ResponseObject createMessage(CommonParameters commonParameters, String strJson);

	/**
	 * 获取对象列表
	 */
	public ResponseObject findMessageAll(CommonParameters commonParameters, String strJson);
	/**
	 * 新闻外场定制消息
	 * @param commonParameters
	 * @param strJson
	 * @return
	 */
	public ResponseObject findFieldNewsMessages(CommonParameters commonParameters, String strJson);

	/**
	 * 根据对象id获取对象信息
	 */
	public ResponseObject findMessageById(CommonParameters commonParameters, String strJson);

	/**
	 * 根据对象id更新对象
	 */
	public ResponseObject updateMessage(CommonParameters commonParameters, String strJson);
	
	/**
	 * 根据通联id更新对象
	 */
	public ResponseObject updateMessageByNewsId(CommonParameters commonParameters, String strJson);

	/**
	 * 创建消息对象
	 */
	public boolean createMessageObj(Map<String, Object> msgMap);

	/**
	 * 根据通联id更新对象
	 */
	public ResponseObject updateBtachNewsStatus(CommonParameters commonParameters, String strJson);
}
