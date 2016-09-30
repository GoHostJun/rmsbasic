package com.cdvcloud.rms.domain;
/**
 * 编目表
 * @author mcxin
 *
 */
public class Catalogue {
	/** 集合名称 */
	public static final String CATALOGUE = "catalogue";

	/** id */
	public static final String ID = "_id";
	/** 标题：    */
	public static final String TITLE = "title";
	/** 概要： */
	public static final String OVERVIEW = "overview";
	/** 来源： */
	public static final String SRC = "src";
	/**  模板{}： */
	public static final String TEMPLATE = "template";
	/** 编目缩略图地址 */
	public static final String THUMBNAILURL = "thumbnailurl";
	/**   备注： */
	public static final String REMARK = "remark";


	/**  视频[]： */
	public static final String VIDEOS = "videos";
	/**   音频[]：*/
	public static final String AUDIOS = "audios";
	/**  图片[]： */
	public static final String PICS = "pics";
	/** 文本[]： */
	public static final String DOCS = "docs";

	/**   状态： */
	public static final String STATUS = "status"; 
	/** 删除情况： */
	public static final String ISDEL = "isdel";
	/** 删除情况： */
	public static final String RECYCLE = "recycle";
	/**    审核情况[]： */
	public static final String CHECK = "check";
	/**  操作记录[]: */
	public static final String LOGS = "logs";

	/**    创建时间： */
	public static final String CTIME = "ctime";
	/**   共享标识： */
	public static final String SHARE = "share";
	/** 更新人主键：   */
	public static final String UUSERID = "uuserid";
	/**  更新人：  */
	public static final String UUSERNAME = "uusername";
	/**    更新时间： */
	public static final String UUTIME = "uutime";
	/**  其他信息[]:  */
	public static final String OTHERMSG = "othermsg";
	/**  地区code: */
	public static final String AREACODE = "areacode";
	/**   地区名称: */
	public static final String AREANAME = "areaname";
	/** 创建人主键 */
	public static final String CUSERID = "cuserid";
	/** 创建人名称 */
	public static final String CUSENAME = "cusename";
	/** 所属商主键 */
	public static final String CONSUMERID = "consumerid";
	/** 所属商名称 */
	public static final String CONSUMERNAME = "consumername";
	/*** 推送次数 */
	public static final String PUSHTOTAL="pushtotal";
}
