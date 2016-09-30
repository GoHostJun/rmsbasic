package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;

public interface IOssService {
	public Map<String, Object> uploadOss(CommonParameters common,String url,String name,String mtype);
}
