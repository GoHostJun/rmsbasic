package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IMediaService {
	/**
	 * 查询单条数据
	 * @param queryById
	 * @return
	 */
	public ResponseObject findOne(CommonParameters queryById,String strJson);
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
	public ResponseObject registerResource(CommonParameters insert,String strJson,String ytpe);
	/**
	 * 创建媒体素材
	 * @param insert
	 * @return
	 */
	public ResponseObject scanRegistration(CommonParameters insert,String strJson);
	/**
	 * 创建媒体素材
	 * @param insert
	 * @return
	 */
	public ResponseObject registerResource(CommonParameters insert,String mtype,String fileDepositUrl,String name,String remark);
	
	/**
	 * 创建媒体素材
	 * @param insert
	 * @return
	 */
	public ResponseObject registerResource(CommonParameters insert,String mtype,String fileDepositUrl,String name,String remark,String uploadUUID);
	/**
	 * 添加素材
	 * @param map
	 * @return
	 */
	public String inset(Map<String, Object> map);
	/**
	 * 添加媒体
	 * @param map
	 * @return
	 */
	public ResponseObject insetMedia(CommonParameters common,String fileName,String fileSize,String file);
	
	public ResponseObject insetHttpMedia(CommonParameters common,String json);
	/**
	 * 修改素材
	 * @param whereMap
	 * @param setMap
	 * @return
	 */
	public ResponseObject update(CommonParameters update,String strJson);
	/**
	 * 删除多个素材
	 * @param id
	 * @return
	 */
	public ResponseObject deleteList(CommonParameters update, String strJson);
	/**
	 * 放入多个素材到回收站
	 * @param id
	 * @return
	 */
	public ResponseObject recycleList(CommonParameters update, String strJson);
	/**
	 * 共享多个
	 * @param id
	 * @return
	 */
	public ResponseObject sharedList(CommonParameters update, String strJson);
	/**
	 * 统计素材
	 * @param count
	 * @param strJson
	 * @return
	 */
	public ResponseObject count(CommonParameters count,String strJson);
	public ResponseObject statistics(CommonParameters count,String strJson);
	
	/**
	 * 查询媒体库
	 * @param query
	 * @return
	 */
	public ResponseObject findList(Map<String, Object> mapUploadIds);
	/**
	 * 查询进度
	 * @param query
	 * @return
	 */
	public ResponseObject queryProgress(CommonParameters common,String json);
}
