package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.IDirectPassingPathDao;
import com.cdvcloud.rms.domain.DirectPassingPath;
import com.cdvcloud.rms.domain.PushSet;
@Repository
public class DirectPassingPathDaoImpl extends BasicDao implements IDirectPassingPathDao{

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(DirectPassingPath.DIRECTPASSINGPATH, _id);
	}

	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(DirectPassingPath.DIRECTPASSINGPATH, _id);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(DirectPassingPath.DIRECTPASSINGPATH, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(DirectPassingPath.DIRECTPASSINGPATH, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(DirectPassingPath.DIRECTPASSINGPATH, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(DirectPassingPath.DIRECTPASSINGPATH, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(DirectPassingPath.DIRECTPASSINGPATH, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(DirectPassingPath.DIRECTPASSINGPATH, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(DirectPassingPath.DIRECTPASSINGPATH, whereMap);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(DirectPassingPath.DIRECTPASSINGPATH, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(DirectPassingPath.DIRECTPASSINGPATH, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(DirectPassingPath.DIRECTPASSINGPATH, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(DirectPassingPath.DIRECTPASSINGPATH, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(DirectPassingPath.DIRECTPASSINGPATH, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(DirectPassingPath.DIRECTPASSINGPATH, filter, update);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(DirectPassingPath.DIRECTPASSINGPATH, filter);
	}

}
