package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.ICustomPushDao;
import com.cdvcloud.rms.domain.CustomPush;
@Repository
public class CustomPushDao extends BasicDao implements ICustomPushDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(CustomPush.CUSTOMPUSH, _id);
	}

	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(CustomPush.CUSTOMPUSH, _id);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(CustomPush.CUSTOMPUSH, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(CustomPush.CUSTOMPUSH, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(CustomPush.CUSTOMPUSH, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(CustomPush.CUSTOMPUSH, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(CustomPush.CUSTOMPUSH, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(CustomPush.CUSTOMPUSH, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(CustomPush.CUSTOMPUSH, whereMap);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(CustomPush.CUSTOMPUSH, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(CustomPush.CUSTOMPUSH, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(CustomPush.CUSTOMPUSH, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(CustomPush.CUSTOMPUSH, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(CustomPush.CUSTOMPUSH, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(CustomPush.CUSTOMPUSH, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(CustomPush.CUSTOMPUSH, filter);
	}

}
