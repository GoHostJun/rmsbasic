package com.cdvcloud.rms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.IPresentationDao;
import com.cdvcloud.rms.domain.Catalogue;

@Repository
public class PresentationDaoImpl extends BasicDao implements IPresentationDao {

	@Override
	public String insertMaterial(Map<String, Object> document) {
		return super.insert(Catalogue.CATALOGUE, document);
	}

	@Override
	public Map<String, Object> queryOne(String id) {
		return super.findOne(Catalogue.CATALOGUE, id);
	}
	@Override
	public Document queryOneDocument(String id) {
		return super.findOneDocument(Catalogue.CATALOGUE, id);
	}
	@Override
	public List<Map<String, Object>> query(Map<String, Object> sortFilter,Map<String, Object>  whereMap, int currentPage, int pageNum) {
		return super.find(Catalogue.CATALOGUE,sortFilter, whereMap, currentPage, pageNum);
	}
	
	@Override
	public List<Map<String, Object>> query(Map<String, Object> sortFilter,Map<String, Object>  whereMap,Map<String, Object> backMap, int currentPage, int pageNum) {
		return super.find(Catalogue.CATALOGUE,sortFilter, whereMap,backMap, currentPage, pageNum);
	}

	@Override
	public long update(Map<String, Object> whereMap, Map<String, Object> set) {
		return super.updateMany(Catalogue.CATALOGUE, whereMap, set);
	}
	@Override
	public long updateBySet(Map<String, Object> whereMap, Map<String, Object> set) {
		return super.updateManyBySet(Catalogue.CATALOGUE, whereMap, set);
	}
	@Override
	public long updateOne(String id, Map<String, Object> set, boolean option) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id", new ObjectId(id));
		Map<String, Object> db = new HashMap<String, Object>();
		db.put("$set", set);
		return super.updateOne(Catalogue.CATALOGUE, filter, db,option);
	}

	@Override
	public long count(Map<String, Object> filter) {
		return super.count(Catalogue.CATALOGUE, filter);
	}

	@Override
	public long delete(Map<String, Object> whereMap) {
		return super.deleteMany(Catalogue.CATALOGUE, whereMap);
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> whereMap) {
		return super.find(Catalogue.CATALOGUE, whereMap, 1, 100);
	}
}
