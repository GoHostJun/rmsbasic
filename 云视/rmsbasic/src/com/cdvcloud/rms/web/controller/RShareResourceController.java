package com.cdvcloud.rms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdvcloud.rms.common.CommonParameters;

/**
 * 共享资源跳转controller
 * @author yumingjun
 *
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/shareresource")
public class RShareResourceController {
	
	@RequestMapping(value = "toShareList/")
	public String toShareList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters) {

		return "shareresource/shareresource_list";
	}

	@RequestMapping(value = "sendShareNews/")
	public String sendShareNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request,HttpServletResponse response) {

		return "shareresource/send_sharenews";
	}
	@RequestMapping(value = "toShareOutsize/")
	public String toShareOutsize(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request,HttpServletResponse response) {

		return "shareresource/shareresource_outsize";
	}

}
