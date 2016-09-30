package com.cdvcloud.rms.common;

/**
 * MongoDB keywords for various query operations
 * 
 * @author huangaigang
 * 
 */
public class QueryOperators {

	/** 任意组合不同的查询条件（可以针对任意key的限制条件），只要满足任意组合条件中的一个即可 */
	public static final String OR = "$or";
	/** 和$or操作符相对，任意组合不同的查询条件（可以针对任意key的限制条件），并且必须同时满足所有条件 */
	public static final String AND = "$and";

	/** 用来判断某个key值大于某个指定的值 */
	public static final String GT = "$gt";
	/** 用来判断某个key值大于等于某个指定的值 */
	public static final String GTE = "$gte";
	/** 用来判断某个key值小于某个指定的值 */
	public static final String LT = "$lt";
	/** 用来判断某个key值小于等于某个指定的值 */
	public static final String LTE = "$lte";
	/** 用来不等值条件过滤某一个key的值 */
	public static final String NE = "$ne";
	/** 用来指定某个key的值在指定的离散的值域内 */
	public static final String IN = "$in";
	/** 和$in相对，用来指定key值不存在某个指定的离散值域内 */
	public static final String NIN = "$nin";
	/** 取余操作符，筛选经过区域操作后，结果符合条件的文档 */
	public static final String MOD = "$mod";
	/** 数组查询操作符，查询条件是一个数组，被查询的字段也是一个数组，要求被查询的数组类型的字段要是查询条件数组的超集（即大数组要包含小数组）*/
	public static final String ALL = "$all";
	/** 用于某个数组类型的key对应值的数量满足要求 */
	public static final String SIZE = "$size";
	/** 查询不包含某一个属性（key）的文档 */
	public static final String EXISTS = "$exists";
	/** 数组查询操作符，用来指定数组的每一个元素同时满足所罗列的条件，如果不指定，则条件会是或的关系 */
	public static final String ELEM_MATCH = "$elemMatch";

	/** 强大的查询关键字，但性能较差，可以传入js表达式或js函数来筛选数据 */
	public static final String WHERE = "$where";
	/** 和$or相反，表示所有条件均不能满足则返回 */
	public static final String NOR = "$nor";
	/** 过滤某个字段是某一个BSON数据类型的数据 */
	public static final String TYPE = "$type";
	/** 元条件语句，需要和其他条件语句组合使用 */
	public static final String NOT = "$not";

	// geo operators
	public static final String WITHIN = "$within";
	/** 基于2d空间索引，指定一个点，返回该点有近及远的所有的点 */
	public static final String NEAR = "$near";
	/** 基于2d空间索引，指定一个点，由近及远的返回所有的点，和$near操作符不同的是计算距离的方式 $nearSphere计算的是球面距离。$near计算的是坐标距离 */
	public static final String NEAR_SPHERE = "$nearSphere";

	// meta query operators (to be implemented in QueryBuilder)
	public static final String MIN = "$min";
	public static final String MAX = "$max";
	/** 在查询、更新或其他操作执行过程中，可以通过添加$comment操作符添加评论。改评论会被记录在日志中，用于后续分析 */
	public static final String COMMENT = "$comment";
	/** 数组类型字段的投影操作，返回原来数据的一个子集.针对一个数组，其有如下几种返回子集的方式 */
	public static final String SLICE = "$slice";

	// update operators
	/** 对一个数字字段的某个field增加value */
	public static final String INC = "$inc";
	/** 对字段进行重命名 */
	public static final String RENAME = "$rename";
	/** 把文档中某个字段field的值设为value */
	public static final String SET = "$set";
	/** 删除某个字段field */
	public static final String UNSET = "$unset";
	/** 加一个值到数组内，而且只有当这个值在数组中不存在时才增加 */
	public static final String ADDTOSET = "$addToSet";
	/** 用于删除数组内的一个值(删除数组内第一个值：{$pop:{field:-1}}、删除数组内最后一个值：{$pop:{field:1}}) */
	public static final String POP = "$pop";
	/** 用法同$pull一样，可以一次性删除数组内的多个值 */
	public static final String PULLALL = "$pullAll";
	/** 从数组field内删除一个等于_value的值 */
	public static final String PULL = "$pull";
	/** 把value追加到field里。注：field只能是数组类型，如果field不存在，会自动插入一个数组类型 */
	public static final String PUSH = "$push";
	/** 用法同$push一样，只是$pushAll可以一次追加多个值到一个数组字段内 */
	public static final String PUSHALL = "$pushAll";
	/** 组合使用 ，$each 配合 $push：将多值压入数组 field 中，不去重 */
	public static final String EACH = "$each";
	/** 用于预算符$push的限定符，用于将数组中的文档重新排序 */
	public static final String SORT = "$sort";
	public static final String BIT = "$bit";

}
