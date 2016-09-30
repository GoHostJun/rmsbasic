package com.cdvcloud.rms.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.DownloadService;

/**
 * 下载
 * 
 * @author TYW
 * 
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/download")
public class DownloadController {

	@Autowired
	private DownloadService downloadService;
	@Autowired
	private ValidateCommonParam validateCommonParam;
	
	/**
	 * 下载单个文件
	 * 
	 * @param appCode
	 * @param versionId
	 * @param companyId
	 * @param userId
	 * @param serviceCode
	 * @param commonParameters
	 * @param strId
	 */
	@RequestMapping(value = "download/")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String appCode, @PathVariable String versionId,
			@PathVariable String companyId, @PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters,
			@RequestParam(value = "id") String strId) {
		//校验参数
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters);
		if (!validParam) {
			return;
		}
		downloadService.download(request, response, commonParameters, strId);
	}

	/**
	 * 打包下载文件
	 * 
	 * @param request
	 * @param response
	 * @param appCode
	 * @param versionId
	 * @param companyId
	 * @param userId
	 * @param serviceCode
	 * @param commonParameters
	 * @param strId
	 */
	@RequestMapping(value = "download4zip/")
	public void download4zip(HttpServletRequest request, HttpServletResponse response, @PathVariable String appCode, @PathVariable String versionId,
			@PathVariable String companyId, @PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters,
			@RequestParam(value = "id") String strId) {
		//校验参数
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters);
		if (!validParam) {
			return;
		}
		downloadService.download4zip(request, response, commonParameters, strId);
	}
	/**
	 * 打包下载素材文件
	 * 
	 * @param request
	 * @param response
	 * @param appCode
	 * @param versionId
	 * @param companyId
	 * @param userId
	 * @param serviceCode
	 * @param commonParameters
	 * @param strId
	 */
	@RequestMapping(value = "downLoadMaterialZip/")
	public void downLoadMaterialZip(HttpServletRequest request, HttpServletResponse response, @PathVariable String appCode, @PathVariable String versionId,
			@PathVariable String companyId, @PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters,
			@RequestParam(value = "id") String strId,	@RequestParam(value = "type") String type){
		//校验参数
		boolean validParam = validateCommonParam.validateCommonParam(commonParameters);
		if (!validParam) {
			return;
		}
		downloadService.downLoadMaterialZip(request, response, commonParameters, strId,type);
	}
}
