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
import com.cdvcloud.rms.service.IMaterialTemplateService;

/**
 * 
 * @author yumingjun
 *
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/directpassingpath")
public class MaterialTemplateController {
	private static final Logger logger = Logger.getLogger(MaterialTemplateController.class);
	
	@Autowired
	private IMaterialTemplateService materialTemplateService;
	
	@RequestMapping(value="/findAllMaterialTemplate/")
	@ResponseBody
	public ResponseObject findAllDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		try {
			resObj = materialTemplateService.findObjectAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，查询失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	@RequestMapping(value="/findOneMaterialTemplate/")
	@ResponseBody
	public ResponseObject findOneDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		try {
			resObj = materialTemplateService.findObjectById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，查询详情失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	@RequestMapping(value="/addMaterialTemplate/")
	@ResponseBody
	public ResponseObject addDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		try {
			resObj = materialTemplateService.createObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	@RequestMapping(value="/updateMaterialTemplate/")
	@ResponseBody
	public ResponseObject updateDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		try {
			resObj = materialTemplateService.updateObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	@RequestMapping(value="/deleteMaterialTemplate/")
	@ResponseBody
	public ResponseObject deleteDirectPassingPath(@PathVariable String appCode, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson, HttpServletRequest request){
		ResponseObject resObj = null;
		try {
			resObj = materialTemplateService.deleteObject(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，删除失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	
}
