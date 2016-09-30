package com.cdvcloud.rms.domain;
/**
 * 直传记录表
 * @author mcxin
 *
 */
public class DirectPassing extends BasicObject {
	/*** 库名 */
	public static final String DIRECTPASSING = "directpassing";
	/*** id */
	public static final String ID = "_id";
	/*** 任务id */
	public static final String TASKID="taskId";
	/*** 任务id */
	public static final String OTHERID="otherId";
	/*** 源文件路径 */
	public static final String SRCFILE="srcFile";
	/*** 目标文件路径 */
	public static final String DESTFILE="destFile";
	/*** 目标路径名称 */
	public static final String DIRECTPATHNAME="directpathname";
	/*** 任务错误信息 */
	public static final String ERRORMESSAGE="errorMessage";
	/*** 任务状态  0：成功   1失败    2迁移中 */
	public static final String STATUS="status";
	/*** 任务状态  0：成功   1失败    2转码中 */
	public static final String UPLOADSTATUS="uploadstatus";
	/*** 内网二次推送状态  0：成功   1失败 */
	public static final String OTHERSTATUS="otherStatus";
	/*** 文件大小 */
	public static final String SIZE="size";
	/*** 文件名称 */
	public static final String NAME="name";
	/*** 文件类型 */
	public static final String TYPE="type";
	/*** 文件是否合规  0合规  1不合规 */
	public static final String COMPLIANCE="compliance";
	/*** 推送id */
	public static final String DIRECTID="directId";
	/*** 推送内容数组 */
	public static final String PUSHCONTENTS="pushContents";
	/*** 是否任务 0是 1否*/
	public static final String ISTASK="istask";
	
}
