package com.cdvcloud.rms.service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;

public interface IDirectPassingService {
	/**
	 * 创建媒体素材
	 * @param insert
	 * @return
	 */
	public ResponseObject saveHttpMaterial(CommonParameters insert,String strJson);
	public void moveTaskCallBack(String strJson);
	public void NTMCallBack(String strJson);
	public ResponseObject findDirectPassAll (CommonParameters commonPara,String strJson);
	public void postRemove(String httpFile ,String fileName ,String size,String md5,String directIds,
			String mtype,String destFile,CommonParameters insert);
}
