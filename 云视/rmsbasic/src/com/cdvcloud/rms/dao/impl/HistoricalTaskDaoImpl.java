package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.IHistoricalTaskDao;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.util.StringUtil;
@Repository
public class HistoricalTaskDaoImpl extends BasicDao implements IHistoricalTaskDao {

	@Override
	public String insert(Map<String, Object> map) {
		String id = super.insert(HistoricalTask.HISTORICALTASK, map);
		if(StringUtil.isEmpty(id)){
			return "";
		}
		return id;
	}

	@Override
	public Map<String, Object> queryOne(String id) {
		return super.findOne(HistoricalTask.HISTORICALTASK, id);
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> sortFilter, Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(HistoricalTask.HISTORICALTASK, sortFilter, whereMap, currentPage, pageNum);
	}

	@Override
	public long count(Map<String, Object> whereMap) {
		return super.count(HistoricalTask.HISTORICALTASK, whereMap);
	}

	@Override
	public long update(Map<String, Object> whereMap, Map<String, Object> setMap) {
		return super.updateOneBySet(HistoricalTask.HISTORICALTASK, whereMap, setMap);
	}

}
