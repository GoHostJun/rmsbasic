package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface ISystemLogService {
	public boolean inset(Map<String, Object> systemLogMap);
	public ResponseObject query(CommonParameters query,String strJson);
	public ResponseObject queryByVisual(CommonParameters query,String strJson);
}
