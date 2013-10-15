package com.uuauth.api.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uuauth.api.exception.ClientInitParamConfigException;
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
	private static final String	APIURL				= "app.url";
	private static final String	APPSUFFIX			= "app.suffix";
	private static final Logger	LOG					= LoggerFactory
															.getLogger(GetLoginTokenServlet.class);
	
	public void init(ServletConfig config) throws ServletException {
		try {
			String token = config.getInitParameter(APPTOKEN);
			if (StringUtils.isEmpty(token)) {
				throw new ClientInitParamConfigException(
						"register init app.token fauiler.");
			}
			apiService.setToken(token);
			
			String password = config.getInitParameter(APPPASSWORD);
			if (StringUtils.isEmpty(password)) {
				throw new ClientInitParamConfigException(
						"register init app.password fauiler.");
			}
			apiService.setPassword(password);
			
			String apiUrl = config.getInitParameter(APIURL);
			if (StringUtils.isEmpty(apiUrl)) {
				throw new ClientInitParamConfigException(
						"register init app.url fauiler.");
			}
			apiService.setApiUrl(apiUrl);
			
			String suffix = config.getInitParameter(APPSUFFIX);
			if (StringUtils.isEmpty(suffix)) {
				throw new ClientInitParamConfigException(
						"register init app.suffix fauiler.");
			}
			apiService.setSuffix(suffix);
			
			apiService.init();
		} catch (ClientInitParamConfigException e) {
			// e.printStackTrace();
			LOG.error("[{}]", e.getMessage());
		}
		
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
