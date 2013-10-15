/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    AutoLoginServlet.java
 * Creator:     joe.zhao
 * Create-Date: 下午12:16:48
 */
package com.uuauth.pms;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uuauth.api.client.AppConst;
import com.uuauth.api.domain.Employee;
import com.uuauth.api.domain.ProjectRole;
import com.uuauth.api.service.UUAuthAPIService;
import com.uuauth.pms.controller.PmsController;
import com.uuauth.pms.model.Power;
import com.uuauth.pms.service.PowerService;
import com.uuauth.pms.service.impl.PowerServiceImpl;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: AutoLoginServlet, v 0.1 2012-6-2 下午12:16:48 Exp $
 */
public class AutoLoginServlet extends HttpServlet implements AuthConstants,
		PmsConst {
	
	/**  */
	private static final long	serialVersionUID	= 5587394358271934898L;
	private Logger				log					= LoggerFactory
															.getLogger(AutoLoginServlet.class);
	private UUAuthAPIService	apiService			= UUAuthAPIService
															.getInstance();
	private PowerService		powerService		= PowerServiceImpl
															.getInstance();
	private String				indexPage			= "/index.html";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (!StringUtils.isEmpty(config.getInitParameter("indexPage")))
			indexPage = config.getInitParameter("indexPage");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doGet(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String name = req.getParameter("name");
		String check = req.getParameter("check");
		String token = req.getParameter("token");
		String url = req.getParameter("url");
		
		if (name != null && check != null) {
			Map<String, String> map = apiService.checkToken(name, check);
			if (map != null) {
				String roles = map.get("role");
				Integer id = Integer.valueOf(map.get("id"));
				apiService.addUser(id.toString());
				HttpSession session = req.getSession();
				ServletContext context = getServletContext();
				
				String clientIp = req.getRemoteAddr();
				log.info("session token=[{}], roles=[{}]", new Object[] {
						token, roles });
				
				Employee employee = (Employee) session
						.getAttribute(PmsConst.sessionUser);
				if (null != employee) {
					if (!StringUtils.isEmpty(url)) {
						resp.sendRedirect(resp.encodeURL(url));
						return;
					}
					resp.sendRedirect(resp.encodeURL(indexPage));
					return;
				}
				//
				String employeeId = apiService.getIdByToken(token);
				List<Power> menuList = powerService.getAdminPmsList(roles);
				employee = apiService.getUserById(Integer.valueOf(employeeId));
				log.info("session employee=[{}]", employee);
				if (null != employee) {
					// 把用户session token保存进子系统的session中
					session.setAttribute(COOKIE_TOKEN, token);
					
					session.setAttribute(PmsConst.sessionUser, employee);
					session.setAttribute(PmsConst.sessionPowers, menuList);
					PmsController
							.setUserToSession(powerService, session, roles);
					
					// 设置在线人数量
					Long onLineNum = (Long) context.getAttribute(ONLINE_NUM);
					if (null == onLineNum)
						onLineNum = Long.valueOf(0);
					context.setAttribute(ONLINE_NUM, onLineNum.longValue() + 1);
					
					log.info("User login success: [{}], ip=[{}]"
							+ new Object[] { employee.getName(), clientIp });
					
					// 把sessionToken 保存进session中
					String sessionToken = (String) session
							.getAttribute(AppConst.sessionToken);
					if (StringUtils.isEmpty(sessionToken)
							|| !sessionToken.equals(token))
						req.getSession().setAttribute(AppConst.sessionToken,
								token);
					
					// 从全局变量中取得appToken
					String proToken = (String) getServletContext()
							.getAttribute(AppConst.appToken);
					log.info("project token=[{}]", proToken);
					
					List<ProjectRole> rolelist = apiService
							.getProjRoleList(proToken);
					
					String rolestr = "|";
					for (ProjectRole role : rolelist) {
						rolestr += role.getRoleName() + "|";
					}
					log.info("project rolelist=[{}]", rolestr);
					
				} else {
					log.warn(
							"User login failer: name=[{}], check=[{}], token=[{}], ip=[{}]",
							new Object[] { name, check, token, clientIp });
				}
				
				if (!StringUtils.isEmpty(url)) {
					resp.sendRedirect(resp.encodeURL(url));
					return;
				}
				resp.sendRedirect(resp.encodeURL(indexPage));
				return;
			}
		}
		resp.getWriter().print("^_^ request error.");
		resp.getWriter().flush();
		resp.getWriter().close();
	}
	
}
