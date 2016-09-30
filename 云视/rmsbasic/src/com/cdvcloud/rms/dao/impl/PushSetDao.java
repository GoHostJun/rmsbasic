package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.IPushSetDao;
import com.cdvcloud.rms.domain.PushSet;
@Repository
public class PushSetDao extends BasicDao implements IPushSetDao {

	@Override
	public Map<String, Object> findOne(Map<String, Object> filter) {
		return super.findOne(PushSet.PUSHSET, filter);
	}

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(PushSet.PUSHSET, _id);
	}

	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(PushSet.PUSHSET, _id);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(PushSet.PUSHSET, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(PushSet.PUSHSET, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(PushSet.PUSHSET, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(PushSet.PUSHSET, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(PushSet.PUSHSET, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(PushSet.PUSHSET, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(PushSet.PUSHSET, whereMap);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(PushSet.PUSHSET, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(PushSet.PUSHSET, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(PushSet.PUSHSET, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(PushSet.PUSHSET, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(PushSet.PUSHSET, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(PushSet.PUSHSET, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(PushSet.PUSHSET, filter);
	}

}
