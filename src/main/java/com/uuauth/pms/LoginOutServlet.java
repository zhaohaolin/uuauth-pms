/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    LogOutServlet.java
 * Creator:     joe.zhao
 * Create-Date: 下午12:17:00
 */
package com.uuauth.pms;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.toolkit.lang.SecurityUtil;
import com.toolkit.lang.WebClient;
import com.uuauth.api.domain.Employee;
import com.uuauth.api.service.UUAuthAPIService;
import com.uuauth.pms.controller.PmsController;

/**
 * 退出登录操作
 * 
 * @author joe.zhao
 * @version $Id: LogOutServlet, v 0.1 2012-6-2 下午12:17:00 Exp $
 */
public class LoginOutServlet extends HttpServlet implements PmsConst {
	
	/**  */
	private static final long	serialVersionUID	= -5642474604463853118L;
	
	private UUAuthAPIService	authApiService		= UUAuthAPIService
															.getInstance();
	
	public void init() {
		//
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doGet(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute(sessionUser);
		String user = employee.getLoginName();
		// 清除掉本地session
		PmsController.removeUserFromSession(req.getSession());
		ServletContext context = this.getServletContext();
		// 在线人数减1
		Long onLineNum = (Long) context.getAttribute(ONLINE_NUM);
		context.setAttribute(ONLINE_NUM, onLineNum.longValue() - 1);
		
		// 清除客户端的cookie
		Cookie cookie = new Cookie(TOKEN_COOKIE, null);
		cookie.setPath("/");// 根据你创建cookie的路径进行填写
		resp.addCookie(cookie);
		
		// 清除aps的缓存
		String check = SecurityUtil.MD5(user + authApiService.getPassword()
				+ authApiService.getToken());
		String outApiUrl = authApiService.getApiUrl()
				+ "/remoteauth/user_login_out" + "?user="
				+ employee.getLoginName() + "&token="
				+ authApiService.getToken() + "&check=" + check;
		WebClient.retrieveWebContent(outApiUrl);
		
		// 跳转到登录去
		String loginUrl = authApiService.getApiUrl() + "/auth?sp="
				+ authApiService.getToken();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter()
				.println(
						"<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>login-out</title></head><body>"
								+ "<script type=\"text/javascript\" language=javascript>top.location.href= '"
								+ loginUrl + "'; </script>" + "</body></html>");
		resp.getWriter().flush();
		resp.getWriter().close();
		return;
	}
	
}
