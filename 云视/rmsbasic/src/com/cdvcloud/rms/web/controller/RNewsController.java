package com.cdvcloud.rms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 通联Controller
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/rms/news")
public class RNewsController {

	@RequestMapping(value = "toNews/")
	public String toNews(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		String roleString = UserUtil.getUserRole(request);
		if(null != roleString && roleString.indexOf("通联人") != -1){
			return "index/index_news";
		}
		return "index/index_person";
	}
	
	@RequestMapping(value = "toJSIndex/")
	public String toJSIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		
		return "index/index_jiangsu";
	}
	
	@RequestMapping(value = "toJXIndex/")
	public String toJXIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		return "index/index_JX";
	}
	
	
	@RequestMapping(value = "toNewsModifyIndex/")
	public String toNewsCreateIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_modify_index";
	}
	
	@RequestMapping(value = "toNewsMyCreateList/")
	public String toNewsMyCreateList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/newsMyCreate_list";
	}
	
	@RequestMapping(value = "toNewsMyWaitingDealList/")
	public String toNewsMyWaitingDealList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/newsMyWaitingDeal_list";
	}
	
	@RequestMapping(value = "toNewsMyWaitingSendListIndex/")
	public String toNewsMyWaitingSendListIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/newsMyWaitingSend_list_index";
	}
	
	@RequestMapping(value = "toNewsSendIndex/")
	public String toNewsSendIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_send_index";
	}
	
	@RequestMapping(value = "toNewsSend/")
	public String toNewsSend(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_send";
	}
	
	
	@RequestMapping(value = "toNewsMyWaitingSendList/")
	public String toNewsMyWaitingSendList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/newsMyWaitingSend_list";
	}
	
	@RequestMapping(value = "toNewsCompleteList/")
	public String toNewsCompleteList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/newsComplete_list";
	}
	
	@RequestMapping(value = "toNewsCreate/")
	public String toNewsCreate(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		return "news/news_create";
	}
	@RequestMapping(value = "toNewsModify/")
	public String toNewsModify(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_modify";
	}
	@RequestMapping(value = "toNewsViewIndex/")
	public String toNewsViewIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_view_index";
	}
	@RequestMapping(value = "toNewsInnerView/")
	public String toNewsInnerView(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_inner_view";
	}
	@RequestMapping(value = "toNewsOutterView/")
	public String toNewsOutterView(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_outter_view";
	}
	
	@RequestMapping(value = "toNewsListIndex/")
	public String toNewsList(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_list_index";
	}
	@RequestMapping(value = "toNewsEdit/")
	public String toNewsEdit(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_edit";
	}
	@RequestMapping(value = "toAuditNewsListIndex/")
	public String toAuditNewsListIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_audit_list_index";
	}
	@RequestMapping(value = "toMyNewsListIndex/")
	public String toMyNewsListIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/news_my_list_index";
	}
	
	@RequestMapping(value = "toCompleteNewsListIndex/")
	public String toCompleteNewsListIndex(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId, @PathVariable String userId,
			@PathVariable String serviceCode, @Valid CommonParameters commonParameters,HttpServletRequest request, HttpServletResponse response) {
		//参数日志采集
		
		return "news/complete_news_list_index";
	}

	

}
