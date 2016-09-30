package com.cdvcloud.rms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;


public class SendSMSThread implements Runnable {
	private static final Logger logger = Logger.getLogger(SendSMSThread.class);
	/**手机号(多个手机号中间用逗号隔开)*/
	private String mobileNum ;
	/**操作（注册register 修改 update 警报alarm 通知 notice）如果没有以上操作可以添加新的操作 */
	private String action="alarm";
	/**获取授权令牌接口返回的授权令牌（默认：12211） */
	private String accessToken = "12211";
	/**发送短信地址*/
	private String sendUrl;
	/**发送短信内容*/
	private String content;

	public SendSMSThread(String mobileNum, String action, String accessToken, String sendUrl, String content) {
		this.mobileNum = mobileNum;
		if(null != action && !"".equals(action)&&!"null".equals(action)){
			this.action = action;
		}
		if(null != accessToken && !"".equals(accessToken)&&!"null".equals(accessToken)){
			this.accessToken = accessToken;
		}
		this.sendUrl = sendUrl;
		this.content = content;
	}

	@Override
	public void run() {
		if (null != sendUrl && !"".equals(sendUrl)&& !"null".equals(sendUrl) && null != action && !"".equals(action)&& !"null".equals(action)  && null != mobileNum  && !"".equals(mobileNum)&& !"null".equals(mobileNum)) {
			Map<String, String> ms_map = new HashMap<String, String>();
			ms_map.put("content", content);
			ms_map.put("phone", mobileNum);
			ms_map.put("action", action);
			ms_map.put("accessToken", accessToken);
			logger.info("发送短信服务url="+sendUrl+";参数："+ms_map);
			String result = HttpUtil.post(ms_map, sendUrl);
			Map<String, Object> map_rems = JsonUtil.readJSON2Map(result);
			if (map_rems.get("status").toString().equals("0")) {
				logger.info("发送成功，发送短信服务返回：" + result);
			} else {
				logger.error("发送失败，发送短信服务返回：" + result);
			}
		}else{
			logger.warn("参数不正确，发送失败！sendUrl="+sendUrl+",action="+action+",accessToken="+accessToken+",mobileNum="+mobileNum+",content="+content);
		}
	}

}
