package com.cdvcloud.rms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.cdvcloud.rms.common.CommonParameters;
@Controller
public class RI18NController {
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/i18n/updateLang/")
	@ResponseBody
	public String updateLang(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @RequestParam(value = "lang") String lang, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		Locale locale =null;
		if("en".equals(lang)){
			locale = new Locale("en", "EN"); 
		}else{
			locale = new Locale("zh", "ZH"); 
		}
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		return "";
	}
}
