package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.IWeChatTemplateDao;
import com.cdvcloud.rms.domain.Scan;
import com.cdvcloud.rms.domain.WechatTemplate;
@Component
public class WeChatTemplateDaoImpl extends BasicDao implements IWeChatTemplateDao{

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(WechatTemplate.WECHATTEMPLATE, _id);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(WechatTemplate.WECHATTEMPLATE, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(WechatTemplate.WECHATTEMPLATE, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(WechatTemplate.WECHATTEMPLATE, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(WechatTemplate.WECHATTEMPLATE, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(Scan.SCAN, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(WechatTemplate.WECHATTEMPLATE, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(WechatTemplate.WECHATTEMPLATE, whereMap);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(WechatTemplate.WECHATTEMPLATE, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(WechatTemplate.WECHATTEMPLATE, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(WechatTemplate.WECHATTEMPLATE, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(WechatTemplate.WECHATTEMPLATE, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(WechatTemplate.WECHATTEMPLATE, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super. updateOneBySet(WechatTemplate.WECHATTEMPLATE, filter, update, option);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(WechatTemplate.WECHATTEMPLATE, filter);
	}

}
