package com.cdvcloud.rms.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cdvcloud.rms.common.CommonParameters;

/**
 * 下载接口
 * 
 * @author TYW
 * 
 */
public interface DownloadService {

	/**
	 * 下载单个文件
	 * 
	 * @param request
	 * @param response
	 * @param commonParameters
	 * @param strId
	 */
	public void download(HttpServletRequest request, HttpServletResponse response, CommonParameters commonParameters, String strId);

	/**
	 * 打包下载
	 * 
	 * @param request
	 * @param response
	 * @param commonParameters
	 * @param strId
	 */
	public void download4zip(HttpServletRequest request, HttpServletResponse response, CommonParameters commonParameters, String strId);
	/**
	 * 素材打包下载
	 * 
	 * @param request
	 * @param response
	 * @param commonParameters
	 * @param strId
	 */
	public void downLoadMaterialZip(HttpServletRequest request, HttpServletResponse response, CommonParameters commonParameters, String strId,String type);
	
	/**
	 * 文稿生成xml并上传oss
	 * @param newsMap
	 * @return 返回xml上传oss后的地址
	 */
	public Map<String, Object> uploadXMLContent(Map<String, Object> newsMap, CommonParameters commonParameters);
}
