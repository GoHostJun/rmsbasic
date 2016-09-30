package com.cdvcloud.rms.domain;

public class WechatUser {
	public static final String SUBSCRIBE = "subscribe";//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	public static final String OPENID = "openid";//普通用户的标识，对当前开发者帐号唯一
	public static final String NICKNAME = "nickname";//普通用户昵称
	public static final String SEX = "sex";//普通用户性别，1为男性，2为女性
	public static final String LANGUAGE = "language";
	public static final String CITY = "city";//普通用户个人资料填写的城市
	public static final String PROVINCE = "province";//普通用户个人资料填写的省份
	public static final String COUNTRY = "country";//国家，如中国为CN
	public static final String HEADIMGURL = "headimgurl";//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	public static final String SUBSCRIBETIME = "subscribe_time";//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	public static final String UNIONID = "unionid";//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
	public static final String REMARK = "remark";//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	public static final String GROUPID = "groupid";//用户所在的分组ID（兼容旧的用户分组接口）
	public static final String TAGIDLIST = "tagid_list";//用户被打上的标签ID列表
	public static final String USERID = "userId";

}
