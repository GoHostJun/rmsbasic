package com.cdvcloud.rms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.IFastCodeDao;
import com.cdvcloud.rms.domain.Fastcode_Project_Subtitle;
@Repository
public class FastCodeDaoImpl extends BasicDao implements IFastCodeDao {

	@Override
	public String insertSubtitles(Map<String, Object> setMap) {
		return super.insert(Fastcode_Project_Subtitle.SUBTITLES,setMap);
	}

	@Override
	public String insertProject(Map<String, Object> setMap) {
		return super.insert(Fastcode_Project_Subtitle.PROJECT,setMap);
	}

	@Override
	public long updateSubtitles(Map<String, Object> whereMap, Map<String, Object> setMap) {
		return super.updateManyBySet(Fastcode_Project_Subtitle.SUBTITLES, whereMap, setMap);
	}

	@Override
	public long updateProject(Map<String, Object> whereMap, Map<String, Object> setMap) {
		return super.updateManyBySet(Fastcode_Project_Subtitle.PROJECT, whereMap, setMap);
	}

	@Override
	public List<Map<String, Object>> querySubtitles(Map<String, Object> sortFilter, Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(Fastcode_Project_Subtitle.SUBTITLES, sortFilter, whereMap, currentPage, pageNum);
	}

	@Override
	public List<Map<String, Object>> queryProject(Map<String, Object> sortFilter, Map<String, Object> whereMap, int currentPage, int pageNum) {
		return super.find(Fastcode_Project_Subtitle.PROJECT, sortFilter, whereMap, currentPage, pageNum);
	}

	@Override
	public Long countProject(Map<String, Object> whereMap) {
		return super.count(Fastcode_Project_Subtitle.PROJECT, whereMap);
	}

	@Override
	public Long countSubtitles(Map<String, Object> whereMap) {
		return super.count(Fastcode_Project_Subtitle.SUBTITLES, whereMap);
	}

}
