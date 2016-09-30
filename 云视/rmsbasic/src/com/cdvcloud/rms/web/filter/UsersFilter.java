package com.cdvcloud.rms.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cdvcloud.rms.common.Configuration;
import com.cdvcloud.rms.common.Constants;

/**
 * 用户访问权限的过滤器
 * 
 * @author huangaigang
 * 
 */
public class UsersFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Object obj = session.getAttribute(Constants.USERNAME);
		String url = req.getRequestURI();
		String uriList = Configuration.getConfigValue("URI_LIST");
		String uriLogin = Configuration.getConfigValue("URI_LOGIN");
		String[] arrURI = uriList.split("-");
		boolean flag = true;
		StringBuffer sb = new StringBuffer();
		for (String uri : arrURI) {
			flag = url.indexOf(uri) < 0;
			sb.append(String.valueOf(flag));
		}
		if (!(sb.indexOf("false") < 0)) {
			flag = false;
		}
		if (obj == null && !url.endsWith(uriLogin) && flag) {
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().write("<script languge='javascript'>window.location.href='" + req.getContextPath() + "/"+uriLogin+"'</script>");
//			res.getWriter().write("<script languge='javascript'>window.location.href='https://passport.yntv.cdvcloud.com/logout?service=http://news.yntv.cdvcloud.com/'</script>");
		} else {
			chain.doFilter(request, response);
			res.setHeader("Cache-Control", "no-store");
			res.setDateHeader("Expires", 0);
			res.setHeader("Pragma", "no-cache");
			res.flushBuffer();
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}