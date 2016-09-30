package com.cdvcloud.rms.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdvcloud.rms.common.CommonParameters;

/**
 * 公告Controller
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/notice")
public class RNoticeController {
	
	@RequestMapping(value = "toNoticeMain/")
	public String toNoticeMain(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		// 参数日志采集

		return "notice/notice_main";
	}

	@RequestMapping(value = "toNoticeList/")
	public String toNewsMyCreateList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		// 参数日志采集

		return "notice/affiche_list";
	}

	
	/**
	 * 
	 * @param appCode
	 * @param versionId
	 * @param companyId
	 * @param userId
	 * @param serviceCode
	 * @param commonParameters
	 * @return
	 */
	@RequestMapping(value = "toNoticeDetail/")
	public String toNoticeDetail(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		//参数日志采集
		
		return "notice/affiche_detail";
	}
	
	/**
	 * 
	 * @param appCode
	 * @param versionId
	 * @param companyId
	 * @param userId
	 * @param serviceCode
	 * @param commonParameters
	 * @return
	 */
	@RequestMapping(value = "indexToNoticeDetail/")
	public String indexToNoticeDetail(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		//参数日志采集
		
		return "notice/index_detail_notice";
	}
	
	@RequestMapping(value = "indexToNoticeDetailMain/")
	public String indexToNoticeDetailMain(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters) {
		// 参数日志采集

		return "notice/index_to_notice_detail_main";
	}
}
