package com.cdvcloud.rms.domain;

/**
 * 推送表
 * 
 * @author huangaigang
 */
public class PushSet extends BasicObject {
	/** 集合名 */
	public static final String PUSHSET = "pushset";
	/** id */
	public static final String ID = "_id";
	/** 名称    */
	public static final String PUSHNAME = "pushname";
	/** 唯一标识    */
	public static final String UNIQUENAME = "uniquename";
	/** 推送地址    */
	public static final String PUSHURL = "pushurl";
	/** 获取栏目地址    */
	public static final String QUERYURL = "queryurl";
	/** 其它信息    */
	public static final String OTHERMSG = "othermsg";
	/** 状态    */
	public static final String STATUS = "status";
}
