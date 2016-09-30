package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.domain.BasicObject;
import com.cdvcloud.rms.util.StringUtil;

@Service
public class BasicService {
	private static final Logger logger = Logger.getLogger(BasicService.class);

	/** 返回参数错误的验证信息 */
	public ResponseObject parameterError(Object o) {
		logger.error("输入参数不合法！" + o);
		return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
	}

	/** 返回查询失败信息 */
	public ResponseObject queryError(Object o) {
		logger.error("查询失败！" + o);
		return new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
	}

	/** 返回更新失败信息 */
	public ResponseObject updateError(Object o) {
		logger.error("更新失败！" + o);
		return new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
	}

	/** 返回删除失败信息 */
	public ResponseObject deleteError(Object o) {
		logger.error("删除失败！" + o);
		return new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
	}

	/** 返回处理失败信息 */
	public ResponseObject createError(Object o) {
		logger.error("处理失败！" + o);
		return new ResponseObject(GeneralStatus.failure.status, GeneralStatus.failure.enDetail, "");
	}

	/** 返回参数输入不合法信息 */
	public ResponseObject inputError(Object o) {
		logger.error("参数输入不合法！" + o);
		return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
	}

	/** 返回系统内部错误信息 */
	public ResponseObject innerError(Object o) {
		logger.error("系统内部错误！" + o);
		return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
	}

	/** 执行成功 */
	public void executeSuccess(ResponseObject resObj) {
		resObj.setCode(GeneralStatus.success.status);
		resObj.setMessage(GeneralStatus.success.enDetail);
	}

	/** 执行成功 */
	public void executeSuccess(ResponseObject resObj, Object data) {
		resObj.setCode(GeneralStatus.success.status);
		resObj.setMessage(GeneralStatus.success.enDetail);
		resObj.setData(data);
	}

	/** 获取公共参数(keyWord:需要模糊匹配的字段名,keyTime:需要匹配的时间字段名) */
	public Map<String, Object> getVommonalityParam(CommonParameters commonParameters, Map<String, Object> mapJson, String keyWord, String keyTime) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (null == commonParameters || null == mapJson) {
			return whereMap;
		}
		// 获取业务参数,并进行封装
		String companyId = String.valueOf(commonParameters.getCompanyId());
		if (!StringUtil.isEmpty(commonParameters.getCompanyId()) && !StringUtil.isEmpty(companyId)) {
			whereMap.put(BasicObject.CONSUMERID, companyId);
		}
		// 用户id
		String userId = String.valueOf(commonParameters.getUserId());
		String roleName = String.valueOf(commonParameters.getRoleName());
		if (!StringUtil.isEmpty(commonParameters.getUserId()) && !StringUtil.isEmpty(userId)) {
			if (!StringUtil.isEmpty(commonParameters.getRoleName()) && !"管理员".equals(roleName) && !"566fd73c84e353224410c0b6".equals(userId)) {
				whereMap.put(BasicObject.CUSERID, userId);
			}
		}
		// 关键字
		String keyWordFlag = String.valueOf(mapJson.get("keyWord"));
		if (!StringUtil.isEmpty(keyWord) && !StringUtil.isEmpty(mapJson.get("keyWord")) && !StringUtil.isEmpty(keyWordFlag)) {
			String regxValue = ".*" + keyWordFlag + ".*";
			Map<String, Object> regxMap = new HashMap<String, Object>();
			regxMap.put("$regex", regxValue);
			regxMap.put("$options", "i");
			whereMap.put(keyWord, regxMap);
		}
		// 开始时间
		String startTime = String.valueOf(mapJson.get("startTime"));
		// 结束时间
		String endTime = String.valueOf(mapJson.get("endTime"));
		if (!StringUtil.isEmpty(keyTime) && !StringUtil.isEmpty(mapJson.get("startTime")) && !StringUtil.isEmpty(startTime)
				&& !StringUtil.isEmpty(mapJson.get("endTime")) && !StringUtil.isEmpty(endTime)) {
			Map<String, Object> mapTime = new HashMap<String, Object>();
			mapTime.put(QueryOperators.GTE, startTime);
			mapTime.put(QueryOperators.LTE, endTime);
			whereMap.put(keyTime, mapTime);
		}else if (!StringUtil.isEmpty(keyTime) && !StringUtil.isEmpty(mapJson.get("startTime")) && !StringUtil.isEmpty(startTime)) {
			Map<String, Object> gteMap = new HashMap<String, Object>();
			gteMap.put(QueryOperators.GTE, startTime);
			whereMap.put(keyTime, gteMap);
		}else if (!StringUtil.isEmpty(keyTime) && !StringUtil.isEmpty(mapJson.get("endTime")) && !StringUtil.isEmpty(endTime)) {
			Map<String, Object> lteMap = new HashMap<String, Object>();
			lteMap.put(QueryOperators.LTE, endTime);
			whereMap.put(keyTime, lteMap);
		}
		logger.debug("获取公共查询参数结果whereMap：" + whereMap);
		return whereMap;
	}

	/** 获取排序参数（keySortWord：排序字段名，sort：排序值，1正序，0倒序） */
	public Map<String, Object> getSortParam(String keySortWord, String sort) {
		Map<String, Object> sortMapKey = new HashMap<String, Object>();
		if (Constants.SONE == sort) {
			sortMapKey.put(keySortWord, 1);
		} else {
			sortMapKey.put(keySortWord, -1);
		}
		return sortMapKey;
	}

	/** 获取当前页 */
	public Integer getCurrentPage(Map<String, Object> mapJson) {
		Integer currentPage = 1;
		String currentPageFlag = String.valueOf(mapJson.get("currentPage"));
		if (!StringUtil.isEmpty(mapJson.get("currentPage")) && !StringUtil.isEmpty(currentPageFlag)) {
			currentPage = Integer.valueOf(currentPageFlag);
		}
		return currentPage;
	}

	/** 获取每页条数 */
	public Integer getPageNum(Map<String, Object> mapJson) {
		Integer pageNum = 10;
		String pageNumFlag = String.valueOf(mapJson.get("pageNum"));
		if (!StringUtil.isEmpty(mapJson.get("pageNum")) && !StringUtil.isEmpty(pageNumFlag)) {
			pageNum = Integer.valueOf(pageNumFlag);
		}
		return pageNum;
	}

	/** 封装返回结果集 （totalRecord：总条数，currentPage：当前页，pageNum：每页条数） */
	public Map<String, Object> getPages(List<Map<String, Object>> results, long totalRecord, Integer currentPage, Integer pageNum) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("results", results);
		resMap.put("totalRecord", totalRecord);
		resMap.put("currentPage", currentPage);
		resMap.put("pageNum", pageNum);
		return resMap;
	}
	
	/** 封装返回结果集 （totalRecord：总条数，currentPage：当前页，pageNum：每页条数） */
	public Map<String, Object> getPages(Set<Map<String, Object>> results, long totalRecord, Integer currentPage, Integer pageNum) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("results", results);
		resMap.put("totalRecord", totalRecord);
		resMap.put("currentPage", currentPage);
		resMap.put("pageNum", pageNum);
		return resMap;
	}
}
