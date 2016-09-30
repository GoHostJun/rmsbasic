package com.cdvcloud.rms.dao;

import java.util.Map;

import org.bson.Document;

public interface ILoginDao {
	/**
	 * 根据条件获取一条数据
	 * @param filter
	 * @return
	 */
	public Document findOneDocument(Map<String, Object> filter);

}
