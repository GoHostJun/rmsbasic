package com.cdvcloud.rms.dao;

import java.util.Map;

public interface IPushSetDao extends IBasicDao {
	/**
	 * 根据条件获取一条推送信息数据
	 * @param filter
	 * @return
	 */
	public Map<String, Object> findOne(Map<String, Object> filter);

}
