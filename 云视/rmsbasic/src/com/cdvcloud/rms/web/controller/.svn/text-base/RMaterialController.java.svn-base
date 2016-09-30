package com.cdvcloud.rms.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cdvcloud.rms.common.CommonParameters;

@Controller
public class RMaterialController {



	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toDocs/")
	public String toDocs(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		return "docs/docs_list";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/createNews/")
	public String createNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		return "docs/create_news";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/modifyNews/")
	public String modifyNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		return "docs/modify_news";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toAddDocs/")
	public String toAddDocs(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		return "docs/docs_add";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toEditDocs/")
	public ModelAndView toEditDocs(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,@RequestParam(value="docId") String docId) {
		ModelAndView model = new ModelAndView("docs/docs_edit","docId",docId);
		return model;
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toViewDocs/")
	public ModelAndView toViewDocs(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,@RequestParam(value="docId") String docId) {
		ModelAndView model = new ModelAndView("docs/docs_view","docId",docId);
		return model;
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toReferDocsList/")
	public ModelAndView toReferDocsList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,@RequestParam(value="mId") String mId,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("docs/docs_refer_list","mId",mId);
		return model;
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toMaterial/")
	public String toMaterial(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		return "material/material_list";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toMaterialPreview/")
	public ModelAndView toMaterialPreview(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("material/material_preview");
		return model;
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toMaterialEdit/")
	public ModelAndView toMaterialEdit(HttpServletRequest request,@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		String mId = request.getParameter("mId");
		ModelAndView model = new ModelAndView("material/material_edit","mId",mId);
		return model;
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toMaterialDown/")
	public String toMaterialDown(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		return "material/material_down";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toFastCode/")
	public ModelAndView toFastCode(HttpServletRequest request,@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		String ids = request.getParameter("ids");
		ModelAndView model = new ModelAndView("fastcode/fastcode","ids",ids);
		return model;
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toDocsListIndex/")
	public String toDocsListIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		return "docs/docs_list_index";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/material/toMaterialListIndex/")
	public String toMaterialListIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		return "material/material_list_index";
	}
	@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/docs/toDocsService/")
	public String toDocsService(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		return "docs/docs_service";
	}
}
