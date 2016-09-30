package com.cdvcloud.rms.dao;


import java.util.List;
import java.util.Map;



public interface ITaskDao {
	/**
	 * 创建任务
	 * @param map
	 * @return
	 */
	public String insertTask(Map<String, Object> map);
	
	public Map<String, Object> queryOne(String id);
	
	public Map<String, Object> query(Map<String, Object> whereMap);
	public List<Map<String, Object>> queryList(Map<String, Object> whereMap);
	public long  update(Map<String, Object> whereMap,Map<String, Object> set);
	
	public long  updateOne(String id,Map<String, Object> set);
	public long  updateOne(String id,Map<String, Object> set,boolean option);
	public long delete(Map<String, Object> filter);
}
