package com.cdvcloud.rms.util;

import java.util.Map;

public class SkipUtil {
	/**
	 * 获取实际素材地址
	 * 
	 * @param orsUrl
	 *            ors服务地址 如：http：//192.168.0.1:8080/ors/
	 * @param url
	 *            跳转地址
	 * @return 实际素材地址
	 */
	@SuppressWarnings("unchecked")
	public static String getHttp(String orsUrl, String url) {
		try {

			if (null != url && !"".equals(url)) {
				String reg = ".*&.*"; // 判断字符串中是否含有特定字符串&
				if (url.matches(reg)) {
					orsUrl += "api/findInternetUrl/";
					String[] params = url.split("\\?");
					// String param =params[1]+"&isLen=1";
					String param = params[1];
					// System.out.println("url="+orsUrl+";内容："+param);
					String ret = HttpUtil.sendPost(orsUrl, param);
					Map<String, Object> json = JsonUtil.readJSON2Map(ret);
					if ("200".equals(String.valueOf(json.get("status")))) {
						Map<String, Object> data = (Map<String, Object>) json.get("data");
						String returl = String.valueOf(data.get("url"));
						// System.out.println("获取实际素材地址："+returl);
						return returl;
					}
					return url;
				}
			}
		} catch (Exception e) {
			return url;
		}
		return url;
	}

}
