package com.cdvcloud.rms.service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IMapInfoService {

	public ResponseObject findUnDealCityTasks(CommonParameters commonParameters,String strJson);
	
}
