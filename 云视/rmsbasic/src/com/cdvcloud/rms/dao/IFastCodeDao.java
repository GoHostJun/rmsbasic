package com.cdvcloud.rms.dao;

import java.util.List;
import java.util.Map;

public interface IFastCodeDao {
	/**
	 * 字幕
	 * @param setMap
	 * @return
	 */
	public String insertSubtitles(Map<String, Object> setMap);
	/**
	 * 项目
	 * @param setMap
	 * @return
	 */
	public String insertProject(Map<String, Object> setMap);
	public Long countProject(Map<String, Object> whereMap);
	public Long countSubtitles(Map<String, Object> whereMap);
	
	public List<Map<String, Object>> querySubtitles(Map<String, Object> sortFilter,Map<String, Object> whereMap,int currentPage,int pageNum);
	public List<Map<String, Object>> queryProject(Map<String, Object> sortFilter,Map<String, Object> whereMap,int currentPage,int pageNum);
	/**
	 * 修改字幕
	 * @param whereMap
	 * @param setMap
	 * @return
	 */
	public long updateSubtitles(Map<String, Object> whereMap,Map<String, Object> setMap);
	/**
	 * 修改项目
	 * @param whereMap
	 * @param setMap
	 * @return
	 */
	public long updateProject(Map<String, Object> whereMap,Map<String, Object> setMap);
}
