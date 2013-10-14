package com.uuauth.pms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkit.lang.ParamUtils;
import com.uuauth.api.client.AppConst;
import com.uuauth.api.domain.ProjectRole;
import com.uuauth.api.service.UUAuthAPIService;
import com.uuauth.pms.model.Power;
import com.uuauth.pms.model.Role;
import com.uuauth.pms.resources.css.CssLoader;
import com.uuauth.pms.resources.image.ImageLoader;
import com.uuauth.pms.service.PowerService;
import com.uuauth.pms.service.RoleService;
import com.uuauth.pms.service.impl.PowerServiceImpl;
import com.uuauth.pms.service.impl.RoleServiceImpl;

public class PmsServlet extends HttpServlet {
	
	public static final String	version				= "v1.0";
	private static final long	serialVersionUID	= 1L;
	private final Logger		logger				= LoggerFactory
															.getLogger(PmsServlet.class);
	private PowerService		powerService		= new PowerServiceImpl();
	private RoleService			roleService			= new RoleServiceImpl();
	
	@Override
	public void init() throws ServletException {
		logger.info("Pms4t " + version + " Initialization start.");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String action = "roleList";
		try {
			String temp = ParamUtils.getString(req, "action", null);
			if (temp != null) {
				action = temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			getClass().getMethod(
					action,
					new Class[] { HttpServletRequest.class,
							HttpServletResponse.class }).invoke(this,
					new Object[] { req, resp });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void css(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/css");
		PrintWriter writer = resp.getWriter();
		writer.print(CssLoader.getCss());
		writer.close();
	}
	
	public void image(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("image/gif");
		String name = req.getParameter("name");
		ImageLoader.getImage(resp, name);
	}
	
	public static boolean checkRoleIsExist(Role role, List<String> list) {
		if (null == list || list.isEmpty())
			return false;
		for (String roleName : list) {
			if (roleName.equals(role.getName()))
				return true;
		}
		return false;
	}
	
	public void roleList(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<String> roleList = new ArrayList<String>();
		roleList = roleService.getRoleList();
		// 5.显示角色列表
		List<Role> list = new ArrayList<Role>();
		Map<String, Role> roleMap = roleService.getRoleMap();
		for (String role : roleList) {
			if (roleMap.containsKey(role)) {
				list.add(roleMap.get(role));
				roleMap.remove(role);
			} else {
				Role adminRole = new Role();
				adminRole.setName(role);
				adminRole.setDescription("");
				adminRole.setPowers("|");
				list.add(adminRole);
				roleService.addRole(adminRole);
			}
		}
		// 1.从API拉取本系统角色列表
		String token = (String) this.getServletContext().getAttribute(
				AppConst.appToken);
		// 2.再从本系统中查询已有的所有角色列表
		if (!StringUtils.isEmpty(token)) {
			List<ProjectRole> proRolelist = UUAuthAPIService.getInstance()
					.getProjRoleList(token);
			// 3.把新添加的角色添加到本系统中
			for (ProjectRole projectRole : proRolelist) {
				Role role = new Role();
				role.setId(projectRole.getId());
				role.setName(projectRole.getRoleName());
				role.setDescription(projectRole.getRoleDesc());
				role.setPowers("|");
				if (token.equals(projectRole.getProToken())
						&& !checkRoleIsExist(role, roleList)) {
					// 把这个角色添加到子系统的数据库中
					roleService.addRole(role);
					list.add(role);
				}
			}
			// 4.把本系统中有的，但API中没有(说明是已经删除的角色)给删除掉
		}
		// for (String role : roleMap.keySet()) {
		// RoleService.delRole(role);
		// }
		req.setAttribute("list", list);
		PageController.roleList(req, resp);
	}
	
	public void showEditRole(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		if (name == null) {
			req.getSession().setAttribute("optMsg", "缺少参数！");
			resp.sendRedirect(req.getRequestURL().toString()
					+ "?action=roleList");
			return;
		}
		Role role = roleService.getRoleByName(name);
		if (role == null) {
			req.getSession().setAttribute("optMsg", "参数不正确！");
			resp.sendRedirect(req.getRequestURL().toString()
					+ "?action=roleList");
			return;
		}
		req.setAttribute("role", role);
		List<Power> list = powerService.adminPowerList();
		if (role.getPowers() != null && role.getPowers().trim().length() > 1) {
			for (Power power : list) {
				if (role.getPowers().indexOf("|" + power.getId() + "|") != -1)
					power.setSelect(true);
				List<Power> list1 = power.getList();
				if (list1 != null && list1.size() > 0) {
					for (Power power1 : list1) {
						if (role.getPowers()
								.indexOf("|" + power1.getId() + "|") != -1)
							power1.setSelect(true);
						List<Power> list2 = power1.getList();
						if (list2 != null && list2.size() > 0) {
							for (Power power2 : list2) {
								if (role.getPowers().indexOf(
										"|" + power2.getId() + "|") != -1)
									power2.setSelect(true);
								
							}
						}
					}
				}
			}
		}
		req.setAttribute("list", list);
		PageController.role(req, resp);
	}
	
	public void editRole(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Role role = new Role();
		role.setName(req.getParameter("name"));
		role.setDescription(req.getParameter("description"));
		String powers = "|";
		Enumeration<?> params = req.getParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			if (param.matches("^(\\d{2}){1,3}$")) {
				powers += param + "|";
			}
		}
		role.setPowers(powers);
		if (roleService.editRole(role)) {
			req.getSession().setAttribute("optMsg", "编辑角色成功！");
		} else {
			req.getSession().setAttribute("optMsg", "编辑角色失败！");
		}
		resp.sendRedirect(req.getRequestURL().toString() + "?action=roleList");
	}
	
	public void powerList(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Power> list = powerService.adminPowerList();
		req.setAttribute("list", list);
		PageController.powerList(req, resp);
	}
	
	public void showAddPower(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Power power = new Power();
		String parentId = req.getParameter("parentId");
		if (parentId != null) {
			power.setId(powerService.getNextSubPowerId(parentId));
		}
		req.setAttribute("power", power);
		PageController.power(req, resp);
	}
	
	public void addPower(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Power power = new Power();
		if (validPower(req, power, true)) {
			PageController.power(req, resp);
		} else {
			if (powerService.addPower(power)) {
				req.getSession().setAttribute("optMsg", "添加权限成功！");
			} else {
				req.getSession().setAttribute("optMsg", "添加权限失败！");
			}
			resp.sendRedirect(req.getRequestURL().toString()
					+ "?action=powerList");
		}
	}
	
	public boolean validPower(HttpServletRequest req, Power power, boolean add) {
		boolean flag = false;
		try {
			power.setId(req.getParameter("id"));
			power.setName(req.getParameter("name"));
			power.setPower(req.getParameter("power"));
			power.setUrl(req.getParameter("url"));
			power.setDescription(req.getParameter("description"));
		} catch (Exception e) {
			flag = true;
		}
		req.setAttribute("power", power);
		if (power.getId() == null || power.getId().trim().length() == 0) {
			flag = true;
			req.setAttribute("idErr", "权限编号不能为空！");
		} else if (!power.getId().matches("^(\\d{2}){1,3}$")) {
			flag = true;
			req.setAttribute("idErr", "权限编号格式不正确！");
		} else if (!powerService.checkParentPowerIdisExist(power.getId())) {
			flag = true;
			req.setAttribute("idErr", "父权限不存在！");
		} else if ((add || !power.getId().equals(req.getParameter("oldId")))
				&& powerService.checkPowerIdIsExist(power.getId())) {
			flag = true;
			req.setAttribute("idErr", "权限编号格式已经存在！");
		} else if (!add) {
			String oldId = req.getParameter("oldId");
			if (oldId != null && power.getId().length() != oldId.length()) {
				flag = true;
				req.setAttribute("idErr", "权限编号长度与原编号不一致！");
			}
		}
		
		if (power.getName() == null || power.getName().trim().length() == 0) {
			flag = true;
			req.setAttribute("nameErr", "权限名称不能为空！");
		} else if (power.getName().length() > 32) {
			flag = true;
			req.setAttribute("nameErr", "权限名称长度太长！");
		}
		// if (power.getPower() == null || power.getPower().trim().length() ==
		// 0) {
		// flag = true;
		// request.setAttribute("powerErr", "权限正则式不能为空！");
		// }
		// else
		// if (power.getPower().trim().length() > 128) {
		// flag = true;
		// request.setAttribute("powerErr", "权限正则式长度太长！");
		// }
		// else
		if (power.getPower() != null) {
			power.setPower(power.getPower().trim());
		}
		// if (power.getUrl() == null || power.getUrl().trim().length() == 0) {
		// flag = true;
		// request.setAttribute("urlErr", "权限URL不能为空！");
		// }
		// else
		if (power.getUrl().length() > 64) {
			flag = true;
			req.setAttribute("urlErr", "菜单链接长度太长！");
		}
		// if (power.getDescription() == null
		// || power.getDescription().trim().length() == 0) {
		// flag = true;
		// request.setAttribute("descriptionErr", "权限描述不能为空！");
		// }
		// else
		if (power.getDescription().length() > 128) {
			flag = true;
			req.setAttribute("descriptionErr", "权限描述长度太长！");
		}
		return flag;
	}
	
	public void showEditPower(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id == null) {
			req.getSession().setAttribute("optMsg", "缺少参数！");
			resp.sendRedirect(req.getRequestURL().toString()
					+ "?action=powerList");
			return;
		}
		Power power = powerService.getPowerById(id);
		if (power == null) {
			req.getSession().setAttribute("optMsg", "参数不正确！");
			resp.sendRedirect(req.getRequestURL().toString()
					+ "?action=powerList");
			return;
		}
		req.setAttribute("power", power);
		req.setAttribute("oldId", id);
		PageController.power(req, resp);
	}
	
	public void editPower(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oldId = req.getParameter("oldId");
		req.setAttribute("oldId", oldId);
		Power power = new Power();
		if (validPower(req, power, false)) {
			PageController.power(req, resp);
		} else {
			if (powerService.editPower(power, oldId)) {
				req.getSession().setAttribute("optMsg", "编辑权限成功！");
			} else {
				req.getSession().setAttribute("optMsg", "编辑权限失败！");
			}
			resp.sendRedirect(req.getRequestURL().toString()
					+ "?action=powerList");
		}
	}
	
	public void delPower(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String id = req.getParameter("id");
		if (id == null) {
			req.getSession().setAttribute("optMsg", "缺少参数！");
			resp.sendRedirect(req.getRequestURL().toString()
					+ "?action=powerList");
			return;
		}
		// Power power = PowerService.getPowerById(id);
		if (powerService.delPower(id)) {
			req.getSession().setAttribute("optMsg", "删除权限成功！");
		} else {
			req.getSession().setAttribute("optMsg", "删除权限成功！");
		}
		resp.sendRedirect(req.getRequestURL().toString() + "?action=powerList");
	}
}
