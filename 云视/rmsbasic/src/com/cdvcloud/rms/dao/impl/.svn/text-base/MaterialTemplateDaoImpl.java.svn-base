package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.dao.IDirectPassingPathDao;
import com.cdvcloud.rms.dao.IMaterialTemplateDao;
import com.cdvcloud.rms.domain.DirectPassingPath;
import com.cdvcloud.rms.domain.MaterialTemplate;
import com.cdvcloud.rms.domain.PushSet;
import com.cdvcloud.rms.service.IMaterialTemplateService;
@Repository
public class MaterialTemplateDaoImpl extends BasicDao implements IMaterialTemplateDao{

	@Override
	public Map<String, Object> findOne(String _id) {
		return super.findOne(MaterialTemplate.MATERIALTEMPLATE, _id);
	}

	@Override
	public Document findOneDocument(String _id) {
		return super.findOneDocument(MaterialTemplate.MATERIALTEMPLATE, _id);
	}

	@Override
	public String insertObject(Map<String, Object> map) {
		return super.insert(MaterialTemplate.MATERIALTEMPLATE, map);
	}

	@Override
	public String insertDocObject(Document doc) {
		return super.insert(MaterialTemplate.MATERIALTEMPLATE, doc);
	}

	@Override
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(MaterialTemplate.MATERIALTEMPLATE, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		return super.find(MaterialTemplate.MATERIALTEMPLATE, sortFilter, filter, currentPage, pageNum);
	}

	@Override
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages) {
		List<Map<String, Object>> listResults = super.find(MaterialTemplate.MATERIALTEMPLATE, sortMap, whereMap, queryMap, pages.getCurrentPage(), pages.getPageNum());
		long totalRecord = super.count(MaterialTemplate.MATERIALTEMPLATE, whereMap);
		pages.setTotalRecord(totalRecord);
		pages.setResults(listResults);
		return pages;
	}

	@Override
	public long countObject(Map<String, Object> whereMap) {
		return super.count(MaterialTemplate.MATERIALTEMPLATE, whereMap);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateMany(MaterialTemplate.MATERIALTEMPLATE, filter, update);
	}

	@Override
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateMany(MaterialTemplate.MATERIALTEMPLATE, filter, update, option);
	}

	@Override
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateManyBySet(MaterialTemplate.MATERIALTEMPLATE, filter, update, option);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update) {
		return super.updateOne(MaterialTemplate.MATERIALTEMPLATE, filter, update);
	}

	@Override
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOne(MaterialTemplate.MATERIALTEMPLATE, filter, update, option);
	}

	@Override
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option) {
		return super.updateOneBySet(MaterialTemplate.MATERIALTEMPLATE, filter, update);
	}

	@Override
	public long deleteOne(Map<String, Object> filter) {
		return super.deleteOne(MaterialTemplate.MATERIALTEMPLATE, filter);
	}

}
