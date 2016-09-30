package com.cdvcloud.rms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdvcloud.rms.common.CommonParameters;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/directpass")
public class RDirectPassingController {

	@RequestMapping(value = "toDirectPassList/")
	public String toDirectPassList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "directpass/directpass_list";
	}
}
