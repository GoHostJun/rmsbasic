package com.cdvcloud.rms.common;

import java.io.Serializable;
import java.util.List;

public class CommonParameters implements Serializable {
	private static final long serialVersionUID = -6531451957081138067L;
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
	/*** 授权令牌 */
	public static final String ACCESSTOKEN = "accessToken";
	/*** 时间戳 */
	public static final String TIMESTAMP = "timeStamp";
	/*** json */
	public static final String STRJSON = "strJson";
	/*** 访问ip */
	public static final String IP = "ip";

	/*** 企业标识 */
	private String companyId;
	/*** 应用标识 */
	private String appCode;
	/*** 用户标识 */
	private String userId;
	/*** 用户标识 */
	private String casUserId;
	/*** 服务类型标识 */
	private String serviceCode;
	/*** 版本号 */
	private String versionId;
	/*** 角色名称 */
	private String roleName;
	/*** 授权令牌 */
	private String accessToken;
	/*** 时间戳 */
	private String timeStamp;
	/*** 所属商名称 */
	private String consumerName;
	/*** 用户名称 */
	private String userName;
	/*** 用户登录名称 */
	private String loginName;
	/*** 地区code */
	private String areaCode;
	/*** 地区名称 */
	private String areaName;
	/*** 部门主键 */
	private List<Object> departmentId;
	/*** 访问ip */
	private String ip;
	/*** 其他内容 */
	private Object other;

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCasUserId() {
		return casUserId;
	}

	public void setCasUserId(String casUserId) {
		this.casUserId = casUserId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public CommonParameters() {
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Object> getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(List<Object> departmentId) {
		this.departmentId = departmentId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public String toString() {
		return "CommonParameters [companyId=" + companyId + ", appCode=" + appCode + ", userId=" + userId + ", serviceCode=" + serviceCode
				+ ", versionId=" + versionId + ", roleName=" + roleName + ", accessToken=" + accessToken + ", timeStamp=" + timeStamp + "]";
	}

	/**
	 * 针对工具集做参数调整：老版本参数如下顺序如下 appCode/versionId/companyId/serviceCode/
	 * 
	 * @return
	 */
	public String getOldPublicAddress(String serviceCode) {
//		return appCode + "/v1/" + companyId + "/" + getUserIdInput() + "/" + serviceCode + "/";
		return "ytl/v1/" + companyId + "/" + getUserIdInput() + "/" + serviceCode + "/";
	}
	
	/**
	 * 获取用户id，如果对接了统一用户则返回统一用户的userid
	 * 
	 * @return
	 */
	public String getUserIdInput() {
		if (null != getCasUserId() && !"".equals(getCasUserId()) && !"null".equals(getCasUserId())) {
			return getCasUserId();
		}
		return getUserId();
	}
}
