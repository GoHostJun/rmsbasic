package com.cdvcloud.rms.service;


import java.util.List;
import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IPresentationService {

	/**
	 * 统计文稿
	 * @param count
	 * @param strJson
	 * @return
	 */
	public ResponseObject count(CommonParameters count,String strJson);
	public ResponseObject statistics(CommonParameters count,String strJson);
	/**
	 * 创建文稿
	 * @param count
	 * @param strJson
	 * @return
	 */
	public ResponseObject insert(CommonParameters insert,String strJson);
	/**
	 * 修改文稿
	 * @param update
	 * @param strJson
	 * @return
	 */
	public ResponseObject update(CommonParameters update,String strJson);
	public ResponseObject addMedia(CommonParameters update,String strJson);
	
	/**
	 * 修改从通联到文稿的方法，只在云通联里使用
	 * @param update
	 * @param strJson
	 * @return
	 */
	public boolean updateNewsToPresentation(String id,Map<String, Object> setMap);

	public ResponseObject delete(CommonParameters update,String strJson);
	public boolean delete(String id);
	
	public ResponseObject shared(CommonParameters update,String strJson);
	/**
	 * 查询文稿
	 * @param query
	 * @param strJson
	 * @return
	 */
	public ResponseObject query(CommonParameters query,String strJson);
	/**
	 * 按id查询文稿
	 * @param queryById
	 * @param strJson
	 * @return
	 */
	public ResponseObject queryById(CommonParameters queryById,String strJson);
	/**
	 * 修改缩略图
	 * @param queryById
	 * @param strJson
	 * @return
	 */
	public ResponseObject thumbnail(CommonParameters queryById,String strJson);
	/**
	 * 追加媒体编目主键
	 * @param mediaids      媒体
	 * @param meaterialids  文稿/编目
	 * @return
	 */
	public void addMediaToCataids(List<String> mediaids,List<String> meaterialids);
	/**
	 * 删除媒体编目主键
	 * @param mediaids
	 * @param meaterialids
	 * @return
	 */
	public void delMediaToCataids(List<String> mediaids,List<String> meaterialids);
	
	/**
	 * 推送到INEWS
	 * @param queryById
	 * @param strJson
	 * @return
	 */
	public ResponseObject pushINEWS(CommonParameters queryById,String strJson);
	/**
	 * 推送到汇聚
	 * @param queryById
	 * @param strJson
	 * @return
	 */
	public ResponseObject pushConverge(CommonParameters queryById,String strJson);
	/**
	 * 复制到外场新闻表
	 * @param copyById
	 * @param strJson
	 * @return
	 */
	public ResponseObject copyToFieldNews(CommonParameters copyById,String strJson);
}
