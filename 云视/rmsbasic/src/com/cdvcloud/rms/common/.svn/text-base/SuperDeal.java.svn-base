package com.cdvcloud.rms.common;

import java.util.HashMap;
import java.util.Map;

import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;

public class SuperDeal {
	
	public  static String  getCommonPara(CommonParameters commonParameters,String strJson){
		Map<String,Object> map=JsonUtil.readJSON2Map(strJson);
		map.put(CommonParameters.ACCESSTOKEN, "token");
		map.put(CommonParameters.TIMESTAMP, DateUtil.getCurrentDateTime());
		commonParameters.setUserId("useid");
		strJson=JsonUtil.map2Json(map);
		return strJson;
	}

}
