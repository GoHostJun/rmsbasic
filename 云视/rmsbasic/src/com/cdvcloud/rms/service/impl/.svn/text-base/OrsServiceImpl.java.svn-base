package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.vo.OrsVo;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IOrsService;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
@Service
public class OrsServiceImpl implements IOrsService {
	private static final Logger logger = Logger.getLogger(OrsServiceImpl.class);
	@Autowired
	ConfigurationService configurationService;

	@Override
	public void sendOrs(List<OrsVo> ors) {

		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
		for (OrsVo orsVo : ors) {
			lists.add(orsVo.toMap());
		}
		//获取工具集地址
		String url =configurationService.getORSAPI();
		//拼装首帧截图url地址
		url=url+"api/add/";
		String json = JsonUtil.writeList2JSON(lists);
		//		String json = "{"+JsonUtil.writeList2JSON(lists)+"}";
		//任务创建失败重试机制
		boolean task = true;
		int i = 0;

		logger.info("发送ors  url="+url+";内容="+json);
		while (task) {
			i++;
			if(i==5){
				task=false;
			}
			String ret = HttpUtil.doPost(url, json);
			logger.info("发送ors返回内容："+ret);
			Map<String, Object> map =  JsonUtil.readJSON2Map(ret);
			//如果返回创建不成功重试
			if(null!=map&&200==Integer.valueOf(String.valueOf(map.get("status")))){
				//创建成功关闭循环
				task=false;
			}
			
		}
	}

}
