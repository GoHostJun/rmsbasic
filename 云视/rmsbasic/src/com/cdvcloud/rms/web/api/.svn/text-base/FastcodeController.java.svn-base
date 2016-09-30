package com.cdvcloud.rms.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.vo.FastcodeVo;
import com.cdvcloud.rms.service.IFastCodeService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;

/**
 * 新奥特快编处理类
 * 
 * @author mcxin
 * 
 */
@RequestMapping(value = "/{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/fastcode/")
@Controller
public class FastcodeController {
	private static final Logger logger = Logger.getLogger(FastcodeController.class);
	@Autowired
	private IUserService userService;
	@Autowired
	private IFastCodeService fastCodeService;

	@RequestMapping(value = "getFastcode/")
	@ResponseBody
	public String getFastcode(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common,
			@RequestParam(value = "catalogType", required = false) String catalogType, @RequestParam(value = "page", required = false) String pageNo,
			@RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "sortMode", required = false) String sortMode,
			@RequestParam(value = "order", required = false) String order, @RequestParam(value = "materialIds", required = false) String materialIds,
			@RequestParam(value = "from", required = false) String from, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户信息
		Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
		UserUtil.getUserInfo(common, userMap);
		// 获取登录ip
		SystemLogUtil.getIp(common, request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FastcodeVo.CATALOGTYPE, catalogType);
		map.put(FastcodeVo.PAGENO, pageNo);
		map.put(FastcodeVo.KEYWORDS, keywords);
		map.put(FastcodeVo.SORTMODE, sortMode);
		map.put(FastcodeVo.ORDER, order);
		map.put(FastcodeVo.MATERIALIDS, materialIds);
		map.put(FastcodeVo.FROM, from);
		return fastCodeService.getXml(common, map);
	}

	/**
	 * * 保存下发
	 * 
	 * 快编页面传过来的xml格式的参数
	 * 
	 * @throws Exception
	 * @param fastXml
	 * @param fileName
	 * @param formatSample
	 * @param fixedInfo
	 *            固定参数
	 * @param request
	 * @return 返回下发快编任务的结果
	 */
	@RequestMapping(value = "saveFastcode/")
	@ResponseBody
	public String saveFastcode(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common,
			@RequestParam(value = "catalogType", required = false) String catalogType,
			@RequestParam(value = "data", required = false) String fastXml, @RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "formatSample", required = false) String formatSample,
			@RequestParam(value = "fixedInfo", required = false) String fixedInfo, @RequestParam(value = "from", required = false) String from,
			HttpServletRequest request) {
		try {
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取登录ip
			SystemLogUtil.getIp(common, request);
			logger.info("快编任务创建参数：catalogType=" + catalogType + "fastXml=" + fastXml + "fileName=" + fileName + "formatSample=" + formatSample
					+ "fixedInfo=" + fixedInfo);
			return fastCodeService.saveFastcode(common, fastXml, fileName, formatSample, fixedInfo, from);
		} catch (Exception e) {
			logger.error("快编保存下发：" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 添加字幕或者项目
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param catalogType
	 *            2_0:字幕模板，2_1:标题字幕; -1_0:项目模板；-1_1:项目
	 * @param data
	 * @param name
	 * @param from
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addFastcode/")
	@ResponseBody
	public String addFastcode(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common,
			@RequestParam(value = "catalogType", required = false) String catalogType, @RequestParam(value = "data", required = false) String data,
			@RequestParam(value = "name", required = false) String name, @RequestParam(value = "from", required = false) String from,
			HttpServletRequest request) {
		try {
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取登录ip
			SystemLogUtil.getIp(common, request);
			logger.info("快编添加字幕或者项目参数：catalogType=" + catalogType + "data=" + data + "name=" + name + "from=" + from);
			return fastCodeService.addFastcode(common, catalogType, data, name, from);
		} catch (Exception e) {
			logger.error("快编添加字幕或者项目错误：" + e);
			return "error";
		}
	}

	/**
	 * 修改字幕或者项目
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param catalogType
	 * @param data
	 * @param fileKEY
	 * @param from
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateFastcode/")
	@ResponseBody
	public String updateFastcode(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common,
			@RequestParam(value = "catalogType", required = false) String catalogType, @RequestParam(value = "data", required = false) String data,
			@RequestParam(value = "fileKey", required = false) String fileKEY, @RequestParam(value = "from", required = false) String from,
			HttpServletRequest request) {
		try {
			logger.info("快编修改字幕或者项目参数：catalogType=" + catalogType + "data=" + data + "fileKEY=" + fileKEY + "from=" + from);
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取登录ip
			SystemLogUtil.getIp(common, request);
			return fastCodeService.updateFastcode(common, catalogType, data, fileKEY, from);
		} catch (Exception e) {
			logger.error("快编修改字幕或者项目错误：" + e);
			return "error";
		}
	}

	/**
	 * 删除字幕模板或项目
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param common
	 * @param catalogType
	 * @param fileKEY
	 * @param from
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteFastcode/")
	@ResponseBody
	public String deleteFastcode(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters common,
			@RequestParam(value = "catalogType", required = false) String catalogType,
			@RequestParam(value = "fileKey", required = false) String fileKEY, @RequestParam(value = "from", required = false) String from,
			HttpServletRequest request) {
		try {
			logger.info("快编删除字幕模板或项目参数：catalogType=" + catalogType + "fileKEY=" + fileKEY + "from=" + from);
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(common.getUserId());
			UserUtil.getUserInfo(common, userMap);
			// 获取登录ip
			SystemLogUtil.getIp(common, request);
			return fastCodeService.deleteFastcode(common, catalogType, fileKEY, from);
		} catch (Exception e) {
			logger.error("快编删除字幕模板或项目错误：" + e);
			return "error";
		}
	}
}
