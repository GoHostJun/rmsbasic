package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.ISystemLogDao;
import com.cdvcloud.rms.domain.SystemLog;
import com.cdvcloud.rms.util.StringUtil;
@Repository
public class SystemLogDaoImpl extends BasicDao implements ISystemLogDao {

	@Override
	public String insert(Map<String, Object> map) {
		String id = super.insert(SystemLog.SYSTEMLOG, map);
		if(StringUtil.isEmpty(id)){
			return "";
		}
		return id;
	}

	@Override
	public Map<String, Object> queryOne(String id) {
		super.findOne(SystemLog.SYSTEMLOG, id);
		return null;
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> sortFilter, Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(SystemLog.SYSTEMLOG, sortFilter, whereMap, currentPage, pageNum);
	}

	@Override
	public long count(Map<String, Object> whereMap) {
		return super.count(SystemLog.SYSTEMLOG, whereMap);
	}

}
