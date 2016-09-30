package com.cdvcloud.rms.util;

import java.util.Arrays;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {
	private static final Logger logger = Logger.getLogger(JPushUtil.class);
	private static final String masterSecret = "9971ed51c41ca81c97037992";
	private static final String appKey = "1944376d469e82465bcc9636";

	private static class JPushClientHolder {
		// 单例变量
		private static JPushClient jpushClient = new JPushClient(masterSecret, appKey);
	}

	private JPushUtil() {
	}

	public static JPushClient getInstance() {
		return JPushClientHolder.jpushClient;
	}
	
	/**
	 * 推送通知到移动端
	 * @param alertContent 通知内容
	 * @param alias 指定用户id
	 */
	public static void pushObjectToPhone(String alertContent,String... alias){
		 logger.info("要推送的消息："+alertContent+"，推送给："+ Arrays.asList(alias));
		 PushPayload payload=buildPushObject_all_alias_alert(alertContent, alias);
		 try {
			PushResult result = getInstance().sendPush(payload);
			logger.info(result);
			logger.info("code" + result.getResponseCode() + " cont" + result.getOriginalContent() + " quota+" + result.getRateLimitQuota()
					+ " remaining: " + result.getRateLimitRemaining() + " reset:" + result.getRateLimitReset());
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			e.printStackTrace();
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			logger.info("Msg ID: " + e.getMsgId());
			e.printStackTrace();
		}
	}
	
	/**
	 * 推送消息到移动端
	 * @param alertContent 通知内容
	 * @param alias 指定用户id
	 */
	public static void pushMessageObjectToPhone(String alertContent,String... alias){
		 logger.info("要推送的消息："+alertContent+"，推送给："+ Arrays.asList(alias));
		 PushPayload payload=buildPushObject_tag__messageWithAlert(alertContent, alias);
		 try {
			PushResult result = getInstance().sendPush(payload);
			logger.info(result);
			logger.info("code" + result.getResponseCode() + " cont" + result.getOriginalContent() + " quota+" + result.getRateLimitQuota()
					+ " remaining: " + result.getRateLimitRemaining() + " reset:" + result.getRateLimitReset());
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			e.printStackTrace();
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			logger.info("Msg ID: " + e.getMsgId());
			e.printStackTrace();
		}
	}

	/**
	 * @Title: buildPushObject_all_alias_alert
	 * @Description: (推送所有通知)
	 * @param @return
	 * @throws
	 * @author huang
	 * @date 2016年1月14日 下午5:37:30
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alertContent) {
		return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.all())// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setNotification(Notification.alert(alertContent)).build();
	}
	
	/**
	 * @Title: buildPushObject_all_alias_alert
	 * @Description: (推送所有通知)
	 * @param @return
	 * @throws
	 * @author huang
	 * @date 2016年1月14日 下午5:37:30
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alertContent,String... alias) {
		return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.alias(alias))// 说明采用广播方式推送，所有指定用户都可以接收到
				.setNotification(Notification.alert(alertContent)).build();
	}
	
	/**
	 * @Title: buildPushObject_all_alias_Extra
	 * @Description: (推送所有消息)
	 * @param @return
	 * @throws
	 * @author huang
	 * @date 2016年1月14日 下午5:40:25
	 */
	public static PushPayload buildPushObject_all_alias_Extra(String msgContent) {
		return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.all())// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setMessage(Message.newBuilder().setMsgContent(msgContent).addExtra("from", "JPush").build()).build();
	}
	
	/**
	 * @Title: buildPushObject_android_tag__messageWithExtras
	 * @Description: (android指定alias通知)
	 * @param @return
	 * @throws
	 * @author huang
	 * @date 2016年1月14日 下午5:41:29
	 */
	public static PushPayload buildPushObject_android_tag__messageWithExtras(String alertContent,String title,String... alias) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias))
				.setNotification(Notification.android(alertContent, title, null)).build();
	}
	
	/**
	 * @Title: buildPushObject_android_tag__messageWithAlert
	 * @Description: (android指定alias消息)
	 * @param @return
	 * @throws
	 * @author huang
	 * @date 2016年1月14日 下午5:42:27
	 */
	public static PushPayload buildPushObject_android_tag__messageWithAlert(String msgContent,String... alias) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(alias)).build())
				.setMessage(Message.newBuilder().setMsgContent(msgContent).addExtra("from", "JPush").build()).build();
	}
	
	/**
	 * @Title: buildPushObject_tag__messageWithAlert
	 * @Description: (指定alias消息)
	 * @param @return
	 * @throws
	 * @author huang
	 * @date 2016年1月14日 下午5:42:27
	 */
	public static PushPayload buildPushObject_tag__messageWithAlert(String msgContent,String... alias) {
		return PushPayload.newBuilder().setPlatform(Platform.all())
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(alias)).build())
				.setMessage(Message.newBuilder().setMsgContent(msgContent).addExtra("from", "JPush").build()).build();
	}

}
