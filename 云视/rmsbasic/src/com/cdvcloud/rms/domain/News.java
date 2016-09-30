package com.cdvcloud.rms.domain;
/**
 * 新闻通联表
 * 
 * @author huangaigang
 */
public class News extends BasicObject {
	/*** 库名 */
	public static final String NEWS = "news";
	/*** id */
	public static final String ID = "_id";
	/*** 文稿 */
	public static final String CATALOGUEID = "catalogueid";
	/*** 标题 */
	public static final String TITLE ="title";
	/*** 概要 */
	public static final String OVERVIEW="overview";
	/*** 来源*/
	public static final String SRC="src";
	/*** 模板 */
	public static final String TEMPLATE="template";   
	/*** 缩略图 */
	public static final String THUMBNAILURL="thumbnailurl";   
	/*** 视频 */
	public static final String VIDEOS="videos";
	/*** 音频 */
	public static final String AUDIOS="audios";
	/*** 图片 */
	public static final String PICS="pics";
	/*** 文本 */
	public static final String DOCS="docs";
	/*** 备注 */
	public static final String REMARK="remark";
	/*** 审核情况 */
	public static final String CHECK="check";
	/*** 操作记录 */
	public static final String LOGS="logs"; 
	/*** 状态 */
	public static final String STATUS="status";
	/*** 阅读状态 */
	public static final String READSTATUS="readstatus";
	/*** 是否删除 */
	public static final String ISDEL="isdel";
	/*** 是否共享 */
	public static final String SHARE="share";
	/*** 地区code */
	public static final String AREACODE="areacode";
	/*** 地区名称 */
	public static final String AREANAME="areaname";
	/*** 审核人 */
	public static final String CHECKUSER="checkuser";
	/*** 通联人 */
	public static final String SHAREUSER="shareuser";
	/*** 评论 */
	public static final String COMMENTS="comments";
	/*** 更新时间 */
	public static final String OTHERMSG="othermsg";
	/*** 阅读次数 */
	public static final String READETOTAL="readetotal";
	/*** 通联推送次数 */
	public static final String PUSHTOTAL="pushtotal";
	/*** 通联是否共享 */
	public static final String SHARENEWS="sharenews";
	/*** 通联推送 */
	public static final String PUSHSET="pushset";
}
