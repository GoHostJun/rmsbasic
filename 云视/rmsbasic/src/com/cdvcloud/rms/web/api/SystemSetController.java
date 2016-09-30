package com.cdvcloud.rms.web.api;

import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.util.HttpUtil;

@Controller
@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/set")
public class SystemSetController {
	private static final Logger logger = Logger.getLogger(SystemSetController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	
	@Autowired
	private ConfigurationService configurationService;
	
	/**
	 * 模板查询
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping("query/")
	@ResponseBody
	public ResponseObject registerResource(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters query,
			@RequestBody String strJson){
		try {
			//校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query,strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			Map<String, Object> map = JsonUtil.readJSON2Map(strJson);
			String accessToken = String.valueOf(map.get("accessToken"));
			Long timeStamp = Long.valueOf(String.valueOf(map.get("timeStamp")));
			String	url =  configurationService.getDigitalFactoryAPI();
			url+=query.getOldPublicAddress("ZHUANM")+"transcode/template/query/";
			ResponseObject resObject = getResults(accessToken,timeStamp,url);
			return resObject ;
		} catch (Exception e) {
			logger.error("模板查询接口错误："+e);
			return new ResponseObject(GeneralStatus.inner_error.status,GeneralStatus.inner_error.enDetail,"");
		}
	}
	
	public ResponseObject getResults(String accessToken,Long timeStamp,String url){
		ResponseObject resObject = new ResponseObject();
		String paramsStr = "owner=ytl&accessToken="+accessToken+"&timeStamp=" + timeStamp;
		String result = HttpUtil.sendPost(url, paramsStr);
		resObject.setCode(60004); 
		if(null == result){
			return resObject;
		}
		Map<String, Object> resultMap = JsonUtil.readJSON2Map(result);
		if(Constants.SZERO.equals(String.valueOf(resultMap.get("code")))){
			resObject.setCode(0);
			resObject.setData(resultMap);
		}else if("60001".equals(resultMap.get("code"))){
			resObject.setCode(60001);
		}else if("60002".equals(resultMap.get("code"))){
			resObject.setCode(60002);
		}else if("60003".equals(resultMap.get("code"))){
			resObject.setCode(60003);
		}else if("60005".equals(resultMap.get("code"))){
			resObject.setCode(60005);
		}else if("60006".equals(resultMap.get("code"))){
			resObject.setCode(60006);
		}
		return resObject;
	}
}

