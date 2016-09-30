package com.cdvcloud.rms.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdvcloud.rms.common.CommonParameters;

@Controller
public class RShareDocsController {
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/sharedocs/toEditShareDocsShare/")
	public String toEditShareDocsShare(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		return "sharedocs/sharedocs_edit_share";
	}
	
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/sharedocs/toEditShareDocsCreate/")
	public String toEditShareDocsCreate(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		return "sharedocs/sharedocs_edit_creator";
	}

}
