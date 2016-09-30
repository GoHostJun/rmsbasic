package com.cdvcloud.rms.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.service.IScanLogService;

@Controller
@RequestMapping(value="{companyId}/{appCode}/{userId}/{serviceCode}/")
public class ScanLogController {
	private static final Logger logger = Logger.getLogger(ScanLogController.class);
	@Autowired
	private IScanLogService scanlogService;
	
	
	@RequestMapping(value="v1/api/scanlog/findScanlLogAll/")
	@ResponseBody
	public ResponseObject findScanAll(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try{
			resObj = scanlogService.findObjectAll(commonParameters, strJson);
		}catch(Exception e){
			logger.error("系统内部错误，查询失败" + e);
			e.printStackTrace();
			resObj=new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	
	
	/** 根据id获取一条信息 */
	@RequestMapping(value = "v1/api/scanlog/getScanLog/")
	@ResponseBody
	public ResponseObject getScan(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			resObj = scanlogService.findObjectById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
}
