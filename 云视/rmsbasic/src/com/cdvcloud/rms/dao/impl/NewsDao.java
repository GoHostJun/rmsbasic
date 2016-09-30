package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.domain.News;

@Repository
public class NewsDao extends BasicDao implements INewsDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(News.NEWS, _id);
	}
	
	@Override
	public Map<String, Object> findOne(String _id, Map<String, Object> queryMap) {
		return super.findOne(News.NEWS, _id, queryMap);
	}
	
	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(News.NEWS, _id);
	}

	@Override
	public String insertNews(Map<String, Object> map) {
		return super.insert(News.NEWS, map);
	}

	@Override
	public String insertDocNews(Document document) {
		return super.insert(News.NEWS, document);
	}

	@Override
	public List<Map<String, Object>> findNewAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(News.NEWS, whereMap, currentPage, pageNum);
	}

	@Override
	public long countNews(Map<String, Object> whereMap) {
		return super.count(News.NEWS, whereMap);
	}

	@Override
	public Pages findNewAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(News.NEWS, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(News.NEWS, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(News.NEWS, sortFilter, filter, currentPage, pageNum);
	}
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, Map<String, Object> queryMap, int currentPage, int pageNum) {
		return super.find(News.NEWS, sortFilter, filter,queryMap, currentPage, pageNum);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(News.NEWS, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(News.NEWS, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(News.NEWS, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(News.NEWS, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(News.NEWS, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(News.NEWS, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(News.NEWS, filter);
	}

}
