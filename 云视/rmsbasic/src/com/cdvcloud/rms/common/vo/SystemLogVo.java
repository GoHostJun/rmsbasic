package com.cdvcloud.rms.common.vo;


public class SystemLogVo {
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
	/*** 分页开始行 默认1 */
	public static final String START="start";
	/*** 分页显示数量（默认显示10条） */
	public static final String LIMIT="limit";
	/*** 总条数 */
	public static final String TOTAL="total";
	/*** 当前页 */
	public static final String PAGENO="pageNo";
	/*** 总页数 */
	public static final String PAGESIZE="pageSize";
	/*** 返回内容 */
	public static final String RESULTS="results";
	/** 操作项 */ 
	public static final String  OPERATION="operation";
	/** 功能项*/ 
	public static final String  ACTION="action";
	/**  访问ip*/ 
	public static final String  IP="ip";
	/**  日志详情*/ 
	public static final String  LOGDESC="logdesc";
	/**   日志类型*/ 
	public static final String  TYPE="type";
	/**   创建人主键*/ 
	public static final String  CUSERID="cuserid";
	/**   创建人名称*/ 
	public static final String  CUSENAME="cusename";
	/**   创建时间*/ 
	public static final String  CTIME="ctime";
	
	/** 企业标识 *必传*/ 
	private String  companyId;
	/**  应用唯一标识  必传 */
	private String  appCode;
	/** 功能项*/ 
	private String  action;
	/**  访问ip*/ 
	private String  ip;
	/**  日志详情*/ 
	private String  logdesc;
	/**   日志类型*/ 
	private String  type;
	/**   创建人主键*/ 
	private String  cuserid;
	/**   创建人名称*/ 
	private String  cusename;
	/**   创建时间*/ 
	private String  ctime;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLogdesc() {
		return logdesc;
	}
	public void setLogdesc(String logdesc) {
		this.logdesc = logdesc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCuserid() {
		return cuserid;
	}
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	public String getCusename() {
		return cusename;
	}
	public void setCusename(String cusename) {
		this.cusename = cusename;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

}
