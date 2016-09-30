package com.cdvcloud.rms.service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IRetryService {
	public ResponseObject retryUrl(CommonParameters common,String _id,String taskType);

}
