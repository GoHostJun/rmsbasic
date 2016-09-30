package com.cdvcloud.rms.web.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.service.ICallBackService;


@Controller
@RequestMapping(value = "collback/")
public class CallBackController {
	private static final Logger logger = Logger.getLogger(CallBackController.class);
	@Autowired
	private  ICallBackService callBackService;
	/**
	 * 截图返回
	 * @param taskId
	 * @param status
	 * @param pointscreen
	 * @return
	 */
	@RequestMapping(value = "screenShot/")
	@ResponseBody
	public String collbackScreenShot(String taskId,String status,String pointscreen){
		logger.info("截图返回内容：taskId="+taskId+",status="+status+",pointscreen="+pointscreen);
		//入库    ---   读取任务表    ----是否转码--- 
		callBackService.manageScreenshot(taskId, status, pointscreen);
		return "";
	}
	/**
	 * 转码返回
	 * @param taskId
	 * @param status
	 * @param fixedInfo
	 * @return
	 */
	@RequestMapping(value = "transcode/")
	@ResponseBody
	public String collbacktranscode(String taskId,String status,String fixedInfo){
		logger.info("转码返回内容：taskId="+taskId+",status="+status+",fixedInfo="+fixedInfo);
		//分析   --- 入库
		callBackService.manageTranscode(taskId, status, fixedInfo);
		return "";
	}
	/**
	 * 推送转码返回
	 * @param taskId
	 * @param status
	 * @param fixedInfo
	 * @return
	 */
	@RequestMapping(value = "pushTranscode/")
	@ResponseBody
	public String collbackPushTranscode(String taskId,String status,String fixedInfo){
		logger.info("转码返回内容：taskId="+taskId+",status="+status+",fixedInfo="+fixedInfo);
		//分析   --- 入库
		callBackService.managePushTranscode(taskId, status, fixedInfo);
		return "ok";
	}
	/**
	 * 快编返回
	 * @param taskId
	 * @param status
	 * @param fixedInfo
	 * @return
	 */
	@RequestMapping(value = "fastedit/")
	@ResponseBody
	public String collbackfastedit(String taskId,String status,String fixedInfo){
		logger.info("快编返回内容：taskId="+taskId+",status="+status+",fixedInfo="+fixedInfo);
		//分析   --- 入库
		callBackService.manageFastedit(taskId, status, fixedInfo);
		return "";
	}
}
