/**
 * HashMap
 * HashMap的JavaScript实现
 * @author  Li Shuhui
 * @version  0.9
 */


OnairHashMap = function() {
	/** Map大小 */
	this._size = 0;
	/** 集合对象 */
	this._entry = new Object();
}

OnairHashMap.prototype = {
	toJson:function(){
		return JSON.stringify(this._entry);
	},
	/**
	 * 在此映射中关联指定值与指定键
	 *
	 * @param  key  指定值将要关联的键
	 * @param  value  指定键将要关联的值
	 * @return  加入键值是否成功, 成功为true, 发生意外失败则为false
	 */
	put:function(key, value) {
		if (typeof(key) != "undefined"){
			if (!this.containsKey(key)) {
				this._size ++;
				this._entry[key] = typeof(value) == "undefined" ? null : value;
				return true;
			} else return false;
		} else return false;
	},
	
	/**
	 * 返回指定键在此标识哈希映射中所映射的值
	 *
	 * @param  key  与要返回的值相关联的键
	 * @return  此映射对于指定键所映射的值, 如果该映射对于此键不包含任何映射关系, 则返回null
	 */
	get:function(key) {
		return this.containsKey(key) ? this._entry[key] : null;
	},

	/**
	 * 返回此映射中所包含的键的数组
	 *
	 * @return  此映射中所包含的键的数组
	 */
	keys:function() {
		var keys = new Array();
		for (var k in this._entry) keys.push(k);
		return keys;
	},

	/**
	 * 返回此映射所包含的值的数组
	 *
	 * @return  此映射所包含的值的数组
	 */
	values:function() {
		var vals = new Array();
		for (var v in this._entry) vals.push(this._entry[v]);
		return vals;
	},
	
	/**
	 * 如果此映射中存在该键的映射关系, 则将其删除
	 *
	 * @param  key  其映射关系要从映射中移除的键
	 */
	remove:function(key) {
		if (this.containsKey(key) && (delete this._entry[key]))
			this._size --;
	},
	
	/**
	 * 如果此映射包含对于指定的键的映射关系, 则返回true
	 *
	 * @param  key  要测试其是否在此映射中存在的键
	 * @return  如果此映射包含对于指定的键的映射关系, 则返回true
	 */
	containsKey:function(key) {
		return (key in this._entry);
	},
	
	/**
	 * 如果此映射将一个或多个键映射到指定值, 则返回true
	 *
	 * @param  value  要测试其是否在此映射中存在的值
	 * @return  如果此映射将一个或多个键映射到指定值, 则返回true
	 */
	containsValue:function(value) {
		for (var v in this._entry) {
			if (this._entry[v] == value) return true;
			else return false;
		}
	},
	
	/**
	 * 返回此映射中的键-值映射关系数
	 *
	 * @return  此映射中的键-值映射关系数
	 */
	size:function() {
		return this._size;
	},
	
	/**
	 * 从此映射中移除所有映射关系
	 */
	clear:function() {
		this._size = 0;
		this._entry = new Object();
	}
};
