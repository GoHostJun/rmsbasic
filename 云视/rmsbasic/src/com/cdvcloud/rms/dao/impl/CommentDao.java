package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.ICommentDao;
import com.cdvcloud.rms.domain.Comment;

@Repository
public class CommentDao extends BasicDao implements ICommentDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(Comment.COMMENT, _id);
	}

	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(Comment.COMMENT, _id);
	}

	@Override
	public String insertNews(Map<String, Object> map) {
		return super.insert(Comment.COMMENT, map);
	}

	@Override
	public String insertDocNews(Document document) {
		return super.insert(Comment.COMMENT, document);
	}

	@Override
	public List<Map<String, Object>> findNewAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(Comment.COMMENT, whereMap, currentPage, pageNum);
	}

	@Override
	public long countComment(Map<String, Object> whereMap) {
		return super.count(Comment.COMMENT, whereMap);
	}

	@Override
	public Pages findNewAll(Map<String, Object> whereMap, Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(Comment.COMMENT, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(Comment.COMMENT, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(Comment.COMMENT, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(Comment.COMMENT, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(Comment.COMMENT, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(Comment.COMMENT, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(Comment.COMMENT, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(Comment.COMMENT, filter);
	}

}
