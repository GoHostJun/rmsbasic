package com.cdvcloud.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.mongodb.MongoJdbc;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Repository
public class BasicDao {
	@Autowired
	private MongoJdbc mongoJdbc;

	/**
	 * 获取集合连接
	 * 
	 * @param collectionName
	 *            集合名称
	 * @return 数据库连接
	 */
	public MongoCollection<Document> getDocumentCollection(String collectionName) {
		return mongoJdbc.getMongodb().getCollection(collectionName);
	}

	/**
	 * 获取集合连接
	 * 
	 * @param collectionName
	 *            集合名称
	 * @return 数据库连接
	 */
	public MongoCollection<BasicDBObject> getBasicDBObjectCollection(String collectionName) {
		return mongoJdbc.getMongodb().getCollection(collectionName, BasicDBObject.class);
	}

	/**
	 * 给集合插入一个文档
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param document
	 *            插入的Document
	 * @return _id
	 */
	public String insert(String collectionName, Document document) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		collection.insertOne(document);
		return document.get("_id").toString();
	}

	/**
	 * 给集合插入一个文档
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param map
	 *            插入的map
	 * @return _id
	 */
	public String insert(String collectionName, Map<String, Object> map) {
		return insert(collectionName, new Document(map));
	}

	/**
	 * 给集合插入多个文档
	 * 
	 * @param collectionName集合名称
	 * @param list
	 *            插入的Document集合
	 */
	public void insertDocuments(String collectionName, List<Document> list) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		collection.insertMany(list);
	}

	/**
	 * 给集合插入多个文档集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param list
	 *            插入的Map集合
	 */
	public void insertMaps(String collectionName, List<Map<String, Object>> list) {
		List<Document> listDoc = new ArrayList<Document>();
		for (Map<String, Object> map : list) {
			listDoc.add(new Document(map));
		}
		insertDocuments(collectionName, listDoc);
	}

	/**
	 * 根据id查询集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param _id
	 *            _id
	 * @return _id对应的Document对象
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findOne(String collectionName, String _id) {
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		BasicDBObject basicDBObject = collection.find(Filters.eq("_id", new ObjectId(_id))).first();
		if (null != basicDBObject) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			return basicDBObject.toMap();
		} else {
			return null;
		}
	}

	/**
	 * 根据id查询集合
	 * 
	 * @param collectionName
	 * @param _id
	 * @param backFilter
	 *            需要返回字段条件(1:返回,0:不返回)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findOne(String collectionName, String _id, Map<String, Object> backFilter) {
		BasicDBObject queryBack = new BasicDBObject();
		queryBack.putAll(backFilter);
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		BasicDBObject basicDBObject = collection.find(Filters.eq("_id", new ObjectId(_id))).projection(queryBack).first();
		if (null != basicDBObject) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			return basicDBObject.toMap();
		} else {
			return null;
		}
	}

	/**
	 * 根据id查询集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param _id
	 *            _id
	 * @return _id对应的Document对象
	 */
	public Document findOneDocument(String collectionName, String _id) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		Document document = collection.find(Filters.eq("_id", new ObjectId(_id))).first();
		return document;
	}

	/**
	 * 查询条件对应的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @return 查询条件对应的第一条记录
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findOne(String collectionName, Map<String, Object> filter) {
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		BasicDBObject basicDBObject = collection.find(new BasicDBObject(filter)).first();
		if (null != basicDBObject) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			return basicDBObject.toMap();
		} else {
			return null;
		}
	}

	/**
	 * 查询条件对应的第一条记录并返回指定字段
	 * 
	 * @param collectionName
	 * @param queryFilter
	 * @param backFilter
	 *            需要返回字段条件(1:返回,0:不返回)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findOne(String collectionName, Map<String, Object> queryFilter, Map<String, Object> backFilter) {
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		BasicDBObject basicDBObject = collection.find(new BasicDBObject(queryFilter)).projection(new BasicDBObject(backFilter)).first();
		if (null != basicDBObject) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			return basicDBObject.toMap();
		} else {
			return null;
		}
	}

	/**
	 * 查询条件对应的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @return 查询条件对应的第一条记录
	 */
	public Document findOneDocument(String collectionName, Map<String, Object> filter) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		Document document = collection.find(new BasicDBObject(filter)).first();
		return document;
	}

	/**
	 * 获取集合默认排序的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @return 集合默认排序的第一条记录
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findOne(String collectionName) {
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		BasicDBObject basicDBObject = collection.find().first();
		if (null != basicDBObject) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			return basicDBObject.toMap();
		} else {
			return null;
		}
	}

	/**
	 * 获取集合默认排序的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @return _id对应的Document对象
	 */
	public Document findOneDocument(String collectionName) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		Document document = collection.find().first();
		return document;
	}

	/**
	 * 分页获取集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> find(String collectionName, int currentPage, int pageNum) {
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		List<DBObject> list = collection.find().skip((currentPage - 1) * pageNum).limit(pageNum).into(new ArrayList<DBObject>());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (null == list) {
			return mapList;
		}
		for (DBObject basicDBObject : list) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			mapList.add(basicDBObject.toMap());
		}
		return mapList;
	}

	/**
	 * 分页获取集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	public List<Document> findDocument(String collectionName, int currentPage, int pageNum) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		List<Document> list = collection.find().skip((currentPage - 1) * pageNum).limit(pageNum).into(new ArrayList<Document>());
		return list;
	}

	/**
	 * 根据条件分页获取集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> find(String collectionName, Map<String, Object> filter, int currentPage, int pageNum) {
		BasicDBObject query = new BasicDBObject();
		query.putAll(filter);
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		List<BasicDBObject> list = collection.find(query).skip((currentPage - 1) * pageNum).limit(pageNum).into(new ArrayList<BasicDBObject>());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (null == list) {
			return mapList;
		}
		for (BasicDBObject basicDBObject : list) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			mapList.add(basicDBObject.toMap());
		}
		return mapList;
	}

	/**
	 * 根据条件查询并返回指定字段集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param queryfilter
	 *            查询条件
	 * @param backfilter
	 *            需要返回字段条件(1:返回,0:不返回)
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findList(String collectionName, Map<String, Object> queryFilter, Map<String, Object> backFilter,
			int currentPage, int pageNum) {
		BasicDBObject query = new BasicDBObject();
		query.putAll(queryFilter);
		BasicDBObject queryBack = new BasicDBObject();
		queryBack.putAll(backFilter);
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		List<BasicDBObject> list = collection.find(query).projection(queryBack).skip((currentPage - 1) * pageNum).limit(pageNum)
				.into(new ArrayList<BasicDBObject>());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (null == list) {
			return mapList;
		}
		for (BasicDBObject basicDBObject : list) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			mapList.add(basicDBObject.toMap());
		}
		return mapList;
	}

	/**
	 * 根据条件进行分页查询,支持排序
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            排序条件
	 * @param filter
	 *            查询条件
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> find(String collectionName, Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage,
			int pageNum) {
		BasicDBObject query = new BasicDBObject();
		query.putAll(filter);
		BasicDBObject sortFlag = new BasicDBObject();
		sortFlag.putAll(sortFilter);
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		List<BasicDBObject> list = collection.find(query).sort(sortFlag).skip((currentPage - 1) * pageNum).limit(pageNum)
				.into(new ArrayList<BasicDBObject>());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (null == list) {
			return mapList;
		}
		for (BasicDBObject basicDBObject : list) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			mapList.add(basicDBObject.toMap());
		}
		return mapList;
	}

	/**
	 * 根据条件进行分页查询,支持排序
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            排序条件
	 * @param filter
	 *            查询条件
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> find(String collectionName, Map<String, Object> sortFilter, Map<String, Object> queryFilter,
			Map<String, Object> backFilter, int currentPage, int pageNum) {
		BasicDBObject query = new BasicDBObject();
		if (null != queryFilter) {
			query.putAll(queryFilter);
		}
		BasicDBObject sortFlag = new BasicDBObject();
		if (null != sortFilter) {
			sortFlag.putAll(sortFilter);
		}
		BasicDBObject queryBack = new BasicDBObject();
		if (null != backFilter) {
			queryBack.putAll(backFilter);
		}
		MongoCollection<BasicDBObject> collection = getBasicDBObjectCollection(collectionName);
		List<BasicDBObject> list = collection.find(query).projection(queryBack).sort(sortFlag).skip((currentPage - 1) * pageNum).limit(pageNum)
				.into(new ArrayList<BasicDBObject>());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (null == list) {
			return mapList;
		}
		for (BasicDBObject basicDBObject : list) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			mapList.add(basicDBObject.toMap());
		}
		return mapList;
	}

	/**
	 * 根据条件进行分页查询
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	public List<Document> findDocument(String collectionName, Map<String, Object> filter, int currentPage, int pageNum) {
		BasicDBObject query = new BasicDBObject();
		query.putAll(filter);
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		List<Document> list = collection.find(query).skip((currentPage - 1) * pageNum).limit(pageNum).into(new ArrayList<Document>());
		return list;
	}

	/**
	 * 根据条件进行分页查询
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            排序条件
	 * @param filter
	 *            查询条件
	 * @param currentPage
	 *            当前页
	 * @param pageNum
	 *            每页条数
	 * @return Document集合
	 */
	public List<Document> findDocument(String collectionName, Map<String, Object> sortFilter, Map<String, Object> filter, int currentPage, int pageNum) {
		BasicDBObject query = new BasicDBObject();
		query.putAll(filter);
		BasicDBObject sortFlag = new BasicDBObject();
		sortFlag.putAll(sortFilter);
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		List<Document> list = collection.find(query).sort(sortFlag).skip((currentPage - 1) * pageNum).limit(pageNum).into(new ArrayList<Document>());
		return list;
	}

	/**
	 * 更新条件满足的所有记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param update
	 *            新值,需要带上操作符（比如：$set）
	 * @return 更新的记录数
	 */
	public long updateMany(String collectionName, Map<String, Object> filter, Map<String, Object> update) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateMany(new BasicDBObject(filter), new BasicDBObject(update));
		return updateResult.getModifiedCount();
	}

	/**
	 * 更新条件满足的所有记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param update
	 *            新值,需要带上操作符（比如：$set）
	 * @param option
	 *            添加true 不添加false
	 * @return 更新的记录数
	 */
	public long updateMany(String collectionName, Map<String, Object> filter, Map<String, Object> update, boolean option) {
		UpdateOptions options = new UpdateOptions();
		options.upsert(option);
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateMany(new BasicDBObject(filter), new BasicDBObject(update), options);
		return updateResult.getModifiedCount();
	}

	/**
	 * 更新条件满足的所有记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param update
	 *            新值
	 * @return 更新的记录数
	 */
	public long updateManyBySet(String collectionName, Map<String, Object> filter, Map<String, Object> update) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateMany(new BasicDBObject(filter), new BasicDBObject("$set", new BasicDBObject(update)));
		return updateResult.getModifiedCount();
	}

	/**
	 * 更新条件满足的所有记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param update
	 *            新值
	 * @param option
	 *            添加true 不添加false
	 * @return 更新的记录数
	 */
	public long updateManyBySet(String collectionName, Map<String, Object> filter, Map<String, Object> update, boolean option) {
		UpdateOptions options = new UpdateOptions();
		options.upsert(option);
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateMany(new BasicDBObject(filter), new BasicDBObject("$set", new BasicDBObject(update)), options);
		return updateResult.getModifiedCount();
	}

	/**
	 * 仅更新满足条件的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param update
	 *            新值,需要带上操作符（比如：$set）
	 * @return 更新的记录数
	 */
	public long updateOne(String collectionName, Map<String, Object> filter, Map<String, Object> update) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateOne(new BasicDBObject(filter), new BasicDBObject(update));
		return updateResult.getModifiedCount();
	}

	/**
	 * 更新
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            过滤条件
	 * @param update
	 *            更新
	 * @param option
	 *            添加true 不添加false
	 * @return
	 */
	public long updateOne(String collectionName, Map<String, Object> filter, Map<String, Object> update, boolean option) {
		UpdateOptions options = new UpdateOptions();
		options.upsert(option);
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateOne(new BasicDBObject(filter), new BasicDBObject(update), options);
		return updateResult.getModifiedCount();
	}

	/**
	 * 仅更新满足条件的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param update
	 *            新值
	 * @return 更新的记录数
	 */
	public long updateOneBySet(String collectionName, Map<String, Object> filter, Map<String, Object> update) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateOne(new BasicDBObject(filter), new BasicDBObject("$set", new BasicDBObject(update)));
		return updateResult.getModifiedCount();
	}

	/**
	 * 仅更新满足条件的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @param update
	 *            新值
	 * @param option
	 *            添加true 不添加false
	 * @return 更新的记录数
	 */
	public long updateOneBySet(String collectionName, Map<String, Object> filter, Map<String, Object> update, boolean option) {
		UpdateOptions options = new UpdateOptions();
		options.upsert(option);
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		UpdateResult updateResult = collection.updateOne(new BasicDBObject(filter), new BasicDBObject("$set", new BasicDBObject(update)), options);
		return updateResult.getModifiedCount();
	}

	/**
	 * 仅删除满足条件的第一条记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @return 删除的记录数
	 */
	public long deleteOne(String collectionName, Map<String, Object> filter) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		DeleteResult deleteResult = collection.deleteOne(new BasicDBObject(filter));
		return deleteResult.getDeletedCount();
	}

	/**
	 * 删除满足条件的所有记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @return 删除的记录数
	 */
	public long deleteMany(String collectionName, Map<String, Object> filter) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		DeleteResult deleteResult = collection.deleteMany(new BasicDBObject(filter));
		return deleteResult.getDeletedCount();
	}

	/**
	 * 统计集合总记录数
	 * 
	 * @param collectionName
	 *            集合名称
	 * @return 总数
	 */
	public long count(String collectionName) {
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		return collection.count();
	}

	/**
	 * 统计满足条件的总记录数
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param filter
	 *            查询条件
	 * @return 总数
	 */
	public long count(String collectionName, Map<String, Object> filter) {
		BasicDBObject query = new BasicDBObject();
		query.putAll(filter);
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		return collection.count(query);
	}
	
	/**
	 * 
	 * @param collectionName  表名
	 * @param group  需要分组统计的list
	 * 如：
	 * List<Document> group = Arrays.asList(
			    new Document("$group", new Document( "_id", 
			            new Document("areaname","$areaname"))
			            .append("count", new Document("$sum",1))), 
			    new Document("$sort", new Document("count", -1))
			); 
	 * @return
	 */
	public List<Map<String, Object>> aggregate(String collectionName,List<Document> group) {
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
		MongoCollection<Document> collection = getDocumentCollection(collectionName);
		AggregateIterable<Document> iterable  = collection.aggregate(group).allowDiskUse(true);
		for (Document document : iterable) {
			lists.add(document);
		}
		return lists;
	}
}
