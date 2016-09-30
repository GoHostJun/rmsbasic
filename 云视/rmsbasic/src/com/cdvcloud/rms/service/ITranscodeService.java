package com.cdvcloud.rms.service;

import java.util.List;
import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;

public interface ITranscodeService {
	/**
	 * 创建转码任务
	 * @param common       公共参数
	 * @param srcFileUrl   源文件地址
	 * @param fixedInfo    固定参数
	 * @param outputType   转码类型   0:预览  ，1:默认，2:终端
	 * @param output       转码模板数组 
	 * @param isSplit      是否切片
	 * @return
	 */
	public Map<String, Object> addTranscode(CommonParameters common,String srcFileUrl,
			String fixedInfo,String outputType,List<String> output,String isSplit,String historicalTaskId);
	/**
	 * 推送转码任务创建
	 * @param common
	 * @param srcFileUrl
	 * @param fixedInfo
	 * @param outputType
	 * @param output
	 * @param isSplit
	 * @param historicalTaskId
	 * @return
	 */
	public Map<String, Object> addPushTranscode(CommonParameters common,String srcFileUrl,
			String fixedInfo,String outputType,List<String> output,String isSplit);
	/**
	 * 自动转码流程
	 * @param common         公共参数
	 * @param namename       文件名称
	 * @param mtype			  文件类型
	 * @param filePath       文件地址
	 * @return
	 */
//	public String addTranscodeAutomatic(CommonParameters common,String name,String mtype,String filePath,String src,String remark,String taskid,String md5);
	public String addTranscodeAutomatic(Map<String, Object> map);
	/**
	 * 进度查询
	 * @param id
	 * @return
	 */
	public String TranscodeQuery(CommonParameters common,String id);
	/**
	 * 自动转码流程
	 * @param common         公共参数
	 * @param namename       文件名称
	 * @param mtype			  文件类型
	 * @param filePath       文件地址
	 * @return
	 */
	public boolean addTranscodeAutomatic(CommonParameters common,String name,String mtype,String filePath,String src,String remark,String taskid,String md5,String uploadUUID);
	
	public void taskTranscode(CommonParameters common,String mediaid ,String mtype,String srcFileUrl,String historicalTaskId);
	
	public void retryTask(CommonParameters common,String mediaid ,String mtype,String srcFileUrl,String historicalTaskId,String taskType); 
}
