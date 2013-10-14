package com.uuauth.pms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uuauth.api.domain.Employee;
import com.uuauth.pms.AuthConstants;
import com.uuauth.pms.PmsConst;
import com.uuauth.pms.model.Power;
import com.uuauth.pms.service.PowerService;

public abstract class PmsController implements AuthConstants, PmsConst {
	
	final static Logger	log	= LoggerFactory.getLogger(PmsController.class);
	
	public final static boolean checkLogin(HttpServletRequest req) {
		// 检查session
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute(sessionUser);
		if (null != employee) {
			log.info("employee=[{}] is exist on req=[{}].", new Object[] {
					employee, req });
			return true;
		}
		
		log.info("employee is not exist on req=[{}].", new Object[] { req });
		return false;
	}
	
	public final static void setUserToSession(PowerService powerService,
			HttpSession session, String role) {
		if (session != null) {
			session.setAttribute(sessionRole, role);
			session.setAttribute(sessionPowers,
					powerService.getAdminPmsList(role));
		}
	}
	
	public final static String getRoleFromSession(HttpSession session) {
		if (session != null) {
			return (String) session.getAttribute(sessionRole);
		}
		return null;
	}
	
	public final static void removeUserFromSession(HttpSession session) {
		if (session != null) {
			session.removeAttribute(sessionUser);
			session.removeAttribute(sessionRole);
			session.removeAttribute(sessionPowers);
			session.setAttribute(sessionUser, null);
			session.setAttribute(sessionRole, null);
			session.setAttribute(sessionPowers, null);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final static boolean hasPower(HttpServletRequest req, String url) {
		if (req != null && url != null) {
			HttpSession session = req.getSession();
			List<Power> list = (List<Power>) session
					.getAttribute(sessionPowers);
			if (list != null && list.size() > 0) {
				for (Power power : list) {
					if (power.getPower() != null
							&& power.getPower().trim().length() > 0) {
						if (url.matches(power.getPower())) {
							return true;
						}
					}
					List<Power> list1 = power.getList();
					if (list1 != null && list1.size() > 0) {
						for (Power power1 : list1) {
							if (power1.getPower() != null
									&& power1.getPower().trim().length() > 0) {
								if (url.matches(power1.getPower())) {
									return true;
								}
							}
							List<Power> list2 = power1.getList();
							if (list2 != null && list2.size() > 0) {
								for (Power power2 : list2) {
									if (power2.getPower() != null
											&& power2.getPower().trim()
													.length() > 0) {
										if (url.matches(power2.getPower())) {
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public final static boolean hasPower(HttpServletRequest req) {
		if (req != null) {
			String url = req.getRequestURI();
			String contextPath = req.getContextPath();
			if (contextPath != null && contextPath.trim().length() > 0
					&& url.startsWith(contextPath)) {
				url = url.substring(contextPath.length());
			}
			String query = req.getQueryString();
			return hasPower(req, url + (query == null ? "" : "?" + query));
		}
		return false;
	}
}
