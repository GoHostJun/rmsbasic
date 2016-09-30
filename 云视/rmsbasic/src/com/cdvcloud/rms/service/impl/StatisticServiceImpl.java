package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.IStatisticDao;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Area;
import com.cdvcloud.rms.domain.Custom;
import com.cdvcloud.rms.domain.NewsStatistic;
import com.cdvcloud.rms.service.IStatisticService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.StringUtil;
@Service
public class StatisticServiceImpl extends BasicService implements IStatisticService {
	@Autowired
	private IStatisticDao statisticDao;
	@Autowired
	private BasicDao basicDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void statistic() {
		List<Map<String,Object>>companyIds = basicDao.find(Custom.COLLECTION, 1, 10000);
		for (Map<String, Object> companyId : companyIds) {
			String companyid = String.valueOf(companyId.get("companyId"));
			List<Map<String, Object>> statictic =  statisticDao.GroupBy("news",companyid);
			List<Map<String, Object>> statictic_new = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : statictic) {
				Map<String, Object> _id = (Map<String, Object>)map.get("_id");
				String consumerid_st = String.valueOf(_id.get("consumerid"));
				if(!StringUtil.isEmpty(consumerid_st)&&companyid.equals(consumerid_st)){
					Map<String, Object> mapWhere = new HashMap<String, Object>();
					String areacode = String.valueOf(_id.get("areacode"));
					mapWhere.put(Area.CODE, areacode);
					Map<String, Object> areaMap  = basicDao.findOne(Area.AREA, mapWhere);
					if(null!=areaMap&&areaMap.containsKey("name")){
						Map<String, Object> staticticMap = new HashMap<String, Object>();
						staticticMap.put(NewsStatistic.AREANAME, areaMap.get("name"));
						staticticMap.put(NewsStatistic.AREACODE, areacode);
						staticticMap.put(NewsStatistic.COUNT, map.get("count"));
						statictic_new.add(staticticMap);
					}
				}
			}
			Map<String, Object> insert = new HashMap<String, Object>();
			insert.put(NewsStatistic.CTIME, DateUtil.getCurrentDateTime());
			insert.put(NewsStatistic.STATISTIC, statictic_new);
			insert.put(NewsStatistic.COMPANYID, companyid);
			statisticDao.insertNewsStatistic(insert);
		}
	}

	@Override
	public ResponseObject getNewsStatistic(CommonParameters commonParameters,String strJson) {
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> statistic = statisticDao.queryOne(commonParameters.getCompanyId());
		resMap.put("statistic",statistic.get("statistic"));
		executeSuccess(resObj, resMap);
		return resObj;
	}

}
