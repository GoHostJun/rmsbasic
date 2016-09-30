package com.cdvcloud.rms.dao.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.IStatisticDao;
import com.cdvcloud.rms.domain.NewsStatistic;
@Repository
public class StatisticDaoImpl extends BasicDao implements IStatisticDao {

	@Override
	public List<Map<String, Object>> GroupBy(String collectionName,String consumerid) {
		List<Document> group = Arrays.asList(
			    new Document("$group", new Document( "_id", 
			            new Document("areacode","$areacode").append("consumerid", "$consumerid"))
			            .append("count", new Document("$sum",1))), 
			    new Document("$sort", new Document("count", -1))
			);  
		return super.aggregate(collectionName, group);
	}

	@Override
	public String insertNewsStatistic(Map<String, Object> statistic) {
		return super.insert(NewsStatistic.NEWSSTATISTIC, statistic);
	}

	@Override
	public Map<String, Object> queryOne(String consumerid) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(NewsStatistic.COMPANYID, consumerid);
		Map<String, Object> sortFilter = new HashMap<String, Object>();
		sortFilter.put(NewsStatistic.CTIME, -1);
		List<Map<String, Object>> lists = super.find(NewsStatistic.NEWSSTATISTIC, sortFilter, filter, 1, 10);
		if(null!=lists&&lists.size()>0){
			return lists.get(0);
		}else{
			Map<String, Object> ret  =new HashMap<String, Object>();
			ret.put("statistic", Collections.EMPTY_LIST);
			return ret;
		}
	}

}
