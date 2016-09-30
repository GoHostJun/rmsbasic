package com.cdvcloud.rms.common;

import java.io.Serializable;

/**
 * 系统中的所有处理状态
 * @author mcxin
 *
 */
public enum GeneralStatus implements Serializable {
	//公共状态
	success(0, "处理成功", "success"),
	failure(1, "处理失败", "failure"),
	//业务状态
	processing(2, "处理中", "processing"), 
	checkWait(3, "待审核", "checkwait"), 
	checking(4, "审核中", "checking"), 
	checkPass(5, "审核通过", "checkpass"),
	checkNotPass(6, "审核打回", "checknotpass"), 
	waitingPush(7, "未推送", "waitingpush"), 
	hasPushed(8, "已推送", "haspushed"), 
	completed(9, "已完成", "completed"), 
	services(10, "服务中", "services"), 
	ServiceCompletion(11, "服务完成", "ServiceCompletion"), 
	
	//公共错误返回状态
	input_error(10001,"输入参数不合法","Input Parameter Invalid"),
	inner_error(10002,"系统内部错误","Inner Error"),
	unknow_error(10003,"未知错误","Unknow Error"),
	query_error(10004,"查询失败","Query error"),
	update_error(10005,"修改失败","Modify the error"),
	delete_error(10006,"删除失败","Delete the error"),
	repeat_error(10007,"重复失败","Repeat the error"),
	top_error(10008,"置顶失败","Top the error"),
	//获取token
	token_fail(20001, "获取授权失败", "Authorization fail"),
	//媒体库
	media_source(30001,"源文件地址无效","Source File Not Found"),
	media_task(30002,"任务id不存在","TaskId Is Not Exist"),
	media_uploadToken(30003,"断点续传的uploadToken失效","uploadToken error"),
	media_md5(30004,"断点续传文件MD5错误","md5 error"),
	media_failure(30005,"上传失败","Failed to upload"),
	//文稿
	presentation_source(40001,"源文件地址无效","Source File Not Found"),
	presentation_task(40002,"任务id不存在","TaskId Is Not Exist"),
	
	//审核
	check_task(50001,"任务id不存在","TaskId Is Not Exist"),
	check_failure(50002,"审核失败","Check failure"),
	submit_check_failure(50003,"提交审核失败","Submit Check failure"),
	//日志
	
	log_task(60001,"任务id不存在","TaskId Is Not Exist"),
	log_failure(60002,"日志失败","Log failure"),
	//NDA库   70001开始
	 
	//微信用户绑定
	user_bind(80001,"当前用户已经绑定","User Is Aready Bind"),
	user_unbind(80002,"当前用户尚未绑定","User Is Not  Bind"),
	wechat_bind(80003,"当前微信已经绑定","Wechat Is Aready Bind"),
	wechat_unbind(80004,"当前微信尚未绑定","Wechat Is Not Bind")
	;

	public int status; // 状态值 ，0：成功，1：失败，2：处理中，3：未处理
	public String detail; // 状态描述信息
	public String enDetail;

	private GeneralStatus(int status, String detail, String enDetail) {
		this.status = status;
		this.detail = detail;
		this.enDetail = enDetail;
	}

	@Override
	public String toString() {
		return "{status:" + this.status + ",detail:" + this.detail + "}";
	}
}
