package com.cdvcloud.rms.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.domain.Global;
import com.cdvcloud.rms.util.JsonUtil;

@Component
public class InitService {
	private static final Logger logger = Logger.getLogger(InitService.class);
	@Autowired
	ConfigurationService configurationService;
	@Autowired
	ICustomService customService;
	@Autowired
	IWeChatTemplateService weChatTemplateService;
	private static final String FILE_NAME = "/dz.properties";
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		//读取配置文件判断当前初始化的环境
		Properties pro = new Properties();
		try {
			InputStream in = InitService.class.getResourceAsStream(FILE_NAME);
			pro.load(in);
			in.close();
			boolean isTestKey=pro.containsKey("ISTEST");
			boolean ISJSKey=pro.containsKey("ISJS");
			//默认为正式环境
			boolean ISTEST=false;
			boolean ISJS=false;
			if(isTestKey){
				ISTEST= Boolean.parseBoolean( String.valueOf(pro.get("ISTEST"))); 
			}
			if(ISJSKey){
				ISJS= Boolean.parseBoolean( String.valueOf(pro.get("ISJS"))); 
			}
			////////////开始校验初始化
			//微信相关的字段
			String wxclientip="wxclientip";
			String wechatpubappid="wechatpubappid";
			//索贝和新奥特url 获取新闻线索url
			String cdvurl="cdvurl";
			String sburl="sburl";
			//统一用户获取地址（包含token和url和Appid）
			String uniontokenurl="uniontokenurl";
			String unionuserurl="unionuserurl";
			String appid="appid";
			String unionkey="unionkey";
			String isuniontoken="isuniontoken";//江苏需要，云南目前不需要
			String liveurl="liveurl";
			CommonParameters query=new CommonParameters();
		    ResponseObject r=customService.getConfiguration(query);
		    String globelId="";
		    if(0==r.getCode()){
		    	Map<String,Object> globel=(Map<String, Object>) r.getData();
		    	globelId=String.valueOf(globel.get(Global.ID));
		    }
		    if(	!configurationService.getConfigKey(liveurl)){
				String value="http://weixin.cdvcloud.com";
				if(pro.containsKey(liveurl)){
					value=(String) pro.get(liveurl);
				}
				updateConfig(globelId,liveurl, value);
			}
			if(	!configurationService.getConfigKey(wxclientip)){
				String value="http://weixin.cdvcloud.com";
				if(ISTEST){
					value="http://news.weixin.cdvcloud.com";
				}
				if(pro.containsKey(wxclientip)){
					value=(String) pro.get(wxclientip);
				}
				updateConfig(globelId,wxclientip, value);
			}
			
			if(	!configurationService.getConfigKey(wechatpubappid)){
				String value="wx228dc5503b0f5422";
				if(ISTEST){
					value="wx27455aef0a78be8d";
				}
				if(pro.containsKey(wechatpubappid)){
					value=(String) pro.get(wechatpubappid);
				}
				updateConfig(globelId,wechatpubappid, value);
			}
			if(	!configurationService.getConfigKey(cdvurl)){
				String value="http://newsphere.cloud.jstv.com";
				if(ISJS){
					value="http://newsphere.cloud.jstv.com";
				}
				if(pro.containsKey(cdvurl)){
					value=(String) pro.get(cdvurl);
				}
				updateConfig(globelId,cdvurl, value);
			}
			if(	!configurationService.getConfigKey(sburl)){
				String value="http://library.cloud.jstv.com";
				if(ISJS){
					value="http://library.cloud.jstv.com";
				}
				if(pro.containsKey(sburl)){
					value=(String) pro.get(sburl);
				}
				updateConfig(globelId,sburl, value);
			}
			if(	!configurationService.getConfigKey(uniontokenurl)){
				String value="http://123.56.237.221:8084/portal/news/V1/cdvcloud/P00045241/USER/";
				if(ISJS){
					value="http://172.20.1.32/lizhiyunAPI_yz/news/V1/cdvcloud/P00045241/TOKEN/";
				}
				if(pro.containsKey(uniontokenurl)){
					value=(String) pro.get(uniontokenurl);
				}
				updateConfig(globelId,uniontokenurl, value);
			}
			if(	!configurationService.getConfigKey(unionuserurl)){
				String value="http://123.56.237.221:8084/portal/news/V1/cdvcloud/P00045241/USER/";
				if(ISJS){
					value="http://172.20.1.31/portalAPI/news/V1/cdvcloud/P00045241/USER/";
				}
				if(pro.containsKey(unionuserurl)){
					value=(String) pro.get(unionuserurl);
				}
				updateConfig(globelId,unionuserurl, value);
			}
			if(	!configurationService.getConfigKey(appid)){
				String value="ytl";
				if(ISJS){
					value="news";
				}
				if(pro.containsKey(appid)){
					value=(String) pro.get(appid);
				}
				updateConfig(globelId,appid, value);
			}
			if(	!configurationService.getConfigKey(unionkey)){
				String value="";
				if(ISJS){
					value="f6eca325a98ae3a1";
				}
				if(pro.containsKey(unionkey)){
					value=(String) pro.get(unionkey);
				}
				updateConfig(globelId,unionkey, value);
			}
			if(	!configurationService.getConfigKey(isuniontoken)){
				String value="1";
				if(ISJS){
					value="0";
				}
				if(pro.containsKey(isuniontoken)){
					value=(String) pro.get(isuniontoken);
				}
				updateConfig(globelId,isuniontoken, value);
			}
			//再初始化微信发送消息模板
			Map<String, Object> onairMap=new HashMap<String, Object>();
			String unbind="_hjP8ThBWm8fPn6zTnBbElrN49Y4o4AF9h9STeo-eBc";
			String bind="3fjEJ95pRXBT3yvCNVnDv4cffs5uB_RsXtAS_pk6ugo";
			String task="TQ_5o5319XIwPvM9tS_tmHdVn8Er_iFA6XO3Fp0f38Q";
			String check="bkzIoRIvxLfr0ekD8kfI8A2dsXozNcyLLm7oPiGIWnU";
			if(ISTEST){
				unbind="Y-k5y4lQ8Kbnvv-zhnA78c5dbZ1y-SF3i-2n4fRToJk";
				bind="MYUuLilso2Ogu0okT34ZNiTp0NiwYf3byLb9fKxRcA0";
				task="JYRnLlNMTwSZi0oURNJz5VVDPQm8vmzgIBnjbas31DM";
				check="HiDWjeooU1_pcYATcikU5GTaBDaUv0KE27qF7WOVShs";
			}
			if(pro.containsKey("unbind")){
				unbind=(String) pro.get("unbind");
			}
			if(pro.containsKey("bind")){
				bind=(String) pro.get("bind");
			}
			if(pro.containsKey("task")){
				task=(String) pro.get("task");
			}
			if(pro.containsKey("check")){
				check=(String) pro.get("check");
			}
			onairMap.put("unbind", unbind);
			onairMap.put("bind", bind);
			onairMap.put("task", task);
			onairMap.put("check", check);
			ResponseObject weChatTemplateRO=weChatTemplateService.initWeChatTemplates(onairMap);
			if(weChatTemplateRO.getCode()!=0){
				logger.error("同步微信消息模板失败");
			}else{
				logger.info("同步微信消息模板成功");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void updateConfig(String id,String config,String value){
		CommonParameters query=new CommonParameters();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(Global.ID, id);
		map.put("globalKey", config);
		map.put("globalValue", value);
		customService.updateConfiguration(query, JsonUtil.map2Json(map));
	}

}
