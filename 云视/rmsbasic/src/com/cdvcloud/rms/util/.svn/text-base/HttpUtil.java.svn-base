package com.cdvcloud.rms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http工具类
 * 
 * @version： v1.0
 * @author huangaigang
 * @date 2015-11-29 23:06:20
 */
public class HttpUtil {

	private static final String CODE = "UTF-8";
	private static HttpClient httpClient = new HttpClient();
	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * 发送post请求
	 * 
	 * @param params
	 * @param url
	 * @return
	 */
	public static synchronized String post(Map<String, String> params, String url) {
		CloseableHttpResponse response = null;
		try {
			if (null == params || params.size() == 0 || null == url || "".equals(url)) {
				logger.error("发送http请求的参数或地址不正确！");
				return null;
			}
			HttpPost httpPost = new HttpPost(url);
			List<org.apache.http.NameValuePair> nvps = new ArrayList<org.apache.http.NameValuePair>(params.size());
			Set<String> keys = params.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = params.get(key);
				nvps.add(new BasicNameValuePair(key, value));
			}
			CloseableHttpClient client = HttpClients.createDefault();
			StringBuffer stringBuffer = null;
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, CODE));
			response = client.execute(httpPost);
			if (response != null && response.getStatusLine().toString().indexOf("200") != -1) {
				stringBuffer = new StringBuffer();
				HttpEntity entity = response.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), CODE));
				String line = null;
				while ((line = br.readLine()) != null) {
					stringBuffer.append(line);
				}
				return stringBuffer.toString();
			} else {
				logger.error("发送http请求的响应状态为：" + response.getStatusLine().toString());
				return null;
			}
		} catch (Exception e) {
			logger.error("发送http请求发生错误：" + e.getMessage());
			return null;
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("关闭response发生错误：" + e.getMessage());
				}
			}
		}
	}

	/**
	 * 模拟mutlipart提交file
	 * 
	 * 传File示例 MultipartEntity mutiEntity = new MultipartEntity(); File file =
	 * new File(filepath); mutiEntity.addPart("请求参数", new StringBody("参数值",
	 * Charset.forName(CODE))); mutiEntity.addPart("请求参数", new StringBody("参数值",
	 * Charset.forName(CODE))); mutiEntity.addPart("file", new FileBody(file));
	 * 
	 * @param httpUrl
	 * @param mutiEntity
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postFile(String httpUrl, MultipartEntity mutiEntity) {
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("User-Agent", "SOHUWapRebot");
		httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpPost.setHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.7");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setEntity(mutiEntity);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse httpResponse;
		String content = null;
		try {
			httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			content = EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			logger.error("模拟发送form表单上传文件异常：" + e.getMessage());
			return content;
		}
		return content;
	}

	/**
	 * 根据URL跨域获取输出结果，支持https
	 * 
	 * @param url
	 *            要访问的URL地址(http://www.xxx.com?)
	 * @param urlParm
	 *            参数(id=1212&pwd=2332)
	 * @return 结果字符串
	 */
	public static String postMethod(String url, String urlParm) {
		if (null == url || "".equals(url)) {
			return null;
		}
		PostMethod post = new PostMethod(url); // new UTF8PostMethod(url);
		if (null != urlParm && !"".equals(urlParm)) {
			String[] arr = urlParm.split("&");
			NameValuePair[] data = new NameValuePair[arr.length];
			for (int i = 0; i < arr.length; i++) {
				String name = arr[i].substring(0, arr[i].lastIndexOf("="));
				String value = arr[i].substring(arr[i].lastIndexOf("=") + 1);
				data[i] = new NameValuePair(name, value);
			}
			post.setRequestBody(data);
		}
		int statusCode = 0;
		String pageContent = "";
		try {
			statusCode = httpClient.executeMethod(post);
			if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				pageContent = post.getResponseBodyAsString();
				return pageContent;
			}
		} catch (Exception e) {
			logger.error("根据URL跨域获取输出结果（https）异常：" + e.getMessage());
			return null;
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPost(String url, String json) {
		if (url == null || "".equals(url)) {
			logger.error("请求地址为null");
			return null;
		}
		if (null == json || "".equals(json)) {
			logger.error("请求参数为null");
			return null;
		}
		PostMethod postMethod = new PostMethod(url);
		String result = null;
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(json, "application/json", CODE);
			postMethod.setRequestEntity(requestEntity);
			/* 发送请求，并获取响应对象 */
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
				logger.error("发送post请求失败：" + postMethod.getStatusLine());
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持编码异常，字符编码有问题:" + e.getMessage());
		} catch (HttpException e) {
			logger.error("请求http地址发生异常：" + e.getMessage());
		} catch (IOException e) {
			logger.error("IO异常:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String doPostForYunKey(String url, String json) {
		PostMethod postMethod = new PostMethod(url);
		String result = null;
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(json, "application/json", CODE);
			postMethod.setRequestEntity(requestEntity);
			/* 发送请求，并获取响应对象 */
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
				logger.error("发送post请求失败：" + postMethod.getStatusLine());
				result = postMethod.getStatusText();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持编码异常，字符编码有问题:" + e.getMessage());
		} catch (HttpException e) {
			logger.error("请求http地址发生异常：" + e.getMessage());
		} catch (IOException e) {
			logger.error("IO异常:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), CODE));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送GET请求出现异常！" + e.getMessage());
		} finally {
			// 使用finally块来关闭输入流
			try {
				if (null != in) {
					in.close();
				}
			} catch (Exception e2) {
				logger.error("发送GET请求关闭输出流、输入流异常！" + e2.getMessage());
			}
		}
		return result;
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
			conn.setRequestProperty("Accept-Charset", CODE);
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
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CODE));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送 POST 请求出现异常！" + e.getMessage());
		} finally {
			// 使用finally块来关闭输出流、输入流
			try {
				if (null != out) {
					out.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("发送 POST 请求关闭输出流、输入流异常！" + ex.getMessage());
			}
		}
		return result;
	}

	public static String sendPost(String requestUrl, Map<String, Object> paramMap) {
		if (requestUrl == null || "".equals(requestUrl)) {
			logger.error("请求地址为null");
			return null;
		}
		if (null == paramMap) {
			logger.error("请求参数为null");
			return null;
		}

		/* 将参数模板实例转换为json */
		String paramJson = JsonUtil.writeMap2JSON(paramMap);
		StringRequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(paramJson, "text/plain", CODE);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		PostMethod postMethod = new PostMethod(requestUrl);
		postMethod.setRequestEntity(requestEntity);
		HttpClient httpClient = new HttpClient();
		/* 发送请求，并获取响应对象 */
		int statusCode = -1;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		if (statusCode != HttpStatus.SC_OK) {
			logger.error("post请求失败： " + postMethod.getStatusLine());
			return null;
		}
		String resultJson = null;
		try {
			InputStream io = postMethod.getResponseBodyAsStream();
			resultJson = inputStream2String(io);
			logger.info("请求结果 : " + resultJson);
		} catch (IOException e) {
			logger.error("获取ResponseBody时出错：" + e.getMessage());
		}
		return resultJson;
	}

	public static String sendPostJson(String requestUrl, String json) {
		/* 将参数模板实例转换为json */
		StringRequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(json, "text/plain", CODE);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		PostMethod postMethod = new PostMethod(requestUrl);
		postMethod.setRequestEntity(requestEntity);
		HttpClient httpClient = new HttpClient();
		/* 发送请求，并获取响应对象 */
		int statusCode = -1;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		if (statusCode != HttpStatus.SC_OK) {
			logger.error("post请求失败： " + postMethod.getStatusLine());
			return null;
		}
		String resultJson = null;
		try {
			InputStream io = postMethod.getResponseBodyAsStream();
			resultJson = inputStream2String(io);
		} catch (IOException e) {
			logger.error("获取ResponseBody时出错：" + e.getMessage());
		}
		return resultJson;
	}

	public static String inputStream2String(InputStream io) {
		String result = null;
		if (io != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(io));
			String line = null;
			StringBuffer sb = new StringBuffer();
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				result = sb.toString();
			} catch (IOException e) {
				logger.error("解析转码服务的响应体时报错  " + e.getMessage());
			}
		}
		return result;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}

	/**
	 * 获取跳转地址的真实路径
	 * 
	 * @param skipUrl
	 * @return
	 */
	public static String getRealUrlFormSkipUrl(String skipUrl) {
		String realUrl = null;
		if (null != skipUrl && !"".equals(skipUrl)) {
			try {
				URL url = new URL(skipUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.getResponseCode();
				realUrl = conn.getURL().toString();
				conn.disconnect();
			} catch (Exception e) {
				logger.error("获取真实地址出错：  " + e);
				e.printStackTrace();
			}
		}
		return realUrl;
	}

	/**
	 * 向指定 URL 发送表单形式的POST方法的请求 application/x-www-form-urlencoded
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendFormPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CODE));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			logger.info(url + ";post请求状态：" + conn.getResponseCode());
		} catch (Exception e) {
			logger.error("发送 POST 请求出现异常！" + e.getMessage());
		}finally {
			// 使用finally块来关闭输出流、输入流
			try {
				if (null != out) {
					out.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("发送 POST 请求关闭输出流、输入流异常！" + ex.getMessage());
			}
		}
		return result;
	}
}
