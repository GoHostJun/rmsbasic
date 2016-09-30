package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.INewsDao;
import com.cdvcloud.rms.domain.News;
import com.cdvcloud.rms.service.IReportService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
@Service
public class ReportServiceImpl extends BasicService implements IReportService {
	private static final Logger logger = Logger.getLogger(ReportServiceImpl.class);
	@Autowired
	private INewsDao newsDao;

	@Override
	public ResponseObject countNewsByTime(CommonParameters commonParameters, String strJson) throws Exception {
		long dateFlag = System.currentTimeMillis();
		ResponseObject resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		// 解析json对象
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		
		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(commonParameters, mapJson, null, null);
		Map<String, Object> mapTime = new HashMap<String, Object>();
		//如果有创建人则添加通联人和审核人，三者为或者关系
		if (!StringUtil.isEmpty(whereMap.get(News.CUSERID))) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapUser = new HashMap<String, Object>();
			Map<String, Object> mapShareUser = new HashMap<String, Object>();
			Map<String, Object> mapCheckUser = new HashMap<String, Object>();
			mapShareUser.put("shareuser.userId", whereMap.get(News.CUSERID));
			mapCheckUser.put("checkuser.userId", whereMap.get(News.CUSERID));
			mapUser.put(News.CUSERID, whereMap.get(News.CUSERID));
			whereMap.remove(News.CUSERID);
			listMap.add(mapUser);
			listMap.add(mapShareUser);
			listMap.add(mapCheckUser);
			whereMap.put(QueryOperators.OR, listMap);
		}
		String dateType =String.valueOf(mapJson.get("dateType"));
		// 开始时间
		String beginDate ="";
		String endDate ="";
		if("week".equals(dateType)){
			String[] dates = DateUtil.getBeginAndEndTime("本周").split(",");
			beginDate=dates[0];
			endDate=dates[1];
		}
		if("month".equals(dateType)){
			String[] dates = DateUtil.getBeginAndEndTime("本月").split(",");
			beginDate=dates[0];
			endDate=dates[1];
		}
		if("year".equals(dateType)){
			String[] dates = DateUtil.getBeginAndEndTime("今年").split(",");
			beginDate=dates[0];
			endDate=dates[1];
		}
		if (!StringUtil.isEmpty(mapJson.get("startTime")) && !StringUtil.isEmpty(String.valueOf(mapJson.get("startTime")))) {
			beginDate = String.valueOf(mapJson.get("startTime"));
		}
		// 结束时间
		if (!StringUtil.isEmpty(mapJson.get("endTime")) && !StringUtil.isEmpty(String.valueOf(mapJson.get("endTime")))) {
			endDate = String.valueOf(mapJson.get("endTime"));
		}
		//		String beginDate = String.valueOf(mapJson.get("beginDate"));// 开始时间
		//		String endDate = String.valueOf(mapJson.get("endDate"));// 结束时间
		Date bgDate = DateUtil.stringToDate(beginDate);
		Date egDate = DateUtil.stringToDate(endDate);
		List<Map<String, Object>> infos = new ArrayList<Map<String, Object>>();// 报表数据封装集合
		List<Date> datelists = DateUtil.findDates(bgDate, egDate);// 获取开始时间到结束时间内的时间点
		if (null != datelists && datelists.size() > 61) {//时间超过两个月的按照月为单位进行统计
			List<Map<String, Object>> maplists = DateUtil.findGroupByMonth(beginDate, endDate);//按月统计
			for (int i = 0; i < maplists.size(); i++) {
				String strdate = null;
				Map<String, Object> maps = maplists.get(i);
				for (String key : maps.keySet()) {
					if ("stime" == key || "stime".equals(key)) {
						strdate = String.valueOf(maps.get(key));
						strdate = strdate.substring(0, strdate.lastIndexOf("-"));
						String starTime = maps.get(key) + " 00:00:00";
						mapTime.put(QueryOperators.GTE, starTime);
					}
					if ("etime" == key || "etime".equals(key)) {
						String endTime = maps.get(key) + " 23:59:59";
						mapTime.put(QueryOperators.LTE, endTime);
					}
				}
				whereMap.put(News.CTIME, mapTime);
				countNum(whereMap, infos, strdate);//统计
			}
		}else if(null != datelists && datelists.size() > 7){//时间超过7天的按照七天为单位进行统计
			List<Map<String, Object>> maplists = DateUtil.findWeekDate(beginDate, endDate);//按周统计
			for (int i = 0; i < maplists.size(); i++) {
				String strdate = null;
				Map<String, Object> maps = maplists.get(i);
				for (String key : maps.keySet()) {
					if ("stime" == key || "stime".equals(key)) {
						strdate = String.valueOf(maps.get(key));
						strdate = strdate.substring(0, strdate.lastIndexOf("-")+3);
						String starTime = maps.get(key) + " 00:00:00";
						mapTime.put(QueryOperators.GTE, starTime);
					}
					if ("etime" == key || "etime".equals(key)) {
						String endTime = maps.get(key) + " 23:59:59";
						strdate = strdate + "~" + maps.get(key);
						mapTime.put(QueryOperators.LTE, endTime);
					}
				}
				whereMap.put(News.CTIME, mapTime);
				countNum(whereMap, infos, strdate);//统计
			}
		}else{
			for (Date date : datelists) {
				String strdate = DateUtil.dateToString(date);
				String starTime = strdate + " 00:00:00";
				String endTime = strdate + " 23:59:59";
				mapTime.put(QueryOperators.GTE, starTime);
				mapTime.put(QueryOperators.LTE, endTime);
				whereMap.put(News.CTIME, mapTime);
				countNum(whereMap, infos, strdate);//统计
			}
		}
		logger.info("获取通联个数结束："+System.currentTimeMillis()+" 时间差："+(System.currentTimeMillis()-dateFlag));
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("statistics", infos);
		data.put("startTime", beginDate);
		data.put("endTime", endDate);
		super.executeSuccess(resObj, data);
		return resObj;
	}

	/** 统计通联个数 */
	private void countNum(Map<String, Object> whereMap,List<Map<String, Object>> infos,String strdate){
		Map<String, Object> infoMap = new HashMap<String, Object>();
		long newsNum = newsDao.countNews(whereMap);
		infoMap.put("strDate", strdate);
		infoMap.put("newsNum", newsNum);
		infos.add(infoMap);
	}

}
