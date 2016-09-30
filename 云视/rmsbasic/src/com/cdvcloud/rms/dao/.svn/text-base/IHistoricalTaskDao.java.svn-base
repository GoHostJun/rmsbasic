package com.cdvcloud.rms.dao;

import java.util.List;
import java.util.Map;

public interface IHistoricalTaskDao {
	public String insert(Map<String, Object> map);
	public long update(Map<String, Object> whereMap,Map<String, Object> setMap);
	
	public Map<String, Object> queryOne(String id);
	
	public List<Map<String, Object>> query(Map<String, Object> sortFilter,Map<String, Object> whereMap,int currentPage,int pageNum);
	
	public long  count(Map<String, Object> whereMap);
}
