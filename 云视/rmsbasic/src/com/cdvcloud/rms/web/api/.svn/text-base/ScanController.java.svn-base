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
import com.cdvcloud.rms.service.IScanService;

@Controller
@RequestMapping(value="{companyId}/{appCode}/{userId}/{serviceCode}/")
public class ScanController {
	private static final Logger logger = Logger.getLogger(ScanController.class);
	@Autowired
	private IScanService scanService;
	
	@RequestMapping(value="v1/api/scan/addScan/")
	@ResponseBody
	public ResponseObject addScan(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try{
			resObj = scanService.createObject(commonParameters, strJson);
		}catch(Exception e){
			logger.error("系统内部错误，创建失败" + e);
			e.printStackTrace();
			resObj=new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	@RequestMapping(value="v1/api/scan/findScanAll/")
	@ResponseBody
	public ResponseObject findScanAll(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try{
			resObj = scanService.findObjectAll(commonParameters, strJson);
		}catch(Exception e){
			logger.error("系统内部错误，创建失败" + e);
			e.printStackTrace();
			resObj=new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 根据id删除一条信息 */
	@RequestMapping(value = "v1/api/scan/deleteScan/")
	@ResponseBody
	public ResponseObject deleteScan(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			resObj = scanService.deleteObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，删除失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 根据id更新一条信息 */
	@RequestMapping(value = "v1/api/scan/updateScan/")
	@ResponseBody
	public ResponseObject updateScan(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			resObj = scanService.updateObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 根据id获取一条信息 */
	@RequestMapping(value = "v1/api/scan/getScan/")
	@ResponseBody
	public ResponseObject getScan(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request) {
		ResponseObject resObj = null;
		commonParameters.setVersionId("v1");
		try {
			resObj = scanService.findObjectById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
}
