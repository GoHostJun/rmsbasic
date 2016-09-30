package com.cdvcloud.rms.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IAnalysisService;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
@Service
public class AnalysisServiceImpl implements IAnalysisService {
	private static final Logger logger = Logger.getLogger(AnalysisServiceImpl.class);
	@Autowired
	ConfigurationService configurationService;

	@Override
	public Map<String, Object> getAnalysis(CommonParameters common,
			String srcFileUrl) {
		//获取工具集地址
		String url =configurationService.getDigitalFactoryAPI();
		//拼装文件分析url地址
		url+=common.getOldPublicAddress("FENX")+"analysis/task/create/";
		//拼装文件分析参数
		//做编码
		try {
			srcFileUrl=URLEncoder.encode(srcFileUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String param="accessToken="+configurationService.getToken(common)+"&timeStamp="+System.currentTimeMillis()+"&srcFileUrl="+srcFileUrl;
		int index =0;
		return getretryAnalysis(url, param,index);
	}

	public String buibui(String url,String param) {
		return  HttpUtil.sendPost(url, param);
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getretryAnalysis(String url, String param,int index) {

		if(index==5){
			return new HashMap<String, Object>();
		}
		logger.info("调用分析接口第"+index+"次：url="+url+",内容="+param);
		String ret = buibui(url, param);
		logger.info("分析返回="+ret);
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(ret)){
			Map<String, Object> retmap = JsonUtil.readJSON2Map(ret);
			if(null!=retmap&&"0".equals(String.valueOf(retmap.get("code")))){
				if(!StringUtil.isEmpty(retmap)&&!StringUtil.isEmpty(retmap.get("code"))){
					Map<String, Object> data = (Map<String, Object>)retmap.get("data");
					Map<String, Object> contents = (Map<String, Object>)data.get("contents");
					Map<String, Object> general = (Map<String, Object>)contents.get("general");
					Map<String, Object> video = (Map<String, Object>)contents.get("video");
					Map<String, Object> image = (Map<String, Object>)contents.get("image");
					Map<String, Object> audio = (Map<String, Object>)contents.get("audio");
					if(general.containsKey("fileSize")&&!StringUtil.isEmpty(general.get("fileSize"))){
						map.put(Media.SIZE, general.get("fileSize"));
					}else{
						map.put(Media.SIZE, "0");
					}
					if(general.containsKey("fileExtension")&&!StringUtil.isEmpty(general.get("fileExtension"))){
						map.put(Media.FMT, general.get("fileExtension"));
					}else{
						map.put(Media.FMT, "");
					}
					if(!StringUtil.isEmpty(video)){
						if(video.containsKey("frameRate")&&!StringUtil.isEmpty(video.get("frameRate"))){
							map.put(Media.FRAME, video.get("frameRate"));
						}else{
							if(general.containsKey("overallBitRate")&&!StringUtil.isEmpty(general.get("overallBitRate"))){
								map.put(Media.FRAME, general.get("overallBitRate"));
							}else{
								map.put(Media.FRAME, "");
							}
						}
						if(video.containsKey("height")&&!StringUtil.isEmpty(String.valueOf(video.get("height")))){
							map.put(Media.HEIGHT, video.get("height"));
						}else{
							map.put(Media.HEIGHT, "");
						}
						if(video.containsKey("width")&&!StringUtil.isEmpty(String.valueOf(video.get("width")))){
							map.put(Media.WIDTH, video.get("width"));
						}else{
							map.put(Media.WIDTH, "");
						}
						if(video.containsKey("bitRate")&&!StringUtil.isEmpty(String.valueOf(video.get("bitRate")))){
							map.put(Media.RATE, video.get("bitRate"));
						}else{
							map.put(Media.RATE, "");
						}
						if(video.containsKey("vedioSize")&&!StringUtil.isEmpty(String.valueOf(video.get("vedioSize")))){
							map.put(Media.VEDIOSIZE, video.get("vedioSize"));
						}else{
							map.put(Media.VEDIOSIZE, "");
						}
						if(video.containsKey("format")&&!StringUtil.isEmpty(String.valueOf(video.get("format")))){
							map.put(Media.FORMAT, video.get("format"));
						}else{
							map.put(Media.FORMAT, "");
						}

						if(video.containsKey("duration")&&!StringUtil.isEmpty(String.valueOf(video.get("duration")))){
							map.put(Media.DURATION, video.get("duration"));
						}else{
							if(general.containsKey("duration")&&!StringUtil.isEmpty(String.valueOf(general.get("duration")))){
								map.put(Media.DURATION, general.get("duration"));
							}else{
								map.put(Media.DURATION, "0");
							}
						}
					}
					if(!StringUtil.isEmpty(audio)){
						if(audio.containsKey("bitRate")&&!StringUtil.isEmpty(String.valueOf(audio.get("bitRate")))){
							map.put(Media.RATE, audio.get("bitRate"));
						}else{
							map.put(Media.RATE, "");
						}
						if(audio.containsKey("duration")&&!StringUtil.isEmpty(String.valueOf(audio.get("duration")))){
							map.put(Media.DURATION, audio.get("duration"));
						}else{
							if(general.containsKey("duration")&&!StringUtil.isEmpty(String.valueOf(general.get("duration")))){
								map.put(Media.DURATION, general.get("duration"));
							}else{
								map.put(Media.DURATION, "0");
							}
						}
					}
					if(!StringUtil.isEmpty(image)){
						if(image.containsKey("height")&&!StringUtil.isEmpty(String.valueOf(image.get("height")))){
							map.put(Media.HEIGHT, image.get("height"));
						}else{
							map.put(Media.HEIGHT, "0");
						}
						if(image.containsKey("width")&&!StringUtil.isEmpty(String.valueOf(image.get("width")))){
							map.put(Media.WIDTH, image.get("width"));
						}else{
							map.put(Media.WIDTH, "0");
						}
					}
				}
			}else{
				index++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getretryAnalysis(url, param,index);
			}
		}else{
			index++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getretryAnalysis(url, param,index);
		}
		return map;
	}
}
