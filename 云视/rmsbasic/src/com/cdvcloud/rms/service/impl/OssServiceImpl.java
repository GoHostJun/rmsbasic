package com.cdvcloud.rms.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.pass.upload.domin.UploadOther;
import com.cdvcloud.pass.upload.domin.UploadRequest;
import com.cdvcloud.pass.upload.domin.UploadResponse;
import com.cdvcloud.pass.upload.service.Upload;
import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IOssService;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.upload.config.Configurations;

@Service
public class OssServiceImpl implements IOssService {
	private static final Logger logger = Logger.getLogger(OssServiceImpl.class);

	@Autowired
	ConfigurationService configurationService;

	@Override
	public Map<String, Object> uploadOss(CommonParameters common, String filepath, String name, String mtype) {
		Map<String, Object> map = new HashMap<String, Object>();
		String nameid = StringUtil.randomUUID();
		if (filepath.contains("http")) {
			map.put(Media.LENURL, filepath);
			map.put(Media.WANURL, filepath);
			map.put(Media.OSSINTERNETURL, filepath);
			map.put(Media.OSSLANURL, filepath);
			map.put(Media.CDNURL, filepath);
			map.put(Media.FILEPATH, filepath);
			return map;
		}

		File file = new File(filepath);
		UploadRequest u = new UploadRequest();
		u.setAppCode(String.valueOf(Constants.APPCODEVALUE));// 应用标识 由统一资源管理提供
		u.setCompanyId(String.valueOf(common.getCompanyId()));// 企业标识 由统一资源管理提供
		u.setUserId(String.valueOf(common.getUserIdInput()));
		// 没有额外参数
		UploadOther uploadOther = new UploadOther();
		uploadOther.setUuidName(nameid);
		String url = configurationService.getOssUploadUrl();
		uploadOther.setUrl(url);
		uploadOther.setIsDelete(1);
		logger.info("调用SDK上传,参数ftpUrl=" + filepath + ";appcode=" + u.getAppCode() + ";CompanyId=" + u.getCompanyId() + ";UserId=" + u.getUserId()
				+ ";uploadOther=" + uploadOther.toString());
		String uploadurl = "";
		try {
			UploadResponse uploadResponse = Upload.getInstance().upload(file, u, uploadOther);
			uploadurl = String.valueOf(uploadResponse.getUrl());
			if (StringUtil.isEmpty(uploadurl)) {
				logger.error("上传oss失败！");
			}
		} catch (Exception e) {
			logger.error("调用SDK上传失败！" + e);
			e.printStackTrace();
		}
		if (Configurations.getBoolean("SKIP")) {
			map.put(Media.LENURL, uploadurl);
			map.put(Media.WANURL, uploadurl);
		} else {
			map.put(Media.LENURL, uploadurl);
			map.put(Media.WANURL, uploadurl);
		}
		map.put(Media.OSSINTERNETURL, uploadurl);
		map.put(Media.OSSLANURL, uploadurl);
		map.put(Media.CDNURL, uploadurl);
		map.put(Media.FILEPATH, filepath);
		return map;
	}

}
