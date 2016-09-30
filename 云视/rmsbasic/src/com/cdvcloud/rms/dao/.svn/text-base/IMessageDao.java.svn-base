package com.cdvcloud.rms.dao;

import java.util.List;
import java.util.Map;

import org.bson.Document;

public interface IMessageDao {

	/**
	 * 根据id查询一条记录
	 * 
	 * @param _id
	 */
	public Map<String, Object> findOne(String _id);

	/**
	 * 根据id查询一条记录
	 * 
	 * @param _id
	 */
	public Document findOneDocument(String _id);

	/**
	 * 根据条件查询一条记录
	 * 
	 * @param filter
	 * @return
	 */
	public Map<String, Object> findOne(Map<String, Object> filter);

	/**
	 * 创建记录
	 * 
	 * @param map
	 * @return
	 */
	public String insertMessage(Map<String, Object> map);

	/**
	 * 创建记录
	 * 
	 * @param map
	 * @return
	 */
	public String insertDocMessage(Document doc);

	/**
	 * 查询列表
	 * 
	 * @param whereMap
	 * @param currentPage
	 *            当前第几页
	 * @param pageNum
	 *            每页多少条
	 */
	public List<Map<String, Object>> findNewAll(Map<String, Object> whereMap, int currentPage, int pageNum);

	/**
	 * 查询列表
	 * 
	 * @param sortFilter
	 *            排序
	 * @param filter
	 *            条件
	 * @param currentPage
	 *            当前第几页
	 * @param pageNum
	 *            每页多少条
	 * @return
	 */
	public List<Map<String, Object>> find(Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum);

	/**
	 * 查询总数
	 * 
	 * @param whereMap
	 * @return
	 */
	public long countMessage(Map<String, Object> whereMap);

	/**
	 * 更新多条记录
	 * 
	 * @param filter
	 *            查询集合
	 * @param update
	 *            更新集合
	 * @return
	 */
	public long updateMany(Map<String, Object> filter, Map<String, Object> update);

	/**
	 * 更新多条记录
	 * 
	 * @param filter
	 *            查询集合
	 * @param update
	 *            更新集合
	 * @param option
	 *            添加true 不添加false
	 * @return
	 */
	public long updateMany(Map<String, Object> filter, Map<String, Object> update, boolean option);

	/**
	 * 更新多条记录
	 * 
	 * @param filter
	 *            查询集合
	 * @param update
	 *            更新集合
	 * @param option
	 *            添加true 不添加false
	 * @return
	 */
	public long updateManyBySet(Map<String, Object> filter, Map<String, Object> update, boolean option);

	/**
	 * 更新一条记录
	 * 
	 * @param filter
	 *            查询集合
	 * @param update
	 *            更新集合
	 * @return
	 */
	public long updateOne(Map<String, Object> filter, Map<String, Object> update);

	/**
	 * 更新一条记录
	 * 
	 * @param filter
	 *            查询集合
	 * @param update
	 *            更新集合
	 * @param option
	 *            添加true 不添加false
	 * @return
	 */
	public long updateOne(Map<String, Object> filter, Map<String, Object> update, boolean option);

	/**
	 * 更新一条记录
	 * 
	 * @param filter
	 *            查询集合
	 * @param update
	 *            更新集合
	 * @param option
	 *            添加true 不添加false
	 * @return
	 */
	public long updateOneBySet(Map<String, Object> filter, Map<String, Object> update, boolean option);

}
