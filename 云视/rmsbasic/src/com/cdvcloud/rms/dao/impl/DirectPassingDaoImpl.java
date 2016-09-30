package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.IDirectPassingDao;
import com.cdvcloud.rms.domain.DirectPassing;

@Repository
public class DirectPassingDaoImpl extends BasicDao implements IDirectPassingDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(DirectPassing.DIRECTPASSING, _id);
	}
	
	@Override
	public Map<String, Object> findOne(String _id, Map<String, Object> queryMap) {
		return super.findOne(DirectPassing.DIRECTPASSING, _id, queryMap);
	}
	
	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(DirectPassing.DIRECTPASSING, _id);
	}



	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(DirectPassing.DIRECTPASSING, sortFilter, filter, currentPage, pageNum);
	}
	

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(DirectPassing.DIRECTPASSING, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(DirectPassing.DIRECTPASSING, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(DirectPassing.DIRECTPASSING, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(DirectPassing.DIRECTPASSING, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(DirectPassing.DIRECTPASSING, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(DirectPassing.DIRECTPASSING, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(DirectPassing.DIRECTPASSING, filter);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(DirectPassing.DIRECTPASSING, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(DirectPassing.DIRECTPASSING, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(DirectPassing.DIRECTPASSING, whereMap, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		// TODO Auto-generated method stub
		return (Pages) super.find(DirectPassing.DIRECTPASSING, sortMap, whereMap, pages.getCurrentPage(), pages.getPageNum());
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(DirectPassing.DIRECTPASSING, whereMap);
	}

}
