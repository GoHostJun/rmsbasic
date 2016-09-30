package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.IWeChatMessageDao;
import com.cdvcloud.rms.domain.Scan;
import com.cdvcloud.rms.domain.WeChatMessage;
@Component
public class WeChatMessageDaoImpl  extends BasicDao implements IWeChatMessageDao {

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(WeChatMessage.WECHATMESSAGE, _id);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(WeChatMessage.WECHATMESSAGE, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(WeChatMessage.WECHATMESSAGE, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(WeChatMessage.WECHATMESSAGE, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(WeChatMessage.WECHATMESSAGE, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(Scan.SCAN, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(WeChatMessage.WECHATMESSAGE, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(WeChatMessage.WECHATMESSAGE, whereMap);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(WeChatMessage.WECHATMESSAGE, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(WeChatMessage.WECHATMESSAGE, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(WeChatMessage.WECHATMESSAGE, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(WeChatMessage.WECHATMESSAGE, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(WeChatMessage.WECHATMESSAGE, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super. updateOneBySet(WeChatMessage.WECHATMESSAGE, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(WeChatMessage.WECHATMESSAGE, filter);
	}

}
