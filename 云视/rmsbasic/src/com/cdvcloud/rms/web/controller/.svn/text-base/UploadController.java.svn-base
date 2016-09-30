package com.cdvcloud.rms.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IOssService;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.UserUtil;
import com.cdvcloud.upload.config.Configurations;

@Controller
@RequestMapping(value = "upload/")
public class UploadController {

	public static String savePath = null;
	private final static Logger logger = Logger.getLogger(UploadController.class);
	@Autowired
	private IOssService ossService;
	@Autowired
	private ConfigurationService configurationService;

	/**
	 * 获得上传页面并指定上传文件的保存路径
	 * 
	 * @param request
	 * @param id
	 *            素材id
	 * @param m_type
	 *            素材类型（音频、视频）
	 * @param mg
	 *            m:素材g:成品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toUpload/")
	public String toUpload(HttpServletRequest request, @RequestParam(value = "m_type") String m_type) {
		String code = configurationService.getCompany();// 设置企业
		String suffix = "";
		if ((Constants.VIDEO_TYPE).equals(m_type)) {
			suffix = Configurations.getStreamVideo();
		} else if ((Constants.AUDIO_TYPE).equals(m_type)) {
			suffix = Configurations.getStreamAudio();
		} else if ((Constants.PICTURE_TYPE).equals(m_type)) {
			suffix = Configurations.getStreamPicture();
		} else if (("va").equals(m_type)) {
			suffix = Configurations.getStreamAudio() + Configurations.getStreamVideo() + Configurations.getStreamText();
		} else if (("excel").equals(m_type)) {
			suffix = ".xlsx*.xls*.t";
		} else if (("t").equals(m_type)) {
			suffix = Configurations.getStreamText();
		} else {
			suffix = Configurations.getStreamVideo() + Configurations.getStreamAudio() + Configurations.getStreamText()
					+ Configurations.getStreamPicture();
		}
		request.setAttribute("suffix", suffix);
		// request.setAttribute("id", id);
		// request.setAttribute("mg", mg);
		request.setAttribute("mType", m_type);
		request.setAttribute("code", code);
		return "upload/upload";
	}

	/**
	 * 上传文件到指定存储路径
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "pictureUpload/")
	@ResponseBody
	public String uploadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "sid") String strId) {
		String strResultMsg = "";
		try {
			savePath = Configurations.getConfig("STREAM_FILE_REPOSITORY").replace("*", "");
			String strFilePath = File.separator + "pictures" + FileUtil.datePath();
			// 此方法只针对于缩略图有效，当uploadsid不为空的时候删除上一张图片,
			String path = "";
			String sqlHttpPath = "";
			String fileType = "";// 文件的后缀
			String fileName = "";
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			String uuidname = UUID.randomUUID().toString();
			String failpath = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				String originalFilename = mf.getOriginalFilename();
				int startIndex = originalFilename.lastIndexOf(".");
				fileName = originalFilename.substring(0, startIndex);
				fileType = originalFilename.substring(startIndex);
				sqlHttpPath = strFilePath + uuidname + fileType;
				sqlHttpPath = sqlHttpPath.replaceAll("\\\\", "/");
				path = savePath + strFilePath;
				FileUtil.createFile(path);
				failpath = savePath + sqlHttpPath;
				File file = new File(failpath);
				InputStream in;
				in = mf.getInputStream();
				byte[] buffer = new byte[1024 * 1024];
				FileOutputStream out = new FileOutputStream(file);
				int ins = -1;
				while ((ins = in.read(buffer)) != -1) {
					out.write(buffer, 0, ins);
				}
				in.close();
				out.flush();
				out.close();
			}
			// sqlHttpPath = configurationUtil.getSourceHttpLen() +
			// splitPath(failpath);
			sqlHttpPath.replaceAll("\\\\", "/");
			StringBuffer sbResultMsg = new StringBuffer();
			sbResultMsg.append("{'saveHttpPath':'");
			sbResultMsg.append(sqlHttpPath);
			sbResultMsg.append("','id':'");
			sbResultMsg.append(strId);
			sbResultMsg.append("','name':'");
			sbResultMsg.append(fileName);
			sbResultMsg.append("'}");
			strResultMsg = sbResultMsg.toString();
		} catch (IOException e) {
			strResultMsg = "";
			logger.error("文件上传异常：" + e.getMessage());
		}
		return strResultMsg;
	}

	/**
	 * 上传缩略图
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "thumbnail/")
	@ResponseBody
	public String fileUpload(@RequestParam(value = "id") String id, HttpServletRequest request) throws Exception {
		CommonParameters common = new CommonParameters();
		common = UserUtil.getUserInfo(request, common);
		common.setAppCode(Constants.APPCODEVALUE);
		common.setVersionId("v1");
		common.setServiceCode(Constants.SERVICECODEVALUE);
		String path = Configurations.getConfig("STREAM_FILE_REPOSITORY").replace("*", "") + File.separator;
		File buildfile = new File(path);
		if (!buildfile.exists() && !buildfile.isDirectory()) {
			buildfile.mkdir();
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String sqlLocalPath = "";
		String uuid = UUID.randomUUID().toString();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String originalFilename = mf.getOriginalFilename();
			int startIndex = originalFilename.lastIndexOf(".");
			String fileType = originalFilename.substring(startIndex);
			sqlLocalPath = uuid + fileType;
			path = path + sqlLocalPath;
			File file = new File(path);
			InputStream in = mf.getInputStream();
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
			// 保存数据库数据
			// 传oss
			path = FileUtil.getSystemPath(path);
			ret = ossService.uploadOss(common, path, sqlLocalPath, "thumbnail");
		}
		return String.valueOf(ret.get(Media.WANURL));
	}

	/**
	 * 上传缩略图
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadNoticePicture/")
	@ResponseBody
	public String uploadNoticePicture(@RequestParam(value = "id") String id, HttpServletRequest request) throws Exception {
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		CommonParameters common = new CommonParameters();
		common = UserUtil.getUserInfo(request, common);
		common.setAppCode(Constants.APPCODEVALUE);
		common.setVersionId("v1");
		common.setServiceCode(Constants.SERVICECODEVALUE);
		String path = Configurations.getConfig("STREAM_FILE_REPOSITORY").replace("*", "") + File.separator;
		File buildfile = new File(path);
		if (!buildfile.exists() && !buildfile.isDirectory()) {
			buildfile.mkdir();
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		if (!(request instanceof MultipartHttpServletRequest)) {
			mapResponse.put("imageActionName", "uploadimage");
			mapResponse.put("imageFieldName", "upfile");
			mapResponse.put("imageCompressEnable", true);
			mapResponse.put("imageInsertAlign", "none");
			return JsonUtil.writeMap2JSON(mapResponse);
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String sqlLocalPath = "";
		String uuid = UUID.randomUUID().toString();
		long longSize = 0L;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String originalFilename = mf.getOriginalFilename();
			int startIndex = originalFilename.lastIndexOf(".");
			String fileType = originalFilename.substring(startIndex);
			sqlLocalPath = uuid + fileType;
			path = path + sqlLocalPath;
			File file = new File(path);
			InputStream in = mf.getInputStream();
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
			// 保存数据库数据
			// 传oss
			path = FileUtil.getSystemPath(path);
			ret = ossService.uploadOss(common, path, sqlLocalPath, "thumbnail");
			mapResponse.put("original", originalFilename);
			mapResponse.put("name", originalFilename);
			mapResponse.put("url", String.valueOf(ret.get(Media.WANURL)));
			mapResponse.put("size", longSize);
			mapResponse.put("type", fileType);
			mapResponse.put("state", "SUCCESS");
		}
		String resultJson = JsonUtil.writeMap2JSON(mapResponse);
		return resultJson;
	}

	@RequestMapping(value = "towebUpload/")
	public String towebUpload(HttpServletRequest request, HttpServletResponse response) {

		return "upload/webUploader";
	}

}
