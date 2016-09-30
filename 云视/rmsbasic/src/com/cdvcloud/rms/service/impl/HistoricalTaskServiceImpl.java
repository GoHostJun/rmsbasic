package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.IHistoricalTaskDao;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.mongodb.BasicDBObject;
@Service
public class HistoricalTaskServiceImpl  extends BasicService implements IHistoricalTaskService {
	@Autowired
	private IHistoricalTaskDao historicalTaskDao;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Autowired
	private ConfigurationService configurationService;
	@Override
	public String inset(Map<String, Object> historicalTaskMap) {
		String id = historicalTaskDao.insert(historicalTaskMap);
		if(null!=id&&!"".equals(id)){
			return id;
		}
		return "";
	}

	@Override
	public boolean update(Map<String, Object> whereMap, Map<String, Object> setMap,String errorMessage) {
		if(!StringUtil.isEmpty(errorMessage)){
			//发送短信
			Map<String, Object> map = historicalTaskDao.queryOne(String.valueOf(whereMap.get(HistoricalTask.ID)));
			String content = "【云南云通联】"+errorMessage+"。标题：《"+map.get(HistoricalTask.FILENAME)+"》";
			String mobileNum = configurationService.getMobileNum();
			CommonParameters common = new CommonParameters();
			common.setCompanyId(String.valueOf(map.get(HistoricalTask.COMPANYID)));
			common.setAppCode(String.valueOf(map.get(HistoricalTask.APPCODE)));
			String accessToken = configurationService.getToken(common);
			String sendUrl = configurationService.getSendUrl();
			threadPoolTaskExecutor.execute(new SendSMSThread(mobileNum, null, accessToken, sendUrl, content));
		}
		long index =  historicalTaskDao.update(whereMap, setMap);
		if(index>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ResponseObject query( String strJson) {
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(String.valueOf(mapJson.get("keyWord")))){
			whereMap.put(HistoricalTask.FILENAME, mapJson.get("keyWord"));
		}
		List<Map<String, Object>> lists = null;
		BasicDBObject dateConditionType = new BasicDBObject();
		dateConditionType.append("$exists",true);
		whereMap.put(HistoricalTask.ERRORMESSAGE, dateConditionType);
		Map<String, Object> sortFilter = new HashMap<String, Object>();
		sortFilter.put(HistoricalTask.CTIME, -1);
		Long total = historicalTaskDao.count(whereMap);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		lists=	historicalTaskDao.query(sortFilter, whereMap, currentPage, pageNum);
		if(null!=lists &&lists.size()>=0){
			Pages pages = new Pages(pageNum,total,currentPage,lists);
			return new ResponseObject(GeneralStatus.success.status,GeneralStatus.success.enDetail,pages);
		}else{
			return new ResponseObject(GeneralStatus.query_error.status,GeneralStatus.query_error.enDetail,"");
		}
	}

	@Override
	public ResponseObject queryByVisual(CommonParameters query, String strJson) {
		// TODO Auto-generated method stub
		return null;
	}

}
