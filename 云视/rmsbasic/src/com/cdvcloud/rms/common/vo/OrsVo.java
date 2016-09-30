package com.cdvcloud.rms.common.vo;

import java.util.HashMap;
import java.util.Map;


public class OrsVo {
	/** 企业标识 *必传*/ 
	private String  companyId;
	/**  应用唯一标识  必传 */
	private String  appCode;
	/** 用户唯一标识 * 必传 */
	private String  userId;
	/** 资源唯一标识 * 必传 */
	private String  resId;
	/** 资源类型，0表示视频，1表示图片,2表示文档，3表示其他 * 必传 */
	private String  resType;
	/** 清晰度，视频：ori（原视频）、shd、hd、sd、ld，图片：poster（海报）、thumbnail（缩略图）  * 必传 */
	private String  cType;
	/**  终端类型，pc、mb、ott * 必传 */
	private String  tType;
	/**  onair外网地址 * 必传 */
	private String  onairInternetUrl;
	/** onair内网地址 * 必传 */
	private String  onairLanUrl;
	/**  oss外网代理地址 * 非必传 */
	private String  ossProxyUrl;
	/**  oss外网地址 *非必传 */
	private String  ossInternetUrl;
	/**  oss内网地址 * 非必传 */
	private String  ossLanUrl;
	/**  cdn地址 *非必传 */
	private String  cdnUrl ;
	/** 备注 * 非必传 */
	private String  remark ;
	/** 存储IP地址 *  必传 */
	private String  storageIp;
	/**  物理路径 * 必传 */
	private String  filePath;
	/**  是否删除，0表示未删除，1表示已删除 * 非必传，默认为0 */
	private String  isDelete;
	/** 是否删除，0表示未删除，1表示已删除 *  非必传，默认为0 */
	private String  status;
	
	
	@Override
	public String toString() {
		return "OrsVo [companyId=" + companyId + ", appCode=" + appCode + ", userId=" + userId + ", resId=" + resId + ", resType=" + resType
				+ ", cType=" + cType + ", tType=" + tType + ", onairInternetUrl=" + onairInternetUrl + ", onairLanUrl=" + onairLanUrl
				+ ", ossProxyUrl=" + ossProxyUrl + ", ossInternetUrl=" + ossInternetUrl + ", ossLanUrl=" + ossLanUrl + ", cdnUrl=" + cdnUrl
				+ ", remark=" + remark + ", storageIp=" + storageIp + ", filePath=" + filePath + ", isDelete=" + isDelete + ", status=" + status
				+ "]";
	}
	public Map<String, Object> toMap() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("companyId", companyId);
		json.put("appCode", appCode);
		json.put("userId", userId);
		json.put("resId", resId);
		json.put("resType", resType);
		json.put("cType", cType);
		json.put("tType", tType);
		json.put("onairInternetUrl", onairInternetUrl);
		json.put("onairLanUrl", onairLanUrl);
		json.put("ossProxyUrl", ossProxyUrl);
		json.put("ossInternetUrl", ossInternetUrl);
		json.put("ossLanUrl", ossLanUrl);
		json.put("cdnUrl", cdnUrl);
//		json.put("remark ", remark);
		json.put("storageIp", storageIp);
		json.put("filePath", filePath);
//		json.put("isDelete", isDelete);
//		json.put("status", status);
		return json;
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
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getcType() {
		return cType;
	}
	public void setcType(String cType) {
		this.cType = cType;
	}
	public String gettType() {
		return tType;
	}
	public void settType(String tType) {
		this.tType = tType;
	}
	public String getOnairInternetUrl() {
		return onairInternetUrl;
	}
	public void setOnairInternetUrl(String onairInternetUrl) {
		this.onairInternetUrl = onairInternetUrl;
	}
	public String getOnairLanUrl() {
		return onairLanUrl;
	}
	public void setOnairLanUrl(String onairLanUrl) {
		this.onairLanUrl = onairLanUrl;
	}
	public String getOssProxyUrl() {
		return ossProxyUrl;
	}
	public void setOssProxyUrl(String ossProxyUrl) {
		this.ossProxyUrl = ossProxyUrl;
	}
	public String getOssInternetUrl() {
		return ossInternetUrl;
	}
	public void setOssInternetUrl(String ossInternetUrl) {
		this.ossInternetUrl = ossInternetUrl;
	}
	public String getOssLanUrl() {
		return ossLanUrl;
	}
	public void setOssLanUrl(String ossLanUrl) {
		this.ossLanUrl = ossLanUrl;
	}
	public String getCdnUrl() {
		return cdnUrl;
	}
	public void setCdnUrl(String cdnUrl) {
		this.cdnUrl = cdnUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStorageIp() {
		return storageIp;
	}
	public void setStorageIp(String storageIp) {
		this.storageIp = storageIp;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
