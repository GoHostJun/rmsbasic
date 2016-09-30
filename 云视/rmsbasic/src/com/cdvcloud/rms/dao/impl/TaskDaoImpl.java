package com.cdvcloud.rms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;


import com.cdvcloud.rms.dao.ITaskDao;
import com.cdvcloud.rms.domain.Task;
import com.cdvcloud.rms.util.StringUtil;
@Repository
public class TaskDaoImpl extends BasicDao implements ITaskDao {

	@Override
	public String insertTask(Map<String, Object> map) {
		String id = super.insert(Task.TASK, map);
		if(StringUtil.isEmpty(id)){
			return "";
		}
		return id;
	}

	@Override
	public Map<String, Object> queryOne(String id) {
		return super.findOne(Task.TASK, id);
	}

	@Override
	public long update(Map<String, Object> whereMap, Map<String, Object> set) {
		return super.updateMany(Task.TASK, whereMap, set);
	}

	@Override
	public long updateOne(String id, Map<String, Object> set) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id", new ObjectId(id));
		Map<String, Object> db = new HashMap<String, Object>();
		db.put("$set", set);
		return super.updateOne(Task.TASK, filter, db);
	}

	@Override
	public Map<String, Object> query(Map<String, Object> whereMap) {
		return super.findOne(Task.TASK, whereMap);
	}

	@Override
	public long updateOne(String id, Map<String, Object> set, boolean option) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id", new ObjectId(id));
		Map<String, Object> db = new HashMap<String, Object>();
		db.put("$set", set);
		return super.updateOne(Task.TASK,filter, db,option);
	}

	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> whereMap) {
		return super.find(Task.TASK, whereMap, 1, 100);
	}

	@Override
	public long delete(Map<String, Object> filter) {
		return super.deleteOne(Task.TASK, filter);
	}

}
