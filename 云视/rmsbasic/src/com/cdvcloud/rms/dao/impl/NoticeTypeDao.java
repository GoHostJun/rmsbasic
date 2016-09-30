package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.INoticeTypeDao;
import com.cdvcloud.rms.domain.NoticeType;

@Repository
public class NoticeTypeDao extends BasicDao implements INoticeTypeDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(NoticeType.NOTICETYPE, _id);
	}
	
	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(NoticeType.NOTICETYPE, _id);
	}

	@Override
	public String insertNoticeType(Map<String, Object> map) {
		return super.insert(NoticeType.NOTICETYPE, map);
	}

	@Override
	public String insertDocNoticeType(Document document) {
		return super.insert(NoticeType.NOTICETYPE, document);
	}

	@Override
	public List<Map<String, Object>> findNoticeTypeAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(NoticeType.NOTICETYPE, whereMap, currentPage, pageNum);
	}

	@Override
	public long countNoticeType(Map<String, Object> whereMap) {
		return super.count(NoticeType.NOTICETYPE, whereMap);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(NoticeType.NOTICETYPE, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(NoticeType.NOTICETYPE, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(NoticeType.NOTICETYPE, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(NoticeType.NOTICETYPE, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(NoticeType.NOTICETYPE, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(NoticeType.NOTICETYPE, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(NoticeType.NOTICETYPE, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(NoticeType.NOTICETYPE, filter);
	}

}
