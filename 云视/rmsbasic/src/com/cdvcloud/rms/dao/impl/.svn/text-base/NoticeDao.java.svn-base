package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.INoticeDao;
import com.cdvcloud.rms.domain.Notice;

@Repository
public class NoticeDao extends BasicDao implements INoticeDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(Notice.NOTICE, _id);
	}
	
	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(Notice.NOTICE, _id);
	}

	@Override
	public String insertNotice(Map<String, Object> map) {
		return super.insert(Notice.NOTICE, map);
	}

	@Override
	public String insertDocNotice(Document document) {
		return super.insert(Notice.NOTICE, document);
	}

	@Override
	public List<Map<String, Object>> findNoticeAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(Notice.NOTICE, whereMap, currentPage, pageNum);
	}

	@Override
	public long countNotice(Map<String, Object> whereMap) {
		return super.count(Notice.NOTICE, whereMap);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(Notice.NOTICE, sortFilter, filter, currentPage, pageNum);
	}
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, Map<String, Object> queryMap,  int currentPage, int pageNum) {
		return super.find(Notice.NOTICE, sortFilter, filter,queryMap, currentPage, pageNum);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(Notice.NOTICE, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(Notice.NOTICE, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(Notice.NOTICE, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(Notice.NOTICE, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(Notice.NOTICE, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(Notice.NOTICE, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(Notice.NOTICE, filter);
	}

}
