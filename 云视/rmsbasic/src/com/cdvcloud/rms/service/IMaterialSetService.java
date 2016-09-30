package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IMaterialSetService {
	/**
	 * 查询单条数据
	 * @param queryById
	 * @return
	 */
	public ResponseObject savematerialSet(CommonParameters common,String strJson);
	/**
	 * 
	 * @param queryById
	 * @return
	 */
	public ResponseObject findMediaList(CommonParameters queryById,String strJson);
	public ResponseObject queryByUser(CommonParameters queryByUser,String strJson);
	/**
	 * 查询媒体库
	 * @param query
	 * @return
	 */
	public ResponseObject findList(CommonParameters query,String json);
	public ResponseObject queryMediaList(CommonParameters query,String json);
	/**
	
	
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
	 * 素材集素材复制到素材
	 * @param update
	 * @param strJson
	 * @return
	 */
	public ResponseObject copyMaterialSet(CommonParameters update, String strJson);
	/**
	 * 修改缩略图
	 * @param queryById
	 * @param strJson
	 * @return
	 */
	public ResponseObject thumbnail(CommonParameters queryById,String strJson);
	
	/**
	 * 查询媒体库
	 * @param query
	 * @return
	 */
	public ResponseObject findList(Map<String, Object> mapUploadIds);
}
