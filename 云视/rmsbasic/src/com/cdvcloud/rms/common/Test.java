package com.cdvcloud.rms.common;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.util.JSONUtils;
import com.cdvcloud.rms.util.StringUtil;

@MyAnnotation(descib = "测试类")
public class Test {
	@MyAnnotation(descib = "新增测试对象")
	public ResponseObject addTest(String Json){
		ResponseObject  responseObject=new ResponseObject(GeneralStatus.failure.status,
				GeneralStatus.failure.detail, "");
		try {
			Map<String,Object> map=JSONUtils.json2map(Json);
			String username=String.valueOf(map.get("username"));
			String password=String.valueOf(map.get("password"));
			if(!StringUtil.isEmpty(username)&&!StringUtil.isEmpty(password)){
				responseObject=new ResponseObject(GeneralStatus.success.status,
						GeneralStatus.success.detail, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseObject;
	}

}
