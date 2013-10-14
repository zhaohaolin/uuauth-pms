package com.uuauth.pms.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkit.lang.StringUtils;
import com.uuauth.api.service.UUAuthAPIService;
import com.uuauth.pms.AuthConstants;
import com.uuauth.pms.controller.PmsController;

/**
 * 
 * 权限过滤器
 * 
 * @author joe.zhao
 * @version $Id: PmsFilter, v 0.1 2012-7-4 上午09:19:02 Exp $
 */
public class PmsFilter implements Filter, AuthConstants {
	
	private Set<String>			excluded;											// 排除的访问规则
	private Set<String>			included;											// 包括的访问规则
	private String				urlRedirect;										// 访问不通过的跳转uri
	private UUAuthAPIService	apiService	= UUAuthAPIService.getInstance();
	private Logger				log			= LoggerFactory
													.getLogger(PmsFilter.class);
	
	// 匹配规则检查
	private boolean canDo(Set<String> exclude, String prefix, String uri) {
		for (String url : exclude) {
			url = prefix + url;
			if (url.startsWith("*")) {
				if (uri.endsWith(url.substring(1)))
					return true;
			} else if (url.endsWith("*")) {
				if (uri.startsWith(url.substring(0, url.length() - 1)))
					return true;
			} else {
				if (url.equals(uri))
					return true;
			}
		}
		return false;
	}
	
	public void init(FilterConfig config) throws ServletException {
		urlRedirect = config.getInitParameter("urlRedirect");
		
		String temps[] = null;
		excluded = new HashSet<String>();
		String e1 = config.getInitParameter("excluded");
		if (e1 != null && e1.trim().length() > 0) {
			temps = e1.split("\\|");
			for (String exclude : temps) {
				excluded.add(exclude);
			}
		}
		
		included = new HashSet<String>();
		String e2 = config.getInitParameter("included");
		if (e2 != null && e2.trim().length() > 0) {
			temps = e2.split("\\|");
			for (String exclude : temps) {
				included.add(exclude);
			}
		}
	}
	
	public void destroy() {
		//
	}
	
	public void doFilter(ServletRequest servletReq,
			ServletResponse servletResp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) servletReq;
		HttpServletResponse resp = (HttpServletResponse) servletResp;
		// 检查是否已经登录
		
		req.setCharacterEncoding("utf-8");
		req.setAttribute("contextPath", req.getContextPath());
		String uri = req.getRequestURI().toString();
		String prefix = req.getContextPath();
		String queryStr = req.getQueryString();
		prefix = prefix == null ? "" : prefix;
		
		log.debug("request url=[{}]", req.getRequestURL().toString()
				+ (StringUtils.isEmpty(queryStr) ? "" : "?" + queryStr));
		
		// 是否排除规则
		if (canDo(excluded, prefix, uri)) {
			chain.doFilter(req, resp);
			return;
			// 是否登录，并检查包括规则
			// 1.先从本子系统中检查是否登录
		} else if (!PmsController.checkLogin(req)) {
			// 2.如果本子系统中没有登录，则带上子系统的URL从验证中心检查是否已经登录
			String url = req.getRequestURL().toString();
			
			@SuppressWarnings("deprecation")
			String checkUrl = apiService.getApiUrl()
					+ "/remoteauth/check_login?proj=" + apiService.getToken()
					+ "&url=" + URLEncoder.encode(url);
			
			resp.sendRedirect(resp.encodeRedirectURL(checkUrl));
			return;
		} else if (PmsController.checkLogin(req)) {
			if (canDo(included, prefix, uri)) {
				chain.doFilter(req, resp);
				return;
				// 检查是否有权限规则
			} else if (PmsController.hasPower(req)) {
				chain.doFilter(req, resp);
				return;
				// 如果是登出，清空session
			} else if ((prefix + "/login_out").equals(uri)) {
				PmsController.removeUserFromSession(req.getSession());
				String loginUrl = apiService.getApiUrl() + "/auth?sp="
						+ apiService.getToken();
				resp.setCharacterEncoding("utf-8");
				resp.getWriter().println(
						"<script type=\"text/javascript\" language=\"javascript\">top.location.href= '"
								+ loginUrl + "'; </script>");
				resp.getWriter().flush();
				resp.getWriter().close();
				return;
			}
		}
		// DWR
		if (req.getRequestURI().startsWith(prefix + "/dwr/call/plaincall/")) {
			resp.setContentType("text/plain");
			resp.getWriter().println("//#DWR-INSERT");
			resp.getWriter().println(urlRedirect);
			resp.getWriter().println("//#DWR-REPLY");
			return;
		}
		
		log.debug("refuse:" + uri);
		
		String basePath = urlRedirect;
		if (basePath.startsWith("/")) {
			basePath = req.getScheme() + "://" + req.getServerName() + ":"
					+ req.getServerPort() + req.getContextPath() + basePath;
		}
		resp.sendRedirect(basePath);
	}
	
}
