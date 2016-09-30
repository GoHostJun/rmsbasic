package com.cdvcloud.rms.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.domain.SystemLog;

public class SystemLogUtil {
	/**
	 * 
	 * @param commons  公共参数
	 * @param type     日志类型
	 * @param action   功能项
	 * @param logdesc  日志详情
	 * @return
	 */
	public static Map<String, Object> getMap(CommonParameters commons,String type,String action,String logdesc){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemLog.COMPANYID, commons.getCompanyId());
		map.put(SystemLog.CONSUMERID, commons.getCompanyId());
		map.put(SystemLog.APPCODE, commons.getAppCode());
		map.put(SystemLog.USERID, commons.getUserId());
		map.put(SystemLog.VERSIONID, commons.getVersionId());
		map.put(SystemLog.SERVICECODE, commons.getServiceCode());
		map.put(SystemLog.ACTION, action);
		map.put(SystemLog.LOGDESC, logdesc);
		map.put(SystemLog.CTIME, DateUtil.getCurrentDateTime());
		map.put(SystemLog.CUSERID, commons.getUserId());
		map.put(SystemLog.TYPE, type);
		map.put(SystemLog.CUSENAME, commons.getUserName());
		map.put(SystemLog.IP, commons.getIp());
		return map;
	}
	/**
	 * 获取访问ip
	 * @param commons
	 * @param request
	 * @return
	 */
	public static CommonParameters getIp(CommonParameters commons,HttpServletRequest request){
		String ipAddr = HttpUtil.getIpAddr(request);
		if (StringUtil.isEmpty(ipAddr)) {
			ipAddr = "-.-.-.-";
		}
		commons.setIp(ipAddr);
		return commons;
	}
}
