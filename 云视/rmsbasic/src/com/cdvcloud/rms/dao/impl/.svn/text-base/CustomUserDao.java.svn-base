package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.ICustomUserDao;
import com.cdvcloud.rms.domain.CustomUsers;
@Repository
public class CustomUserDao extends BasicDao implements ICustomUserDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(CustomUsers.CUSTOMUSER, _id);
	}

	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(CustomUsers.CUSTOMUSER, _id);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(CustomUsers.CUSTOMUSER, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(CustomUsers.CUSTOMUSER, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(CustomUsers.CUSTOMUSER, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(CustomUsers.CUSTOMUSER, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(CustomUsers.CUSTOMUSER, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(CustomUsers.CUSTOMUSER, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(CustomUsers.CUSTOMUSER, whereMap);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(CustomUsers.CUSTOMUSER, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(CustomUsers.CUSTOMUSER, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(CustomUsers.CUSTOMUSER, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(CustomUsers.CUSTOMUSER, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(CustomUsers.CUSTOMUSER, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(CustomUsers.CUSTOMUSER, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(CustomUsers.CUSTOMUSER, filter);
	}

}
