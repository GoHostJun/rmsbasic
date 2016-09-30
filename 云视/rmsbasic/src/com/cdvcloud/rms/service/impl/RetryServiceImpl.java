package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.IHistoricalTaskDao;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.service.IRetryService;
import com.cdvcloud.rms.service.ITranscodeService;
@Service
public class RetryServiceImpl implements IRetryService{
	@Autowired
	ITranscodeService transcodeService;
	@Autowired
	private IHistoricalTaskDao historicalTaskDao;
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	ConfigurationService configurationService;
	@Override
	public ResponseObject retryUrl(CommonParameters common, String historyId,String taskType) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
		Map<String, Object> mediaWhereMap=new HashMap<String, Object>();
		mediaWhereMap.put(Media.TASKID, historyId);
		Map<String,Object> mediaMap=mediaDao.queryOne(mediaWhereMap);
		//更新一下media状态处理中
		String mediaId=String.valueOf(mediaMap.get(Media.ID));
		
		Map<String, Object> histtoryMap=historicalTaskDao.queryOne(historyId);
		String srcFileUrl=String.valueOf(histtoryMap.get(HistoricalTask.FILEDEPOSITURL));
		String mtype=String.valueOf(histtoryMap.get(HistoricalTask.MTYPE));
		common.setAppCode(String.valueOf(histtoryMap.get(HistoricalTask.APPCODE)));
		common.setCompanyId(String.valueOf(histtoryMap.get(HistoricalTask.COMPANYID)));
		common.setUserId(String.valueOf(histtoryMap.get(HistoricalTask.USERID)));
		common.setVersionId(String.valueOf(histtoryMap.get(HistoricalTask.VERSIONID)));
		common.setServiceCode(String.valueOf(histtoryMap.get(HistoricalTask.SERVICECODE)));
		common.setAccessToken(configurationService.getToken(common));
		transcodeService.retryTask(common, mediaId, mtype, srcFileUrl, historyId, taskType);
		return new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
	}

}
