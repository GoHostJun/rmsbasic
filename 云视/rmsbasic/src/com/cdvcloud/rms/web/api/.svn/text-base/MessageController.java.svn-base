package com.cdvcloud.rms.web.api;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.IMessageService;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/message")
public class MessageController {
	private static final Logger logger = Logger.getLogger(MessageController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private IMessageService messageService;

	/** 添加消息 */
	@RequestMapping(value = "addMessage/")
	@ResponseBody
	public ResponseObject addMessage(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = messageService.createMessage(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，创建消息失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 获取消息列表 */
	@RequestMapping(value = "findMessages/")
	@ResponseBody
	public ResponseObject findMessages(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = messageService.findMessageAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取消息失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	/** 获取外场新闻最新动态列表 */
	@RequestMapping(value = "findFieldNewsMessages/")
	@ResponseBody
	public ResponseObject findFieldNewsMessages(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = messageService.findFieldNewsMessages(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取外场新闻最新动态列表失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 根据id获取消息 */
	@RequestMapping(value = "findMessageById/")
	@ResponseBody
	public ResponseObject findMessageById(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = messageService.findMessageById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取消息失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 更新消息状态 */
	@RequestMapping(value = "updateMessageStatus/")
	@ResponseBody
	public ResponseObject updateMessageStatus(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = messageService.updateMessage(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新消息状态失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 更新消息状态 */
	@RequestMapping(value = "updateNewsStatus/")
	@ResponseBody
	public ResponseObject updateNewsStatus(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = messageService.updateMessageByNewsId(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新消息状态失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
	
	/** 更新消息状态 */
	@RequestMapping(value = "updateBtachNewsStatus/")
	@ResponseBody
	public ResponseObject updateBtachNewsStatus(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = messageService.updateBtachNewsStatus(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新消息状态失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}
}
