package com.cdvcloud.rms.domain;

import java.util.HashMap;
import java.util.Map;

public class WechatTemplate {
	public static final String WECHATTEMPLATE="wechattemplate";
	public static final String ID="_id";
	/**当前用户openid **/
	public static final String TOUSER="touser";
	/**当前用户id **/
	public static final String USERID="userid";
	/**消息模板id **/
	public static final String TEMPLATEID="template_id";
	/**消息first字段 **/
	public static final String FIRST="first";
	/**消息remark字段 **/
	public static final String REMARK="remark";
	/**消息data字段 **/
	public static final String DATA="data";
	/**消息keyword字段 **/
	public static final String KEYWORD="keyword";
	/** 发送微信类型**/
	public static final String TYPE="type";
	/** 发送微信标题**/
	public static final String TITLE="title";
	/**
	 * 返回微信的标准map类型
	 * @param mapPara
	 * @return
	 */
	public static Map<String,Object> weChatTemplate(Map<String,Object> mapPara){
		Map<String,Object>map=new HashMap<String, Object>();
		Map<String,Object>dataOut=new HashMap<String, Object>();
		Map<String,Object>data=new HashMap<String, Object>();
		for(Map.Entry<String,Object> entry:mapPara.entrySet()){
			Map<String,Object>tempMap=new HashMap<String, Object>();
			if(WechatTemplate.TOUSER.equals(entry.getKey())||WechatTemplate.TEMPLATEID.equals(entry.getKey())
					||WechatTemplate.USERID.equals(entry.getKey())){
				tempMap.put(entry.getKey(), entry.getValue());
				map.putAll(tempMap);
			}else{
				tempMap.put("value", entry.getValue());
				data.put(entry.getKey(), tempMap);
			}
		}
		dataOut.put(WechatTemplate.DATA, data);
		map.putAll(dataOut);
		return map;
		
	}

}
