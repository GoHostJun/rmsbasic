package com.cdvcloud.rms.util;


public class TokenUtil {

	public static boolean getValidateToken(String accessToken){
		String url ="http://121.43.168.160:8080/lizhiyunAPI_yz/auth/token/authToken/";
		String param="accessToken="+accessToken+"&serviceCode=YTL";
		String ret = HttpUtil.sendGet(url, param);
		if("true".equals(ret)){
			return true;
		}else{
			return false;
		}
		
	}
}
