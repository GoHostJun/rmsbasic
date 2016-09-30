package com.cdvcloud.rms.dao;

import java.util.List;
import java.util.Map;

public interface IStatisticDao {
	public String insertNewsStatistic(Map<String, Object> statistic);
	public Map<String, Object> queryOne(String consumerid);
	public List<Map<String, Object>> GroupBy(String collectionName,String consumerid);
}
