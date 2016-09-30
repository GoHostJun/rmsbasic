package com.cdvcloud.rms.domain;


public class Fastcode_Project_Subtitle extends BasicObject{
	/**集合名（字幕） */
	public static final String SUBTITLES = "subtitles";
	/**集合名（项目）新奥特 */
	public static final String PROJECT = "project";
	/**集合名（项目）天琴 */
	public static final String PROJECT_TQ = "project_tq";
	
	public static final String ID = "_id"; // 主键

	/**是否删除（1：是，0：否）*/
	public static final String ISDELETE = "isDelete";
	/** 2_0:字幕模板，2_1:标题字幕; -1_0:项目模板；-1_1:项目 */
	public static final String TYPE = "type";
	/** 内容 */
	public static final String DATA = "data";
	/** 创建时间*/
	public static final String CTIME = "ctime";// 
	/** 用户*/
	public static final String USER = "user";// 
	/** 标题*/
	public static final String TITLE = "title";// 
	/** -1 */
	public static final String negative_one = "-1";// 
	/** -2 */
	public static final String negative_two = "-2";
}
