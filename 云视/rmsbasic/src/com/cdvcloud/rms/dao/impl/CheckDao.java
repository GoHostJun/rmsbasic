package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.ICheckDao;
import com.cdvcloud.rms.domain.Checks;

@Repository
public class CheckDao extends BasicDao implements ICheckDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(Checks.CHECKS, _id);
	}

	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(Checks.CHECKS, _id);
	}

	@Override
	public String insertCheck(Map<String, Object> map) {
		return super.insert(Checks.CHECKS, map);
	}

	@Override
	public String insertDocCheck(Document document) {
		return super.insert(Checks.CHECKS, document);
	}

	@Override
	public List<Map<String, Object>> findCheckAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(Checks.CHECKS, whereMap, currentPage, pageNum);
	}

	@Override
	public long countCheck(Map<String, Object> whereMap) {
		return super.count(Checks.CHECKS, whereMap);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(Checks.CHECKS, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(Checks.CHECKS, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(Checks.CHECKS, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(Checks.CHECKS, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(Checks.CHECKS, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(Checks.CHECKS, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(Checks.CHECKS, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(Checks.CHECKS, filter);
	}

}
