package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.IMessageDao;
import com.cdvcloud.rms.domain.Message;

@Repository
public class MessageDao extends BasicDao implements IMessageDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(Message.MESSAGE, _id);
	}
	
	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(Message.MESSAGE, _id);
	}

	@Override
	public String insertMessage(Map<String, Object> map) {
		return super.insert(Message.MESSAGE, map);
	}

	@Override
	public String insertDocMessage(Document document) {
		return super.insert(Message.MESSAGE, document);
	}

	@Override
	public List<Map<String, Object>> findNewAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(Message.MESSAGE, whereMap, currentPage, pageNum);
	}

	@Override
	public long countMessage(Map<String, Object> whereMap) {
		return super.count(Message.MESSAGE, whereMap);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(Message.MESSAGE, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(Message.MESSAGE, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(Message.MESSAGE, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(Message.MESSAGE, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(Message.MESSAGE, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(Message.MESSAGE, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(Message.MESSAGE, filter, update, option);
	}

	@Override
	public Map<String, Object> findOne(Map<String, Object> filter) {
		return super.findOne(Message.MESSAGE, filter);
	}

}
