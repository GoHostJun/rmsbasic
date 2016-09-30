package com.cdvcloud.rms.util;

import java.util.HashMap;
import java.util.Map;

import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
/**
 * 向数据库写状态时，使用这个工具类
 * @version： v1.0
 * @author huangaigang
 * @date 2015-11-29 23:06:20
 */
public class StatusUtil {
	
	/**
	 * 根据相应的的枚举实例返回，相应的状态map
	 * @param vmsStatus
	 * @return
	 */
	public static Map<String,Object> generateStatusMap(GeneralStatus vmsStatus){
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put(Constants.VMSSTATUS_STATUS, vmsStatus.status);
		statusMap.put(Constants.VMSSTATUS_DETAIL, vmsStatus.detail);
		statusMap.put(Constants.VMSSTATUS_ENDETAIL, vmsStatus.enDetail);
		return statusMap;
	}

}
