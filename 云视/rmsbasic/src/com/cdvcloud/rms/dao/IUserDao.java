package com.cdvcloud.rms.dao;

import java.util.Map;

public interface IUserDao {
	/**
	 * 根据条件获取一条用户信息数据
	 * @param filter
	 * @return
	 */
	public Map<String, Object> findOne(Map<String, Object> filter);
	/**
	 * 根据id和条件获取一条用户信息数据
	 * @param filter
	 * @return
	 */
	public Map<String, Object> findOne(String id);
	/**
	 * 更新user根据条件更新
	 * @param filter
	 * @param update
	 * @return
	 */
	public long updateUserByset(Map<String,Object> filter,Map<String,Object>update);
}
