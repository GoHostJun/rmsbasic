package com.cdvcloud.rms.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdvcloud.rms.util.HttpUtil;


public class UploadFileToPPThread implements Runnable {
	private static final Logger logger = Logger.getLogger(UploadFileToPPThread.class);
	
	private List<Map<String, Object>> listFileUrl;
	private String fid;
	private String uid;
	private String sendUrl;

	public UploadFileToPPThread(List<Map<String, Object>> listFileUrl, String fid, String uid, String sendUrl) {
		this.listFileUrl = listFileUrl;
		this.fid = fid;
		this.uid = uid;
		this.sendUrl = sendUrl;
	}

	@Override
	public void run() {
		if (null != sendUrl && null != fid && null != uid && listFileUrl.size() > 0) {
			for (Map<String, Object> mapFile : listFileUrl) {
				String wanurl = String.valueOf(mapFile.get("wanurl"));
				try {
					wanurl = URLEncoder.encode(wanurl, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String fmt = String.valueOf(mapFile.get("fmt"));
				String paramJson = "uid="+uid +"&fid=" + fid +"&ext=" + fmt + "&upload=" + wanurl;
				logger.info("发送拍拍文件，地址：" + sendUrl + "，参数：" + paramJson);
				String result = HttpUtil.sendPost(sendUrl, paramJson);
				logger.info("返回参数："+result);
			}
		}else{
			logger.warn("参数不正确，推送失败！sendUrl="+sendUrl+",fid="+fid+",uid="+uid+",listFileUrl="+listFileUrl);
		}
	}

}
