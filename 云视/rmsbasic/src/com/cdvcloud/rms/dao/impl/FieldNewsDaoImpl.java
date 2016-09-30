package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.IFieldNewsDao;
import com.cdvcloud.rms.domain.FieldNews;

@Repository
public class FieldNewsDaoImpl extends BasicDao implements IFieldNewsDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(FieldNews.FIELDNEWS, _id);
	}
	
	@Override
	public Map<String, Object> findOne(String _id, Map<String, Object> queryMap) {
		return super.findOne(FieldNews.FIELDNEWS, _id, queryMap);
	}
	
	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(FieldNews.FIELDNEWS, _id);
	}

	@Override
	public String insertNews(Map<String, Object> map) {
		return super.insert(FieldNews.FIELDNEWS, map);
	}

	@Override
	public String insertDocNews(Document document) {
		return super.insert(FieldNews.FIELDNEWS, document);
	}

	@Override
	public List<Map<String, Object>> findNewAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(FieldNews.FIELDNEWS, whereMap, currentPage, pageNum);
	}

	@Override
	public long countNews(Map<String, Object> whereMap) {
		return super.count(FieldNews.FIELDNEWS, whereMap);
	}

	@Override
	public Pages findNewAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(FieldNews.FIELDNEWS, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(FieldNews.FIELDNEWS, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(FieldNews.FIELDNEWS, sortFilter, filter, currentPage, pageNum);
	}
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, Map<String, Object> queryMap, int currentPage, int pageNum) {
		return super.find(FieldNews.FIELDNEWS, sortFilter, filter,queryMap, currentPage, pageNum);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(FieldNews.FIELDNEWS, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(FieldNews.FIELDNEWS, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(FieldNews.FIELDNEWS, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(FieldNews.FIELDNEWS, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(FieldNews.FIELDNEWS, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(FieldNews.FIELDNEWS, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(FieldNews.FIELDNEWS, filter);
	}

}
