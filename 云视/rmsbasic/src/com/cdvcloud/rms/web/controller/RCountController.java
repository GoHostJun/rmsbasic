package com.cdvcloud.rms.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdvcloud.rms.common.CommonParameters;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/count")
public class RCountController {

	@RequestMapping(value = "toCount/")
	public String toCount(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		
		return "count/count";
	}
	
	@RequestMapping(value = "toReport/")
	public String toReport(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		
		return "count/report";
	}
}
