package com.cdvcloud.rms.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.util.DateUtil;
/**
 * 日志工具类
 * @author mcxin
 *
 */
public class LogUtil {
	private static final Logger loggerIntegral = Logger.getLogger("integralCode");
	
	public static String getLogMessage(Map<String, Object> LogMap,String start,String action){
		String  message = "";
		try {
			Map<String,Object> map = new LinkedHashMap<String, Object>();
			map.put("Action", action);
			map.put("CompanyID", LogMap.get(CommonParameters.COMPANYID));
			map.put("AppCode", LogMap.get(CommonParameters.APPCODE));
			map.put("ServiceCode", LogMap.get(CommonParameters.SERVICECODE));
			map.put("UserID", LogMap.get(CommonParameters.USERID));
			map.put("Time", DateUtil.getCurrentDateTime());
			map.put("IP", LogMap.get(""));
			map.put("Status","0");
//			Map<String, Object> info = new LinkedHashMap<String, Object>();
//			info.put("resID", LogMap.get(""));
//			info.put("TarPath", LogMap.get(""));
//			info.put("FileNum", LogMap.get(""));
			map.put("InFo", LogMap.get("info"));
			message =start+":{"+JsonUtil.writeMap2JSON(map)+"}";
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * 输出积分日志
	 * @param common 公共参数
	 * @param userMap 用户数据
	 * @param type 操作类型
	 *  移动终端登录	phonelogin
	 *	PC终端登录	pclogin
	 *	绑定个人微信号	wxbd
	 *	每日登录签到	sign-in
	 *	上传素材		uploadfile
	 *	新建文稿		createdoc
	 *	推送文稿		pushdoc
	 *	分享文稿至微信	sharedoctowx
	 *	审核文稿		checkdoc
	 *	一键直传		straightupload
	 *	新建线索		createclew
	 *	线索分享		shareclew
	 *	审核线索		checkclew
	 *	创建通联		createnews
	 *	审核通联		checknews
	 *	推送通联		pushnews
	 * @param info 其它信息
	 */
	public static void printIntegralLog(CommonParameters common,Map<String, Object> userMap,String type,String info){
		Map<String, Object> mapIntegral = new HashMap<String, Object>();
		mapIntegral.put("companyId", common.getCompanyId());
//		mapIntegral.put("appCode", common.getAppCode());
		mapIntegral.put("appCode", Constants.APPCODEVALUE);
		mapIntegral.put("userName", userMap.get(User.NAME));
		mapIntegral.put("loginName", userMap.get(User.EMAIL));
		mapIntegral.put("userId", userMap.get(User.ID));
		mapIntegral.put("type", type);
		mapIntegral.put("createTime", DateUtil.getCurrentTime());
		mapIntegral.put("info", info);
		String jsonStr = JsonUtil.map2Json(mapIntegral);
		String integralStr = "OnAirLogIntegral:"+jsonStr;
		loggerIntegral.info(integralStr);
	}
	
	/**
	 * 输出积分日志
	 * @param common 公共参数
	 * @param type 操作类型
	 *  移动终端登录	phonelogin
	 *	PC终端登录	pclogin
	 *	绑定个人微信号	wxbd
	 *	每日登录签到	sign-in
	 *	上传素材		uploadfile
	 *	新建文稿		createdoc
	 *	推送文稿		pushdoc
	 *	分享文稿至微信	sharedoctowx
	 *	审核文稿		checkdoc
	 *	一键直传		straightupload
	 *	新建线索		createclew
	 *	线索分享		shareclew
	 *	审核线索		checkclew
	 *	创建通联		createnews
	 *	审核通联		checknews
	 *	推送通联		pushnews
	 * @param info 其它信息
	 */
	public static void printIntegralLog(CommonParameters common,String type,String info){
		Map<String, Object> mapIntegral = new HashMap<String, Object>();
		mapIntegral.put("companyId", common.getCompanyId());
		mapIntegral.put("appCode", Constants.APPCODEVALUE);
		mapIntegral.put("userName", common.getUserName());
		mapIntegral.put("loginName", common.getLoginName());
		mapIntegral.put("userId", common.getUserId());
		mapIntegral.put("type", type);
		mapIntegral.put("createTime", DateUtil.getCurrentTime());
		mapIntegral.put("info", info);
		String jsonStr = JsonUtil.map2Json(mapIntegral);
		String integralStr = "OnAirLogIntegral:"+jsonStr;
		loggerIntegral.info(integralStr);
	}
}
