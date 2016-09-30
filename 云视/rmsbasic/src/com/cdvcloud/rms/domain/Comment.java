package com.cdvcloud.rms.domain;

/**
 * 评论表
 * 
 * @author huangaigang
 */
public class Comment extends BasicObject {
	/** 集合名 */
	public static final String COMMENT = "comment";
	/*** id */
	public static final String ID = "_id";
	/** 评论父id */
	public static final String PID = "pid";
	/** 评论标题 */
	public static final String COMTITLE = "comtitle";
	/** 内容 */
	public static final String COMCONTENT = "comcontent";
	/** 通联id */
	public static final String NEWSID = "newsid";
	/** 状态 */
	public static final String STATUS = "status";
	/** 类型 */
	public static final String TYPE = "type";
	/** 评论接收人 */
	public static final String TOUSERID = "touserid";
	/** 评论接收人人头像 */
	public static final String TOUSERURL = "touserurl";
	/** 创建人头像 */
	public static final String USERURL = "userurl";
	
}
