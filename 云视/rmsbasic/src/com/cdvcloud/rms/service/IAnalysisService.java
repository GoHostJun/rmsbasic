package com.cdvcloud.rms.service;

import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;

public interface IAnalysisService {
	/**
	 * 获取文件分析参数
	 * @param common
	 * @param srcFileUrl
	 * @return
	 */
	public Map<String, Object> getAnalysis(CommonParameters common,String srcFileUrl);

}
