package com.cdvcloud.rms.web.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.service.INoticeService;
import com.cdvcloud.rms.service.IOssService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.UserUtil;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/notice")
public class NoticeController {
	private static final Logger logger = Logger.getLogger(NoticeController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IUserService userService;
	@Autowired
	IOssService ossService;

	/** 添加公告类型 */
	@RequestMapping(value = "addNoticeType/")
	@ResponseBody
	public ResponseObject addNoticeType(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			resObj = noticeService.createNoticeType(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，创建公告类型失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 获取公告类型列表 */
	@RequestMapping(value = "findNoticeTypeAll/")
	@ResponseBody
	public ResponseObject findNoticeTypeAll(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			resObj = noticeService.findNoticeTypeAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取公告类型列表失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 根据id获取公告类型 */
	@RequestMapping(value = "findNoticeTypeById/")
	@ResponseBody
	public ResponseObject findNoticeTypeById(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = noticeService.findNoticeTypeById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取公告类型失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 删除公告类型 */
	@RequestMapping(value = "deleteNoticeType/")
	@ResponseBody
	public ResponseObject deleteNoticeType(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = noticeService.deleteNoticeType(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，删除公告类型失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 更新公告类型 */
	@RequestMapping(value = "updateNoticeType/")
	@ResponseBody
	public ResponseObject updateNoticeType(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = noticeService.updateNoticeType(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新公告类型失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 添加公告 */
	@RequestMapping(value = "addNotice/")
	@ResponseBody
	public ResponseObject addNotice(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			resObj = noticeService.createNotice(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，创建公告失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 获取公告列表 */
	@RequestMapping(value = "findNotices/")
	@ResponseBody
	public ResponseObject findNotices(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson,
			HttpServletRequest request) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(commonParameters.getUserId());
			commonParameters = UserUtil.getUserInfo(commonParameters, userMap);
			resObj = noticeService.findNoticeAll(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取公告失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 根据id获取公告 */
	@RequestMapping(value = "findNoticeById/")
	@ResponseBody
	public ResponseObject findNoticeById(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = noticeService.findNoticeById(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，获取公告失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 删除公告 */
	@RequestMapping(value = "deleteNotice/")
	@ResponseBody
	public ResponseObject deleteNotice(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = noticeService.deleteNotice(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，删除公告失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 批量删除公告 */
	@RequestMapping(value = "deleteNotices/")
	@ResponseBody
	public ResponseObject deleteNotices(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = noticeService.deleteNotices(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，删除公告失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/** 更新公告 */
	@RequestMapping(value = "updateNotice/")
	@ResponseBody
	public ResponseObject updateNotice(@PathVariable String appCode, @PathVariable String versionId, @PathVariable String companyId,
			@PathVariable String userId, @PathVariable String serviceCode, @Valid CommonParameters commonParameters, @RequestBody String strJson) {
		ResponseObject resObj = null;
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(commonParameters, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			resObj = noticeService.updateNotice(commonParameters, strJson);
		} catch (Exception e) {
			logger.error("系统内部错误，更新公告失败" + e);
			e.printStackTrace();
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
		return resObj;
	}

	/**
	 * 上传公告图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "uploadNoticePicture/")
	@ResponseBody
	public Map<String, Object> uploadNoticePicture(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		try {
			// String ossPath = "dz_cloud" + FileUtil.linuxDatePath();
			logger.info("上传公告图片开始!");
			// 文件保存路径
			String savePath = request.getSession().getServletContext().getRealPath(File.separator) + "tmp" + File.separator;
			// 如果当前文件夹不存在，则创建
			FileUtil.createFile(savePath);
			if (!(request instanceof MultipartHttpServletRequest)) {
				return null;
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> mapFile = multipartRequest.getFileMap();
			String strFileName = "", strSuffix = "";
			long longSize = 0L;
			for (Map.Entry<String, MultipartFile> entity : mapFile.entrySet()) {
				MultipartFile mf = entity.getValue();
				String strOriginalFilename = mf.getOriginalFilename();
				int intStart = strOriginalFilename.lastIndexOf(".");
				// 获取文件名称
				strFileName = strOriginalFilename.substring(0, intStart); // 1.云视营业执照副本
				// 获取文件后缀名
				strSuffix = strOriginalFilename.substring(intStart); // .jpg
				// 使用uuid重新命名文件
				String strUUIDName = StringUtil.randomUUID();
				String destFileName = strUUIDName + strSuffix;
				savePath += destFileName;
				logger.info("图片的保存地址为[" + savePath + "]");
				// 文件访问地址
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				String strFullHttpPath = basePath + "/tmp/" + destFileName;
				logger.info("图片的访问地址为[" + strFullHttpPath + "]");
				File file = new File(savePath);
				InputStream in;
				in = mf.getInputStream();
				// 2M写一次
				byte[] buffer = new byte[2097152];
				FileOutputStream out = new FileOutputStream(file);
				int ins = -1;
				while ((ins = in.read(buffer)) != -1) {
					out.write(buffer, 0, ins);
				}
				in.close();
				out.flush();
				out.close();
				longSize = file.length();
				// Config ossconfig = ossConfigService.selectConfig();
				// 目前上传oss屏蔽，返回本地访问地址
				// if (null != ossconfig) {
				// logger.info("logo上传完毕，执行上传oss!");
				// // ossConfigService.uploadOss(savePath, "3", destFileName,
				// // ossconfig, ossPath, request);
				// } else {
				// logger.error("oss数据库配置为空！");
				// }
				// String filePath = ossconfig.getExtranetUrl() + ossPath +
				// destFileName;
				// logger.info("文件访问地址：" + filePath);
				mapResponse.put("original", strFileName + strSuffix);
				mapResponse.put("name", strFileName + strSuffix);
				mapResponse.put("url", strFullHttpPath);
				mapResponse.put("size", longSize);
				mapResponse.put("type", strSuffix);
				mapResponse.put("state", "SUCCESS");
			}

		} catch (IOException e) {
			logger.error("上传图片异常:" + e.getMessage());
			mapResponse.put("state", "");
		}
		logger.info("上传图片最终返回的结果为" + mapResponse);
		return mapResponse;
	}

}
