package com.cdvcloud.rms.web.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.ValidateCommonParam;
import com.cdvcloud.rms.domain.User;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.JsonUtil;

@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/hot")
public class HotEventController {
	private static final Logger logger = Logger.getLogger(SystemSetController.class);
	@Autowired
	private ValidateCommonParam validateCommonParam;

	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private IUserService userService;

	/**
	 * 热点事件查询
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping("findHotEvents/")
	@ResponseBody
	public ResponseObject findHotEvents(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query, @RequestBody String strJson,
			HttpServletRequest request) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			// 获取用户信息
			Map<String, Object> userMap = userService.getUserInforById(query.getUserId());
			Map<String, Object> map = JsonUtil.readJSON2Map(strJson);
			String accessToken = String.valueOf(map.get("accessToken"));
			// 获取用户名为英文的
			String loginName = String.valueOf(userMap.get(User.EMAIL));
			int dateFlag = Integer.valueOf(String.valueOf(map.get("dateFlag")));
			int count = Integer.valueOf(String.valueOf(map.get("count")));
			String url = configurationService.getHotEventUrl();
			ResponseObject resObject = getResults(accessToken, loginName, dateFlag, count, url);
			return resObject;
		} catch (Exception e) {
			logger.error("模板查询接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	/**
	 * 热点事件查询
	 * 
	 * @param companyId
	 * @param appCode
	 * @param userId
	 * @param serviceCode
	 * @param versionId
	 * @param query
	 * @param strJson
	 * @return
	 */
	@RequestMapping("findJSHotEvents/")
	@ResponseBody
	public ResponseObject findJSHotEvents(@PathVariable String companyId, @PathVariable String appCode, @PathVariable String userId,
			@PathVariable String serviceCode, @PathVariable String versionId, @Valid CommonParameters query, @RequestBody String strJson) {
		try {
			// 校验参数
			boolean validParam = validateCommonParam.validateCommonParam(query, strJson);
			if (!validParam) {
				return new ResponseObject(GeneralStatus.input_error.status, GeneralStatus.input_error.enDetail, "");
			}
			Map<String, Object> map = JsonUtil.readJSON2Map(strJson);
			String accessToken = String.valueOf(map.get("accessToken"));
			String keyWord = String.valueOf(map.get("keyWord"));
			int pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")));
			String url = configurationService.getHotEventUrl();
			ResponseObject resObject = getJSResults(accessToken, keyWord, pageSize, url);
			return resObject;
		} catch (Exception e) {
			logger.error("模板查询接口错误：" + e);
			return new ResponseObject(GeneralStatus.inner_error.status, GeneralStatus.inner_error.enDetail, "");
		}
	}

	public ResponseObject getJSResults(String accessToken, String keyWord, int pageSize, String url) {
		ResponseObject resObject = new ResponseObject();
		String paramsStr = "accessToken=" + accessToken + "&keyWord=" + keyWord + "&pageSize=" + pageSize;
		@SuppressWarnings("static-access")
		String result = this.sendPost(url, paramsStr);
		resObject.setCode(60004);
		if (null == result) {
			return resObject;
		}
		Map<String, Object> resultMap = JsonUtil.readJSON2Map(result);
		if (resultMap == null) {
			return resObject;
		}
		if ("200".equals(String.valueOf(resultMap.get("status")))) {
			resObject.setCode(0);
			resObject.setData(resultMap);
		}
		return resObject;
	}

	public ResponseObject getResults(String accessToken, String loginName, int dateFlag, int count, String url) {
		ResponseObject resObject = new ResponseObject();
		String paramsStr = "loginName=" + loginName + "&currentPage=1&pageSize=" + count;
		@SuppressWarnings("static-access")
		String result = this.sendPost(url, paramsStr);
		resObject.setCode(60004);
		if (null == result) {
			return resObject;
		}
		Map<String, Object> resultMap = JsonUtil.readJSON2Map(result);
		if (resultMap == null) {
			return resObject;
		}
		if ("0".equals(String.valueOf(resultMap.get("code")))) {
			resObject.setCode(0);
			resObject.setData(resultMap);
		}
		return resObject;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送 POST 请求出现异常！" + e.getMessage());
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("发送 POST 请求关闭输出流、输入流异常！" + ex.getMessage());
			}
		}
		return result;
	}

}
