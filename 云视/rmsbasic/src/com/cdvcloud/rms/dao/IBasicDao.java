package com.cdvcloud.rms.dao;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.cdvcloud.rms.common.Pages;

public interface IBasicDao {

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
	 * 创建记录
	 * 
	 * @param map
	 * @return
	 */
	public String insertObject(Map<String, Object> map);

	/**
	 * 创建记录
	 * 
	 * @param map
	 * @return
	 */
	public String insertDocObject(Document doc);

	/**
	 * 查询列表
	 * 
	 * @param whereMap
	 * @param currentPage
	 *            当前第几页
	 * @param pageNum
	 *            每页多少条
	 */
	public List<Map<String, Object>> findObjectAll(Map<String, Object> whereMap, int currentPage, int pageNum);

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
	 * 查询列表
	 * 
	 * @param whereMap
	 *            查询条件
	 * @param queryMap
	 *            要查询字段，支持最外层的字段选择
	 */
	public Pages findObjectAll(Map<String, Object> whereMap, Map<String, Object> sortMap, Map<String, Object> queryMap, Pages pages);

	/**
	 * 查询总数
	 * 
	 * @param whereMap
	 * @return
	 */
	public long countObject(Map<String, Object> whereMap);

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

	/**
	 * 删除一个对象
	 * 
	 * @param filter
	 * @return
	 */
	public long deleteOne(Map<String, Object> filter);
}
