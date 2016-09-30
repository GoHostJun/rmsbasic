package com.cdvcloud.rms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.dao.impl.BasicDao;
import com.cdvcloud.rms.domain.Custom;
import com.cdvcloud.rms.domain.Global;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;

/***
 * 获取全局配置的值
 * 
 * @author mc-xin
 */
@Service
public class ConfigurationService {
	@Autowired
	private BasicDao basicDao;
	@Autowired
	private IStatisticService statisticService;
	private static final Logger logger = Logger.getLogger(ConfigurationService.class);

	/** 原素材 source/program/子目录 */
	public static final String SOURCEPROGRAM = "source/program/";
	/** 原素材 source/program子目录 */
	public static final String SOURCEPROGRAMNEW = "source/program";
	/** 原素材 source/service/子目录 */
	public static final String SOURCESERVICE = "source/service/";
	/** 业务转码后素材子目录 */
	public static final String BUSINESS = "business/";
	/** 终端转码后素材子目录 */
	public static final String PUBLISH = "publish/";

	/**
	 * 根据key,返回value
	 * 
	 * @param key
	 *            配置名称
	 * @return 配置值,若没有符合条件的key,则返回null
	 */
	public String getConfigValue(String key) {
		try {
			Map<String, Object> dbObj_global = basicDao.findOne(Global.GLOBAL);
			if (null != dbObj_global) {
				String value = (String) dbObj_global.get(key);
				return value;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 判断是否包含字段key
	 * @param key
	 * @return
	 */
	public boolean getConfigKey(String key){
		boolean containsKey=false;
		try {
			Map<String, Object> dbObj_global = basicDao.findOne(Global.GLOBAL);
			if (null != dbObj_global) {
				 containsKey=dbObj_global.containsKey(key);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return containsKey;
	}

	public String getSourceProgramLocal() {
		return getConfigValue(Global.LOCAL) + SOURCEPROGRAM;
	}

	/** 获取用户校验地址 */
	public String getValidateUserUrl() {
		return getConfigValue(Global.VALIDATEUSERURL);
	}

	/** 工具集 地址 */
	public String getDigitalFactoryAPI() {
		return getConfigValue(Global.DIGITALFACTORYAPI);
	}

	/** 存储代理地址服务 地址 */
	public String getORSAPI() {
		return getConfigValue(Global.ORS);
	}

	/** 回调 地址 */
	public String getCallback() {
		return getConfigValue(Global.CALLBACK);
	}

	/** 回调 地址 */
	public String getOuterCallback() {
		return getConfigValue(Global.OUTERCALLBACK);
	}

	/** 消息集群IP */
	public String getQueueHost() {
		return getConfigValue(Global.QUEUE_HOST);
	}

	/** 消息集群端口号 */
	public String getQueuePort() {
		return getConfigValue(Global.QUEUE_PORT);
	}

	/** 消息集群用户名 */
	public String getQueueUsername() {
		return getConfigValue(Global.QUEUE_USERNAME);
	}

	/** 消息集群密码 */
	public String getQueuePassword() {
		return getConfigValue(Global.QUEUE_PASSWORD);
	}

	/** 企业标识 */
	public String getCompany() {
		return getConfigValue(Global.COMPANY);
	}

	/** 获取预览转码模板id */
	public String getPrewar() {
		return getConfigValue(Global.PREWAR);
	}

	/** 获取视频终端转码模板id */
	public String getvideoPublish() {
		return getConfigValue(Global.VIDEOPUBLISH);
	}

	/** 获取视频默认转码模板id */
	public String getvideoDefaults() {
		return getConfigValue(Global.VIDEODEFAULTS);
	}

	/** 获取音频终端转码模板id */
	public String getaudioPublish() {
		return getConfigValue(Global.AUDIOPUBLISH);
	}

	/** 获取音频默认转码模板id */
	public String getaudioDefaults() {
		return getConfigValue(Global.AUDIODEFAULTS);
	}

	/** 获取音频预览转码模板id */
	public String getaudioPrewar() {
		return getConfigValue(Global.AUDIOPREWAR);
	}

	/** 获取跳转外网地址 */
	public String getSkipWan() {
		return getConfigValue(Global.SKIPWAN);
	}

	/** 获取跳转内网地址 */
	public String getSkipLen() {
		return getConfigValue(Global.SKIPLEN);
	}

	/** 获取oss内网地址 */
	public String getOssWan() {
		return getConfigValue(Global.OSSWAN);
	}

	/** 获取oss内网地址 */
	public String getOssLen() {
		return getConfigValue(Global.OSSLEN);
	}

	/** 获取cdn加速地址 */
	public String getCdn() {
		return getConfigValue(Global.CDN);
	}

	/** 获取文件存储服务器ip */
	public String getStorageIp() {
		return getConfigValue(Global.STORAGEIP);
	}

	/** 获取oss代理地址 */
	public String getossAdderssUrl() {
		return getConfigValue(Global.OSSADDERSSURL);
	}

	/** 获取外网地址 */
	public String getHttpWan() {
		return getConfigValue(Global.HTTPWAN);
	}

	/** 获取内网地址 */
	public String getHttpLen() {
		return getConfigValue(Global.HTTPLEN);
	}

	/** 获取转码模板查寻地址 */
	public String getTemplateUrl() {
		return getConfigValue(Global.TEMPLATEURL);
	}

	/** 获取热点事件查寻地址 */
	public String getHotEventUrl() {
		return getConfigValue(Global.HOTEVENTURL);
	}

	/** 获取md5 */
	public String getIsMd5() {
		return getConfigValue(Global.ISMD5);
	}

	@PostConstruct
	protected void token() {
		logger.info("获取token线程启动！");
		new Thread(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				while (true) {
					try {
						String apiurl = getDigitalFactoryAPI();
						// 统计通联排行榜
						statisticService.statistic();
						// 老的获取token  获取所有的项目对象
						String oldUrl = apiurl + "/" + getTokenAddress(getAppCode(), getCompany(), "123456", getServiceCode()) + "/auth/token/getAccessToken/";
						String oldParam = "accessKey=" + getAccessKey() + "&timeStamp=" + System.currentTimeMillis();
						logger.info("获取tokenOld的url：" + oldUrl + "内容：" + oldParam);
						String oldRet = HttpUtil.sendGet(oldUrl, oldParam);
						logger.info("tokenOld返回内容：" + oldRet);
						Map<String, Object> oldJsonMap = JsonUtil.readJSON2Map(oldRet);
						Map<String, Object> oldData = (Map<String, Object>) oldJsonMap.get("data");
						String oldaccessToken = String.valueOf(oldData.get("accessToken"));
						Map<String, Object> oldfilter = new HashMap<String, Object>();
						Map<String, Object> oldupdate = new HashMap<String, Object>();
						oldupdate.put(Global.ACCESSTOKEN, oldaccessToken);
						basicDao.updateOneBySet(Global.GLOBAL, oldfilter, oldupdate);
						// 老的获取token
						List<Map<String, Object>> customs = basicDao.find(Custom.COLLECTION, 1, 10000);
						logger.info("customs:" + customs);
						for (Map<String, Object> map : customs) {
							String company = String.valueOf(map.get(Custom.COMPANYID));
							String appcode = String.valueOf(map.get(Custom.APPCODE));
							String servicecode = String.valueOf(map.get(Custom.SERVICECODE));
							String accesskey = String.valueOf(map.get(Custom.OTHERCONFIG));
							// 获取所有的项目对象
							String url = apiurl + "/" + getTokenAddress(appcode, company, "123456", servicecode) + "/auth/token/getAccessToken/";
							String param = "accessKey=" + accesskey + "&timeStamp=" + System.currentTimeMillis();
							logger.info("获取token的url：" + url + "内容：" + param);
							String ret = HttpUtil.sendGet(url, param);
							logger.info("返回内容：" + ret);
							Map<String, Object> jsonMap = JsonUtil.readJSON2Map(ret);
							Map<String, Object> data = (Map<String, Object>) jsonMap.get("data");
							String accessToken = String.valueOf(data.get("accessToken"));
							Map<String, Object> filter = new HashMap<String, Object>();
							filter.put(Custom.COMPANYID, company);
							filter.put(Custom.APPCODE, appcode);
							Map<String, Object> update = new HashMap<String, Object>();
							update.put(Custom.ACCESSTOKEN, accessToken);
							basicDao.updateOneBySet(Custom.COLLECTION, filter, update);
						}
						// 顺利执行完for循环表示所有的信息都创建完毕，睡眠90分钟后，继续重新获取
						Thread.sleep(90 * 60 * 1000);

					} catch (Exception e) {
						logger.error("获取token错误{" + e + "}");
						try {
							Thread.sleep(30 * 60 * 1000);
						} catch (Exception e1) {
							logger.error("获取token错误{" + e1 + "}");
						}
					}
				}

			}
		}).start();
	}

	/**
	 * 获取token
	 */
	public String getToken(CommonParameters common) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(Custom.COMPANYID, common.getCompanyId());
		filter.put(Custom.APPCODE, common.getAppCode());
		Map<String, Object> map = basicDao.findOne(Custom.COLLECTION, filter);
		if (null != map && map.size() > 0) {
			return String.valueOf(map.get(Custom.ACCESSTOKEN));
		} else {
			return "123456";
		}
	}

	public String getToken() {
		return getConfigValue(Global.ACCESSTOKEN);
	}

	/**
	 * 验证token
	 * 
	 * @param accessToken
	 * @return
	 */
	public boolean getValidateToken(String accessToken) {
		String url = getDigitalFactoryAPI() + "/auth/token/authToken/";
		String param = "accessToken=" + accessToken + "&serviceCode=" + getServiceCode();
		String ret = HttpUtil.sendGet(url, param);
		if ("true".equals(ret)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取推送newsphere的url
	 */
	public String getNewsphereUrl() {
		return getConfigValue(Global.NEWSPHEREURL);
	}

	public String getDocsLocalUrl() {
		return getConfigValue(Global.DOCSLOCALURL);
	}

	/**
	 * 获取推送星云的url
	 */
	public String getConvergeUrl() {
		return getConfigValue(Global.CONVERGEURL);
	}

	/** 获取发送手机号 多个，号隔开 */
	public String getMobileNum() {
		return getConfigValue(Global.MOBILENUM);
	}

	/** 获取短信接口url地址 */
	public String getSendUrl() {
		return getConfigValue(Global.SENDURL);
	}

	/** 使用sdk上传 需要的url */
	public String getOssUploadUrl() {
		return getConfigValue(Global.OSSUPLOADURL);
	}

	/** 获取微信appid */
	public String getWeChatAppid() {
		return getConfigValue(Global.WECHATAPPID);
	}

	/** 获取微信secret */
	public String getWeChatSecret() {
		return getConfigValue(Global.WECHATSECRET);
	}

	/** 获取微信跳转地址 */
	public String getRedirectUri() {
		return getConfigValue(Global.REDIRECTURI);
	}

	/** 获取qq授权跳转地址 */
	public String getQQRedirectUri() {
		return getConfigValue(Global.QQREDIRECTURI);
	}

	/** 获取QQappid */
	public String getQQclientId() {
		return getConfigValue(Global.QQCLIENTID);
	}

	/** 获取QQclientSecret */
	public String getQQclientSecret() {
		return getConfigValue(Global.QQCLIENTSECRET);
	}

	/** 获取QQscope */
	public String getQQScope() {
		return getConfigValue(Global.QQSCOPE);
	}

	/** 获取极光推送value */
	public String getJpushValue() {
		return getConfigValue(Global.JPUSHMASTERSECRET);
	}

	/** 获取极光推送key */
	public String getJpushKey() {
		return getConfigValue(Global.JPUSAPPKEY);
	}

	/** appcode */
	public String getAppCode() {
		return getConfigValue(Global.APPCODE);
	}

	/** serviceCode */
	public String getServiceCode() {
		return getConfigValue(Global.SERVICECODE);
	}

	/** accessKey */
	public String getAccessKey() {
		return getConfigValue(Global.ACCESSKEY);
	}

	/** 过去内网直传迁移 tokenurl */
	public String getDirectPassing_getaccesstoken() {
		return getConfigValue(Global.DIRECTPASSING_GETACCESSTOKEN);
	}

	/** 过去内网直传迁移 tokenurl 秘钥 */
	public String getDirectPassing_accessKey() {
		return getConfigValue(Global.DIRECTPASSING_ACCESSKEY);
	}

	/** 过去内网直传迁移 接口地址 */
	public String getDirectPassing_move() {
		return getConfigValue(Global.DIRECTPASSING_MOVE);
	}

	/** 过去内网直传迁移 迁移任务的目标版块ID */
	public String getDirectPassing_movetaskdestsectionid() {
		return getConfigValue(Global.DIRECTPASSING_MOVETASKDESTSECTIONID);
	}

	/** 微信客户端域名 */
	public String getWxclient() {
		return getConfigValue(Global.WXCLIENTIP);
	}

	/** 获取统一用户Token接口的域名 */
	public String getUnionTokenUrl() {
		return getConfigValue(Global.UNIONTOKENURL);
	}

	/** 获取统一用户用户管理接口的域名 */
	public String getUnionUserUrl() {
		return getConfigValue(Global.UNIONUSERURL);
	}

	/** 获取统一用户accessen的key */
	public String getUnionKey() {
		return getConfigValue(Global.UNIONKEY);
	}

	/** 获取统一用户Appid */
	public String getAppid() {
		return getConfigValue(Global.APPID);
	}

	/** 获取公众号的Appid */
	public String getWeChatPubAppid() {
		return getConfigValue(Global.WECHATPUBAPPID);
	}

	/** 获取新奥特url */
	public String getCDVUrl() {
		return getConfigValue(Global.CDVURL);
	}

	/** 获取索贝url */
	public String getSBUrl() {
		return getConfigValue(Global.SBURL);
	}

	/** 获取积分url */
	public String getIntegralUrl() {
		return getConfigValue(Global.QUERYINTEGRAL);
	}

	/** 同步用户是否需要token 0需要 1不需要 */
	public String getIsUnionToken() {
		return getConfigValue(Global.ISUNIONTOKEN);
	}

	/** 智云url(如：新闻线索url) */
	public String getZYurl() {
		return getConfigValue(Global.ZYURL);
	}
	/** 直播url(如：直播url) */
	public String getLiveurl() {
		return getConfigValue(Global.LIVEURL);
	}
	
	/**
	 * 针对获取token：老版本参数如下顺序如下 appCode/versionId/companyId/123456/serviceCode/
	 * 
	 * @return
	 */
	public String getTokenAddress(String appCode,String companyId,String userId,String serviceCode) {
//		return appCode + "/v1/" + companyId + "/"+userId+"/" + serviceCode;
		return "JXYTL_YUNSHI/v1/" + companyId + "/"+userId+"/" + serviceCode;
	}
}
