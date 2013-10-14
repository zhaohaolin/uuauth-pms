package com.uuauth.api.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.uuauth.api.service.UUAuthAPIService;

/**
 * 
 * config this servlet uri=["/login/get_login_token"]
 * 
 * @author joe.zhao
 * @version $Id: GetLoginTokenServlet, v 0.1 2012-5-16 下午03:41:00 Exp $
 */
public class GetLoginTokenServlet extends HttpServlet {
	
	/**  */
	private static final long	serialVersionUID	= 4045479596880248650L;
	private UUAuthAPIService	apiService			= UUAuthAPIService
															.getInstance();
	private static final String	APPTOKEN			= "app.token";
	private static final String	APPPASSWORD			= "app.password";
	private static final String	APIURL				= "app.api.url";
	private static final String	APPSUFFIX			= "app.suffix";
	
	public void init(ServletConfig config) throws ServletException {
		String token = config.getInitParameter(APPTOKEN);
		if (StringUtils.isNotEmpty(token)) {
			apiService.setToken(token);
		}
		
		String password = config.getInitParameter(APPPASSWORD);
		if (StringUtils.isNotEmpty(password)) {
			apiService.setPassword(password);
		}
		
		String apiUrl = config.getInitParameter(APIURL);
		if (StringUtils.isNotEmpty(apiUrl)) {
			apiService.setApiUrl(apiUrl);
		}
		
		String suffix = config.getInitParameter(APPSUFFIX);
		if (StringUtils.isNotEmpty(suffix)) {
			apiService.setSuffix(suffix);
		}
		apiService.init();
		super.init(config);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}
	
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String name = req.getParameter("name");
		String role = req.getParameter("role");
		String id = req.getParameter("id");
		String check = req.getParameter("check");
		String sessionToken = req.getParameter("token");
		
		// 主要为客户端用于验证的
		apiService.saveSessionToken(sessionToken, id);
		apiService.getUserById(Integer.valueOf(id));
		
		String loginToken = apiService.getLoginToken(name, role, id, check);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(loginToken);
		out.flush();
		out.close();
		out = null;
	}
	
}
