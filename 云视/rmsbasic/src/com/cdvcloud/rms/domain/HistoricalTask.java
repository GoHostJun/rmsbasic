package com.cdvcloud.rms.domain;

public class HistoricalTask {
	/** 表名*/ 
	public static final String  HISTORICALTASK="historicalTask";
	/***id */
	public static final String ID = "_id";
	/*** 企业标识 */
	public static final String COMPANYID = "companyId";
	/*** 应用标识 */
	public static final String APPCODE = "appCode";
	/*** 用户标识 */
	public static final String USERID = "userId";
	/*** 服务类型标识 */
	public static final String SERVICECODE = "serviceCode";
	/*** 版本号 */
	public static final String VERSIONID = "versionId";
	/** 所属商主键 */
	public static final String CONSUMERID = "consumerid";
	/** 操作项 */ 
	public static final String  OPERATION="operation";
	/** 文件名 */ 
	public static final String  FILENAME="fileName";
	/** 文件大小 */ 
	public static final String  FILESIZE="fileSize";
	/** 文件地址 */ 
	public static final String  FILEDEPOSITURL="fileDepositUrl";
	/** 来源*/ 
	public static final String  SRC="src";
	/** 任务状态     0 ：成功  1：失败   2：进心中*/ 
	public static final String  STATUS="status";
	/**  文件类型*/ 
	public static final String  TYPE="type";
	/**  */ 
	public static final String  MTYPE="mtype";
	/**   创建时间*/ 
	public static final String  CTIME="ctime";
	/**  错误信息： 截图错误，转码错误*/ 
	public static final String  ERRORMESSAGE="errorMessage";
	/**   转码任务下发url地址*/ 
	public static final String  TRANSCODEURL="transcodeUrl";
	/**   转码任务下发内容*/ 
	public static final String  TRANSCODEPARAM="transcodeParam";
	/**   快编任务下发url地址*/ 
	public static final String  FASTCODEURL="fastCodeUrl";
	/**   快编任务下发内容*/ 
	public static final String  FASTCODEPARAM="fastCodeParam";
	/**   截图任务下发url地址*/ 
	public static final String  SCREENURL="screenUrl";
	/**   截图任务下发内容*/ 
	public static final String  SCREENPARAM="screenParam";
	/**   上传任务id  */ 
	public static final String  UPLOADUUID="uploaduuid";
}
