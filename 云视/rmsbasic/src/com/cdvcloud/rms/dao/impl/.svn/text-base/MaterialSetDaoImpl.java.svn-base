package com.cdvcloud.rms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.MaterialSetDao;
import com.cdvcloud.rms.domain.MaterialSet;
@Repository
public class MaterialSetDaoImpl extends BasicDao implements MaterialSetDao {

	@Override
	public String insertMedia(Map<String, Object> map) {
		return super.insert(MaterialSet.MATERIAL,map);
	}

	@Override
	public Map<String, Object> queryOne(String id) {
		return super.findOne(MaterialSet.MATERIAL, id);
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> sortFilter,Map<String, Object> whereMap,int currentPage,int pageNum) {
		return super.find(MaterialSet.MATERIAL,sortFilter, whereMap, currentPage, pageNum);
	}
	
	@Override
	public List<Map<String, Object>> query(Map<String, Object> sortFilter,Map<String, Object> whereMap,Map<String, Object> backMap,int currentPage,int pageNum) {
		return super.find(MaterialSet.MATERIAL,sortFilter, whereMap, backMap, currentPage, pageNum);
	}

	@Override
	public long updateBySet(Map<String, Object> whereMap, Map<String, Object> set) {
		return super.updateManyBySet(MaterialSet.MATERIAL, whereMap, set);
	}
	@Override
	public long update(Map<String, Object> whereMap, Map<String, Object> set) {
		return super.updateMany(MaterialSet.MATERIAL, whereMap, set);
	}

	@Override
	public long updateOne(String id, Map<String, Object> set,boolean option) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id", new ObjectId(id));
		Map<String, Object> db = new HashMap<String, Object>();
		db.put("$set", set);
		return super.updateOne(MaterialSet.MATERIAL, filter, db,option);
	}

	@Override
	public long count(Map<String, Object> set) {
		return super.count(MaterialSet.MATERIAL, set);
	}

	@Override
	public long delete(Map<String, Object> whereMap) {
		return super.deleteMany(MaterialSet.MATERIAL, whereMap);
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> whereMap) {
		return super.find(MaterialSet.MATERIAL, whereMap, 1, 100);
	}

}
