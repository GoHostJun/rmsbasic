package com.cdvcloud.rms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 系统设置Controller
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/system")
public class RSystemController {
	@RequestMapping(value = "toPage/")
	public String organizeManage(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters,
			@RequestParam(value = "view") String view) {
		return view;
	}

	@RequestMapping(value = "toSetting/")
	public String toSetting(HttpServletRequest request, @PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters,
			@RequestParam(value = "toModule") String toModule) {
		String roleString = UserUtil.getUserRole(request);
		String nameString = UserUtil.getUserName(request);
		if (null != roleString && "管理员".equals(roleString)) {
			request.setAttribute("nomalUser", "2");
			request.setAttribute("toModule", toModule);
		} else if ("超级管理员".equals(roleString) || "超级管理员".equals(nameString)) {
			request.setAttribute("toModule", toModule);
			request.setAttribute("showDept", "show");
		} else if ("系统管理员".equals(roleString) || "system".equals(nameString)) {
			request.setAttribute("toModule", toModule);
			request.setAttribute("showDept", "show");
			request.setAttribute("systemUser", "1");
		} else {
			request.setAttribute("toModule", "logs");
			request.setAttribute("nomalUser", "1");
		}
		return "system/system";
	}

}
