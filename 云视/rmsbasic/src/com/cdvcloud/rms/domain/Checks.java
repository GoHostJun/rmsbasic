package com.cdvcloud.rms.domain;

/**
 * 审核
 * 
 * @author huangaigang
 */
public class Checks extends BasicObject{
	public static final String CHECKS = "checks";// 审查表名
	/** 主键 */
	public static final String ID = "_id";
	/** 素材标题 */
	public static final String TITLE = "title";
	/** 所属编目 */
	public static final String CATAID = "cataid";
	/** 审核意见 */
	public static final String CHECKINFO = "checkinfo";
	/** 审核操作 */
	public static final String CHECKOPT = "checkopt";
	/** 审核状态 */
	public static final String STATUS = "status";
	
}